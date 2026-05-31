package net.chakchak777.items;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.items.custom.Syringe;
import net.chakchak777.items.custom.Vodka;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChakchakMod.MODID);



    public static final DeferredItem<Item> CATOST_SPAWN_EGG = ITEMS.register("catost_spawn_egg",
            ()-> new DeferredSpawnEggItem(ModEntities.CATOST, 0x1cb018, 0xf4ea00,
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

    public static final DeferredItem<Vodka> VODKA = ITEMS.registerItem(
            "vodka",
            properties -> new Vodka(properties

                    .food(ModFoodProperties.VODKA).stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Чистая водка. Говорят, если выпить, то станет хорошим оружием ближнего боя")
                            .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<SwordItem> EMPTY_BOTTLE_OF_VODKA = ITEMS.register("empty_bottle_of_vodka",
            ()->new SwordItem(Tiers.WOOD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.WOOD, 4, 2))
            ){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

                    tooltipComponents.add(Component.literal("Ты рил выпил? Ну ок. У бутылки сильный урон кстати")
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





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

