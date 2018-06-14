package com.buuz135.litterboxlib.proxy.common.tile.container.capability;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.button.StateButtonInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

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

    public StateButtonInfo getInfo(EnumFacing facing, String name) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Facing", facing.getName());
        compound.setInteger("Next", (this.getIndex() + 1) % values().length);
        compound.setString("Name", name);
        return new StateButtonInfo(this.getIndex(), GuiTile.BG_TEXTURE, 196, 1 + 15 * this.getIndex(), new String[]{this.name()}, compound);
    }

    public int getIndex() {
        return index;
    }
}
