package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

public class WireBlock extends Block implements IForgeBlock{
    // Propriété pour gérer le signal de 0 à 15 (4 bits)
    public static final IntegerProperty SIGNAL = IntegerProperty.create("signal", 0, 15);
    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 4);

    public WireBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SIGNAL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIGNAL);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!world.isClientSide()) {
            updateSignal(world, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!world.isClientSide()) {
            updateSignal(world, pos, state);
        }
    }

    // Mise à jour du signal
    private void updateSignal(Level world, BlockPos pos, BlockState state) {
        int inputSignal = getStrongestNeighborSignal(world, pos);
        if (inputSignal != state.getValue(SIGNAL)) {
            world.setBlock(pos, state.setValue(SIGNAL, inputSignal), 3);
            if(state.getValue(SIGNAL) > 0) {
                state.setValue(LIGHT, 4);
            } else {
                state.setValue(LIGHT, 0);
            }
            world.updateNeighborsAt(pos, this); // Notifie les voisins que le signal a changé
        }
    }

    // Obtenir le signal maximum des blocs voisins
    private int getStrongestNeighborSignal(Level world, BlockPos pos) {
        int maxSignal = 0;
        for (Direction direction : Direction.values()) {
            int neighborSignal = world.getSignal(pos.relative(direction), direction.getOpposite());
            if (neighborSignal > maxSignal) {
                maxSignal = neighborSignal;
            }
        }
        return Math.min(maxSignal, 15); // Limité à 4 bits (0-15)
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
        return state.getValue(SIGNAL);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter p_60484_, BlockPos p_60485_, Direction p_60486_) {
        return state.getValue(SIGNAL);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true; // Le bloc est une source de signal de redstone
    }

    public static int getLightLevel(BlockState state) {
        if(state.getValue(SIGNAL) > 0) {
            return 3;
        }
        return 0;
    }
}
