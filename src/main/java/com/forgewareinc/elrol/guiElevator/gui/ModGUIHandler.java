package com.forgewareinc.elrol.guiElevator.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.forgewareinc.elrol.guiElevator.ContainerTileEntityElevator;
import com.forgewareinc.elrol.guiElevator.ModInfo;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;

public class ModGUIHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case ModInfo.CAMO_GUI: return new ContainerTileEntityElevator(player.inventory, (TileEntityElevator)world.getTileEntity(new BlockPos(x,y,z)));
		default: return null;
		}
	}

	
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case ModInfo.FLOOR_GUI: return new FloorSelectGUI(world, x, y, z, player);
		case ModInfo.RENAME_GUI: return new ElevatorNamingGUI(world, x, y, z, player);
		case ModInfo.CAMO_GUI: return new ElevatorCamoGUI(player.inventory, (TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z)), world, x, y, z);
		case ModInfo.ADV_GUI: return new AdvElevatorGUI(world, x, y, z, player);
		case ModInfo.WHITELIST_GUI: return new ElevatorWhitelistGUI(world, x, y, z, player);
		default: return null;
		}
	}

}
