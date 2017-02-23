package com.forgewareinc.elrol.guiElevator.proxy;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;
import com.forgewareinc.elrol.guiElevator.ModInfo;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ElevatorRenderRegistry {

	public static void preInit(){
		
	}
	
	public static void init(){
		reg(ElevatorMain.elevatorBlack, 0, "elevator_black");
		reg(ElevatorMain.elevatorBlack, 1, "elevator_black");
		reg(ElevatorMain.elevatorBlack, 2, "elevator_black");
		reg(ElevatorMain.elevatorBlack, 3, "elevator_black");
		reg(ElevatorMain.elevatorBlack, 4, "elevator_black");
		reg(ElevatorMain.elevatorBlack, 5, "elevator_black");
		
		System.out.println("Initializing Textures");
	}
	
	public static void reg(Block block, int meta, String file) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(ModInfo.MODID + ":" + file, "inventory"));
	}
}
