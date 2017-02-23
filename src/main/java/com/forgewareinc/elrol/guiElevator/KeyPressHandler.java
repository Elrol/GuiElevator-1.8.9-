package com.forgewareinc.elrol.guiElevator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyPressHandler
{
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent event){
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = player.worldObj;
	    int blockX = MathHelper.floor_double(player.posX);
	    int blockY = MathHelper.floor_double(player.posY-0.2D - (double)player.height)+3;
	    int blockZ = MathHelper.floor_double(player.posZ);
	    if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){
	    	Methods.descendFloor(world, player, blockX, blockY, blockZ);
	    	return;
	    }else if(Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()){
	    	Methods.ascendFloor(world, player, blockX, blockY, blockZ);
	    	return;
	    }
	
	}
}