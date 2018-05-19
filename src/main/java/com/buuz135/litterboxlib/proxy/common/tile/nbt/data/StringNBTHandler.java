package com.buuz135.litterboxlib.proxy.common.tile.nbt.data;

import com.buuz135.litterboxlib.proxy.common.tile.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class StringNBTHandler implements INBTHandler<String> {
    @Override
    public boolean isClassValid(Class<?> aClass) {
        return String.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull String object) {
        compound.setString(name, object);
        return true;
    }

    @Override
    public String readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, String current) {
        return compound.hasKey(name) ? compound.getString(name) : null;
    }
}
