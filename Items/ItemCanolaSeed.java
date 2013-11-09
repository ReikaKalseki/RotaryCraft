/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemBasic;

public class ItemCanolaSeed extends ItemBasic implements IPlantable{

	public ItemCanolaSeed(int ID, int tex) {
		super(ID, tex);
		this.setMaxDamage(0);
		hasSubtypes = true;
	}

	@Override
	public boolean onItemUse(ItemStack items, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (items.getItemDamage() > 0)
			return false;
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
		}
		if ((!ReikaWorldHelper.softBlocks(world.getBlockId(x, y, z))) || world.getBlockId(x, y-1, z) != Block.tilledField.blockID)
			return false;
		if (!player.canPlayerEdit(x, y, z, 0, items))
			return false;
		else
		{
			if (!player.capabilities.isCreativeMode)
				--items.stackSize;
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, RotaryCraft.canola.blockID);
			return true;
		}
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		return 80+item.getItemDamage();
	}
	
    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return RotaryCraft.canola.blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return 0;
    }
}
