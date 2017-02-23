package com.forgewareinc.elrol.guiElevator;

import java.io.File;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GeneralConfig {

	public static Configuration config;
	private static File file = new File("/config/Gui_Elevator.cfg");
	private static final String GENERAL = config.CATEGORY_GENERAL; 
	
	public static boolean preventTraps;
	public static final String preventTrapsComment = "If true, floors will be unavailable if there is no floor or any of the blacklisted blocks are present";
	
	public static String[] trapBlockList;
	public static final String[] defaultTrapBlockList = new String[]{Blocks.lava.getUnlocalizedName().substring(5), Blocks.fire.getUnlocalizedName().substring(5), Blocks.water.getUnlocalizedName().substring(5)};
	public static final String trapBlockListComment = "List of blocks to be checked for as traps";
	
	public static String[] camoBlackList;
	public static final String[] defaultCamoBlacklist = new String[]{};
	public static final String camoBlackListComment = "List of blocks that cannot be used as a camouflage block";
	
	public static boolean permissionProtection;
	public static final String permissionProtectionComment = "set to true if the Elevators should be usable if the player can edit terrain";
	
	public static boolean whitelistProtection;
	public static final String whitelistProtectionComment = "set to true if the Elevators should be usable if the player is on the whitelist, Elevator must be advanced to use this feature";
	
	
	public static void registerConfig(FMLPreInitializationEvent event){
		config = new Configuration(new File(event.getModConfigurationDirectory() + "/GuiElevator/guiElevator.cfg"));
		FMLCommonHandler.instance().bus().register(ElevatorMain.instance);
		config.addCustomCategoryComment(GENERAL, "General Configuration Options");
		config.load();
		//Boolean - prevent traps
		preventTraps = config.getBoolean("Prevent Traps", GENERAL, false, preventTrapsComment);
		
		//String[] - trap
		trapBlockList = config.getStringList("Trap Blocklist", GENERAL, defaultTrapBlockList, trapBlockListComment);
		
		//String[] - blacklist
		camoBlackList = config.getStringList("Camouflage Blacklist", GENERAL, defaultCamoBlacklist, camoBlackListComment);
		
		//boolean - permission protection
		permissionProtection = config.getBoolean("Permission Protection", GENERAL, false, permissionProtectionComment);
		
		//boolean - whitelist protection
		whitelistProtection = config.getBoolean("Whitelist Protection", GENERAL, true, whitelistProtectionComment);
		config.save();
	}
	
	
}
