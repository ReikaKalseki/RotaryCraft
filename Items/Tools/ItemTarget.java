/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;

public class ItemTarget extends ItemRotaryTool {

	public ItemTarget(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		MovingObjectPosition mov = null;//= ReikaPlayerAPI.getLookedAtBlock(512);
		for (float i = 0; i <= 512; i += 0.5) {
			int[] xyz = ReikaVectorHelper.getPlayerLookBlockCoords(ep, i);
			int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
			if (id != 0) {
				mov = new MovingObjectPosition(xyz[0], xyz[1], xyz[2], 0, ep.getLookVec());
				break;
			}
		}
		//ReikaChatHelper.write(mov);
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			int range = 16;
			for (int i = -range; i <= range; i++) {
				for (int j = -range; j <= range; j++) {
					for (int k = -range; k <= range; k++) {
						TileEntity te = world.getBlockTileEntity((int)ep.posX+i, (int)ep.posY+j, (int)ep.posZ+k);
						if (te instanceof TileEntityTNTCannon) {
							TileEntityTNTCannon tc = (TileEntityTNTCannon)te;
							if (tc.targetMode) {
								tc.target[0] = x;
								tc.target[1] = y;
								tc.target[2] = z;
							}
						}
					}
				}
			}
		}
		return is;
	}

}
