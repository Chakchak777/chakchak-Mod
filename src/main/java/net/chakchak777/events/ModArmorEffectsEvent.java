package net.chakchak777.events;


import net.chakchak777.ChakchakMod;
import net.chakchak777.effects.ModEffects;
import net.chakchak777.items.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModArmorEffectsEvent {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide())return;



        if (hasPhysicHelmet(player)){

            player.addEffect(new MobEffectInstance(
                    ModEffects.PHYSICS_KNOWLEDGE_EFFECT, 60, 0, false, true, true));
        }



    }

  /*  private static boolean hasFullArmorSet (Player player){
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return !helmet.isEmpty() && helmet.is(ModItems.CUSTOM_HELMET.get()) &&
                !chest.isEmpty() && chest.is(ModItems.CUSTOM_CHESTPLATE.get()) &&
                !legs.isEmpty() && legs.is(ModItems.CUSTOM_LEGGINGS.get()) &&
                !boots.isEmpty() && boots.is(ModItems.CUSTOM_BOOTS.get());

   */

    private static boolean hasPhysicHelmet (Player player){
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        return !helmet.isEmpty() && helmet.is(ModItems.PHYSIC_GLASSES.get());
    }
}
