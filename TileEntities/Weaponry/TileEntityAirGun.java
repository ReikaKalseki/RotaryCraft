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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAirGun extends TileEntityPowerReceiver implements RangedEffect {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		//ReikaJavaLibrary.pConsole(tickcount+"/"+this.getFireRate()+":"+ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv));

		if (tickcount >= this.getFireRate()) {
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
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 2:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 3:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			break;
		}
	}

	private double getFirePower() {
		return ReikaMathLibrary.logbase(torque+1, 2);
	}

	private int getFireRate() {
		return ReikaMathLibrary.extrema(16-(int)ReikaMathLibrary.logbase(omega+1, 2), 4, "max");
	}

	private void fire(World world, int x, int y, int z, int meta, List<EntityLivingBase> li) {
		double vx = 0;
		double vz = 0;
		double v = this.getFirePower();
		switch(meta) {
		case 1:
			vx = v;
			break;
		case 0:
			vx = -v;
			break;
		case 2:
			vz = v;
			break;
		case 3:
			vz = -v;
			break;
		}
		for (int i = 0; i < li.size(); i++) {
			Entity e = li.get(i);
			e.motionX = vx;
			e.motionZ = vz;
			e.motionY = 1;
		}
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 1, 1); //gravity gun sound?
	}

	private AxisAlignedBB drawAABB(int x, int y, int z, int meta) {
		double d = 0.1;
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).contract(d, d, d);
		switch(meta) {
		case 1:
			box.offset(1, 0, 0);
			box.maxX += this.getRange();
			break;
		case 0:
			box.offset(-1, 0, 0);
			box.minX -= this.getRange();
			break;
		case 2:
			box.offset(0, 0, 1);
			box.maxZ += this.getRange();
			break;
		case 3:
			box.offset(0, 0, -1);
			box.minZ -= this.getRange();
			break;
		}

		return box;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.AIRGUN.ordinal();
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
