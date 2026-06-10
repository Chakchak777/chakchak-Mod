package net.chakchak777.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.chakchak777.items.custom.bubbleGun.BubbleGunItem;
import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables the bobView when holding the gun
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(
        method = "renderItemInHand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
            shift = At.Shift.BEFORE
        )
    )
    private void azexamples$bobViewGun(Camera camera, float partialTick, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (
            this.minecraft.player.getUseItem().getItem() instanceof PhysicBookItem && !this.minecraft.player
                .isSprinting()
        ) {
            azexamples$gunBobView(projectionMatrix, partialTick);
        }
    }

    @WrapWithCondition(
        method = "renderItemInHand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"
        )
    )
    private boolean azexamples$vanillaBobView(GameRenderer instance, PoseStack matrices, float tickDelta) {
        return !(this.minecraft.player.getUseItem().getItem() instanceof PhysicBookItem  );
    }

    @Unique
    private void azexamples$gunBobView(Matrix4f projectionMatrix, float tickDelta) {
        if (!(this.minecraft.gameRenderer.getMainCamera().getEntity() instanceof Player playerEntity)) {
            return;
        }
        var f = (playerEntity.walkDist - playerEntity.walkDistO);
        var g = -(playerEntity.walkDist + f * tickDelta);
        var h = Mth.lerp(tickDelta, playerEntity.oBob, playerEntity.bob) * 0.25f;

        projectionMatrix.translate(
            Mth.sin(g * (float) Math.PI) * h * 0.5f,
            -Math.abs(Mth.cos(g * (float) Math.PI) * h),
            0
        );

        projectionMatrix.rotate(Axis.ZP.rotationDegrees(Mth.sin(g * (float) Math.PI) * h * 3.0f));
        projectionMatrix.rotate(Axis.XP.rotationDegrees(Math.abs(Mth.cos(g * (float) Math.PI - 0.2f) * h) * 5.0f));
    }
}
