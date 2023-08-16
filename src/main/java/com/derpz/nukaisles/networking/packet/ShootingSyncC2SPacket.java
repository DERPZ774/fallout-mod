package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.item.custom.GunItem;
import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.derpz.nukaisles.util.GunHelper.shootRay;

public class ShootingSyncC2SPacket {
    private final boolean isShooting;
    private final double range;
    private final float damage;

    public ShootingSyncC2SPacket(boolean isShooting, double range, float damage) {
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

    public ShootingSyncC2SPacket(FriendlyByteBuf buf) {
        isShooting = buf.readBoolean();
        range = buf.readDouble();
        damage = buf.readFloat();
    }

    public static void encode(ShootingSyncC2SPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.isShooting);
        buffer.writeDouble(packet.range);
        buffer.writeFloat(packet.damage);
    }

    public static ShootingSyncC2SPacket decode(FriendlyByteBuf buffer) {
        boolean isShooting = buffer.readBoolean();
        double range = buffer.readDouble();
        float damage = buffer.readFloat();
        return new ShootingSyncC2SPacket(isShooting, range, damage);
    }

    public static class Handler {

        public static void handle(ShootingSyncC2SPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getSender() != null) {
                    Player player = context.getSender();
                    double range = packet.getRange();
                    float damage = packet.getDamage();

                    // Only proceed if the player is shooting
                    if (packet.isShooting() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem) {
                        // Shoot the ray and send a client packet back to the player
                        shootRay(player.getLevel(), player, range, damage);

                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
