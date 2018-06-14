package com.buuz135.litterboxlib.proxy.common.client.gui.addon.button;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import lombok.Getter;
import lombok.Setter;

public abstract class BasicGuiAddon implements IGuiAddon {

    @Getter
    @Setter
    private int posX;
    @Getter
    @Setter
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
