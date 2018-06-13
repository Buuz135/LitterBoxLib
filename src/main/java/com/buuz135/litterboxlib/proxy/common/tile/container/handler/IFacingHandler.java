package com.buuz135.litterboxlib.proxy.common.tile.container.handler;

import net.minecraft.util.EnumFacing;

import java.util.HashMap;

public interface IFacingHandler {

    HashMap<EnumFacing, FaceMode> getFacingModes();
}
