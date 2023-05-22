package com.derpz.nukaisl.networking;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.networking.packet.RadC2SPacket;
import com.derpz.nukaisl.networking.packet.RadsDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    public static int id() {
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FalloutMod.MOD_ID, "main_channel"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(RadC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
            .decoder(RadC2SPacket::new)
            .encoder(RadC2SPacket::toBytes)
            .consumerMainThread(RadC2SPacket::handle)
            .add();

        net.messageBuilder(RadsDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RadsDataSyncS2CPacket::new)
                .encoder(RadsDataSyncS2CPacket::toBytes)
                .consumerMainThread(RadsDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
