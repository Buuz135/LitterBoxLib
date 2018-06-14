package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.button.BasicGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.items.PosInventoryHandler;
import javafx.util.Pair;

import java.util.List;

public class SlotsGuiAddon extends BasicGuiAddon {

    private static final Pair<Integer, Integer> SLOT_POS = new Pair<>(1, 167 + 18);
    private static final Pair<Integer, Integer> SLOT_POS_FRAME = new Pair<>(20, 167 + 18);
    private static final Pair<Integer, Integer> SLOT_SIZE = new Pair<>(18, 18);

    private static final Pair<Integer, Integer> BIG_SLOT_POS = new Pair<>(1, 186 + 18);
    private static final Pair<Integer, Integer> BIG_SLOT_POS_FRAME = new Pair<>(28, 186 + 18);
    private static final Pair<Integer, Integer> BIG_SLOT_SIZE = new Pair<>(26, 26);

    private final PosInventoryHandler handler;

    public SlotsGuiAddon(PosInventoryHandler handler) {
        super(handler.getXPos(), handler.getYPos());
        this.handler = handler;
    }


    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {
        int renderingOffset = handler.isBigSlot() ? 5 : 1;
        if (handler.isColorEnabled())
            container.drawColor(getPosX() + container.getGuiLeft() - renderingOffset - 1, getPosY() + container.getGuiTop() - renderingOffset - 1, 18 * handler.getXSize() + renderingOffset * 2, 18 * handler.getYSize() + renderingOffset * 2, handler.getColor());
        renderSlots(container, renderingOffset, SLOT_POS, BIG_SLOT_POS);
        if (handler.isColorEnabled()) {
            container.drawColor(getPosX() + container.getGuiLeft() - renderingOffset, getPosY() + container.getGuiTop() - renderingOffset, 18 * handler.getXSize() + renderingOffset * 2 - 1, 18 * handler.getYSize() + renderingOffset * 2 - 1, 0x5F000000 + handler.getColor());
            renderSlots(container, renderingOffset, SLOT_POS_FRAME, BIG_SLOT_POS_FRAME);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipLines() {
        return null;
    }

    @Override
    public int getXSize() {
        return handler.getXSize() * SLOT_SIZE.getKey();
    }

    @Override
    public int getYSize() {
        return handler.getYSize() * SLOT_SIZE.getValue();
    }

    @Override
    public boolean isInside(GuiTile container, int mouseX, int mouseY) {
        return false;
    }

    private void renderSlots(GuiTile container, int renderingOffset, Pair<Integer, Integer> small_slot, Pair<Integer, Integer> big_slot) {
        for (int x = 0; x < handler.getXSize(); ++x) {
            for (int y = 0; y < handler.getYSize(); ++y) {
                int hasToRenderLeftBar = (handler.isBigSlot() && x > 0 ? 1 : 0);
                int hasToRenderRightBar = (handler.isBigSlot() && x < handler.getXSize() - 1 ? 1 : 0);
                int hasToRenderTopBar = (handler.isBigSlot() && y > 0 ? 1 : 0);
                int hasToRenderBottomBar = (handler.isBigSlot() && y < handler.getYSize() - 1 ? 1 : 0);
                container.drawTexturedModalRect(this.getPosX() + 18 * x + container.getGuiLeft() - renderingOffset + hasToRenderLeftBar, this.getPosY() + 18 * y + container.getGuiTop() - renderingOffset + hasToRenderTopBar, (handler.isBigSlot() ? big_slot : small_slot).getKey() + hasToRenderLeftBar, (handler.isBigSlot() ? big_slot : small_slot).getValue() + hasToRenderTopBar, (handler.isBigSlot() ? BIG_SLOT_SIZE : SLOT_SIZE).getKey() - hasToRenderRightBar - hasToRenderLeftBar, (handler.isBigSlot() ? BIG_SLOT_SIZE : SLOT_SIZE).getValue() - hasToRenderBottomBar);
            }
        }
    }

}
