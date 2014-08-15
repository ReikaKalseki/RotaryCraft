/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockIMachine extends BlockBasicMultiTE {

	public BlockIMachine(Material mat) {
		super(mat);
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[MachineRegistry.FERMENTER.getMachineMetadata()][j][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][0][4][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][1][5][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][2][2][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][3][3][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][0][4][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][1][5][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][2][2][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][3][3][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][0][5][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][1][4][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][2][3][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getMachineMetadata()][3][2][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.FIREWORK.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.FIREWORK.getMachineMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:firew_top");

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][j][i][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_side");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][0][4][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][1][5][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][2][2][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][3][3][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][0][4][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][1][5][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][2][2][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getMachineMetadata()][3][3][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.IGNITER.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:igniter");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.PURIFIER.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.PURIFIER.getMachineMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:purifier");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.BUCKETFILLER.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller");
		icons[MachineRegistry.BUCKETFILLER.getMachineMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller_top");
		icons[MachineRegistry.BUCKETFILLER.getMachineMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller_top");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.WORKTABLE.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:worktable");
		icons[MachineRegistry.WORKTABLE.getMachineMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:worktable_top");
		icons[MachineRegistry.WORKTABLE.getMachineMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:worktable_bottom");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.CRAFTER.getMachineMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel_dark");
		icons[MachineRegistry.CRAFTER.getMachineMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:crafter_top");
		icons[MachineRegistry.CRAFTER.getMachineMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:steel");

	}
}