package com.forgewareinc.elrol.guiElevator.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;

public class TickPacket implements IMessage {

	static int x;
	static int y;
	static int z;
	
	public TickPacket(){}
	
	public TickPacket(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void sendToServer(IMessage packet){
		ElevatorMain.network.sendToServer(packet);
	}
	
	public static void send(int X, int Y, int Z){
		sendToServer(new TickPacket(X, Y, Z));
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

	public static class Handler implements IMessageHandler<TickPacket, IMessage>{

		@Override
		public IMessage onMessage(TickPacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Block block = world.getBlockState(new BlockPos(message.x, message.y, message.z)).getBlock();
			world.scheduleBlockUpdate(new BlockPos(message.x, message.y, message.z), block, block.tickRate(world), 0);
			return null;
		}
		
		
		
	}

}
