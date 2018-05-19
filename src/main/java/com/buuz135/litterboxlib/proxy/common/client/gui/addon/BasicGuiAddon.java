package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

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
}
