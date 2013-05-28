/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaChunkHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelSpawner;

public class TileEntitySpawnerController extends TileEntityPowerReceiver implements GuiController {
	public static final int FALLOFF = 128; // rad/s per spawn tick
	public static final int BASEDELAY = 800; //40s default max spawner delay

	public boolean disable;
	private int hijackdelay = BASEDELAY;
	private int spawnCount = 4;
	private int spawnRange = 4;

	public int setDelay = BASEDELAY;

    public int getMinDelay() {
    	int time = BASEDELAY-40*(int)ReikaMathLibrary.logbase(omega, 2);
    	if (time < 0)
    		time = 0; //0 tick minimum
    	return time;
    }

    public int getDelay() {
    	if (setDelay > this.getMinDelay())
    		return setDelay;
    	else
    		return this.getMinDelay();
    }

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		double dist = ReikaMathLibrary.py3d(xCoord-var1.posX, yCoord-var1.posY, zCoord-var1.posZ);
		return (dist <= 8) && (power >= MINPOWER || true);
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
			return;
		}
		this.getPower4Sided(0, -1, 0); //The spawner itself is the power input
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
		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y-1, z);
		if (tile == null)
			return;
		MobSpawnerBaseLogic lgc = tile.func_98049_a();
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

		// This is to hide the particle effects
		//tile.xCoord = 0;
		//tile.yCoord = 0;
		//tile.zCoord = 0;
		//ReikaChatHelper.writeInt(tile.xCoord);
	}

	private void applyToSpawner(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y-1, z);
		if (tile == null)
			return;
		MobSpawnerBaseLogic lgc = tile.func_98049_a();
		lgc.spawnDelay = 5; //Disable "real" spawner
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", lgc.spawnDelay));
		if (disable) {
			hijackdelay = 255;
			this.shutdownSpawner(world, x, y, z);
		}
		else if (this.canSpawn(world, x, y, z))
			this.setSpawnDelayLimit(world, x, y, z, this.getDelay());
		else
			hijackdelay = 255;
		this.hijackSpawn(world, x, y-1, z, tile);
	}

	private int getNumberSpawns(World world, int x, int y, int z, Entity ent) {
		int num = ReikaChunkHelper.getChunkRangePopln(world, ent.getClass(), x-32, z-32, x+32, z+32);
		return num;
	}

	private boolean canSpawn(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y-1, z);
		if (tile == null)
			return false;
		MobSpawnerBaseLogic lgc = tile.func_98049_a();
		String mobname = lgc.getEntityNameToSpawn();
		Entity ent = EntityList.createEntityByName(mobname, world);
		int num = this.getNumberSpawns(world, x, y, z, ent);
		return (num < RotaryConfig.spawnerlimit);
	}

	/** Fetches from real spawner! */
	private int getSpawnDelay(World world, int x, int y, int z) {
		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y-1, z);
		if (tile == null)
			return -1;
		MobSpawnerBaseLogic lgc = tile.func_98049_a();
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
		return (id == Block.mobSpawner.blockID);
	}

    private void hijackSpawn(World world, int x, int y, int z, TileEntityMobSpawner tile) //y = y-1, since spawner below
    {
		MobSpawnerBaseLogic lgc = tile.func_98049_a();
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

                boolean var12 = false;

                for (int var2 = 0; var2 < spawnCount; ++var2)
                {
                    Entity var13 = EntityList.createEntityByName(lgc.getEntityNameToSpawn(), world);

                    if (var13 == null)
                    {
                        return;
                    }

// This is the max-6 code int var4 = world.getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)).expand((double)(this.spawnRange * 2), 4.0D, (double)(this.spawnRange * 2))).size();

                    if (var13 != null)
                    {
                        var5 = x + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange;
                        double var7 = y + world.rand.nextInt(3) - 1;
                        double var9 = z + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange;
                        EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving)var13 : null;
                        var13.setLocationAndAngles(var5, var7, var9, world.rand.nextFloat() * 360.0F, 0.0F);

                        if (var11 == null || var11.getCanSpawnHere())
                        {
                            lgc.func_98265_a(var13);
                            world.spawnEntityInWorld(var13);
                            world.playAuxSFX(2004, x, y, z, 0);

                            if (var11 != null)
                            {
                                var11.spawnExplosionParticle();
                            }

                            var12 = true;
                        }
                    }
                }
            }
        }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
	public void writeToNBT(NBTTagCompound NBT)
    {
        super.writeToNBT(NBT);
        NBT.setInteger("hjdelay", hijackdelay);
        NBT.setInteger("setdelay", setDelay);
        NBT.setBoolean("disable", disable);
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
	public void readFromNBT(NBTTagCompound NBT)
    {
        super.readFromNBT(NBT);
        hijackdelay = NBT.getInteger("hjdelay");
        setDelay = NBT.getInteger("setdelay");
        disable = NBT.getBoolean("disable");
    }

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelSpawner();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SPAWNERCONTROLLER.ordinal();
	}
}
