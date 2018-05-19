package com.buuz135.litterboxlib.proxy.common.tile.container;

import com.buuz135.litterboxlib.proxy.common.client.gui.IGuiAddonProvider;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.BasicButtonAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class ButtonHandler implements IGuiAddonProvider {

    private List<BasicButtonAddon> basicButtonAddons;

    public ButtonHandler() {
        basicButtonAddons = new ArrayList<>();
    }

    public void addButton(BasicButtonAddon buttonAddon){
        basicButtonAddons.add(buttonAddon.setId(basicButtonAddons.size()));
    }

    public void clickButton(int id, NBTTagCompound compound){
        basicButtonAddons.stream().filter(buttonAddon -> buttonAddon.getId() == id).forEach(buttonAddon -> buttonAddon.onButtonClicked(compound));
    }

    @Override
    public List<? extends IGuiAddon> getGuiAddons() {
        return basicButtonAddons;
    }
}
