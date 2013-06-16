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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.ReikaRenderHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityMirror extends RotaryCraftTileEntity implements MultiBlockMachine {

	//2.3 kW/m^2 (392MW/170000) -> 2kW/block; sunlight is 15 kW per m^2, so thus efficiency of 13%

	public float theta;
	public boolean broken;

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
		if (broken)
			return;
		this.adjustAim(world, x, y, z, meta);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			AxisAlignedBB above = AxisAlignedBB.getAABBPool().getAABB(x+0.25, y+1, z+0.25, x+0.75, y+1.5, z+0.75);
			List in = world.getEntitiesWithinAABB(Entity.class, above);
			for (int i = 0; i < in.size(); i++) {
				Entity e = (Entity)in.get(i);
				double m = ReikaEntityHelper.getEntityMass(e);
				//ReikaJavaLibrary.pConsole(m+" kg moving at "+e.motionY+" b/s, E: "+(m-e.motionY*20));
				if (e.motionY < -0.1 && m-e.motionY*20 > 80) {
					ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MIRROR.getMinValue(), this);
					e.attackEntityFrom(DamageSource.cactus, 1);
				}
			}
		}
		//this.repair(world, x, y, z);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public float getLightLevel() {
		if (broken)
			return 0;
		if (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
			return 0;
		float sun = worldObj.getSunBrightness(0);
		if (sun > 0.21) {
			return (int)(15*sun);
		}
		int moon = worldObj.getMoonPhase();
		float phase;
		switch(moon) {
		case 0:
			phase = 1;
			break;
		case 1:
		case 7:
			phase = 0.8F;
			break;
		case 2:
		case 6:
			phase = 0.5F;
			break;
		case 3:
		case 5:
			phase = 0.2F;
			break;
		case 4:
			phase = 0.05F;
			break;
		default:
			phase = 0;
		}
		//ReikaJavaLibrary.pConsole(phase);
		return 15*0.2F*phase;
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
		float movespeed = 0.5F;

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

	public void breakMirror(World world, int x, int y, int z) {
		broken = true;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.addModelledBlockParticles("/Reika/RotaryCraft/Textures/TileEntityTex/", world, x, y, z, this.getMachine().getBlockVariable(), Minecraft.getMinecraft().effectRenderer, ReikaJavaLibrary.makeListFrom(new double[]{0,0,1,1}));
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.glass", 1, 1);
	}

	public void repair(World world, int x, int y, int z) {
		broken = false;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setBoolean("broke", broken);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		broken = NBT.getBoolean("broke");
	}

}
