/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;


public class ItemHandheldPiston extends ItemChargedTool {

	public ItemHandheldPiston(int index) {
		super(index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float a, float b, float c) {
		if (is.getItemDamage() <= 0)
			return false;
		if (!world.isRemote) {
			//ReikaChatHelper.write(mov);
			//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null)
				return false;
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side].getOpposite();
			int power = 0;
			if (ep.isSneaking()) {
				int d = 12;
				for (int i = 1; i <= 13; i++) {
					int dx = x+dir.offsetX*i;
					int dy = y+dir.offsetY*i;
					int dz = z+dir.offsetZ*i;
					if (i == 13) {
						if (!ReikaWorldHelper.softBlocks(world, dx, dy, dz))
							return false;
					}
					if (dy < 0 || dy >= 256)
						return false;
					Block bk = world.getBlock(dx, dy, dz);
					int meta = world.getBlockMetadata(dx, dy, dz);
					if (ReikaBlockHelper.isUnbreakable(world, dx, dy, dz, bk, meta, ep))
						return false;
					te = world.getTileEntity(dx, dy, dz);
					if (te != null)
						return false;
					int amt = bk.getMaterial().getMaterialMobility() == 2 ? 8 : 2;
					power += amt;
					if (ReikaWorldHelper.softBlocks(world, dx, dy, dz) || power >= is.getItemDamage()) {
						d = i-1;
						break;
					}
				}
				for (int i = d; i >= 0; i--) {
					int dx1 = x+dir.offsetX*i;
					int dy1 = y+dir.offsetY*i;
					int dz1 = z+dir.offsetZ*i;
					int dx2 = x+dir.offsetX*(i+1);
					int dy2 = y+dir.offsetY*(i+1);
					int dz2 = z+dir.offsetZ*(i+1);
					BlockKey bk = BlockKey.getAt(world, dx1, dy1, dz1);
					bk.place(world, dx2, dy2, dz2);
				}
			}
			else {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if (!ReikaWorldHelper.softBlocks(world, dx, dy, dz))
					return false;
				BlockKey bk = BlockKey.getAt(world, x, y, z);
				bk.place(world, dx, dy, dz);
				power = 1;
			}
			ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-power));
			world.setBlock(x, y, z, Blocks.air);
		}
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "tile.piston.out");
		return true;
	}

}
