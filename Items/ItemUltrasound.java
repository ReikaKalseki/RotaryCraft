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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemBasic;

public class ItemUltrasound extends ItemBasic {

	public ItemUltrasound(int itemID) {
		super(itemID, 128);
		maxStackSize = 1;
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		hasSubtypes = true;
		this.setMaxDamage(0);
	}
	//Can find ores, detect near caves, detect silverfish stone

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		is.setItemDamage(30);
		if (is.getItemDamage() <= 0) {
			ReikaChatHelper.clearChat(); //clr
			return is;
		}
		//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
		boolean ores = false;
		boolean cave = false;
		boolean silver = false;
		boolean liq = false;
		boolean caveready = false;
		for (float i = 0; i <= 5; i += 0.2) {
			double[] xyz = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			int x = (int)xyz[0];
			int y = (int)xyz[1];
			int z = (int)xyz[2];
			if (x < 0)
				x--;
			if (z < 0)
				z--;
			int id = world.getBlockId(x, y, z);
			if (ReikaBlockHelper.isOre(id) && !ores) {
				ores = true;
				ReikaChatHelper.write("Ore Detected!");
			}
			if (id == Block.silverfish.blockID && !silver) {
				silver = true;
				ReikaChatHelper.write("Silverfish Detected!");
			}
			if (id != 0 && !ReikaWorldHelper.softBlocks(id))
				caveready = true;
			if ((id == Block.waterStill.blockID || id == Block.waterMoving.blockID) && !liq) {
				liq = true;
				ReikaChatHelper.write("Water Detected!");
			}
			if ((id == Block.lavaStill.blockID || id == Block.lavaMoving.blockID) && !liq) {
				liq = true;
				ReikaChatHelper.write("Lava Detected!");
			}
			if (caveready && ReikaWorldHelper.caveBlock(id) && !cave) {
				cave = true;
				ReikaChatHelper.write("Cave Detected!");
			}
			if (!ores && !silver && !cave && !liq) {
				ReikaChatHelper.clearChat(); //clr
			}
		}
		return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
	}


	public static String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}
}
