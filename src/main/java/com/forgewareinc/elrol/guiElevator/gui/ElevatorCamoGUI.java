package com.forgewareinc.elrol.guiElevator.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.forgewareinc.elrol.guiElevator.ContainerTileEntityElevator;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;

public class ElevatorCamoGUI extends GuiContainer{

	private IInventory inv;
	private TileEntityElevator tile;
	private int x, y, z;
	private World world;
	
	public ElevatorCamoGUI(IInventory inv, TileEntityElevator tile, World world, int x, int y, int z) {
		super(new ContainerTileEntityElevator(inv, tile));
		this.world = world;
		this.inv = inv;
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("guielevator:textures/gui/gui_camo.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}
	
	public void onGuiClosed()
    {	
		world.markBlockForUpdate(new BlockPos(x, y, z));
        super.onGuiClosed();
    }
	
	public boolean doesGuiPauseGame()
	{
        return false;
    }
}
