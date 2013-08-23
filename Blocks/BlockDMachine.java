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
import net.minecraft.client.renderer.texture.IconRegister;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class BlockDMachine extends BlockBasicMultiTE {

	public BlockDMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public void registerIcons(IconRegister ico) {
		//-------------Borer------------------------
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[MachineRegistry.BORER.getMachineMetadata()][j][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.BORER.getMachineMetadata()][0][4][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[MachineRegistry.BORER.getMachineMetadata()][1][5][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[MachineRegistry.BORER.getMachineMetadata()][3][2][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[MachineRegistry.BORER.getMachineMetadata()][2][3][0] = ico.registerIcon("RotaryCraft:borer_front");

		icons[MachineRegistry.BORER.getMachineMetadata()][0][4][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[MachineRegistry.BORER.getMachineMetadata()][1][5][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[MachineRegistry.BORER.getMachineMetadata()][3][2][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[MachineRegistry.BORER.getMachineMetadata()][2][3][1] = ico.registerIcon("RotaryCraft:borer_front_active");

		icons[MachineRegistry.BORER.getMachineMetadata()][0][5][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[MachineRegistry.BORER.getMachineMetadata()][1][4][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[MachineRegistry.BORER.getMachineMetadata()][3][3][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[MachineRegistry.BORER.getMachineMetadata()][2][2][0] = ico.registerIcon("RotaryCraft:borer_back");
	}
}
