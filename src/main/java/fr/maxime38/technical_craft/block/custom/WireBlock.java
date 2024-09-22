package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.net.HttpRetryException;

public class WireBlock extends Block {
    // Propriété pour gérer le signal de 0 à 15 (4 bits)
    public static final IntegerProperty SIGNAL = IntegerProperty.create("signal", 0, 15);

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

    // Méthode pour vérifier si le bloc doit surveiller les signaux faibles (comme les torches de redstone)


    @Override
    public boolean shouldCheckWeakPower(BlockState state, LevelReader level, BlockPos pos, Direction side) {
        return true;
    }

    // Mise à jour du signal (y compris la réinitialisation à 0 si aucune source n'est présente)
    private void updateSignal(Level world, BlockPos pos, BlockState state) {
        int inputSignal = getStrongestNeighborSignal(world, pos);

        // Si le signal détecté est différent de l'actuel, mettre à jour le bloc
        if (inputSignal != state.getValue(SIGNAL)) {
            world.setBlock(pos, state.setValue(SIGNAL, inputSignal), 3);
            notifyNeighbors(world, pos);
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
        return maxSignal; // Retourne 0 si aucune source n'est détectée
    }

    // Notifier tous les voisins pour forcer la mise à jour des signaux redstone
    private void notifyNeighbors(Level world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            world.updateNeighborsAt(pos.relative(direction), this);
        }
    }

    // Méthode pour transmettre un signal de redstone (méthode indirecte)


    @Override
    public int getSignal(BlockState state, BlockGetter p_60484_, BlockPos p_60485_, Direction p_60486_) {
        return state.getValue(SIGNAL);
    }

    // Méthode pour transmettre un signal direct (comme les comparateurs, repeaters)


    @Override
    public int getDirectSignal(BlockState state, BlockGetter p_60560_, BlockPos p_60561_, Direction p_60562_) {
        return state.getValue(SIGNAL);
    }

    // Permet de se connecter à d'autres composants de redstone


    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true; // Le bloc est une source de signal de redstone
    }
}
