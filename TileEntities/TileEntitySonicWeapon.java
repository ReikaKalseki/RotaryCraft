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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaPhysicsHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelSonicWeapon;

public class TileEntitySonicWeapon extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	public long setpitch;
	public long setvolume;

	public static final long MAXBROWNNOTE = 64; //Triggers food poisoning
	public static final long BATKILL = 80000; //Well within their echolocation pitch range
	public static final long OMINOUS = 16; //That almost-infrasonic uneasy-feeling-generating tone; triggers blindness effect
	public static final long DOGWHISTLE = 40000; //dog whistle
	public static final long LRAD = 2400; //Pitch for most crowd-control audio weapons

	public static final long LETHALVOLUME = 100000000; //10^8 W/m (~38psi)=200dB for 1-hit kill
	public static final long BRICKDESTROY = 1000000; //== 20kPa overpressure -> "Brick walls destroyed"
	public static final long LRADVOLUME = 1260;
	public static final long SHATTERGLASS = 118680; //1psi overpressure
	public static final long BREAKWOOD = 475410; //2 psi overpressure
	public static final long LUNGDAMAGE = 2971000; //5 psi causes pulmonary damage -> cause drowning effect
	public static final long BRAINDAMAGE = 3906200; //125kPa causes TBIs
	public static final long EYEDAMAGE = 1807500; //Causes blindness

	public static final long REFERENCE = 1000000000000L; // 10^-12 W/m^2 reference

	public static final int fudge = 100;

	public static final int FALLOFF = 16384;

	public static final long INTENSITYPERTORQUE = 262144L*65536L*256L;
	public static final int HZPEROMEGA = 8192;

	public static final boolean ENABLEFREQ = false;
	public static final boolean DECIBELMODE = true;

	public int brownrange;
	public int batrange;
	public int ominousrange;
	public int dogrange;
	public int lradrange;

	public int killrange;
	public int brickrange;
	public int glassrange;
	public int woodrange;
	public int lungrange;
	public int brainrange;
	public int eyerange;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		this.getEffectRanges();
		if (setpitch > this.getMaxPitch())
			setpitch = this.getMaxPitch();
		if (setvolume > this.getMaxVolume())
			setvolume = this.getMaxVolume();
		this.applyEffects(world, x, y, z);
	}

	public void applyEffects(World world, int x, int y, int z) {
		this.testKill(world, x, y, z);
		this.breakBrick(world, x, y, z);
		this.testLung(world, x, y, z);
		this.testEye(world, x, y, z);
		this.testBrain(world, x, y, z);
	}

	public void testEye(World world, int x, int y, int z) {
		boolean vuln = true;
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (ent instanceof EntityPlayer)
				if (!this.isPlayerVulnerable((EntityPlayer)ent))
					vuln = false;
			//ReikaChatHelper.write(this.EYEDAMAGE-this.fudge*ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getMaxVolume()));
			if (vuln && fudge*ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()) >= EYEDAMAGE) {
				ent.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0));
				//ReikaChatHelper.write(ent.getAITarget());
				//ent.getNavigator().clearPathEntity();
				//ent.setAttackTarget(null);
				//ent.setRevengeTarget(null);
				//ent.setLastAttackingEntity(null);
				if (ent instanceof EntityCreature) {
					//((EntityCreature)ent).setTarget(null);
				}
			}
		}
	}

	public void testBrain(World world, int x, int y, int z) {
		boolean vuln = true;
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (ent instanceof EntityPlayer)
				if (!this.isPlayerVulnerable((EntityPlayer)ent))
					vuln = false;
			//ReikaChatHelper.write(this.BRAINDAMAGE-this.fudge*ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()));
			if (vuln && fudge*ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()) >= BRAINDAMAGE) {
				ent.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 10));
				ent.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 3));
				ent.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
				if (ent instanceof EntityAnimal) {
					ent.getNavigator().clearPathEntity();
					if (ent.getNavigator().noPath()) {
						double randx = ent.posX - 8 + par5Random.nextInt(17);
						double randz = ent.posZ - 8 + par5Random.nextInt(17);
						int randy = world.getTopSolidOrLiquidBlock((int)randx, (int)randz);
						PathEntity path = ent.getNavigator().getPathToXYZ(randx, randy, randz);
						ent.getNavigator().setPath(path, 0.2F);
					}
				}
				if (ent instanceof EntityMob) {
					AxisAlignedBB nearmob = AxisAlignedBB.getBoundingBox(ent.posX, ent.posY, ent.posZ, ent.posX, ent.posY, ent.posZ).expand(10, 10, 10);
					Entity target = world.findNearestEntityWithinAABB(EntityMob.class, nearmob, ent);
					if (target instanceof EntityMob) {
						ent.setAttackTarget((EntityLiving)target);
						((EntityMob)ent).setTarget(target);
						ent.setRevengeTarget((EntityLiving)target);
						ent.setLastAttackingEntity(target);
					}
				}
			}
		}
	}

	public void testLung(World world, int x, int y, int z) {
		boolean vuln = true;
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (ent instanceof EntityPlayer)
				if (!this.isPlayerVulnerable((EntityPlayer)ent))
					vuln = false;
			if (vuln && ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()) >= LUNGDAMAGE)
				if (par5Random.nextInt(40) == 0)
					ent.attackEntityFrom(DamageSource.drown, 1);
		}
	}

	public void breakBrick(World world, int x, int y, int z) {
		//ReikaWorldHelper
	}

	public void testKill(World world, int x, int y, int z) {
		boolean vuln = true;
		//ReikaChatHelper.write(this.getMaxVolume());
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		List inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);/*
		if (inbox.size() > 0) {
			for (int i = 0; i < inbox.size(); i++) {
				EntityLiving ent = (EntityLiving)inbox.get(i);
				if (vuln && ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()) >= LETHALVOLUME)
					ReikaJavaLibrary.pConsole(ent.getEntityName()+" @ "+ent.posX+", "+ent.posZ+" ("+i+"/"+inbox.size()+") @ Range "+Math.sqrt(this.getVolume()/LETHALVOLUME));
			}
		}*/
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (ent instanceof EntityPlayer)
				if (!this.isPlayerVulnerable((EntityPlayer)ent))
					vuln = false;
			//ReikaChatHelper.write(this.volume);
			//ReikaChatHelper.write(this.LETHALVOLUME-ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getMaxVolume()));
			//ReikaChatHelper.write(this.LETHALVOLUME);
			//ReikaChatHelper.write(ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getMaxVolume()));
			if (vuln && ReikaPhysicsHelper.inverseSquare(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, this.getVolume()) >= LETHALVOLUME)
				ent.attackEntityFrom(DamageSource.outOfWorld, 200000);
		}
	}

	public long getMaxPitch() {
		return (omega*HZPEROMEGA);
	}

	public long getMaxVolume() {
		return (INTENSITYPERTORQUE*torque);
	}

	public long getVolume() {
		long maxvol = this.getMaxVolume();
		if (setvolume > maxvol)
			return maxvol/1000000;
		return setvolume/1000000;
	}

	public long getPitch() {
		long maxpitch = this.getMaxPitch();
		if (setpitch > maxpitch)
			return maxpitch;
		return setpitch;
	}

	public int getRange() {
		if (this != null)
			return 16;
		int overpower = (int)(power-MINPOWER)/FALLOFF;
		if (overpower > RotaryConfig.maxsonicrange)
			return RotaryConfig.maxsonicrange;
		return overpower;
	}

	public void getEffectRanges() {
		int maxrange = this.getRange();
	}

	private boolean isPlayerVulnerable(EntityPlayer ep) {
		if (ep.capabilities.isCreativeMode)
			return false;
		if (ep.inventory.armorInventory[3] != null) {
			//if (ep.inventory.armorInventory[0].itemID == RotaryCraft.earmuff.itemID)
			return false;
		}
		return true;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setLong("setfrequency", setpitch);
		NBT.setLong("setvolume", setvolume);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		setpitch = NBT.getLong("setfrequency");
		setvolume = NBT.getLong("setvolume");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelSonicWeapon();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SONICWEAPON.ordinal();
	}

	@Override
	public int getMaxRange() {
		return RotaryConfig.maxsonicrange;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
