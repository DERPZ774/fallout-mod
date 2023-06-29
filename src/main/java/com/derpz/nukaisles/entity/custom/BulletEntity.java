package com.derpz.nukaisles.entity.custom;

import com.derpz.nukaisles.item.ModItems;
import com.derpz.nukaisles.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.random.RandomGenerator;

public class BulletEntity extends AbstractArrow {
    private float damage;
    private int maxRange;
    private int lifeTicks;
    private float yRot;
    private float xRot;

    public BulletEntity(EntityType<? extends AbstractArrow> arrowEntityType, Level level) {
        super(arrowEntityType, level);
    }

    public BulletEntity(EntityType<? extends BulletEntity> bulletEntityType, double x, double y, double z, Level level) {
        super(bulletEntityType, x, y, z, level);
    }


    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }
    @Override
    public void tick() {
        // Handle any logic needed on each tick/update of the bullet entity
        // For example, check for collisions, apply damage, or spawn particles

        super.tick();

       /* if (lifeTicks >= maxRange) {
            remove(RemovalReason.DISCARDED); // Remove the bullet entity after reaching the maximum range
        }

        lifeTicks++;*/
///ToDO: Bullet deletion logic

    }


    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        DamageSource damageSource;
        damageSource = this.m_269291_().m_269418_(this, this);
        // Apply damage or perform other actions upon hitting an entity
        pResult.getEntity().hurt(Objects.requireNonNull(damageSource.getEntity()).m_269291_().m_268989_(), damage);
       // remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        remove(RemovalReason.DISCARDED);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ModItems.BOTTLE_CAP.get());
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.LAMP_USE.get();
    }
}
