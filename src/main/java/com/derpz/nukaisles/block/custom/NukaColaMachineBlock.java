package com.derpz.nukaisles.block.custom;

import javax.annotation.Nullable;

import com.derpz.nukaisles.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import com.derpz.nukaisles.block.entity.ModBlockEntities;
import com.derpz.nukaisles.block.entity.NukaColaMachineBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class NukaColaMachineBlock extends BaseEntityBlock {


        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public static final IntegerProperty CLICKED = IntegerProperty.create("clicked", 0, 1);

        public NukaColaMachineBlock(Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CLICKED, 0));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, CLICKED);
        }



    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 32, 16);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pBlock, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    //getStateForPlacement
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    //rotate
    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    //mirror
    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof NukaColaMachineBlockEntity) {
                ((NukaColaMachineBlockEntity) blockEntity).drops();
            }
        }

        // Get the facing direction of the removed block
        Direction facing = pState.getValue(FACING);

        // Calculate the position of the top block based on the facing direction
        BlockPos topPos = pPos.above();
        BlockPos bottomPos = pPos.below();

        // Remove the top block
        if (pLevel.getBlockState(topPos).getBlock() instanceof NukaColaMachineTop) {
            pLevel.destroyBlock(topPos, !pIsMoving);
        }
        if (pLevel.getBlockState(bottomPos).getBlock() instanceof NukaColaMachineBlock) {
            pLevel.destroyBlock(bottomPos, !pIsMoving);
        }
    }


    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof NukaColaMachineBlockEntity NukaColaMachineBlockEntity) {
                ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
                NetworkHooks.openScreen(serverPlayer, NukaColaMachineBlockEntity, pPos);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        // Get the facing direction of the placed block
        Direction facing = pState.getValue(FACING);

        // Calculate the position for the top block based on the facing direction
        BlockPos topPos = pPos.above();

        // Place the top block
        if (pLevel.getBlockState(topPos).isAir()) {
            BlockState topBlockState = ModBlocks.NUKA_COLA_MACHINE_TOP.get().defaultBlockState().setValue(FACING, facing);
            pLevel.setBlock(topPos, topBlockState, 3);

            // Get the block entity of the bottom block
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof NukaColaMachineBlockEntity) {
                NukaColaMachineBlockEntity bottomBlockEntity = (NukaColaMachineBlockEntity) blockEntity;

                // Set the top block position
                bottomBlockEntity.setTopBlock(topPos);

                // Create a new block entity instance for the top block and copy data from the bottom block entity
                BlockEntity topBlockEntity = pLevel.getBlockEntity(topPos);
                if (topBlockEntity instanceof NukaColaMachineBlockEntity) {
                    ((NukaColaMachineBlockEntity) topBlockEntity).copyDataFrom(bottomBlockEntity);
                }
            }
        }
    }


    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new NukaColaMachineBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pType) {
        return createTickerHelper(pType, ModBlockEntities.NUKA_COLA_MACHINE.get(), NukaColaMachineBlockEntity::tick);
    }

}
