package com.derpz.nukaisles.networking;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.networking.packet.*;
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

        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(AimingSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AimingSyncS2CPacket::decode)
                .encoder(AimingSyncS2CPacket::encode)
                .consumerMainThread(AimingSyncS2CPacket.Handler::handle)
                .add();

        net.messageBuilder(ShootingSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ShootingSyncS2CPacket::decode)
                .encoder(ShootingSyncS2CPacket::encode)
                .consumerMainThread(ShootingSyncS2CPacket.Handler::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
