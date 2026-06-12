package net.chakchak777.items;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.items.blockItems.BubbleAmmoBlockItem;
import net.chakchak777.items.custom.bubbleGun.BubbleGunItem;
import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.chakchak777.items.custom.Syringe;
import net.chakchak777.items.custom.bat.BatItem;
import net.chakchak777.items.custom.whistle.WhistleItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChakchakMod.MODID);



    public static final DeferredItem<Item> CATOST_SPAWN_EGG = ITEMS.register("catost_spawn_egg",
            ()-> new DeferredSpawnEggItem(ModEntities.CATOST, 0x1cb018, 0xf4ea00,
                    new Item.Properties()));

    public static final DeferredItem<Item> RAT_SPAWN_EGG = ITEMS.register("rat_spawn_egg",
            ()-> new DeferredSpawnEggItem(ModEntities.RAT, 0xFF808080, 0xFFF9E02E,
                    new Item.Properties()));

    public static final DeferredItem<Item> CATOST_FIGURINE = ITEMS.register("catost_figurine",
            ()-> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Коллекционная фигурка котости")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CAT_FOOD = ITEMS.register("cat_food",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CAT_FOOD)) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Вкусный корм для котиков. Мяу! У людей вызывает рвоту и проблемы в будущем")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });




    public static final DeferredItem<Syringe> SYRINGE = ITEMS.registerItem(
            "syringe",
            properties -> new Syringe(properties.stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Шприц, со странным веществом внутри, лучше не использовать")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> MANGO = ITEMS.register("mango",
            () -> new Item(new Item.Properties().food(ModFoodProperties.MANGO).rarity(Rarity.RARE).stacksTo(16)) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Манго, манго, манго тутутурутутуруру")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<BatItem> BAT_ITEM = ITEMS.registerItem(
            "bat",
            properties -> new BatItem(Tiers.IRON, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Бита йоу, типо круто")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<PhysicBookItem> PHYSIC_BOOK = ITEMS.register("physic_book",
            ()-> new PhysicBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Учебник физики за восьмой класс," +
                                    " можно читать зажав шифт и кликнув пкм")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final DeferredItem<BubbleGunItem> BUBBLE_GUN = ITEMS.register("bubble_gun",
            ()-> new BubbleGunItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 100)
                );

    public static final DeferredItem<WhistleItem> WHISTLE = ITEMS.register("whistle",
            ()-> new WhistleItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))
    );

    public static final DeferredItem<ArmorItem> PHYSIC_GLASSES = ITEMS.register("physic_glasses",
            ()->new ArmorItem(ModArmorMaterials.PHYSIC, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)
                    .stacksTo(1)));









    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

