package com.forgewareinc.elrol.guiElevator.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.forgewareinc.elrol.guiElevator.ModInfo;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;
import com.forgewareinc.elrol.guiElevator.network.WhitelistPacket;

public class ElevatorWhitelistGUI extends GuiScreen{
	public static ResourceLocation gui = new ResourceLocation(ModInfo.MODID, "textures/gui/floor_name.png");
	public static int guiWidth = 248;
	public static int guiHeight = 166;
	GuiTextField nameInput;
	
	GuiButton okButton;
	GuiButton cancelButton;
	GuiButton deleteButton;
	GuiButton next;
	GuiButton previous;
	
	public int selected;
	
	public EntityPlayer player;
	
	public int X;
	public int Y;
	public int Z;
	
	public World world;
	
	public TileEntityElevator te;
	public ArrayList<String> whitelist;
	public int pages;
	public int currentPage = 0;
	
	public ElevatorWhitelistGUI (World world, int x, int y, int z, EntityPlayer player){
		this.X = x;
		this.Y = y;
		this.Z = z;
		
		this.player = player;
		this.world = world;
		te = (TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z));
		whitelist = te.getUsernames();
		pages = whitelist.size()/7;
	}
	
	@Override
	public void drawScreen(int x, int y,float tick){
		int guiX = (width-guiWidth)/2;
		int guiY = (height-guiHeight)/2;
		
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(gui);
		drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
		this.nameInput.drawTextBox();
		drawStrings(guiX, guiY);
		//list.drawScreen(x, y, tick);
		super.drawScreen(x, y, tick);
		
	}
	
	public void initGui(){
		int guiX = (width-guiWidth)/2;
		int guiY = (height-guiHeight)/2;
		pages = (whitelist.size()-1)/7;
		
		okButton = new GuiButton(0, guiX+17, guiY+46, 40, 20, "Add");
		cancelButton = new GuiButton(1, guiX+67, guiY+46, 40, 20, "Cancel");
        deleteButton = new GuiButton(2, guiX+17, guiY+68, 40, 20, "Delete");
        deleteButton.enabled = false;
		next = new GuiButton(3,guiX+67, guiY+90, 40, 20,"->");
		previous = new GuiButton(4, guiX+17, guiY+90, 40, 20, "<-");
		next.enabled = false;
		previous.enabled = false;
		if(currentPage < pages)
			next.enabled = true;
		if(currentPage > 0)
			previous.enabled = true;
		
		buttonList.clear();
		nameInput = new GuiTextField(0, this.fontRendererObj, guiX+12, guiY+20, 100, 20);
        nameInput.setMaxStringLength(32);
		nameInput.setFocused(true);
		
		buttonList.add(okButton);
        buttonList.add(cancelButton);
        buttonList.add(deleteButton);
        buttonList.add(next);
        buttonList.add(previous);
        for(int i = (currentPage * 7); i < (currentPage*7)+7; i++){
        	if(i < whitelist.size()){
        		buttonList.add(new GuiButton(i+5, guiX + 128, guiY+12+((i-(currentPage * 7))*20), 100, 20, whitelist.get(i)));
        	}else{
        		break;
        	}
        }
		super.initGui();
	}
	
	public void drawStrings(int guiX, int guiY){
		fontRendererObj.drawString("Floor Whitelist", guiX+8,guiY+8, 0x000000);
	}
	
	protected void actionPerformed(GuiButton button){
		switch(button.id){
		case 0:
			if(!nameInput.getText().trim().equals("")){
				System.out.println("Sending Packet");
				((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).addUsername(this.nameInput.getText().trim());
				((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).markDirty();
				WhitelistPacket.send(this.nameInput.getText().trim(), X, Y, Z);
			}
			
			this.initGui();
			break;
		case 1:
			mc.displayGuiScreen(null);
			break;
		case 2:
			((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).removeUsername(selected-5);
			((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).markDirty();
			WhitelistPacket.send("-delete-" + (selected-5), X, Y, Z);
			this.initGui();
			break;
		case 3:
			++currentPage;
			initGui();
			break;
		case 4:
			--currentPage;
			initGui();
			break;
		default:
			selected = button.id;
			button.enabled = false;
			deleteButton.enabled = true;
			break;
		}
	}
	
	protected void keyTyped(char c, int key){
		this.nameInput.textboxKeyTyped(c, key);
        
		if(!this.nameInput.isFocused()){
			switch(key){
			case Keyboard.KEY_E:
				mc.displayGuiScreen(null);

			case Keyboard.KEY_ESCAPE:
				mc.displayGuiScreen(null);
			}
		}else{
			switch(key){
			case Keyboard.KEY_ESCAPE:
				this.nameInput.setFocused(false);
			}
			
		}
		
	}
	
	public void updateScreen()
    {
        super.updateScreen();
        this.nameInput.updateCursorCounter();
    }
	
	protected void mouseClicked(int x, int y, int btn) {
		this.nameInput.mouseClicked(x, y, btn);
		try {
			super.mouseClicked(x, y, btn);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public void nameBlock(String name){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream dos = new DataOutputStream(bos);
		try{
			dos.writeUTF(name);
		}catch(Exception v){
			v.printStackTrace();
		}
		
	}
	
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
