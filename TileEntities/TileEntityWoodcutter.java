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

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelWoodcutter;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWoodcutter extends TileEntityPowerReceiver {

	public int editx;
	public int edity;
	public int editz;
	public double dropx;
	public double dropz;

	/** For the 3x3 area of effect */
	public boolean varyx;
	public boolean varyz;
	public int stepx;
	public int stepz;

	private BlockArray tree = new BlockArray();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power < MINPOWER || torque < MINTORQUE) {
			return;
		}

		if (world.isRemote)
			return;

		if (tree.isEmpty() && this.hasWood()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					tree.addGenerousTree(world, editx+i, edity, editz+j, 16);
				}
			}
		}

		int id = world.getBlockId(x, y+1, z);
		if (id != 0) {
			Block b = Block.blocksList[id];
			ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, b.getBlockDropped(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), 0));
			world.setBlock(x, y+1, z, 0);
		}

		//ReikaJavaLibrary.pConsole(tree);

		if (tree.isEmpty())
			return;

		if (!this.operationComplete(tickcount, 0) && ConfigRegistry.INSTACUT.getState())
			return;
		tickcount = 0;

		int[] xyz = tree.getNextAndMoveOn();
		int drop = world.getBlockId(xyz[0], xyz[1], xyz[2]);
		if (drop != 0) {
			int dropmeta = world.getBlockMetadata(xyz[0], xyz[1], xyz[2]);
			Material mat = world.getBlockMaterial(xyz[0], xyz[1], xyz[2]);
			Block dropBlock = Block.blocksList[drop];
			if (ConfigRegistry.INSTACUT.getState()) {
				world.setBlock(xyz[0], xyz[1], xyz[2], 0);
				ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlock.getBlockDropped(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
			}
			else {
				boolean fall = BlockSand.canFallBelow(world, xyz[0], xyz[1]-1, xyz[2]);
				if (fall) {
					EntityFallingSand e = new EntityFallingSand(world, xyz[0]+0.5, xyz[1]+0.65, xyz[2]+0.5, drop, dropmeta);
					e.fallTime = -2000;
					e.shouldDropItem = false;
					if (!world.isRemote) {
						world.spawnEntityInWorld(e);
					}
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);
				}
				else {
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);
					ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlock.getBlockDropped(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
					if (mat == Material.leaves)
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+par5Random.nextFloat(), 1F);
					else
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+par5Random.nextFloat(), 1F);
				}
			}
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			editx = x-1;
			edity = y;
			editz = z;
			dropx = x+1+0.125;
			dropz = z+0.5;
			stepx = 1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			editx = x+1;
			edity = y;
			editz = z;
			dropx = x-0.125;
			dropz = z+0.5;
			stepx = -1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 2:
			readz = z+1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z-1;
			dropx = x+0.5;
			dropz = z+1+0.125;
			stepx = 0;
			stepz = 1;
			varyx = true;
			varyz = false;
			break;
		case 3:
			readz = z-1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z+1;
			dropx = x+0.5;
			dropz = z-0.125;
			stepx = 0;
			stepz = -1;
			varyx = true;
			varyz = false;
			break;
		}
		dropx = x+0.5; dropz = z+0.5;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelWoodcutter();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.WOODCUTTER.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasWood())
			return 15;
		return 0;
	}

	private boolean hasWood() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (worldObj.getBlockId(editx+i, edity, editz+j) == Block.wood.blockID)
					return true;
				if (ModWoodList.isModWood(new ItemStack(worldObj.getBlockId(editx+i, edity, editz+j), 1, worldObj.getBlockMetadata(editx+i, edity, editz+j))))
					return true;
			}
		}
		return false;
	}
}
