package com.buuz135.litterboxlib._test;

import com.buuz135.litterboxlib.annotation.NBTSave;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.PrettyColor;
import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.fluids.PosFluidTank;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.items.PosInventoryHandler;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.work.PosWorkBar;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class TileEntityTest extends TileEntitySided {

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
    @NBTSave
    private PosWorkBar firstBar;
    @NBTSave
    private PosWorkBar secondBar;

    public TileEntityTest() {
        randomNumber = 0;
        handler = new PosInventoryHandler("test", 15, 20 + 5, 4).setRange(2, 2).setColor(PrettyColor.BLUE).setTile(this).setOutputFilter((stack, integer) -> false);
        addInventory(handler);

//        handler2 = new PosInventoryHandler("test2", 20 + 18 * 3, 20, 1).setColor(PrettyColor.YELLOW).setTile(this).setInputFilter((stack, integer) -> stack.isItemEqual(new ItemStack(Blocks.CHEST)));
//        addInventory(handler2);

        handler3 = new PosInventoryHandler("test3", 20 + 18 * 5 + 10, 30, 4).setRange(2, 2).setColor(PrettyColor.RED).setTile(this).setBigSlot().setOutputFilter((stack, integer) -> stack.isItemEqual(new ItemStack(Blocks.STONE)));
        addInventory(handler3);
//        water = new PosFluidTank(8000, 20 + 40, 20, "water").setTile(this).setFillFilter(fluidStack -> fluidStack.getFluid().equals(FluidRegistry.WATER)).setColor(EnumDyeColor.GREEN.getColorValue());
//        this.addTank(water);
//        lava = new PosFluidTank(8000, 40 + 40, 20, "lava").setTile(this).setFillFilter(fluidStack -> fluidStack.getFluid().equals(FluidRegistry.LAVA)).setColor(EnumDyeColor.CYAN.getColorValue());
//        this.addTank(lava);
//
//        PosButton button = new PosButton(10, 10, 16, 16) {
//            @Override
//            public List<? extends IGuiAddon> getGuiAddons() {
//                return Collections.singletonList(new BasicButtonAddon(this));
//            }
//        };
//        this.addButton(button.setPredicate(compound -> {
//            System.out.println("ID: " + button.getId());
//            System.out.println("LAVA:" + lava.getFluidAmount());
//            return true;
//        }));
        firstBar = new PosWorkBar(60, 20, 50).setTickingTime(1).setRunnable(() -> secondBar.increase(this.getWorld())).setColor(EnumDyeColor.CYAN.getColorValue()).setTile(this);
        this.addWorkBar(firstBar);
        secondBar = new PosWorkBar(80, 20, 50).setShouldTickIncrease(false).setRunnable(() -> System.out.println("FULL SECOND BAR")).setColor(EnumDyeColor.LIME.getColorValue()).setTile(this);
        this.addWorkBar(secondBar);
    }

}
