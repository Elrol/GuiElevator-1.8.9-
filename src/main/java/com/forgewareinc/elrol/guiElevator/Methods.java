package com.forgewareinc.elrol.guiElevator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.forgewareinc.elrol.guiElevator.network.PacketNaming;
import com.forgewareinc.elrol.guiElevator.network.TeleportPacket;

public class Methods {
	
	public static void dropItem(World world, int x, int y, int z, ItemStack itemstack){
		Random rand = new Random();
		if (itemstack != null){
	        float f = rand.nextFloat() * 0.8F + 0.1F;
	        float f1 = rand.nextFloat() * 0.8F + 0.1F;
	        float f2 = rand.nextFloat() * 0.8F + 0.1F;
	
	        while (itemstack.stackSize > 0)
	        {
	            int j1 = rand.nextInt(21) + 10;
	
	            if (j1 > itemstack.stackSize)
	            {
	                j1 = itemstack.stackSize;
	            }
	
	            itemstack.stackSize -= j1;
	            EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
	
	            if (itemstack.hasTagCompound())
	            {
	                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
	            }
	
	            float f3 = 0.05F;
	            entityitem.motionX = (double)((float)rand.nextGaussian() * f3);
	            entityitem.motionY = (double)((float)rand.nextGaussian() * f3 + 0.2F);
	            entityitem.motionZ = (double)((float)rand.nextGaussian() * f3);
	            world.spawnEntityInWorld(entityitem);
	        }
	    }
	}
	
	public static void dropItems(World world, int x, int y, int z){
		Random rand = new Random();
	    TileEntityElevator entity = (TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z));
	
