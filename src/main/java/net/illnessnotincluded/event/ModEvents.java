package net.illnessnotincluded.event;


import net.illnessnotincluded.IllnessNotIncluded;
import net.illnessnotincluded.networking.ModNetworking;
import net.illnessnotincluded.networking.packet.ThirstDataSyncS2CPacket;
import net.illnessnotincluded.thirst.PlayerThirst;
import net.illnessnotincluded.thirst.PlayerThirstProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IllnessNotIncluded.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(IllnessNotIncluded.MOD_ID, "properties"), new PlayerThirstProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                newStore.setThirst(PlayerThirst.MAX_THIRST);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                if (playerThirst.getThirst() > 0 && event.player.getRandom().nextFloat() < 0.005F) {
                    if (event.player.isSwimming()) playerThirst.subThirst(0.1F);
                    if (event.player.isSprinting()) playerThirst.subThirst(0.5F);
                    if (event.player.isOnFire()) playerThirst.subThirst(1F);

                    ModNetworking.sendToPlayer(new ThirstDataSyncS2CPacket(playerThirst.getThirst()), (ServerPlayer) event.player);

                } else if (playerThirst.getThirst() == 0 && event.player.getRandom().nextFloat() < 0.01F) {
                    event.player.hurt(event.player.damageSources().starve(), 1.0F);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    ModNetworking.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                });
            }
        }
    }
}
