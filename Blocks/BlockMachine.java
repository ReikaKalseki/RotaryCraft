/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class BlockMachine extends BlockBasicMultiTE {

	public BlockMachine(Material mat) {
		super(mat);
	}

	@Override
	public void registerBlockIcons(IIconRegister ico) {
		if (RotaryCraft.instance.isLocked())
			return;
		icons[MachineRegistry.ECU.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.ECU.getBlockMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:ecu_top");
		for (int i = 2; i < 6; i++)
			icons[MachineRegistry.ECU.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:ecu_side");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.MUSICBOX.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:musicbox");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.REFRESHER.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:refresh");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.SELFDESTRUCT.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:destruct");
		icons[MachineRegistry.SELFDESTRUCT.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.SELFDESTRUCT.getBlockMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:steel");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.TERRAFORMER.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.TERRAFORMER.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.TERRAFORMER.getBlockMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:screen");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.LAMP.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:lamp");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.SPILLER.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.SPILLER.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:spiller");

		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][ForgeDirection.NORTH.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_north");
		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][ForgeDirection.EAST.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_east");
		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][ForgeDirection.SOUTH.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_south");
		icons[MachineRegistry.POWERBUS.getBlockMetadata()][0][ForgeDirection.WEST.ordinal()][0] = ico.registerIcon("RotaryCraft:bus_west");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.PARTICLE.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.PARTICLE.getBlockMetadata()][0][1][0] = ico.registerIcon("RotaryCraft:particle_top");

		for (int i = 0; i < 6; i++)
			icons[MachineRegistry.FILLER.getBlockMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:steel");
		icons[MachineRegistry.FILLER.getBlockMetadata()][0][0][0] = ico.registerIcon("RotaryCraft:filler");
	}
}
