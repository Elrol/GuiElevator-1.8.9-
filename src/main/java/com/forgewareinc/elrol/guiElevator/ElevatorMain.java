package com.forgewareinc.elrol.guiElevator;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.Logger;

import com.forgewareinc.elrol.guiElevator.gui.ModGUIHandler;
import com.forgewareinc.elrol.guiElevator.network.ColoringPacket;
import com.forgewareinc.elrol.guiElevator.network.OpenInventoryPacket;
import com.forgewareinc.elrol.guiElevator.network.PacketNaming;
import com.forgewareinc.elrol.guiElevator.network.TeleportPacket;
import com.forgewareinc.elrol.guiElevator.network.TickPacket;
import com.forgewareinc.elrol.guiElevator.network.UpdatePacket;
import com.forgewareinc.elrol.guiElevator.network.WhitelistPacket;
import com.forgewareinc.elrol.guiElevator.proxy.ClientProxy;
import com.forgewareinc.elrol.guiElevator.proxy.CommonProxy;



@Mod(modid = ModInfo.MODID, name = ModInfo.NAME,version = ModInfo.VERSION)
public class ElevatorMain
{
	public static Block elevatorBlack;
	public static Block elevatorRed;
	public static Block elevatorGreen;
	public static Block elevatorBrown;
	public static Block elevatorBlue;
	public static Block elevatorPurple;
	public static Block elevatorCyan;
	public static Block elevatorSilver;
	public static Block elevatorGray;
	public static Block elevatorPink;
	public static Block elevatorLime;
	public static Block elevatorYellow;
	public static Block elevatorLBlue;
	public static Block elevatorMagenta;
	public static Block elevatorOrange;
	public static Block elevatorWhite;
	
	public static Block elevatorAdv;

	public static Block elevatorOverlay;
	//public static final ElevatorPacketSystem packetSystem = new ElevatorPacketSystem(ModInfo.MODID);
	public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("guielevator");
	
	@Instance
	public static ElevatorMain instance = new ElevatorMain();
	
	@SidedProxy(clientSide="com.forgewareinc.elrol.guiElevator.proxy.ClientProxy", serverSide="com.forgewareinc.elrol.guiElevator.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static Logger logger;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	event.getModLog();
    	network.registerMessage(PacketNaming.Handler.class, PacketNaming.class, 0, Side.SERVER);
    	network.registerMessage(TeleportPacket.Handler.class, TeleportPacket.class, 1, Side.SERVER);
    	network.registerMessage(ColoringPacket.Handler.class, ColoringPacket.class, 2, Side.SERVER);
    	network.registerMessage(OpenInventoryPacket.Handler.class, OpenInventoryPacket.class, 3, Side.SERVER);
    	network.registerMessage(UpdatePacket.Handler.class, UpdatePacket.class, 4, Side.SERVER);
    	network.registerMessage(TickPacket.Handler.class, TickPacket.class, 5, Side.CLIENT);
    	network.registerMessage(WhitelistPacket.Handler.class, WhitelistPacket.class, 6, Side.SERVER);
    	GeneralConfig.registerConfig(event);
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(ElevatorMain.instance, new ModGUIHandler());
    	elevatorBlack = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "black");
    	elevatorRed = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "red");
    	elevatorGreen = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "green");
    	elevatorBrown = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "brown");
    	elevatorBlue = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "blue");
    	elevatorPurple = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "purple");
    	elevatorCyan = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "cyan");
    	elevatorSilver = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "lightGray");
    	elevatorGray = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "gray");
    	elevatorPink = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "pink");
    	elevatorLime = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "lime");
    	elevatorYellow = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "yellow");
    	elevatorLBlue = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "lightBlue");
    	elevatorMagenta = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "magenta");
    	elevatorOrange = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "orange");
    	elevatorWhite = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal, "white");
    	elevatorAdv = new Elevator("guielevator", CreativeTabs.tabTransport, 3.0F, 6.0F, Block.soundTypeMetal);
    	GameRegistry.registerTileEntity(TileEntityElevator.class, "elevatorBlock");
    	TileEntity.addMapping(TileEntityElevator.class, ModInfo.MODID + "TileEntityElevator");
    	registerHandler();
    	oreDict();
    	recipes();
    	proxy.init(event);
	}
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    }
    
    
    public void recipes(){
    	System.out.println("Register Recipe");
    	GameRegistry.addRecipe(new ItemStack(elevatorWhite, 1, 0), "xyx", "yzy", "xyx", 'x', Items.iron_ingot, 'y', Blocks.wool, 'z', Items.ender_pearl);
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorBlack, "blockElevator", "dyeBlack"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorRed, "blockElevator", "dyeRed"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorGreen, "blockElevator", "dyeGreen"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorBrown, "blockElevator", "dyeBrown"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorBlue, "blockElevator", "dyeBlue"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorPurple, "blockElevator", "dyePurple"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorCyan, "blockElevator", "dyeCyan"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorSilver, "blockElevator", "dyeLightGray"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorGray, "blockElevator", "dyeGray"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorPink, "blockElevator", "dyePink"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorLime, "blockElevator", "dyeLime"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorYellow, "blockElevator", "dyeYellow"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorLBlue, "blockElevator", "dyeLightBlue"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorMagenta, "blockElevator", "dyeMagenta"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorOrange, "blockElevator", "dyeOrange"));
    	GameRegistry.addRecipe(new ShapelessOreRecipe(elevatorWhite, "blockElevator", "dyeWhite"));
    }
    
    public void oreDict(){
    	OreDictionary.registerOre("blockElevator", elevatorBlack);
    	OreDictionary.registerOre("blockElevator", elevatorRed);
    	OreDictionary.registerOre("blockElevator", elevatorGreen);
    	OreDictionary.registerOre("blockElevator", elevatorBrown);
    	OreDictionary.registerOre("blockElevator", elevatorBlue);
    	OreDictionary.registerOre("blockElevator", elevatorPurple);
    	OreDictionary.registerOre("blockElevator", elevatorCyan);
    	OreDictionary.registerOre("blockElevator", elevatorSilver);
    	OreDictionary.registerOre("blockElevator", elevatorGray);
    	OreDictionary.registerOre("blockElevator", elevatorPink);
    	OreDictionary.registerOre("blockElevator", elevatorLime);
    	OreDictionary.registerOre("blockElevator", elevatorYellow);
    	OreDictionary.registerOre("blockElevator", elevatorLBlue);
    	OreDictionary.registerOre("blockElevator", elevatorMagenta);
    	OreDictionary.registerOre("blockElevator", elevatorOrange);
    	OreDictionary.registerOre("blockElevator", elevatorWhite);
    }
    
    public static void registerHandler(){
    	KeyPressHandler handler = new KeyPressHandler();
    	MinecraftForge.EVENT_BUS.register(handler);
    	FMLCommonHandler.instance().bus().register(handler);
    }
}
