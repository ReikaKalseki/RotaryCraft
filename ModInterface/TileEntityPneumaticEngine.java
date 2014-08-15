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

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntityPneumaticEngine extends EnergyToPowerBase implements IPowerReceptor, IPipeConnection {

	private PowerHandler pp;

	public static final int maxMJ = 36000;

	private StepTimer sound = new StepTimer(72);

	public TileEntityPneumaticEngine() {
		super();
		pp = new PowerHandler(this, PowerHandler.Type.MACHINE);
		pp.configure(0, maxMJ, 0, maxMJ);
		pp.configurePowerPerdition(0, 0);
		sound.setTick(sound.getCap());
	}

	@Override
	public boolean isValidSupplier(TileEntity te) {
		if (te == null)
			return false;
		if (te.getClass().getSimpleName().contains("PipePower") || te.getClass().getSimpleName().equals("TileGenericPipe"))
			return true;
		return false;
	}

	@Override
	public int getConsumedUnitsPerTick() {
		return (int)Math.ceil(this.getMJPerTick());
	}

	public float getMJPerTick() {
		return (float)(this.getPowerLevel()/ReikaBuildCraftHelper.getWattsPerMJ());
	}

	@Override
	public int getMaxStorage() {
		return maxMJ;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PNEUENGINE;
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
		if (DragonAPICore.debugtest) {
			pp.setEnergy(pp.getMaxEnergyStored());
		}
		this.getIOSides(world, x, y, z, meta);

		if (pp.getEnergyStored() > 0)
			pp.addEnergy(0.01F); //To nullify the mandatory power loss... why the HELL was that added?

		pp.configure(0, maxMJ, 0, maxMJ);

		if (!world.isRemote)
			storedEnergy = (int)pp.getEnergyStored();
		if (storedEnergy < 0)
			storedEnergy = (int)pp.getMaxEnergyStored();

		this.updateSpeed();
		if (!world.isRemote) {
			sound.update();

			if (power > 0) {
				if (sound.checkCap())
					SoundRegistry.PNEUMATIC.playSoundAtBlock(world, x, y, z, this.isMuffled() ? 0.3F : 1.2F, 1);
			}
		}

		this.basicPowerReceiver();
	}

	@Override
	protected void usePower() {
		float amt = this.getMJPerTick();
		pp.useEnergy(amt, amt, true);
	}

	public int powerRequest(ForgeDirection from) {
		float needed = pp.getMaxEnergyStored() - pp.getEnergyStored();
		return (int) Math.ceil(Math.min(pp.getMaxEnergyReceived(), needed));
	}

	public boolean isPipeConnected(ForgeDirection with) {
		switch(this.getBlockMetadata()) {
		case 0:
			return with == ForgeDirection.NORTH;
		case 1:
			return with == ForgeDirection.WEST;
		case 2:
			return with == ForgeDirection.SOUTH;
		case 3:
			return with == ForgeDirection.EAST;
		}
		return false;
	}

	@Override
	public long getMaxPower() {
		return (long)(ReikaBuildCraftHelper.getWattsPerMJ()*pp.getEnergyStored());
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		pp.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		pp.readFromNBT(NBT);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return type == PipeType.POWER && this.isPipeConnected(with) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.isValidSupplier(this.getAdjacentTileEntity(side)) ? pp.getPowerReceiver() : null;
	}

	@Override
	public void doWork(PowerHandler workProvider) {

	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public String getUnitDisplay() {
		return "MJ";
	}

	@Override
	public Color getPowerColor() {
		return new Color(50, 170, 255);
	}

	//@Override
	//public int getMaxSpeedBase(int tier) {
	//	return 7+4*tier;
	//}

}