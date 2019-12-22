/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Event.SpawnerControllerSpawnEvent;
import Reika.RotaryCraft.API.Interfaces.ControllableSpawner;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySpawnerController extends TileEntityPowerReceiver implements GuiController, DiscreteFunction, ConditionalOperation {
	public static final int BASEDELAY = 800; //40s default max spawner delay

	public boolean disable;

	private SpawnerControl control = null;

	private int setDelay = BASEDELAY;
	private StepTimer timer = new StepTimer(0);

	public int getOperationTime() {
		int time = BASEDELAY-40*(int)ReikaMathLibrary.logbase(omega, 2);
		if (time < 0)
			time = 0; //0 tick minimum
		return time;
	}

	public void setDelay(int delay) {
		setDelay = delay;
		timer.setCap(setDelay);
		timer.setTick(Math.min(setDelay-1, timer.getTick()));
	}

	public int getDelay() {
		if (setDelay >= this.getOperationTime())
			return setDelay;
		else
			return this.getOperationTime();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (!this.isValidLocation(world, x, y, z)) {
			disable = false;
			this.setDelay(0);
			timer.setTick(0);
			omega = torque = 0;
			power = 0;
			this.setPointingOffset(0, -1, 0);
			control = null;
			return;
		}
		this.getOffsetPower4Sided(0, -1, 0, true); //The spawner itself is the power input
		if (power >= MINPOWER && setDelay > 0)
			this.applyToSpawner(world, x, y, z);
	}

	private void shutdownSpawner(World world, int x, int y, int z) {
		control.setInactive();
		for (int i = 0; i < 4; i++) {
			double var1 = xCoord + worldObj.rand.nextFloat();
			double var3 = (float)yCoord-1 + worldObj.rand.nextFloat();
			double var5 = zCoord + worldObj.rand.nextFloat();
			double var11 = xCoord-0.25 + 1.5*worldObj.rand.nextFloat();
			double var15 = zCoord-0.25 + 1.5*worldObj.rand.nextFloat();
			worldObj.spawnParticle("reddust", var11, var3, var15, 0, 0, 0);
			worldObj.spawnParticle("crit", var1, var3, var5, -0.3+0.6*worldObj.rand.nextFloat(), 0.4*worldObj.rand.nextFloat(), -0.3+0.6*worldObj.rand.nextFloat());
		}
	}

	private void applyToSpawner(World world, int x, int y, int z) {
		control = new SpawnerControl(this.getAdjacentTileEntity(ForgeDirection.DOWN));
		if (disable || world.isBlockIndirectlyGettingPowered(x, y-1, z)) {
			this.shutdownSpawner(world, x, y, z);
		}
		else if (this.canSpawn(world, x, y, z)) {
			timer.update();
			control.setDelay(setDelay-timer.getTick());
			timer.checkCap();
		}
		else {
			;//hijackdelay = 255; //"do not affect"
		}
		if (control.getDelay() <= 0)
			control.spawnCycle(this);
	}

	private int getNumberNearbySpawns(World world, int x, int y, int z, Class ent) {
		return world.getEntitiesWithinAABB(ent, ReikaAABBHelper.getBlockAABB(x, y, z).expand(16, 24, 16)).size();
	}

	private boolean canSpawn(World world, int x, int y, int z) {
		Class ent = this.getEntityClass();
		int num = this.getNumberNearbySpawns(world, x, y, z, ent);
		return num < this.getSpawnLimit();
	}

	private Class getEntityClass() {
		return control.getEntity();
	}

	private int getSpawnLimit() {
		return 99;//Math.max(24, ConfigRegistry.SPAWNERLIMIT.getValue());
	}

	public boolean isValidLocation(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y-1, z);
		return b == Blocks.mob_spawner || this.getAdjacentTileEntity(ForgeDirection.DOWN) instanceof ControllableSpawner;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("setdelay", setDelay);
		NBT.setBoolean("disable", disable);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		this.setDelay(NBT.getInteger("setdelay"));
		disable = NBT.getBoolean("disable");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPAWNERCONTROLLER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean areConditionsMet() {
		return this.isValidLocation(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Spawner";
	}

	private static class SpawnerControl {

		private final TileEntityMobSpawner tile;
		private final ControllableSpawner proxy;
		private final boolean vanilla;

		private SpawnerControl(TileEntity te) {
			vanilla = te instanceof TileEntityMobSpawner;
			tile = vanilla ? (TileEntityMobSpawner)te : null;
			proxy = vanilla ? null : (ControllableSpawner)te;
		}

		private void setInactive() {
			if (vanilla) {
				tile.func_145881_a().spawnDelay = 5;
			}
			else {
				proxy.setInactive();
			}
		}

		private int getDelay() {
			return vanilla ? tile.func_145881_a().spawnDelay : proxy.getCurrentSpawnDelay();
		}

		private void setDelay(int t) {
			if (vanilla) {
				tile.func_145881_a().spawnDelay = t;
			}
			else {
				proxy.setCurrentSpawnDelay(t);
			}
		}

		private void spawnCycle(TileEntitySpawnerController te) {
			if (vanilla) {
				runVanillaSpawnCycle(tile, te);
			}
			else {
				Collection<Entity> c = proxy.performSpawnCycle();
				for (Entity e : c)
					flagNoDespawn(e);
			}
			MinecraftForge.EVENT_BUS.post(new SpawnerControllerSpawnEvent(te, this.getEntity()));
		}

		private Class<? extends EntityLiving> getEntity() {
			return vanilla ? (Class)EntityList.stringToClassMapping.get(tile.func_145881_a().getEntityNameToSpawn()) : proxy.getSpawningEntityClass();
		}

	}

	public static final String CONTROLLED_SPAWN_TAG = "ControllerSpawned";

	private static void flagNoDespawn(Entity e) {
		NBTTagCompound tag = e.getEntityData();
		tag.setBoolean(CONTROLLED_SPAWN_TAG, true);
	}

	public static boolean isFlaggedNoDespawn(Entity e) {
		return e.getEntityData().getBoolean(CONTROLLED_SPAWN_TAG);
	}

	private static void runVanillaSpawnCycle(TileEntityMobSpawner tile, TileEntitySpawnerController c) {
		World world = tile.worldObj;
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		MobSpawnerBaseLogic lgc = tile.func_145881_a();
		lgc.maxNearbyEntities = Integer.MAX_VALUE;
		lgc.activatingRangeFromPlayer = Integer.MAX_VALUE;

		if (world.isRemote) {
			double var1 = x+world.rand.nextFloat();
			double var3 = y+world.rand.nextFloat();
			double var5 = z+world.rand.nextFloat();
			world.spawnParticle("smoke", var1, var3, var5, 0, 0, 0);
			world.spawnParticle("flame", var1, var3, var5, 0, 0, 0);
			lgc.field_98284_d = lgc.field_98287_c;
			lgc.field_98287_c = (lgc.field_98287_c+1000D / (lgc.spawnDelay+200))%360;
		}
		else {
			for (int i = 0; i < lgc.spawnCount; i++) {
				Entity toSpawn = EntityList.createEntityByName(lgc.getEntityNameToSpawn(), world);

				// This is the max-6 code
				//int var4 = world.getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(spawnRange*2, 4, spawnRange*2)).size();

				if (toSpawn != null) {
					double ex = x+(world.rand.nextDouble()-world.rand.nextDouble())*lgc.spawnRange;
					double ey = y+world.rand.nextInt(3)-1;
					double ez = z+(world.rand.nextDouble()-world.rand.nextDouble())*lgc.spawnRange;
					EntityLiving livingSpawn = toSpawn instanceof EntityLiving ? (EntityLiving)toSpawn : null;
					toSpawn.setLocationAndAngles(ex, ey, ez, world.rand.nextFloat()*360, 0);

					if (livingSpawn == null || livingSpawn.getCanSpawnHere()) {
						lgc.func_98265_a(toSpawn);
						flagNoDespawn(toSpawn);
						world.playAuxSFX(2004, x, y, z, 0);
						if (livingSpawn != null) {
							livingSpawn.spawnExplosionParticle();
						}
					}
				}
			}
		}
	}
}
