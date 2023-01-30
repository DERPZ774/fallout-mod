package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.command.DevCommand;
import com.derpz.nukaisl.effect.ModEffects;
import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.item.custom.ModArmorItem;
import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.RadsDataSyncS2CPacket;
import com.derpz.nukaisl.rads.PlayerRads;
import com.derpz.nukaisl.rads.PlayerRadsProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.List;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID)
public class ModEvents {

   /* @SubscribeEvent

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
*/
    /*
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

 */

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
            /*if (event.side == LogicalSide.SERVER) {
                event.player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(playerRads -> {
                    if (playerRads.getRads() > 0 && event.player.getRandom().nextFloat() < 0.005F) {
                        playerRads.subRads(1);
                        ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(playerRads.getRads()), (ServerPlayer) event.player);
                    }
                });
            }

             */
            /*if(event.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CUMMIE_WHACKER.get())) {
                event.player.getItemInHand(InteractionHand.MAIN_HAND).setCount(0);
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("no no no"));
                if(event.player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.CUMMIE_WHACKER.get())) {
                    event.player.getItemInHand(InteractionHand.OFF_HAND).setCount(0);
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("no no no"));
                }
            }

             */
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof ServerPlayer player && event.getEntity().getPersistentData().isEmpty()) {
            player.getPersistentData().putByte("PLAYER TAGGED", (byte) 1);
            player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.NUKA_COLA.get()));
        }
        if (!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(playerRads -> {
                    ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(playerRads.getRads()), player);
                });
            }
        }
    }
    @SubscribeEvent
    public static void customTrades(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        int villagerLevel = 1;

        if (event.getType() == VillagerProfession.ARMORER || event.getType() == VillagerProfession.BUTCHER ||
                event.getType() == VillagerProfession.CARTOGRAPHER || event.getType() == VillagerProfession.CLERIC ||
                event.getType() == VillagerProfession.LEATHERWORKER || event.getType() == VillagerProfession.FARMER ||
                event.getType() == VillagerProfession.FARMER || event.getType() == VillagerProfession.FLETCHER ||
                event.getType() == VillagerProfession.LIBRARIAN || event.getType() == VillagerProfession.MASON ||
                event.getType() == VillagerProfession.NITWIT || event.getType() == VillagerProfession.SHEPHERD ||
                event.getType() == VillagerProfession.TOOLSMITH || event.getType() == VillagerProfession.WEAPONSMITH) {

            ItemStack stack = new ItemStack(ModItems.NUKA_COLA.get(), 1);


            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.BOTTLE_CAP.get(), 2),
                    stack, 10, 8, 0.02F));
        }

        if(event.getType() == VillagerProfession.ARMORER || event.getType() == VillagerProfession.WEAPONSMITH ||
                event.getType() == VillagerProfession.TOOLSMITH) {
            ItemStack stack = new ItemStack(ModItems.SCRAP_METAL.get(), 1);

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.BOTTLE_CAP.get(), 2),
                    stack, 10, 8, 0.02F));

        }

        /// ToDo : Balance prices && add diff level rewards and prices using a formula
    }

}