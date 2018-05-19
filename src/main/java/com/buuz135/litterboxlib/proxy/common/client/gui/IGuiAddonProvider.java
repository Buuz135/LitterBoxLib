package com.buuz135.litterboxlib.proxy.common.client.gui;

import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;

import java.util.List;

public interface IGuiAddonProvider {

    public List<? extends IGuiAddon> getGuiAddons();
}
