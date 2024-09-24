package fr.maxime38.technical_craft.event;

import fr.maxime38.technical_craft.TechnicalCraft;
import fr.maxime38.technical_craft.networking.ModPackets;
import fr.maxime38.technical_craft.networking.packet.ExampleC2SPacket;
import fr.maxime38.technical_craft.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = TechnicalCraft.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.HELPER_KEY.consumeClick()) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed help (there's no help lol)"));
                ModPackets.sendToServer(new ExampleC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = TechnicalCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.HELPER_KEY);
        }
    }
}
