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
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;

public class BlockIMachine extends BlockBasicMultiTE {

	public BlockIMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		int p = MachineRegistry.FERMENTER.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[j][i] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[p][4] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[p+1][5] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[p+2][2] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[p+3][3] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[p][5] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[p+1][4] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[p+2][3] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[p+3][2] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		p = MachineRegistry.FIREWORK.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			icons[p][i] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[p][1] = par1IconRegister.registerIcon("RotaryCraft:firew_top");
		p = MachineRegistry.BLASTFURNACE.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[p+j][i] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_side");
		icons[p][4] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[p+1][5] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[p+2][2] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[p+3][3] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		p = MachineRegistry.IGNITER.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			icons[p][i] = par1IconRegister.registerIcon("RotaryCraft:igniter");
		p = MachineRegistry.PURIFIER.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			icons[p][i] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[p][1] = par1IconRegister.registerIcon("RotaryCraft:purifier");
	}
}
