package com.derpz.dfm.event;

import javax.swing.text.html.parser.Entity;
import org.lwjgl.system.CallbackI.P;
import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.rads.PlayerRads;
import com.derpz.dfm.rads.PlayerRadsProvider;
import com.mojang.authlib.yggdrasil.request.TelemetryEventsRequest.Event;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event) {// TODO: optimize this "AttachCapabilitiesEvent<Entity> event"
        if(event.getObject() instanceof Player) {
            if(!((ICapabilityProvider) event.getObject()).getCapability(PlayerRadsProvider.PLAYER_RADS).isPresent()) {
                event.addCapability(new ResourceLocation(FalloutMod.MOD_ID, "properties"), new PlayerRadsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerRads.class);
    }

    
}
