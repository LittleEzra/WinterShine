package com.feliscape.wintershine.content.screen;

import com.feliscape.wintershine.WinterShine;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class ConfectioneryOvenScreen extends AbstractContainerScreen<ConfectioneryOvenMenu> {
    private static final ResourceLocation TEXTURE =
            WinterShine.asResource("textures/gui/container/confectionery_oven.png");
    private static final ResourceLocation BAKE_PROGRESS =
            WinterShine.asResource("textures/gui/sprites/container/confectionery_oven/bake_progress.png");
    private static final ResourceLocation LIT_PROGRESS =
            WinterShine.asResource("textures/gui/sprites/container/confectionery_oven/lit_progress.png");

    @Override
    protected void init() {
        super.init();
    }

    public ConfectioneryOvenScreen(ConfectioneryOvenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);


        int scaledWidth = this.menu.getCookProgressionScaled();
        WinterShine.printDebug(scaledWidth);
        guiGraphics.blit(TEXTURE,
                this.leftPos + 88, this.topPos + 32,
                176, 8,
                scaledWidth, 16);

        if (menu.isLit()) {
            int scaledLitTime = Mth.ceil(this.menu.getLitProgress() * 8.0F);
            guiGraphics.blit(TEXTURE,
                    this.leftPos + 95, this.topPos + 48 + 8 - scaledLitTime,
                    183, 8 - scaledLitTime,
                    7, scaledLitTime);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float pPartialTick) {
        super.render(guiGraphics, mouseX, mouseY, pPartialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
