package com.derpz.nukaisl.block.entity;


import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.recipe.NukaColaBottleOpenerRecipe;
import com.derpz.nukaisl.screen.NukaColaOpenerMenu;
import com.derpz.nukaisl.util.InventoryUtil;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NukaColaOpenerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected  void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;



    public NukaColaOpenerBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.NUKA_COLA_OPENER.get(), pWorldPosition, pBlockState);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> NukaColaOpenerBlockEntity.this.progress;
                    case 1 -> NukaColaOpenerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> NukaColaOpenerBlockEntity.this.progress = value;
                    case 1 -> NukaColaOpenerBlockEntity.this.maxProgress = value;

                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Bottle Opener");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new NukaColaOpenerMenu(pContainerId, pInventory, this, this.data);
    }
    @NonNull
    @Override
    public <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("cap.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("cap.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, NukaColaOpenerBlockEntity pBlockEntity) {
        if (hasRecipe(pBlockEntity) && hasNotReachedStackLimit(pBlockEntity)) {
            pBlockEntity.progress++;
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                setChanged(pLevel, pPos, pState);
                craftItem(pBlockEntity);
                pBlockEntity.itemHandler.getStackInSlot(2).getOrCreateTag().putInt("Cap", 1);
            }
        }
        }


    private static void craftItem(NukaColaOpenerBlockEntity entity) {
        entity.itemHandler.extractItem(0, 1, false);

        entity.itemHandler.setStackInSlot(1, new ItemStack(ModItems.BOTTLE_CAP.get(),
                entity.itemHandler.getStackInSlot(1).getCount() + 1));

        entity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.NUKA_COLA.get(),
                entity.itemHandler.getStackInSlot(2).getCount() + 1));

        entity.resetProgress();
    }
    private static boolean hasRecipe(NukaColaOpenerBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() == ModItems.NUKA_COLA_CHERRY.get();
    }


    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean hasNotReachedStackLimit(NukaColaOpenerBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getCount() < entity.itemHandler.getStackInSlot(2).getMaxStackSize();
    }
}
/// TODO: 8/14/2022 Add other cola recipes
