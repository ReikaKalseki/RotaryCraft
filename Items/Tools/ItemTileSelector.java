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

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.SelectableTiles;
import Reika.RotaryCraft.Base.ItemRotaryTool;

public class ItemTileSelector extends ItemRotaryTool {

	public ItemTileSelector(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof SelectableTiles && !ep.isSneaking()) {
			SelectableTiles sc = (SelectableTiles)te;
			this.setID(is, sc.getUniqueID());
			return true;
		}
		SelectableTiles sc = this.getController(world, is);
		if (sc != null) {
			sc.addTile(x, y, z);
		}
		return true;
	}

	private SelectableTiles getController(World world, ItemStack is) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			return null;
		int[] xyz = nbt.getIntArray("locID");
		TileEntity te = world.getBlockTileEntity(xyz[0], xyz[1], xyz[2]);
		if (te instanceof SelectableTiles) {
			RotaryCraft.logger.debug("Read tile "+te+" at "+Arrays.toString(xyz));
			return (SelectableTiles)te;
		}
		return null;
	}

	private void setID(ItemStack is, int[] id) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setIntArray("locID", id);
		RotaryCraft.logger.debug("Saved tile "+Arrays.toString(id));
	}

}
