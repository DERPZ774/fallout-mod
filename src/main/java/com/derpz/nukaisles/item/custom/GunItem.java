package com.derpz.nukaisles.item.custom;


import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.networking.packet.ShootingSyncS2CPacket;
import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.util.GunHelper;
import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GunItem extends Item {
    private int ammunitionCount;
    private int maxAmmunition;
    private final float damage;
    private final double range;
    private final SoundEvent sound;
    private final int fireRate;
    private boolean isAiming;
    private boolean isShooting;
    public GunItem(Properties properties, float damage, double range, int fireRate, SoundEvent sound) {
        super(properties);
//        this.maxAmmunition = maxAmmunition;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.sound = sound;
        /// TODO: 6/29/2023 etc is taken in via ItemRegistration Properties :Recoil, Accuracy, Spread Capacity,
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public boolean isAiming() {
        return isAiming;
    }
    public boolean isShooting() {return isShooting;}

    public void setAiming(boolean aiming) {
        this.isAiming = aiming;
    }
   @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
       if (pEntity.isCrouching() && pEntity instanceof Player player && !player.getCooldowns().isOnCooldown(this) && ((Player) pEntity).getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem && player.level.isClientSide) {
           // Send the shooting packet to the server when starting to hold left-click
           ModMessages.sendToServer(new ShootingSyncS2CPacket(true, range, damage));
           //pLevel.playSound(player, player.blockPosition(), sound, SoundSource.PLAYERS, 1, 1);
       }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
