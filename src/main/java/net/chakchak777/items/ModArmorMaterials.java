package net.chakchak777.items;

import net.chakchak777.ChakchakMod;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, ChakchakMod.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PHYSIC =
            ARMOR_MATERIALS.register("physic", () -> new ArmorMaterial(

                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 4);
                        map.put(ArmorItem.Type.HELMET, 2);
                        map.put(ArmorItem.Type.BODY, 11);
                    }),
                    15,
                    SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(Items.DIAMOND),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "physic"))),
                    2.0F,
                    0.0F
            ));

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
