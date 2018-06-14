package com.buuz135.litterboxlib.proxy.common.tile;

import com.buuz135.litterboxlib.proxy.common.block.BlockTileHorizontal;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.tile.container.ButtonHandler;
import com.buuz135.litterboxlib.proxy.common.tile.container.PosButton;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.FaceMode;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.IFacingHandler;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.fluids.MultiTankHandler;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.fluids.PosFluidTank;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.items.MultiInventoryHandler;
import com.buuz135.litterboxlib.proxy.common.tile.container.capability.items.PosInventoryHandler;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntitySided extends TileEntitySaving {

    @Getter
    private MultiInventoryHandler multiInventoryHandler;
    private MultiTankHandler multiTankHandler;
    private ButtonHandler buttonHandler;

    public void addInventory(PosInventoryHandler handler) {
        if (multiInventoryHandler == null) multiInventoryHandler = new MultiInventoryHandler();
        multiInventoryHandler.addInventory(handler);
    }

    public void addTank(PosFluidTank tank) {
        if (multiTankHandler == null) multiTankHandler = new MultiTankHandler();
        multiTankHandler.addTank(tank);
    }

    public void addButton(PosButton buttonAddon) {
        if (buttonHandler == null) buttonHandler = new ButtonHandler();
        buttonHandler.addButton(buttonAddon);
    }

    public void handleButton(int id, NBTTagCompound compound) {
        buttonHandler.clickButton(id, compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && multiInventoryHandler != null && multiInventoryHandler.getCapabilityForSide(facing).getSlots() > 0)
            return true;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && multiTankHandler != null && !multiTankHandler.getCapabilityForSide(facing).isEmpty())
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(multiInventoryHandler.getCapabilityForSide(facing));
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(multiTankHandler.getCapabilityForSide(facing));
        return super.getCapability(capability, facing);
    }

    public List<IGuiAddon> getGuiAddons() {
        List<IGuiAddon> list = new ArrayList<>();
        if (multiInventoryHandler != null) list.addAll(multiInventoryHandler.getGuiAddons());
        if (multiTankHandler != null) list.addAll(multiTankHandler.getGuiAddons());
        if (buttonHandler != null) list.addAll(buttonHandler.getGuiAddons());
        return list;
    }

    public IFacingHandler getHandlerFromName(String string) {
        for (PosInventoryHandler handler : multiInventoryHandler.getHandlers()) {
            if (handler.getName().equalsIgnoreCase(string)) return handler;
        }
        for (PosFluidTank posFluidTank : multiTankHandler.getTanks()) {
            if (posFluidTank.getName().equalsIgnoreCase(string)) return posFluidTank;
        }
        return null;
    }

    @Override
    public void handleClientPacket(String id, NBTTagCompound information) {
        super.handleClientPacket(id, information);
        if (id.equalsIgnoreCase("SIDE_CHANGE")) {
            IFacingHandler handler = getHandlerFromName(information.getString("Name"));
            if (handler != null) {
                EnumFacing facing = EnumFacing.byName(information.getString("Facing"));
                FaceMode mode = FaceMode.values()[information.getInteger("Next")];
                handler.getFacingModes().put(facing, mode);
                updateNeigh();
            }
            markForUpdate();
        }
    }

    public void updateNeigh() {
        this.world.notifyNeighborsOfStateChange(this.pos, this.blockType, true);
        this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(pos), this.world.getBlockState(pos), 3);
    }

    public EnumFacing getFacingDirection() {
        return this.world.getBlockState(pos).getValue(BlockTileHorizontal.FACING);
    }
}
