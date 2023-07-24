/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.SolarPlant;
import Reika.RotaryCraft.Auxiliary.Interfaces.SolarPlantBlock;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMirror extends RotaryCraftTileEntity implements SolarPlantBlock {

	//2.3 kW/m^2 (392MW/170000) -> 2kW/block; sunlight is 15 kW per m^2, so thus efficiency of 13%

	@SideOnly(Side.CLIENT)
	public float theta;

	@SideOnly(Side.CLIENT)
	private float targetTheta;
	@SideOnly(Side.CLIENT)
	private float targetPhi;

	public boolean broken;
	private boolean rotatingLarge;

	private float aimFactor = 1;
	private float lastAimFactor;

	private SolarPlant plant;

	public void searchForPlant(World world, int x, int y, int z) {
		if (plant != null)
			return;
		plant = SolarPlant.build(world, x, y, z);
	}

	public SolarPlant getPlant() {
		return plant;
	}

	public void setPlant(SolarPlant p) {
		plant = p;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.MIRROR;
	}

	public float getAimingAccuracy() {
		return aimFactor;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (broken)
			return;

		this.searchForPlant(world, x, y, z);

		if (world.isRemote && (this.getTicksExisted() < 400 || rotatingLarge || Math.abs(world.getTotalWorldTime()%8) == Math.abs(System.identityHashCode(this)%8))) {
			this.adjustAim(world, x, y, z, meta);
		}

		if (!world.isRemote) {
			AxisAlignedBB above = AxisAlignedBB.getBoundingBox(x+0.25, y+1, z+0.25, x+0.75, y+1.5, z+0.75);
			List<Entity> in = world.getEntitiesWithinAABB(Entity.class, above);
			for (Entity e : in) {
				if (ReikaEntityHelper.isSolidEntity(e)) {
					double m = ReikaEntityHelper.getEntityMass(e);
					//ReikaJavaLibrary.pConsole(m+" kg moving at "+e.motionY+" b/s, E: "+(m-e.motionY*20));
					if (e.motionY < -0.1 && m-e.motionY*20 > 80) {
						ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.MIRROR.ordinal(), this, new PacketTarget.RadiusTarget(this, 32));
						e.attackEntityFrom(DamageSource.cactus, 1);
						this.breakMirror(world, x, y, z);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public boolean isFunctional() {
		if (broken)
			return false;
		if (MachineRegistry.getMachine(worldObj, xCoord, yCoord+1, zCoord) != null)
			return false;
		/*
		if (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
			return false;
		 */
		//if (worldObj.getPrecipitationHeight(xCoord, zCoord) > yCoord+1)
		//	return false;
		return plant != null && /*plant.canSeeTheSky(worldObj, xCoord, yCoord, zCoord)*/ plant.canSeeTheSky(this);
	}

	@SideOnly(Side.CLIENT)
	private void adjustAim(World world, int x, int y, int z, int meta) {
		if (plant == null)
			return;

		Coordinate target = plant.getAimingPositionForMirror(this);
		if (target == null)
			return;

		float finalphi;
		float finaltheta;

		long tot = world.getWorldTime();
		int time = (int)(tot%12000);

		time = this.forceDuskDawnAiming(tot, time);

		float sunphi = time >= 6000 ? -90 : 90;
		float suntheta = ReikaWorldHelper.getSunAngle(world);

		//rises in +90 sets in 270 (+x, -x)
		float movespeed = 0.5F;

		double[] angs = ReikaPhysicsHelper.cartesianToPolar(x-target.xCoord, y-target.yCoord, z-target.zCoord);
		float targetphi = (float)angs[2];
		float targettheta = (float)angs[1];

		targettheta = Math.abs(targettheta)-90;
		targettheta *= 0.5;

		sunphi = this.clampPhi(sunphi, time);
		boolean bool = time >= 6000 || targetphi > 270;

		//ReikaJavaLibrary.pConsole(targetphi+" clamped to "+this.clampPhi(targetphi, time)+"  :  "+bool);
		if (bool)
			targetphi = this.clampPhi(targetphi, time);

		if (time >= 6000) {
			finalphi = sunphi - (sunphi-targetphi)/2F;
		}
		else {
			finalphi = sunphi + (targetphi-sunphi)/2F; //These are mathematically equivalent...
		}

		float sunangle = time >= 6000 ? (float)(1-Math.cos(Math.toRadians((time-6000)*90D/6000D))) : (float)Math.cos(Math.toRadians(time*90D/6000D));


		finalphi = (finalphi*sunangle + (1-sunangle)*targetphi);
		finalphi = this.clampPhi(finalphi, time);

		finaltheta = targettheta + (suntheta - targettheta)/2F;

		//ReikaJavaLibrary.pConsole(targetphi);
		if (!(targetphi >= 0 && targetphi <= 90) && time >= 6000) {
			finalphi = -sunphi - (sunphi-targetphi)/2F;
			finalphi = (finalphi*sunangle + (1-sunangle)*targetphi);
		}

		finalphi = this.adjustPhiForClosestPath(finalphi);
		if (Math.abs(sunphi - targetphi) == 180) {
			//ReikaJavaLibrary.pConsole(x+", "+y+", "+z);
			finalphi = targetphi;
			finaltheta = Math.max(60-suntheta, finaltheta);
		}

		if (finalphi - phi > 180)
			finalphi -= 360;

		//ReikaJavaLibrary.pConsole(String.format("TIME: %d     SUN: %.3f    TARGET: %.3f     FINAL: %.3f", time, sunphi, targetphi, finalphi));

		targetTheta = finaltheta;
		targetPhi = finalphi;

		if (phi < targetPhi)
			phi += movespeed;
		if (phi > targetPhi)
			phi -= movespeed;

		if (theta < targetTheta)
			theta += movespeed;
		if (theta > targetTheta)
			theta -= movespeed;

		float aim = (float)Math.max(0, 1-ReikaMathLibrary.py3d(theta-targetTheta, 0, phi-targetPhi)/20D);
		if (Math.abs(aimFactor-aim) > 0.05) {
			lastAimFactor = aimFactor;
			aimFactor = aim;
			ReikaPacketHelper.sendSyncPacket(RotaryCraft.packetChannel, this, "aimFactor", true);
		}

		//ReikaJavaLibrary.pConsole(targetPhi+":"+phi);
		if (rotatingLarge) {
			rotatingLarge = Math.abs(targetPhi-phi) > 2;
		}
		else {
			rotatingLarge = Math.abs(targetPhi-phi) > 10;
		}
	}

	private int forceDuskDawnAiming(long tot, int time) {
		int day = (int)(tot%24000);
		if (ReikaMathLibrary.isValueInsideBoundsIncl(12000, 13000, day))
			return 11999;
		if (ReikaMathLibrary.isValueInsideBoundsIncl(23000, 24000, day))
			return 0;
		return time;
	}

	public void breakMirror(World world, int x, int y, int z) {
		broken = true;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.addModelledBlockParticles("/Reika/RotaryCraft/Textures/TileEntityTex/", world, x, y, z, this.getTile().getBlock(), Minecraft.getMinecraft().effectRenderer, ReikaJavaLibrary.makeListFrom(new double[]{0,0,1,1}), RotaryCraft.class);
		}
		ReikaSoundHelper.playBreakSound(world, x, y, z, Blocks.glass);
	}

	public void repair(World world, int x, int y, int z) {
		broken = false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("broke", broken);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		broken = NBT.getBoolean("broke");
	}

	@SideOnly(Side.CLIENT)
	private float clampPhi(float phi, int time) {
		boolean afternoon = time >= 6000;
		if (afternoon) {
			if (phi >= 360)
				phi -= 360;
			if (phi < -360)
				phi += 360;
		}
		else {
			if (phi > 180)
				phi -= 360;
			if (phi <= -180)
				phi += 360;
		}
		return phi;
	}

	@SideOnly(Side.CLIENT)
	private float adjustPhiForClosestPath(float finalphi) {
		//ReikaJavaLibrary.pConsole(String.format("PHI: %.3f    TARGET: %.3f", phi, finalphi));
		if (!ReikaMathLibrary.isSameSign(finalphi, phi)) {
			if (finalphi < -180) {
				finalphi += 360;
			}
			if (finalphi > 180) {
				finalphi -= 360;
			}
			if (finalphi < 0 && finalphi < -90) {
				finalphi += 360;
			}
		}
		return finalphi;
	}

	@Override
	public void onEMP() {}

	@Override
	public void breakBlock() {
		if (plant != null)
			plant.invalidate(worldObj);
	}

}
