package fr.maxime38.technical_craft.block;

import fr.maxime38.technical_craft.TechnicalCraft;
import fr.maxime38.technical_craft.block.custom.ResiPoutreBlock;
import fr.maxime38.technical_craft.item.ModCreativeModeTab;
import fr.maxime38.technical_craft.item.ModItems;
import fr.maxime38.technical_craft.item.custom.CustomBlockItem;
import fr.maxime38.technical_craft.item.custom.RGBLampBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static List<String> customBlocksItems = Arrays.asList("beamion_resistance_block", "etc");

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechnicalCraft.MODID);

    public static final RegistryObject<Block> BEAMION_BLOCK = registerBlock("beamion_block",
            () -> new Block(
                    BlockBehaviour.Properties
                            .of(Material.STONE)
                            .strength(6f)
                            .requiresCorrectToolForDrops())

            , ModCreativeModeTab.TECHNICAL_CRAFT_TAB);

    public static final RegistryObject<Block> BEAMION_ORE = registerBlock("beamion_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties
                            .of(Material.STONE)
                            .strength(6f)
                            .requiresCorrectToolForDrops()
                    , UniformInt.of(1, 4))

            , ModCreativeModeTab.TECHNICAL_CRAFT_TAB);



    public static final RegistryObject<Block> BEAMION_RESISTANCE_BLOCK = registerBlock("beamion_resistance_block",
            () -> new ResiPoutreBlock(
                    BlockBehaviour.Properties
                            .of(Material.STONE)
                            .strength(7f)
                            .requiresCorrectToolForDrops())

            , ModCreativeModeTab.TECHNICAL_CRAFT_TAB);

    public static final RegistryObject<Block> RGB_LAMP = registerBlock("rgb_lamp",
            () -> new RGBLampBlock(
                    BlockBehaviour.Properties
                            .of(Material.GLASS)
                            .strength(4f)
                            .lightLevel(state -> state.getValue(RGBLampBlock.LIT) ? 15 : 0))

            , ModCreativeModeTab.TECHNICAL_CRAFT_TAB);



    // UTILS FUNCTION THAT ARE NOT BLOCKS

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        if(customBlocksItems.contains(name)) {
            ModItems.ITEMS.register(name, () -> new CustomBlockItem(block.get(), new Item.Properties().tab(tab), name)); // Custom stuff like tooltips
        } else {
            ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        }
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
