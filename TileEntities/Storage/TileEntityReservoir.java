/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Storage;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityReservoir extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler, NBTMachine {

	public boolean isCreative;

	public static final int CAPACITY = 64000;

	public boolean isCovered = false;

	private HybridTank tank = new HybridTank("reservoir", CAPACITY);

	private static final ArrayList<Fluid> creativeFluids = new ArrayList();

	private StepTimer flowTimer = new StepTimer(TileEntityPiping.getTickDelay());

	public int getLiquidScaled(int par1) {
		return (tank.getLevel()*par1)/CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		flowTimer.update();
		if (flowTimer.checkCap())
			this.transferBetween(world, x, y, z);
		if (!isCovered) {
			if (!world.isRemote) {
				BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
				if (world.isRaining() && biome.canSpawnLightningBolt() && worldObj.canBlockSeeTheSky(x, y+1, z)) {
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

		if (!world.isRemote && !this.isEmpty() && rand.nextInt(this.getThermalTickChance()) == 0) {
			int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
			int temp = tank.getActualFluid().getTemperature(world, x, y, z)-273;
			int dT = temp-Tamb;
			int r = 2;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						double dd = ReikaMathLibrary.py3d(i, j, k)+1;
						int hiT = (int)(Tamb+dT/dd/2D);
						ReikaWorldHelper.temperatureEnvironment(world, x+i, y+j, z+k, hiT);
						if (temp > 2500)
							ReikaSoundHelper.playSoundAtBlock(world, x+i, y+j, z+k, "random.fizz", 0.2F, 1F);
					}
				}
			}
			if (temp > 2500) {
				world.setBlock(x, y, z, Block.lavaMoving.blockID);
				world.setBlock(x+1, y, z, Block.lavaMoving.blockID);
				world.setBlock(x-1, y, z, Block.lavaMoving.blockID);
				world.setBlock(x, y, z+1, Block.lavaMoving.blockID);
				world.setBlock(x, y, z-1, Block.lavaMoving.blockID);
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz", 0.4F, 1F);
			}

			boolean hot = Tamb >= 300;
			hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null;
			hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null;
			if (hot) {
				Fluid f = tank.getActualFluid();
				boolean flammable = f.equals(FluidRegistry.getFluid("rc ethanol")) || f.equals(FluidRegistry.getFluid("jet fuel"));
				flammable = flammable || f.equals(FluidRegistry.getFluid("oil")) || f.equals(FluidRegistry.getFluid("fuel"));
				flammable = flammable || f.equals(FluidRegistry.getFluid("ethanol")) || f.equals(FluidRegistry.getFluid("creosote"));
				if (flammable) {
					world.setBlock(x, y, z, 0);
					world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4, true, true);
				}
			}
		}
	}

	private int getThermalTickChance() {
		return isCovered ? 20 : 10;
	}

	private void transferBetween(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			if (tank.getLevel() < CAPACITY) {
				if (this.matchMachine(world, dx, dy, dz)) {
					TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(dx, dy, dz);
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

	private boolean canMixWith(TileEntityReservoir tile) {
		if (tile.getFluid() == null)
			return false;
		if (tank.isEmpty() || this.getFluid().equals(tile.getFluid())) {
			return true;
		}
		return false;
	}


	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setBoolean("cover", isCovered);
		NBT.setBoolean("creative", isCreative);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		isCovered = NBT.getBoolean("cover");
		isCreative = NBT.getBoolean("creative");
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.RESERVOIR;
	}

	@Override
	public int getRedstoneOverride() {
		return (int)(15*tank.getFraction());
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE || m == MachineRegistry.VALVE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public void onEMP() {}

	public AxisAlignedBB getHitbox() {
		if (isCovered || this.isEdgePiece(worldObj, xCoord, yCoord, zCoord))
			return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord);
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord+1, yCoord+0.0625, zCoord+1);
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
		if (from == ForgeDirection.UP)
			return null;
		return tank.drain(resource.amount, doDrain);
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
		return from != ForgeDirection.UP;
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
		if (this.matchMachine(worldObj, dx, dy, dz)) {
			TileEntityReservoir te = (TileEntityReservoir)worldObj.getBlockTileEntity(dx, dy, dz);
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

	@Override
	public NBTTagCompound getTagsToWriteToStack() {
		if (this.isEmpty())
			return null;
		NBTTagCompound NBT = new NBTTagCompound();
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
			return;
		}
		Fluid f = ReikaNBTHelper.getFluidFromNBT(NBT);
		int level = NBT.getInteger("lvl");
		tank.setContents(level, f);

		if (NBT.getBoolean("creative"))
			isCreative = true;
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
		return li;
	}

	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		ArrayList<NBTTagCompound> li = new ArrayList();
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
		addCreativeFluid("lubricant");
		addCreativeFluid("jet fuel");
		addCreativeFluid("rc ethanol");
		addCreativeFluid("liquid nitrogen");
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
	}
}
