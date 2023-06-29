package com.derpz.nukaisles.item.custom;


import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GunItem extends Item {
    private int ammunitionCount;
    private int maxAmmunition;
    private int damage;

    public GunItem(Properties properties) {
        super(properties);
//        this.maxAmmunition = maxAmmunition;
//        this.damage = damage;
        /// TODO: 6/29/2023 Add Properties so dmg, ammo, etc is taken in via ItemRegistration 
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(net.minecraft.world.level.Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        ItemStack arrow = Items.ARROW.getDefaultInstance();
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.TEN_MM_SHOOT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        if (!pLevel.isClientSide) {
            shoot(pLevel, pPlayer);
            pPlayer.getInventory().removeItem(Items.ARROW.getDefaultInstance());
            consumeAmmunition(arrow);
            pLevel.playSound(pPlayer, pPlayer.blockPosition(), ModSounds.TEN_MM_SHOOT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }

/*    private void shoot(net.minecraft.world.level.Level pLevel, LivingEntity shooter) {
        if (shooter instanceof Player player) {
            // Calculate the bullet's initial position based on the shooter's eye position
            double x = player.getX();
            double y = player.getEyeY() - 0.1;
            double z = player.getZ();

            // Calculate the direction vector based on the player's rotation angles
            float yaw = player.getYRot();
            float pitch = player.getXRot();

            // Convert the rotation angles to radians
            float yawRadians = yaw * ((float) Math.PI / 180f);
            float pitchRadians = pitch * ((float) Math.PI / 180f);

            // Calculate the motion vector based on the direction and desired velocity
            double velocity = 3.0; // Adjust the velocity as needed
            double motionX = -Math.sin(yawRadians) * Math.cos(pitchRadians) * velocity;
            double motionY = -Math.sin(pitchRadians) * velocity;
            double motionZ = Math.cos(yawRadians) * Math.cos(pitchRadians) * velocity;

            // Create the bullet entity
            BulletEntity bullet = new BulletEntity(ModEntityTypes.BULLET.get(), x, y, z, pLevel);
            bullet.setDamage(1000); // Set the damage of the bullet

            // Set the motion vector of the bullet entity
            bullet.setDeltaMovement(motionX, motionY, motionZ);

            // Set the no gravity flag to prevent drop-off
            bullet.setNoGravity(true);

            // Spawn the bullet entity in the world
            pLevel.addFreshEntity(bullet);

            // Example: Spawn particles
            pLevel.addParticle(ModParticles.RADIATION_PARTICLES.get(), x, y, z, 0, 0, 0);
        }
    }*/

    private void shoot(net.minecraft.world.level.Level pLevel, LivingEntity shooter) {
        if (shooter instanceof Player player) {
            // Calculate the bullet's initial position based on the shooter's eye position
            double x = player.getX();
            double y = player.getEyeY() - 0.1;
            double z = player.getZ();

            // Calculate the direction vector based on the player's rotation angles
            float yaw = player.getYRot();
            float pitch = player.getXRot();

            // Convert the rotation angles to radians
            float yawRadians = yaw * ((float) Math.PI / 180f);
            float pitchRadians = pitch * ((float) Math.PI / 180f);

            // Calculate the motion vector based on the direction and desired velocity
            double velocity = 3.0; // Adjust the velocity as needed
            double motionX = -Math.sin(yawRadians) * Math.cos(pitchRadians) * velocity;
            double motionY = -Math.sin(pitchRadians) * velocity;
            double motionZ = Math.cos(yawRadians) * Math.cos(pitchRadians) * velocity;

            // Create a ray from the shooter's position and direction
            Vec3 shooterPos = new Vec3(x, y, z);
            Vec3 shooterDir = new Vec3(motionX, motionY, motionZ);
            Vec3 endPos = shooterPos.add(shooterDir.normalize().scale(100)); // Adjust the maximum range as needed

            // Perform raycasting to check for collision with entities
            List<Entity> entities = pLevel.getEntities(player, shooter.getBoundingBox().expandTowards(shooterDir), entity -> entity instanceof LivingEntity);

            for (Entity entity : entities) {
                // Handle each hit entity
                if (entity instanceof LivingEntity livingEntity) {
                    // Example: Spawn particles at the impact position
                    double impactX = entity.getX();
                    double impactY = entity.getY();
                    double impactZ = entity.getZ();
                    pLevel.addParticle(ModParticles.RADIATION_PARTICLES.get(), impactX, impactY, impactZ, 0, 0, 0);
                    System.out.println("Entity shot");

                    // Example: Play impact sound
                    //pLevel.playSound(null, new BlockPos(impactX, impactY, impactZ), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);

                    // Example: Damage the entity
                    float damageAmount = 5.0f; // Adjust the damage amount as needed

                    livingEntity.hurt(entity.m_269291_().m_268989_(), damageAmount);
                    /// TODO: 6/29/2023 Fix range 
                }
            }
        }
    }





    private boolean hasAmmunition(ItemStack itemStack) {
        // Example: Check if the itemStack is an arrow
        return itemStack.getItem() == Items.ARROW;
    }

    private void consumeAmmunition(ItemStack itemStack) {
        if (hasAmmunition(itemStack)) {
            itemStack.shrink(1);
        }
    }

}
