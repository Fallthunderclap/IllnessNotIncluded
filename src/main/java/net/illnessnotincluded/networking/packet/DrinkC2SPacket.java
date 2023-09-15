package net.illnessnotincluded.networking.packet;

import net.illnessnotincluded.networking.ModNetworking;
import net.illnessnotincluded.thirst.PlayerThirstProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrinkC2SPacket {
    public DrinkC2SPacket() {

    }

    public DrinkC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel().getLevel();

            if (hasWaterAround(player, level)) {
                level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    thirst.addThirst(3F);
                    ModNetworking.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                });
            }
        });
        return true;
    }

    private boolean hasWaterAround(ServerPlayer player, ServerLevel level) {
        return player.isInWaterOrRain() || level.getBlockStates(player.getBoundingBox().inflate(1)).anyMatch(blockState -> blockState.is(Blocks.WATER) || blockState.is(Blocks.WATER_CAULDRON));
    }
}
