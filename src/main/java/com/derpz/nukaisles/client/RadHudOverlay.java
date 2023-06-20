package com.derpz.nukaisles.client;

import com.derpz.nukaisles.FalloutMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class RadHudOverlay {
    private static final ResourceLocation FILLED_RADS = new ResourceLocation(FalloutMod.MOD_ID,
            "textures/rads/filled_rads.png");

    private static final ResourceLocation EMPTY_RADS = new ResourceLocation(FalloutMod.MOD_ID,
            "textures/rads/empty_rads.png");

    public static final IGuiOverlay HUD_RADS = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_RADS);
        for(int i = 0; i < 10; i++) {
            GuiComponent.blit(poseStack, x - 94 + (i * 9), y - 54, 0, 0, 12, 12,
                    12, 12);
        }
        RenderSystem.setShaderTexture(0, FILLED_RADS);
        for(int i = 0; i < 10; i ++) {
           if(ClientRadData.getPlayerRads () > i) {
                GuiComponent.blit(poseStack, x - 94 + (i * 9), y - 54, 0, 0, 12, 12,
                        12, 12);
            } else {
                break;
            }
            /// TODO: 10/16/2022 fix this, rads overlay never goes down or up only stays on the filled one (have dom do since he made the rads) 

        }



    });
}
