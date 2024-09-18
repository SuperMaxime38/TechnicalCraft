package fr.maxime38.technical_craft.item.custom;

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

public class RGBLampBlock extends Block {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty RED = BooleanProperty.create("red");
    public static final BooleanProperty GREEN = BooleanProperty.create("green");
    public static final BooleanProperty BLUE = BooleanProperty.create("blue");
    public static final IntegerProperty COLOR_STATE = IntegerProperty.create("colorstate", 0, 7);

    public RGBLampBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            level.setBlock(pos, state.cycle(LIT), 3);

            if(state.getValue(COLOR_STATE) == 0) {
                level.setBlock(pos, state.cycle(RED), 4);
            }
            if(state.getValue(COLOR_STATE) == 3) {
                level.setBlock(pos, state.cycle(GREEN), 5);
            }
            if(state.getValue(COLOR_STATE) == 6) {
                level.setBlock(pos, state.cycle(BLUE), 6);
            }

            state.cycle(COLOR_STATE);
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(RED);
        builder.add(GREEN);
        builder.add(BLUE);
    }
}
