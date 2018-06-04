package com.buuz135.litterboxlib.proxy.common.client.gui;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGuiInformation {

    BlockPos getBlockPos();

    World getWorld();

}
