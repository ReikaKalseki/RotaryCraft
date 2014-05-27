/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Entities.EntitySonicShot;
import Reika.RotaryCraft.Registry.ConfigRegistry;
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
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
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
			read = ForgeDirection.WEST;
			xstep = 1;
			ystep = 0;
			zstep = 0;
			break;
		case 0:
			read = ForgeDirection.EAST;
			xstep = -1;
			ystep = 0;
			zstep = 0;
			break;
		case 3:
			read = ForgeDirection.NORTH;
			xstep = 0;
			ystep = 0;
			zstep = 1;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			xstep = 0;
			ystep = 0;
			zstep = -1;
			break;
		case 5:	//moving up
			read = ForgeDirection.UP;
			xstep = 0;
			ystep = -1;
			zstep = 0;
			break;
		case 4:	//moving down
			read = ForgeDirection.DOWN;
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

		EntitySonicShot e = new EntitySonicShot(world, this, placer);
		if (!world.isRemote) {
			world.spawnEntityInWorld(e);
		}
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
	}

	private int getDistanceToSurface(World world, int x, int y, int z) {
		for (int m = 1; m < this.getMaxRange(); m++) {
			int dx = x+m*xstep;
			int dy = y+m*ystep;
			int dz = z+m*zstep;
			boolean nonair = false;
			int k = FOV;
			if (xstep != 0) {
				for (int i = z-k; i <= z+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						if (!this.canDrop(world, dx, j, i))
							return -1;
						int id = world.getBlockId(dx, j, i);
						if (id != 0)
							nonair = true;
					}
				}
			}
			else if (zstep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						if (!this.canDrop(world, i, j, dz))
							return -1;
						int id = world.getBlockId(i, j, dz);
						if (id != 0)
							nonair = true;
					}
				}
			}
			else if (ystep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = z-k; j <= z+k; j++) {
						if (!this.canDrop(world, i, dy, j))
							return -1;
						int id = world.getBlockId(i, dy, j);
						if (id != 0)
							nonair = true;
					}
				}
			}
			if (nonair)
				return m;
		}
		return this.getMaxRange();
	}

	private int getMaxRange() {
		return Math.max(ConfigRegistry.SONICBORERRANGE.getValue(), 64);
	}

	public static boolean canDrop(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id == 0)
			return true;
		Block b = Block.blocksList[id];
		if (b.getBlockHardness(world, x, y, z) < 0)
			return false;
		if (b instanceof BlockFluid)
			return false;
		if (b instanceof BlockFluidBase)
			return false;
		int meta = world.getBlockMetadata(x, y, z);
		if (id == ItemStacks.shieldblock.itemID && meta == ItemStacks.shieldblock.getItemDamage())
			return false;
		return true;
	}

	private boolean canFire(World world, int x, int y, int z, int meta) {
		if (pressure < FIRE_PRESSURE)
			return false;
		if (power < MINPOWER || torque < MINTORQUE)
			return false;
		if (y-this.getDistanceToSurface(world, x, y, z) <= 0 && ystep == -1)
			return false;
		return true;
	}

	private int getPressureIncrement() {
		int amt3 = (int)(power/65536);
		return amt3;
	}

	public int[] getTargetPosn() {
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int[] arr = new int[3];
		int r = this.getDistanceToSurface(world, x, y, z);
		if (r < 0)
			r = 0;
		arr[0] = x+xstep*r;
		arr[1] = y+ystep*r;
		arr[2] = z+zstep*r;
		return arr;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SONICBORER;
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
		int pd = dP/384+1;
		//ReikaJavaLibrary.pConsole(dP+":"+pd+":"+(pressure-pd), Side.SERVER);
		if (dP > 0)
			pressure -= pd;
		else
			pressure++;
		if (power >= MINPOWER && torque >= MINTORQUE) {
			pressure += this.getPressureIncrement();
		}
		//ReikaJavaLibrary.pConsole(pressure, Side.SERVER);
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
		float f = 4;
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());

		world.createExplosion(null, x+0.5, y+1.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());
		world.createExplosion(null, x+0.5, y-0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());

		world.createExplosion(null, x+1.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());
		world.createExplosion(null, x-0.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());

		world.createExplosion(null, x+0.5, y+0.5, z+1.5, f, ConfigRegistry.BLOCKDAMAGE.getState());
		world.createExplosion(null, x+0.5, y+0.5, z-0.5, f, ConfigRegistry.BLOCKDAMAGE.getState());
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("press", pressure);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		pressure = NBT.getInteger("press");
	}

	public int getDistanceToSurface() {
		return this.getDistanceToSurface(worldObj, xCoord, yCoord, zCoord);
	}

}
