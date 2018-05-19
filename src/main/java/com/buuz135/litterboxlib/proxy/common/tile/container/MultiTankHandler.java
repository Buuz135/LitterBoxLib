package com.buuz135.litterboxlib.proxy.common.tile.container;

import com.buuz135.litterboxlib.proxy.common.client.gui.IGuiAddonProvider;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.TankGuiAddon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MultiTankHandler implements IFluidHandler, IGuiAddonProvider{

    private List<PosFluidTank> tanks;

    public MultiTankHandler() {
        tanks = new ArrayList<>();
    }

    public void addTank(PosFluidTank tank){
        this.tanks.add(tank);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return (IFluidTankProperties[]) tanks.stream().map(FluidTank::getTankProperties).toArray();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null) return 0;
        for (PosFluidTank tank : tanks){
            if (tank.canFill() && tank.canFillFluidType(resource) && tank.fill(resource, false) != 0) return tank.fill(resource, doFill);
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null) return null;
        for (PosFluidTank tank : tanks){
            if (tank.canDrain() && tank.canDrainFluidType(resource) && tank.drain(resource, false) != null) return tank.drain(resource, doDrain);
        }
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        for (PosFluidTank tank : tanks){
            if (tank.canDrain() && tank.drain(maxDrain, false) != null) return tank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public List<IGuiAddon> getGuiAddons() {
        List<IGuiAddon> addons = new ArrayList<>();
        for (PosFluidTank tank : tanks) {
            addons.add(new TankGuiAddon(tank));
        }
        return addons;
    }
}
