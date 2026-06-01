package net.chakchak777.entities;

import net.chakchak777.items.ModItems;
import net.chakchak777.network.DialoguePacket;
import net.chakchak777.quest.QuestAdvancements;
import net.chakchak777.quest.QuestDefinitions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ChachakDialogueMob extends PathfinderMob {
    protected ChachakDialogueMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }


    private static final int FEEDINGS_FOR_FIGURINE = 5;

    private static final RawAnimation ANIM_SLEEP = RawAnimation.begin().thenLoop("sleep");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> IS_SITTING =
            SynchedEntityData.defineId(net.chakchak777.entities.custom.CatostEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_SLEEPING =
            SynchedEntityData.defineId(net.chakchak777.entities.custom.CatostEntity.class, EntityDataSerializers.BOOLEAN);

    public record DialogueLine(String text, int ticks, String icon) {
    }

    public record DialogueScenario(boolean startsWithAnim,
                                   List<net.chakchak777.entities.custom.CatostEntity.DialogueLine> lines) {
    }

    private static final List<net.chakchak777.entities.custom.CatostEntity.DialogueScenario> SCENARIOS = List.of(
            new net.chakchak777.entities.custom.CatostEntity.DialogueScenario(false, List.of(
                    new net.chakchak777.entities.custom.CatostEntity.DialogueLine("DefaultDialog", 20, "player")

            ))
    );

    private int currentScenario = 0;
    private int currentStep = 0;
    private int timer = 0;
    private boolean isPlaying = false;
    private ServerPlayer dialoguePlayer;




    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (!level().isClientSide) {
            applyScenarioPose();
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (player.isCreative() && player.isShiftKeyDown()) {
                cycle(player);
            } else if (itemStack.is(ModItems.CAT_FOOD)) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                this.playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);

                ServerLevel serverLevel = (ServerLevel) this.level();
                serverLevel.sendParticles(
                        ParticleTypes.HEART,
                        this.getX(),
                        this.getY() + 1.0,
                        this.getZ(),
                        5,
                        0.3, 0.3, 0.3,
                        0.02
                );


            } else if (isSleeping()) {
                wakeUp();
                startDialogue(player);
            } else {
                startDialogue(player);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private void giveVodkaReward(ServerPlayer player) {
        ItemStack rewardStack = new ItemStack(ModItems.VODKA.get());
        if (!player.getInventory().add(rewardStack.copy())) {
            player.drop(rewardStack.copy(), false);
        }
        this.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.2F);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    12,
                    0.4, 0.4, 0.4,
                    0.02
            );
        }
    }

    public void cycle(Player player) {
        if (this.isPlaying) {
            return;
        }

        this.currentScenario++;
        if (this.currentScenario >= SCENARIOS.size()) {
            this.currentScenario = 0;
        }

        applyScenarioPose();
        player.sendSystemMessage(Component.literal("Сценарий: " + (this.currentScenario + 1)
                + (getScenario().startsAsleep() ? " (спит)" : " (бодрствует)")));
    }

    public void wakeUp() {
        if (!isSleeping()) {
            return;
        }
        setSitting();
    }

    public void startDialogue(Player player) {
        if (this.isPlaying || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        this.dialoguePlayer = serverPlayer;
        this.currentStep = 0;
        this.isPlaying = true;
        QuestAdvancements.unlock(serverPlayer, QuestDefinitions.questIdForScenario(this.currentScenario));
        setSitting();
        playCurrentLine();
    }

    private void playCurrentLine() {
        if (this.level() == null || this.dialoguePlayer == null) {
            return;
        }

        net.chakchak777.entities.custom.CatostEntity.DialogueLine line = getScenario().lines().get(this.currentStep);
        PacketDistributor.sendToPlayer(this.dialoguePlayer, new DialoguePacket(line.text(), line.icon()));
        this.timer = line.ticks();
    }

    private void advanceDialogue() {
        this.currentStep++;
        if (this.currentStep >= getScenario().lines().size()) {
            endDialogue();
        } else {
            playCurrentLine();
        }
    }

    private void endDialogue() {
        this.isPlaying = false;
        this.currentStep = 0;

        if (this.dialoguePlayer != null) {
            if (this.currentScenario == 0) {
                giveVodkaReward(this.dialoguePlayer);
            }
            QuestAdvancements.complete(
                    this.dialoguePlayer,
                    QuestDefinitions.questIdForScenario(this.currentScenario)
            );
            PacketDistributor.sendToPlayer(this.dialoguePlayer, new DialoguePacket("", ""));
            this.dialoguePlayer = null;
        }

        applyScenarioPose();
    }

    private void applyScenarioPose() {
        if (getScenario().startsAsleep() && !this.isPlaying) {
            setSleeping();
        } else {
            setSitting();
        }
    }

    private net.chakchak777.entities.custom.CatostEntity.DialogueScenario getScenario() {
        return SCENARIOS.get(this.currentScenario);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CurrentScenario", this.currentScenario);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.currentScenario = Math.floorMod(tag.getInt("CurrentScenario"), SCENARIOS.size());

        resetDialogueState();
        if (!level().isClientSide) {
            applyScenarioPose();
        }
    }

    public void resetDialogueState() {
        this.isPlaying = false;
        this.currentStep = 0;
        this.timer = 0;
        this.dialoguePlayer = null;
    }

    public ServerPlayer getDialoguePlayer() {
        return this.dialoguePlayer;
    }

    public void onDialoguePlayerDisconnect(ServerPlayer player) {
        if (this.dialoguePlayer == player) {
            endDialogue();
        }
    }

    public int getFeedingAmount() {
        return this.feedingAmount;
    }

    public int getCurrentScenario() {
        return this.currentScenario;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_SITTING, true);
        builder.define(IS_SLEEPING, false);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "state_controller", 5, state -> {
            if (this.isSleeping()) {
                return state.setAndContinue(ANIM_SLEEP);
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean isSitting() {
        return this.entityData.get(IS_SITTING);
    }

    public boolean isSleeping() {
        return this.entityData.get(IS_SLEEPING);
    }

    public void setSitting() {
        this.entityData.set(IS_SITTING, true);
        this.entityData.set(IS_SLEEPING, false);
    }

    public void setSleeping() {
        this.entityData.set(IS_SITTING, false);
        this.entityData.set(IS_SLEEPING, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new net.chakchak777.entities.custom.CatostEntity.AlwaysStillGoal(this));
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8, 1) {
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.SAFE_FALL_DISTANCE)
                .add(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && this.isPlaying) {
            if (this.timer > 0) {
                this.timer--;
            } else {
                advanceDialogue();
            }
        }
    }
}
