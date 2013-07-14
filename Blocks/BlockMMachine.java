/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import Reika.RotaryCraft.Base.BlockModelledMultiTE;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;

public class BlockMMachine extends BlockModelledMultiTE {

	public BlockMMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int par5)
	{
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)iba.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityPlayerDetector))
			return 0;
		TileEntityPlayerDetector tp = (TileEntityPlayerDetector)te;
		if (tp.isActive)
			return 15;
		else
			return 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.FORCEFIELD || m == MachineRegistry.CONTAINMENT)
			return 15;
		if (m == MachineRegistry.DISPLAY)
			return 15;
		return 0;
	}
}
