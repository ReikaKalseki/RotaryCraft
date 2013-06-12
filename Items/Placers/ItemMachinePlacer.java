/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Placers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;

public class ItemMachinePlacer extends ItemBlockPlacer {

	public ItemMachinePlacer(int ID) {
		super(ID);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!ReikaWorldHelper.softBlocks(world.getBlockId(x, y, z)) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
			if (!ReikaWorldHelper.softBlocks(world.getBlockId(x, y, z)) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava)
				return false;
		}
		if (!this.checkValidBounds(is, ep, world, x, y, z))
			return false;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLiving.class, box);
		if (inblock.size() > 0)
			return false;
		MachineRegistry m = MachineRegistry.machineList[is.getItemDamage()];
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, m.getBlockID(), m.getMachineMetadata(), 3);
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)world.getBlockTileEntity(x, y, z);
		if (te instanceof TemperatureTE) {
			int Tb = ReikaWorldHelper.getBiomeTemp(world, x, z);
			try {
				if (te.getClass().getField("temperature") != null) {
					te.getClass().getField("temperature").setInt(te, Tb);
				}
			}catch (NoSuchFieldException e) {} catch (SecurityException e) {} catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {}
			try {
				if (te.getClass().getField("pressure") != null) {
					te.getClass().getField("pressure").setInt(te, 101);
				}
			}catch (NoSuchFieldException e) {} catch (SecurityException e) {} catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {}
		}

		if (te instanceof EnchantableMachine) {
			ReikaJavaLibrary.pConsole(is);
			EnchantableMachine e = (EnchantableMachine)te;
			e.applyEnchants(is);
		}
		if (m == MachineRegistry.GPR) {
			TileEntityGPR tile = (TileEntityGPR)te;
			switch (RotaryAux.get2SidedMetadataFromPlayerLook(ep)) {
				case 0:
				case 2:
					tile.xdir = true;
					break;
				case 1:
				case 3:
					tile.xdir = false;
					break;
			}
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, BlockGPR.getBiomeDesign(world, x, y, z));
			return true;
		}
		if (m.isCannon())
			return true;
		if (!m.hasModel() && m.is4Sided() && !m.hasInv()) {
			switch(RotaryAux.get4SidedMetadataFromPlayerLook(ep)) {
				case 0:
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 0);
					break;
				case 1:
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 1);
					break;
				case 2:
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 3);
					break;
				case 3:
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 2);
					break;
			}
			te.setBlockMetadata(world.getBlockMetadata(x, y, z));
			return true;
		}
		if (m.hasModel()) {
			if (m.is6Sided())
				te.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
			else if (m.is4Sided())
				te.setBlockMetadata(RotaryAux.get4SidedMetadataFromPlayerLook(ep));
			else if (m.is2Sided())
				te.setBlockMetadata(RotaryAux.get2SidedMetadataFromPlayerLook(ep));
			if (m.isXFlipped())
				RotaryAux.flipXMetadatas(world.getBlockTileEntity(x, y, z));
			if (m.isZFlipped())
				RotaryAux.flipZMetadatas(world.getBlockTileEntity(x, y, z));
		}
		else {
			if (m.is6Sided())
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, m.getMachineMetadata()+RotaryAux.get6SidedMetadataFromPlayerLook(ep));
			else if (m.is4Sided())
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, m.getMachineMetadata()+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
			else if (m.is2Sided())
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, m.getMachineMetadata()+RotaryAux.get2SidedMetadataFromPlayerLook(ep));

			if (m.is6Sided())
				te.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
			else if (m.is4Sided())
				te.setBlockMetadata(RotaryAux.get4SidedMetadataFromPlayerLook(ep));
			else if (m.is2Sided())
				te.setBlockMetadata(RotaryAux.get2SidedMetadataFromPlayerLook(ep));

			if (m.isXFlipped()) {
				RotaryAux.flipXMetadatas(world.getBlockTileEntity(x, y, z));
				if (world.getBlockMetadata(x, y, z) == m.getMachineMetadata())
					world.setBlockMetadataWithNotify(x, y, z, m.getMachineMetadata()+1, 3);
				if (world.getBlockMetadata(x, y, z) == m.getMachineMetadata()+1)
					world.setBlockMetadataWithNotify(x, y, z, m.getMachineMetadata(), 3);
			}
			if (m.isZFlipped()) {
				RotaryAux.flipZMetadatas(world.getBlockTileEntity(x, y, z));
				if (world.getBlockMetadata(x, y, z) == m.getMachineMetadata()+2)
					world.setBlockMetadataWithNotify(x, y, z, m.getMachineMetadata()+3, 3);
				if (world.getBlockMetadata(x, y, z) == m.getMachineMetadata()+3)
					world.setBlockMetadataWithNotify(x, y, z, m.getMachineMetadata()+2, 3);
			}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			if (!MachineRegistry.machineList[i].hasCustomPlacerItem()) {
				ItemStack item = new ItemStack(par1, 1, i);
				par3List.add(item);
			}
		}
	}

	@Override
	protected boolean checkValidBounds(ItemStack is, EntityPlayer ep, World world, int x, int y, int z) {
		if (is.getItemDamage() == MachineRegistry.SMOKEDETECTOR.ordinal()) {
			if (world.getBlockId(x, y+1, z) == 0)
				return false;
			return (Block.blocksList[world.getBlockId(x, y+1, z)].isOpaqueCube());
		}
		return true;
	}

	@Override
	public int getMetadata(int meta) {
		return MachineRegistry.machineList[meta].getMachineMetadata();
	}
}
