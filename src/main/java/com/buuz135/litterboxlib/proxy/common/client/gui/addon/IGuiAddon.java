package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;

import java.util.List;


public interface IGuiAddon {

    void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY);

    void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY);

    List<String> getTooltipLines();

    boolean isInside(GuiTile container, int mouseX, int mouseY);
}
