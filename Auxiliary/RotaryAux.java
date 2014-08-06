/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.MekToolHandler;
import Reika.DragonAPI.ModInteract.RedstoneArsenalHandler;
import Reika.DragonAPI.ModInteract.TinkerToolHandler;
import Reika.RotaryCraft.GuiHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class RotaryAux {

	public static int blockModel;

	public static final Color[] sideColors = {Color.CYAN, Color.BLUE, Color.YELLOW, Color.BLACK, new Color(255, 120, 0), Color.MAGENTA};
	public static final String[] sideColorNames = {"CYAN", "BLUE", "YELLOW", "BLACK", "ORANGE", "MAGENTA"};

	private static List<Class<? extends TileEntity>> shaftPowerBlacklist = new ArrayList<Class<? extends TileEntity>>();

	static {
		//addShaftBlacklist("example.author.unauthorizedconverter.teclass");
	}

	public static boolean isBlacklistedIOMachine(TileEntity te) {
		return shaftPowerBlacklist.contains(te.getClass());
	}

	private static void addShaftBlacklist(String name) {
		Class cl;
		try {
			cl = Class.forName(name);
			shaftPowerBlacklist.add(cl);
			RotaryCraft.logger.log("Disabling "+name+" for shaft power. Destructive compatibility.");
		}
		catch (ClassNotFoundException e) {
			//RotaryCraft.logger.logError("Could not add EMP blacklist for "+name+": Class not found!");
		}
	}

	public static final boolean hasGui(World world, int x, int y, int z, EntityPlayer ep) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
			if (te == null)
				return false;
			if (te.getEngineType() == null)
				return false;
			if (!te.getEngineType().hasGui())
				return false;
			return true;
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter te = (TileEntitySplitter)world.getBlockTileEntity(x, y, z);
			return (te.getBlockMetadata() >= 8);
		}
		if (m == MachineRegistry.SCREEN)
			return !ep.isSneaking();
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			Object GUI = GuiHandler.instance.getClientGuiElement(GuiRegistry.MACHINE.ordinal(), ep, world, x, y, z);
			if (GUI != null)
				return true;
		}
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			Object GUI = GuiHandler.instance.getServerGuiElement(GuiRegistry.MACHINE.ordinal(), ep, world, x, y, z);
			if (GUI != null)
				return true;
		}
		return false;
	}

	public static int get4SidedMetadataFromPlayerLook(EntityLivingBase ep) {
		int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;
		switch (i) {
		case 0:
			return 2;
		case 1:
			return 1;
		case 2:
			return 3;
		case 3:
			return 0;
		}
		return -1;
	}

	public static int get6SidedMetadataFromPlayerLook(EntityLivingBase ep) {
		if (MathHelper.abs(ep.rotationPitch) < 60) {
			int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
			while (i > 3)
				i -= 4;
			while (i < 0)
				i += 4;
			switch (i) {
			case 0:
				return 2;
			case 1:
				return 1;
			case 2:
				return 3;
			case 3:
				return 0;
			}
		}
		else { //Looking up/down
			if (ep.rotationPitch > 0)
				return 4; //set to up
			else
				return 5; //set to down
		}
		return -1;
	}

	public static int get2SidedMetadataFromPlayerLook(EntityLivingBase ep) {
		int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 0;
		case 3:
			return 1;
		}
		return -1;
	}

	public static void flipXMetadatas(TileEntity t) {
		if (!(t instanceof RotaryCraftTileEntity))
			return;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)t;
		int m = te.getBlockMetadata();
		switch (m) {
		case 0:
			te.setBlockMetadata(1);
			break;
		case 1:
			te.setBlockMetadata(0);
			break;
		}
	}

	public static void flipZMetadatas(TileEntity t) {
		if (!(t instanceof RotaryCraftTileEntity))
			return;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)t;
		int m = te.getBlockMetadata();
		switch (m) {
		case 2:
			te.setBlockMetadata(3);
			break;
		case 3:
			te.setBlockMetadata(2);
			break;
		}
	}

	public static boolean canHarvestSteelMachine(EntityPlayer ep) {
		if (ep.capabilities.isCreativeMode)
			return false;
		ItemStack eitem = ep.inventory.getCurrentItem();
		if (eitem == null)
			return false;
		if (TinkerToolHandler.getInstance().isPick(eitem) && TinkerToolHandler.getInstance().isStoneOrBetter(eitem))
			return true;
		if (MekToolHandler.getInstance().isPickTypeTool(eitem) && !MekToolHandler.getInstance().isWood(eitem))
			return true;
		if (eitem.itemID == RedstoneArsenalHandler.getInstance().pickID) {
			return RedstoneArsenalHandler.getInstance().pickLevel > 0;
		}
		if (!(eitem.getItem() instanceof ItemPickaxe))
			return false;
		if (eitem.getItem().canHarvestBlock(Block.oreIron, eitem))
			return true;
		return false;
	}

	public static boolean shouldSetFlipped(World world, int x, int y, int z) {
		boolean softBelow = ReikaWorldHelper.softBlocks(world, x, y-1, z);
		boolean softAbove = ReikaWorldHelper.softBlocks(world, x, y+1, z);
		if (!softAbove && softBelow) {
			return true;
		}
		return false;
	}

	public static String getMessage(String tag) {
		return StatCollector.translateToLocal("message."+tag);
	}

	public static void writeMessage(String tag) {
		ReikaChatHelper.writeString(getMessage(tag));
	}

	public static boolean isMuffled(TileEntity te) {
		World world = te.worldObj;
		int x = te.xCoord;
		int y = te.yCoord;
		int z = te.zCoord;
		if (world.getBlockId(x, y+1, z) == Block.cloth.blockID && world.getBlockId(x, y-1, z) == Block.cloth.blockID) {
			return true;
		}
		return false;
	}
}
