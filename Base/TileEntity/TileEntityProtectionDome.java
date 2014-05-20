/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityProtectionDome extends TileEntityPowerReceiver implements RangedEffect, GuiController {

	public abstract String getParticleType();

	public abstract int getFallOff();

	public abstract int getRangeBoost();

	private int[] RGB = new int[3];

	public int setRange;

	protected void setColor(int r, int g, int b) {
		RGB[0] = Math.max(0, Math.min(255, r));
		RGB[1] = Math.max(0, Math.min(255, g));
		RGB[2] = Math.max(0, Math.min(255, b));
	}

	public final Color getDomeColor() {
		return new Color(RGB[0], RGB[1], RGB[2]);
	}

	protected final boolean isClear(World world, int x, int y, int z) {
		for (int i = 1; i <= setRange; i++) {
			int id = world.getBlockId(x, y+i, z);
			if (id != 0 && Block.blocksList[id].getLightOpacity(world, x, y+i, z) > 0)
				return false;
		}
		return true;
	}

	public final int getMaxRange() {
		if (!this.isClear(worldObj, xCoord, yCoord, zCoord))
			return 0;
		if (power < MINPOWER)
			return 0;
		int range = 2+(int)(power-MINPOWER)/this.getFallOff()+this.getRangeBoost();
		int max = Math.max(64, ConfigRegistry.FORCERANGE.getValue());
		if (range > max)
			return max;
		return range;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final double getMaxRenderDistanceSquared()
	{
		return 16384D;
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("setRange", setRange);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		setRange = NBT.getInteger("setRange");
	}

	protected final void spawnParticles(World world, int x, int y, int z) {
		for (int i = 0; i < 4; i++) {
			world.spawnParticle(this.getParticleType(), x+rand.nextDouble(), y+rand.nextDouble()-0.5, z+rand.nextDouble(), rand.nextDouble()-0.5, rand.nextDouble(), rand.nextDouble()-0.5);
		}
	}

	protected final AxisAlignedBB getRangedBox() {
		int r = this.getRange();
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(r, r, r);
	}

	@Override
	public final boolean hasModelTransparency() {
		return false;
	}

	@Override
	public final int getRange() {
		if (setRange > this.getMaxRange())
			return this.getMaxRange();
		return setRange;
	}

	@Override
	public final void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
