package com.forgewareinc.elrol.guiElevator.gui;

import org.lwjgl.opengl.GL11;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;
import com.forgewareinc.elrol.guiElevator.ModInfo;
import com.forgewareinc.elrol.guiElevator.network.OpenInventoryPacket;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AdvElevatorGUI extends GuiScreen{

	public static ResourceLocation gui = new ResourceLocation(ModInfo.MODID, "textures/gui/gui_adv.png");
	public static int guiWidth = 88;
	public static int guiHeight = 88;
	
	GuiButton rename;
	GuiButton camo;
	GuiButton whitelist;
	
	private EntityPlayer player;
	
	private int x;
	private int y;
	private int z;
	
	private World world;
	
	public AdvElevatorGUI(World world, int x, int y, int z, EntityPlayer player){
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.player = player;
	}
	
	public void drawScreen(int x, int y, float tick){
		int guiX = (width - guiWidth)/2;
		int guiY = (height - guiHeight)/2;
		
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(gui);
		drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
		
		super.drawScreen(x, y, tick);
	}
	
	public void initGui(){
		int guiX = (width-guiWidth)/2;
		int guiY = (height-guiHeight)/2;
		
		buttonList.clear();
		buttonList.add(rename = new GuiButton(0, guiX+11, guiY+11, 64, 20, "Rename"));
        buttonList.add(camo = new GuiButton(1, guiX+11, guiY+33, 64, 20, "Camouflage"));
        buttonList.add(whitelist = new GuiButton(2, guiX+11, guiY+55 , 64, 20, "Whitelist"));
        
        camo.enabled = false;
		super.initGui();
	}
	
	protected void actionPerformed(GuiButton button){
		switch(button.id){
		case 0: player.openGui(ElevatorMain.instance, ModInfo.RENAME_GUI, world, x, y, z); break;
		case 1: 
			if(world.isRemote){
				player.closeScreen();
				OpenInventoryPacket.send(x, y, z);
			}
			break;
		case 2: player.openGui(ElevatorMain.instance, ModInfo.WHITELIST_GUI, world, x, y, z); break;
			default: break;
				
		}
	}
	
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
