package com.derpz.nukaisl.block.entity;

import javax.annotation.Nullable;

import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.EnergySyncS2CPacket;
import com.derpz.nukaisl.recipe.NukaColaMachineRecipe;
import com.derpz.nukaisl.util.ModEnergyStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.screen.NukaColaMachineMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

public class NukaColaMachineBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    //ToDo Edit fuel numbers

    public NukaColaMachineBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.NUKA_COLA_MACHINE.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory pInventory, Player pPlayer) {
        return new NukaColaMachineMenu(id, pInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Nuka-Cola Machine");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pCompound) {
        pCompound.put("inventory", itemHandler.serializeNBT());
        pCompound.putInt("nuka_cola_machine.progress", this.progress);
        pCompound.putInt("nuka_cola_machine.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pCompound);
    }

    @Override
    public void load(@NotNull CompoundTag pCompound) {
        super.load(pCompound);
        itemHandler.deserializeNBT(pCompound.getCompound("inventory"));
        progress = serializeNBT().getInt("nuka_cola_machine.progress");
        ENERGY_STORAGE.setEnergy(pCompound.getInt("nuka_cola_machine.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);

    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, NukaColaMachineBlockEntity pEntity) {
        if (pLevel.isClientSide) {
            return;
        }

        if(hasEnergyInFirstSlot(pEntity)) {
            pEntity.ENERGY_STORAGE.receiveEnergy(64, false);
        }

        if (getRecipe(pEntity) != null && hasEnoughEnergy(pEntity)) {
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(pLevel, pPos, pState);

            if (pEntity.progress >= pEntity.maxProgress) {
                pEntity.resetProgress();
                craft(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static void extractEnergy(NukaColaMachineBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(NukaColaMachineBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private static boolean hasEnergyInFirstSlot(NukaColaMachineBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == Items.ICE;
        /// TODO: 5/24/2023 Change this method to accept more items for fuel
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craft(NukaColaMachineBlockEntity pEntity) {
        NukaColaMachineRecipe recipe = getRecipe(pEntity);
        if(recipe != null) {
            pEntity.itemHandler.extractItem(recipe.getSlot(), 1, false);

            pEntity.itemHandler.insertItem(recipe.getSlot(), recipe.getResultItem(), false);

            pEntity.resetProgress();
        }

    }

    private static NukaColaMachineRecipe getRecipe(NukaColaMachineBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(NukaColaMachineRecipe.Type.INSTANCE, inventory, level).orElse(null);
    }

}