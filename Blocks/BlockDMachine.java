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
		int k = MachineRegistry.BORER.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[k][j][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[k][0][4][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[k][1][5][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[k][3][2][0] = ico.registerIcon("RotaryCraft:borer_front");
		icons[k][2][3][0] = ico.registerIcon("RotaryCraft:borer_front");

		icons[k][0][4][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[k][1][5][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[k][3][2][1] = ico.registerIcon("RotaryCraft:borer_front_active");
		icons[k][2][3][1] = ico.registerIcon("RotaryCraft:borer_front_active");

		icons[k][0][5][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][1][4][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][3][3][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][2][2][0] = ico.registerIcon("RotaryCraft:borer_back");

		k = MachineRegistry.ARROWGUN.getMachineMetadata();
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 6; i++) {
				icons[k][j][i][0] = ico.registerIcon("RotaryCraft:steel_dark");
			}
			icons[k][j][0][0] = ico.registerIcon("RotaryCraft:steel_dark_top");
			icons[k][j][1][0] = ico.registerIcon("RotaryCraft:steel_dark_top");
		}

		icons[k][0][4][0] = ico.registerIcon("RotaryCraft:gun_front");
		icons[k][1][5][0] = ico.registerIcon("RotaryCraft:gun_front");
		icons[k][3][2][0] = ico.registerIcon("RotaryCraft:gun_front");
		icons[k][2][3][0] = ico.registerIcon("RotaryCraft:gun_front");

		icons[k][0][5][0] = ico.registerIcon("RotaryCraft:gun_back");
		icons[k][1][4][0] = ico.registerIcon("RotaryCraft:gun_back");
		icons[k][3][3][0] = ico.registerIcon("RotaryCraft:gun_back");
		icons[k][2][2][0] = ico.registerIcon("RotaryCraft:gun_back");

		k = MachineRegistry.SORTING.getMachineMetadata();
		String s = "RotaryCraft:sorter_side";
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				for (int n = 2; n < 6; n++) {
					String tex = i > 0 ? String.format("%s%d", s, i) : s;
					icons[MachineRegistry.SORTING.getMachineMetadata()][j][n][i] = ico.registerIcon(tex);
				}
			}
			icons[MachineRegistry.SORTING.getMachineMetadata()][j][0][0] = ico.registerIcon("RotaryCraft:sorter_bottom");
			icons[MachineRegistry.SORTING.getMachineMetadata()][j][1][0] = ico.registerIcon("RotaryCraft:sorter_top");
		}

		icons[k][1][4][0] = ico.registerIcon("RotaryCraft:sorter_input");
		icons[k][0][5][0] = ico.registerIcon("RotaryCraft:sorter_input");
		icons[k][2][2][0] = ico.registerIcon("RotaryCraft:sorter_input");
		icons[k][3][3][0] = ico.registerIcon("RotaryCraft:sorter_input");

		k = MachineRegistry.BUSCONTROLLER.getMachineMetadata();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				icons[k][j][i][0] = ico.registerIcon("RotaryCraft:buscontroller");
				icons[k][j][0][0] = ico.registerIcon("RotaryCraft:steel");
				icons[k][j][1][0] = ico.registerIcon("RotaryCraft:steel");
			}
		}

		icons[k][0][5][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][1][4][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][3][3][0] = ico.registerIcon("RotaryCraft:borer_back");
		icons[k][2][2][0] = ico.registerIcon("RotaryCraft:borer_back");
	}
}
