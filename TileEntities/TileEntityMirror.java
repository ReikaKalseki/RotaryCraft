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

import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaPhysicsHelper;
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMirror extends RotaryCraftTileEntity implements MultiBlockMachine {

	//2.3 kW/m^2 (392MW/170000) -> 2kW/block; sunlight is 15 kW per m^2, so thus efficiency of 13%

	public float theta;

	public int[] targetloc = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

	@Override
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
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
		return MachineRegistry.MIRROR.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.adjustAim(world, x, y, z, meta);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private int getLightLevel(World world, int x, int y, int z) {
		if (!world.canBlockSeeTheSky(x, y, z))
			return 0;
		return (int)(15*world.getSunBrightness(0));
	}

	private void adjustAim(World world, int x, int y, int z, int meta) {
		if (phi >= 360)
			phi -= 360;
		if (phi < 0)
			phi += 360;
		if (theta >= 360)
			theta -= 360;
		if (theta < 0)
			theta += 360;

		if (targetloc[0] == targetloc[1] && targetloc[0] == targetloc[2] && targetloc[0] == Integer.MIN_VALUE)
			return;

		long time = world.getWorldTime()%12000;

		//float sunphi = 90+180*time/12000F; //rises in +90 sets in 270 (+x, -x)
		float movespeed = 2.5F;

		float targetphi = (float)ReikaPhysicsHelper.cartesianToPolar(x-targetloc[0], y-targetloc[1], z-targetloc[2])[2];
		float targettheta = (float)ReikaPhysicsHelper.cartesianToPolar(x-targetloc[0], y-targetloc[1], z-targetloc[2])[1];

		targettheta = Math.abs(targettheta)-90;
		targettheta *= 0.5;

		float suntheta = (time/6000F)*60;
		float sunphi = 0;
		if (time >= 6000)
			sunphi = 270;
		else
			sunphi = 90;

		float finalphi = 0;
		float finaltheta = 0;

		//ReikaJavaLibrary.pConsole(String.format("SUN: %.3f  TARGET: %.3f  FINAL: %.3f", sunphi, targetphi, finalphi));

		//ReikaJavaLibrary.pConsole(Arrays.toString(targetloc)+" @ "+x+", "+y+", "+z);
		//sunphi = targetphi;

		/*
		if (time >= 6000) {
			suntheta = Math.abs(120-suntheta);
		}*/

		finalphi = targetphi - Math.abs(sunphi-targetphi)/2;

		finaltheta = Math.abs(suntheta - targettheta)/2;

		if (finalphi - phi > 180)
			finalphi -= 360;
		int a = 1;
		if (a == 1) {
			finalphi = targetphi;
			finaltheta = targettheta;
		}
		if (a == 2) {
			finalphi = sunphi;
			finaltheta = suntheta;
		}

		//ReikaJavaLibrary.pConsole(String.format("SUN: %.3f  TARGET: %.3f  FINAL: %.3f", suntheta, targettheta, finaltheta));

		if (finalphi >= 360)
			finalphi -= 360;
		if (finalphi < 0)
			finalphi += 360;
		if (finaltheta >= 360)
			finaltheta -= 360;
		if (finaltheta < 0)
			finaltheta += 360;

		if (phi < finalphi)
			phi += movespeed;
		if (phi > finalphi)
			phi -= movespeed;

		if (theta < finaltheta)
			theta += movespeed;
		if (theta > finaltheta)
			theta -= movespeed;
	}

}
