package com.derpz.nukaisles.item.custom;


import com.derpz.nukaisles.util.GunHelper;
import net.minecraft.client.Minecraft;
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

public class GunItem extends Item {
    private int ammunitionCount;
    private int maxAmmunition;
    private final float damage;
    private final double range;
    private final SoundEvent sound;
    private final int fireRate;
    private boolean isAiming;
    public GunItem(Properties properties, float damage, double range, int fireRate, SoundEvent sound) {
        super(properties);
//        this.maxAmmunition = maxAmmunition;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.sound = sound;
        /// TODO: 6/29/2023 Add Properties so dmg, ammo, etc is taken in via ItemRegistration Properties :Recoil, Accuracy, Spread, Range, Capacity, Fire Rate, Damage
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        }


        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public boolean isAiming() {
        return isAiming;
    }

    public void setAiming(boolean aiming) {
        this.isAiming = aiming;
    }
   @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (Minecraft.getInstance().options.keyAttack.isDown() && pEntity instanceof Player player && !player.getCooldowns().isOnCooldown(this)) {
            if(!pLevel.isClientSide()) {
                player.getCooldowns().addCooldown(this, fireRate);
                GunHelper.shootRay(pLevel, pEntity, range, damage);
            }
            pLevel.playSound(player, player.blockPosition(), sound, SoundSource.PLAYERS, 1, 1);
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    /*    public @NotNull InteractionResultHolder<ItemStack> use(net.minecraft.world.level.Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        ItemStack arrow = Items.ARROW.getDefaultInstance();
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), sound, SoundSource.PLAYERS, 1.0f, 1.0f);
        if (!pLevel.isClientSide) {
            GunHelper.shootRay(pLevel, pPlayer, range, damage);
            pPlayer.getInventory().removeItem(Items.ARROW.getDefaultInstance());
            consumeAmmunition(arrow);
            return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }*/


}
