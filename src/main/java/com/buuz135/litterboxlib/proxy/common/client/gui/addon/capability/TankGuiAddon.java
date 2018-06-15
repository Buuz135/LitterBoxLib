package com.buuz135.litterboxlib.proxy.common.client.gui.addon.capability;

import com.buuz135.litterboxlib.proxy.common.client.gui.GuiTile;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.button.BasicGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.fluids.PosFluidTank;
import javafx.util.Pair;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class TankGuiAddon extends BasicGuiAddon {

    private Pair<Integer, Integer> TANK_POS = new Pair<>(177, 1);
    private Pair<Integer, Integer> TANK_SIZE = new Pair<>(18, 46);

    private PosFluidTank tank;

    public TankGuiAddon(PosFluidTank tank) {
        super(tank.getPosX(), tank.getPosY());
        this.tank = tank;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(GuiTile container, float partialTicks, int mouseX, int mouseY) {
        //DRAW FLUID
        if (tank.getFluid() != null) {
            FluidStack stack = tank.getFluid();
            double filledAmount = tank.getFluidAmount() / (double) tank.getCapacity();
            ResourceLocation flowing = stack.getFluid().getFlowing();
            if (flowing != null) {
                TextureAtlasSprite sprite = container.mc.getTextureMapBlocks().getTextureExtry(flowing.toString());
                if (sprite == null) sprite = container.mc.getTextureMapBlocks().getMissingSprite();
                container.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                GlStateManager.enableBlend();
                container.drawTexturedModalRect(this.getPosX() + container.getGuiLeft() + 3, (int) (this.getPosY() + container.getGuiTop() + 3 + (stack.getFluid().isGaseous() ? TANK_SIZE.getValue() - 6 : (TANK_SIZE.getValue() - 6) - (TANK_SIZE.getValue() - 6) * filledAmount)), sprite, TANK_SIZE.getKey() - 6, (int) ((TANK_SIZE.getValue() - 6) * filledAmount));
                GlStateManager.disableBlend();
            }

        }
        container.mc.getTextureManager().bindTexture(GuiTile.BG_TEXTURE);
        container.drawTexturedModalRect(this.getPosX() + container.getGuiLeft(), this.getPosY() + container.getGuiTop(), TANK_POS.getKey(), TANK_POS.getValue(), TANK_SIZE.getKey(), TANK_SIZE.getValue());
    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiTile container, int mouseX, int mouseY) {
        //DRAW OVERLAY
    }

    @Override
    public List<String> getTooltipLines() { ///TODO localize
        return Arrays.asList("Fluid: " + (tank.getFluid() == null ? "Empty" : tank.getFluid().getFluid().getLocalizedName(tank.getFluid())), "Amount: " + new DecimalFormat().format(tank.getFluidAmount()) + "/" + new DecimalFormat().format(tank.getCapacity()) + "mb");
    }

    @Override
    public int getXSize() {
        return TANK_SIZE.getKey();
    }

    @Override
    public int getYSize() {
        return TANK_SIZE.getValue();
    }

}