package com.buuz135.litterboxlib.proxy.common.client.gui.addon.capability;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.button.BasicGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.work.PosWorkBar;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.List;

public class WorkBarGuiAddon extends BasicGuiAddon {

    private static final Rectangle BACKGROUND = new Rectangle(211, 1, 11, 56);
    private static final Rectangle BAR = new Rectangle(223, 1, 5, 50);
    private static final Rectangle BACKGROUND_BAR = new Rectangle(229, 1, 5, 50);

    private final PosWorkBar workBar;

    public WorkBarGuiAddon(PosWorkBar workBar) {
        super(workBar.getPosX(), workBar.getPosY());
        this.workBar = workBar;
    }

    @Override
    public int getXSize() {
        return BACKGROUND.width;
    }

    @Override
    public int getYSize() {
        return BACKGROUND.height;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        container.mc.getTextureManager().bindTexture(GuiTile.BG_TEXTURE);
        container.drawTexturedModalRect(this.getPosX() + container.getGuiLeft(), this.getPosY() + container.getGuiTop(), BACKGROUND.x, BACKGROUND.y, BACKGROUND.width, BACKGROUND.height);
        Color color = new Color(workBar.getColor());
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
        container.drawTexturedModalRect(this.getPosX() + container.getGuiLeft() + 3, this.getPosY() + container.getGuiTop() + 3, BACKGROUND_BAR.x, BACKGROUND_BAR.y, BACKGROUND_BAR.width, BACKGROUND_BAR.height);
        double progress = workBar.getProgress() / (double) workBar.getMaxProgress();
        int barSize = (int) Math.floor(BAR.height * progress);
        container.drawTexturedModalRect(this.getPosX() + container.getGuiLeft() + 3, (this.getPosY() + container.getGuiTop() + 3 + BAR.height - barSize), BAR.x, BAR.y, BAR.width, barSize);
    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipLines() {
        return null;
    }
}
