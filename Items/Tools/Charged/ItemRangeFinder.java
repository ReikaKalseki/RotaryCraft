/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.ItemChargedTool;

public class ItemRangeFinder extends ItemChargedTool {

	public ItemRangeFinder(int index) {
		super(index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() <= 0) {
			ReikaChatHelper.clearChat(); //clr
			this.noCharge();
			return is;
		}
		this.warnCharge(is);

		MovingObjectPosition mov = ReikaPlayerAPI.getLookedAtBlock(ep, 512, true);
		if (mov != null) {
			double d = ReikaMathLibrary.py3d(mov.blockX-ep.posX, mov.blockY-ep.posY, mov.blockZ-ep.posZ);
			Block b = world.getBlock(mov.blockX, mov.blockY, mov.blockZ);
			int meta = world.getBlockMetadata(mov.blockX, mov.blockY, mov.blockZ);
			String s = Item.getItemFromBlock(b).getItemStackDisplayName(new ItemStack(b, 1, meta));
			if (s == null || s.isEmpty())
				s = "[No Name]";
			ReikaChatHelper.write(String.format("Block '%s' is %.3fm away.", s, d));
			is.setItemDamage(is.getItemDamage()-1);
		}

		return is;
	}

}
