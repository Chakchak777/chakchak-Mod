package net.chakchak777.entities.base;

import net.chakchak777.entities.dialogue.DialogueLine;
import net.chakchak777.entities.dialogue.DialogueNpc;
import net.chakchak777.entities.dialogue.DialogueScenario;
import net.chakchak777.network.DialoguePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public abstract class ChachakDialogueMob extends PathfinderMob implements DialogueNpc {
    protected ChachakDialogueMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }



    private static final EntityDataAccessor<Boolean> IS_ANIM_PLAY=
            SynchedEntityData.defineId(ChachakDialogueMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ANIM_STILL =
            SynchedEntityData.defineId(ChachakDialogueMob.class, EntityDataSerializers.BOOLEAN);


    private int currentScenario = 0;
    private int currentStep = 0;
    private int timer = 0;
    private boolean isPlaying = false;
    private ServerPlayer dialoguePlayer;

    protected abstract List<DialogueScenario> getScenarios();

    protected void onDialogueStarted(ServerPlayer player, int currentScenario){
    }
    protected void onDialogueEnded(ServerPlayer player, int currentScenario){
    }




    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (!level().isClientSide) {
            applyScenarioPose();
        }
    }

    protected boolean ItemInteract(Player player, ItemStack itemStack){
        return false;
    }



    public void cycle(Player player) {
        if (this.isPlaying) {
            return;
        }

        this.currentScenario++;
        if (this.currentScenario >= getScenarios().size()) {
            this.currentScenario = 0;
        }

        applyScenarioPose();
        player.sendSystemMessage(Component.literal("Сценарий: " + (this.currentScenario + 1)
                + (getScenario().startsWithoutAnim() ? " (With anim)" : " (Without anim)")));
    }

    public void stopAnim() {
        if (!isPlaying) {
            return;
        }
        setStopAnim();
    }

    public void startDialogue(Player player) {
        if (this.isPlaying || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        this.dialoguePlayer = serverPlayer;
        this.currentStep = 0;
        this.isPlaying = true;
        onDialogueStarted(serverPlayer, this.currentScenario);
        setStopAnim();
        playCurrentLine();
    }

    private void playCurrentLine() {
        if (this.level() == null || this.dialoguePlayer == null) {
            return;
        }

        DialogueLine line = getScenario().lines().get(this.currentStep);

        if (this.level() instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersNear(
                    serverLevel,
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    10,
                    new DialoguePacket(line.text(), line.icon()));
            this.timer = line.ticks();
        }
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
            onDialogueEnded(this.dialoguePlayer, this.currentScenario);
            PacketDistributor.sendToPlayer(this.dialoguePlayer, new DialoguePacket("",""));
            this.dialoguePlayer = null;
        }

        applyScenarioPose();
    }

    private void applyScenarioPose() {
        if (getScenario().startsWithoutAnim() && !this.isPlaying) {
            setStopAnim();
        } else {
            setAnimPlay();
        }
    }


    protected DialogueScenario getScenario(){
        return getScenarios().get(this.currentScenario);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CurrentScenario", this.currentScenario);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.currentScenario = Math.floorMod(tag.getInt("CurrentScenario"), getScenarios().size());
        if (!level().isClientSide){
            applyScenarioPose();
            resetDialogueState();
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

    public int getCurrentScenario() {
        return this.currentScenario;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_ANIM_STILL, true);
        builder.define(IS_ANIM_PLAY, false);
    }


    public boolean isAnimStill() {
        return this.entityData.get(IS_ANIM_STILL);
    }

    public boolean isAnimPlay() {
        return this.entityData.get(IS_ANIM_PLAY);
    }

    public void setAnimPlay() {
        this.entityData.set(IS_ANIM_PLAY, true);
        this.entityData.set(IS_ANIM_STILL, false);
    }

    public void setStopAnim() {
        this.entityData.set(IS_ANIM_PLAY, false);
        this.entityData.set(IS_ANIM_STILL, true);
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8, 1) {
        });
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
