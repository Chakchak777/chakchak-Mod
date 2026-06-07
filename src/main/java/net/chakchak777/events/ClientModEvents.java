package net.chakchak777.events;


import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.chakchak777.ChakchakMod;
import net.chakchak777.client.DialogueLineClientState;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.client.CatostRenderer;
import net.chakchak777.entities.client.FireballRenderer;
import net.chakchak777.items.ModItems;
import net.chakchak777.items.custom.physicBook.PhysicBookRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CATOST.get(), CatostRenderer::new);
        event.registerEntityRenderer(ModEntities.FIREBALL.get(), FireballRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

      //  AzItemRendererRegistry.register(BatRenderer::new, ModItems.BAT_ITEM.get());

        AzItemRendererRegistry.register(PhysicBookRenderer::new, ModItems.PHYSIC_BOOK.get());
    }

    @EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            DialogueLineClientState.clientTick();
        }
    }

}