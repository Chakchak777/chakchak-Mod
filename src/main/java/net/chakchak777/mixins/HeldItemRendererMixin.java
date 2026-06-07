package net.chakchak777.mixins;


import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stops the vanilla drop down aniamtion when firing
 */
@Mixin(value = ItemInHandRenderer.class)
public class HeldItemRendererMixin {
	@Mutable
	@Shadow
	@Final
	private final Minecraft minecraft;
	@Shadow
	private float mainHandHeight;
	@Shadow
	private float offHandHeight;
	@Shadow
	private ItemStack mainHandItem;
	@Shadow
	private ItemStack offHandItem;

	protected HeldItemRendererMixin(Minecraft client) {
		this.minecraft = client;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void azexamples$cancelAnimation(CallbackInfo ci) {
		final var clientPlayerEntity = minecraft.player;
		assert clientPlayerEntity != null;
		final var itemStack = clientPlayerEntity.getMainHandItem();
		final var itemStack2 = clientPlayerEntity.getOffhandItem();
		if (mainHandItem.getItem() instanceof PhysicBookItem && ItemStack.isSameItem(mainHandItem, itemStack)) {
			mainHandHeight = 1;
			mainHandItem = itemStack;
		}
		if (offHandItem.getItem() instanceof PhysicBookItem && ItemStack.isSameItem(offHandItem, itemStack2)) {
			offHandHeight = 1;
			offHandItem = itemStack2;
		}
	}
}
