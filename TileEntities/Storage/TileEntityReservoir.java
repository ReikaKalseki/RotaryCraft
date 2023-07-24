/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.TileEntity.AdjacentUpdateWatcher;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Interfaces.TileEntity.OpenTopTank;
import Reika.DragonAPI.Interfaces.TileEntity.PlaceNotification;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.ReservoirAPI.TankHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityReservoir extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler, NBTMachine, BreakAction,
AdjacentUpdateWatcher, PlaceNotification, OpenTopTank {

	public static final int CAPACITY = 64000;

	private static final ArrayList<Fluid> creativeFluids = new ArrayList();

	private static final Collection<TankHandler> tankHandlers = new HashSet();
	private static final HashMap<String, FluidEffect> fluidEffects = new HashMap();

	private final StepTimer flowTimer = new StepTimer(TileEntityPiping.getTickDelay());
	private final StepTimer tempTimer = new StepTimer(20).stagger();

	public boolean isCovered = false;

	public boolean isCreative;

	private final HybridTank tank = new HybridTank("reservoir", CAPACITY);

	private boolean[] adjacent = new boolean[10];

	//private CompoundReservoir network;

	public int getLiquidScaled(int par1) {
		return (tank.getLevel()*par1)/CAPACITY;
	}
	/*
	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		if (!world.isRemote)
			this.recalculateCompound();
	}

	public void recalculateCompound() {
		network = new CompoundReservoir(worldObj).addReservoir(this);
		for (int i = 2; i < 6; i++) {
			TileEntity te = this.getAdjacentTileEntity(dirs[i]);
			if (te instanceof TileEntityReservoir) {
				TileEntityReservoir tr = (TileEntityReservoir)te;
				if (tr.getFluid() == this.getFluid() || tr.getFluid() == null || this.getFluid() == null) {
					if (tr.network != null) {
						network.merge(tr.network);
					}
				}
			}
		}
	}
	 */

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		this.updateSides(world, x, y, z);
	}

	private void updateSides(World world, int x, int y, int z) {
		adjacent[8] = MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.RESERVOIR;
		adjacent[4] = MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.RESERVOIR;
		adjacent[6] = MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.RESERVOIR;
		adjacent[2] = MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.RESERVOIR;

		adjacent[1] = MachineRegistry.getMachine(world, x-1, y, z+1) == MachineRegistry.RESERVOIR;
		adjacent[3] = MachineRegistry.getMachine(world, x+1, y, z+1) == MachineRegistry.RESERVOIR;
		adjacent[7] = MachineRegistry.getMachine(world, x-1, y, z-1) == MachineRegistry.RESERVOIR;
		adjacent[9] = MachineRegistry.getMachine(world, x+1, y, z-1) == MachineRegistry.RESERVOIR;

		this.syncAllData(false);
	}

	@Override
	public void onAdjacentUpdate(World world, int x, int y, int z, Block b) {
		this.updateSides(world, x, y, z);
	}

	@Override
	public void onPlaced() {
		this.updateNeighbors();
	}

	@Override
	public void breakBlock() {
		this.updateNeighbors();
	}

	private void updateNeighbors() {
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				if (i != 0 || k != 0) {
					int dx = xCoord+i;
					int dz = zCoord+k;
					if (MachineRegistry.getMachine(worldObj, dx, yCoord, dz) == this.getTile()) {
						((TileEntityReservoir)worldObj.getTileEntity(dx, yCoord, dz)).updateSides(worldObj, dx, yCoord, dz);
					}
				}
			}
		}
	}

	public boolean hasNearbyReservoir(int loc) {
		return adjacent[loc];
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		for (TankHandler th : tankHandlers) {
			int amt = th.onTick(this, tank.getFluid(), this.getPlacer());
			if (amt > 0)
				tank.removeLiquid(amt);
		}

		flowTimer.update();
		if (flowTimer.checkCap())
			this.transferBetween(world, x, y, z);
		if (!isCovered) {
			if (!world.isRemote) {
				BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
				if (world.isRaining() && biome.canSpawnLightningBolt() && world.canLightningStrikeAt(x, y+1, z)) {
					if (this.isEmpty() || (this.getFluid().equals(FluidRegistry.WATER) && this.getLevel() < CAPACITY)) {
						this.addLiquid(25, FluidRegistry.WATER);
					}
				}
			}

			if (!tank.isEmpty()) {
				if (tank.getActualFluid().getDensity(world, x, y, z) < 0 && tank.getActualFluid().isGaseous()) {
					tank.removeLiquid(100); //evaporate
				}
			}
		}

		if (tank.getActualFluid() == null || this.getLevel() <= 0)
			tank.empty();
		else if (isCreative)
			tank.addLiquid(CAPACITY, tank.getActualFluid());

		//if (!world.isRemote && network != null)
		//	network.tick();

		tempTimer.setCap(isCovered ? 30 : 20);

		tempTimer.update();
		if (!world.isRemote && !this.isEmpty() && tempTimer.checkCap()) {
			if (!this.isSurrounded(false)) {
				Fluid f = tank.getActualFluid();
				int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
				int temp = f.getTemperature(world, x, y, z)-273;
				int dT = temp-Tamb;
				int r = 2;
				for (int i = -r; i <= r; i++) {
					for (int j = -r; j <= r; j++) {
						for (int k = -r; k <= r; k++) {
							double dd = ReikaMathLibrary.py3d(i, j, k)+1;
							int hiT = (int)(Tamb+dT/dd/2D);
							//ReikaJavaLibrary.pConsole(dT+" above "+Tamb+" @ "+temp+", h = "+hiT, Side.SERVER);
							ReikaWorldHelper.temperatureEnvironment(world, x+i, y+j, z+k, hiT);
							if (temp > 2500)
								ReikaSoundHelper.playSoundAtBlock(world, x+i, y+j, z+k, "random.fizz", 0.2F, 1F);
						}
					}
				}
				if (temp > 2500) {
					world.setBlock(x, y, z, Blocks.flowing_lava);
					world.setBlock(x+1, y, z, Blocks.flowing_lava);
					world.setBlock(x-1, y, z, Blocks.flowing_lava);
					world.setBlock(x, y, z+1, Blocks.flowing_lava);
					world.setBlock(x, y, z-1, Blocks.flowing_lava);
					ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz", 0.4F, 1F);
				}

				boolean hot = Tamb >= 300;
				hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null;
				hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null;
				if (hot) {
					if (ReikaFluidHelper.isFlammable(f)) {
						world.setBlockToAir(x, y, z);
						world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4, true, true);
					}
				}
			}
		}

	}

	private boolean isSurrounded(boolean reservoirOnly) {
		return (adjacent[2] && adjacent[4] && adjacent[6] && adjacent[8]) || (!reservoirOnly && ReikaWorldHelper.isBlockSurroundedBySolid(worldObj, xCoord, yCoord, zCoord, false));
	}
	/*
	public CompoundReservoir getCompound() {
		return network;
	}

	public void setCompound(CompoundReservoir cr) {
		network = cr;
	}
	 */
	private void transferBetween(World world, int x, int y, int z) {
		if (tank.getLevel() < CAPACITY) {
			for (int i = 2; i < 6; i++) {
				ForgeDirection dir = dirs[i];
				if (this.adjacentOnSide(dir)) {
					int dx = x+dir.offsetX;
					int dy = y+dir.offsetY;
					int dz = z+dir.offsetZ;
					if (this.matchMachine(world, dx, dy, dz)) {
						TileEntityReservoir tile = (TileEntityReservoir)world.getTileEntity(dx, dy, dz);
						if (this.canMixWith(tile)) {
							int diff = tile.getLevel()-this.getLevel();
							if (diff > 1) {
								tile.tank.removeLiquid(diff/2);
								tank.addLiquid(diff/2, tile.getFluid());
							}
						}
					}
				}
			}
		}
	}

	public boolean adjacentOnSide(ForgeDirection dir) {
		switch(dir) {
			case EAST:
				return adjacent[6];
			case NORTH:
				return adjacent[8];
			case SOUTH:
				return adjacent[2];
			case WEST:
				return adjacent[4];
			case DOWN:
			case UNKNOWN:
			case UP:
			default:
				return false;
		}
	}

	private boolean canMixWith(TileEntityReservoir tile) {
		if (tile.getFluid() == null)
			return false;
		if (tank.isEmpty() || this.getFluid().equals(tile.getFluid())) {
			return true;
		}
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setBoolean("cover", isCovered);
		NBT.setBoolean("creative", isCreative);

		NBT.setInteger("sides", ReikaArrayHelper.booleanToBitflags(adjacent));
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		isCovered = NBT.getBoolean("cover");
		isCreative = NBT.getBoolean("creative");

		adjacent = ReikaArrayHelper.booleanFromBitflags(NBT.getInteger("sides"), 10);
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.RESERVOIR;
	}

	@Override
	public int getRedstoneOverride() {
		return (int)(15*tank.getFraction());
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE || m == MachineRegistry.VALVE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public void onEMP() {}

	public Collection<AxisAlignedBB> getComplexHitbox() {
		Collection<AxisAlignedBB> li = new ArrayList();
		li.add(AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.0625, 1));
		if (isCovered) {
			li.add(AxisAlignedBB.getBoundingBox(0.0625, 0.875, 0.0625, 0.9375, 0.9375, 0.9375));
		}
		if (!this.isConnectedOnSide(ForgeDirection.EAST)) {
			li.add(AxisAlignedBB.getBoundingBox(0.9375, 0, 0, 1, 1, 1));
		}
		if (!this.isConnectedOnSide(ForgeDirection.WEST)) {
			li.add(AxisAlignedBB.getBoundingBox(0, 0, 0, 0.0625, 1, 1));
		}
		if (!this.isConnectedOnSide(ForgeDirection.SOUTH)) {
			li.add(AxisAlignedBB.getBoundingBox(0, 0, 0.9375, 1, 1, 1));
		}
		if (!this.isConnectedOnSide(ForgeDirection.NORTH)) {
			li.add(AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 0.0625));
		}
		return li;
	}

	public AxisAlignedBB getHitbox() {
		if (isCovered || this.isEdgePiece(worldObj, xCoord, yCoord, zCoord))
			return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord);
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+0.0625, zCoord+1);
	}

	private boolean isEdgePiece(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m != MachineRegistry.RESERVOIR)
				return true;
		}
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (from == ForgeDirection.UP)
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from == ForgeDirection.UP)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP && ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public boolean canAcceptFluid(Fluid f) {
		return tank.isEmpty() || f.equals(tank.getActualFluid());
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	public void setLevel(int amt, Fluid f) {
		tank.setContents(amt, f);
	}

	public void removeLiquid(int amt) {
		tank.removeLiquid(amt);
	}

	public void addLiquid(int amt, Fluid f) {
		tank.addLiquid(amt, f);
	}

	public boolean isEmpty() {
		return tank.isEmpty();
	}

	public FluidStack getContents() {
		return tank.getFluid();
	}

	public boolean isConnectedOnSide(ForgeDirection dir) {
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		if (this.adjacentOnSide(dir)) {
			TileEntityReservoir te = (TileEntityReservoir)worldObj.getTileEntity(dx, dy, dz);
			if (te == null)
				return false;
			return te.isEmpty() || this.isEmpty() || te.getFluid().equals(this.getFluid());
		}
		return false;
	}

	public void setEmpty() {
		tank.empty();
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.OUTPUT: Flow.INPUT;
	}

	@SideOnly(Side.CLIENT)
	public int getFluidRenderColor() {
		FluidStack fs = tank.getFluid();
		if (fs == null)
			return 0xffffff;
		int clr = fs.tag != null && fs.tag.hasKey("renderColor") ? fs.tag.getInteger("renderColor") : 0xffffff;
		if (this.isInWorld() && fs.getFluid().canBePlacedInWorld()) {
			clr = fs.getFluid().getBlock().colorMultiplier(worldObj, xCoord, yCoord, zCoord);
		}
		return clr;
	}
	/*
	@Override
	public void breakBlock() {
		if (network != null && !worldObj.isRemote) {
			network.removeReservoir(this);
		}
	}
	 */

	public void applyFluidEffectsToEntity(EntityLivingBase e) {
		if (!tank.isEmpty() && !isCovered) {
			Fluid f = tank.getActualFluid();
			FluidEffect eff = fluidEffects.get(f.getName());
			if (eff != null) {
				eff.applyEffect(e);
			}
			if (f.equals(FluidRegistry.LAVA) || f.getTemperature(worldObj, xCoord, yCoord, zCoord) > 500) {
				e.attackEntityFrom(DamageSource.lava, 4);
				e.setFire(12);
			}
			if (f.getTemperature(worldObj, xCoord, yCoord, zCoord) < 250)
				e.attackEntityFrom(DamageSource.wither, 1);
			if (e.isBurning() && ReikaFluidHelper.isFlammable(f)) {
				this.delete();
				worldObj.newExplosion(e, e.posX, e.posY, e.posZ, 4F, true, true);
			}
			if (f.canBePlacedInWorld()) {
				Block b = f.getBlock();
				b.onEntityCollidedWithBlock(worldObj, xCoord, yCoord, zCoord, e);
			}
		}
	}

	@Override
	public NBTTagCompound getTagsToWriteToStack() {
		NBTTagCompound NBT = new NBTTagCompound();
		NBT.setBoolean("cover", isCovered);
		if (this.isEmpty())
			return NBT;
		Fluid f = this.getFluid();
		int level = this.getLevel();
		ReikaNBTHelper.writeFluidToNBT(NBT, f);
		NBT.setInteger("lvl", level);
		return NBT;
	}

	@Override
	public void setDataFromItemStackTag(NBTTagCompound NBT) {
		if (NBT == null) {
			tank.empty();
			isCovered = false;
			return;
		}
		Fluid f = ReikaNBTHelper.getFluidFromNBT(NBT);
		int level = NBT.getInteger("lvl");
		tank.setContents(level, f);

		isCovered = NBT.getBoolean("cover");
	}

	public void combineDataFromItemStackTag(NBTTagCompound NBT) {
		if (NBT == null)
			return;
		Fluid f = ReikaNBTHelper.getFluidFromNBT(NBT);
		if (f != tank.getActualFluid())
			return;
		int level = NBT.getInteger("lvl");
		tank.setContents(level+tank.getLevel(), f);

		isCovered = isCovered || NBT.getBoolean("cover");
	}

	public ArrayList<String> getDisplayTags(NBTTagCompound nbt) {
		ArrayList li = new ArrayList();
		Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
		if (f != null) {
			String fluid = f.getLocalizedName();
			int amt = nbt.getInteger("lvl");
			if (amt > 0) {
				String amount = String.format("%d", amt);
				String contents = "Contents: "+amount+" mB of "+fluid;
				li.add(contents);
			}
		}
		if (nbt.getBoolean("cover"))
			li.add("Covered");
		return li;
	}

	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		ArrayList<NBTTagCompound> li = new ArrayList();
		li.add(null);
		for (int i = 0; i < creativeFluids.size(); i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("lvl", CAPACITY);
			ReikaNBTHelper.writeFluidToNBT(nbt, creativeFluids.get(i));
			li.add(nbt);
		}
		return li;
	}

	private static void addCreativeFluid(String name) {
		Fluid f = FluidRegistry.getFluid(name);
		if (f != null)
			creativeFluids.add(f);
	}

	public static void initCreativeFluids() {
		creativeFluids.clear();
		addCreativeFluid("water");
		addCreativeFluid("lava");
		addCreativeFluid("rc lubricant");
		addCreativeFluid("rc jet fuel");
		addCreativeFluid("rc ethanol");
		addCreativeFluid("rc liquid nitrogen");
		addCreativeFluid("rc ammonia");
		addCreativeFluid("rc sodium");
		addCreativeFluid("rc chlorine");
		addCreativeFluid("rc oxygen");
		addCreativeFluid("rc co2");
		addCreativeFluid("heavy water");
		addCreativeFluid("fuel");
		addCreativeFluid("oil");
		addCreativeFluid("ender");
		addCreativeFluid("redstone");
		addCreativeFluid("glowstone");
		addCreativeFluid("pyrotheum");
		addCreativeFluid("cryotheum");
		addCreativeFluid("coal");
		addCreativeFluid("bop.springwater");
		addCreativeFluid("poison");
		addCreativeFluid("sewage");
		addCreativeFluid("potion crystal");
		addCreativeFluid("chroma");

		fluidEffects.put("rc jet fuel", new PotionFluidEffect(Potion.poison, 0, 200));
		fluidEffects.put("rc ammonia", new PotionFluidEffect(Potion.poison, 0, 200));
		fluidEffects.put("ammonia", new PotionFluidEffect(Potion.poison, 0, 200));
		fluidEffects.put("rc ethanol", new EthanolEffect());
		fluidEffects.put("ethanol", new EthanolEffect());
	}

	public static void addFluidEffect(Fluid f, FluidEffect e) {
		addFluidEffect(f.getName(), e);
	}

	public static void addFluidEffect(String f, FluidEffect e) {
		FluidEffect g = fluidEffects.get(f);
		if (g != null) {
			RotaryCraft.logger.logError("Cannot add effect "+e+" for fluid "+f+"; fluid already mapped to "+g);
		}
		else {
			fluidEffects.put(f, e);
		}
	}

	public static interface FluidEffect {

		public void applyEffect(EntityLivingBase e);

	}

	public static final class PotionFluidEffect implements FluidEffect {

		public final int duration;
		public final int level;
		public final Potion potion;

		public PotionFluidEffect(Potion p, int l, int d) {
			potion = p;
			level = l;
			duration = d;
		}

		@Override
		public void applyEffect(EntityLivingBase e) {
			e.addPotionEffect(new PotionEffect(potion.id, duration, level));
		}

	}

	public static class EthanolEffect implements FluidEffect {

		@Override
		public void applyEffect(EntityLivingBase e) {
			PotionEffect eff = e.getActivePotionEffect(Potion.confusion);
			int dura = 1;
			if (eff != null) {
				dura = eff.getDuration()+1;
			}
			if (dura > 600)
				dura = 600;
			e.addPotionEffect(new PotionEffect(Potion.confusion.id, dura, 3));
		}

	}

	public static final class WaterEffect implements FluidEffect {

		@Override
		public void applyEffect(EntityLivingBase e) {
			e.extinguish();
		}

	}

	@Override
	public int addLiquid(Fluid f, int amt, boolean doAdd) {
		if (!tank.isEmpty() && tank.getActualFluid() != f)
			return 0;
		amt = Math.min(amt, tank.getRemainingSpace());
		if (doAdd)
			tank.addLiquid(amt, f);
		return amt;
	}
}
