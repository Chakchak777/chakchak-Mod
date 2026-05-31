package net.chakchak777.events;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.custom.CatostEntity;
import net.chakchak777.quest.QuestAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ChakchakMod.MODID)
public class ModServerEvents {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            QuestAdvancements.unlock(player, "quest5");
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getType() != EntityType.ZOMBIE) {
            return;
        }

        ServerPlayer killer = getKillingPlayer(event.getSource());
        if (killer != null) {
            QuestAdvancements.complete(killer, "quest5");
        }
    }

    private static ServerPlayer getKillingPlayer(DamageSource source) {
        Entity directEntity = source.getEntity();
        if (directEntity instanceof ServerPlayer player) {
            return player;
        }
        Entity causingEntity = source.getDirectEntity();
        if (causingEntity instanceof ServerPlayer player) {
            return player;
        }
        return null;
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        List<CatostEntity> activeDialogues = new ArrayList<>();
        serverLevel.getEntities(ModEntities.CATOST.get(), catost -> catost.getDialoguePlayer() == player, activeDialogues);
        for (CatostEntity catost : activeDialogues) {
            catost.onDialoguePlayerDisconnect(player);
        }
    }
}
