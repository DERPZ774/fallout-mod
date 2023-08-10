package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;
import java.util.function.Supplier;

import static com.derpz.nukaisles.util.GunHelper.shootRay;

public class ShootingSyncS2CPacket {
    private final boolean isShooting;
    private final double range;
    private final float damage;

    public ShootingSyncS2CPacket(boolean isShooting, double range, float damage) {
        this.isShooting = isShooting;
        this.range = range;
        this.damage = damage;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public double getRange() {
        return range;
    }

    public float getDamage() {
        return damage;
    }

    public ShootingSyncS2CPacket(FriendlyByteBuf buf) {
        isShooting = buf.readBoolean();
        range = buf.readDouble();
        damage = buf.readFloat();
    }

    public static void encode(ShootingSyncS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.isShooting);
        buffer.writeDouble(packet.range);
        buffer.writeFloat(packet.damage);
    }

    public static ShootingSyncS2CPacket decode(FriendlyByteBuf buffer) {
        boolean isShooting = buffer.readBoolean();
        double range = buffer.readDouble();
        float damage = buffer.readFloat();
        return new ShootingSyncS2CPacket(isShooting, range, damage);
    }

    public static class Handler {


        public static void handle(ShootingSyncS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getSender() != null) {
                    Player player = context.getSender();
                    double range = packet.getRange();
                    float damage = packet.getDamage();
                    /// TODO: 8/10/2023 Fix Keybinding Shooting detection
                    ///ToDo: add ammo check
                        if (packet.isShooting) {
                            shootRay(player.getLevel(), player, range, damage);
                        }

                }
            });
            context.setPacketHandled(true);
        }
    }
}

