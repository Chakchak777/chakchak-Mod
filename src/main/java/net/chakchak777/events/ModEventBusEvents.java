package net.chakchak777.events;


import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.custom.CatostEntity;
import net.chakchak777.entities.custom.RatEntity;
import net.chakchak777.items.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


@EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


    @SubscribeEvent
    public static void registerAttributes (EntityAttributeCreationEvent event){
        event.put(ModEntities.CATOST.get(), CatostEntity.createAttributes().build());
        event.put(ModEntities.RAT.get(), RatEntity.createAttributes().build());

    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> AzIdentityRegistry.register(ModItems.PHYSIC_BOOK.get()));
        event.enqueueWork(() -> AzIdentityRegistry.register(ModItems.BUBBLE_GUN.get()));
    }
}
