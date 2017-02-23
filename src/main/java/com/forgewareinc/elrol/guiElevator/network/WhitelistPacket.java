package com.forgewareinc.elrol.guiElevator.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.forgewareinc.elrol.guiElevator.ElevatorMain;
import com.forgewareinc.elrol.guiElevator.TileEntityElevator;

public class WhitelistPacket implements IMessage{

	private int x;
	private int y;
	private int z;
	private String name;
	
	public WhitelistPacket(){
		
	}
	
	public WhitelistPacket(String name, int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
	}
	
	public static void sendToServer(IMessage packet){
		ElevatorMain.network.sendToServer(packet);
	}
	
	public static void send(String name, int X, int Y, int Z){
		sendToServer(new WhitelistPacket(name, X, Y, Z));
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		name = ByteBufUtils.readUTF8String(buf);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeUTF8String(buf, name);
	}

	public static class Handler implements IMessageHandler<WhitelistPacket, IMessage>{

		@Override
		public IMessage onMessage(WhitelistPacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			System.out.println("trying to work");
			if(world.getTileEntity(new BlockPos(message.x, message.y, message.z)) instanceof TileEntityElevator){
				TileEntityElevator tile = (TileEntityElevator)world.getTileEntity(new BlockPos(message.x, message.y, message.z));
				if(message.name.startsWith("-delete-")){
					System.out.println("Removing user: " + message.name.substring(8));
					tile.removeUsername(Integer.parseInt(message.name.substring(8)));
					tile.markDirty();
				}else{
					System.out.println("Setting user: " + message.name);
					tile.addUsername(message.name);
					tile.markDirty();
				}
			}else{
				System.out.print("Error?");
			}
			return null;
		}
		
	}
}
