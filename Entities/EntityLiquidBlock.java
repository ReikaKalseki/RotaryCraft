/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import codechicken.lib.math.MathHelper;

public class EntityLiquidBlock extends Entity {

	private Fluid fluid;

	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public EntityLiquidBlock(World world, int x, int y, int z, Fluid f) {
		super(world);
		fluid = f;
		this.setPosition(x, y, z);
	}

	@Override
	public void onUpdate() {

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

	public boolean canMove() {
		return true;
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

}
