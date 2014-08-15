/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.BlockColorMapper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntitySpyCam extends RemoteControlMachine implements RangedEffect {

	private int tickcount2 = 0;

	public static final int MAXRANGE = 24;

	private int[][] topBlocks = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	private int[][] mobs = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	private int[][] topY = new int[2*MAXRANGE+1][2*MAXRANGE+1];
	public List inzone;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPYCAM;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.setColors();
		if (!this.hasCoil()) {
			on = false;
			return;
		}
		on = true;
		tickcount2++;
		int dmg = inv[0].getItemDamage();
		if (tickcount2 > this.getUnwindTime()) {
			ItemStack is = this.getDecrementedCharged();
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
		inzone = world.getEntitiesWithinAABB(EntityLivingBase.class, zone);
		for (int i = 0; i < inzone.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)inzone.get(i);
			int ex = (int)ent.posX-x;
			int ey = (int)ent.posY-y;
			int ez = (int)ent.posZ-z;
			if (EntityList.getEntityID(ent) > 0 && Math.abs(ex) < range+1 && Math.abs(ez) < range+1 && ent.posY >= ReikaWorldHelper.findTopBlockBelowY(world, (int)ent.posX, (int)ent.posZ, y)) {
				//ReikaJavaLibrary.pConsole(ent.getCommandSenderName()+" @ "+ex+", "+ez);
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
				Block b = world.getBlock(x+i, topy, z+j);
				int meta = world.getBlockMetadata(x+i, topy, z+j);
				if (world.isRemote)
					topBlocks[(i+range)][j+range] = BlockColorMapper.instance.getColorForBlock(b, meta);
				if (world.getBlock(x+i, y, z+j) != Blocks.air) {
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
	public void activate(World world, EntityPlayer ep, int x, int y, int z) {
		if (on)
			ep.openGui(RotaryCraft.instance, GuiRegistry.SPYCAM.ordinal(), world, x, y, z);
	}
}