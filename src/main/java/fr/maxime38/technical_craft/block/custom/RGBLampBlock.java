package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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

public class RGBLampBlock extends Block {


    public static final IntegerProperty COLOR_STATE = IntegerProperty.create("colorstate", 0, 15);

    public RGBLampBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            player.sendSystemMessage(Component.literal("COLOR_STATE: "+ COLOR_STATE));
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }


    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!world.isClientSide()) {
            updateSignal(world, pos, state);
        }
    }

    // Mise à jour du signal (y compris la réinitialisation à 0 si aucune source n'est présente)
    private void updateSignal(Level world, BlockPos pos, BlockState state) {
        int inputSignal = getStrongestNeighborSignal(world, pos);

        // Si le signal détecté est différent de l'actuel, mettre à jour le bloc
        if (inputSignal != state.getValue(COLOR_STATE)) {
            world.setBlock(pos, state.setValue(COLOR_STATE, inputSignal), 3);
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR_STATE);
    }
}
