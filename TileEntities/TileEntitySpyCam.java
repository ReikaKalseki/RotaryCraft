/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RemoteControlMachine;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySpyCam extends RemoteControlMachine implements RangedEffect {

	private int tickcount2 = 0;

	public static final int MAXRANGE = 24;

	private int[][] topBlocks = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	private int[][] mobs = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	private int[][] topY = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	public List inzone;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SPYCAM.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.setColors();
		if (inv[0] == null) {
			on = false;
			return;
		}
		if (inv[0].itemID != ItemRegistry.SPRING.getShiftedID()) {
			on = false;
			return;
		}
		if (inv[0].getItemDamage() <= 0) {
			on = false;
			return;
		}
		on = true;
		tickcount2++;
		int dmg = inv[0].getItemDamage();
		if (tickcount2 > 120) {
			ItemStack is = new ItemStack(ItemRegistry.SPRING.getShiftedID(), 1, dmg-1);
			inv[0] = is;
			tickcount2 = 0;
		}
		this.getTopBlocks(world, x, y, z);
		this.getMobs(world, x, y, z);
	}

	private void getMobs(World world, int x, int y, int z) {
		mobs = ReikaArrayHelper.fillMatrix(mobs, 0);
		int range = this.getRange();
		int maxrange = this.getMaxRange();
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-range, 0, z-range, x+1+range, y+1, z+1+range);
		inzone = world.getEntitiesWithinAABB(EntityLiving.class, zone);
		for (int i = 0; i < inzone.size(); i++) {
			EntityLiving ent = (EntityLiving)inzone.get(i);
			int ex = (int)ent.posX-x;
			int ey = (int)ent.posY-y;
			int ez = (int)ent.posZ-z;
			if (EntityList.getEntityID(ent) > 0 && Math.abs(ex) < range+1 && Math.abs(ez) < range+1 && ent.posY >= ReikaWorldHelper.findTopBlockBelowY(world, (int)ent.posX, (int)ent.posZ, y)) {
				//ReikaJavaLibrary.pConsole(ent.getEntityName()+" @ "+ex+", "+ez);
				mobs[(ez+range)][ex+range] = EntityList.getEntityID(ent);
				//ReikaJavaLibrary.pConsole(mobs[ex+range][ez+range]+String.format("@ %d,  %d  from  %d", ex+range, ez+range, EntityList.getEntityID(ent)));
			}
		}
	}

	private void getTopBlocks(World world, int x, int y, int z) {
		//topBlocks = ReikaArrayHelper.fillMatrix(topBlocks, 0);
		int range = this.getRange();
		int maxrange = this.getMaxRange();
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				int topy = ReikaWorldHelper.findTopBlockBelowY(world, x+i, z+j, y);
				topY[i+range][j+range] = topy;
				int id = world.getBlockId(x+i, topy, z+j);
				topBlocks[(i+range)][j+range] = ReikaWorldHelper.blockColors(id, ConfigRegistry.GPRORES.getState());
				if (world.getBlockId(x+i, y, z+j) != 0) {
					//topBlocks[(i+range)][j+range] = 0;
				}
			}
		}
	}

	public int[] getBounds() {
		int range = this.getRange();
		int mrange = this.getMaxRange();
		int[] bounds = {mrange-range, mrange+range};
		return bounds;
	}

	public int getMobAt(int i, int j) {
		return mobs[i][j];
	}

	public int getTopBlockAt(int i, int j) {
		return topBlocks[i][j];
	}

	public int getHeightAt(int i, int j) {
		return topY[i][j];
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getRange() {
		return this.getMaxRange();
	}

	@Override
	public int getMaxRange() {
		return MAXRANGE;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void activate(World world, EntityPlayer ep, int x, int y, int z) {
		if (on)
			ep.openGui(RotaryCraft.instance, GuiRegistry.SPYCAM.ordinal(), world, x, y, z);
	}
}
