package com.buuz135.litterboxlib.proxy.common.tile;

import com.buuz135.litterboxlib.proxy.common.client.gui.addon.BasicButtonAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.container.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntitySided extends TileEntitySaving {

    private MultiInventoryHandler multiInventoryHandler;
    private MultiTankHandler multiTankHandler;
    private ButtonHandler buttonHandler;

    public void addInventory(PosInventoryHandler handler) {
        if (multiInventoryHandler == null) multiInventoryHandler = new MultiInventoryHandler();
        multiInventoryHandler.addInventory(handler);
    }

    public void addTank(PosFluidTank tank){
        if (multiTankHandler == null) multiTankHandler = new MultiTankHandler();
        multiTankHandler.addTank(tank);
    }

    public void addButton(BasicButtonAddon buttonAddon){
        if (buttonHandler == null) buttonHandler = new ButtonHandler();
        buttonHandler.addButton(buttonAddon);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && multiInventoryHandler != null) return true;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && multiTankHandler != null) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) multiInventoryHandler;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) multiTankHandler;
        return super.getCapability(capability, facing);
    }

    public List<IGuiAddon> getGuiAddons() {
        List<IGuiAddon> list = new ArrayList<>();
        if (multiInventoryHandler != null) list.addAll(multiInventoryHandler.getGuiAddons());
        if (multiTankHandler != null) list.addAll(multiTankHandler.getGuiAddons());
        if (buttonHandler != null) list.addAll(buttonHandler.getGuiAddons());
        return list;
    }
}
