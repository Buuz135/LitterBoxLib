package com.buuz135.litterboxlib.util;

import net.minecraft.util.EnumFacing;

public class FacingUtils {

    public static Sideness getFacingRelative(EnumFacing relative, EnumFacing facing) {
        if (facing == EnumFacing.UP) return Sideness.TOP;
        if (facing == EnumFacing.DOWN) return Sideness.BOTTOM;
        if (relative == facing) return Sideness.FRONT;
        if (relative == facing.getOpposite()) return Sideness.BACK;
        if (relative == facing.rotateY()) return Sideness.RIGTH;
        if (relative == facing.rotateYCCW()) return Sideness.LEFT;
        return Sideness.BOTTOM;
    }

    public enum Sideness {
        FRONT, BACK, LEFT, RIGTH, TOP, BOTTOM,
    }
}
