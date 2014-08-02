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

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import cpw.mods.fml.client.FMLClientHandler;

public class TileEntityAirCompressor extends TileEntityPowerReceiver implements IPowerEmitter, PressureTE {

	private int pressure;

	private boolean isOut;

	public static final int MAXPRESSURE = 1000;

	private ForgeDirection facingDir;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
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
		float v = 0.5F*this.getSoundVolume(world, x, y, z);
		FMLClientHandler.instance().getClient().sndManager.playSound(SoundRegistry.AIRCOMP.getPlayableReference(), x, y, z, v, p);
		//SoundRegistry.AIRCOMP.playSoundAtBlock(world, x, y, z, 0.5F*this.getSoundVolume(world, x, y, z), p);
	}

	private float getSoundVolume(World world, int x, int y, int z) {
		ForgeDirection dir = facingDir;
		if (dir == null)
			return 1F;
		ForgeDirection dir2 = dir.getOpposite();
		for (int i = 0; i < 6; i++) {
			ForgeDirection side = dirs[i];
			if (side != dir && side != dir2) {
				int dx = x+side.offsetX;
				int dy = y+side.offsetY;
				int dz = z+side.offsetZ;
				int id = world.getBlockId(dx, dy, dz);
				if (id != Block.cloth.blockID)
					return 1;
			}
		}
		return 0.2625F;
	}

	private boolean hasOutputTile() {
		TileEntity te = this.getAdjacentTileEntity(write);
		return te instanceof IPowerReceptor;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.COMPRESSOR;
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
		this.getPower(false);

		if (power > 0) {
			TileEntity tile = this.getAdjacentTileEntity(write);
			if (tile instanceof IPowerReceptor) {
				IPowerReceptor rc = (IPowerReceptor)tile;
				PowerReceiver pp = rc.getPowerReceiver(facingDir);
				if (pp == null)
					return;
				float mj = (float)this.getGenMJ();
				float used = pp.receiveEnergy(PowerHandler.Type.ENGINE, mj, facingDir);
			}
		}

		if (tickcount < 20)
			return;
		tickcount = 0;

		this.updatePressure(world, x, y, z, meta);
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.DOWN;
			break;
		case 1:
			facingDir = ForgeDirection.UP;
			break;
		case 2:
			facingDir = ForgeDirection.NORTH;
			break;
		case 3:
			facingDir = ForgeDirection.WEST;
			break;
		case 4:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 5:
			facingDir = ForgeDirection.EAST;
			break;
		}
		read = facingDir;
		write = read.getOpposite();
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
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		pressure = NBT.getInteger("pressure");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("pressure", pressure);
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

	public double getGenMJ() {
		return power/ReikaBuildCraftHelper.getWattsPerMJ();
	}

}
