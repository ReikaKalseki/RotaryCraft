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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntityPneumaticEngine extends TileEntityIOMachine implements IPowerReceptor, IPipeConnection, SimpleProvider,
PowerGenerator, GuiController {

	private int MJ;

	private PowerHandler pp;

	private ForgeDirection facingDir;

	public static final int maxMJ = 36000; //up to 1 MC megajoule

	private StepTimer sound = new StepTimer(72);

	private static final int GENOMEGA = 1024;

	private int base = -1;

	public int storedpower;

	private static final int MINBASE = -1;
	private static final int MAXBASE = 11; //2048 Nm -> 2.09 MW

	public TileEntityPneumaticEngine()
	{
		super();
		pp = new PowerHandler(this, PowerHandler.Type.MACHINE);
		pp.configure(0, maxMJ, 0, maxMJ);
		pp.configurePowerPerdition(0, 0);
		sound.setTick(sound.getCap());
	}

	public boolean hasEnoughEnergy() {
		float energy = pp.getEnergyStored();
		return energy > this.getMJPerTick();
	}

	public long getPowerLevel() {
		return GENOMEGA*this.getTorqueLevel();
	}

	public int getSpeed() {
		if (base < 0)
			return 0;
		return GENOMEGA;
	}

	public int getTorqueLevel() {
		if (base < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, base);
	}

	public float getMJPerTick() {
		return (float)(this.getPowerLevel()/ReikaBuildCraftHelper.getWattsPerMJ());
	}

	public int getStoredEnergy() {
		return storedpower;
	}

	public float getPercentStorage() {
		return pp.getEnergyStored()/maxMJ;
	}

	public int getEnergyScaled(int h) {
		return (int)(this.getPercentStorage()*h);
	}

	public int getTier() {
		return base;
	}

	public void increment() {
		if (base < MAXBASE)
			base++;
	}

	public void decrement() {
		if (base > MINBASE)
			base--;
	}

	@Override
	public boolean canProvidePower() {
		return true;
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

		if (pp.getEnergyStored() > 0)
			pp.addEnergy(0.01F); //To nullify the mandatory power loss... why the HELL was that added?

		if (!world.isRemote)
			storedpower = (int)pp.getEnergyStored();
		if (storedpower < 0)
			storedpower = (int)pp.getMaxEnergyStored();

		//ReikaJavaLibrary.pConsoleSideOnly(this.getMJPerTick()+" && "+pp.getEnergyStored(), Side.SERVER);

		if (!this.hasEnoughEnergy()) {
			torque = 0;
			omega = 0;
			power = 0;
			return;
		}
		if (!world.isRemote) {
			float mj = pp.getEnergyStored();

			torque = this.getTorqueLevel();
			omega = this.getSpeed();

			power = (long)torque*(long)omega;

			pp.useEnergy(this.getMJPerTick(), this.getMJPerTick(), true);

			this.basicPowerReceiver();

			sound.update();

			if (power > 0) {
				if (sound.checkCap())
					SoundRegistry.PNEUMATIC.playSoundAtBlock(world, x, y, z, 1.2F, 1);
			}
		}
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

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public long getMaxPower() {
		return (long)(ReikaBuildCraftHelper.getWattsPerMJ()*pp.getEnergyStored());
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("tier", base);
		NBT.setInteger("storage", storedpower);
		pp.writeToNBT(NBT);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		base = NBT.getInteger("tier");
		storedpower = NBT.getInteger("storage");
		pp.readFromNBT(NBT);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return type == PipeType.POWER && this.isPipeConnected(with) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return pp.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {

	}

	@Override
	public World getWorld() {
		return worldObj;
	}

}
