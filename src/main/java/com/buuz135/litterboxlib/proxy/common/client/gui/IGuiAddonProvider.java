package com.buuz135.litterboxlib.proxy.common.client.gui;

import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IGuiAddonProvider {

    @SideOnly(Side.CLIENT)
    public List<? extends IGuiAddon> getGuiAddons();
}
