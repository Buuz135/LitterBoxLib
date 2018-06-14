package com.buuz135.litterboxlib.proxy.common.client.gui.addon.button;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.util.Arrays;
import java.util.List;

public abstract class StateButtonAddon extends BasicButtonAddon {

    private StateButtonInfo[] buttonInfos;

    public StateButtonAddon(PosButton posButton, StateButtonInfo... buttonInfos) {
        super(posButton);
        this.buttonInfos = new StateButtonInfo[]{};
        if (buttonInfos != null) this.buttonInfos = buttonInfos;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {
        StateButtonInfo buttonInfo = getStateInfo();
        if (buttonInfo != null) {
            GlStateManager.color(1, 1, 1, 1);
            Minecraft.getMinecraft().getTextureManager().bindTexture(buttonInfo.getTexture());
            container.drawTexturedModalRect(getPosX() + container.getGuiLeft(), getPosY() + container.getGuiTop(), buttonInfo.getTextureX(), buttonInfo.getTextureY(), getXSize(), getYSize());
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {
        StateButtonInfo buttonInfo = getStateInfo();
        if (buttonInfo != null && isInside(container, mouseX - container.getGuiLeft(), mouseY - container.getGuiTop())) {
            container.drawSelectingOverlay(getPosX() + 1, getPosY() + 1, getPosX() + getXSize() - 1, getPosY() + getYSize() - 1);
        }
    }

    @Override
    public List<String> getTooltipLines() {
        StateButtonInfo buttonInfo = getStateInfo();
        if (buttonInfo != null) {
            return Arrays.asList(buttonInfo.getTooltip());
        }
        return null;
    }

    @Override
    public void handleClick(GuiTile information, int guiX, int guiY, int mouseX, int mouseY) {

    }

    public StateButtonInfo getStateInfo() {
        for (StateButtonInfo buttonInfo : buttonInfos) {
            if (buttonInfo.getState() == getState()) {
                return buttonInfo;
            }
        }
        return null;
    }

    public abstract int getState();
}
