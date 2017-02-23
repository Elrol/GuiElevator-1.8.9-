package com.forgewareinc.elrol.guiElevator.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;
import com.forgewareinc.elrol.guiElevator.ModInfo;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;
import com.forgewareinc.elrol.guiElevator.network.PacketNaming;

public class ElevatorNamingGUI extends GuiScreen{
	public static ResourceLocation gui = new ResourceLocation(ModInfo.MODID, "textures/gui/floor_name.png");
	public static int guiWidth = 248;
	public static int guiHeight = 166;
	
	GuiTextField nameInput;
	
	GuiButton okButton;
	GuiButton cancelButton;
	
	public EntityPlayer player;
	
	public int X;
	public int Y;
	public int Z;
	
	public World world;
	 
	public ElevatorNamingGUI (World world, int x, int y, int z, EntityPlayer player){
		this.X = x;
		this.Y = y;
		this.Z = z;
		
		this.player = player;
		this.world = world;
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
		
		super.drawScreen(x, y, tick);
		
	}
	
	public void initGui(){
		int guiX = (width-guiWidth)/2;
		int guiY = (height-guiHeight)/2;
		
		buttonList.clear();
		this.nameInput = new GuiTextField(0, this.fontRendererObj, guiX+74, guiY+32, 100, 20);
        nameInput.setMaxStringLength(32);
		this.nameInput.setFocused(true);
		if(((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).isNamed()){
			this.nameInput.setText(((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).getName());
			System.out.println(((TileEntityElevator)world.getTileEntity(new BlockPos(X, Y, Z))).getName());
		}else{
			this.nameInput.setText("");
		}
        
        buttonList.add(okButton = new GuiButton(Y, guiX+79, guiY+64, 40, 20, "Rename"));
        buttonList.add(cancelButton = new GuiButton(1, guiX+129, guiY+64, 40, 20, "Cancel"));
		super.initGui();
	}
	
	public void drawStrings(int guiX, int guiY){
		fontRendererObj.drawString("Name the floor", guiX+8,guiY+8, 0x000000);
	}
	
	protected void actionPerformed(GuiButton button){
	
		if(button.id == Y){
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("name", this.nameInput.getText().trim());
			tag.setBoolean("named", true);
			System.out.println(Y);
			((TileEntityElevator)world.getTileEntity(new BlockPos(X, button.id, Z))).readFromNBT(tag);
			nameBlock(this.nameInput.getText().trim());
			//NamingPacket.send(X, button.id, Z, this.nameInput.getText().trim(), (byte)0);
			if(world.isRemote){
				ElevatorMain.network.sendToServer(new PacketNaming(this.nameInput.getText().trim(), X, button.id, Z));
			}
			mc.displayGuiScreen(null);
			return ;
		}else{
			mc.displayGuiScreen(null);	
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
	
	protected void mouseClicked(int x, int y, int btn){
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
