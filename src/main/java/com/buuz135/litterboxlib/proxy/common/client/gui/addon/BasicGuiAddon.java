package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import lombok.Getter;

public abstract class BasicGuiAddon implements IGuiAddon {

    @Getter
    private int posX;
    @Getter
    private int posY;

    public BasicGuiAddon(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public abstract int getXSize();

    public abstract int getYSize();

    @Override
    public boolean isInside(GuiTile container, int mouseX, int mouseY) {
        return mouseX > this.getPosX() && mouseX < this.getPosX() + getXSize() && mouseY > this.getPosY() && mouseY < this.getPosY() + getYSize();
    }
}
