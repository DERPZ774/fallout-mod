package com.derpz.nukaisles.item.custom;


import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.networking.packet.ShootingSyncC2SPacket;
import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

public class GunItem extends Item {
    public static SoundEvent sound;
    private int ammunitionCount;
    private int maxAmmunition;
    private static float damage;
    private static double range;

    private final int fireRate;

    public GunItem(Properties properties, float damage, double range, int fireRate, SoundEvent sound) {
        super(properties);
//        this.maxAmmunition = maxAmmunition;
        GunItem.damage = damage;
        GunItem.range = range;
        this.fireRate = fireRate;
        GunItem.sound = sound;
        /// TODO: 6/29/2023 etc is taken in via ItemRegistration Properties :Recoil, Accuracy, Spread Capacity,
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT)
    public static class ShootingEvent {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.InteractionKeyMappingTriggered event) {
            if (event.isCanceled())
                return;

            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if(player == null)
                return;

            KeyMapping key = KeyBinding.SHOOTING_KEY;

            if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem && event.getKeyMapping().isActiveAndMatches(key.getKey())) {
                ModMessages.sendToServer(new ShootingSyncC2SPacket(true, GunItem.range, GunItem.damage));
            }
            else {
                ModMessages.sendToServer(new ShootingSyncC2SPacket(false, GunItem.range, GunItem.damage));
            }
        }

        @SubscribeEvent
        public static void onOtherKey(InputEvent.Key event) {
            if (event.isCanceled())
                return;

            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if(player == null)
                return;

            if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem && KeyBinding.SHOOTING_KEY.consumeClick()) {
                ModMessages.sendToServer(new ShootingSyncC2SPacket(true, GunItem.range, GunItem.damage));
            }
            else {
                ModMessages.sendToServer(new ShootingSyncC2SPacket(false, GunItem.range, GunItem.damage));
            }
        }
    }
}
