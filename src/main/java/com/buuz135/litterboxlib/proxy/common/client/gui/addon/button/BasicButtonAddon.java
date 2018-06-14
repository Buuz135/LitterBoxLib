package com.buuz135.litterboxlib.proxy.common.client.gui.addon.button;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.IClickable;
import com.buuz135.litterboxlib.proxy.common.network.ButtonClickedMessage;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

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
    public void handleClick(GuiTile information, int guiX, int guiY, int mouseX, int mouseY, int button) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        Litterboxlib.NETWORK.sendToServer(new ButtonClickedMessage(information.getContainerTile().getTile().getPos(), this.button.getId()));
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
