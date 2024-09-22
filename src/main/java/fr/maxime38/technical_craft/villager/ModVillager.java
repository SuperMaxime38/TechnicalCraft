package fr.maxime38.technical_craft.villager;

import com.google.common.collect.ImmutableSet;
import fr.maxime38.technical_craft.TechnicalCraft;
import fr.maxime38.technical_craft.block.ModBlocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillager {

    public static final DeferredRegister<PoiType> POIT_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, TechnicalCraft.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, TechnicalCraft.MODID);

    public static final RegistryObject<PoiType> BEAMION_RESISTANCE_BLOCK_POI = POIT_TYPES.register("beamion_resistance_block_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.BEAMION_RESISTANCE_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> METAL_BEAMS_MASTER = VILLAGER_PROFESSIONS.register("metal_beam_master",
            () -> new VillagerProfession("metal_beam_master", x -> x.get() == BEAMION_RESISTANCE_BLOCK_POI.get(), x -> x.get() == BEAMION_RESISTANCE_BLOCK_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

    public static void registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, BEAMION_RESISTANCE_BLOCK_POI.get());
        } catch (InvocationTargetException |IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POIT_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
