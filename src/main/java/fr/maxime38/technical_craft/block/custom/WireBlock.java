package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class WireBlock extends Block {
    public WireBlock(Properties properties) {
        super(properties);
    }

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos neighborPos, boolean isMoving) {
        if (!world.isClientSide()) {
            boolean isPowered = state.getValue(POWERED);
            if (isPowered != world.hasNeighborSignal(pos)) {
                world.setBlock(pos, state.setValue(POWERED, world.hasNeighborSignal(pos)), 3);
                world.updateNeighborsAt(pos, this);
            }
        }
    }


    public int getSignal(BlockState state, Level world, BlockPos pos, BlockState neighborState, net.minecraft.core.Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }
}
