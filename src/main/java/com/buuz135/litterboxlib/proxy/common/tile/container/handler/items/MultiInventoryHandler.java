package com.buuz135.litterboxlib.proxy.common.tile.container.handler.items;

import com.buuz135.litterboxlib.proxy.common.client.gui.IGuiAddonProvider;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.SlotsGuiAddon;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class MultiInventoryHandler implements IGuiAddonProvider {

    private final LinkedHashMap<String, PosInventoryHandler> inventoryHandlers;

    public MultiInventoryHandler() {
        inventoryHandlers = new LinkedHashMap<>();
    }

    public void addInventory(PosInventoryHandler handler) {
        inventoryHandlers.put(handler.getName(), handler);
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        for (String key : inventoryHandlers.keySet()) {
            compound.setTag(key, inventoryHandlers.get(key).serializeNBT());
        }
        return compound;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            if (inventoryHandlers.containsKey(key)) {
                PosInventoryHandler handler = inventoryHandlers.get(key);
                handler.deserializeNBT(nbt.getCompoundTag(key));
            }
        }
    }

    public MultiInvCapabilityHandler getCapabilityForSide(EnumFacing facing) {
        List<PosInventoryHandler> handlers = new ArrayList<>();
        for (PosInventoryHandler posInventoryHandler : inventoryHandlers.values()) {
            if (posInventoryHandler.getFacingModes().containsKey(facing) && posInventoryHandler.getFacingModes().get(facing).isAllowsConnection())
                handlers.add(posInventoryHandler);
        }
        return new MultiInvCapabilityHandler(handlers);
    }

    public Collection<PosInventoryHandler> getHandlers() {
        return inventoryHandlers.values();
    }

    @Override
    public List<IGuiAddon> getGuiAddons() {
        List<IGuiAddon> addons = new ArrayList<>();
        for (PosInventoryHandler handler : inventoryHandlers.values()) {
            addons.add(new SlotsGuiAddon(handler));
        }
        return addons;
    }

    public static class MultiInvCapabilityHandler extends ItemStackHandler {

        private final List<PosInventoryHandler> inventoryHandlers;
        private int slotAmount;

        public MultiInvCapabilityHandler(List<PosInventoryHandler> inventoryHandlers) {
            this.inventoryHandlers = inventoryHandlers;
            this.slotAmount = 0;
            for (PosInventoryHandler inventoryHandler : this.inventoryHandlers) {
                slotAmount += inventoryHandler.getSlots();
            }
        }

        @Override
        public int getSlots() {
            return slotAmount;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            PosInventoryHandler handler = getFromSlot(slot);
            if (handler != null) {
                return handler.insertItem(getRelativeSlot(handler, slot), stack, simulate);
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            PosInventoryHandler handler = getFromSlot(slot);
            if (handler != null) {
                int relativeSlot = getRelativeSlot(handler, slot);
                if (!handler.getExtractPredicate().test(handler.getStackInSlot(relativeSlot), relativeSlot))
                    return ItemStack.EMPTY;
                return handler.extractItem(relativeSlot, amount, simulate);
            }
            return super.extractItem(slot, amount, simulate);
        }

        @Nonnull
        @Override
        public ItemStack getStackInSlot(int slot) {
            PosInventoryHandler handler = getFromSlot(slot);
            if (handler != null) {
                return handler.getStackInSlot(getRelativeSlot(handler, slot));
            }
            return super.getStackInSlot(slot);
        }

        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            PosInventoryHandler handler = getFromSlot(slot);
            if (handler != null) {
                handler.setStackInSlot(getRelativeSlot(handler, slot), stack);
            }
            super.setStackInSlot(slot, stack);
        }

        @Override
        protected void validateSlotIndex(int slot) {
            if (slot < 0 || slot >= slotAmount)
                throw new RuntimeException("Slot " + slot + " not in valid range - [0," + slotAmount + ")");
        }

        public PosInventoryHandler getFromSlot(int slot) {
            for (PosInventoryHandler handler : inventoryHandlers) {
                slot -= handler.getSlots();
                if (slot < 0) {
                    return handler;
                }
            }
            return null;
        }

        public int getRelativeSlot(PosInventoryHandler handler, int slot) {
            for (PosInventoryHandler h : inventoryHandlers) {
                if (h.equals(handler)) return slot;
                slot -= h.getSlots();
            }
            return 0;
        }
    }
}
