package net.chakchak777;

import com.mojang.logging.LogUtils;
import net.chakchak777.blocks.ModBlocks;

import net.chakchak777.dataComponent.ModDataComponents;
import net.chakchak777.effects.ModEffects;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.client.CatostRenderer;
import net.chakchak777.items.ModArmorMaterials;
import net.chakchak777.items.ModCreativeModTab;
import net.chakchak777.items.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ChakchakMod.MODID)
public class ChakchakMod {
    public static final String MODID = "chakchakmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChakchakMod(IEventBus modEventBus, ModContainer modContainer) {



        ModItems.register(modEventBus);

        ModCreativeModTab.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEntities.register(modEventBus);

        ModDataComponents.register(modEventBus);

        ModArmorMaterials.register(modEventBus);

        ModEffects.register(modEventBus);



    }




}