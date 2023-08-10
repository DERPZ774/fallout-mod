///*
//package com.derpz.nukaisles.util.handler;
//
//import com.derpz.nukaisles.FalloutMod;
//import com.derpz.nukaisles.item.custom.GunItem;
//import com.derpz.nukaisles.sound.ModSounds;
//import com.derpz.nukaisles.util.GunHelper;
//import net.minecraft.client.Minecraft;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.InputEvent;
//import net.minecraftforge.client.event.ViewportEvent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import org.lwjgl.glfw.GLFW;
//
//@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class AimingHandler {
//    private static boolean isAiming = false;
//
//    @SubscribeEvent
//    public static void onMouseInput(InputEvent.MouseButton event) {
//        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS) {
//            Minecraft minecraft = Minecraft.getInstance();
//            assert minecraft.player != null;
//            ItemStack currentItemStack = minecraft.player.getMainHandItem();
//
//            if (currentItemStack.getItem() instanceof GunItem) {
//                GunItem gunItem = (GunItem) currentItemStack.getItem();
//                gunItem.setAiming(true); // Set aiming state of the gun item
//                isAiming = true;
//                event.setCanceled(true); // Cancel the event to prevent other interactions
//            }
//        } else if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_RELEASE) {
//            Minecraft minecraft = Minecraft.getInstance();
//            assert minecraft.player != null;
//            ItemStack currentItemStack = minecraft.player.getMainHandItem();
//
//            if (currentItemStack.getItem() instanceof GunItem) {
//                GunItem gunItem = (GunItem) currentItemStack.getItem();
//                gunItem.setAiming(false); // Set aiming state of the gun item
//                isAiming = false;
//            }
//        }
//    }
//
//
//    @SubscribeEvent
//    public static void onFovCalculation(ViewportEvent.ComputeFov event) {
//        Minecraft minecraft = Minecraft.getInstance();
//        if (minecraft.options.getCameraType().isFirstPerson()) {
//            float currentFov = (float) event.getFOV();
//
//            if (isAiming) {
//                float targetFov = currentFov - (currentFov * 0.15F); // Calculate the target FOV (10% zoom)
//                float transitionSpeed = 0.05F; // Adjust the speed of the transition
//                int transitionFrames = 10; // Number of frames for the transition
//
//                for (int i = 0; i < transitionFrames; i++) {
//                    float progress = (float) i / transitionFrames; // Calculate the progress of the transition
//                    float fov = currentFov + ((targetFov - currentFov) * progress);
//                    event.setFOV(fov);
//                }
//            }
//        }
//    }
/////ToDo: Edit the zoom based on gun and attachment & re-add after shooting is completely furbished
//
//}*/
