/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Interpolation;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.PlanetDimensionHandler;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import streams.block.FixedFlowBlock;

public class TileEntityHydroEngine extends TileEntityEngine {

	public boolean failed;
	private boolean bedrock;

	private Fluid fluidType;
	private double fluidFallSpeed;

	private StreamPowerData streamData = null;

	private static final Interpolation streamWidthFactor = new Interpolation(false);
	private static final Interpolation streamDepthFactor = new Interpolation(false);
	private static final Interpolation streamFallHeights = new Interpolation(false);

	static {
		streamDepthFactor.addPoint(1, 0.5);
		streamDepthFactor.addPoint(2, 1/Math.sqrt(2));
		streamDepthFactor.addPoint(3, 1);
		streamDepthFactor.addPoint(4, 1.25);
		streamDepthFactor.addPoint(5, 1.5);
		streamDepthFactor.addPoint(6, 2);
		streamDepthFactor.addPoint(7, 3);
		streamDepthFactor.addPoint(8, 5);

		streamWidthFactor.addPoint(1, 1/3D);
		streamWidthFactor.addPoint(2, 1/Math.sqrt(2));
		streamWidthFactor.addPoint(3, 0.8);
		streamWidthFactor.addPoint(4, 1);
		streamWidthFactor.addPoint(5, 1);
		streamWidthFactor.addPoint(6, 1.2);
		streamWidthFactor.addPoint(7, 1.5);
		streamWidthFactor.addPoint(8, 2);

		int h = ConfigRegistry.HYDROSTREAMFALLMAX.getValue();
		streamFallHeights.addPoint(h, 64);
		for (int i = 1; i <= 5; i++) {
			streamFallHeights.addPoint(h+i, 64+i*2);
		}
		int d = 64;
		int s = 5;
		while (h > 0 && d > 6) {
			h--;
			d -= s;
			d = Math.max(d, 6);
			s += 3+Math.max(0, s/6-1);
			streamFallHeights.addPoint(h, d);
		}
		streamFallHeights.addPoint(0, 6);
	}

	@Override
	protected void consumeFuel() {

	}

	@Override
	protected void internalizeFuel() {

	}

	public boolean isBedrock() {
		return bedrock;
	}

	@Override
	public void setDataFromPlacer(ItemStack is) {
		if (is.stackTagCompound != null) {
			bedrock = is.stackTagCompound.getBoolean("bed");
		}
	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		boolean hasLube = !lubricant.isEmpty() && lubricant.getActualFluid().equals(FluidRegistry.getFluid("rc lubricant"));
		if (hasLube)
			this.distributeLubricant(world, x, y, z);
		else
			return false;

		if (this.doesBlockObstructBlades(world, x, y+1, z)) {
			omega = 0;
			return false;
		}
		if (this.doesBlockObstructBlades(world, x, y-1, z)) {
			omega = 0;
			return false;
		}

		int[] pos = this.getWaterColumnPos();

		for (int i = -1; i <= 1; i++) {
			if (this.doesBlockObstructBlades(world, 2*x-pos[0], y+i, 2*z-pos[1])) {
				omega = 0;
				return false;
			}
		}

		//ReikaJavaLibrary.pConsole(Block.getIdFromBlock(world.getBlock(x, y-1, z))+":"+world.getBlockMetadata(x, y, z)+" > "+world.getBlock(x, y-1, z));
		Block b = world.getBlock(x, y-1, z);
		if (InterfaceCache.STREAM.instanceOf(b)) {
			return this.handleStream(world, x, y, z, x, y-1, z, meta, b, pos);
		}
		else {
			streamData = null;

			if (!ReikaWorldHelper.isLiquidAColumn(world, pos[0], y, pos[1]))
				return false;

			this.getFluidData(world, x, y, z, meta, pos);

			if (fluidType != null) {
				if (fluidType.getTemperature() >= 900) {
					if (ReikaRandomHelper.doWithChance(2)) {
						world.setBlockToAir(x, y, z);
						boolean lube = !lubricant.isEmpty();
						world.newExplosion(null, x+0.5, y+0.5, z+0.5, lube ? 3 : 2, lube, true);
					}
				}
				if (fluidType.isGaseous() || fluidType.getDensity() <= 0)
					return false;
			}
			return true;
		}
	}

