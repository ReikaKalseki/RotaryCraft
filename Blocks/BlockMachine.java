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

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class BlockMachine extends BlockBasicMultiTE {

	public BlockMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public void registerIcons(IconRegister ico) {
		if (RotaryCraft.instance.isLocked())
			return;
		icons[MachineRegistry.ECU.getMachineMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.ECU.getMachineMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:ecu_top");
		for (int i = 2; i < 6; i++)
			icons[MachineRegistry.ECU.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:ecu_side");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.MUSICBOX.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:musicbox");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.REFRESHER.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:refresh");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.SELFDESTRUCT.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:destruct");
		icons[MachineRegistry.SELFDESTRUCT.getMachineMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.SELFDESTRUCT.getMachineMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:steel");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.TERRAFORMER.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.TERRAFORMER.getMachineMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.TERRAFORMER.getMachineMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:screen");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.LAMP.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:lamp");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.SPILLER.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.SPILLER.getMachineMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:spiller");

		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][ForgeDirection.NORTH.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_north");
		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][ForgeDirection.EAST.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_east");
		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][ForgeDirection.SOUTH.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_south");
		icons[MachineRegistry.POWERBUS.getMachineMetadata()][0][ForgeDirection.WEST.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_west");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.PARTICLE.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.PARTICLE.getMachineMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:particle_top");
	}
}
