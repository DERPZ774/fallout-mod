package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.util.handler.AimingHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AimingSyncS2CPacket {
    private final boolean isAiming;
    public AimingSyncS2CPacket(boolean isAiming) {
        this.isAiming = isAiming;
    }

    public boolean isAiming() {
        return isAiming;
    }

    public AimingSyncS2CPacket(FriendlyByteBuf buf) {
        isAiming = buf.readBoolean();
    }

    public static void encode(AimingSyncS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.isAiming);
    }

    public static AimingSyncS2CPacket decode(FriendlyByteBuf buffer) {
        boolean isAiming = buffer.readBoolean();
        return new AimingSyncS2CPacket(isAiming);
    }

    public static class Handler {
        public static void handle(AimingSyncS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                // Handle the packet data here
                boolean isAiming = packet.isAiming;
                // Update the aiming state or perform any necessary actions
            });
            context.setPacketHandled(true);
        }
    }
}
