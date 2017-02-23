package com.forgewareinc.elrol.guiElevator.gui;

import com.forgewareinc.elrol.guiElevator.Elevator;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BlockSlot extends Slot{

	public BlockSlot(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}
	
	public boolean isItemValid(ItemStack stack)
    {
		Block block = Block.getBlockFromItem(stack.getItem());
		if(block != null && block.isNormalCube() && !(block instanceof Elevator)){
			return true;
		}
        return false;
    }
	
	public int getSlotStackLimit()
    {
        return 1;
    }
	
	

}
