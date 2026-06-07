package net.chakchak777.entities.custom;

import net.chakchak777.blocks.ModBlocks;
import net.chakchak777.entities.azure.CatostDispatcher;
import net.chakchak777.entities.base.ChachakDialogueMob;
import net.chakchak777.entities.dialogue.DialogueScenario;
import net.chakchak777.items.ModItems;
import net.chakchak777.network.SentDialogueLine;
import net.chakchak777.quest.QuestAdvancements;
import net.chakchak777.quest.QuestDefinitions;
import net.chakchak777.network.TotemAnimationPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.List;

public class CatostEntity extends ChachakDialogueMob {

    public CatostEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public int feedingAmount = 0;
    public boolean figurineReward = false;
    public boolean VodkaReward = false;

    public static final int FEEDINGS_FOR_FIGURINE = 5;
    private final CatostDispatcher animationDispatcher = new CatostDispatcher(this);

    @Override
    protected List<DialogueScenario> getScenarios() {
        return net.chakchak777.entities.custom.dialogues.CatostDialogues.SCENARIOS;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (player.isCreative() && player.isShiftKeyDown()) {
                if (!level().isClientSide) {
                    cycle(player);
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            } else if (itemStack.is(ModItems.CAT_FOOD)) {
                if (!level().isClientSide) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }


                    this.playSound(SoundEvents.CAT_EAT);

                    ServerLevel serverLevel = (ServerLevel) this.level();
                    serverLevel.sendParticles(
                            ParticleTypes.HEART,
                            this.getX(),
                            this.getY() + 1.0,
                            this.getZ(),
                            5,
                            0.3, 0.3, 0.3,
                            0.02);

                    if (!this.figurineReward) {
                        this.feedingAmount++;
                        if (this.feedingAmount >= FEEDINGS_FOR_FIGURINE) {
                            getFigurineRewarg(player, serverLevel);
                        }
                    }
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            }
            if (!level().isClientSide) {
                if (VodkaReward && (getCurrentScenario() == 2)) {
                    getVodkaReward(player, (ServerLevel) this.level());
                    return InteractionResult.PASS;
                } else {
                    startDialogue(player);
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);

        }
        return super.mobInteract(player, hand);
    }

    private void getFigurineRewarg(Player player, ServerLevel serverLevel) {
        this.figurineReward = true;
        this.feedingAmount = FEEDINGS_FOR_FIGURINE;

        ItemStack rewardStack = new ItemStack(ModItems.CATOST_FIGURINE.get());
        if (!player.getInventory().add(rewardStack.copy())) {
            player.drop(rewardStack.copy(), false);
        }

        this.playSound(SoundEvents.TOTEM_USE, 1.0F, 1.0F);

        serverLevel.sendParticles(
                ParticleTypes.HEART,
                this.getX(),
                this.getY() + 1.0,
                this.getZ(),
                20,
                0.4, 0.4, 0.4,
                0.02
        );

        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new TotemAnimationPacket(new ItemStack(ModItems.CATOST_FIGURINE.get())));

        }
    }

    @Override
    protected void onDialogueEnded(ServerPlayer player, int currentScenario) {
        if (currentScenario == 2) {
            getVodkaReward(player, (ServerLevel) this.level());


        }
        QuestAdvancements.complete(player, QuestDefinitions.questIdForScenario(currentScenario));
        ;
    }

    private void getVodkaReward(Player player, ServerLevel serverLevel) {

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (this.VodkaReward) {
            PacketDistributor.sendToPlayersNear(
                    serverLevel,
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    10,
                    new SentDialogueLine("Брат, чекушку я тебе уже отдал, это одноразовое предложение", "catost", 60));
            return;
        }

        this.VodkaReward = true;

        ItemStack rewardStack = new ItemStack(ModBlocks.VODKA.asItem());
        if (!player.getInventory().add(rewardStack.copy())) {
            player.drop(rewardStack.copy(), false);
        }

        this.playSound(SoundEvents.TOTEM_USE, 1.0F, 1.0F);

        serverLevel.sendParticles(
                ParticleTypes.HEART,
                this.getX(),
                this.getY() + 1.0,
                this.getZ(),
                20,
                0.4, 0.4, 0.4,
                0.02
        );

        PacketDistributor.sendToAllPlayers( new TotemAnimationPacket(new ItemStack(ModItems.CATOST_FIGURINE.get())));


    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.SAFE_FALL_DISTANCE)
                .add(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FeedingAmount", this.feedingAmount);
        compound.putBoolean("FigurineReward", this.figurineReward);
        compound.putBoolean("VodkaReward", this.VodkaReward);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.feedingAmount = compound.getInt("FeedingAmount");
        this.figurineReward = compound.getBoolean("FigurineReward");
        this.VodkaReward = compound.getBoolean("VodkaReward");
    }




    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            if (this.isPlaying) {
                if (this.timer > 0) {
                    this.timer--;
                } else {
                    advanceDialogue();
                }
            }
        }
        if (level().isClientSide) {
            Runnable animationRunner;
            if (isAnimPlay()) {
                animationRunner = animationDispatcher::sleep;
                animationRunner.run();
            }
        }
    }
}