	    if (entity != null)
	    {
	        for (int i1 = 0; i1 < entity.getSizeInventory(); ++i1)
	        {
	            ItemStack itemstack = entity.getStackInSlot(i1);
	
	            if (itemstack != null)
	            {
	                float f = rand.nextFloat() * 0.8F + 0.1F;
	                float f1 = rand.nextFloat() * 0.8F + 0.1F;
	                float f2 = rand.nextFloat() * 0.8F + 0.1F;
	
	                while (itemstack.stackSize > 0)
	                {
	                    int j1 = rand.nextInt(21) + 10;
	
	                    if (j1 > itemstack.stackSize)
	                    {
	                        j1 = itemstack.stackSize;
	                    }
	
	                    itemstack.stackSize -= j1;
	                    EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
	
	                    if (itemstack.hasTagCompound())
	                    {
	                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
	                    }
	
	                    float f3 = 0.05F;
	                    entityitem.motionX = (double)((float)rand.nextGaussian() * f3);
	                    entityitem.motionY = (double)((float)rand.nextGaussian() * f3 + 0.2F);
	                    entityitem.motionZ = (double)((float)rand.nextGaussian() * f3);
	                    world.spawnEntityInWorld(entityitem);
	                }
	            }
	        }
	    }
	}
	
	public static boolean checkForRedstone(World world, int x, int y, int z){
		boolean isPowered = false;
		Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.UP) > 0)
			isPowered = true;
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.DOWN) > 0)
			isPowered = true;
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.EAST) > 0)
			isPowered = true;
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.WEST) > 0)
			isPowered = true;
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.NORTH) > 0)
			isPowered = true;
		if(block.getWeakPower(world, new BlockPos(x, y, z), block.getDefaultState(), EnumFacing.SOUTH) > 0)
			isPowered = true;
		
		return isPowered;
	}
	
	
	public static BlockPos findElevatorMeta(World world, EntityPlayer player, int x, int y, int z){
		if(world.getBlockState(new BlockPos(x, y+1, z)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x, y+1, z)).getValue(Elevator.meta) == 0)
			return new BlockPos(x, y+1, z);
		if(world.getBlockState(new BlockPos(x, y-2, z)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x, y-2, z)).getValue(Elevator.meta) == 1)
			return new BlockPos(x, y-2, z);
		if(world.getBlockState(new BlockPos(x, y, z+1)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x, y, z+1)).getValue(Elevator.meta) == 2)
			return new BlockPos(x, y, z+1);
		if(world.getBlockState(new BlockPos(x, y, z-1)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x, y, z-1)).getValue(Elevator.meta) == 3)
			return new BlockPos(x, y, z-1);
		if(world.getBlockState(new BlockPos(x+1, y, z)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x+1, y, z)).getValue(Elevator.meta) == 4)
			return new BlockPos(x+1, y, z);
		if(world.getBlockState(new BlockPos(x-1, y, z)).getBlock() instanceof Elevator && world.getBlockState(new BlockPos(x-1, y, z)).getValue(Elevator.meta) == 5)
			return new BlockPos(x-1, y, z);
		return null;
	}
	
	public static void ascendFloor(World world, EntityPlayer player,int x, int y, int z){
		if(findElevatorMeta(world, player, x, y, z) == null){
			System.out.println("No elevators found");
			return;
		}else{
		BlockPos pos = findElevatorMeta(world, player, x, y, z);
		int blockX = pos.getX();
		int blockY = pos.getY();
		int blockZ = pos.getZ();
		Elevator elevator = ((Elevator)world.getBlockState(pos).getBlock());
		System.out.println("Start teleport check");
		for(int a = blockY +1; a < world.getActualHeight(); a++){
			if(world.getBlockState(new BlockPos(blockX, a, blockZ)).getBlock() instanceof Elevator){
				if(Elevator.canUse(player, world, new BlockPos(blockX, a, blockZ)) && isFloorValid(world, blockX, a, blockZ, world.getBlockState(new BlockPos(blockX, a, blockZ)).getValue(Elevator.meta))){
					System.out.println("Teleporting up");
					teleport(world, blockX, a, blockZ, world.getBlockState(new BlockPos(blockX, a, blockZ)).getValue(Elevator.meta));
					return;
				}
			}
		}
		System.out.println("Error");
		return;
		}
	
	}
	
	public static void descendFloor(World world, EntityPlayer player,int x, int y, int z){
		if(findElevatorMeta(world, player, x, y, z) == null){
			System.out.println("No elevators found");
			return;
		}else{
			BlockPos pos = findElevatorMeta(world, player, x, y, z);
			int blockX = pos.getX();
			int blockY = pos.getY();
			int blockZ = pos.getZ();
			Elevator elevator = ((Elevator)world.getBlockState(pos).getBlock());
			System.out.println("Start teleport check");
			for(int a = blockY -1; a > 0; a--){
				if(world.getBlockState(new BlockPos(blockX, a, blockZ)).getBlock() instanceof Elevator){
					if(Elevator.canUse(player, world, new BlockPos(blockX, a, blockZ)) && isFloorValid(world, blockX, a, blockZ, world.getBlockState(new BlockPos(blockX, a, blockZ)).getValue(Elevator.meta))){	System.out.println("Teleporting down");
						teleport(world, blockX, a, blockZ, world.getBlockState(new BlockPos(blockX, a, blockZ)).getValue(Elevator.meta));
						return;
					}
				}
			}
			return;
		}
	}
	public static boolean isFloorValid(World world, int x, int y, int z, int meta){
		if(!Methods.checkForRedstone(world, x, y, z)){
			switch(world.getBlockState(new BlockPos(x,y,z)).getValue(Elevator.meta)){
			case 0:
				if(isEmpty(world, new BlockPos(x, y-1, z)) && isEmpty(world, new BlockPos(x, y-2, z))){
					if(GeneralConfig.preventTraps && world.getBlockState(new BlockPos(x, y-3, z)).getBlock().isNormalCube()){
						if(checkTraps(world.getBlockState(new BlockPos(x, y-1, z)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x, y-2, z)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			case 1:
				if(isEmpty(world, new BlockPos(x, y+1, z)) && isEmpty(world, new BlockPos(x, y+2, z))){
					if(GeneralConfig.preventTraps){
						if(checkTraps(world.getBlockState(new BlockPos(x, y+1, z)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x, y+2, z)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			case 2:
				if(isEmpty(world, new BlockPos(x, y, z-1)) && isEmpty(world, new BlockPos(x, y-1, z-1))){
					if(GeneralConfig.preventTraps && world.getBlockState(new BlockPos(x, y-2, z-1)).getBlock().isNormalCube()){
						if(checkTraps(world.getBlockState(new BlockPos(x, y, z-1)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x, y-1, z-1)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			case 3:
				if(isEmpty(world, new BlockPos(x, y, z+1)) && isEmpty(world, new BlockPos(x, y-1, z+1))){
					if(GeneralConfig.preventTraps && world.getBlockState(new BlockPos(x, y-2, z+1)).getBlock().isNormalCube()){
						if(checkTraps(world.getBlockState(new BlockPos(x, y, z+1)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x, y-1, z+1)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			case 4:
				if(isEmpty(world, new BlockPos(x-1, y, z)) && isEmpty(world, new BlockPos(x-1, y-1, z))){
					if(GeneralConfig.preventTraps && world.getBlockState(new BlockPos(x-1, y-2, z)).getBlock().isNormalCube()){
						if(checkTraps(world.getBlockState(new BlockPos(x-1, y, z)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x-1, y-1, z)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			case 5:
				if(isEmpty(world, new BlockPos(x+1, y, z)) && isEmpty(world, new BlockPos(x+1, y-1, z))){
					if(GeneralConfig.preventTraps && world.getBlockState(new BlockPos(x+1, y-2, z)).getBlock().isNormalCube()){
						if(checkTraps(world.getBlockState(new BlockPos(x+1, y, z)).getBlock()) || checkTraps(world.getBlockState(new BlockPos(x+1, y-1, z)).getBlock())){
							return false;
						}
					}else{
						return true;
					}
					return true;
				}else{
					return false;}
			default: 
				System.out.println("ERROR: METADATA > 5");
				return false;
			}
		}else{
			return false;
		}
		
		
	}
	
	//Returns true if traps exist, false if they dont
	private static boolean checkTraps(Block block){
		for(int i = 0; i < GeneralConfig.trapBlockList.length; i++){
			if(block.getUnlocalizedName().substring(5).equals(GeneralConfig.trapBlockList[i])){
				return true;
			}
		}
		return false;
	}
	
	public static void teleport(World world, int x, int y, int z, int meta){
		if(isFloorValid(world, x, y, z, meta)){
			switch(meta){
				case 0:
					TeleportPacket.send(x, y - 2, z);
					return;
				case 1:
					TeleportPacket.send(x, y + 1, z);
					return;
				case 2:
					TeleportPacket.send(x, y - 1, z - 1);
					return;
				case 3:
					TeleportPacket.send(x, y - 1, z + 1);	
					return;
				case 4:
					TeleportPacket.send(x- 1, y - 1, z);
					return;
				case 5:
					TeleportPacket.send(x + 1, y - 1, z);
					return;
				default: 
					return;
			}
		}
	}
	public static boolean isEmpty(World world, BlockPos pos){
		Block block = world.getBlockState(pos).getBlock();
		if(block.getCollisionBoundingBox(world, pos, block.getDefaultState()) == null){
			return true;
		}
		return false;
	}
	
	public static void nameElevator(World world, BlockPos pos){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		System.out.println("Checking for Elevators");
		for(int a = y; a > 0; --a){
			if(world.getBlockState(new BlockPos(x, a, z)).getBlock() instanceof Elevator){
				TileEntityElevator tileEntity = (TileEntityElevator)world.getTileEntity(new BlockPos(x, a, z));
				if(tileEntity.getName() != null){
					if(tileEntity.getName().startsWith("Floor ")){
						int priorId = 1;
						
						try{priorId = Integer.parseInt(tileEntity.getName().substring(6));
						}catch(Exception e){return;}
						int floorId = priorId+1;
						System.out.println("Floor naming");
						if(world.isRemote)
							ElevatorMain.network.sendToServer(new PacketNaming("Floor " + floorId, x, y, z));
						return;
					}
				}else{
					if(world.isRemote)
						ElevatorMain.network.sendToServer(new PacketNaming("Floor 1", x, y, z));
					return;
				}
					
			}
		}
		for(int b = y; b < world.getActualHeight(); ++b){
			if(world.getBlockState(new BlockPos(x, b, z)).getBlock() instanceof Elevator){
				TileEntityElevator tileEntity = (TileEntityElevator)world.getTileEntity(new BlockPos(x, b, z));
				if(tileEntity.getName() != null){
					if(tileEntity.getName().equals("Floor 1")){
						System.out.println("Basement naming");
						if(world.isRemote)
							ElevatorMain.network.sendToServer(new PacketNaming("Basement 1", x, y, z));
						return;
					}else if(tileEntity.getName().startsWith("Basement ")){
						int priorId = 0;
						
						try{priorId = Integer.parseInt(tileEntity.getName().substring(9));
						}catch(Exception e){return;}
						System.out.println("Basement naming");
						int basementId = priorId+1;
						if(world.isRemote)
							ElevatorMain.network.sendToServer(new PacketNaming("Basement " + (basementId+1), x, y, z));
						return;
					}
				}
			}
		}
		System.out.println("Default naming");
		if(world.isRemote && ((TileEntityElevator)world.getTileEntity(pos)).getName() == "")
			ElevatorMain.network.sendToServer(new PacketNaming("Floor 1", x, y, z));
		return;
	}
}