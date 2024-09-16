package fr.maxime38.technical_craft.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {

    public static final CreativeModeTab TECHNICAL_CRAFT_TAB = new CreativeModeTab("technical_craft_tab") {
        @Contract(" -> new")
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.BEAMION.get());
        }
    };
}
