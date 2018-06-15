package com.buuz135.litterboxlib.proxy.common.tile.nbt.data;

import com.buuz135.litterboxlib.proxy.common.tile.container.capability.work.PosWorkBar;
import com.buuz135.litterboxlib.proxy.common.tile.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class WorkBarNBTHandler implements INBTHandler<PosWorkBar> {
    @Override
    public boolean isClassValid(Class<?> aClass) {
        return aClass.equals(PosWorkBar.class);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull PosWorkBar object) {
        compound.setTag(name, object.serializeNBT());
        return true;
    }

    @Override
    public PosWorkBar readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, PosWorkBar currentValue) {
        if (compound.hasKey(name)) {
            currentValue.deserializeNBT(compound.getCompoundTag(name));
            return currentValue;
        }
        return null;
    }
}