	private boolean isStreamFall(World world, int dx, int dy, int dz) {
		while (world.getBlock(dx, dy, dz) == Blocks.flowing_water || world.getBlock(dx, dy, dz) == Blocks.water) {
			dy--;
		}
		return InterfaceCache.STREAM.instanceOf(world.getBlock(dx, dy, dz));
	}

	private boolean handleStream(World world, int x, int y, int z, int wx, int wy, int wz, int meta, Block b, int[] pos) {
		if (streamData != null) {
			this.checkForOtherStreamEngines(world, x, y, z, meta);
			if (streamData.valid(world))
				return true;
			else
				streamData = null;
		}
		FixedFlowBlock ff = (FixedFlowBlock)b;
		double vel = this.getUsefulVelocity(world, ff.dx(), ff.dz(), meta);
		if (vel > 0) {
			streamData = new StreamPowerData(this, wx, wy, wz, vel);
			streamData.calculatePower(world);
			this.checkForOtherStreamEngines(world, x, y, z, meta);
			return true;
		}
		streamData = null;
		return false;
	}

	public void invalidateStream() {
		streamData = null;
	}

	@Override
	protected void onAdjacentBlockUpdate() {
		this.invalidateStream();
	}

	private void checkForOtherStreamEngines(World world, int x, int y, int z, int meta) {
		if (this.getWriteDirection() == null)
			return;
		boolean continueForward = true;
		boolean continueBackward = true;
		for (int d = 1; d < 4; d++) {
			if (continueForward) {
				continueForward = false;
				int dx = x+this.getWriteDirection().offsetX*d;
				int dz = z+this.getWriteDirection().offsetZ*d;
				if (MachineRegistry.getMachine(world, dx, y, dz) == MachineRegistry.ENGINE) {
					TileEntity te = world.getTileEntity(dx, y, dz);
					if (te instanceof TileEntityHydroEngine) {
						continueForward = true;
						TileEntityHydroEngine th = (TileEntityHydroEngine)te;
						if (th.streamData != null && th.streamData.omega > streamData.omega) {
							streamData = th.streamData;
						}
					}
				}
			}
			if (continueBackward) {
				continueBackward = false;
				int dx = x-this.getWriteDirection().offsetX*d;
				int dz = z-this.getWriteDirection().offsetZ*d;
				if (MachineRegistry.getMachine(world, dx, y, dz) == MachineRegistry.ENGINE) {
					TileEntity te = world.getTileEntity(dx, y, dz);
					if (te instanceof TileEntityHydroEngine) {
						continueBackward = true;
						TileEntityHydroEngine th = (TileEntityHydroEngine)te;
						if (th.streamData != null && th.streamData.omega > streamData.omega) {
							streamData = th.streamData;
						}
					}
				}
			}
		}
	}

	private double getUsefulVelocity(World world, int dx, int dz, int meta) {
		double vx = Math.abs(dx/2);
		double vz = Math.abs(dz/2); // div by 2 since speeds are from 0-2
		switch(meta) {
			case 0:
				return vz;
			case 1:
				return vz;
			case 2:
				return vx;
			case 3:
				return vx;
			default:
				return 0;
		}
	}

