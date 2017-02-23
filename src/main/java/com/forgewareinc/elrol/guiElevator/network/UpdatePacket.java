package com.forgewareinc.elrol.guiElevator.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.forgewareinc.elrol.guiElevator.Elevator;
import com.forgewareinc.elrol.guiElevator.ElevatorMain;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;

public class UpdatePacket implements IMessage {

	static int x;
	static int y;
	static int z;
	
	public UpdatePacket(){}
	
	public UpdatePacket(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void sendToServer(IMessage packet){
		ElevatorMain.network.sendToServer(packet);
	}
	
	public static void send(int X, int Y, int Z){
		sendToServer(new UpdatePacket(X, Y, Z));
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<UpdatePacket, IMessage>{

		@Override
		public IMessage onMessage(UpdatePacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			String name = ((TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z))).getName();
			int meta = world.getBlockState(new BlockPos(message.x, message.y, message.z)).getValue(Elevator.meta);
			world.setBlockState(new BlockPos(message.x, message.y, message.z), ElevatorMain.elevatorAdv.getDefaultState().withProperty(Elevator.meta, Integer.valueOf(meta)));
			TileEntityElevator tile = (TileEntityElevator)world.getTileEntity(new BlockPos(x, y, z));
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			if(!player.capabilities.isCreativeMode){
				--player.getCurrentEquippedItem().stackSize; 	
			} 
			tile.setAdv(true);
			tile.setName(name);
			tile.markDirty();
			return null;
		}
		
		
		
	}

}
