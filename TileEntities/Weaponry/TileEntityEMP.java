/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
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
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.api.aura.EnumNodeType;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEMP extends TileEntityPowerReceiver implements RangedEffect {

	private List<TileEntity> blocks = new ArrayList<TileEntity>();
	private BlockArray check  = new BlockArray();

	private static List<Class<? extends TileEntity>> blacklist = new ArrayList<Class<? extends TileEntity>>();

	public static final double BLAST_ENERGY = 4.814*1000000000000D; //1 kiloton

	private boolean loading = true;
	private boolean canLoad = true;

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

		addEntry("buildcraft.factory.TileTank");
		addEntry("buildcraft.transport.PipeTransport");

		addEntry("thermalexpansion.block.conduit.TileConduitRoot");

		addEntry("ic2.core.block.wiring.TileEntityCable");

		addEntry("codechicken.enderstorage.common.TileFrequencyOwner");

		addEntry("thaumcraft.common.tiles.TileAlembic");
		addEntry("thaumcraft.common.tiles.TileAlembicAdvanced");
		addEntry("thaumcraft.common.tiles.TileTileArcaneBore");
		addEntry("thaumcraft.common.tiles.TileArcaneBoreBase");
		addEntry("thaumcraft.common.tiles.TileArcaneFurnace");
		addEntry("thaumcraft.common.tiles.TileOwned");
		addEntry("thaumcraft.common.tiles.TileMagicWorkbench");
		addEntry("thaumcraft.common.tiles.TileBellows");
		addEntry("thaumcraft.common.tiles.TileChestHungry");
		addEntry("thaumcraft.common.tiles.TileCrucible");
		addEntry("thaumcraft.common.tiles.TileCrucibleAdvanced");
		addEntry("thaumcraft.common.tiles.TileCrystal");
		addEntry("thaumcraft.common.tiles.TileCrystalCapacitor");
		addEntry("thaumcraft.common.tiles.TileCrystalCore");
		addEntry("thaumcraft.common.tiles.TileMemory");
		addEntry("thaumcraft.common.tiles.TileLifter");
		addEntry("thaumcraft.common.tiles.TileMarker");
		addEntry("thaumcraft.common.tiles.TileMirror");
		addEntry("thaumcraft.common.tiles.TileNitor");
		addEntry("thaumcraft.common.tiles.TileResearchTable");
		addEntry("thaumcraft.common.tiles.TileSensor");
		addEntry("thaumcraft.common.tiles.TileTable");

		addEntry("forestry.core.gadgets.TileNaturalistChest");
		addEntry("forestry.core.gadgets.TileMill");
		addEntry("forestry.apiculture.gadgets.TileAlveary");
		addEntry("forestry.apiculture.gadgets.TileBeehouse");
		addEntry("forestry.aboriculture.gadgets.TileTreeContainer");
		addEntry("forestry.factory.gadgets.TileWorktable");

		addEntry("Reika.FurryKingdoms.TileEntities.TileEntityFlag");

		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntity555");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityBreaker");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityBUD");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityCamo");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityChestReader");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityDriver");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityEffector");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityEmitter");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityPlacer");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityProximity");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityReceiver");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityToggle");
		addEntry("Reika.ExpandedRedstone.TileEntities.TileEntityWeather");
	}

	private static void addEntry(Class<? extends TileEntity> cl) {
		blacklist.add(cl);
		RotaryCraft.logger.log("Adding "+cl.getCanonicalName()+" to the EMP immunity list");
	}

	private static void addEntry(String name) {
		Class cl;
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
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.EMP.ordinal();
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
		//ReikaJavaLibrary.pConsoleSideOnly(blocks, Side.SERVER);

		if (!world.isRemote)
			this.createListing();

		if (loading) {
			for (int i = 0; i < 6; i++) {
				double dx = par5Random.nextDouble();
				double dz = par5Random.nextDouble();
				world.spawnParticle("portal", x-0.5+dx*2, y+par5Random.nextDouble()+0.4, z-0.5+dz*2, -1+dx*2, 0.2, -1+dz*2);
			}
		}

		if (power >= BLAST_ENERGY && !loading && !world.isRemote)
			this.fire(world, x, y, z);
	}

	private void createListing() {
		World world = worldObj;
		int num = 1+8*ConfigRegistry.EMPLOAD.getValue();
		for (int i = 0; i < num && loading; i++) {
			int index = par5Random.nextInt(check.getSize());
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
			this.shutdownTE(te);
		}
		if (APIRegistry.THAUMCRAFT.conditionsMet())
			this.affectNearNodes(world, x, y, z);
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 3F, true);
		world.setBlock(x, y, z, 0);
		if (ReikaMathLibrary.doWithChance(50)) {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, this.getMachine().getCraftedProduct());
		}
		else if (ReikaMathLibrary.doWithChance(50)) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(Item.netherStar));
			items.add(new ItemStack(Item.diamond, 9, 0));
			items.add(new ItemStack(Item.ingotGold, 32, 0));
			items.add(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, par5Random.nextInt(16)));
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, items);
		}
		else {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 8+par5Random.nextInt(16)));
		}
	}

	private void affectNearNodes(World world, int x, int y, int z) {
		List<AuraNode> nodes = ReikaThaumHelper.getAllNodeCopiesNear(world, x+0.5, y+0.5, z+0.5, this.getRange());
		if (nodes == null)
			return;
		ObjectTags flux = new ObjectTags();
		flux.add(EnumTag.DESTRUCTION, 100);
		flux.add(EnumTag.FLUX, 200);
		flux.add(EnumTag.MECHANISM, 100);
		flux.add(EnumTag.POWER, 400);

		for (int i = 0; i < nodes.size(); i++) {
			AuraNode au = nodes.get(i);
			if (au != null) {
				int base = au.baseLevel;
				switch(au.type) {
				case NORMAL:
					ReikaThaumHelper.affectNode(au, 0, par5Random.nextInt(base/2), false, flux, 0, 0, 0);
					if (par5Random.nextInt(100) == 0) {
						ReikaThaumHelper.setNodeType(au, EnumNodeType.UNSTABLE);
					}
					break;
				case PURE:
					ReikaThaumHelper.affectNode(au, 0, -base/2, false, flux, 0, 0, 0);
					break;
				case UNSTABLE:
					ReikaThaumHelper.affectNode(au, 0, base/2, false, flux, 0, 0, 0);
					if (par5Random.nextInt(50) == 0) {
						ReikaThaumHelper.setNodeType(au, EnumNodeType.DARK);
					}
					break;
				case DARK:
					ReikaThaumHelper.affectNode(au, 0, -base/2+par5Random.nextInt(base)+1, false, flux, 0, 0, 0);
					if (par5Random.nextInt(1000) == 0) {
						ReikaThaumHelper.setNodeType(	au, EnumNodeType.PURE);
					}
					break;
				default:
					break;
				}
			}
		}
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
		else
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

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setBoolean("load", loading);
		NBT.setBoolean("cload", canLoad);
		NBT.setBoolean("fire", fired);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		loading = NBT.getBoolean("load");
		canLoad = NBT.getBoolean("cload");
		fired = NBT.getBoolean("fire");
	}

	public void updateListing() {
		canLoad = true;
	}

}
