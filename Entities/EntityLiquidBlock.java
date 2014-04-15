/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.RotaryCraft.TileEntities.World.TileEntityFlooder;

public class EntityLiquidBlock extends Entity {

	private Fluid fluid;
	private TileEntityFlooder tile;

	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public EntityLiquidBlock(World world) {
		super(world);
		fluid = null;
		tile = null;
	}

	public EntityLiquidBlock(World world, int x, int y, int z, Fluid f, TileEntityFlooder te) {
		super(world);
		fluid = f;
		tile = te;
		this.setPosition(x, y, z);
	}

	@Override
	public void onUpdate() {
		//super.onUpdate();
		World world = worldObj;
		int x = this.getIntegerX();
		int y = this.getIntegerY();
		int z = this.getIntegerZ();
		if (this.canMoveInto(ForgeDirection.DOWN)) {
			posY--;
		}
		else {
			ForgeDirection toPit = this.findPathToDepression(world, x, y, z);
			if (toPit != null) {
				int dx = x+toPit.offsetX;
				int dy = y+toPit.offsetY;
				int dz = z+toPit.offsetZ;
				if (this.canMoveInto(toPit)) {
					this.moveEntity(toPit.offsetX, toPit.offsetY, toPit.offsetZ);
				}
			}
			else {

			}
		}
		this.setDead();
	}

	public ForgeDirection findPathToDepression(World world, int x, int y, int z) {
		ForgeDirection[] dir = new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.NORTH};
		ReikaArrayHelper.shuffleArray(dir);
		int dy = y-1;
		int r = 6;
		for (int i = 0; i < 4; i++) {
			ForgeDirection d = dir[i];
			for (int k = 1; k <= r; k++) {
				int dx = x+d.offsetX*k;
				int dz = z+d.offsetZ*k;
				if (this.canMoveInto(d))
					return d;
			}
		}
		return null;
	}

	public int getIntegerX() {
		return MathHelper.floor_double(posX);
	}

	public int getIntegerY() {
		return MathHelper.floor_double(posY);
	}

	public int getIntegerZ() {
		return MathHelper.floor_double(posZ);
	}

	public boolean canMoveInto(ForgeDirection side) {
		int dx = this.getIntegerX()+side.offsetX;
		int dy = this.getIntegerY()+side.offsetY;
		int dz = this.getIntegerZ()+side.offsetZ;
		return false;
	}

	public int getBlockID(ForgeDirection side) {
		int dx = this.getIntegerX()+side.offsetX;
		int dy = this.getIntegerY()+side.offsetY;
		int dz = this.getIntegerZ()+side.offsetZ;
		return worldObj.getBlockId(dx, dy, dz);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound NBT) {
		fluid = ReikaNBTHelper.getFluidFromNBT(NBT);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound NBT) {
		ReikaNBTHelper.writeFluidToNBT(NBT, fluid);
	}

	public Fluid getFluid() {
		return fluid;
	}

}
