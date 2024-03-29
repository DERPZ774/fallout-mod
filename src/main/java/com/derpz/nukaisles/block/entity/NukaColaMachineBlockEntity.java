package com.derpz.nukaisles.block.entity;

import javax.annotation.Nullable;

import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.networking.packet.EnergySyncS2CPacket;
import com.derpz.nukaisles.networking.packet.ItemStackSyncS2CPacket;
import com.derpz.nukaisles.recipe.NukaColaMachineRecipe;
import com.derpz.nukaisles.util.ModEnergyStorage;
import com.google.common.collect.Maps;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import com.derpz.nukaisles.screen.NukaColaMachineMenu;

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
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Map;
import java.util.Objects;

public class NukaColaMachineBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private int extractionTickCounter = 0;
    private static final int EXTRACTION_TICK_INTERVAL = 20; // Adjust this value to change the extraction interval (20 ticks = 1 second)
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(3500, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 10;
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private BlockPos topBlockPos;

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
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new NukaColaMachineMenu(id, pInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public void setTopBlock(BlockPos topBlockPos) {
        this.topBlockPos = topBlockPos;
    }

    public void copyDataFrom(NukaColaMachineBlockEntity bottomBlockEntity) {
        // Copy the relevant data from the bottom block entity to the top block entity

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return bottomBlockEntity.data.get(index);
            }

            @Override
            public void set(int index, int value) {
                bottomBlockEntity.data.set(index, value);
            }

            @Override
            public int getCount() {
                return bottomBlockEntity.data.getCount();
            }
        };

        // Copy other necessary data here
        // For example, if you have additional fields that need to be copied, you can do so like this:
        // this.property1 = bottomBlockEntity.property1;
        // this.property2 = bottomBlockEntity.property2;
        // ...
    }


    public ItemStack getRenderStack(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Nuka-Cola Machine");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
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

        if (hasEnergyInFirstSlot(pEntity)) {
            setChanged(pLevel, pPos, pState);
            return;
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
        Map<Item, Integer> fuelMap = getFuel();
        ItemStack stack = pEntity.itemHandler.getStackInSlot(0);
        Item item = stack.getItem();

        if (fuelMap.containsKey(item) && pEntity.ENERGY_STORAGE.getEnergyStored() != pEntity.ENERGY_STORAGE.getMaxEnergyStored()) {
            int energy = fuelMap.get(item);

            // Check if the extraction tick counter has reached the interval
            if (pEntity.extractionTickCounter >= EXTRACTION_TICK_INTERVAL) {
                pEntity.extractionTickCounter = 0; // Reset the tick counter

                pEntity.ENERGY_STORAGE.receiveEnergy(energy, false);

                if (pEntity.itemHandler.getStackInSlot(0).getItem() == Items.POWDER_SNOW_BUCKET) {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.insertItem(0, new ItemStack(Items.BUCKET), false);
                } else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                }

                return true;
            } else {
                pEntity.extractionTickCounter++; // Increment the tick counter
            }
        }

        return false;
    }


    public static Map<Item, Integer> getFuel() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        add(map, Items.SNOWBALL, 500);
        add(map, Items.POWDER_SNOW_BUCKET, 750);
        add(map, Items.SNOW_BLOCK, 1250);
        add(map, Items.ICE, 1750);
        add(map, Items.PACKED_ICE, 2250);
        add(map, Items.BLUE_ICE, 2750);
        return map;
    }


    private static void add(Map<Item, Integer> pMap, ItemLike pItem, int pCoolantTime) {
        Item item = pItem.asItem();
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            throw Util.pauseInIde(new IllegalStateException("Creating an item that is immune to Candy's magnificent power"));
        } else {
            pMap.put(item, pCoolantTime);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craft(NukaColaMachineBlockEntity pEntity) {
        NukaColaMachineRecipe recipe = getRecipe(pEntity);
        if (recipe != null) {
            pEntity.itemHandler.extractItem(recipe.getSlot(), 0, false);

            pEntity.itemHandler.extractItem(recipe.getSlot(), 1, false);

            pEntity.itemHandler.insertItem(recipe.getSlot(), recipe.getResultItem(Objects.requireNonNull(pEntity.getLevel()).m_9598_()), false);

            pEntity.resetProgress();
        }

    }

    private static NukaColaMachineRecipe getRecipe(NukaColaMachineBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        assert level != null;
        return level.getRecipeManager().getRecipeFor(NukaColaMachineRecipe.Type.INSTANCE, inventory, level).orElse(null);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    public void setTopBlockPos(BlockPos topBlockPos) {
        this.topBlockPos = topBlockPos;
    }

    public BlockPos getTopBlockPos() {
        return topBlockPos;
    }


}