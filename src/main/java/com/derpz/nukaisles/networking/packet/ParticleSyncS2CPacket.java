package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleSyncS2CPacket {
    private final List<Vector3d> particlePositions;

    public ParticleSyncS2CPacket(List<Vector3d> particlePositions) {
        this.particlePositions = particlePositions;
    }

    public List<Vector3d> getParticlePositions() {
        return particlePositions;
    }

    public static void encode(ParticleSyncS2CPacket packet, FriendlyByteBuf buffer) {
        int numParticles = packet.particlePositions.size();
        buffer.writeInt(numParticles);
        for (Vector3d pos : packet.particlePositions) {
            buffer.writeDouble(pos.x);
            buffer.writeDouble(pos.y);
            buffer.writeDouble(pos.z);
        }
    }

    public static ParticleSyncS2CPacket decode(FriendlyByteBuf buffer) {
        int numParticles = buffer.readInt();
        List<Vector3d> particlePositions = new ArrayList<>();
        for (int i = 0; i < numParticles; i++) {
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            particlePositions.add(new Vector3d(x, y, z));
        }
        return new ParticleSyncS2CPacket(particlePositions);
    }

    public static class Handler {
        public static void handle(ParticleSyncS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                // Spawn particles on the client based on the packet data
                List<Vector3d> particlePositions = packet.getParticlePositions();
                for (Vector3d pos : particlePositions) {
                    assert Minecraft.getInstance().level != null;
                    Minecraft.getInstance().level.addParticle(ModParticles.RADIATION_PARTICLES.get(), pos.x, pos.y, pos.z, 0, 0, 0);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
