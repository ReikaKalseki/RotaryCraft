/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Base.ItemMulti;

public class ItemSpawner extends ItemMulti {

	public ItemSpawner(int id) {
		super(id, 9);
		this.setHasSubtypes(true);
		//setItemName("spawner");
		this.setCreativeTab(mod_RotaryCraft.tabRotary);
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
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, Block.mobSpawner.blockID);
			TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
			if (spw != null) {
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
				MobSpawnerBaseLogic lgc = spw.func_98049_a();
				lgc.setMobID(this.getMobID(is));
				lgc.spawnDelay = 400; //20s delay
			}
		}
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", world.getBlockMetadata(x, y, z)));
		return true;
	}

	public String getMobID(ItemStack is) {
		switch(is.getItemDamage()) {
		case 0:
			return "Zombie";
		case 1:
			return "Spider";
		case 2:
			return "CaveSpider";
		case 3:
			return "Skeleton";
		case 4:
			return "Silverfish";
		case 5:
			return "Blaze";
		default:
			return null;
		}
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
}
