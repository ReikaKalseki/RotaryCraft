/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

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
				icons[MachineRegistry.FERMENTER.getBlockMetadata()][j][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][0][4][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][1][5][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][2][2][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][3][3][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_front");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][0][4][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][1][5][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][2][2][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][3][3][1] = par1IconRegister.registerIcon("RotaryCraft:ferm_front_active");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][0][5][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][1][4][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][2][3][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");
		icons[MachineRegistry.FERMENTER.getBlockMetadata()][3][2][0] = par1IconRegister.registerIcon("RotaryCraft:ferm_back");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.FIREWORK.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.FIREWORK.getBlockMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:firew_top");

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][j][i][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_side");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][0][4][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][1][5][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][2][2][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][3][3][0] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][0][4][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][1][5][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][2][2][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");
		icons[MachineRegistry.BLASTFURNACE.getBlockMetadata()][3][3][1] = par1IconRegister.registerIcon("RotaryCraft:blastfurn_front_active");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.IGNITER.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:igniter");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.PURIFIER.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.PURIFIER.getBlockMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:purifier");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.BUCKETFILLER.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller");
		icons[MachineRegistry.BUCKETFILLER.getBlockMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller_top");
		icons[MachineRegistry.BUCKETFILLER.getBlockMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:bucketfiller_top");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.WORKTABLE.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:worktable");
		icons[MachineRegistry.WORKTABLE.getBlockMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:worktable_top");
		icons[MachineRegistry.WORKTABLE.getBlockMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:worktable_bottom");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.CRAFTER.getBlockMetadata()][0][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel_dark");
		icons[MachineRegistry.CRAFTER.getBlockMetadata()][0][1][0] = par1IconRegister.registerIcon("RotaryCraft:crafter_top");
		icons[MachineRegistry.CRAFTER.getBlockMetadata()][0][0][0] = par1IconRegister.registerIcon("RotaryCraft:steel");

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[MachineRegistry.DROPS.getBlockMetadata()][j][i][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.DROPS.getBlockMetadata()][0][4][0] = par1IconRegister.registerIcon("RotaryCraft:drops_front");
		icons[MachineRegistry.DROPS.getBlockMetadata()][1][5][0] = par1IconRegister.registerIcon("RotaryCraft:drops_front");
		icons[MachineRegistry.DROPS.getBlockMetadata()][2][2][0] = par1IconRegister.registerIcon("RotaryCraft:drops_front");
		icons[MachineRegistry.DROPS.getBlockMetadata()][3][3][0] = par1IconRegister.registerIcon("RotaryCraft:drops_front");
		icons[MachineRegistry.DROPS.getBlockMetadata()][0][5][0] = par1IconRegister.registerIcon("RotaryCraft:drops_back");
		icons[MachineRegistry.DROPS.getBlockMetadata()][1][4][0] = par1IconRegister.registerIcon("RotaryCraft:drops_back");
		icons[MachineRegistry.DROPS.getBlockMetadata()][2][3][0] = par1IconRegister.registerIcon("RotaryCraft:drops_back");
		icons[MachineRegistry.DROPS.getBlockMetadata()][3][2][0] = par1IconRegister.registerIcon("RotaryCraft:drops_back");

	}
}
