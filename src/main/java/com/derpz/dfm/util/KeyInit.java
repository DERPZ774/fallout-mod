package com.derpz.dfm.util;

import com.derpz.dfm.FalloutMod;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyInit {
   /* public static final String KEY_CATEGORY_FALLOUT = "key.category.dfm.fallout";
    public static final String KEY_CHECK_RADS = "key.dfm.check_rads";

    public static final KeyMapping RAD_CHECK_KEY = new KeyMapping(KEY_CHECK_RADS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_FALLOUT);*/


    private KeyInit(){

    }

    public static KeyMapping radCheckKeyMapping;

    public static void init(){
        radCheckKeyMapping = registerKey("rad_check_key", KeyMapping.CATEGORY_MISC, InputConstants.KEY_PERIOD);
    }

    private static KeyMapping registerKey(String name, String category, int keycode) {
        final var KEY = new KeyMapping("key."+FalloutMod.MOD_ID+"."+name, keycode, category);
        ClientRegistry.registerKeyBinding(KEY);
        return KEY;
    }
            
}
