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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityBeamMachine;
import Reika.RotaryCraft.Models.ModelHRay;

public class TileEntityHeatRay extends TileEntityBeamMachine implements RangedEffect {

	/** Rate of conversion - one power++ = 1/falloff ++ blocks range */
	public static final int FALLOFF = 1024; //1kW per block right now

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		power = omega*torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		this.scorch(world, x, y, z, meta);
	}

	public void scorch(World world, int x, int y, int z, int metadata) {
		boolean blocked = false;
		int step;
		if (power > MINPOWER) { //524+ kW (real military laser)
			int maxdist = this.getRange();
			for (step = 1; step < maxdist && (step < this.getMaxRange() || this.getMaxRange() == -1) && !blocked; step++) {
				int id = world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep);
				if (ReikaWorldHelper.flammable(id) || id == mod_RotaryCraft.gravleaves.blockID || id == mod_RotaryCraft.gravlog.blockID)
					this.ignite(world, x+step*xstep, y+step*ystep, z+step*zstep, metadata, step);
				if (this.makeBeam(world, x, y, z, metadata, step, world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep), maxdist)) {
					blocked = true;
					tickcount = 0;
				}
				if (Block.opaqueCubeLookup[world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep)])
					blocked = true; //break loop
				/*if (world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep) == 0)
	    			ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, 4);*/
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d ", step, world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep))+String.valueOf(blocked));
				world.markBlockForUpdate(x+xstep*step, y+ystep*step, z+zstep*step);
			}
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d ", step, world.getBlockId(x+step*xstep, y+step*ystep, z+step*zstep))+String.valueOf(blocked));
			AxisAlignedBB zone = this.getBurnZone(metadata, step);
			List inzone = worldObj.getEntitiesWithinAABB(Entity.class, zone);
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", inzone.size()));
			for (int i = 0; i < inzone.size(); i++) {
				if (inzone.get(i) instanceof Entity) {
					Entity caught = (Entity)inzone.get(i);
					if (!(caught instanceof EntityItem)) //Do not burn drops
						caught.setFire(2+(int)(4*power/MINPOWER));	// 1 Hearts worth of fire at min power, +1 heart for every 65kW extra
					if (caught instanceof EntityTNTPrimed)
						world.spawnParticle("lava", caught.posX+par5Random.nextFloat(), caught.posY+par5Random.nextFloat(), caught.posZ+par5Random.nextFloat(), 0, 0, 0);
				}
			}
		}
	}

	public int getRange() {
		return (8+(int)(power-MINPOWER)/FALLOFF);
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
			minx = xCoord-step-1;
			maxx = xCoord-1;
			miny = yCoord;
			maxy = yCoord;
			minz = zCoord;
			maxz = zCoord;
			break;
		case 1:
			minx = xCoord+1;
			maxx = xCoord+step+1;
			miny = yCoord;
			maxy = yCoord;
			minz = zCoord;
			maxz = zCoord;
			break;
		case 2:
			maxz = zCoord+step+1;
			minz = zCoord+1;
			miny = yCoord;
			maxy = yCoord;
			minx = xCoord;
			maxx = xCoord;
			break;
		case 3:
			maxz = zCoord-1;
			minz = zCoord-step-1;
			miny = yCoord;
			maxy = yCoord;
			minx = xCoord;
			maxx = xCoord;
			break;
		case 4:
			miny = yCoord;
			maxz = zCoord;
			miny = yCoord+1;
			maxy = yCoord+step+1;
			minx = xCoord;
			maxx = xCoord;
			break;
		case 5:
			minz = zCoord;
			maxz = zCoord;
			miny = yCoord-1;
			maxy = yCoord-step-1;
			minx = xCoord;
			maxx = xCoord;
			break;
		}
		/*ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, miny, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, maxy, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, maxy, maxz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, miny, maxz, 20);*/
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz).expand(0.5D, 0.5D, 0.5D);
	}

	public void ignite(World world, int x, int y, int z, int metadata, int step) {
		if (world.getBlockId(x+1, y, z) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x+1, y, z, Block.fire.blockID);
		if (world.getBlockId(x-1, y, z) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x-1, y, z, Block.fire.blockID);
		if (world.getBlockId(x, y+1, z) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y+1, z, Block.fire.blockID);
		if (world.getBlockId(x, y-1, z) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y-1, z, Block.fire.blockID);
		if (world.getBlockId(x, y, z+1) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z+1, Block.fire.blockID);
		if (world.getBlockId(x, y, z-1) == 0)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z-1, Block.fire.blockID);
	}

	public boolean makeBeam(World world, int x, int y, int z, int metadata, int step, int id, int maxdist) {
		boolean value = false;
		if (id == Block.stone.blockID || id == Block.cobblestone.blockID || id == Block.stoneBrick.blockID || id == Block.sandStone.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 32));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.lavaMoving.blockID);
			world.spawnParticle("lava", x+step*xstep+par5Random.nextFloat(), y+step*ystep+par5Random.nextFloat(), z+step*zstep+par5Random.nextFloat(), 0, 0, 0);
		}
		if (id == Block.sand.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 16));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.glass.blockID);
		}
		if (id == Block.gravel.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 16));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.cobblestone.blockID);
		}/*
    	if (id == Block.netherrack.blockID) {
    		if (world.getBlockId(x+step*xstep, 1+y+step*ystep, z+step*zstep) == 0) {
    			ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, 1+y+step*ystep, z+step*zstep, Block.fire.blockID);
    		}
    	}*/
		if (id == Block.netherrack.blockID && tickcount >= 6) {
			world.newExplosion(null, x+step*xstep+0.5, y+step*ystep+0.5, z+step*zstep+0.5, 3F, true, true);
			step = maxdist;
			value = true;
		}
		if (id == Block.dirt.blockID || id == Block.tilledField.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 16));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.sand.blockID);
		}
		if (id == Block.grass.blockID || id == Block.mycelium.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 16));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.dirt.blockID);
		}
		if (id == Block.ice.blockID || id == Block.blockSnow.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 4));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.waterMoving.blockID);
		}
		if (id == Block.tallGrass.blockID || id == Block.web.blockID || id == Block.plantYellow.blockID || id == Block.snow.blockID ||
				id == Block.plantRed.blockID || id == Block.mushroomRed.blockID || id == Block.mushroomBrown.blockID ||
				id == Block.deadBush.blockID || id == Block.crops.blockID || id == 142 || id == 141 || id == Block.vine.blockID ||
				id == Block.melonStem.blockID || id == Block.pumpkinStem.blockID || id == Block.waterlily.blockID) {
			int chance = (int)((power-MINPOWER)/(1024 * step * 2));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0) {
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, 0);
					if (id == Block.snow.blockID)
						world.playSoundEffect(x+step*xstep + 0.5D, y+step*ystep + 0.5D, z+step*zstep + 0.5D, "random.fizz", 0.5F, 2.6F + (par5Random.nextFloat() - par5Random.nextFloat()) * 0.8F);
				}
		}
		if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", id));
			int chance = (int)((power-MINPOWER)/(1024 * step * 2));
			chance = ReikaMathLibrary.extrema(chance, 1, "max");
			if (par5Random.nextInt(chance) != 0)
				if (par5Random.nextInt(step) == 0) {
					ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, 0);
					world.playSoundEffect(x+step*xstep + 0.5D, y+step*ystep + 0.5D, z+step*zstep + 0.5D, "random.fizz", 0.5F, 2.6F + (par5Random.nextFloat() - par5Random.nextFloat()) * 0.8F);
				}
		}
		if (id == Block.tnt.blockID) {
			ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, 0);
			EntityTNTPrimed var6 = new EntityTNTPrimed(world, x+step*xstep+0.5D, y+step*ystep+0.5D, z+step*zstep+0.5D, null);
			world.spawnEntityInWorld(var6);
			world.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
			world.spawnParticle("lava", x+step*xstep+par5Random.nextFloat(), y+step*ystep+par5Random.nextFloat(), z+step*zstep+par5Random.nextFloat(), 0, 0, 0);
		}/*
    	if (id == 0) {
    		if (world.getBlockId(x+step*xstep, -1+y+step*ystep, z+step*zstep) == Block.netherrack.blockID) {
    			ReikaWorldHelper.legacySetBlockWithNotify(world, x+step*xstep, y+step*ystep, z+step*zstep, Block.fire.blockID);
    		}
    	}*/
		return value;
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelHRay();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HEATRAY.ordinal();
	}

	@Override
	public int getMaxRange() {
		return RotaryConfig.maxheatrayrange;
	}
}
