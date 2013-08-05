/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Models.ModelPneumatic;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityEngineController;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipeConnection;

public class TileEntityPneumaticEngine extends TileEntityIOMachine implements IPowerReceptor, IPipeConnection, SimpleProvider {

	private int MJ;

	private IPowerProvider pp;

	private ForgeDirection facingDir;

	public static final int maxMJ = 1000000; //up to 1 MC megajoule

	public TileEntityPneumaticEngine()
	{
		super();
		pp = new CompressorPowerProvider();
		pp.configure(0, 0, maxMJ, 0, maxMJ);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelPneumatic();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PNEUENGINE.ordinal();
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

		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)world.getBlockTileEntity(x, y-1, z);
			if (te != null) {
				if (!te.enabled) {
					omega = 0;
					torque = 0;
					power = 0;
					return;
				}
			}
		}

		float mj = pp.getEnergyStored();
		PneuEngineStage st = PneuEngineStage.getStageFromMJ(mj);
		if (st == st.OFF)
			pp.useEnergy(mj, mj, true);
		//ReikaJavaLibrary.pConsoleSideOnly(st+" from "+mj, Side.SERVER);
		torque = st.getTorque();
		omega = st.getOmega();
		power = (long)torque*(long)omega;
		//pp.useEnergy(mj, mj, true);
		pp.useEnergy(st.getConsumedMJ(), st.getConsumedMJ(), true);

		TileEntity te = world.getBlockTileEntity(writex, writey, writez);
		if (te instanceof ShaftPowerReceiver) {
			this.writePowerToReciever((ShaftPowerReceiver)te);
		}
	}

	@Override
	public void setPowerProvider(IPowerProvider provider) {

	}

	@Override
	public IPowerProvider getPowerProvider() {
		return pp;
	}

	@Override
	public void doWork() {

	}

	@Override
	public int powerRequest(ForgeDirection from) {
		IPowerProvider p = this.getPowerProvider();
		float needed = p.getMaxEnergyStored() - p.getEnergyStored();
		return (int) Math.ceil(Math.min(p.getMaxEnergyReceived(), needed));
	}

	@Override
	public boolean isPipeConnected(ForgeDirection with) {
		//return with == facingDir.getOpposite();
		return true;
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 0:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

}
