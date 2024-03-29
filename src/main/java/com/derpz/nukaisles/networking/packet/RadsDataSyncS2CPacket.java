package com.derpz.nukaisles.networking.packet;

import com.derpz.nukaisles.client.ClientRadData;
import net.minecraft.network.FriendlyByteBuf;
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientRadData.set(rads));
    }
}
