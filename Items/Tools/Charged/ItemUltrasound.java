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

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.ConfigRegistry;

public class ItemUltrasound extends ItemChargedTool {

	public ItemUltrasound(int ID, int tex) {
		super(ID, tex);
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
			int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
			int meta = world.getBlockMetadata(xyz[0], xyz[1], xyz[2]);
			if (ReikaBlockHelper.isOre(id, meta) && !ores) {
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
				if (ConfigRegistry.CLEARCHAT.getState())
					ReikaChatHelper.clearChat(); //clr
			}
		}
		return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
	}
}