	private void distributeLubricant(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (dir == this.getWriteDirection() || dir.getOpposite() == this.getWriteDirection()) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.ENGINE) {
					TileEntityEngine eng = (TileEntityEngine)this.getAdjacentTileEntity(dir);
					if (eng instanceof TileEntityHydroEngine) {
						TileEntityHydroEngine hy = (TileEntityHydroEngine)eng;
						int it = hy.lubricant.getLevel();
						int dL = lubricant.getLevel()-it;
						if (dL > 3) {
							hy.lubricant.addLiquid(dL/4, FluidRegistry.getFluid("rc lubricant"));
							lubricant.removeLiquid(dL/4);
						}
					}
				}
				else if (m == MachineRegistry.RESERVOIR) {
					TileEntityReservoir te = (TileEntityReservoir)this.getAdjacentTileEntity(dir);
					if (!lubricant.isEmpty() && te.canAcceptFluid(FluidRegistry.getFluid("rc lubricant"))) {
						int amt = Math.min(this.getLube(), te.CAPACITY-te.getLevel());
						if (amt > 0) {
							te.addLiquid(amt, FluidRegistry.getFluid("rc lubricant"));
							lubricant.removeLiquid(amt);
						}
					}
				}
			}
		}
		if (!failed && !lubricant.isEmpty() && omega > 0) {
			if (world.getWorldTime()%10 == 0)
				lubricant.removeLiquid(1);
		}
	}

	private boolean doesBlockObstructBlades(World world, int x, int y, int z) {
		return !failed && !ReikaWorldHelper.softBlocks(world, x, y, z);
	}

	private int[] getWaterColumnPos() {
		int[] pos = {xCoord, zCoord};
		switch(this.getBlockMetadata()) {
			case 0:
				pos[1] += -1;
				break;
			case 1:
				pos[1] += 1;
				break;
			case 2:
				pos[0] += 1;
				break;
			case 3:
				pos[0] += -1;
				break;
		}
		return pos;
	}

	private void getFluidData(World world, int x, int y, int z, int meta, int[] pos) {
		Fluid f = ReikaWorldHelper.getFluid(world, pos[0], y, pos[1]);
		fluidType = f;
		if (f == null || f.isGaseous() || f.getDensity() <= 0) {
			fluidFallSpeed = 0;
			return;
		}
		double grav = this.getGravity(world);
		int top = ReikaWorldHelper.findFluidSurface(world, pos[0], y, pos[1], fluidType);
		double dy = top-y;
		if (dy >= 2 && this.isStreamFall(world, pos[0], y, pos[1])) {
			dy = streamFallHeights.getValue(dy);
		}
		dy = Math.pow(dy, 1.5)/32;
		fluidFallSpeed = 0.92*Math.sqrt(2*grav*dy)/Math.max(0.25, Math.pow(f.getViscosity()/1000, 0.375));
	}

	public int getWaterfallHeightForDisplay() {
		int[] pos = this.getWaterColumnPos();
		Fluid f = ReikaWorldHelper.getFluid(worldObj, pos[0], yCoord, pos[1]);
		if (f == null || f.isGaseous() || f.getDensity() <= 0) {
			return 0;
		}
		return MathHelper.ceiling_double_int(ReikaWorldHelper.findFluidSurface(worldObj, pos[0], yCoord, pos[1]))-yCoord;
	}

	private int getEffectiveSpeed(World world, int x, int y, int z) {
		if (this.isStreamPowered())
			return streamData.omega;
		double omg = fluidFallSpeed*2;
		return Math.min((int)omg, type.getSpeed());
	}

	private int getEffectiveTorque(World world, int x, int y, int z) {
		if (this.isStreamPowered())
			return streamData.torque;
		double mdot = Math.min(12000, fluidType.getDensity())*fluidFallSpeed; //*1 since area is 1m^2
		double tau = 0.0625*mdot*fluidFallSpeed;
		return Math.min((int)tau, type.getTorque());
	}

	private void dealPanelDamage(World world, int x, int y, int z, int meta) {
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(a, 1, b);
		List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase ent : in) {
			ent.attackEntityFrom(RotaryCraft.hydrokinetic, 1);
		}
	}

	private double getGravity(World world) {
		double grav = ReikaPhysicsHelper.g;
		grav += PlanetDimensionHandler.getExtraGravity(world)*20; //*20 since tick/s
		return grav;
	}

	private boolean isPartOfArray() {
		return this.isBackEndOfArray() || this.isFrontOfArray();
	}

	public boolean isBackEndOfArray() {
		MachineRegistry to = this.getMachine(write);
		if (to == MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)this.getAdjacentTileEntity(write);
			return te.getEngineType() == EngineType.HYDRO && !((TileEntityHydroEngine)te).failed;
		}
		return false;
	}

	public boolean isFrontOfArray() {
		MachineRegistry from = MachineRegistry.getMachine(worldObj, backx, yCoord, backz);
		MachineRegistry to = this.getMachine(write);
		if (from == MachineRegistry.ENGINE && to != MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)worldObj.getTileEntity(backx, yCoord, backz);
			return te.getEngineType() == EngineType.HYDRO;
		}
		TileEntity te = this.getAdjacentTileEntity(write);
		if (te instanceof TileEntityHydroEngine) {
			return ((TileEntityHydroEngine)te).failed;
		}
		return false;
	}

	private int getArrayTorqueMultiplier() {
		ArrayList<TileEntityHydroEngine> li = new ArrayList();
		int size = 1;
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine && te != this && !li.contains(te)) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			li.add(eng);
			if (eng.getRequirements(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata())) {
				if (eng.omega == omega && !eng.failed) {
					//float fac = eng.getHydroFactor(worldObj, xyz[0], xyz[1], xyz[2], true);
					size++;
					te = eng.getAdjacentTileEntity(eng.write.getOpposite());
				}
				else {
					ReikaParticleHelper.CRITICAL.spawnAroundBlock(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, 5);
					if (rand.nextInt(3) == 0)
						ReikaSoundHelper.playSoundAtBlock(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, "mob.blaze.hit");
					break;
				}
			}
		}
		return size;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		if (this.isFrontOfArray() || !this.isPartOfArray())
			SoundRegistry.HYDRO.playSoundAtBlock(world, x, y, z, 1F*volume, 0.9F*pitchMultiplier);
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return Math.max(1, this.getEffectiveSpeed(world, x, y, z));
	}

	@Override
	protected int getGenTorque(World world, int x, int y, int z, int meta) {
		if (failed)
			return 1;
		int torque = this.getEffectiveTorque(world, x, y, z)*this.getArrayTorqueMultiplier();
		double ratio = (double)torque/EngineType.HYDRO.getTorque();
		int r = bedrock ? 16 : 4;
		if (ratio > r) {
			this.fail(world, x, y, z);
		}
		return torque;
	}

	private void fail(World world, int x, int y, int z) {
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.break");
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 0.2F, 0.5F);
		/*
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			eng.getGenTorque(world, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata());
			te = eng.getAdjacentTileEntity(write.getOpposite());
		}*/
		failed = true;
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.dealPanelDamage(world, x, y, z, meta);
		this.spawnParticles(world, x, y, z);
		if (failed) {
			ForgeDirection dir = this.getWriteDirection();
			ForgeDirection left = ReikaDirectionHelper.getLeftBy90(dir);
			for (int i = -1; i <= y; i++) {
				int dx = x+left.offsetX;
				int dy = y+i;
				int dz = z+left.offsetZ;
				if (!InterfaceCache.STREAM.instanceOf(world.getBlock(dx, dy, dz)))
					ReikaWorldHelper.dropAndDestroyBlockAt(world, dx, dy, dz, null, false, true);
			}
			ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y+1, z, null, false, true);
			if (!InterfaceCache.STREAM.instanceOf(world.getBlock(x, y-1, z)))
				ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y-1, z, null, false, true);
		}
	}

	private void spawnParticles(World world, int x, int y, int z) {
		int[] xz = this.getWaterColumnPos();
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, x, y, z, 16);
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, x-(xz[0]-x), y, z-(xz[1]-z), 16);
		if (this.isStreamPowered()) {

		}
		else {
			ReikaParticleHelper.RAIN.spawnAroundBlock(world, xz[0], y, xz[1], 16);
		}
		if (failed) {
			if (rand.nextInt(5) == 0)
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "mob.blaze.hit");
			ReikaParticleHelper.CRITICAL.spawnAroundBlockWithOutset(world, x, y, z, 3, 0.25);
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		failed = NBT.getBoolean("fail");
		bedrock = NBT.getBoolean("bedrock");

		if (NBT.hasKey("stream"))
			streamData = StreamPowerData.readFromNBT(NBT.getCompoundTag("stream"));
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("fail", failed);
		NBT.setBoolean("bedrock", bedrock);

		if (streamData != null) {
			NBT.setTag("stream", streamData.writeToNBT());
		}
	}

	public void makeBedrock() {
		bedrock = true;
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		PowerSourceList psl = super.getPowerSources(io, caller);
		ArrayList<TileEntityHydroEngine> li = new ArrayList();
		ArrayList<TileEntityHydroEngine> li2 = new ArrayList();
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine && te != this && !li2.contains(te)) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			li2.add(eng);
			if (eng.getRequirements(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata())) {
				if (eng.omega == omega && !eng.failed) {
					li.add(eng);
					te = eng.getAdjacentTileEntity(eng.write.getOpposite());
				}

			}
		}
		for (int i = 0; i < li.size(); i++) {
			psl.addSource(li.get(i));
		}
		return psl;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord).expand(1, 1, 1);
	}

	public boolean isStreamPowered() {
		return streamData != null;
	}

	public ArrayList<String> getStreamData() {
		return streamData.getDataDisplay();
	}

	private static class StreamPowerData {

		private static final long startTime = ReikaJavaLibrary.getLaunchTime();

		private long gameInstance = startTime;

		private final Coordinate engineSource;
		private final Coordinate waterSource;
		private final double normalVelocity;

		private int localDepth;
		private int localWidth;
		private double powerFactor;
		private int torque;
		private int omega;

		private long lastFullCheck = -1;

		private StreamPowerData(TileEntityHydroEngine te, int wx, int wy, int wz, double vel) {
			this(new Coordinate(te), new Coordinate(wx, wy, wz), vel);
		}

		private StreamPowerData(Coordinate c1, Coordinate c2, double vel) {
			engineSource = c1;
			waterSource = c2;
			normalVelocity = vel;
		}

		private void calculateGeography(World world) {
			localWidth = 8;
			localDepth = 8;
			for (int i = 0; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
				if (dir != ForgeDirection.UP) {
					for (int d = 1; d < (dir.offsetY == 0 ? localWidth : localDepth); d++) {
						int dx = waterSource.xCoord+dir.offsetX*d;
						int dy = waterSource.yCoord+dir.offsetY*d;
						int dz = waterSource.zCoord+dir.offsetZ*d;
						Block b = world.getBlock(dx, dy, dz);
						if (!(b instanceof FixedFlowBlock)) {
							if (dir.offsetY == 0)
								localWidth = d-1;
							else
								localDepth = d-1;
							break;
						}
					}
				}
			}
		}

		private void calculatePower(World world) {
			this.calculateGeography(world);
			powerFactor = Math.min(1, this.getStreamSpeedScale());
			omega = (int)(EngineType.HYDRO.getSpeed()*powerFactor);
			torque = (int)(EngineType.HYDRO.getTorque()*powerFactor);
		}

		private boolean valid(World world) {
			return gameInstance == startTime && engineSource.getTileEntity(world) instanceof TileEntityHydroEngine && waterSource.getBlock(world) instanceof FixedFlowBlock;
		}

		private double getStreamSpeedScale() {
			return Math.sqrt(streamDepthFactor.getValue(localDepth+1)*streamWidthFactor.getValue(localWidth+1)*normalVelocity);
		}

		private NBTTagCompound writeToNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("depth", localDepth);
			tag.setInteger("width", localWidth);
			tag.setDouble("power", powerFactor);
			tag.setDouble("velocity", normalVelocity);
			tag.setInteger("torque", torque);
			tag.setInteger("omega", omega);
			engineSource.writeToNBT("engine", tag);
			waterSource.writeToNBT("water", tag);
			tag.setLong("time", gameInstance);
			return tag;
		}

		private static StreamPowerData readFromNBT(NBTTagCompound tag) {
			if (tag == null || tag.hasNoTags())
				return null;
			Coordinate eng = Coordinate.readFromNBT("engine", tag);
			Coordinate water = Coordinate.readFromNBT("water", tag);
			double vel = tag.getDouble("velocity");
			StreamPowerData ret = new StreamPowerData(eng, water, vel);
			ret.localDepth = tag.getInteger("depth");
			ret.localWidth = tag.getInteger("width");
			ret.powerFactor = tag.getDouble("power");
			ret.torque = tag.getInteger("torque");
			ret.omega = tag.getInteger("omega");
			ret.gameInstance = tag.getLong("time");
			return ret;
		}

		public ArrayList<String> getDataDisplay() {
			ArrayList<String> li = new ArrayList();
			li.add("Width: "+(localWidth+1)+"m");
			li.add("Depth: "+(localDepth+1)+"m");
			li.add("Local Flow Velocity: "+String.format("%.0f", normalVelocity*100)+"%%");
			li.add("Efficiency: "+String.format("%.2f", powerFactor*powerFactor*100)+"%%");
			return li;
		}
	}
}
