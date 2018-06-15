package com.buuz135.litterboxlib.proxy.common.tile.container.capability.work;

import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.awt.*;

public class PosWorkBar implements INBTSerializable<NBTTagCompound> {

    @Getter
    private final int posX;
    @Getter
    private final int posY;
    @Setter
    @Getter
    private int progress;
    @Setter
    @Getter
    private int maxProgress;
    @Getter
    private int progressIncrease;
    @Getter
    private boolean shouldTickIncrease;
    @Getter
    private int tickingTime;
    @Getter
    private Runnable runnable;
    private int color;
    private TileEntitySided tile;

    public PosWorkBar(int posX, int posY, int maxProgress) {
        this.posX = posX;
        this.posY = posY;
        this.progress = 0;
        this.maxProgress = maxProgress;
        this.progressIncrease = 1;
        this.tickingTime = 1;
        this.shouldTickIncrease = true;
    }

    public PosWorkBar setProgressIncrease(int progressIncrease) {
        this.progressIncrease = progressIncrease;
        return this;
    }

    public PosWorkBar setShouldTickIncrease(boolean shouldTickIncrease) {
        this.shouldTickIncrease = shouldTickIncrease;
        return this;
    }

    public PosWorkBar setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public PosWorkBar setTickingTime(int tickingTime) {
        this.tickingTime = tickingTime;
        return this;
    }

    public PosWorkBar setTile(TileEntitySided tile) {
        this.tile = tile;
        return this;
    }

    public void increase(World world) {
        if (world.getTotalWorldTime() % tickingTime == 0) {
            this.progress += progressIncrease;
            if (tile != null) tile.markForUpdate();
        }
        if (progress > maxProgress) {
            this.progress = 0;
            if (runnable != null) runnable.run();
        }
    }

    public int getColor() {
        return new Color(this.color).getRGB();
    }

    public PosWorkBar setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Tick", progress);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        progress = nbt.getInteger("Tick");
    }
}
