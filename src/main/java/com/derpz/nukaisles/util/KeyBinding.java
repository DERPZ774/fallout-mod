package com.derpz.nukaisles.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final  String KEY_CATEGORY_nukaisles = "key.category.nukaisles";

    public static final KeyMapping SHOOTING_KEY = new KeyMapping("key.nukaisles.shoot", KeyConflictContext.UNIVERSAL,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_1, KEY_CATEGORY_nukaisles);

    public static final KeyMapping AIMING_KEY = new KeyMapping("key.nukaisles.aim", KeyConflictContext.UNIVERSAL,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_2, KEY_CATEGORY_nukaisles);
}
