package fr.maxime38.technical_craft.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomBlockItem extends BlockItem {

    public static HashMap<String, HashMap<String, String>> datas = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String ,String> dt = new HashMap<String, String>();

    public static String itemName;

    public CustomBlockItem(Block block, Properties properties, String name) {
        super(block, properties);
        itemName = name;

        dt.put("tooltip", "This is a very strong block that can emit a bit of his power to you");
        dt.put("shift_tooltip", "You get resistance for 3 second when you walk on it :)");
        datas.put("beamion_resistance_block", dt);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack item, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {

        HashMap<String, String> extradata = datas.get(itemName);

        if(extradata.containsKey("tooltip")) {
            components.add(Component.literal(extradata.get("tooltip")));
            components.add(Component.literal(""));
            if(Screen.hasShiftDown() && extradata.containsKey("shift_tooltip")) {
                components.add(Component.literal(extradata.get("shift_tooltip")));
            } else {
                components.add(Component.literal("§ePress Shift for more infos"));
            }
        }

        super.appendHoverText(item, level, components, flag);
    }
}
