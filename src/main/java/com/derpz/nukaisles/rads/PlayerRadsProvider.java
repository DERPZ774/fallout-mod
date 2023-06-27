package com.derpz.nukaisles.rads;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class PlayerRadsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<PlayerRads> PLAYER_RADS = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerRads rads = null;
    private final LazyOptional<PlayerRads> optional = LazyOptional.of(this::createPlayerRads);

    private PlayerRads createPlayerRads() {
        if(this.rads == null) {
            this.rads = new PlayerRads();
        }

        return this.rads;
    }
        
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if(cap == PLAYER_RADS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            createPlayerRads().saveNBTData(nbt);
            return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
            createPlayerRads().loadNBTData(nbt);
    }
    
}
