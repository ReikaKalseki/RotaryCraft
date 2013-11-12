/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelForce;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityContainment extends TileEntityPowerReceiver implements RangedEffect, GuiController {

	public static final int DRAGONPOWER = 2097152;
	public static final int WITHERPOWER = 524288;

	public static final int FALLOFF = 8192;

	public int setRange;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelForce();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.CONTAINMENT.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		this.spawnParticles(world, x, y, z);
		List inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getRangedBox());
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)inbox.get(i);
			e.attackEntityFrom(DamageSource.cactus, 0); //to prevent some despawns
			if (ReikaEntityHelper.isHostile(e) && !(e instanceof EntityDragon || e instanceof EntityWither)) {
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-0.5) {
					e.motionX = -dx/dd/2;
					e.motionY = -dy/dd/2;
					e.motionZ = -dz/dd/2;
					if (!world.isRemote)
						e.velocityChanged = true;
				}
			}
			if (e instanceof EntityDragon && power >= DRAGONPOWER) {
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-2) {
					e.motionX -= dx/dd;
					e.motionY -= dy/dd;
					e.motionZ -= dz/dd;
				}
			}
			if (e instanceof EntityWither && power >= WITHERPOWER) {
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				if (dd > this.getRange()-2) {
					e.motionX -= dx/dd;
					e.motionY -= dy/dd;
					e.motionZ -= dz/dd;
				}
				int id = ((EntityWither)e).getWatchedTargetId(0);
				Entity ent = world.getEntityByID(id);
				if (ent != null) {
					double dx2 = ent.posX-x-0.5;
					double dy2 = ent.posY-y-0.5;
					double dz2 = ent.posZ-z-0.5;
					double dd2 = ReikaMathLibrary.py3d(dx2, dy2, dz2);
					if (dd2 > this.getRange())
						((EntityWither)e).func_82211_c(0, 0);
				}
			}
		}
	}

	private void spawnParticles(World world, int x, int y, int z) {
		for (int i = 0; i < 4; i++) {
			world.spawnParticle("portal", x+rand.nextDouble(), y+rand.nextDouble()-0.5, z+rand.nextDouble(), rand.nextDouble()-0.5, rand.nextDouble(), rand.nextDouble()-0.5);
		}
	}

	private AxisAlignedBB getRangedBox() {
		int r = this.getRange();
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord).expand(r, r, r);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRange() {
		if (!this.isClear(worldObj, xCoord, yCoord, zCoord))
			return 0;
		if (setRange > this.getMaxRange())
			return this.getMaxRange();
		return setRange;
	}

	private boolean isClear(World world, int x, int y, int z) {
		for (int i = 1; i <= setRange; i++) {
			int id = world.getBlockId(x, y+i, z);
			if (id != 0 && Block.blocksList[id].getLightOpacity(world, x, y+i, z) > 0)
				return false;
		}
		return true;
	}

	public int getMaxRange() {
		int range = (int)((power)/MINPOWER*FALLOFF);
		if (range > ConfigRegistry.FORCERANGE.getValue())
			return ConfigRegistry.FORCERANGE.getValue();
		return range;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("setRange", setRange);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		setRange = NBT.getInteger("setRange");
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
