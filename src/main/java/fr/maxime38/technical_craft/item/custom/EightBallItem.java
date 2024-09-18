package fr.maxime38.technical_craft.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendHoverText(@NotNull ItemStack item, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {

        components.add(Component.literal("This is a really useful item !\n"));
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("I'm joking this is useless"));
        } else {
            components.add(Component.literal("§ePress Shift for more infos"));
        }

        super.appendHoverText(item, level, components, flag);
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(8) +1;
    }
}
