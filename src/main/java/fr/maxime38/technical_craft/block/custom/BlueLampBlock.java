package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlueLampBlock extends Block {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public BlueLampBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void neighborChanged(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos neighborPos, boolean isMoving) {
        if (!world.isClientSide()) {
            boolean isPowered = state.getValue(LIT);
            if (isPowered != world.hasNeighborSignal(pos)) {
                world.setBlock(pos, state.setValue(LIT, world.hasNeighborSignal(pos)), 3);
                world.updateNeighborsAt(pos, this);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
