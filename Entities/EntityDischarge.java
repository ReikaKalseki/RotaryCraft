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

import io.netty.buffer.ByteBuf;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDischarge extends Entity implements IEntityAdditionalSpawnData {

	public int charge;
	public double targetX;
	public double targetY;
	public double targetZ;

	public EntityDischarge(World world) {
		super(world);
		targetX = targetY = targetZ = 0;
		charge = 0;
	}

	public EntityDischarge(World world, double x, double y, double z, int charge, double tx, double ty, double tz) {
		super(world);
		this.setPosition(x, y, z);
		this.charge = charge;
		targetX = tx;
		targetY = ty;
		targetZ = tz;
	}

	public int getCurrent() {
		return charge*20/1000; //1A = 1C/s, 1t = 1/20 s
	}

	public Color getColor() {
		int a = this.getCurrent();
		if (a > 120) {
			return new Color(127, 0, 255);
		}
		else if (a > 90) {
			return new Color(0, 192, 255);
		}
		else if (a > 70) {
			return new Color(255, 255, 255);
		}
		else if (a > 50) {
			return new Color(255, 255, 0);
		}
		else {
			return new Color(0, 0, 0);
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (ticksExisted > 1)
			this.setDead();
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound NBT) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound NBT) {

	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeDouble(targetX);
		data.writeDouble(targetY);
		data.writeDouble(targetZ);

		data.writeInt(charge);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		targetX = data.readDouble();
		targetY = data.readDouble();
		targetZ = data.readDouble();

		charge = data.readInt();
	}

}