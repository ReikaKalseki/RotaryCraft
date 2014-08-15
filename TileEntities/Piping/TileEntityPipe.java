/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.PumpablePipe;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TileEntityPipe extends TileEntityPiping implements TemperatureTE, PumpablePipe {

	private Fluid liquid;
	private int liquidLevel = 0;

	private int temperature;

	public static final int HORIZLOSS = 1*0;	// all are 1(friction)+g (10m) * delta h (0 or 1m)
	public static final int UPLOSS = 1*0;
	public static final int DOWNLOSS = -1*0;

	public static final int UPPRESSURE = 40;
	public static final int HORIZPRESSURE = 20;
	public static final int DOWNPRESSURE = 0;

	public static final int MAXPRESSURE = 2400000;

	public int getPressure() {
		if (liquid == null || liquidLevel <= 0)
			return 101300;
		//p = rho*R*T approximation
		if (liquid.isGaseous())
			return 101300+(128*(int)(liquidLevel/1000D*liquid.getTemperature()*Math.abs(liquid.getDensity())/1000D));
		else
			return 101300+liquidLevel*24;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z, meta);

		if (ModList.BCFACTORY.isLoaded() && ModList.REACTORCRAFT.isLoaded()) { //Only if, since need a way to pipe it
			if (this.contains(FluidRegistry.getFluid("uranium hexafluoride")) || this.contains(FluidRegistry.getFluid("hydrofluoric acid"))) {
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz");
				for (int i = 0; i < 6; i++) {
					ForgeDirection dir = dirs[i];
					int dx = x+dir.offsetX;
					int dy = y+dir.offsetY;
					int dz = z+dir.offsetZ;
					MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
					if (m == MachineRegistry.PIPE) {
						TileEntityPipe p = (TileEntityPipe)world.getTileEntity(dx, dy, dz);
						p.setFluid(liquid);
						p.addFluid(5);
						//ReikaParticleHelper.SMOKE.spawnAroundBlock(world, dx, dy, dz, 8);
					}
				}
				world.setBlockToAir(x, y, z);
				ReikaParticleHelper.SMOKE.spawnAroundBlock(world, x, y, z, 8);
			}
		}

		if (rand.nextInt(60) == 0) {
			ReikaWorldHelper.temperatureEnvironment(world, x, y, z, this.getTemperature());
		}

		if (liquid != null) {
			int temp = liquid.getTemperature(worldObj, xCoord, yCoord, zCoord);
			temperature = temp > 750 ? temp-425 : temp-273;
			if (temperature > 2500) {
				this.overheat(worldObj, xCoord, yCoord, zCoord);
			}
		}
		else {
			temperature = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		}

		if (this.getPressure() > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
		}
	}

	private void overpressure(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (liquid.canBePlacedInWorld()) {
				world.setBlock(x, y, z, liquid.getBlock());
			}
			else {
				world.setBlockToAir(x, y, z);
			}
		}
		world.markBlockForUpdate(x, y, z);
		world.notifyBlockOfNeighborChange(x, y, z, liquid.getBlock());
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
		ReikaParticleHelper.EXPLODE.spawnAroundBlock(world, x, y, z, 1);
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.VALVE || m == MachineRegistry.SPILLER || m == MachineRegistry.SEPARATION || m == MachineRegistry.BYPASS || m == MachineRegistry.SUCTION;
	}

	@Override
	public IIcon getBlockIcon() {
		return BlockRegistry.DECO.getBlockInstance().getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return liquid != null && liquidLevel > 0;
	}

	@Override
	public Fluid getFluidType() {
		return liquid;
	}

	public boolean contains(Fluid f) {
		return f.equals(liquid);
	}

	@Override
	public void setFluid(Fluid f) {
		liquid = f;
	}

	@Override
	public int getFluidLevel() {
		return liquidLevel;
	}

	@Override
	protected void setLevel(int amt) {
		liquidLevel = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return true;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return false;
		if (f.equals(FluidRegistry.getFluid("lubricant")))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc co2")))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc hot co2")))
			return false;
		if (f.equals(FluidRegistry.getFluid("chlorine")))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc oxygen")))
			return false;
		return true;
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public Block getPipeBlockType() {
		return BlockRegistry.DECO.getBlockInstance();
	}

	@Override
	public boolean canIntakeFromIFluidHandler(ForgeDirection side) {
		return side.offsetY != 0;
	}

	@Override
	public boolean canOutputToIFluidHandler(ForgeDirection side) {
		return side.offsetY == 0;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int getThermalDamage() {
		return this.getTemperature() >= 100 ? this.getTemperature()/400 : 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		BlockArray blocks = new BlockArray();
		MachineRegistry m = this.getMachine();
		blocks.recursiveAddWithMetadata(world, x, y, z, m.getBlock(), m.getMachineMetadata());

		for (int i = 0; i < blocks.getSize(); i++) {
			int[] xyz = blocks.getNthBlock(i);
			ReikaSoundHelper.playSoundAtBlock(world, xyz[0], xyz[1], xyz[2], "random.fizz", 0.4F, 1);
			ReikaParticleHelper.LAVA.spawnAroundBlock(world, xyz[0], xyz[1], xyz[2], 36);
			world.setBlock(xyz[0], xyz[1], xyz[2], Blocks.flowing_lava);
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temp", temperature);
	}

	@Override
	public boolean canTransferTo(PumpablePipe p, ForgeDirection dir) {
		if (p instanceof TileEntityPipe) {
			Fluid f = ((TileEntityPipe)p).liquid;
			return f != null ? f.equals(liquid) : true;
		}
		return false;
	}

	@Override
	public void transferFrom(PumpablePipe from, int amt) {
		((TileEntityPipe)from).liquidLevel -= amt;
		liquid = ((TileEntityPipe)from).liquid;
		liquidLevel += amt;
	}
}