package com.forgewareinc.elrol.guiElevator.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ElevatorPacketSystem {
	private int count = 0;
	private SimpleNetworkWrapper INSTANCE;
	
	public ElevatorPacketSystem(String name){
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(name);
	}
	
	public <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class <? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side){
		INSTANCE.registerMessage(messageHandler, requestMessageType, count, side);
		count++;
	}
	
	public SimpleNetworkWrapper instance(){
		return INSTANCE;
	}
	
	public void inc(){
		count++;
	}
}
