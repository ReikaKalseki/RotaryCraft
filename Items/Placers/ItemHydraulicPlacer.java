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

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityHydraulicPump;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHydraulicPlacer extends ItemBlockPlacer {

	public ItemHydraulicPlacer(int id) {
		super(id);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava) {
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
			if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava)
				return false;
		}
		if (!this.checkValidBounds(is, ep, world, x, y, z))
			return false;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		if (inblock.size() > 0)
			return false;
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, MachineRegistry.HYDRAULIC.getBlockID(), MachineRegistry.HYDRAULIC.getMachineMetadata(), 3);
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
		TileEntityHydraulicPump hyd = (TileEntityHydraulicPump)world.getBlockTileEntity(x, y, z);
		hyd.setBlockMetadata(6*is.getItemDamage()+RotaryAux.get6SidedMetadataFromPlayerLook(ep));
		hyd.placer = ep.getEntityName();
		if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
			hyd.setFlipped(true);
		}
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < RotaryNames.getNumberHydraulicTypes(); i++) {
			ItemStack is = MachineRegistry.HYDRAULIC.getCraftedMetadataProduct(i);
			list.add(is);
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {

	}
}
