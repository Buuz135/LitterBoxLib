package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.function.Predicate;

public class BasicButtonAddon extends BasicGuiAddon {

    @Getter private Predicate<NBTTagCompound> serverPredicate;
    @Getter private int id;

    public BasicButtonAddon(int posX, int posY) {
        super(posX, posY);
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
    public boolean isInside(GuiTile container, int mouseX, int mouseY) {
        return false;
    }

    public BasicButtonAddon setServerRunnable(Predicate<NBTTagCompound> predicate){
        this.serverPredicate = predicate;
        return this;
    }

    public void onButtonClicked(NBTTagCompound compound){
        if (serverPredicate != null){
            serverPredicate.test(compound);
        }
    }

    public BasicButtonAddon setId(int id){
        this.id = id;
        return this;
    }
}
