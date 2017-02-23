package com.forgewareinc.elrol.guiElevator.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		ElevatorRenderRegistry.preInit();
	}
	
	public void init(FMLInitializationEvent e){
		super.init(e);
		ElevatorRenderRegistry.init();
		
	}

	public void postInit(FMLPostInitializationEvent e){
		super.postInit(e);
	}
	
}
