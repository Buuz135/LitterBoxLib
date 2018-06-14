package com.buuz135.litterboxlib.proxy.common.tile.container.capability;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.button.StateButtonInfo;

public enum FaceMode {
    NONE(false, 0), ENABLED(true, 1), PUSH(true, 2), PULL(true, 3);

    private final boolean allowsConnection;
    private final int index;

    FaceMode(boolean allowsConnection, int index) {
        this.allowsConnection = allowsConnection;
        this.index = index;
    }

    public boolean allowsConnection() {
        return allowsConnection;
    }

    public StateButtonInfo getInfo() {
        return new StateButtonInfo(this.getIndex(), GuiTile.BG_TEXTURE, 196, 1 + 15 * this.getIndex(), new String[]{this.name()});
    }

    public int getIndex() {
        return index;
    }
}
