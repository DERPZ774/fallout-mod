package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.client.ClientRadData;
import com.derpz.nukaisles.effect.ModEffects;
import com.derpz.nukaisles.rads.PlayerRadsProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RadsDataSyncS2CPacket {
    private final int rads;

    public RadsDataSyncS2CPacket(int rads) {
        this.rads = rads;
    }

    public RadsDataSyncS2CPacket(FriendlyByteBuf buf){
        this.rads = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(rads);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientRadData.set(rads);
        });
        return true;
    }
}
