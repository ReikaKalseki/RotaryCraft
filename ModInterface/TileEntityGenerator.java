/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ModInteract.ReikaEUHelper;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

//@Strippable(value = {"universalelectricity.api.electricity.IVoltageOutput", "universalelectricity.api.energy.IEnergyInterface"})
@Strippable(value = {"ic2.api.energy.tile.IEnergySource"})
public class TileEntityGenerator extends PoweredLiquidReceiver implements IEnergySource {

	//public static final int OUTPUT_VOLTAGE = 24000;
	//public static final float POWER_FACTOR = 0.875F;

	private ForgeDirection facingDir;

	public static final boolean hardModeEU = ConfigRegistry.HARDEU.getState() || ModList.GREGTECH.isLoaded();

	public ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GENERATOR;
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
		this.getIOSides(world, x, y, z, meta);
		if (hardModeEU) {
			if (tank.isEmpty()) {
				omega = torque = 0;
				power = 0;
				return;
			}
			else {
				if (power > 0) {
					tank.removeLiquid(1);
				}
			}
		}

		this.getPower(false);

		//ReikaJavaLibrary.pConsole(this.getOfferedEnergy(), Side.SERVER);
		/*
		if (power > 0) {
			int dx = x+facingDir.offsetX;
			int dy = y+facingDir.offsetY;
			int dz = z+facingDir.offsetZ;
			Block b = world.getBlock(dx, dy, dz);
			if (b != Blocks.air) {
				int metadata = world.getBlockMetadata(dx, dy, dz);
				if (b.hasTileEntity(metadata)) {
					TileEntity te = world.getTileEntity(dx, dy, dz);

				}
			}
		}*/
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.EAST;
			break;
		}
		read = facingDir;
		write = read.getOpposite();
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		return direction == this.getFacing().getOpposite();
	}

	@Override
	public double getOfferedEnergy() {
		return power/ReikaEUHelper.WATTS_PER_EU;
	}

	@Override
	public void drawEnergy(double amount) {

	}

	@Override
	public void onFirstTick(World world, int x, int y, int z) {
		if (!world.isRemote && ModList.IC2.isLoaded())
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalidate) {
		this.removeTileFromNet(world, x, y, z);
	}

	private void removeTileFromNet(World world, int x, int y, int z) {
		if (!world.isRemote && ModList.IC2.isLoaded())
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
	}

	@Override
	public int getSourceTier() {
		return ReikaEUHelper.getIC2TierFromPower(this.getOfferedEnergy());
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return f != null && (f.equals(FluidRegistry.getFluid("ic2coolant")) || f.equals(FluidRegistry.getFluid("liquid nitrogen")));
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public int getCapacity() {
		return 6000;
	}
}
