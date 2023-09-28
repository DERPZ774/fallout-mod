package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.item.custom.GunItem;
import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public record ShootingSyncS2CPacket(List<Vector3d> particlePositions) {

    public static void encode(ShootingSyncS2CPacket packet, FriendlyByteBuf buffer) {
        int numParticles = packet.particlePositions.size();
        buffer.writeInt(numParticles);
        for (Vector3d pos : packet.particlePositions) {
            buffer.writeDouble(pos.x);
            buffer.writeDouble(pos.y);
            buffer.writeDouble(pos.z);
        }
    }

    public static ShootingSyncS2CPacket decode(FriendlyByteBuf buffer) {
        int numParticles = buffer.readInt();
        List<Vector3d> particlePositions = new ArrayList<>();
        for (int i = 0; i < numParticles; i++) {
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            particlePositions.add(new Vector3d(x, y, z));
        }
        return new ShootingSyncS2CPacket(particlePositions);
    }

    public static class Handler {
        public static void handle(ShootingSyncS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                Minecraft minecraft = Minecraft.getInstance();
                Player player = minecraft.player;
                assert minecraft.level != null;
                assert player != null;
                // Spawn particles on the client based on the packet data
                List<Vector3d> particlePositions = packet.particlePositions();
                for (Vector3d pos : particlePositions) {
                    minecraft.level.addParticle(ModParticles.RADIATION_PARTICLES.get(), pos.x, pos.y, pos.z, 0, 0, 0);
                }
                minecraft.level.playSound(player, player.getX(), player.getY(), player.getZ(), GunItem.sound, SoundSource.PLAYERS, 0.8f, 1.0f);
            });
            context.setPacketHandled(true);
        }
    }
}
