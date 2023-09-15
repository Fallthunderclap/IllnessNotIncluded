package net.illnessnotincluded.networking;

import net.illnessnotincluded.IllnessNotIncluded;
import net.illnessnotincluded.networking.packet.DrinkC2SPacket;
import net.illnessnotincluded.networking.packet.ThirstDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {

        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(IllnessNotIncluded.MOD_ID, "networking"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(DrinkC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkC2SPacket::new)
                .encoder(DrinkC2SPacket::toBytes)
                .consumerMainThread(DrinkC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(ThirstDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ThirstDataSyncS2CPacket::new)
                .encoder(ThirstDataSyncS2CPacket::toBytes)
                .consumerMainThread(ThirstDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
