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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;

public class ItemTarget extends ItemBasic {

	public ItemTarget(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		MovingObjectPosition mov = ReikaPlayerAPI.getLookedAtBlock(512);
		//ReikaChatHelper.write(mov);
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			for (int i = -8; i <= 8; i++) {
				for (int j = -8; j <= 8; j++) {
					for (int k = -8; k <= 8; k++) {
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
