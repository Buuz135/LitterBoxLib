package com.buuz135.litterboxlib.proxy.common.client.gui.addon.button;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.IClickable;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.network.TileUpdateFromClientMessage;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosButton;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.FaceMode;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.IFacingHandler;
import com.buuz135.litterboxlib.util.FacingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile.BG_TEXTURE;

public class FacingHandlerGuiAddon extends BasicGuiAddon implements IClickable {

    private final IFacingHandler handler;
    private final Rectangle location;
    private boolean clicked;
    private List<StateButtonAddon> buttonAddons;

    public FacingHandlerGuiAddon(Rectangle location, IFacingHandler facingHandler) {
        super(location.x, location.y);
        this.location = location;
        this.handler = facingHandler;
        this.buttonAddons = new ArrayList<>();
    }

    public static Point getPointFromFacing(FacingUtils.Sideness sideness) {
        Point origin = new Point(32, 121);
        switch (sideness) {
            case TOP:
                origin.translate(0, -16);
                break;
            case BOTTOM:
                origin.translate(0, 16);
                break;
            case LEFT:
                origin.translate(-16, 0);
                break;
            case RIGTH:
                origin.translate(16, 0);
                break;
            case BACK:
                origin.translate(16, 16);
                break;
        }
        return origin;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BG_TEXTURE);
        container.drawTexturedModalRect(container.getGuiLeft() + getPosX(), container.getGuiTop() + getPosY(), 1, 213 + 18, 14, 14);
        int offset = 2;
        container.drawColor(container.getGuiLeft() + getPosX() + offset, container.getGuiTop() + getPosY() + offset, getXSize() - offset - 2, getYSize() - offset - 2, handler.getColor());
        if (isClicked()) {
            container.drawTexturedModalRect(container.getGuiLeft() + getPosX(), container.getGuiTop() + getPosY(), 16, 213 + 18, 14, 14);
            container.drawTexturedModalRect(container.getGuiLeft() + 7, container.getGuiTop() + 101, 56, 185, 162, 54);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {
        if (isInside(container, mouseX - container.getGuiLeft(), mouseY - container.getGuiTop()) || isClicked()) {
            container.drawHorizontalLine(handler.getRectangle().x, handler.getRectangle().x + handler.getRectangle().width, handler.getRectangle().y, handler.getColor());
            container.drawHorizontalLine(handler.getRectangle().x, handler.getRectangle().x + handler.getRectangle().width, handler.getRectangle().y + handler.getRectangle().height, handler.getColor());
            container.drawVerticalLine(handler.getRectangle().x, handler.getRectangle().y, handler.getRectangle().y + handler.getRectangle().height, handler.getColor());
            container.drawVerticalLine(handler.getRectangle().x + handler.getRectangle().width, handler.getRectangle().y, handler.getRectangle().y + handler.getRectangle().height, handler.getColor());
        }
    }

    @Override
    public List<String> getTooltipLines() {
        return Arrays.asList(handler.getName());
    }

    @Override
    public int getXSize() {
        return location.width;
    }

    @Override
    public int getYSize() {
        return location.height;
    }

    @Override
    public void handleClick(GuiTile information, int guiX, int guiY, int mouseX, int mouseY) {
        for (IGuiAddon addon : new ArrayList<>(information.getAddonList())) {
            if (addon instanceof FacingHandlerGuiAddon && addon != this) {
                ((FacingHandlerGuiAddon) addon).setClicked(information, false);
            }
        }
        this.setClicked(information, !clicked);
        if (clicked) {
            information.getContainerTile().removeChestInventory();
            EnumFacing relative = information.getContainerTile().getTile().getFacingDirection();
            int i = 0;
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (!handler.getFacingModes().containsKey(facing)) continue;
                FacingUtils.Sideness sideness = FacingUtils.getFacingRelative(relative, facing);
                Point point = getPointFromFacing(sideness);
                StateButtonAddon addon = new StateButtonAddon(new PosButton(point.x, point.y, 14, 14), FaceMode.NONE.getInfo(facing, FacingHandlerGuiAddon.this.handler.getName()), FaceMode.ENABLED.getInfo(facing, FacingHandlerGuiAddon.this.handler.getName()), FaceMode.PULL.getInfo(facing, FacingHandlerGuiAddon.this.handler.getName()), FaceMode.PUSH.getInfo(facing, FacingHandlerGuiAddon.this.handler.getName())) {
                    @Override
                    public int getState() {
                        IFacingHandler handler = information.getContainerTile().getTile().getHandlerFromName(FacingHandlerGuiAddon.this.handler.getName());
                        return handler != null && handler.getFacingModes().containsKey(facing) ? handler.getFacingModes().get(facing).getIndex() : 0;
                    }

                    @Override
                    public void handleClick(GuiTile gui, int guiX, int guiY, int mouseX, int mouseY) {
                        StateButtonInfo info = getStateInfo();
                        if (info != null) {
                            Litterboxlib.NETWORK.sendToServer(new TileUpdateFromClientMessage("SIDE_CHANGE", gui.getContainerTile().getTile().getPos(), info.getCompound()));
                            NBTTagCompound information = info.getCompound();
                            EnumFacing facing = EnumFacing.byName(information.getString("Facing"));
                            FaceMode mode = FaceMode.values()[information.getInteger("Next")];
                            handler.getFacingModes().put(facing, mode);
                            gui.getContainerTile().getTile().updateNeigh();
                        }
                    }

                    @Override
                    public List<String> getTooltipLines() {
                        List<String> strings = new ArrayList<>(super.getTooltipLines());
                        strings.add(sideness.toString());
                        return strings;
                    }
                };
                buttonAddons.add(addon);
                information.getAddonList().add(addon);
                ++i;
            }
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(GuiTile information, boolean clicked) {
        this.clicked = clicked;
        if (!clicked) {
            information.getContainerTile().addPlayerChestInventory();
            information.getAddonList().removeIf(iGuiAddon -> buttonAddons.contains(iGuiAddon));
            buttonAddons.clear();
        }
    }
}
