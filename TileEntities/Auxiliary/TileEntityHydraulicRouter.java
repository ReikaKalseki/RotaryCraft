/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHydraulicLine;

public class TileEntityHydraulicRouter extends RotaryCraftTileEntity {

	private Flow[] connections = new Flow[6];

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ROUTER.ordinal();
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
		int input = this.getSupplyPressure(world, x, y, z);
		int num = this.getNumberOutputs();
		if (num > 0)
			this.giveToOutputs(world, x, y, z, input, num);
	}

	@Override
	public Icon getIconForSide(ForgeDirection dir) {
		switch(this.getConnection(dir.ordinal())) {
		case NONE:
			return RotaryCraft.decoblock.getIcon(0, 0);
		case INPUT:
			return Block.blockLapis.getIcon(0, 0);
		case OUTPUT:
			return Block.blockRedstone.getIcon(0, 0);
		default:
			return RotaryCraft.decoblock.getIcon(0, 0);
		}
	}

	private boolean isConnected(ForgeDirection dir) {
		return this.getConnection(dir.ordinal()) != Flow.NONE;
	}

	@Override
	public boolean hasIconOverride() {
		return true;
	}

	public void updateSide(ForgeDirection side) {
		Flow f = this.getConnection(side.ordinal());
		if (f == Flow.NONE) {
			this.setConnection(side.ordinal(), Flow.INPUT);
		}
		else if (f == Flow.INPUT) {
			this.setConnection(side.ordinal(), Flow.OUTPUT);
		}
		else if (f == Flow.OUTPUT) {
			this.setConnection(side.ordinal(), Flow.NONE);
		}
	}

	private void giveToOutputs(World world, int x, int y, int z, int input, int num) {
		int togive = input/num-this.getLoss(input);
		for (int i = 0; i < 6; i++) {
			if (this.getConnection(i).canOutput) {
				ForgeDirection dir = dirs[i];
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.HYDRAULICLINE) {
					TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(dx, dy, dz);
					te.setPressure(togive);
				}
			}
		}
	}

	private int getLoss(int amount) {
		return (int)(amount*0.025);
	}

	public int getSupplyPressure(World world, int x, int y, int z) {
		int p = 0;
		for (int i = 0; i < 6; i++) {
			if (this.getConnection(i).canIntake) {
				ForgeDirection dir = dirs[i];
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.HYDRAULICLINE) {
					TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(dx, dy, dz);
					p += te.getPressure();
				}
			}
		}
		return p;
	}

	public int getNumberOutputs() {
		int o = 0;
		for (int i = 0; i < 6; i++) {
			if (this.getConnection(i).canOutput) {
				o++;
			}
		}
		return o;
	}

	public Flow getStateForSide(ForgeDirection dir) {
		return this.getConnection(dir.ordinal());
	}

	private Flow getConnection(int i) {
		return connections[i] != null ? connections[i] : Flow.NONE;
	}

	private void setConnection(int i, Flow f) {
		connections[i] = f;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		for (int i = 0; i < 6; i++) {
			connections[i] = Flow.list[NBT.getInteger("conn"+i)];
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		for (int i = 0; i < 6; i++) {
			NBT.setInteger("conn"+i, this.getConnection(i).ordinal());
		}
	}

}
