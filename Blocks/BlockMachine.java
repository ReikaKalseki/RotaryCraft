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

public class BlockMachine extends BlockBasicMultiTE {

	public BlockMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public void registerIcons(IconRegister ico) {
		int p = MachineRegistry.ECU.getMachineMetadata();
		icons[p][0] = ico.registerIcon("RotaryCraft:steel");
		icons[p][1] = ico.registerIcon("RotaryCraft:ecu_top");
		for (int i = 2; i < 6; i++)
			icons[p][i] = ico.registerIcon("RotaryCraft:ecu_side");

		p = MachineRegistry.MUSICBOX.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			icons[p][i] = ico.registerIcon("RotaryCraft:musicbox");

		p = MachineRegistry.REFRESHER.getMachineMetadata();
		for (int i = 0; i < 6; i++)
			icons[p][i] = ico.registerIcon("RotaryCraft:refresh");
	}
}
