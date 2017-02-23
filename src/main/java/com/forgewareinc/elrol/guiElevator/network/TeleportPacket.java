package com.forgewareinc.elrol.guiElevator.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;

public class TeleportPacket implements IMessage {

	static int x;
	static int y;
	static int z;
	
	public TeleportPacket(){}
	
	public TeleportPacket(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void sendToServer(IMessage packet){
		ElevatorMain.network.sendToServer(packet);
	}
	
	public static void send(int X, int Y, int Z){
		sendToServer(new TeleportPacket(X, Y, Z));
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

	public static class Handler implements IMessageHandler<TeleportPacket, IMessage>{

		@Override
		public IMessage onMessage(TeleportPacket message, MessageContext ctx) {
			System.out.println("Teleporting?");
			if(ctx.getServerHandler().playerEntity instanceof EntityPlayer){
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				player.mountEntity((Entity)null);
				player.setPositionAndUpdate(x + .5D, y, z + .5D);
	             
			}
			 
			return null;
		}
		
		
		
	}

}
