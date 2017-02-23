package com.forgewareinc.elrol.guiElevator.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.forgewareinc.elrol.guiElevator.Elevator;
import com.forgewareinc.elrol.guiElevator.ElevatorMain;

public class ColoringPacket implements IMessage {

	static int x;
	static int y;
	static int z;
	static int dye;
	static int meta;
	
	public ColoringPacket(){}
	
	public ColoringPacket(int x, int y, int z, int dye, int meta){
		this.x = x;
		this.y = y;
		this.z = z;
		this.dye = dye;
		this.meta = meta;
	}
	
	public static void sendToServer(IMessage packet){
		ElevatorMain.network.sendToServer(packet);
	}
	
	public static void send(int X, int Y, int Z, int dye, int meta){
		sendToServer(new ColoringPacket(X, Y, Z, dye, meta));
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		dye = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dye);
		buf.writeInt(meta);
	}

	public static class Handler implements IMessageHandler<ColoringPacket, IMessage>{

		@Override
		public IMessage onMessage(ColoringPacket message, MessageContext ctx) {
			Block block = null;
			World world = ctx.getServerHandler().playerEntity.worldObj;
			switch(message.dye){
				case 0: block = ElevatorMain.elevatorBlack; break;
				case 1: block = ElevatorMain.elevatorRed; break;
				case 2: block = ElevatorMain.elevatorGreen; break;
				case 3: block = ElevatorMain.elevatorBrown; break;
				case 4: block = ElevatorMain.elevatorBlue; break;
				case 5: block = ElevatorMain.elevatorPurple; break;
				case 6: block = ElevatorMain.elevatorCyan; break;
				case 7: block = ElevatorMain.elevatorSilver; break;
				case 8: block = ElevatorMain.elevatorGray; break;
				case 9: block = ElevatorMain.elevatorPink; break;
				case 10: block = ElevatorMain.elevatorLime; break;
				case 11: block = ElevatorMain.elevatorYellow; break;
				case 12: block = ElevatorMain.elevatorLBlue; break;
				case 13: block = ElevatorMain.elevatorMagenta; break;
				case 14: block = ElevatorMain.elevatorOrange; break;
				case 15: block = ElevatorMain.elevatorWhite; break;
				default: break;
			}
			if(block != null){
				world.setBlockState(new BlockPos(message.x, message.y, message.z), block.getDefaultState().withProperty(Elevator.meta, Integer.valueOf(message.meta)));
					
			}
			return null;
		}
		
		
		
	}

}
