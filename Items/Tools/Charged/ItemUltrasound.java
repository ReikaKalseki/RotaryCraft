/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUltrasound extends ItemChargedTool {

	public ItemUltrasound(int tex) {
		super(tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() <= 0) {
			this.noCharge();
			return is;
		}
		this.warnCharge(is);
		//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
		boolean ores = false;
		boolean cave = false;
		boolean silver = false;
		boolean liq = false;
		boolean caveready = false;
		for (float i = 0; i <= 5; i += 0.2) {
			int[] xyz = ReikaVectorHelper.getPlayerLookBlockCoords(ep, i);
			Block id = world.getBlock(xyz[0], xyz[1], xyz[2]);
			int meta = world.getBlockMetadata(xyz[0], xyz[1], xyz[2]);
			if (ReikaBlockHelper.isOre(id, meta) && !ores) {
				ores = true;
				ReikaChatHelper.write("Ore Detected!");
			}
			if (id == Blocks.monster_egg && !silver) {
				silver = true;
				ReikaChatHelper.write("Silverfish Detected!");
			}
			if (id != Blocks.air && !ReikaWorldHelper.softBlocks(id))
				caveready = true;
			if ((id == Blocks.water || id == Blocks.flowing_water) && !liq) {
				liq = true;
				ReikaChatHelper.write("Water Detected!");
			}
			if ((id == Blocks.lava || id == Blocks.flowing_lava) && !liq) {
				liq = true;
				ReikaChatHelper.write("Lava Detected!");
			}
			if (caveready && ReikaWorldHelper.caveBlock(id) && !cave) {
				cave = true;
				ReikaChatHelper.write("Cave Detected!");
			}
			if (!ores && !silver && !cave && !liq) {
				if (ConfigRegistry.CLEARCHAT.getState())
					ReikaChatHelper.clearChat(); //clr
			}
		}
		return new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1);
	}
}