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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Base.TileEntityIOMachine;

public class ItemIOGoggles extends ItemRotaryArmor implements IArmorTextureProvider {

	public ItemIOGoggles(int itemID, int texID) {
		super(itemID, RotaryCraft.IOGM, texID, 0, 1);
		this.setNoRepair();
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		int x = (int)ep.posX;
		int y = (int)ep.posY;
		int z = (int)ep.posZ;
		for (int i = -6; i <= 6; i++) {
			for (int j = -6; j <= 6; j++) {
				for (int k = -6; k <= 6; k++) {
					if (world.getBlockTileEntity(x+i, y+j, z+k) instanceof TileEntityIOMachine) {
						TileEntityIOMachine te = (TileEntityIOMachine)world.getBlockTileEntity(x+i, y+j, z+k);
						te.iotick = 512;
					}
				}
			}
		}
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {}

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return "/Reika/RotaryCraft/Textures/Misc/IOGoggles.png";
	}
}
