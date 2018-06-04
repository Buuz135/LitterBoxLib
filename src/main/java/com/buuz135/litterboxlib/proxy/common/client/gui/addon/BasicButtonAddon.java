package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.IClickable;
import com.buuz135.litterboxlib.proxy.common.client.gui.IGuiInformation;
import com.buuz135.litterboxlib.proxy.common.network.ButtonClickedMessage;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosButton;

import java.util.List;

public class BasicButtonAddon extends BasicGuiAddon implements IClickable {

    private PosButton button;

    public BasicButtonAddon(PosButton posButton) {
        super(posButton.getPosX(), posButton.getPosY());
        this.button = posButton;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipLines() {
        return null;
    }


    @Override
    public void handleClick(IGuiInformation information, int guiX, int guiY, int mouseX, int mouseY) {
        Litterboxlib.NETWORK.sendToServer(new ButtonClickedMessage(information.getBlockPos(), button.getId()));
    }

    @Override
    public int getXSize() {
        return button.getSizeX();
    }

    @Override
    public int getYSize() {
        return button.getSizeY();
    }
}
