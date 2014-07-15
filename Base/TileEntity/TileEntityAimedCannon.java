/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public abstract class TileEntityAimedCannon extends TileEntityPowerReceiver implements RangedEffect, DiscreteFunction, ConditionalOperation {

	private List<String> safePlayers = new ArrayList<String>();

	public boolean targetPlayers = true;
	private int numSafePlayers = 0;

	public float theta;
	protected double[] target = new double[4];

	protected EntityLivingBase closestMob;
	protected double voffset = -0.125;

	public static final int MAXLOWANGLE = -10;
	/** Up/down angle */
	protected int dir = 1;

	public boolean isCustomAim;

	public final double[] getTarget() {
		return target;
	}

	protected void printSafeList() {
		for (int i = 0; i < safePlayers.size(); i++) {
			String player = safePlayers.get(i);
			if (player == null)
				player = "NULL PLAYER IN SLOT "+i;
			else if (player.isEmpty())
				player = "EMPTY STRING PLAYER IN SLOT "+i;
			ReikaJavaLibrary.pConsole("Side "+FMLCommonHandler.instance().getEffectiveSide()+": Safe Player "+(i+1)+" of "+safePlayers.size()+": "+player);
		}
	}

	protected final double getFiringPositionY(double dy) {
		double a = dir == 1 ? 0.75 : 0.25;
		return yCoord+voffset*0+a+dy;
	}

	@Override
	public void updateTileEntity() {
		//this.printSafeList();
		super.updateTileEntity();
		tickcount++;
		if (worldObj.isRemote)
			return;
		switch(this.getBlockMetadata()) {
		case 0:
			this.getPowerBelow();
			dir = 1;
			break;
		case 1:
			this.getPowerAbove();
			dir = -1;
			break;
		}
		if (power < MINPOWER)
			return;
		if (isCustomAim) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				phi--;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				phi++;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if (theta < 45)
					theta++;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if (theta > MAXLOWANGLE)
					theta--;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
				if (this.hasAmmo()) {
					double dd = 20;
					double[] tg = ReikaPhysicsHelper.polarToCartesian(dd, theta, 90-phi);
					tg[0] += xCoord;
					tg[1] += yCoord;
					tg[2] += zCoord;
					this.fire(worldObj, tg);
				}
			}
			return;
		}
		else {
			target = this.getTarget(worldObj, xCoord, yCoord, zCoord);
			this.adjustAim(worldObj, xCoord, yCoord, zCoord, target);
		}
	}

	public boolean isAimingAtTarget(World world, int x, int y, int z, double[] t) {
		double[] tg = ReikaPhysicsHelper.cartesianToPolar(x-t[0], y-t[1], z-t[2]);
		tg[1] = Math.abs(tg[1])-90;
		float phi2 = phi;
		while (phi2 < 0)
			phi2 += 360;
		while (phi2 >= 360)
			phi2 -= 360;
		//ReikaJavaLibrary.pConsole("PHI: "+phi2+" THETA: "+theta+" for "+tg[2]+", "+tg[1]);
		if (tg[2] - phi2 > 180)
			tg[2] -= 360;
		if (!ReikaMathLibrary.approxr(theta, tg[1], 5))
			return false;
		if (!ReikaMathLibrary.approxr(phi2, tg[2], 5))
			return false;
		return true;
	}

	public void adjustAim(World world, int x, int y, int z, double[] t) {
		if (t[3] != 1)
			return;
		double dx = x+0.5-t[0];
		double dy = y+0.5-t[1];
		double dz = z+0.5-t[2];
		double[] tg = ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz);
		tg[1] = Math.abs(tg[1])-90+0.25;
		//ReikaJavaLibrary.pConsole("PHI: "+phi+" THETA: "+theta+" for "+tg[2]+", "+tg[1]);
		if (tg[2] - phi > 180)
			tg[2] -= 360;
		float movespeed = this.getAimingSpeed();
		if (phi < tg[2])
			phi += movespeed*2;
		if (phi > tg[2])
			phi -= movespeed*2;
		if (theta < tg[1])
			theta += movespeed;
		if (theta > tg[1])
			theta -= movespeed;
		if (theta < MAXLOWANGLE && dir == 1)
			theta = MAXLOWANGLE;
		if (theta > -MAXLOWANGLE && dir == -1)
			theta = MAXLOWANGLE;
	}

	protected float getAimingSpeed() {
		return 1;
	}

	public abstract boolean hasAmmo();

	protected abstract double[] getTarget(World world, int x, int y, int z);

	public abstract void fire(World world, double[] xyz);

	@Override
	public final void animateWithTick(World world, int x, int y, int z) {}

	protected abstract double randomOffset();

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setFloat("theta", theta);
		NBT.setInteger("direction", dir);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBT.setInteger("numsafe", numSafePlayers);

		NBT.setBoolean("aim", isCustomAim);

		for (int i = 0; i < safePlayers.size(); i++) {
			NBT.setString("Safe_Player_"+String.valueOf(i), safePlayers.get(i));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		isCustomAim = NBT.getBoolean("aim");

		safePlayers = new ArrayList<String>();
		numSafePlayers = NBT.getInteger("numsafe");
		for (int i = 0; i < numSafePlayers; i++) {
			safePlayers.add(NBT.getString("Safe_Player_"+String.valueOf(i)));
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		theta = NBT.getFloat("theta");
		dir = NBT.getInteger("direction");
	}

	@Override
	public int getUpdatePacketRadius() {
		return 192;
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	protected abstract boolean isValidTarget(EntityLivingBase ent);

	protected final boolean isMobOrUnlistedPlayer(EntityLivingBase ent) {
		return (ReikaEntityHelper.isHostile(ent) || (targetPlayers && ent instanceof EntityPlayer && !this.playerIsSafe(((EntityPlayer)ent))));
	}

	public void addPlayerToWhiteList(String name) {
		ReikaChatHelper.write(name+" added to "+placer+"'s whitelist for the\n"+this.getName()+" at "+xCoord+", "+yCoord+", "+zCoord+".");
		if (name.equals(placer)) {
			ReikaChatHelper.write("Note: "+name+" is the owner;");
			ReikaChatHelper.write("They did not need to tell the "+this.getName()+" to not target them.");
		}
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
			return;
		safePlayers.add(name);
		numSafePlayers++;
	}

	public void removePlayerFromWhiteList(String name) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			safePlayers.remove(name);
			numSafePlayers--;
		}
		safePlayers.removeAll(Arrays.asList("", null));
		ReikaChatHelper.write(name+" removed from "+placer+"'s "+this.getName()+" whitelist.");
	}

	public final boolean playerIsSafe(EntityPlayer ep) {
		if (!ConfigRegistry.TURRETPLAYERS.getState())
			return true;
		if (ep.capabilities.isCreativeMode)
			return true;
		String name = ep.getEntityName();
		if (name == null)
			return true;
		if (name.equals("Reika_Kalseki")) //If you try...
			return true;
		if (this.getPlacer() == null)
			return safePlayers.contains(name);
		if (safePlayers == null)
			return name.equals(this.getPlacer().getEntityName());
		return safePlayers.contains(name) || name.equals(this.getPlacer().getEntityName());
	}

	public List<String> getCopyOfSafePlayerList() {
		return ReikaJavaLibrary.copyList(safePlayers);
	}

	public int getOperationTime() {
		return 20;
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasAmmo();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Ammunition";
	}

}
