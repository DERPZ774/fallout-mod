package com.derpz.nukaisl.block.entity;

import javax.annotation.Nullable;

import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.EnergySyncS2CPacket;
import com.derpz.nukaisl.networking.packet.ItemStackSyncS2CPacket;
import com.derpz.nukaisl.recipe.NukaColaMachineRecipe;
import com.derpz.nukaisl.util.ModEnergyStorage;
import com.google.common.collect.Maps;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

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

import java.util.Map;
import java.util.Objects;

public class NukaColaMachineBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
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

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(3500, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

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

    public ItemStack getRenderStack() {
        int stackWithItem = 0;

        for (int i = 1; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                stackWithItem = i;
            }
        }

        if (!itemHandler.getStackInSlot(stackWithItem).isEmpty() && stackWithItem != 0) {
            return itemHandler.getStackInSlot(stackWithItem);
        }
        else {
            return itemHandler.getStackInSlot(1);
        }
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
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
            pEntity.ENERGY_STORAGE.receiveEnergy(energy, false);
            if (pEntity.itemHandler.getStackInSlot(0).getItem() == Items.POWDER_SNOW_BUCKET) {
                pEntity.itemHandler.extractItem(0, 1, false);
                pEntity.itemHandler.insertItem(0, new ItemStack(Items.BUCKET), false);
            }
            else {
                pEntity.itemHandler.extractItem(0, 1, false);
            }
            return true;
        }
        /// TODO: 5/30/2023 make fuel get taken out less fast

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
        if(recipe != null) {
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

}