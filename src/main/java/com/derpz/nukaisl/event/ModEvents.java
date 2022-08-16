package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.command.DevCommand;
import com.derpz.nukaisl.effect.ModEffects;
import com.derpz.nukaisl.rads.PlayerRads;
import com.derpz.nukaisl.rads.PlayerRadsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new DevCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if(!event.getOriginal().getLevel().isClientSide()) {
            event.getPlayer().getPersistentData().putIntArray(FalloutMod.MOD_ID + "dev",
                    event.getOriginal().getPersistentData().getIntArray(FalloutMod.MOD_ID + "dev"));
        }
    }

    @SubscribeEvent
    public static void setEntityOnFireWhenHit(LivingDamageEvent event) {
        //example event code
        if(!event.getEntity().level.isClientSide()) {
            if(event.getSource().getDirectEntity() instanceof Player player) {
                if(player.getMainHandItem().getItem() == Items.NETHER_BRICK) {
                    player.getMainHandItem().shrink(1);
                    event.getEntityLiving().setSecondsOnFire(2);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event) {
        if(event.getObject() instanceof Player) {
            if(!((ICapabilityProvider) event.getObject()).getCapability(PlayerRadsProvider.PLAYER_RADS).isPresent()) {
                event.addCapability(new ResourceLocation(FalloutMod.MOD_ID, "properties"), new PlayerRadsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerRads.class);
    }

    @SubscribeEvent
    public static void onTick(PlayerTickEvent event) {
        if(event.player.isAlive()) {
            MobEffectInstance effect = event.player.getEffect(ModEffects.RAD.get());
            if(effect != null) {
                if(event.player.getHealth() > 20 - 2 - (effect.getAmplifier() * 2)) {
                    event.player.setHealth(event.player.getHealth() - 1);
                }
            }
        }
    }
}
/// TODO: 8/13/2022 REMOVE setEntityOnFireWhenHit method