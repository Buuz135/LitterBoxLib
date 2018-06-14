package com.buuz135.litterboxlib.proxy.common.tile.container;

import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import com.buuz135.litterboxlib.proxy.common.tile.container.handler.items.PosInventoryHandler;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;


public class ContainerTile<T extends TileEntitySided> extends Container {

    @Getter
    private T tile;
    private InventoryPlayer player;
    private boolean hasInventory;

    public ContainerTile(InventoryPlayer player, T tile) {
        this.tile = tile;
        this.player = player;
        for (PosInventoryHandler handler : tile.getMultiInventoryHandler().getHandlers()) {
            int i = 0;
            for (int y = 0; y < handler.getYSize(); ++y) {
                for (int x = 0; x < handler.getXSize(); ++x) {
                    addSlotToContainer(new SlotItemHandler(handler, i, handler.getXPos() + x * 18, handler.getYPos() + y * 18));
                    ++i;
                }
            }
        }
        createPlayerInventory();
    }

    private void createPlayerInventory() {
        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(player, k, 8 + k * 18, 142 + 18));
        }
        addPlayerChestInventory();
    }

    public void addPlayerChestInventory() {
        if (hasInventory) return;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 18 + 84 + i * 18));
            }
        }
        hasInventory = true;
    }

    public void removeChestInventory() {
        this.inventorySlots.removeIf(slot -> slot.getSlotIndex() >= 9 && slot.getSlotIndex() < 9 + 3 * 9);
        hasInventory = false;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
