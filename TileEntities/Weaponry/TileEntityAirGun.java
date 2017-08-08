/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAirGun extends TileEntityPowerReceiver implements RangedEffect, DiscreteFunction {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		//ReikaJavaLibrary.pConsole(tickcount+"/"+this.getFireRate()+":"+ReikaInventoryHelper.checkForItem(Items.arrow.itemID, inv));

		if (tickcount >= this.getOperationTime()&& !world.isRemote) {
			AxisAlignedBB box = this.drawAABB(x, y, z, meta);
			List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
			if (li.size() > 0 && !ReikaEntityHelper.allAreDead(li, false)) {
				this.fire(world, x, y, z, meta, li);
			}
			tickcount = 0;
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 1:
			read = ForgeDirection.WEST;
			break;
		case 0:
			read = ForgeDirection.EAST;
			break;
		case 3:
			read = ForgeDirection.NORTH;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			break;
		}
	}

	private double getFirePower() {
		return ReikaMathLibrary.logbase(torque+1, 2);
	}

	public int getOperationTime() {
		return ReikaMathLibrary.extrema(16-(int)ReikaMathLibrary.logbase(omega+1, 2), 4, "max");
	}

	private void fire(World world, int x, int y, int z, int meta, List<EntityLivingBase> li) {
		double vx = 0;
		double vz = 0;
		double v = this.getFirePower()/4;
		switch(meta) {
		case 1:
			vx = v;
			break;
		case 0:
			vx = -v;
			break;
		case 3:
			vz = v;
			break;
		case 2:
			vz = -v;
			break;
		}
		boolean flag = false;
		for (EntityLivingBase e : li) {
			int x2 = (int)Math.floor(e.posX);
			int z2 = (int)Math.floor(e.posZ);
			int y2 = (int)e.posY-1;
			Block b = world.getBlock(x2, y2, z2);
			boolean immune = false;
			if (e instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)e;
				if (this.isPlacer(ep) || ReikaPlayerAPI.isReika(ep))
					immune = true;
			}
			if (!immune && b != Blocks.air) {
				e.motionX = vx;
				e.motionZ = vz;
				e.motionY = 0.5;
				e.velocityChanged = true;
				flag = true;
			}
		}
		if (flag)
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 1, 1); //gravity gun sound?
	}

	private AxisAlignedBB drawAABB(int x, int y, int z, int meta) {
		double d = 0.1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).contract(d, d, d);
		switch(meta) {
		case 1:
			box.offset(1, 0, 0);
			box.maxX += this.getRange();
			break;
		case 0:
			box.offset(-1, 0, 0);
			box.minX -= this.getRange();
			break;
		case 3:
			box.offset(0, 0, 1);
			box.maxZ += this.getRange();
			break;
		case 2:
			box.offset(0, 0, -1);
			box.minZ -= this.getRange();
			break;
		}

		return box;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.AIRGUN;
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
		return 10+2*(int)ReikaMathLibrary.logbase(torque+1, 2);
	}

}
