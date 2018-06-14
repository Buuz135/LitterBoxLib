package com.buuz135.litterboxlib.proxy.common.client.gui;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import com.buuz135.litterboxlib.proxy.common.tile.container.ContainerTile;
import lombok.Getter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiTile extends GuiContainer {

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(Litterboxlib.MOD_ID, "textures/gui/background.png");

    private final InventoryPlayer playerInv;
    @Getter
    private final ContainerTile containerTile;
    @Getter
    private List<IGuiAddon> addonList;

    private int x;
    private int y;

    public GuiTile(ContainerTile inventorySlotsIn, InventoryPlayer playerInv) {
        super(inventorySlotsIn);
        this.playerInv = playerInv;
        this.containerTile = inventorySlotsIn;
        this.ySize = 184;
        this.addonList = containerTile.getTile().getGuiAddons();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        //BG RENDERING
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //GUI ADDON RENDERING

        for (IGuiAddon iGuiAddon : addonList) {
            GlStateManager.pushMatrix();
            iGuiAddon.drawGuiContainerBackgroundLayer(this, partialTicks, mouseX, mouseY);
            GlStateManager.popMatrix();
        }
        //TESTING STUFF
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        TileEntitySided sided = ((ContainerTile) this.inventorySlots).getTile();
        String name = new TextComponentTranslation(sided.getWorld().getBlockState(sided.getPos()).getBlock().getUnlocalizedName()).getFormattedText();
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        //fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        for (IGuiAddon iGuiAddon : addonList) {
            GlStateManager.pushMatrix();
            mc.getTextureManager().bindTexture(BG_TEXTURE);
            iGuiAddon.drawGuiContainerForegroundLayer(this, mouseX, mouseY);
            GlStateManager.popMatrix();
        }
        renderHoveredToolTip(mouseX - x, mouseY - y);
        for (IGuiAddon iGuiAddon : addonList) {
            if (iGuiAddon.isInside(this, mouseX - x, mouseY - y) && iGuiAddon.getTooltipLines() != null && !iGuiAddon.getTooltipLines().isEmpty()) {
                GlStateManager.pushMatrix();
                drawHoveringText(iGuiAddon.getTooltipLines(), mouseX - this.getGuiLeft(), mouseY - this.getGuiTop());
                GlStateManager.popMatrix();
            }
        }
    }

    public void drawColor(int left, int top, int sizeX, int sizeY, int color) {
        this.drawGradientRect(left, top, sizeX + left, sizeY + top, color, color);
    }

    @Override
    public void drawHorizontalLine(int startX, int endX, int y, int color) {
        super.drawHorizontalLine(startX, endX, y, color);
    }

    @Override
    public void drawVerticalLine(int x, int startY, int endY, int color) {
        super.drawVerticalLine(x, startY, endY, color);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        new ArrayList<>(addonList).stream().filter(iGuiAddon -> iGuiAddon instanceof IClickable && iGuiAddon.isInside(this, mouseX - x, mouseY - y))
                .forEach(iGuiAddon -> ((IClickable) iGuiAddon).handleClick(this, x, y, mouseX, mouseY));
    }

    public void drawSelectingOverlay(int x, int y, int width, int height) {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        drawRect(x, y, width, height, -2130706433);
        GlStateManager.enableLighting();
        GlStateManager.disableDepth();
    }

}
