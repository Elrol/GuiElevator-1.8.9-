package com.forgewareinc.elrol.guiElevator;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.forgewareinc.elrol.guiElevator.network.ColoringPacket;
import com.forgewareinc.elrol.guiElevator.network.PacketNaming;
import com.forgewareinc.elrol.guiElevator.network.UpdatePacket;
import com.forgewareinc.elrol.guiElevator.network.WhitelistPacket;

public class Elevator extends BlockContainer {

	public static final PropertyInteger meta = PropertyInteger.create("meta", 0, 5);
	private static String color;
	public static String[] colors = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lblue", "magenta", "orange", "white"};
	public static String[] dyes = new String[]{"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	
	public Elevator(String name, CreativeTabs tab, float hardness, float resistance, SoundType stepSound, String color) {
		super(Material.iron);
		this.setUnlocalizedName(name + "_" + color);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setCreativeTab(tab);
		this.setStepSound(stepSound);
		this.color = color;
		GameRegistry.registerBlock(this, name + "_" + color);
	}
	
	public Elevator(String name, CreativeTabs tab, float hardness, float resistance, SoundType stepSound) {
		super(Material.iron);
		this.setUnlocalizedName(name + "_adv");
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setCreativeTab(tab);
		this.setStepSound(stepSound);
		GameRegistry.registerBlock(this, name + "_adv");
	}
	
	protected BlockState createBlockState(){
		return new BlockState(this, new IProperty[]{meta});
	}
	
	public boolean isNormalCube()
    {
        return true;
    }
	
	public boolean isOpaqueCube(){
			return true;
	}
	
	public int getRenderType()
    {
        return 3;
    }
	
	   public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		   int metadata = world.getBlockState(pos).getValue(Elevator.meta);
		   return world.getBlockState(pos).withProperty(Elevator.meta, Integer.valueOf(metadata));
	   }
	   public int getMetaFromState(IBlockState state) {
		   return state.getValue(Elevator.meta);
	   }
	   public IBlockState getStateFromMeta(int meta) {
		   return this.getDefaultState().withProperty(Elevator.meta, meta);
	   }
	
	/*@Override
	public boolean canRenderInPass(int pass){
		ClientProxy.renderPass = pass;
		return true;
	}*/
	
