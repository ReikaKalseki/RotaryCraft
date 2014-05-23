/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Event.SpawnerControllerSpawnEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySpawnerController extends TileEntityPowerReceiver implements GuiController, DiscreteFunction, ConditionalOperation {
	public static final int BASEDELAY = 800; //40s default max spawner delay

	public boolean disable;
	private int hijackdelay = BASEDELAY;
	private int spawnCount = 4;
	private int spawnRange = 4;

	public int setDelay = BASEDELAY;

	public int getOperationTime() {
		int time = BASEDELAY-40*(int)ReikaMathLibrary.logbase(omega, 2);
		if (time < 0)
			time = 0; //0 tick minimum
		return time;
	}

	public int getDelay() {
		if (setDelay > this.getOperationTime())
			return setDelay;
		else
			return this.getOperationTime();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (!this.isValidLocation(world, x, y, z)) {
			disable = false;
			hijackdelay = 0;
			setDelay = 0;
			omega = torque = 0;
			power = 0;
			this.setPointingOffset(0, -1, 0);
			return;
		}
		this.getOffsetPower4Sided(0, -1, 0); //The spawner itself is the power input
		if (power < MINPOWER) {
			//this.shutdownSpawner(world, x, y, z);
			disable = false;
			hijackdelay = 255;
			setDelay = 0;
			return;
		}
		this.applyToSpawner(world, x, y, z);
	}

	private void shutdownSpawner(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)this.getAdjacentTileEntity(ForgeDirection.DOWN);
		if (tile == null)
			return;
		MobSpawnerBaseLogic lgc = tile.getSpawnerLogic();
		lgc.field_98287_c = 0;
		lgc.field_98284_d = 0;
		lgc.spawnDelay = 5;
		for (int i = 0; i < 4; i++) {
			double var1 = xCoord + worldObj.rand.nextFloat();
			double var3 = (float)yCoord-1 + worldObj.rand.nextFloat();
			double var5 = zCoord + worldObj.rand.nextFloat();
			double var11 = xCoord-0.25 + 1.5*worldObj.rand.nextFloat();
			double var15 = zCoord-0.25 + 1.5*worldObj.rand.nextFloat();
			worldObj.spawnParticle("reddust", var11, var3, var15, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("crit", var1, var3, var5, -0.3+0.6*worldObj.rand.nextFloat(), 0.4*worldObj.rand.nextFloat(), -0.3+0.6*worldObj.rand.nextFloat());
		}
	}

	private void applyToSpawner(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)this.getAdjacentTileEntity(ForgeDirection.DOWN);
		if (tile == null)
			return;
		MobSpawnerBaseLogic lgc = tile.getSpawnerLogic();
		lgc.spawnDelay = 5; //Disable "real" spawner
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", lgc.spawnDelay));
		if (disable || world.isBlockIndirectlyGettingPowered(x, y-1, z)) {
			hijackdelay = 255;
			this.shutdownSpawner(world, x, y, z);
		}
		else if (this.canSpawn(world, x, y, z))
			this.setSpawnDelayLimit(world, x, y, z, this.getDelay());
		else
			hijackdelay = 255;
		this.hijackSpawn(world, x, y-1, z, tile);
	}

	private int getNumberSpawns(World world, int x, int y, int z, Class ent) {
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(8, 8, 8);
		int num = world.getEntitiesWithinAABB(ent, box).size();
		return num;
	}

	private boolean canSpawn(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)this.getAdjacentTileEntity(ForgeDirection.DOWN);
		if (tile == null)
			return false;
		MobSpawnerBaseLogic lgc = tile.getSpawnerLogic();
		Class ent = this.getEntityClass(lgc);
		int num = this.getNumberSpawns(world, x, y, z, ent);
		return num < this.getSpawnLimit();
	}

	private Class getEntityClass(MobSpawnerBaseLogic lgc) {
		return (Class)EntityList.stringToClassMapping.get(lgc.getEntityNameToSpawn());
	}

	private int getSpawnLimit() {
		return Math.max(24, ConfigRegistry.SPAWNERLIMIT.getValue());
	}

	/** Fetches from real spawner! */
	private int getSpawnDelay(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)this.getAdjacentTileEntity(ForgeDirection.DOWN);
		if (tile == null)
			return -1;
		MobSpawnerBaseLogic lgc = tile.getSpawnerLogic();
		return lgc.spawnDelay;
	}

	private void setSpawnDelayLimit(World world, int x, int y, int z, int time) {
		/*		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y-1, z);
		if (tile == null)
			return;*/
		if (hijackdelay > time)
			hijackdelay = time;
		//ReikaChatHelper.writeInt(this.hijackdelay);
	}

	public boolean isValidLocation(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y-1, z);
		return id == Block.mobSpawner.blockID;
	}

	private void hijackSpawn(World world, int x, int y, int z, TileEntityMobSpawner tile) //y = y-1, since spawner below
	{
		MobSpawnerBaseLogic lgc = tile.getSpawnerLogic();
		double var5;

		if (world.isRemote)
		{
			double var1 = x + world.rand.nextFloat();
			double var3 = y + world.rand.nextFloat();
			var5 = z + world.rand.nextFloat();
			world.spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);

			if (hijackdelay > 0)
			{
				--hijackdelay;
			}

			lgc.field_98284_d = lgc.field_98287_c;
			lgc.field_98287_c = (lgc.field_98287_c + 1000.0F / (hijackdelay + 200.0F)) % 360.0D;
		}
		else
		{

			if (hijackdelay > 0)
			{
				--hijackdelay;
				return;
			}

			hijackdelay = this.getDelay();

			for (int i = 0; i < spawnCount; ++i)
			{
				Entity toSpawn = EntityList.createEntityByName(lgc.getEntityNameToSpawn(), world);

				// This is the max-6 code int var4 = world.getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)).expand((double)(this.spawnRange * 2), 4.0D, (double)(this.spawnRange * 2))).size();

				if (toSpawn != null)
				{
					var5 = x + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange;
					double var7 = y + world.rand.nextInt(3) - 1;
					double var9 = z + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange;
					EntityLiving livingSpawn = toSpawn instanceof EntityLiving ? (EntityLiving)toSpawn : null;
					toSpawn.setLocationAndAngles(var5, var7, var9, world.rand.nextFloat() * 360.0F, 0.0F);

					if (livingSpawn == null || livingSpawn.getCanSpawnHere()) {

						lgc.func_98265_a(toSpawn);
						world.playAuxSFX(2004, x, y, z, 0);

						if (livingSpawn != null) {
							livingSpawn.spawnExplosionParticle();
							MinecraftForge.EVENT_BUS.post(new SpawnerControllerSpawnEvent(this, livingSpawn.getClass()));
						}
					}
				}
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("hjdelay", hijackdelay);
		NBT.setInteger("setdelay", setDelay);
		NBT.setBoolean("disable", disable);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		hijackdelay = NBT.getInteger("hjdelay");
		setDelay = NBT.getInteger("setdelay");
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
}
