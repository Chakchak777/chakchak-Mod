package net.chakchak777.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public HitResult hitResult;

    @Shadow
    public LocalPlayer player;

    @Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
    private void azexamples$handleBlockBreaking(boolean b, CallbackInfo ci) {
        if (player.getUseItem().getItem() instanceof PhysicBookItem) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(
        method = "startUseItem", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/InteractionResult;shouldSwing()Z"
        )
    )
    private boolean azexamples$dontSwingGun(boolean original) {
        return original && !(this.player.getUseItem().getItem() instanceof PhysicBookItem);
    }

    @Inject(
        method = "startAttack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"
        ), cancellable = true
    )
    private void azexamples$onStartAttack(CallbackInfoReturnable<Boolean> cir) {
        if (hitResult == null || player == null) {
            return;
        }
        var mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof PhysicBookItem) {
            cir.setReturnValue(false);
        }
    }
}
