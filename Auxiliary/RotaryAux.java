/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.awt.Color;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWaySet;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.MekToolHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.RedstoneArsenalHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler;
import Reika.RotaryCraft.GuiHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.EnvironmentalHeatSource;
import Reika.RotaryCraft.API.Interfaces.EnvironmentalHeatSource.SourceType;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class RotaryAux {

	public static int blockModel;

	public static final Color[] sideColors = {Color.CYAN, Color.BLUE, Color.YELLOW, Color.BLACK, new Color(255, 120, 0), Color.MAGENTA};
	public static final String[] sideColorNames = {"CYAN", "BLUE", "YELLOW", "BLACK", "ORANGE", "MAGENTA"};

	public static final boolean getPowerOnClient = ConfigRegistry.POWERCLIENT.getState();

	private static Set<Class<? extends TileEntity>> shaftPowerBlacklist = new OneWaySet<Class<? extends TileEntity>>();

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
			TileEntityEngine te = (TileEntityEngine)world.getTileEntity(x, y, z);
			if (te == null)
				return false;
			if (te.getEngineType() == null)
				return false;
			if (!te.getEngineType().hasGui())
				return false;
			return true;
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter te = (TileEntitySplitter)world.getTileEntity(x, y, z);
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
		if (eitem.getItem() == RedstoneArsenalHandler.getInstance().pickID) {
			return RedstoneArsenalHandler.getInstance().pickLevel > 0;
		}
		//if (!(eitem.getItem() instanceof ItemPickaxe))
		//	return false;
		if (eitem.getItem().canHarvestBlock(Blocks.iron_ore, eitem))
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
		if (world.getBlock(x, y+1, z) == Blocks.wool && world.getBlock(x, y-1, z) == Blocks.wool) {
			return true;
		}
		return false;
	}

	public static boolean isNextToIce(World world, int x, int y, int z) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.ice) != null)
			return true;
		Block b = world.getBlock(x, y-1, z);
		if (b instanceof EnvironmentalHeatSource) {
			EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
			return ehs.isActive(world, x, y, z) && ehs.getSourceType(world, x, y, z) == SourceType.ICY;
		}
		return false;
	}

	public static boolean isNextToWater(World world, int x, int y, int z) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water) != null)
			return true;
		for (int i = 1; i <= 2; i++) {
			Block b = world.getBlock(x, y-i, z);
			if (b instanceof EnvironmentalHeatSource) {
				EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
				return ehs.isActive(world, x, y-i, z) && ehs.getSourceType(world, x, y-i, z) == SourceType.WATER;
			}
		}
		return false;
	}

	public static boolean isNextToFire(World world, int x, int y, int z) {
		if (ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.fire) != null)
			return true;
		for (int i = 1; i <= 2; i++) {
			Block b = world.getBlock(x, y-i, z);
			if (b instanceof EnvironmentalHeatSource) {
				EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
				return ehs.isActive(world, x, y-i, z) && ehs.getSourceType(world, x, y-i, z) == SourceType.FIRE;
			}
		}
		return false;
	}

	public static boolean isNextToLava(World world, int x, int y, int z) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null)
			return true;
		for (int i = 1; i <= 2; i++) {
			Block b = world.getBlock(x, y-i, z);
			if (b instanceof EnvironmentalHeatSource) {
				EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
				return ehs.isActive(world, x, y-i, z) && ehs.getSourceType(world, x, y-i, z) == SourceType.LAVA;
			}
		}
		return false;
	}

	public static boolean isAboveFire(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y-1, z);
		if (b == Blocks.fire)
			return true;
		for (int i = 1; i <= 2; i++) {
			b = world.getBlock(x, y-i, z);
			if (b instanceof EnvironmentalHeatSource) {
				EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
				return ehs.isActive(world, x, y-i, z) && ehs.getSourceType(world, x, y-i, z) == SourceType.FIRE;
			}
		}
		return false;
	}

	public static boolean isAboveLava(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y-1, z);
		if (b.getMaterial() == Material.lava)
			return true;
		for (int i = 1; i <= 2; i++) {
			b = world.getBlock(x, y-i, z);
			if (b instanceof EnvironmentalHeatSource) {
				EnvironmentalHeatSource ehs = (EnvironmentalHeatSource)b;
				return ehs.isActive(world, x, y-i, z) && ehs.getSourceType(world, x, y-i, z) == SourceType.LAVA;
			}
		}
		return false;
	}

	public static boolean func_28813_f(int p_147439_1_, int p_147439_2_, final boolean p_147439_3_, final Class p_147439_4_) {
		int par1 = p_147439_1_;
		int par2 = p_147439_2_;
		int par3 = p_147439_3_ ? 16 : 0;

		int par4 = par3 - par2 + (par2 << 4);
		int par5 = par4 - par1 + (p_147439_3_ ? 0 : 1 << 8);

		final Calendar par6 = Calendar.getInstance();

		int par7 = 2;
		int par8 = par5 > 0 ? 1 : -1;
		int par9 = 5;
		int par10 = 40;

		int par11 = par7;
		int par12 = par10;
		int par13 = par9;
		par7 *= 2;
		par8 *= par7;
		int par14 = par13*par10;

		if (p_147439_4_ == RotaryCraft.class) {
			par14 = -par14;
		}
		else if (BlockBasicMachine.class.isAssignableFrom(p_147439_4_)) {
			par12 -= 32;
			par14 += 3;
		}
		par10 -= 38;

		return par6.get(par11) == 3 && par6.get(par13) <= par10;
	}

	private static EnumMap field_95695_gc_ = new EnumMap(MachineRegistry.class);
	private static IntHashMap field_95696_r_ = new IntHashMap();

	public static IIcon func_39467_a_(Block p_286117_1_, int p_286117_2_, final int p_286117_3_, WorldRenderer p_286117_4_) {
		MachineRegistry par1 = MachineRegistry.getMachineFromIDandMetadata(p_286117_1_, p_286117_2_);
		boolean par2 = p_286117_4_ != null && p_286117_4_.isVisible && !p_286117_4_.skipAllRenderPasses();
		boolean par3 = p_286117_4_ == null || p_286117_4_.tileEntityRenderers.isEmpty();
		if (par2 && par3)
			return null;
		IconSide par4 = (IconSide)field_95695_gc_.get(par1);
		int par5 = par4 == null ? -1 : par4.field_118037_b.containsKey(p_286117_3_) && par3 ? par4.field_118037_b.get(p_286117_3_) : par4.field_118037_a;
		if (p_286117_1_ instanceof BlockGearbox && par5 == 10) {
			par5 += p_286117_2_/4;
		}
		if (p_286117_1_ instanceof BlockFlywheel) {
			par5 += 16*(p_286117_2_/4);
		}
		return func_39467_a_(par5);
	}

	private static IIcon func_39467_a_(int p_603793_1_) {
		IIcon par1 = null;
		if (p_603793_1_ >= 0) {
			par1 = (IIcon)field_95696_r_.lookup(p_603793_1_);
		}
		else {
			par1 = (IIcon)field_95696_r_.lookup(-1);
		}
		return par1 != null ? par1 : (IIcon)field_95696_r_.lookup(-1);
	}

	private static class IconSide {

		private final int field_118037_a;
		private HashMap<Integer, Integer> field_118037_b = new HashMap();

		private IconSide(int f) {
			field_118037_a = f;
		}

		private IconSide func_23957_a(int p_934087_1_, int p_934087_2_) {
			field_118037_b.put(p_934087_1_, p_934087_2_);
			return this;
		}

	}

	public static void func_72350_c_(TextureMap p_542354_1_) {
		Set par1 = field_95695_gc_.keySet();
		Iterator par2 = par1.iterator();
		while (par2.hasNext()) {
			IconSide par3 = (IconSide)field_95695_gc_.get(par2.next());
			field_95696_r_.addKey(par3.field_118037_a, p_542354_1_.registerIcon(func_23890_f_(par3.field_118037_a)));
			for (int par4 : par3.field_118037_b.keySet())
				field_95696_r_.addKey(par3.field_118037_b.get(par4), p_542354_1_.registerIcon(func_23890_f_(par3.field_118037_b.get(par4))));
		}

		field_95696_r_.addKey(-1, p_542354_1_.registerIcon(func_23890_f_(255)));
	}

	private static String func_23890_f_(int p_345689_1_) {
		int par1 = p_345689_1_ / 16;
		int par2 = p_345689_1_ % 16;
		return "rotarycraft:/old/tile"+par2+"_"+par1;
	}

	static {
		field_95695_gc_.put(MachineRegistry.SHAFT, new IconSide(8).func_23957_a(2, 9).func_23957_a(3, 9));
		field_95695_gc_.put(MachineRegistry.GEARBOX, new IconSide(8).func_23957_a(0, 10).func_23957_a(1, 10).func_23957_a(2, 9).func_23957_a(3, 9));
		field_95695_gc_.put(MachineRegistry.BEDROCKBREAKER, new IconSide(1).func_23957_a(2, 2).func_23957_a(3, 3).func_23957_a(1, 4));
		field_95695_gc_.put(MachineRegistry.FERMENTER, new IconSide(20).func_23957_a(2, 21).func_23957_a(3, 22));
		field_95695_gc_.put(MachineRegistry.CLUTCH, new IconSide(25).func_23957_a(2, 26).func_23957_a(3, 26));
		field_95695_gc_.put(MachineRegistry.BEVELGEARS, new IconSide(27).func_23957_a(3, 28).func_23957_a(5, 28).func_23957_a(1, 28));
		field_95695_gc_.put(MachineRegistry.FLOODLIGHT, new IconSide(29).func_23957_a(3, 31).func_23957_a(4, 30).func_23957_a(5, 30).func_23957_a(0, 30).func_23957_a(1, 30));
		field_95695_gc_.put(MachineRegistry.SPLITTER, new IconSide(1).func_23957_a(1, 17).func_23957_a(0, 18).func_23957_a(3, 19).func_23957_a(4, 19).func_23957_a(5, 19));
		field_95695_gc_.put(MachineRegistry.GRINDER, new IconSide(49).func_23957_a(2, 50).func_23957_a(3, 51));
		field_95695_gc_.put(MachineRegistry.HEATRAY, new IconSide(53).func_23957_a(2, 54));
		field_95695_gc_.put(MachineRegistry.BORER, new IconSide(57).func_23957_a(2, 58).func_23957_a(3, 59));
		field_95695_gc_.put(MachineRegistry.FLYWHEEL, new IconSide(23).func_23957_a(2, 24));
		field_95695_gc_.put(MachineRegistry.ENGINE, new IconSide(1).func_23957_a(2, 14).func_23957_a(3, 15));
		field_95695_gc_.put(MachineRegistry.DYNAMOMETER, new IconSide(34).func_23957_a(2, 35).func_23957_a(3, 35));
	}
}
