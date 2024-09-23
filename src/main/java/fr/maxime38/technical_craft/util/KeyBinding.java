package fr.maxime38.technical_craft.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_HELPER = "key.category.technical_craft.technicalcraft";
    public static final String KEY_OPEN_HELP = "key.technical_craft.open_help";

    public static final KeyMapping HELPER_KEY = new KeyMapping(KEY_OPEN_HELP, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_HELPER);
}
