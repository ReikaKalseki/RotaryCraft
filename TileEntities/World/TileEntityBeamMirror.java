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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Interfaces.SemiTransparent;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBeamMirror extends RotaryCraftTileEntity implements RangedEffect {

	public float theta;

	private BlockArray light = new BlockArray();
	private int lastRange = 0;
	private ForgeDirection facingDir;

	@Override
	public void onEMP() {}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BEAMMIRROR;
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
		this.getDirection(meta);

		this.adjustAim(world, x, y, z);

		this.setLight(world, x, y, z);

		this.burnMobs(world, x, y, z);
	}

	private void getDirection(int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.EAST;
			break;
		case 1:
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.NORTH;
			break;
		}
	}

	private void burnMobs(World world, int x, int y, int z) {
		AxisAlignedBB box = this.getBurningBox(world, x, y, z);
		List<EntityLivingBase> inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase e = inbox.get(i);
			if (ReikaEntityHelper.burnsInSun(e)) {
				e.setFire(10);
			}
		}
	}

	private AxisAlignedBB getBurningBox(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
		switch(facingDir) {
		case EAST:
			return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1+r, y+1, z+1);
		case NORTH:
			return AxisAlignedBB.getAABBPool().getAABB(x, y, z-r, x+1, y+1, z+1);
		case SOUTH:
			return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1+r);
		case WEST:
			return AxisAlignedBB.getAABBPool().getAABB(x-r, y, z, x+1, y+1, z+1);
		default:
			return box;
		}
	}

	private void setLight(World world, int x, int y, int z) {
		//ReikaJavaLibrary.pConsole(lastRange+":"+r, Side.SERVER);
		int r = this.getRange();
		if (lastRange != r) {
			//ReikaJavaLibrary.pConsole(light);
			for (int i = 0; i < light.getSize(); i++) {
				int[] xyz = light.getNthBlock(i);
				int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
				if (id == RotaryCraft.lightblock.blockID) {
					//ReikaJavaLibrary.pConsole(Arrays.toString(xyz));
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);
					world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
				}
			}
			light.clear();
			if (r > 0 && world.canBlockSeeTheSky(x, y+1, z))
				light.addLineOfClear(world, x+facingDir.offsetX, y, z+facingDir.offsetZ, r, facingDir.offsetX, 0, facingDir.offsetZ);
			lastRange = r;
		}

		for (int i = 0; i < light.getSize(); i++) {
			int[] xyz = light.getNthBlock(i);
			if (world.getBlockId(xyz[0], xyz[1], xyz[2]) == 0)
				world.setBlock(xyz[0], xyz[1], xyz[2], RotaryCraft.lightblock.blockID, 15, 3);
			world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
		}
	}

	private void adjustAim(World world, int x, int y, int z) {
		float suntheta = ReikaWorldHelper.getSunAngle(world)/2+12.5F;
		float movespeed = 0.5F;

		if (theta < suntheta)
			theta += movespeed;
		if (theta > suntheta)
			theta -= movespeed;
	}

	@Override
	public int getRange() {
		if (!worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
			return 0;
		int time = (int)(worldObj.getWorldTime()%24000);
		if (time > 13500 && time < 22500)
			return 0;
		double r = ReikaMathLibrary.doubpow(2, 7*ReikaWorldHelper.getSunIntensity(worldObj));
		//ReikaJavaLibrary.pConsole(r);
		int ir = (int)r;
		if (ir > this.getMaxRange())
			ir = this.getMaxRange();
		for (int i = 1; i < ir; i++) {
			int dx = xCoord+i*facingDir.offsetX;
			int dy = yCoord+i*facingDir.offsetY;
			int dz = zCoord+i*facingDir.offsetZ;
			int id = worldObj.getBlockId(dx, dy, dz);
			if (id != 0) {
				Block b = Block.blocksList[id];
				if (b instanceof SemiTransparent) {
					if (((SemiTransparent)b).isOpaque(worldObj.getBlockMetadata(dx, dy, dz)))
						return i;
				}
				else if (b.isOpaqueCube())
					return i;
			}
		}
		return ir;
	}

	@Override
	public int getMaxRange() {
		return Math.max(ConfigRegistry.FLOODLIGHTRANGE.getValue(), 64);
	}

	public void lightsOut() {
		World world = worldObj;
		for (int i = 0; i < light.getSize(); i++) {
			int[] xyz = light.getNthBlock(i);
			int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
			if (id == RotaryCraft.lightblock.blockID) {
				world.setBlock(xyz[0], xyz[1], xyz[2], 0);
				world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
			}
		}
	}

}
