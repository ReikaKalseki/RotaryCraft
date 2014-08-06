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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Interfaces.SemiTransparent;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFloodlight extends TileEntityBeamMachine implements RangedEffect {

	public int distancelimit = Math.max(64, ConfigRegistry.FLOODLIGHTRANGE.getValue());
	public boolean beammode = false;

	private BlockArray beam = new BlockArray();
	private int lastRange = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		if (power >= MINPOWER)
			RotaryAchievements.FLOODLIGHT.triggerAchievement(this.getPlacer());
		power = (long)omega*(long)torque;
		if (!world.isRemote) {
			if ((world.getTotalWorldTime()&8) == 8) //almost unnoticeable light lag, but big FPS increase
				this.makeBeam(world, x, y, z, meta);
		}
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int metadata) {
		//ReikaJavaLibrary.pConsole(lastRange+":"+this.getRange(), Side.SERVER);
		int r = this.getRange();
		if (lastRange != r) {
			RotaryCraft.logger.debug("Updating "+this+" range from "+lastRange+" to "+r);
			//ReikaJavaLibrary.pConsole(beam);
			for (int i = 0; i < beam.getSize(); i++) {
				int[] xyz = beam.getNthBlock(i);
				int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
				if (this.isLightBlock(id)) {
					//ReikaJavaLibrary.pConsole(Arrays.toString(xyz));
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);
					world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
				}
			}
			beam.clear();
			if (r > 0)
				beam.addLineOfClear(world, x, y, z, r, xstep, ystep, zstep);
			lastRange = r;
		}

		for (int i = 0; i < beam.getSize(); i++) {
			int[] xyz = beam.getNthBlock(i);
			if (world.getBlockId(xyz[0], xyz[1], xyz[2]) == 0)
				world.setBlock(xyz[0], xyz[1], xyz[2], this.getPlacedBlockID(), 15, 3);
			world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
		}
	}

	private int getPlacedBlockID() {
		return beammode ? RotaryCraft.beamblock.blockID : RotaryCraft.lightblock.blockID;
	}

	private boolean isLightBlock(int id) {
		return id == RotaryCraft.beamblock.blockID || id == RotaryCraft.lightblock.blockID;
	}

	public void lightsOut(World world, int x, int y, int z) {
		world.markBlockForUpdate(x, y, z);
		world.notifyBlocksOfNeighborChange(x, y, z, this.getTileEntityBlockID());
		for (int i = 0; i < beam.getSize(); i++) {
			int[] xyz = beam.getNthBlock(i);
			int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
			if (this.isLightBlock(id)) {
				//ReikaJavaLibrary.pConsole(Arrays.toString(xyz));
				world.setBlock(xyz[0], xyz[1], xyz[2], 0);
				world.markBlockForRenderUpdate(xyz[0], xyz[1], xyz[2]);
				world.markBlockForUpdate(xyz[0], xyz[1], xyz[2]);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("beam", beammode);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		beammode = NBT.getBoolean("beam");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRange() {
		//ReikaJavaLibrary.pConsole(r);
		if (power < MINPOWER)
			return 0;
		int ir = this.getMaxRange();
		for (int i = 1; i <= ir; i++) {
			int dx = xCoord+i*xstep;
			int dy = yCoord+i*ystep;
			int dz = zCoord+i*zstep;
			int id = worldObj.getBlockId(dx, dy, dz);
			if (id != 0) {
				Block b = Block.blocksList[id];
				if (b instanceof SemiTransparent) {
					SemiTransparent sm = (SemiTransparent)b;
					if (sm.isOpaque(worldObj.getBlockMetadata(dx, dy, dz)))
						return i;
				}
				else if (b.isOpaqueCube())
					return i;
			}
		}
		return ir;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FLOODLIGHT;
	}

	@Override
	public int getMaxRange() {
		return distancelimit;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
