package com.buuz135.litterboxlib._test;

import com.buuz135.litterboxlib.annotation.NBTSave;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.BasicButtonAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.PrettyColor;
import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosFluidTank;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosInventoryHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Random;

public class TileEntityTest extends TileEntitySided implements ITickable {

    @NBTSave
    private int randomNumber;
    @NBTSave
    private PosInventoryHandler handler;
    @NBTSave
    private PosInventoryHandler handler2;
    @NBTSave
    private PosInventoryHandler handler3;
    @NBTSave
    private PosFluidTank water;
    @NBTSave
    private PosFluidTank lava;

    public TileEntityTest() {
        randomNumber = 0;
        handler = new PosInventoryHandler("test", 15 + 18 * 3, 20+20+5, 4).setRange(2, 2).setColor(PrettyColor.BLUE).setTile(this).setOutputFilter((stack, integer) -> false);
        addInventory(handler);

        handler2 = new PosInventoryHandler("test2", 20 + 18 * 3, 20, 1).setColor(PrettyColor.YELLOW).setTile(this).setInputFilter((stack, integer) -> stack.isItemEqual(new ItemStack(Blocks.CHEST)));
        addInventory(handler2);

        handler3 = new PosInventoryHandler("test3", 20 + 18 * 5+10, 30, 4).setRange(2, 2).setColor(PrettyColor.YELLOW).setTile(this).setBigSlot().setOutputFilter((stack, integer) -> stack.isItemEqual(new ItemStack(Blocks.STONE)));
        addInventory(handler3);
        water = new PosFluidTank(8000,20,20).setTile(this).setFillFilter(fluidStack -> fluidStack.getFluid().equals(FluidRegistry.WATER));
        this.addTank(water);
        lava = new PosFluidTank(8000,40, 20).setTile(this).setFillFilter(fluidStack -> fluidStack.getFluid().equals(FluidRegistry.LAVA));
        this.addTank(lava);

        this.addButton(new BasicButtonAddon(5,5).setServerRunnable(compound -> {
            System.out.println(randomNumber);
            System.out.println(compound);
            return true;
        }));
    }

    @Override
    public void update() {
        if (world.isRemote) return;
        if (randomNumber == 0) {
            randomNumber = new Random().nextInt();
            this.markForUpdate();
        }
    }


}