/*******************************************************************************
 * @author Reika
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

import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;

public class BlockDMachine extends BlockBasicMultiTE {

	public BlockDMachine(int id, Material mat) {
		super(id, mat);
	}
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		int p = MachineRegistry.BORER.getMachineMetadata();
		//-------------Borer------------------------
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[p+j][i] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[p][4] = par1IconRegister.registerIcon("RotaryCraft:borer_front");
		icons[p+1][5] = par1IconRegister.registerIcon("RotaryCraft:borer_front");
		icons[p+3][2] = par1IconRegister.registerIcon("RotaryCraft:borer_front");
		icons[p+2][3] = par1IconRegister.registerIcon("RotaryCraft:borer_front");

		icons[p][5] = par1IconRegister.registerIcon("RotaryCraft:borer_back");
		icons[p+1][4] = par1IconRegister.registerIcon("RotaryCraft:borer_back");
		icons[p+3][3] = par1IconRegister.registerIcon("RotaryCraft:borer_back");
		icons[p+2][2] = par1IconRegister.registerIcon("RotaryCraft:borer_back");
	}
}
