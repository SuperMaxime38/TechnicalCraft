package fr.maxime38.technical_craft.world.feature;

import fr.maxime38.technical_craft.TechnicalCraft;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TechnicalCraft.MODID);


    public static final RegistryObject<PlacedFeature> BEAMION_ORE_PLACED = PLACED_FEATURES.register("beamion_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.BEAMION_ORE.getHolder().get(),
                    commonOrePlacement(4, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-14), VerticalAnchor.aboveBottom(19)))));


    public static List<PlacementModifier> orePlacement(PlacementModifier pMod, PlacementModifier pMod2) {
        return List.of(pMod, InSquarePlacement.spread(), pMod2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
