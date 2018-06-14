package com.buuz135.litterboxlib.proxy.common.tile.container;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Predicate;

public class PosButton {

    @Getter
    private final int posX;
    @Getter
    private final int posY;
    @Getter
    private final int sizeX;
    @Getter
    private final int sizeY;
    @Getter
    private int id;
    @Getter
    private Predicate<NBTTagCompound> serverPredicate;

    public PosButton(int posX, int posY, int sizeX, int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public PosButton setId(int id) {
        this.id = id;
        return this;
    }

    public PosButton setPredicate(Predicate<NBTTagCompound> serverPredicate) {
        this.serverPredicate = serverPredicate;
        return this;
    }

    public void onButtonClicked(NBTTagCompound information) {
        if (serverPredicate != null) serverPredicate.test(information);
    }
}
