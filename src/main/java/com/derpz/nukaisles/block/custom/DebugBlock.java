package com.derpz.nukaisles.block.custom;

import net.minecraft.world.level.block.Block;

public class DebugBlock extends Block {
    public DebugBlock(Properties properties) {
        super(properties);
    }
/*
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        float chance = 0.35f;

        if(chance < pRandom.nextFloat()) {
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + 0.5D, pPos.getZ() + pRandom.nextDouble(),
                    0d, 0.015d + pRandom.nextDouble(), 0d);
        }

        if(chance < pRandom.nextFloat()) {
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + 0.5D, pPos.getZ() + pRandom.nextDouble(),
                    0d, 0.015d + pRandom.nextDouble(), 0d);
        }

        if(chance < pRandom.nextFloat()) {
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + 0.5D, pPos.getZ() + pRandom.nextDouble(),
                    0d, 0.015d + pRandom.nextDouble(), 0d);
        }

        if(chance < pRandom.nextFloat()) {
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + 0.5D, pPos.getZ() + pRandom.nextDouble(),
                    0d, 0.015d + pRandom.nextDouble(), 0d);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide()) {
            if(pEntity instanceof LivingEntity) {
                LivingEntity entity = ((LivingEntity) pEntity);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
            }
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

 */
}
