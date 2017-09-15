/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import Reika.ChromatiCraft.TileEntity.Networking.TileEntityCrystalPylon;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Collections.ClassNameCache;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEMP extends TileEntityPowerReceiver implements RangedEffect {

	public static final long BLAST_ENERGY = (long)(4.184e9);

	private ArrayList<Coordinate> blocks = new ArrayList();
	private BlockArray check  = new BlockArray();

	private static ClassNameCache blacklist = new ClassNameCache();

	private static HashSet<WorldLocation> shutdownLocations = new HashSet();

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

		addEntry("thermalexpansion.Blocks.conduit.TileConduitRoot", ModList.THERMALEXPANSION);

		addEntry("ic2.core.Blocks.wiring.TileEntityCable", ModList.IC2);

		addEntry("codechicken.enderstorage.common.TileFrequencyOwner", ModList.ENDERSTORAGE);

		addEntry("thaumcraft.common.tiles.*", ModList.THAUMCRAFT);

		addEntry("forestry.core.tiles.TileNaturalistChest", ModList.FORESTRY);
		addEntry("forestry.core.tiles.TileMill", ModList.FORESTRY);
		addEntry("forestry.apiculture.multiblock.TileAlvearyPlain", ModList.FORESTRY);
		addEntry("forestry.apiculture.tiles.*", ModList.FORESTRY);
		addEntry("forestry.aboriculture.tiles.TileTreeContainer", ModList.FORESTRY);
		addEntry("forestry.factory.tiles.TileWorktable", ModList.FORESTRY);

		//addEntry("Reika.FurryKingdoms.TileEntities.TileEntityFlag", ModList.FURRYKINGDOMS);

		addEntry("Reika.ExpandedRedstone.TileEntities.*", ModList.EXPANDEDREDSTONE);
	}

	private static void addEntry(Class<? extends TileEntity> cl) {
		blacklist.add(cl.getName());
		RotaryCraft.logger.log("Adding "+cl.getName()+" to the EMP immunity list");
	}

	private static void addEntry(String name, ModList mod) {
		if (!mod.isLoaded())
			return;
		blacklist.add(name);
		RotaryCraft.logger.log("Adding "+name+" to the EMP immunity list");
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
		if (power >= MINPOWER)
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

		if (energy/20L >= BLAST_ENERGY && !loading && !world.isRemote)
			this.fire(world, x, y, z);
	}

	private void createListing() {
		World world = worldObj;
		int num = 1+8*ConfigRegistry.EMPLOAD.getValue();
		for (int i = 0; i < num && loading; i++) {
			int index = rand.nextInt(check.getSize());
			Coordinate b = check.getNthBlock(index);
			int x = b.xCoord;
			int z = b.zCoord;
			for (int y = 0; y < world.provider.getHeight(); y++) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null) {
					blocks.add(new Coordinate(te));
				}
			}
			check.remove(b.xCoord, b.yCoord, b.zCoord);
			if (check.isEmpty()) {
				loading = false;
				canLoad = false;
			}
		}
	}

	private void fire(World world, int x, int y, int z) {
		fired = true;
		for (int i = 0; i < blocks.size(); i++) {
			TileEntity te = blocks.get(i).getTileEntity(world);
			if (ModList.CHROMATICRAFT.isLoaded() && te instanceof TileEntityCrystalPylon)
				((TileEntityCrystalPylon)te).onEMP(this);
			else if (InterfaceCache.NODE.instanceOf(te))
				this.chargeNode((INode)te);
			else
				this.shutdownTE(te);
		}
		this.affectEntities(world, x, y, z);
		world.setBlockToAir(x, y, z);
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 3F, true);
		if (ReikaRandomHelper.doWithChance(50)) {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, this.getMachine().getCraftedProduct());
		}
		else if (ReikaRandomHelper.doWithChance(50)) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(Items.nether_star));
			items.add(new ItemStack(Items.diamond, 9, 0));
			items.add(new ItemStack(Items.gold_ingot, 32, 0));
			items.add(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, rand.nextInt(16)));
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, items);
		}
		else {
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 8+rand.nextInt(16)));
		}
	}

	private void affectEntities(World world, int x, int y, int z) {
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(128, 64, 128);
		List<Entity> li = world.getEntitiesWithinAABB(Entity.class, box);
		for (Entity e : li) {
			if (InterfaceCache.BCROBOT.instanceOf(e)) {
				world.createExplosion(e, e.posX, e.posY, e.posZ, 3, false);
				e.setDead();
			}
			else if (e instanceof EntityLivingBase) {
				if (ReikaEntityHelper.isEntityWearingPoweredArmor((EntityLivingBase)e)) {
					for (int i = 1; i <= 4; i++) {
						e.setCurrentItemOrArmor(i, null);
					}
					float f = (float)ReikaRandomHelper.getRandomBetween(3D, 10D);
					world.newExplosion(e, e.posX, e.posY, e.posZ, f, true, true);
					e.motionX += ReikaRandomHelper.getRandomPlusMinus(0, 1.5);
					e.motionZ += ReikaRandomHelper.getRandomPlusMinus(0, 1.5);
					e.motionY += -ReikaRandomHelper.getRandomBetween(0.125, 1);
				}
			}
		}
	}

	private void chargeNode(INode te) {
		//ReikaJavaLibrary.pConsole(te.getNodeType()+":"+te.getAspects().aspects);
		te.setNodeVisBase(Aspect.ENERGY, (short)32000);
		te.setNodeVisBase(Aspect.WEAPON, (short)32000);
		te.setNodeVisBase(Aspect.MECHANISM, (short)32000);

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
		return blacklist.contains(te.getClass());
	}

	private void shutdownFallback(TileEntity te) {
		//shutdownLocations.add(new WorldLocation(te));

		int x = te.xCoord;
		int y = te.yCoord;
		int z = te.zCoord;
		Block id = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		this.dropMachine(worldObj, x, y, z);
		/*
		;
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
		worldObj.setBlock(x, y, z, BlockRegistry.DEADMACHINE.getBlock());
		TileEntityDeadMachine dead = (TileEntityDeadMachine)worldObj.getTileEntity(x, y, z);
		dead.setBlock(b, id, meta);
		dead.setInvSize(inv.length);
		for (int i = 0; i < inv.length; i++) {
			dead.setInventorySlotContents(i, inv[i]);
		}*/
	}

	public static boolean isShutdown(TileEntity te) {
		return shutdownLocations.contains(new WorldLocation(te));
	}

	public static boolean isShutdown(World world, int x, int y, int z) {
		return shutdownLocations.contains(new WorldLocation(world, x, y, z));
	}

	public static void resetCoordinate(World world, int x, int y, int z) {
		shutdownLocations.remove(new WorldLocation(world, x, y, z));
	}

	private void dropMachine(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		;
		if (b != null) {
			//ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, b.getDrops(world, x, y, z, meta, 0));
			b.dropBlockAsItem(world, x, y, z, meta, 0);
		}
		world.setBlockToAir(x, y, z);
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
