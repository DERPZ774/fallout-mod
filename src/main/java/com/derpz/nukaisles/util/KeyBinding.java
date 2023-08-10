package com.derpz.nukaisles.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final  String KEY_CATEGORY_nukaisles = "key.category.nukaisles";
    public static final String KEY_DRINK_COLA = "key.nukaisles.drink_cola";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_COLA, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_nukaisles);

    public static final KeyMapping SHOOTING_KEY = new KeyMapping("key.nukaisles.shoot", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_1, KEY_CATEGORY_nukaisles);

}
