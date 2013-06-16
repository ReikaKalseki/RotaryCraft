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

import net.minecraft.world.World;
import Reika.DragonAPI.BlockArray;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySolar extends TileEntityIOMachine implements MultiBlockMachine, SimpleProvider {

	private BlockArray solarBlocks = new BlockArray();
	private int numberMirrors = 0;

	private int waterLevel = 0;
	private float lightMultiplier = 0;

	@Override
	public boolean canProvidePower() {
		//return this.isMultiBlock(worldObj, xCoord, yCoord, zCoord) && this.getMultiBlockPosition(worldObj, xCoord, yCoord, zCoord)[1] == 0;
		return true;
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
		return MachineRegistry.SOLARTOWER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (world.getBlockId(x, y-1, z) == 0 || MachineRegistry.getMachine(world, x, y-1, z) != this.getMachine()) {
			//ReikaJavaLibrary.pConsole("TOWER: "+this.getTowerHeight()+";  SIZE: "+this.getArraySize());
			this.generatePower(world, x, y, z);
		}
		if (world.getBlockId(x, y+1, z) != 0)
			return;
		if (solarBlocks.isEmpty()) {
			lightMultiplier = 0;
			solarBlocks.recursiveFill(world, x, y, z, this.getTileEntityBlockID());
			numberMirrors = solarBlocks.getSize();
			while (solarBlocks.getSize() > 0) {
				int[] xyz = solarBlocks.getNextAndMoveOn();
				MachineRegistry m = MachineRegistry.getMachine(world, xyz[0], xyz[1], xyz[2]);
				if (m == MachineRegistry.MIRROR) {
					TileEntityMirror te = (TileEntityMirror)world.getBlockTileEntity(xyz[0], xyz[1], xyz[2]);
					te.targetloc = new int[]{x,y,z};
					int light = te.getLightLevel();
					lightMultiplier += light;
				}
				else numberMirrors--;
			}
			lightMultiplier /= 15F;
			lightMultiplier /= numberMirrors;
		}
	}

	private void generatePower(World world, int x, int y, int z) {
		writex = x;
		writez = z;
		writey = y-1;
		//omega = 1*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 8, "min")*(this.getArraySize()+1);
		omega = 1024;
		torque = 2*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 64, "min")*(this.getArraySize()+1);
		if (this.getArraySize() <= 0) {
			omega = 0;
			torque = 0;
		}
		power = omega*torque;
	}

	private int getTowerHeight() {
		return this.getTopOfTower(worldObj, xCoord, yCoord, zCoord)-yCoord;
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
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
	}

	private int getArraySize() {
		return ((TileEntitySolar)worldObj.getBlockTileEntity(xCoord, this.getTopOfTower(worldObj, xCoord, yCoord, zCoord), zCoord)).numberMirrors;
	}

	private float getArrayOverallBrightness() {
		return ((TileEntitySolar)worldObj.getBlockTileEntity(xCoord, this.getTopOfTower(worldObj, xCoord, yCoord, zCoord), zCoord)).lightMultiplier;
	}

	public int getTopOfTower(World world, int x, int y, int z) {
		while (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.SOLARTOWER) {
			y++;
		}
		return y-1;
	}

}
