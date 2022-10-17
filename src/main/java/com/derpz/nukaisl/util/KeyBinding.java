package com.derpz.nukaisl.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final  String KEY_CATEGORY_NUKAISL = "key.category.nukaisl";
    public static final String KEY_DRINK_COLA = "key.nukaisl.drink_cola";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_COLA, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_NUKAISL);
}
