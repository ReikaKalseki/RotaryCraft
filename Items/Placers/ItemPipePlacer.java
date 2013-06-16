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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.ItemMulti;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class ItemPipePlacer extends ItemMulti {

	public ItemPipePlacer(int id) {
		super(id, 10);
		this.setHasSubtypes(true);
		//setItemName("pipeplacer");
		this.setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		//if (world.blockHasTileEntity(x, y, z))
		//return false;
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
			//if (world.blockHasTileEntity(x, y, z))
			//return false;
			if (!ReikaWorldHelper.softBlocks(world.getBlockId(x, y, z)) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava)
				return false;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLiving.class, box);
		if (inblock.size() > 0)
			return false;
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(BlockRegistry.PIPING.getBlockID(), is.getItemDamage());
			switch(is.getItemDamage()) {
			case 0:
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 1F+0.2F*par5Random.nextFloat(), 0.6F+0.4F*par5Random.nextFloat());
				break;
			case 1:
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 0.8F+0.2F*par5Random.nextFloat(), 1.3F+0.5F*par5Random.nextFloat());
				break;
			case 2:
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 0.8F+0.2F*par5Random.nextFloat(), 0.6F+0.4F*par5Random.nextFloat());
				break;
			case 3:
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 0.8F+0.2F*par5Random.nextFloat(), 1.3F+0.5F*par5Random.nextFloat());
				break;
			}
			world.setBlock(x, y, z, BlockRegistry.PIPING.getBlockID(), m.getMachineMetadata(), 3);
			((RotaryCraftTileEntity)(world.getBlockTileEntity(x, y, z))).placer = ep.getEntityName();
		}
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", world.getBlockMetadata(x, y, z)));
		return true;
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + RotaryNames.pipeNames[d];
	}
}
