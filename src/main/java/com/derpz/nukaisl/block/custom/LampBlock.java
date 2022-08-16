package com.derpz.nukaisl.block.custom;

import com.derpz.nukaisl.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;

public class LampBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public LampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED, false));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(10, 3, 9, 11, 8, 10),
            Block.box(5, 3, 9, 6, 8, 10),
            Block.box(10, 1, 8, 11, 3, 9),
            Block.box(5, 0, 5, 11, 1, 12),
            Block.box(5, 1, 8, 6, 3, 9),
            Block.box(5, 6, 5, 11, 12, 10),
            Block.box(7, 10, 6, 10, 11, 9),
            Block.box(6, 7, 10, 10, 11, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(7, 3, 10, 8, 8, 11),
            Block.box(7, 3, 5, 8, 8, 6),
            Block.box(7, 1, 10, 8, 3, 11),
            Block.box(4, 0, 5, 11, 1, 11),
            Block.box(7, 1, 5, 8, 3, 6),
            Block.box(6, 6, 5, 11, 12, 11),
            Block.box(7, 8, 7, 10, 11, 10),
            Block.box(4, 7, 6, 6, 11, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(5, 3, 7, 6, 8, 8),
            Block.box(10, 3, 7, 11, 8, 8),
            Block.box(5, 1, 7, 6, 3, 8),
            Block.box(5, 0, 4, 11, 1, 11),
            Block.box(10, 1, 7, 11, 3, 8),
            Block.box(5, 6, 6, 11, 12, 11),
            Block.box(7, 8, 7, 10, 11, 10),
            Block.box(6, 7, 4, 10, 11, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(9, 3, 5, 10, 8, 6),
            Block.box(9, 3, 10, 10, 8, 11),
            Block.box(8, 1, 5, 9, 3, 6),
            Block.box(5, 0, 5, 12, 1, 11),
            Block.box(8, 1, 10, 9, 3, 11),
            Block.box(5, 5, 5, 10, 12, 11),
            Block.box(6, 8, 7, 9, 11, 10),
            Block.box(10, 7, 6, 13, 11, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case SOUTH -> SHAPE_S;
            case EAST -> SHAPE_E;
            case WEST -> SHAPE_W;
            default -> SHAPE_N;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
            boolean currentState = pState.getValue(CLICKED);
            pLevel.setBlock(pPos, pState.setValue(CLICKED, !currentState), 3);

            float f = pState.getValue(CLICKED) ? 0.6F : 0.5F;
            pLevel.playSound((Player)null, pPos, ModSounds.LAMP_USE.get(), SoundSource.BLOCKS, 0.3F, f);

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, CLICKED);
    }
}