	public String getDye(){
		return "dye" + color.substring(0, 1).toUpperCase() + color.substring(1);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		ItemStack stack = player.inventory.getCurrentItem();
			if(canUse(player, world, pos)){
				if(player.isSneaking()){
					if(world.getBlockState(pos).getBlock().equals(ElevatorMain.elevatorAdv)){
						if(world.isRemote)
							player.openGui(ElevatorMain.instance, ModInfo.ADV_GUI, world, pos.getX(), pos.getY(), pos.getZ());
						return true;
					}else{
						player.openGui(ElevatorMain.instance, ModInfo.RENAME_GUI, world,pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
				}else{
					if(stack != null){
						if(stack.getItem() == Items.stick && world.isRemote){
							TileEntityElevator te = (TileEntityElevator)world.getTileEntity(pos);
							player.addChatComponentMessage(new ChatComponentText("Current Players Whitelisted on this floor:"));
							for(int i = 0; i < te.getUsernames().size(); i++){
								player.addChatComponentMessage(new ChatComponentText(te.getUsernames().get(i)));
							}
							return true;
						}else if(stack.getItem() == Items.diamond && world.getBlockState(pos).getBlock() != ElevatorMain.elevatorAdv){
							UpdatePacket.send(pos.getX(), pos.getY(), pos.getZ());
							WhitelistPacket.send(player.getDisplayName().toString(), pos.getX(), pos.getY(), pos.getZ());
							return false;
						}else if(getOres(stack).contains("dye") && world.getBlockState(pos).getBlock() != ElevatorMain.elevatorAdv){
							for(int i = 0; i < dyes.length; i++){
								if(getOres(stack).contains(dyes[i]) && !((Elevator)world.getBlockState(pos).getBlock()).getDye().equalsIgnoreCase(colors[i]) ){
									dyeBlock(world, pos, i);
									if(!player.capabilities.isCreativeMode){
										--player.getCurrentEquippedItem().stackSize; 	
									}
									return false;
								}
							}
						}else{
							
						}
					}
					player.openGui(ElevatorMain.instance, ModInfo.FLOOR_GUI, world, pos.getX(), pos.getY(), pos.getZ());
					return true;
				}
			}else{
				if(!world.isRemote)player.addChatComponentMessage(new ChatComponentText("You do not have permission to use this"));
				
			}
			return true;
		
		
    }

	public static ArrayList<String> getOres(ItemStack stack){
		int[] ores = OreDictionary.getOreIDs(stack);
		ArrayList<String> ore = new ArrayList<String>();
		for(int i = 0; i < ores.length; i++){
			ore.add(OreDictionary.getOreName(ores[i]));
		}
		return ore;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityElevator();
	}
	
	public boolean hasTileEntity(int meta){
		return true;
	}
	
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {	
		
	
    }
	
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player)
    {
        return this.getDefaultState().withProperty(Elevator.meta, Elevator.determineOrientation(world, pos, player, facing));
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
    {
        if(world.getTileEntity(pos) instanceof TileEntityElevator){
			Elevator block = (Elevator)Block.getBlockFromItem(stack.getItem());
			TileEntityElevator tileElevator = (TileEntityElevator)world.getTileEntity(pos);
			if(block == ElevatorMain.elevatorAdv){
				tileElevator.setAdv(true);
				if(!world.isRemote)System.out.println("true");
			}
			if(world.isRemote){
				System.out.println("naming");
				Methods.nameElevator(world, pos);
			}
        }
    }
	
	public void HandlePacket(EntityPlayer player, int x, int y, int z, String value, byte extra){
		TileEntity te = player.worldObj.getTileEntity(new BlockPos(x, y, z));
		if(te != null && te instanceof TileEntityElevator){
			((TileEntityElevator)te).setName(value);
			((TileEntityElevator)te).markDirty();;
		}
	}
	
	public void dyeBlock(World world, BlockPos pos, int dye){
		TileEntityElevator tile = (TileEntityElevator)world.getTileEntity(pos);
		String name = tile.getName();
		IBlockState state = world.getBlockState(pos);
		if(world.isRemote){
			ColoringPacket.send(pos.getX(), pos.getY(), pos.getZ(), dye, state.getValue(meta));
			ElevatorMain.network.sendToServer(new PacketNaming(name, pos.getX(), pos.getY(), pos.getZ()));
		}
	}
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ElevatorMain.elevatorAdv){
			return Item.getItemFromBlock(ElevatorMain.elevatorWhite);
		}
        return Item.getItemFromBlock(this);
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
	    int x = pos.getX();
	    int y = pos.getY();
	    int z = pos.getZ();
		
		TileEntityElevator te = (TileEntityElevator) world.getTileEntity(pos);
	    Methods.dropItems(world, x, y, z);
	    if(te.isAdv()){
	    	Methods.dropItem(world, x, y, z, new ItemStack(Items.diamond, 1, 0));
	    }
	    super.breakBlock(world, pos, state);
	}
	
	public static int determineOrientation(World world, BlockPos pos, EntityLivingBase player, EnumFacing facing)
    {
		if (MathHelper.abs((float)player.posX - (float)pos.getX()) < 2.0F && MathHelper.abs((float)player.posZ - (float)pos.getZ()) < 2.0F)
        {
            double d0 = player.posY + (double)player.getEyeHeight();

            if (d0 - (double)pos.getY() > 2.0D)
            {
                return EnumFacing.UP.getOpposite().getIndex();
            }

            if ((double)pos.getY() - d0 > 0.0D)
            {
                return EnumFacing.DOWN.getOpposite().getIndex();
            }
        }

        return player.getHorizontalFacing().getOpposite().getIndex();
    }
	
	public static boolean canUse(EntityPlayer player, World world, BlockPos pos){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		Elevator elevator = (Elevator)world.getBlockState(new BlockPos(x, y, z)).getBlock();
		TileEntityElevator tile = (TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z));
		if(elevator.equals(ElevatorMain.elevatorAdv) && tile.isAdv()){
			if(player.capabilities.isCreativeMode){
				return true;
			}
			if(GeneralConfig.whitelistProtection){
				if(GeneralConfig.permissionProtection && tile.getUsernames().contains(player.getDisplayName())){
					return player.capabilities.allowEdit;
				}
				return tile.getUsernames().contains(player.getDisplayName());
			}else{
				if(GeneralConfig.permissionProtection){
					return player.capabilities.allowEdit;
				}else{
					return true;
				}
			}
		}else{
			return true;
		}
	}
	
}
