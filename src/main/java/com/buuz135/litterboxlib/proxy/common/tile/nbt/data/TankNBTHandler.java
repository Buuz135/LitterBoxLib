package com.buuz135.litterboxlib.proxy.common.tile.nbt.data;

import com.buuz135.litterboxlib.proxy.common.tile.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

public class TankNBTHandler implements INBTHandler<FluidTank> {

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return FluidTank.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull FluidTank object) {
        compound.setTag(name, object.writeToNBT(new NBTTagCompound()));
        return true;
    }

    @Override
    public FluidTank readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, FluidTank currentValue) {
        if (compound.hasKey(name)) {
            currentValue.readFromNBT(compound.getCompoundTag(name));
            return currentValue;
        }
        return null;
    }
}
