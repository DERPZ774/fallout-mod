package com.derpz.nukaisles.networking.packet;

import java.util.function.Supplier;
import com.derpz.nukaisles.effect.ModEffects;
import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.rads.PlayerRadsProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

public class RadC2SPacket {

    public RadC2SPacket() {

    }

    public RadC2SPacket(FriendlyByteBuf buf){

    }
    
    public void toBytes(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            assert player != null;
            player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(rads -> {
                rads.addRads(20); //increase rad level
                ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(rads.getRads()), player);
                //send rad count increase message to player
                int radmath = Math.min(rads.getRads(), 900);
                int amp = (int)Math.floor(radmath / 100.0);
                player.addEffect(new MobEffectInstance(ModEffects.RAD.get(), 9999999, amp, false, false));

            });
        });
    }
}
