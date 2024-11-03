package com.feliscape.wintershine.content.client.gui;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.client.ClientMobEffectData;
import com.feliscape.wintershine.registry.WinterShineMobEffects;
import com.feliscape.wintershine.util.ColorUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

public class SiphonGuiLayer implements LayeredDraw.Layer {
    public static final ResourceLocation ID = WinterShine.asResource("siphon");

    private static final ResourceLocation TEXTURE = WinterShine.asResource("textures/gui/hud.png");
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int centerX = guiGraphics.guiWidth() / 2;
        int centerY = guiGraphics.guiHeight() / 2;

        if (Minecraft.getInstance().player.hasEffect(WinterShineMobEffects.SIPHON)){
            int siphonedHearts = ClientMobEffectData.getSiphonedHearts();
            RenderSystem.enableBlend();

            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, TEXTURE);

            RenderSystem.defaultBlendFunc();

            guiGraphics.blit(TEXTURE, centerX + 5, centerY - 4, 0, 0, 9, 9);
            guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(siphonedHearts), centerX + 16, centerY - 3, ColorUtil.getIntColor("#ffffff"));
        }
    }
}
