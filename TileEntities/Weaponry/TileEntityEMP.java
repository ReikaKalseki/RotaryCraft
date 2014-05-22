/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEMP extends TileEntityPowerReceiver implements RangedEffect {

	private List<TileEntity> blocks = new ArrayList<TileEntity>();
	private BlockArray check  = new BlockArray();

	private static List<Class<? extends TileEntity>> blacklist = new ArrayList<Class<? extends TileEntity>>();

	private boolean loading = true;
	private boolean canLoad = true;

	private long energy;

	static {
		addEntry(TileEntityChest.class);
		addEntry(TileEntityEnderChest.class);
		addEntry(TileEntityHopper.class);
		addEntry(TileEntityDropper.class);
		addEntry(TileEntityDispenser.class);
		addEntry(TileEntityBrewingStand.class);
		addEntry(TileEntityEnchantmentTable.class);
		addEntry(TileEntityEndPortal.class);
		addEntry(TileEntitySign.class);
		addEntry(TileEntitySkull.class);

		addEntry("buildcraft.factory.TileTank", ModList.BCFACTORY);
		addEntry("buildcraft.transport.PipeTransport", ModList.BCTRANSPORT);

		addEntry("thermalexpansion.block.conduit.TileConduitRoot", ModList.THERMALEXPANSION);

		addEntry("ic2.core.block.wiring.TileEntityCable", ModList.IC2);

		addEntry("codechicken.enderstorage.common.TileFrequencyOwner", ModList.ENDERSTORAGE);

		addEntry("thaumcraft.common.tiles.TileAlembic", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileAlembicAdvanced", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileTileArcaneBore", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileArcaneBoreBase", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileArcaneFurnace", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileOwned", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileMagicWorkbench", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileBellows", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileChestHungry", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileCrucible", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileCrucibleAdvanced", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileCrystal", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileCrystalCapacitor", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileCrystalCore", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileMemory", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileLifter", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileMarker", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileMirror", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileNitor", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileResearchTable", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileSensor", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileTable", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileEtherealBloom", ModList.THAUMCRAFT);
		addEntry("thaumcraft.common.tiles.TileThaumcraft", ModList.THAUMCRAFT);

		addEntry("forestry.core.gadgets.TileNaturalistChest", ModList.FORESTRY);
		addEntry("forestry.core.gadgets.TileMill", ModList.FORESTRY);
		addEntry("forestry.apiculture.gadgets.TileAlveary", ModList.FORESTRY);
		addEntry("forestry.apiculture.gadgets.TileBeehouse", ModList.FORESTRY);
		addEntry("forestry.aboriculture.gadgets.TileTreeContainer", ModList.FORESTRY);
		addEntry("forestry.factory.gadgets.TileWorktable", ModList.FORESTRY);

		addEntry("Reika.FurryKingdoms.TileEntities.TileEntityFlag", ModList.FURRYKINGDOMS);

		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntity555", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityBreaker", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityBUD", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityCamo", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityChestReader", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityDriver", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityEffector", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityEmitter", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityPlacer", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityProximity", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityReceiver", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityToggle", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityWeather", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityRedstonePump", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityHopperTicker", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntitySignalScaler", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityColumnDecrementer", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityAnalogTransmitter", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityAnalogReceiver", ModList.EXPANDEDREDSTONE);
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityEqualizer", ModList.EXPANDEDREDSTONE);
	}

	private static void addEntry(Class<? extends TileEntity> cl) {
		blacklist.add(cl);
		RotaryCraft.logger.log("Adding "+cl.getCanonicalName()+" to the EMP immunity list");
	}

	private static void addEntry(String name, ModList mod) {
		Class cl;
		if (!mod.isLoaded())
			return;
		try {
			cl = Class.forName(name);
			blacklist.add(cl);
			RotaryCraft.logger.log("Adding "+name+" to the EMP immunity list");
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.logError("Could not add EMP blacklist for "+name+": Class not found!");
		}
	}

	private boolean fired = false;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.EMP;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		this.getPowerBelow();
		energy += power;

		if (fired)
			return;

		if (canLoad && check.isEmpty()) {
			int r = this.getRange();
			for (int i = x-r; i <= x+r; i++) {
				for (int k = z-r; k <= z+r; k++) {
					check.addBlockCoordinate(i, y, k);
					loading = true;
				}
			}
		}

		//ReikaJavaLibrary.pConsoleSideOnly(check.getSize(), Side.SERVER);
		//ReikaJavaLibrary.pConsole(blocks.size(), Side.SERVER);

		if (!world.isRemote)
			this.createListing();

		if (loading) {
			for (int i = 0; i < 6; i++) {
				double dx = rand.nextDouble();
				double dz = rand.nextDouble();
				world.spawnParticle("portal", x-0.5+dx*2, y+rand.nextDouble()+0.4, z-0.5+dz*2, -1+dx*2, 0.2, -1+dz*2);
			}
		}

		//power = (long)BLAST_ENERGY+800;

		if (energy/20L >= MINPOWER && !loading && !world.isRemote)
			this.fire(world, x, y, z);
	}

	private void createListing() {
		World world = worldObj;
		int num = 1+8*ConfigRegistry.EMPLOAD.getValue();
		for (int i = 0; i < num && loading; i++) {
			int index = rand.nextInt(check.getSize());
			int[] b = check.getNthBlock(index);
			int x = b[0];
			int z = b[2];
			for (int y = 0; y < world.provider.getHeight(); y++) {
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te != null) {
					blocks.add(te);
				}
			}
			check.remove(index);
			if (check.isEmpty()) {
				loading = false;
				canLoad = false;
			}
		}
	}

	private void fire(World world, int x, int y, int z) {
		fired = true;
		for (int i = 0; i < blocks.size(); i++) {
			TileEntity te = blocks.get(i);
			if (te instanceof INode)
				this.chargeNode((INode)te);
			else
				this.shutdownTE(te);
		}
		world.setBlock(x, y, z, 0);
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 3F, true);
		if (ReikaRandomHelper.doWithChance(50)) {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, this.getMachine().getCraftedProduct());
		}
		else if (ReikaRandomHelper.doWithChance(50)) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(Item.netherStar));
			items.add(new ItemStack(Item.diamond, 9, 0));
			items.add(new ItemStack(Item.ingotGold, 32, 0));
			items.add(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, rand.nextInt(16)));
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, items);
		}
		else {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 8+rand.nextInt(16)));
		}
	}

	private void chargeNode(INode te) {
		//ReikaJavaLibrary.pConsole(te.getNodeType()+":"+te.getAspects().aspects);
		te.setNodeVisBase((short)32000);
		te.addToContainer(Aspect.ENERGY, (short)8000);
		te.addToContainer(Aspect.WEAPON, (short)1000);
		te.addToContainer(Aspect.MECHANISM, (short)2000);
		switch(te.getNodeType()) {
		case UNSTABLE:
			if (rand.nextInt(2) == 0) {
				te.setNodeType(NodeType.DARK);
			}
			else
				te.setNodeType(NodeType.PURE);
			break;
		case DARK:
			te.setNodeType(NodeType.TAINTED);
			break;
		case NORMAL:
			te.setNodeType(NodeType.UNSTABLE);
			break;
		case TAINTED:
			te.setNodeType(NodeType.HUNGRY);
			break;
		default:
			break;
		}
		//ReikaJavaLibrary.pConsole(te.getNodeType()+":"+te.getAspects().aspects);
	}

	private void shutdownTE(TileEntity te) {
		if (te == null)
			return;
		if (this.isBlacklisted(te))
			return;
		if (te instanceof RotaryCraftTileEntity) {
			RotaryCraftTileEntity rc = (RotaryCraftTileEntity)te;
			if (!rc.isShutdown())
				rc.onEMP();
		}
		else if (ConfigRegistry.ATTACKBLOCKS.getState())
			this.shutdownFallback(te);
	}

	private boolean isBlacklisted(TileEntity te) {
		Class c = te.getClass();
		for (int i = 0; i < blacklist.size(); i++) {
			Class b = blacklist.get(i);
			if (b.isAssignableFrom(c))
				return true;
		}
		return false;
	}

	private void shutdownFallback(TileEntity te) {
		int x = te.xCoord;
		int y = te.yCoord;
		int z = te.zCoord;
		int id = worldObj.getBlockId(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		this.dropMachine(worldObj, x, y, z);
		/*
		Block b = Block.blocksList[id];
		ItemStack[] inv;
		if (te instanceof IInventory) {
			IInventory ii = (IInventory)te;
			inv = new ItemStack[ii.getSizeInventory()];
			for (int i = 0; i < inv.length; i++) {
				inv[i] = ii.getStackInSlot(i);
			}
		}
		else {
			inv = new ItemStack[0];
		}
		worldObj.setBlock(x, y, z, BlockRegistry.DEADMACHINE.getBlockID());
		TileEntityDeadMachine dead = (TileEntityDeadMachine)worldObj.getBlockTileEntity(x, y, z);
		dead.setBlock(b, id, meta);
		dead.setInvSize(inv.length);
		for (int i = 0; i < inv.length; i++) {
			dead.setInventorySlotContents(i, inv[i]);
		}*/
	}

	private void dropMachine(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		Block b = Block.blocksList[id];
		if (b != null) {
			//ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, b.getBlockDropped(world, x, y, z, meta, 0));
			b.dropBlockAsItem(world, x, y, z, meta, 0);
		}
		world.setBlock(x, y, z, 0);
	}

	@Override
	public int getRange() {
		return 64;
	}

	@Override
	public int getMaxRange() {
		return 64;
	}

	public boolean isLoading() {
		return loading;
	}

	public boolean usable() {
		return !fired;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("load", loading);
		NBT.setBoolean("cload", canLoad);
		NBT.setBoolean("fire", fired);
		NBT.setLong("e", energy);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		loading = NBT.getBoolean("load");
		canLoad = NBT.getBoolean("cload");
		fired = NBT.getBoolean("fire");

		energy = NBT.getLong("e");
	}

	public void updateListing() {
		canLoad = true;
	}

}
