package net.illnessnotincluded.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.illness_not_included";
    public static final String KEY_DRINK = "key.illness_not_included.drink";

    public static final KeyMapping DRINKING_KEY =
            new KeyMapping(KEY_DRINK, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY);
}
