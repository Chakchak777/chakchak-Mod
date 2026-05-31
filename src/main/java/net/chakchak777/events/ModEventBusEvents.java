package net.chakchak777.events;


import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.custom.CatostEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


    @SubscribeEvent
    public static void registerAttributes (EntityAttributeCreationEvent event){
        event.put(ModEntities.CATOST.get(), CatostEntity.createAttributes().build());
    }
}
