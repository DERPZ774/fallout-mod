package com.derpz.nukaisl.block.entity;

import javax.annotation.Nullable;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.screen.NukaColaMachineMenu;
import com.mojang.serialization.Decoder.Simple;

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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

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
    private static int currentSlot = 1;

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

    @Override
    public Component getDisplayName() {
        return Component.literal("Nuka-Cola Machine");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pCompound) {
        pCompound.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pCompound);
    }

    @Override
    public void load(CompoundTag pCompound) {
        super.load(pCompound);
        itemHandler.deserializeNBT(pCompound.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);

    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, NukaColaMachineBlockEntity pEntity) {
        if (pLevel.isClientSide) {
            return;
        }

        if (hasRecipe(pEntity)) {
            pEntity.progress++;
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

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craft(NukaColaMachineBlockEntity pEntity) {
        if (hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(currentSlot, 1, false);
            pEntity.itemHandler.insertItem(currentSlot, ModItems.NUKA_COLA_CHERRY.get().getDefaultInstance(), false);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(NukaColaMachineBlockEntity pEntity) {
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        for (int i = 1; i <= 6; ++i) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
            if (stack.getItem() == ModItems.NUKA_COLA.get()) {
                currentSlot = i;
                return true;
            }
        }
        return false;

    }
}
