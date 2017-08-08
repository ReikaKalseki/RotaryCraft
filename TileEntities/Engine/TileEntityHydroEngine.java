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

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import streams.block.FixedFlowBlock;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityHydroEngine extends TileEntityEngine {

	public boolean failed;
	private boolean bedrock;

	private Fluid fluidType;
	private double fluidFallSpeed;

	private boolean streamPower = false;
	private int streamTorque = 0;
	private int streamOmega = 0;

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
			return this.handleStream(world, x, y, z, meta, b, pos);
		}
		else {
			streamPower = false;

			if (!ReikaWorldHelper.isLiquidAColumn(world, pos[0], y, pos[1]))
				return false;

			this.getFluidData(world, x, y, z, pos);

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

	private boolean handleStream(World world, int x, int y, int z, int meta, Block b, int[] pos) {
		FixedFlowBlock ff = (FixedFlowBlock)b;
		double vel = this.getUsefulVelocity(ff.dx(), ff.dz(), meta);
		if (vel > 0) {
			streamPower = true;
			Block b2 = world.getBlock(pos[0], y, pos[1]);
			boolean fall = (FluidRegistry.lookupFluidForBlock(b2) == FluidRegistry.WATER && world.getBlock(pos[0], y-1, pos[1]) instanceof FixedFlowBlock) || (b2 instanceof FixedFlowBlock && this.getUsefulVelocity(((FixedFlowBlock)b2).dx(), ((FixedFlowBlock)b2).dz(), meta) > 0);
			double grav = this.getGravity(world);
			double vh_sq = fall ? 2*grav*1 : 0;
			double vtot = Math.sqrt(vh_sq+vel*vel);
			streamOmega = (int)(vtot*2);
			double F = ReikaEngLibrary.rhowater*vtot*vtot; //A=1
			double fudge = 0.875;
			streamTorque = 2*ReikaMathLibrary.ceil2exp((int)(F*0.5*fudge));
			return true;
		}
		streamPower = false;
		return false;
	}

	private double getUsefulVelocity(int dx, int dz, int meta) {
		double vx = Math.abs(2D*dx);
		double vz = Math.abs(2D*dz);
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

	private void getFluidData(World world, int x, int y, int z, int[] pos) {
		Fluid f = ReikaWorldHelper.getFluid(world, pos[0], y, pos[1]);
		fluidType = f;
		if (f == null || f.isGaseous() || f.getDensity() <= 0) {
			fluidFallSpeed = 0;
			return;
		}
		double grav = this.getGravity(world);
		double dy = (ReikaWorldHelper.findFluidSurface(world, pos[0], y, pos[1])-y)-0.5;
		dy = Math.pow(dy, 1.5)/32;
		fluidFallSpeed = 0.92*Math.sqrt(2*grav*dy)/Math.max(0.25, Math.pow(f.getViscosity()/1000, 0.375));
	}

	private int getEffectiveSpeed(World world, int x, int y, int z) {
		if (streamPower)
			return streamOmega;
		double omg = fluidFallSpeed*2;
		return Math.min((int)omg, type.getSpeed());
	}

	private int getEffectiveTorque(World world, int x, int y, int z) {
		if (streamPower)
			return streamTorque;
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
		if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			grav += ig.getGravity()*20; //*20 since tick/s
		}
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
		int r = bedrock ? 16 : 4;
		double ratio = (double)torque/EngineType.HYDRO.getTorque();
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
				ReikaWorldHelper.dropAndDestroyBlockAt(world, x+left.offsetX, y+i, z+left.offsetZ, null, false, true);
			}
			ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y+1, z, null, false, true);
			ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y-1, z, null, false, true);
		}
	}

	private void spawnParticles(World world, int x, int y, int z) {
		int[] xz = this.getWaterColumnPos();
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, x, y, z, 16);
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, xz[0], y, xz[1], 16);
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
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("fail", failed);
		NBT.setBoolean("bedrock", bedrock);
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
}
