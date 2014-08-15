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

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.WorldEditHelper;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWorldEdit extends ItemRotaryTool {

	public int[] start = new int[3];
	public int[] end = new int[3];

	public ItemWorldEdit(int tex) {
		super(tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float a, float b, float c) {
		if (is.stackTagCompound == null) {
			is.setTagCompound(new NBTTagCompound());
			is.stackTagCompound.setInteger("sx", Integer.MIN_VALUE);
			is.stackTagCompound.setInteger("sy", Integer.MIN_VALUE);
			is.stackTagCompound.setInteger("sz", Integer.MIN_VALUE);
			is.stackTagCompound.setInteger("ex", Integer.MIN_VALUE);
			is.stackTagCompound.setInteger("ey", Integer.MIN_VALUE);
			is.stackTagCompound.setInteger("ez", Integer.MIN_VALUE);
		}
		MovingObjectPosition mov = new MovingObjectPosition(x, y, z, side, ep.getLookVec());
		if (!WorldEditHelper.hasPlayer(ep)) {
			ReikaChatHelper.write("Player "+ep.getCommandSenderName()+" has no chosen block!");
			return false;
		}
		Block id = WorldEditHelper.getCommandedID(ep);
		int meta = WorldEditHelper.getCommandedMetadata(ep);

		if (id != Blocks.air) {
			if (id == null) {
				ReikaChatHelper.write("Block "+id+" does not exist!");
				return false;
			}
		}

		if (ep.isSneaking()) {
			is.stackTagCompound.setInteger("ex", mov.blockX);
			is.stackTagCompound.setInteger("ey", mov.blockY);
			is.stackTagCompound.setInteger("ez", mov.blockZ);
			ReikaChatHelper.write("Position 2 set to "+is.stackTagCompound.getInteger("ex")+", "+is.stackTagCompound.getInteger("ey")+", "+is.stackTagCompound.getInteger("ez"));
		}
		else {
			is.stackTagCompound.setInteger("sx", mov.blockX);
			is.stackTagCompound.setInteger("sy", mov.blockY);
			is.stackTagCompound.setInteger("sz", mov.blockZ);
			ReikaChatHelper.write("Position 1 set to "+is.stackTagCompound.getInteger("sx")+", "+is.stackTagCompound.getInteger("sy")+", "+is.stackTagCompound.getInteger("sz"));
		}
		is.setItemDamage(1);
		ep.setCurrentItemOrArmor(0, is);
		int[] s = {is.stackTagCompound.getInteger("sx"), is.stackTagCompound.getInteger("sy"), is.stackTagCompound.getInteger("sz")};
		int[] e = {is.stackTagCompound.getInteger("ex"), is.stackTagCompound.getInteger("ey"), is.stackTagCompound.getInteger("ez")};
		if (s[0] != Integer.MIN_VALUE && s[1] != Integer.MIN_VALUE && s[2] != Integer.MIN_VALUE) {
			if (e[0] != Integer.MIN_VALUE && e[1] != Integer.MIN_VALUE && e[2] != Integer.MIN_VALUE) {
				if (s[0] != e[0] || s[1] != e[1] || s[2] != e[2]) {
					String name;
					if (id == Blocks.air)
						name = "Air";
					else
						name = new ItemStack(id, 1, meta).getDisplayName();
					ReikaChatHelper.write(String.format("%d", Math.abs((s[0]-e[0]+1)*(s[1]-e[1]+1)*(s[2]-e[2]+1)))+" blocks being changed to "+name+" (ID "+id+") with Metadata "+meta);
					for (int m = 0; m < 3; m++) {
						if (s[m] > e[m]) {
							int sc = s[m];
							s[m] = e[m];
							e[m] = sc;
						}
					}
					if (!world.isRemote)
						for (int i = s[0]; i <= e[0]; i += 1) {
							for (int j = s[1]; j <= e[1]; j += 1) {
								for (int k = s[2]; k <= e[2]; k += 1) {
									world.setBlock(i, j, k, id, meta, 3);
									world.markBlockForUpdate(i, j, k);
								}
							}
						}
					this.reset(is, ep);
				}
			}
		}
		return true;
	}

	private void reset (ItemStack is, EntityPlayer ep) {
		is.stackTagCompound.setInteger("sx", Integer.MIN_VALUE);
		is.stackTagCompound.setInteger("sy", Integer.MIN_VALUE);
		is.stackTagCompound.setInteger("sz", Integer.MIN_VALUE);
		is.stackTagCompound.setInteger("ex", Integer.MIN_VALUE);
		is.stackTagCompound.setInteger("ey", Integer.MIN_VALUE);
		is.stackTagCompound.setInteger("ez", Integer.MIN_VALUE);
		is.setItemDamage(0);
		ep.setCurrentItemOrArmor(0, is);
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		return 114+is.getItemDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		ep.openGui(RotaryCraft.instance, GuiRegistry.WORLDEDIT.ordinal(), world, 0, 0, 0);
		return is;
	}
}