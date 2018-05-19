package com.buuz135.litterboxlib.proxy.common.client.gui;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import com.buuz135.litterboxlib.proxy.common.tile.container.ContainerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiTile extends GuiContainer {

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(Litterboxlib.MOD_ID, "textures/gui/background.png");

    private final InventoryPlayer playerInv;
    private final ContainerTile containerTile;

    public GuiTile(ContainerTile inventorySlotsIn, InventoryPlayer playerInv) {
        super(inventorySlotsIn);
        this.playerInv = playerInv;
        this.containerTile = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        //BG RENDERING
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //GUI ADDON RENDERING

        for (IGuiAddon iGuiAddon : containerTile.getTile().getGuiAddons()) {
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
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        for (IGuiAddon iGuiAddon : containerTile.getTile().getGuiAddons()) {
            GlStateManager.pushMatrix();
            mc.getTextureManager().bindTexture(BG_TEXTURE);
            iGuiAddon.drawGuiContainerForegroundLayer(this, mouseX, mouseY);
            GlStateManager.popMatrix();
        }
        renderHoveredToolTip(mouseX - x, mouseY - y);
        for (IGuiAddon iGuiAddon : containerTile.getTile().getGuiAddons()) {
            if (iGuiAddon.isInside(this, mouseX, mouseY) && iGuiAddon.getTooltipLines() != null && !iGuiAddon.getTooltipLines().isEmpty()){
                GlStateManager.pushMatrix();
                drawHoveringText(iGuiAddon.getTooltipLines(), mouseX-this.getGuiLeft(), mouseY-this.getGuiTop());
                GlStateManager.popMatrix();
            }
        }
    }

    public void drawColor(int left, int top, int sizeX, int sizeY, int color) {
        this.drawGradientRect(left, top, sizeX + left, sizeY + top, color, color);
    }


}
