/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.PressureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySonicBorer extends TileEntityPowerReceiver implements PressureTE {

	private int pressure;

	public static final int FIRE_PRESSURE = 400; //4 atm
	public static final int MAXPRESSURE = 1000;

	public int xstep;
	public int ystep;
	public int zstep;

	public static final int FOV = 3;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, true);
		this.updatePressure(world, x, y, z, meta);
		if (this.canFire(world, x, y, z, meta)) {
			this.fire(world, x, y, z, meta);
			pressure -= FIRE_PRESSURE;
		}
		if (pressure > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
		}
	}

	public final void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			xstep = 1;
			ystep = 0;
			zstep = 0;
			break;
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			xstep = -1;
			ystep = 0;
			zstep = 0;
			break;
		case 3:
			readz = z-1;
			readx = x;
			ready = y;
			xstep = 0;
			ystep = 0;
			zstep = 1;
			break;
		case 2:
			readz = z+1;
			readx = x;
			ready = y;
			xstep = 0;
			ystep = 0;
			zstep = -1;
			break;
		case 5:	//moving up
			readx = x;
			readz = z;
			ready = y+1;
			xstep = 0;
			ystep = -1;
			zstep = 0;
			break;
		case 4:	//moving down
			readx = x;
			readz = z;
			ready = y-1;
			xstep = 0;
			ystep = 1;
			zstep = 0;
			break;
		}
	}

	private void fire(World world, int x, int y, int z, int meta) {
		int r = this.getDistanceToSurface(world, x, y, z);
		if (r < 0)
			return;
		int fx = x+r*xstep;
		int fy = y+r*ystep;
		int fz = z+r*zstep;
		int k = FOV;
		if (!world.isRemote) {
			if (xstep != 0) {
				for (int i = z-k; i <= z+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						this.dropBlockAt(world, fx, j, i);
					}
				}
			}
			else if (zstep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						this.dropBlockAt(world, i, j, fz);
					}
				}
			}
			else if (ystep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = z-k; j <= z+k; j++) {
						this.dropBlockAt(world, i, fy, j);
					}
				}
			}
		}
		ReikaSoundHelper.playSoundAtBlock(world, fx, fy, fz, "random.explode");
		ReikaParticleHelper.EXPLODE.spawnAt(world, fx, fy, fz);
	}

	private void dropBlockAt(World world, int x, int y, int z) {
		if (y == 0)
			return;
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (id == 0)
			return;
		Block b = Block.blocksList[id];
		if (b.getBlockHardness(world, x, y, z) < 0)
			;//return;
		List<ItemStack> li = b.getBlockDropped(world, x, y, z, meta, 0);
		ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
		world.setBlock(x, y, z, 0);
	}

	private int getDistanceToSurface(World world, int x, int y, int z) {
		for (int m = 1; m < 512; m++) {
			int dx = x+m*xstep;
			int dy = y+m*ystep;
			int dz = z+m*zstep;
			int k = FOV;
			if (xstep != 0) {
				for (int i = z-k; i <= z+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						if (!ReikaWorldHelper.softBlocks(world, dx, j, i))
							return m;
					}
				}
			}
			else if (zstep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						if (!ReikaWorldHelper.softBlocks(world, i, j, dz))
							return m;
					}
				}
			}
			else if (ystep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = z-k; j <= z+k; j++) {
						if (!ReikaWorldHelper.softBlocks(world, i, dy, j))
							return m;
					}
				}
			}
		}
		return -1;
	}

	private boolean canFire(World world, int x, int y, int z, int meta) {
		if (pressure < FIRE_PRESSURE)
			return false;
		if (y-this.getDistanceToSurface(world, x, y, z) <= 0)
			return false;
		return true;
	}

	private int getPressureIncrement() {
		return 8*(int)ReikaMathLibrary.logbase(torque+1, 2);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SONICBORER.ordinal();
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
	public void updatePressure(World world, int x, int y, int z, int meta) {
		int Pamb = 101;
		if (world.provider.isHellWorld)
			Pamb = 2000;
		int dP = pressure-Pamb;
		//pressure -= dP/32+1;
		pressure += this.getPressureIncrement();
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

	}

}
