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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Auxiliary.PressureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class TileEntityAirCompressor extends TileEntityPowerReceiver implements IPowerEmitter, PressureTE {

	private int pressure;

	private boolean isOut;

	public static final int MAXPRESSURE = 1000;

	private ForgeDirection facingDir;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelCompressor();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (omega <= 1 || !this.hasOutputTile()) {
			if (phi > 0) {
				double speed = 0.0125;
				if (isOut)
					phi += speed;
				else
					phi -= speed;
				if (phi <= 0)
					isOut = true;
				if (phi >= 0.5)
					isOut = false;
			}
			return;
		}
		double speed = ReikaMathLibrary.logbase(omega, 2)*0.025/6D;
		if (speed > 0.125)
			speed = 0.125;

		if (isOut) {
			phi += speed;
			if (phi <= 0.095)
				this.playSound(world, x, y, z);
		}
		else
			phi -= speed;
		if (phi <= 0) {
			isOut = true;
			phi = 0;
		}
		if (phi >= 0.5) {
			isOut = false;
		}
	}

	private void playSound(World world, int x, int y, int z) {
		int p = (int)(ReikaMathLibrary.logbase(omega, 2)/8);
		SoundRegistry.playSoundAtBlock(SoundRegistry.AIRCOMP, world, x, y, z, 0.5F, p);
	}

	private boolean hasOutputTile() {
		TileEntity te = worldObj.getBlockTileEntity(writex, writey, writez);
		return te instanceof IPowerReceptor;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.COMPRESSOR.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 15*pressure/MAXPRESSURE;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power > 0) {
			float fudge = 1F;
			TileEntity tile = world.getBlockTileEntity(writex, writey, writez);
			if (tile instanceof IPowerReceptor) {
				IPowerReceptor rc = (IPowerReceptor)tile;
				PowerReceiver pp = rc.getPowerReceiver(facingDir);
				if (pp == null)
					return;
				float mj = (float)(power/ReikaBuildCraftHelper.getWattsPerMJ())/fudge;
				float used = pp.receiveEnergy(PowerHandler.Type.ENGINE, mj, facingDir);
			}
		}

		if (tickcount < 20)
			return;
		tickcount = 0;

		this.updatePressure(world, x, y, z, meta);
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
			ready = y-1;
			writey = y+1;
			facingDir = ForgeDirection.DOWN;
			break;
		case 1:
			ready = y+1;
			writey = y-1;
			facingDir = ForgeDirection.UP;
			break;
		case 2:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 3:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 4:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 5:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

	public boolean isPipeConnected(ForgeDirection with) {
		switch(this.getBlockMetadata()) {
		case 4:
			return with == ForgeDirection.NORTH;
		case 5:
			return with == ForgeDirection.WEST;
		case 2:
			return with == ForgeDirection.SOUTH;
		case 3:
			return with == ForgeDirection.EAST;
		case 0:
			return with == ForgeDirection.UP;
		case 1:
			return with == ForgeDirection.DOWN;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		isOut = NBT.getBoolean("out");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setBoolean("out", isOut);
	}

	@Override
	public void updatePressure(World world, int x, int y, int z, int meta) {
		int Pamb = 101;

		if (pressure > Pamb)
			this.addPressure((Pamb-pressure)/50);

		this.addPressure((int)Math.sqrt(power)/64);

		if (pressure > MAXPRESSURE)
			this.overpressure(world, x, y, z);
	}

	@Override
	public void addPressure(int press) {
		pressure += press;
	}

	@Override
	public int getPressure() {
		return pressure;
	}

	@Override
	public void overpressure(World world, int x, int y, int z) {
		pressure = MAXPRESSURE;
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 4F+rand.nextFloat()*2, ConfigRegistry.BLOCKDAMAGE.getState());

		for (int i = 0; i < 6; i++)
			world.createExplosion(null, x+0.5-1+rand.nextDouble()*2, y+0.5-1+rand.nextDouble()*2, z+0.5-1+rand.nextDouble()*2, 3F, ConfigRegistry.BLOCKDAMAGE.getState());
	}
	/*
	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return this.isPipeConnected(with) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}*/

	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		return this.isPipeConnected(side);
	}

}
