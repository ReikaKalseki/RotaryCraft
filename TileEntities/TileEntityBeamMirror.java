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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.ConfigRegistry;

public class TileEntityBeamMirror extends RotaryCraftTileEntity implements RangedEffect {

	private float theta;

	private BlockArray light = new BlockArray();
	private int lastRange = 0;
	private ForgeDirection facingDir;

	@Override
	public void onEMP() {}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return 0;
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.adjustAim(world, x, y, z);

		this.setLight(world, x, y, z);

		this.burnMobs(world, x, y, z);
	}

	private void burnMobs(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
		List<EntityLivingBase> inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase e = inbox.get(i);
			if (ReikaEntityHelper.burnsInSun(e)) {
				e.setFire(10);
			}
		}
	}

	private void setLight(World world, int x, int y, int z) {
		if (lastRange != this.getRange()) {
			for (int i = 0; i < light.getSize(); i++) {
				int[] xyz = light.getNthBlock(i);
				int id = world.getBlockId(xyz[0], xyz[1], xyz[2]);
				if (id == RotaryCraft.lightblock.blockID) {
					world.setBlock(x, y, z, 0);
				}
			}
			if (this.getRange() > 0 && world.canBlockSeeTheSky(x, y+1, z))
				light.addLineOfClear(world, x, y, z, this.getRange(), facingDir.offsetX, 0, facingDir.offsetZ);
			lastRange = this.getRange();
		}
	}

	private void adjustAim(World world, int x, int y, int z) {
		float suntheta = ReikaWorldHelper.getSunAngle(world);

		float movespeed = 0.5F;

		if (theta < suntheta)
			theta += movespeed;
		if (theta > suntheta)
			theta -= movespeed;
	}

	@Override
	public int getRange() {
		int r = ReikaMathLibrary.intpow2(2, (int)(7*ReikaWorldHelper.getSunIntensity(worldObj)));
		if (r > this.getMaxRange())
			return this.getMaxRange();
		return r;
	}

	@Override
	public int getMaxRange() {
		return ConfigRegistry.FLOODLIGHTRANGE.getValue();
	}

}
