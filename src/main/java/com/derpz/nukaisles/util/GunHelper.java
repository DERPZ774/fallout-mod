package com.derpz.nukaisles.util;

import com.derpz.nukaisles.entity.ModEntityTypes;
import com.derpz.nukaisles.entity.custom.BulletEntity;
import com.derpz.nukaisles.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GunHelper {

/*    public static void shoot(net.minecraft.world.level.Level pLevel, LivingEntity shooter, double range) {
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

            // Normalize the direction vector
            Vec3 normalizedDir = shooterDir.normalize();

            // Calculate the end position based on the range
            Vec3 endPos = shooterPos.add(normalizedDir.scale(range));

            // Iterate through each block along the ray and check for entity collision
            for (double step = 0.0; step < range; step += 0.1) {
                double posX = x + normalizedDir.x * step;
                double posY = y + normalizedDir.y * step;
                double posZ = z + normalizedDir.z * step;

                BlockPos blockPos = new BlockPos((int) posX, (int) posY, (int) posZ);
                BlockState blockState = pLevel.getBlockState(blockPos);

                if (blockState.getMaterial().isSolid()) {
                    break; // Stop the ray if a solid block is encountered
                }

                // Perform raycasting to check for collision with entities
                List<Entity> entities = pLevel.getEntities(player, new AABB(posX, posY, posZ, posX, posY, posZ), entity -> entity instanceof LivingEntity);

                if (!entities.isEmpty()) {
                    Entity targetEntity = entities.get(0); // Target the first entity hit

                    // Handle the hit entity
                    if (targetEntity instanceof LivingEntity livingEntity) {
                        // Example: Spawn particles at the impact position
                        double impactX = targetEntity.getX();
                        double impactY = targetEntity.getY();
                        double impactZ = targetEntity.getZ();
                        pLevel.addParticle(ModParticles.RADIATION_PARTICLES.get(), impactX, impactY, impactZ, 0, 0, 0);
                        System.out.println("Entity shot");

                        // Example: Play impact sound
                        //pLevel.playSound(null, new BlockPos(impactX, impactY, impactZ), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);

                        // Example: Damage the entity
                        float damageAmount = 5.0f; // Adjust the damage amount as needed

                        livingEntity.hurt(livingEntity.m_269291_().m_268989_(), damageAmount);
                    }

                    break; // Stop the ray after hitting the first entity
                }
            }
        }
    }*/

    public static void shootRay(net.minecraft.world.level.Level pLevel, Entity shooter, double range, float damage) {
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

            // Normalize the direction vector
            Vec3 normalizedDir = shooterDir.normalize();

            // Calculate the end position based on the range
            Vec3 endPos = shooterPos.add(normalizedDir.scale(range));

            // Iterate through each block along the ray and check for entity collision
            for (double step = 0.0; step < range; step += 0.1) {
                double posX = x + normalizedDir.x * step;
                double posY = y + normalizedDir.y * step;
                double posZ = z + normalizedDir.z * step;

                BlockPos blockPos = new BlockPos((int) posX, (int) posY, (int) posZ);
                BlockState blockState = pLevel.getBlockState(blockPos);

                if (blockState.getMaterial().isSolid()) {
                    break; // Stop the ray if a solid block is encountered
                }

                // Perform raycasting to check for collision with entities
                List<Entity> entities = pLevel.getEntities(player, new AABB(posX, posY, posZ, posX, posY, posZ), entity -> entity instanceof LivingEntity);

                if (!entities.isEmpty()) {
                    Entity targetEntity = entities.get(0); // Target the first entity hit

                    // Handle the hit entity
                    if (targetEntity instanceof LivingEntity livingEntity) {
                        // Example: Spawn particles at the impact position
                        double impactX = targetEntity.getX();
                        double impactY = targetEntity.getY();
                        double impactZ = targetEntity.getZ();
                        pLevel.addParticle(ModParticles.RADIATION_PARTICLES.get(), impactX, impactY, impactZ, 0, 0, 0);
                        System.out.println("Entity shot");

                        // Example: Play impact sound
                        //pLevel.playSound(null, new BlockPos(impactX, impactY, impactZ), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);

                        // Example: Damage the entity
                        livingEntity.hurt(livingEntity.m_269291_().m_268989_(), damage);
                    }

                    break; // Stop the ray after hitting the first entity
                }
            }
        }
    }




       public static void shootEntity(net.minecraft.world.level.Level pLevel, Entity shooter) {
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
    }

}
