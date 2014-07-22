/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Interfaces.SemiTransparent;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Laserable;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityHeatRay extends TileEntityBeamMachine implements RangedEffect {

	/** Rate of conversion - one power++ = 1/falloff ++ blocks range */
	public static final int FALLOFF = 256;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		power = (long)omega*(long)torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		//if ((world.getTotalWorldTime()&2) == 2) //halves load
		this.scorch(world, x, y, z, meta);
	}

	public void scorch(World world, int x, int y, int z, int metadata) {
		boolean blocked = false;
		int step;
		if (power >= MINPOWER) { //2MW+ (real military laser)
			int maxdist = this.getRange();
			for (step = 1; step < maxdist && (step < this.getMaxRange() || this.getMaxRange() == -1) && !blocked; step++) {
				int dx = x+step*xstep;
				int dy = y+step*ystep;
				int dz = z+step*zstep;
				int id = world.getBlockId(dx, dy, dz);
				int meta2 = world.getBlockMetadata(dx, dy, dz);
				if (id != 0 && Block.blocksList[id].isFlammable(world, dx, dy, dz, meta2, ForgeDirection.UP))
					this.ignite(world, dx, dy, dz, metadata, step);
				//ReikaJavaLibrary.pConsole(Block.blocksList[id]);
				if (this.makeBeam(world, x, y, z, step, id, meta2, maxdist)) {
					blocked = true;
					tickcount = 0;
				}
				if (Block.blocksList[id] instanceof SemiTransparent) {
					SemiTransparent st = (SemiTransparent)Block.blocksList[id];
					if (st.isOpaque(meta2))
						blocked = true;
				}
				else if (Block.opaqueCubeLookup[id])
					blocked = true; //break loop
				world.markBlockForUpdate(dx, dy, dz);
			}
			AxisAlignedBB zone = this.getBurnZone(metadata, step);
			List inzone = worldObj.getEntitiesWithinAABB(Entity.class, zone);
			for (int i = 0; i < inzone.size(); i++) {
				if (inzone.get(i) instanceof Entity) {
					Entity caught = (Entity)inzone.get(i);
					if (!(caught instanceof EntityItem)) //Do not burn drops
						caught.setFire(this.getBurnTime());	// 1 Hearts worth of fire at min power, +1 heart for every 65kW extra
					if (caught instanceof EntityTNTPrimed)
						world.spawnParticle("lava", caught.posX+rand.nextFloat(), caught.posY+rand.nextFloat(), caught.posZ+rand.nextFloat(), 0, 0, 0);
					if (caught instanceof Laserable) {
						((Laserable)caught).whenInBeam(world, MathHelper.floor_double(caught.posX), MathHelper.floor_double(caught.posY), MathHelper.floor_double(caught.posZ), power, step);
					}
				}
			}
		}
	}

	public int getBurnTime() {
		return 2+(int)(16L*power/MINPOWER);
	}

	public int getRange() {
		int r = (int)(8L+(power-MINPOWER)/FALLOFF);
		if (r > this.getMaxRange())
			return this.getMaxRange();
		return r;
	}

	public AxisAlignedBB getBurnZone(int meta, int step) {
		int minx = 0;
		int miny = 0;
		int minz = 0;
		int maxx = 0;
		int maxy = 0;
		int maxz = 0;

		switch (meta) {
		case 0:
			minx = xCoord-step;
			maxx = xCoord-1;
			miny = yCoord;
			maxy = yCoord;
			minz = zCoord;
			maxz = zCoord;
			break;
		case 1:
			minx = xCoord+1;
			maxx = xCoord+step;
			miny = yCoord;
			maxy = yCoord+1;
			minz = zCoord;
			maxz = zCoord+1;
			break;
		case 2:
			maxz = zCoord+step;
			minz = zCoord+1;
			miny = yCoord;
			maxy = yCoord+1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 3:
			maxz = zCoord-1;
			minz = zCoord-step;
			miny = yCoord;
			maxy = yCoord+1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 4:
			miny = yCoord;
			maxz = zCoord+1;
			miny = yCoord+1;
			maxy = yCoord+step;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 5:
			minz = zCoord;
			maxz = zCoord+1;
			miny = yCoord-1;
			maxy = yCoord-step-1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		}
		/*ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, miny, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, maxy, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, maxy, maxz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, miny, maxz, 20);*/
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);//.expand(0.25D, 0.25D, 0.25D);
	}

	public void ignite(World world, int x, int y, int z, int metadata, int step) {
		if (world.getBlockId(x+1, y, z) == 0)
			world.setBlock(x+1, y, z, Block.fire.blockID);
		if (world.getBlockId(x-1, y, z) == 0)
			world.setBlock(x-1, y, z, Block.fire.blockID);
		if (world.getBlockId(x, y+1, z) == 0)
			world.setBlock(x, y+1, z, Block.fire.blockID);
		if (world.getBlockId(x, y-1, z) == 0)
			world.setBlock(x, y-1, z, Block.fire.blockID);
		if (world.getBlockId(x, y, z+1) == 0)
			world.setBlock(x, y, z+1, Block.fire.blockID);
		if (world.getBlockId(x, y, z-1) == 0)
			world.setBlock(x, y, z-1, Block.fire.blockID);
	}

	public boolean makeBeam(World world, int x, int y, int z, int step, int id, int metadata, int maxdist) {
		boolean value = false;
		if (id == 0)
			return false;
		Block b = Block.blocksList[id];
		if (b.hasTileEntity(metadata)) {
			TileEntity te = world.getBlockTileEntity(x+step*xstep, y+step*ystep, z+step*zstep);
			if (te instanceof Laserable) {
				((Laserable)te).whenInBeam(world, x+step*xstep, y+step*ystep, z+step*zstep, power, step);
				if (((Laserable)te).blockBeam(world, x+step*xstep, y+step*ystep, z+step*zstep, power))
					return true;
			}
		}
		if (ConfigRegistry.ATTACKBLOCKS.getState()) {
			if (id == Block.stone.blockID || id == Block.cobblestone.blockID || id == Block.stoneBrick.blockID || id == Block.sandStone.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step * 2));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.lavaMoving.blockID);
				world.spawnParticle("lava", x+step*xstep+rand.nextFloat(), y+step*ystep+rand.nextFloat(), z+step*zstep+rand.nextFloat(), 0, 0, 0);
			}
			if (id == Block.sand.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step * 1));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.glass.blockID);
			}
			if (id == Block.gravel.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step * 1));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.cobblestone.blockID);
			}/*
    	if (id == Block.netherrack.blockID) {
    		if (world.getBlockId(x+step*xstep, 1+y+step*ystep, z+step*zstep) == 0) {
    			world.setBlock(x+step*xstep, 1+y+step*ystep, z+step*zstep, Block.fire.blockID);
    		}
    	}*/
			if (id == Block.netherrack.blockID && tickcount >= 6) {
				world.newExplosion(null, x+step*xstep+0.5, y+step*ystep+0.5, z+step*zstep+0.5, 3F, true, true);
				if (world.provider.dimensionId == -1 && step >= 500)
					RotaryAchievements.NETHERHEATRAY.triggerAchievement(this.getPlacer());
				step = maxdist;
				value = true;
			}
			if (id == Block.dirt.blockID || id == Block.tilledField.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step * 1));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.sand.blockID);
			}
			if (id == Block.grass.blockID || id == Block.mycelium.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step * 2));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.dirt.blockID);
			}
			if (id == Block.ice.blockID || id == Block.blockSnow.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step / 4));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0)
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, Block.waterMoving.blockID);
			}
			if (id == Block.tallGrass.blockID || id == Block.web.blockID || id == Block.plantYellow.blockID || id == Block.snow.blockID ||
					id == Block.plantRed.blockID || id == Block.mushroomRed.blockID || id == Block.mushroomBrown.blockID ||
					id == Block.deadBush.blockID || id == Block.crops.blockID || id == 142 || id == 141 || id == Block.vine.blockID ||
					id == Block.melonStem.blockID || id == Block.pumpkinStem.blockID || id == Block.waterlily.blockID) {
				int chance = (int)((power-MINPOWER)/(1024 * step / 8));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0) {
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, 0);
						if (id == Block.snow.blockID)
							world.playSoundEffect(x+step*xstep + 0.5D, y+step*ystep + 0.5D, z+step*zstep + 0.5D, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
					}
			}
			if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID) {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", id));
				int chance = (int)((power-MINPOWER)/(1024 * step / 8));
				chance = ReikaMathLibrary.extrema(chance, 1, "max");
				if (rand.nextInt(chance) != 0)
					if (rand.nextInt(step) == 0) {
						world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, 0);
						world.playSoundEffect(x+step*xstep + 0.5D, y+step*ystep + 0.5D, z+step*zstep + 0.5D, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
					}
			}
			if (id == Block.tnt.blockID) {
				world.setBlock(x+step*xstep, y+step*ystep, z+step*zstep, 0);
				EntityTNTPrimed var6 = new EntityTNTPrimed(world, x+step*xstep+0.5D, y+step*ystep+0.5D, z+step*zstep+0.5D, null);
				world.spawnEntityInWorld(var6);
				world.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
				world.spawnParticle("lava", x+step*xstep+rand.nextFloat(), y+step*ystep+rand.nextFloat(), z+step*zstep+rand.nextFloat(), 0, 0, 0);
			}
			if (b instanceof Laserable) {
				((Laserable) b).whenInBeam(world, x+step*xstep, y+step*ystep, z+step*zstep, power, step);
				if (((Laserable)b).blockBeam(world, x+step*xstep, y+step*ystep, z+step*zstep, power))
					return true;
			}

		}
		return value;
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.HEATRAY;
	}

	@Override
	public int getMaxRange() {
		return Math.max(64, ConfigRegistry.HEATRAYRANGE.getValue());
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
