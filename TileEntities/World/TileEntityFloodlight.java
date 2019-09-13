/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Interfaces.Block.SemiTransparent;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFloodlight extends TileEntityBeamMachine implements RangedEffect, BreakAction {

	public static final int MAX_RANGE = Math.max(64, ConfigRegistry.FLOODLIGHTRANGE.getValue());

	public boolean beammode = false;
	public boolean fresnel = false;

	private BlockArray beam = new BlockArray();
	private int lastRange = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		if (fresnel)
			beammode = false;
		if (power >= MINPOWER)
			RotaryAchievements.FLOODLIGHT.triggerAchievement(this.getPlacer());
		power = (long)omega*(long)torque;
		if (!world.isRemote) {
			if ((world.getTotalWorldTime()&8) == 8) //almost unnoticeable light lag, but big FPS increase
				this.makeBeam(world, x, y, z, meta);
		}
	}

	@Override
	protected void makeBeam(World world, int x, int y, int z, int metadata) {
		//ReikaJavaLibrary.pConsole(lastRange+":"+this.getRange(), Side.SERVER);
		int r = this.getRange();
		if (lastRange != r) {
			RotaryCraft.logger.debug("Updating "+this+" range from "+lastRange+" to "+r);
			//ReikaJavaLibrary.pConsole(beam);
			for (int i = 0; i < beam.getSize(); i++) {
				Coordinate c = beam.getNthBlock(i);
				Block b = c.getBlock(world);
				if (this.isLightBlock(b)) {
					//ReikaJavaLibrary.pConsole(Arrays.toString(xyz));
					c.setBlock(world, Blocks.air);
					world.func_147479_m(c.xCoord, c.yCoord, c.zCoord);
				}
			}
			beam.clear();
			if (r > 0) {
				if (fresnel) {
					ArrayList<ForgeDirection> ds = ReikaDirectionHelper.getPerpendicularDirections(facing);
					ForgeDirection d1 = ds.get(0);
					ForgeDirection d2 = ds.get(1);
					for (int d = 1; d <= r; d++) {
						int w = (d-1)/3;
						if (d > 1)
							w++;
						for (int a = -w; a <= w; a++) {
							for (int b = -w; b <= w; b++) {
								int dx = x+facing.offsetX*d+d1.offsetX*a+d2.offsetX*b;
								int dy = y+facing.offsetY*d+d1.offsetY*a+d2.offsetY*b;
								int dz = z+facing.offsetZ*d+d1.offsetZ*a+d2.offsetZ*b;
								beam.addIfClear(world, dx, dy, dz);
							}
						}
					}
				}
				else {
					beam.addLineOfClear(world, x, y, z, r, facing.offsetX, facing.offsetY, facing.offsetZ);
				}
			}
			lastRange = r;
		}

		for (int i = 0; i < beam.getSize(); i++) {
			Coordinate c = beam.getNthBlock(i);
			if (c.getBlock(world) == Blocks.air)
				c.setBlock(world, this.getPlacedBlockID(), 15);
			world.func_147479_m(c.xCoord, c.yCoord, c.zCoord);
		}
	}

	private Block getPlacedBlockID() {
		return beammode ? BlockRegistry.BEAM.getBlockInstance() : BlockRegistry.LIGHT.getBlockInstance();
	}

	private boolean isLightBlock(Block id) {
		return id == BlockRegistry.BEAM.getBlockInstance() || id == BlockRegistry.LIGHT.getBlockInstance();
	}

	public void lightsOut(World world, int x, int y, int z) {
		world.markBlockForUpdate(x, y, z);
		world.notifyBlocksOfNeighborChange(x, y, z, this.getTileEntityBlockID());
		for (int i = 0; i < beam.getSize(); i++) {
			Coordinate c = beam.getNthBlock(i);
			Block b = c.getBlock(world);
			if (this.isLightBlock(b)) {
				//ReikaJavaLibrary.pConsole(Arrays.toString(xyz));
				c.setBlock(world, Blocks.air);
				world.func_147479_m(c.xCoord, c.yCoord, c.zCoord);
				world.markBlockForUpdate(c.xCoord, c.yCoord, c.zCoord);
			}
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setBoolean("beam", beammode);
		NBT.setBoolean("lens", fresnel);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		beammode = NBT.getBoolean("beam");
		fresnel = NBT.getBoolean("lens");
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
			int dx = xCoord+i*facing.offsetX;
			int dy = yCoord+i*facing.offsetY;
			int dz = zCoord+i*facing.offsetZ;
			Block b = worldObj.getBlock(dx, dy, dz);
			if (b != Blocks.air) {
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
		return fresnel ? 24 : MAX_RANGE;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void breakBlock() {
		this.lightsOut(worldObj, xCoord, yCoord, zCoord);
		if (fresnel) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemStacks.lens);
		}
	}
}
