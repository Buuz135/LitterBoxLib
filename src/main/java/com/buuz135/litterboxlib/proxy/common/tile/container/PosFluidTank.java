package com.buuz135.litterboxlib.proxy.common.tile.container;

import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySaving;
import lombok.Getter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class PosFluidTank extends FluidTank implements IFluidTankProperties {

    @Getter private final int posX;
    @Getter private final int posY;
    private Predicate<FluidStack> fillPredicate;
    private Predicate<FluidStack> drainPredicate;

    public PosFluidTank(int amount, int posX, int posY) {
        super(amount);
        this.posX = posX;
        this.posY = posY;
        this.fillPredicate = fluidStack1 -> true;
        this.drainPredicate = fluidStack1 -> true;
    }

    public PosFluidTank setFillFilter(Predicate<FluidStack> filter){
        this.fillPredicate = filter;
        return this;
    }

    public PosFluidTank setDrainFilter(Predicate<FluidStack> filter){
        this.drainPredicate = filter;
        return this;
    }

    public PosFluidTank setTile(TileEntity tile){
        this.tile = tile;
        return this;
    }

    @Nullable
    @Override
    public FluidStack getContents() {
        return getFluid();
    }

    @Override
    public boolean canFillFluidType(FluidStack fluidStack) {
        return fluidStack != null && fillPredicate.test(fluidStack);
    }

    @Override
    public boolean canDrainFluidType(FluidStack fluidStack) {
        return fluidStack != null && drainPredicate.test(fluidStack);
    }

    @Override
    protected void onContentsChanged() {
        super.onContentsChanged();
        if (tile instanceof TileEntitySaving){
            ((TileEntitySaving) tile).markForUpdate();
        }else {
            tile.markDirty();
        }
    }
}
