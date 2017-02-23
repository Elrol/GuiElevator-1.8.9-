package com.forgewareinc.elrol.guiElevator;

import com.forgewareinc.elrol.guiElevator.gui.BlockSlot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTileEntityElevator extends Container{

	private TileEntityElevator tile;
	
	public ContainerTileEntityElevator(IInventory playerInv, TileEntityElevator tile){
		this.tile = tile;
		
		// Tile Entity, Slot 0. Slot IDs 0
	    this.addSlotToContainer(new BlockSlot(tile, 0, 62 + 18, 17 + 18));
	    
	    // Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_){
        return null;
    }
	
	
}
