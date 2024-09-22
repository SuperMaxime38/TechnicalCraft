package fr.maxime38.technical_craft.item.custom;

import fr.maxime38.technical_craft.block.ModBlocks;
import fr.maxime38.technical_craft.block.custom.WireBlock;
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

    public static HashMap<String, HashMap<String, List<String>>> datas = new HashMap<String, HashMap<String, List<String>>>();

    public static String itemName;

    public CustomBlockItem(Block block, Properties properties, String name) {
        super(block, properties);
        itemName = name;

        initDatas();
    }

    public void initDatas() {
        HashMap<String ,List<String>> dt = new HashMap<String, List<String>>();
        for(String block : ModBlocks.customBlocksItems) {

            dt.clear();

            switch(block) {
                default:
                    dt.put("tooltip", List.of(""));
                    break;
                case "beamion_resistance_block":
                    dt.put("tooltip", List.of("This is a very strong block that can emit a bit of his power to you"));
                    dt.put("shift_tooltip", List.of("You get resistance for 3 second when you walk on it :)"));
                    break;
                case "rgb_lamp":
                    dt.put("tooltip", List.of("Allow you to display serveral colors depending on the input signal strenght"));
                    dt.put("shift_tooltip", Arrays.asList("§cRED §f-> §68","§2GREEN §f-> §64","§9BLUE §f-> §62","","§fYou can combine colors:","blue (2) + red (8) = purple (10)"));
                    break;
            }

            datas.put(block, dt);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack item, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {

        HashMap<String, List<String>> extradata = datas.get(itemName);

        if(extradata.containsKey("tooltip")) {
            for(String line : extradata.get("tooltip")) {
                components.add(Component.literal(line));
            }
            components.add(Component.literal(""));
            if(Screen.hasShiftDown() && extradata.containsKey("shift_tooltip")) {
                for(String line : extradata.get("shift_tooltip")) {
                    components.add(Component.literal(line));
                }
            } else {
                components.add(Component.literal("§ePress Shift for more infos"));
            }
        }

        super.appendHoverText(item, level, components, flag);
    }
}
