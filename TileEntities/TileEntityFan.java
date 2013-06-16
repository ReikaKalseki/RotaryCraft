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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityBeamMachine;
import Reika.RotaryCraft.Models.ModelFan;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFan extends TileEntityBeamMachine implements RangedEffect {

	public int distancelimit = RotaryConfig.maxfanrange;

	public static final long MAXPOWER = 2097152;
	/** Rate of conversion - one power++ = 1/falloff ++ light levels */
	public static final int FALLOFF = 1024; //1kW a light level right now
	public static final double AXISSPEEDCAP = 1; //40 m/s
	public static final double BASESPEED = 0.000125;

	/** Minimum speeds required to rip up blocks */
	public static final int WEBSPEED = 256;
	public static final int LEAFSPEED = 4096;
	public static final int GRASSSPEED = 1024;
	public static final int FIRESPEED = 64;
	public static final int FIRESPREADSPEED = 16;
	public static final int HARVESTSPEED = 512;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		power = omega*torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, true);
		this.makeBeam(world, x, y, z, meta);
	}

	public void spreadFire(World world, int x, int y, int z, int meta, int range) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != -1 || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != -1) {
			int a = 0;
			if (meta > 1)
				a = 1;
			int b = 1-a;
			int editx;
			int edity;
			int editz;
			for (int i = 1; i <= range; i++) {
				editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = -1*a+x+i*xstep; edity = y+i*ystep; editz = -1*b+z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = -1*a+x+i*xstep; edity = 1+y+i*ystep; editz = -1*b+z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);

				editx = -1*a+x+i*xstep; edity = 2+y+i*ystep; editz = -1*b+z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = x+i*xstep; edity = 1+y+i*ystep; editz = z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);

				editx = x+i*xstep; edity = 2+y+i*ystep; editz = z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = 1*a+x+i*xstep; edity = y+i*ystep; editz = 1*b+z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
				editx = 1*a+x+i*xstep; edity = 2+y+i*ystep; editz = 1*b+z+i*zstep;
				if (par5Random.nextInt(60) == 0)
					ReikaWorldHelper.legacySetBlockWithNotify(world, editx, edity, editz, Block.fire.blockID);
			}
		}
	}

	public int getRange() {
		long power2 = ReikaMathLibrary.extrema((int)(power - MINPOWER), (int)MAXPOWER, "absmin");
		int range = 8+(int)(power2-MINPOWER)/FALLOFF;
		if (range > this.getMaxRange())
			range = this.getMaxRange();
		return range;
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {
		if (power < MINPOWER)
			return;
		long power2 = ReikaMathLibrary.extrema((int)(power - MINPOWER), (int)MAXPOWER, "absmin");
		int range = this.getRange();
		boolean blocked = false;
		for (int i = 1; i <= range && !blocked; i++) { //Limit range to line-of-sight
			if (Block.opaqueCubeLookup[world.getBlockId(x+i*xstep, y+i*ystep, z+i*zstep)]) {
				blocked = true;
				range = i;
			}
		}
		AxisAlignedBB zone = this.getBlowZone(meta, range);
		List inzone = world.getEntitiesWithinAABB(Entity.class, zone);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", inzone.size()));
		for (int k = 0; k < inzone.size(); k++) {
			Entity caught = (Entity)inzone.get(k);
			double mass = ReikaEntityHelper.getEntityMass(caught);
			double speedstep = ReikaMathLibrary.extremad((power2-MINPOWER)*BASESPEED/mass, AXISSPEEDCAP, "absmin");
			if (caught.motionX < AXISSPEEDCAP && xstep != 0) {
				double d = caught.posX-x;
				if (d == 0) //add a test to make sure do not reduce walk speed
					d = 1;
				double multiplier = Math.abs(1/(d-this.getMaxRange()));
				if (d-this.getMaxRange() > 12)
					multiplier = 0;
				if (multiplier > 1 || (Math.abs(caught.posX - xCoord) < this.getMaxRange()))
					multiplier = 1;
				//if (caught instanceof EntityPlayer)
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%f  %f", d, multiplier));
				double oldmotion = caught.motionX;
				if (Math.abs(d) < this.getMaxRange()) {
					if (Math.abs(d) >= this.getMaxRange()-8) {
						multiplier -= -0.125*(this.getMaxRange()-Math.abs(d));
					}
					caught.motionX = xstep*ReikaMathLibrary.extremad(Math.abs(caught.motionX) + multiplier*(power2-MINPOWER)*BASESPEED/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
					if (Math.abs(caught.motionX) < Math.abs(oldmotion))
						caught.motionX = oldmotion;
				}
			}
			if (caught.motionY < AXISSPEEDCAP && ystep != 0) {
				double d = caught.posY-y;
				if (d == 0)
					d = 1;
				double multiplier = 1/(d-this.getMaxRange());
				if (d-this.getMaxRange() > 12)
					multiplier = 0;
				if (multiplier > 1 || multiplier < 0)
					multiplier = 1;
				caught.motionY = ystep*ReikaMathLibrary.extremad(Math.abs(caught.motionY) + multiplier*(power2-MINPOWER)*BASESPEED/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
			}
			if (caught.motionZ < AXISSPEEDCAP && zstep != 0) {
				double d = caught.posZ-z;
				if (d == 0)
					d = 1;
				double multiplier = 1/(d-this.getMaxRange());
				if (d-this.getMaxRange() > 12)
					multiplier = 0;
				if (multiplier > 1 || multiplier < 0)
					multiplier = 1;
				caught.motionZ = zstep*ReikaMathLibrary.extremad(Math.abs(caught.motionZ) + multiplier*(power2-MINPOWER)*BASESPEED/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
			}/*
    		if (!world.isRemote)
    			caught.velocityChanged = true;*/
		}
		this.clearBlocks(world, x, y, z, meta, range);
		this.spreadFire(world, x, y, z, meta, range);
	}

	public void clearBlocks(World world, int x, int y, int z, int meta, int range) {
		int a = 0;
		if (meta > 1)
			a = 1;
		int b = 1-a;
		int editx;
		int edity;
		int editz;
		for (int i = 1; i <= range; i++) {
			editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = -1*a+x+i*xstep; edity = y+i*ystep; editz = -1*b+z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = -1*a+x+i*xstep; edity = 1+y+i*ystep; editz = -1*b+z+i*zstep;
			this.rip2(world, editx, edity, editz);

			editx = -1*a+x+i*xstep; edity = 2+y+i*ystep; editz = -1*b+z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = x+i*xstep; edity = 1+y+i*ystep; editz = z+i*zstep;
			this.rip2(world, editx, edity, editz);

			editx = x+i*xstep; edity = 2+y+i*ystep; editz = z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = 1*a+x+i*xstep; edity = y+i*ystep; editz = 1*b+z+i*zstep;
			this.rip2(world, editx, edity, editz);
			editx = 1*a+x+i*xstep; edity = 2+y+i*ystep; editz = 1*b+z+i*zstep;
			this.rip2(world, editx, edity, editz);
		}
	}

	public void rip2(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id != Block.web.blockID && id != Block.leaves.blockID && id != Block.tallGrass.blockID && id != Block.fire.blockID &&
				id != Block.crops.blockID && id != Block.potato.blockID && id != Block.carrot.blockID && id != RotaryCraft.canola.blockID && id != Block.netherStalk.blockID)
			return;
		if ((par5Random.nextInt(600) > 0 && id != Block.tallGrass.blockID) || (par5Random.nextInt(200) > 0 && id == Block.tallGrass.blockID))
			return;
		if (id == Block.web.blockID && omega < WEBSPEED)
			return;
		if (id == Block.leaves.blockID && omega < LEAFSPEED)
			return;
		if (id == Block.tallGrass.blockID && omega < GRASSSPEED)
			return;
		if (id == Block.fire.blockID && omega < FIRESPEED)
			return;
		if ((id == Block.crops.blockID || id == Block.potato.blockID || id == Block.carrot.blockID ||
				id == RotaryCraft.canola.blockID || id == Block.netherStalk.blockID) && omega < HARVESTSPEED)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", id, world.getBlockMetadata(x, y, z), x, y, z));
		if (id == Block.crops.blockID || id == Block.potato.blockID || id == Block.carrot.blockID || id == RotaryCraft.canola.blockID || id == Block.netherStalk.blockID) {
			this.harvest(world, x, y, z, this.getBlockMetadata(), id);
			return;
		}
		this.dropBlocks(world, x, y, z, id, world.getBlockMetadata(x, y, z));
		ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);
	}

	private void harvest(World world, int x, int y, int z, int meta, int id) {
		if (world.isRemote)
			return;
		int itemid = -1;
		int itemmeta = -1;
		int itemsize = -1;
		int itemid2 = -1;
		int itemmeta2 = -1;
		int itemsize2 = -1;

		if (id == Block.crops.blockID && world.getBlockMetadata(x, y, z) == 7) {
			itemid = Item.wheat.itemID;
			itemmeta = 0;
			itemsize = 1;
			itemid2 = Item.seeds.itemID;
			itemmeta2 = 0;
			itemsize2 = par5Random.nextInt(3);
		}
		if (id == Block.potato.blockID && world.getBlockMetadata(x, y, z) == 7) {
			itemid = Item.potato.itemID;
			itemmeta = 0;
			itemsize = 1+par5Random.nextInt(4);
			if (par5Random.nextInt(100) < 2) {	//2% chance of poison potato
				itemid2 = Item.poisonousPotato.itemID;
				itemmeta2 = 0;
				itemsize2 = 1;
			}
		}
		if (id == Block.netherStalk.blockID && world.getBlockMetadata(x, y, z) == 3) {
			itemid = Item.netherStalkSeeds.itemID;
			itemmeta = 0;
			itemsize = 2+par5Random.nextInt(3); // 2-4
		}
		if (id == Block.carrot.blockID && world.getBlockMetadata(x, y, z) == 7) {
			itemid = Item.carrot.itemID;
			itemmeta = 0;
			itemsize = 1+par5Random.nextInt(4);
		}
		if (id == RotaryCraft.canola.blockID && world.getBlockMetadata(x, y, z) == 9) {
			itemid = ItemRegistry.CANOLA.getID();
			itemmeta = 0;
			itemsize = 1+par5Random.nextInt(6)+par5Random.nextInt(3);
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", id, world.getBlockMetadata(x, y, z), x, y, z));
		if (itemid != 1 && itemmeta != -1 && itemsize != -1) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("Dropping Item 1: ItemStack(%d, %d, %d)", itemid, itemsize, itemmeta));
			EntityItem item1 = new EntityItem(world, x, y, z, new ItemStack(itemid, itemsize, itemmeta));
			item1.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(item1);
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 0);
		}
		if (itemid2 != 1 && itemmeta2 != -1 && itemsize2 != -1) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("Dropping Item 1: ItemStack(%d, %d, %d)", itemid2, itemsize2, itemmeta2));
			EntityItem item2 = new EntityItem(world, x, y, z, new ItemStack(itemid2, itemsize2, itemmeta2));
			item2.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(item2);
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 0);
		}
	}

	public void dropBlocks(World world, int x, int y, int z, int id, int meta) {
		if (id == Block.fire.blockID)
			return;
		int iddrop = 0;
		int metadrop = 0;
		boolean dropchance = false;
		boolean dropapple = false;
		if (id == Block.web.blockID) {
			iddrop = Item.silk.itemID;
			metadrop = 0;
			dropchance = true;
		}
		if (id == Block.leaves.blockID) {
			iddrop = Block.sapling.blockID;
			metadrop = meta;
			dropchance = false;
			if (par5Random.nextInt(20) == 0 && meta != 3) //Most saplings have 5% drop rate
				dropchance = true;
			if (par5Random.nextInt(40) == 0 && meta == 3) //Jungle saplings have 2.5% chance
				dropchance = true;
			if (meta == 0 && par5Random.nextInt(200) == 0) //0.5% chance of apple from oak
				dropapple = true;
		}
		if (id == Block.tallGrass.blockID) {
			iddrop = Item.seeds.itemID;
			metadrop = 0;
			dropchance = false;
			if (par5Random.nextInt(2) == 0) //50% drop rate
				dropchance = true;
		}
		if (dropapple) {
			ItemStack apple = new ItemStack(Item.appleRed.itemID, 1, 0);
			EntityItem itemdrop = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D, apple);
			itemdrop.motionX = -0.1+0.2*par5Random.nextFloat();
			itemdrop.motionY = 0.2*par5Random.nextFloat();
			itemdrop.motionZ = -0.1+0.2*par5Random.nextFloat();
			itemdrop.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(itemdrop);
		}
		if (!dropchance)
			return;
		ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);
		ItemStack todrop = new ItemStack(iddrop, 1, metadrop);
		EntityItem itemdrop = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D, todrop);
		itemdrop.motionX = -0.1+0.2*par5Random.nextFloat();
		itemdrop.motionY = 0.2*par5Random.nextFloat();
		itemdrop.motionZ = -0.1+0.2*par5Random.nextFloat();
		itemdrop.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(itemdrop);

	}

	public AxisAlignedBB getBlowZone(int meta, int step) {
		int minx = 0;
		int miny = 0;
		int minz = 0;
		int maxx = 0;
		int maxy = 0;
		int maxz = 0;

		switch (meta) {
		case 0:
			minx = xCoord-step-1;
			maxx = xCoord;
			miny = yCoord;
			maxy = yCoord+1;
			minz = zCoord;
			maxz = zCoord+1;
			break;
		case 1:
			minx = xCoord+1;
			maxx = xCoord+step+1;
			miny = yCoord;
			maxy = yCoord+1;
			minz = zCoord;
			maxz = zCoord+1;
			break;
		case 2:
			maxz = zCoord+step+1;
			minz = zCoord+1;
			miny = yCoord;
			maxy = yCoord+1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 3:
			maxz = zCoord;
			minz = zCoord-step-1;
			miny = yCoord;
			maxy = yCoord+1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 4:
			minz = zCoord;
			maxz = zCoord+1;
			miny = yCoord+1;
			maxy = yCoord+step+1;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		case 5:
			minz = zCoord;
			maxz = zCoord+1;
			maxy = yCoord;
			miny = yCoord-step;
			minx = xCoord;
			maxx = xCoord+1;
			break;
		}
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz).expand(0.0, 0.0, 0.0);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelFan();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		phi += 3*ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FAN.ordinal();
	}

	@Override
	public int getMaxRange() {
		return 32;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
