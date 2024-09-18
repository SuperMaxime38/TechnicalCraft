package fr.maxime38.technical_craft.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EightBallItem extends Item {


    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        if(level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            //Output something
            player.sendSystemMessage(Component.literal("Number : " + getRandomNumber()));

            //Cooldown
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(8) +1;
    }
}
