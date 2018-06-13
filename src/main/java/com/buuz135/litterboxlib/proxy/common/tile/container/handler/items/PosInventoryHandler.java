package com.buuz135.litterboxlib.proxy.common.tile.container.handler.items;

import com.buuz135.litterboxlib.proxy.common.client.gui.addon.PrettyColor;
import com.buuz135.litterboxlib.proxy.common.tile.container.handler.FaceMode;
import com.buuz135.litterboxlib.proxy.common.tile.container.handler.IFacingHandler;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.function.BiPredicate;

public class PosInventoryHandler extends ItemStackHandler implements IFacingHandler {

    @Getter private final String name;
    @Getter private final int xPos;
    @Getter private final int yPos;
    @Getter private int xSize;
    @Getter private int ySize;
    @Getter private TileEntity tileEntity;
    @Getter private boolean colorEnabled;
    @Getter private int color;
    @Getter private boolean isBigSlot;
    @Getter private BiPredicate<ItemStack, Integer> insertPredicate;
    @Getter
    private BiPredicate<ItemStack, Integer> extractPredicate;
    private HashMap<EnumFacing, FaceMode> facingModes;

    public PosInventoryHandler(String name, int xPos, int yPos, int size) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.colorEnabled = false;
        this.setSize(size);
        this.setRange(size, 1);
        this.insertPredicate = (stack, integer) -> true;
        this.extractPredicate = (stack, integer) -> true;
        this.facingModes = new HashMap<>();
        for (EnumFacing facing : EnumFacing.values()) {
            this.facingModes.put(facing, FaceMode.ENABLED);
        }
    }

    public PosInventoryHandler setRange(int x, int y) {
        this.xSize = x;
        this.ySize = y;
        return this;
    }

    public PosInventoryHandler setTile(TileEntity tile) {
        this.tileEntity = tile;
        return this;
    }

    public PosInventoryHandler setColor(PrettyColor color) {
        this.color = color.getColor().getRGB();
        this.colorEnabled = true;
        return this;
    }

    public PosInventoryHandler setBigSlot() {
        this.isBigSlot = true;
        return this;
    }

    public PosInventoryHandler setInputFilter(BiPredicate<ItemStack, Integer> predicate){
        this.insertPredicate = predicate;
        return this;
    }

    public PosInventoryHandler setOutputFilter(BiPredicate<ItemStack, Integer> predicate){
        this.extractPredicate = predicate;
        return this;
    }

    public PosInventoryHandler setFaceMode(EnumFacing facing, FaceMode faceMode) {
        this.facingModes.put(facing, faceMode);
        return this;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (this.tileEntity != null) tileEntity.markDirty();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!insertPredicate.test(stack, slot)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        NBTTagCompound compound = new NBTTagCompound();
        for (EnumFacing facing : facingModes.keySet()) {
            compound.setString(facing.getName(), facingModes.get(facing).name());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        if (nbt.hasKey("FacingModes")) {
            NBTTagCompound compound = nbt.getCompoundTag("FacingModes");
            for (String face : compound.getKeySet()) {
                facingModes.put(EnumFacing.byName(face), FaceMode.valueOf(compound.getString(face)));
            }
        }
    }

    @Override
    public HashMap<EnumFacing, FaceMode> getFacingModes() {
        return facingModes;
    }
}
