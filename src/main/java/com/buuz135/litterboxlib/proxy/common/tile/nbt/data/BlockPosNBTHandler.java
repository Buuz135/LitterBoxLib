package com.buuz135.litterboxlib.proxy.common.tile.nbt.data;


import com.buuz135.litterboxlib.proxy.common.tile.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class BlockPosNBTHandler implements INBTHandler<BlockPos> {

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return BlockPos.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull BlockPos object) {
        compound.setLong(name, object.toLong());
        return false;
    }

    @Override
    public BlockPos readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, BlockPos current) {
        return compound.hasKey(name) ? BlockPos.fromLong(compound.getLong(name)) : null;
    }
}
