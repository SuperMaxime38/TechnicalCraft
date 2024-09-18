package fr.maxime38.technical_craft.item;

import fr.maxime38.technical_craft.TechnicalCraft;
import fr.maxime38.technical_craft.item.custom.EightBallItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechnicalCraft.MODID);

    public static final RegistryObject<Item> BEAMION  =ITEMS.register("beamion", () -> new Item(
            new Item.Properties()
                    .stacksTo(69)
                    .tab(ModCreativeModeTab.TECHNICAL_CRAFT_TAB)
                    .rarity(Rarity.RARE)));

    public static final RegistryObject<Item> RAW_BEAMION  =ITEMS.register("raw_beamion", () -> new Item(
            new Item.Properties()
                    .stacksTo(69)
                    .tab(ModCreativeModeTab.TECHNICAL_CRAFT_TAB)
                    .rarity(Rarity.RARE)));

    public static final RegistryObject<Item> EIGHT_BALL  =ITEMS.register("eight_ball", () -> new EightBallItem(
            new Item.Properties()
                    .stacksTo(8)
                    .tab(ModCreativeModeTab.TECHNICAL_CRAFT_TAB)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
