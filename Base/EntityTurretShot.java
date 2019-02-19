/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.RotaryCraft.Auxiliary.TurretDamage;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityTurretShot extends EntityFireball implements IEntityAdditionalSpawnData {

	protected TileEntityAimedCannon gun;

	public EntityTurretShot(World world) {
		super(world);
	}

	public EntityTurretShot(World world, double x, double y, double z, double vx, double vy, double vz, TileEntityAimedCannon te) {
		super(world, x, y, z, 0, 0, 0);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		if (!world.isRemote)
			velocityChanged = true;

		gun = te;
	}

	@Override
	public abstract void onImpact(MovingObjectPosition mov);

	@Override
	public final boolean canRenderOnFire() {
		return false;
	}

	@Override
	public final AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6);
	}

	protected abstract int getAttackDamage();

	protected abstract void applyAttackEffectsToEntity(World world, Entity el);

	public final void writeSpawnData(ByteBuf buf) {
		if (gun != null) {
			buf.writeInt(gun.xCoord);
			buf.writeInt(gun.yCoord);
			buf.writeInt(gun.zCoord);
		}
		else {
			buf.writeInt(0);
			buf.writeInt(-1);
			buf.writeInt(0);
		}
		this.writeData(buf);
	}

	public final void readSpawnData(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		gun = (TileEntityAimedCannon)worldObj.getTileEntity(x, y, z);
		this.readData(buf);
	}

	protected void writeData(ByteBuf buf) {

	}

	protected void readData(ByteBuf buf) {

	}

	protected final DamageSource getDamageSource() {
		if (gun == null || !gun.isInWorld())
			return DamageSource.generic;
		return new TurretDamage(gun);
	}

}
