package com.derpz.nukaisles.event;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.effect.ModEffects;
import com.derpz.nukaisles.item.ModItems;
import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.networking.packet.RadsDataSyncS2CPacket;
import com.derpz.nukaisles.rads.PlayerRads;
import com.derpz.nukaisles.rads.PlayerRadsProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(playerRads -> ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(playerRads.getRads()), player));
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
                event.getType() == VillagerProfession.SHEPHERD ||
                event.getType() == VillagerProfession.TOOLSMITH || event.getType() == VillagerProfession.WEAPONSMITH) {

            ItemStack cola = new ItemStack(ModItems.NUKA_COLA.get(), 1);
            ItemStack cap = new ItemStack(ModItems.BOTTLE_CAP.get(), 3);


            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.BOTTLE_CAP.get(), 2),
                    cola, 10, 8, 0.02F));

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    cap, 10, 8, 0.02F));
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