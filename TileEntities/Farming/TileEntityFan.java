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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.API.BlowableCrop;
import Reika.RotaryCraft.API.Event.FanHarvestEvent;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;

public class TileEntityFan extends TileEntityBeamMachine implements RangedEffect {

	public int distancelimit = Math.max(32, ConfigRegistry.FANRANGE.getValue());

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

	private StepTimer sound = new StepTimer(27);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		power = (long)omega*(long)torque;
		this.makeBeam(world, x, y, z, meta);
		sound.update();
		if (omega > 0) {
			if (sound.checkCap())
				SoundRegistry.FAN.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.05F : 0.5F, 1F);
		}
	}

	public void spreadFire(World world, int x, int y, int z, int meta, int range) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null) {
			int a = 0;
			if (meta > 1)
				a = 1;
			int b = 1-a;
			int editx;
			int edity;
			int editz;
			for (int i = 1; i <= range; i++) {
				editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = -1*a+x+i*xstep; edity = y+i*ystep; editz = -1*b+z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = -1*a+x+i*xstep; edity = 1+y+i*ystep; editz = -1*b+z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);

				editx = -1*a+x+i*xstep; edity = 2+y+i*ystep; editz = -1*b+z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = x+i*xstep; edity = y+i*ystep; editz = z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = x+i*xstep; edity = 1+y+i*ystep; editz = z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);

				editx = x+i*xstep; edity = 2+y+i*ystep; editz = z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = 1*a+x+i*xstep; edity = y+i*ystep; editz = 1*b+z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
				editx = 1*a+x+i*xstep; edity = 2+y+i*ystep; editz = 1*b+z+i*zstep;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Block.fire.blockID);
			}
		}
	}

	public int getRange() {
		if (power < MINPOWER)
			return 0;
		long power2 = Math.min(power - MINPOWER, MAXPOWER);
		int range = 8+(int)(power2-MINPOWER)/FALLOFF;
		if (range > this.getMaxRange())
			range = this.getMaxRange();
		return range;
	}

	private boolean isStoppedBy(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id == 0)
			return false;
		if (Block.opaqueCubeLookup[id]) {
			return true;
		}
		Block b = Block.blocksList[id];
		if (b.isOpaqueCube() || b.renderAsNormalBlock())
			return true;
		if (b.blockMaterial.isSolid())
			return true;
		return false;
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {
		if (power < MINPOWER)
			return;
		long power2 = Math.min(power, MAXPOWER);
		int range = this.getRange();
		boolean blocked = false;
		for (int i = 1; i <= range && !blocked; i++) { //Limit range to line-of-sight
			if (this.isStoppedBy(world, x+i*xstep, y+i*ystep, z+i*zstep)) {
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
			if (caught.motionX < AXISSPEEDCAP && xstep != 0) {
				double d = caught.posX-x;
				if (d == 0)
					d = 1;
				double multiplier = 1/(d-this.getMaxRange());
				if (d-this.getMaxRange() > 12)
					multiplier = 0;
				if (multiplier > 1 || multiplier < 0)
					multiplier = 1;
				double base = power2*BASESPEED;
				double speedstep = ReikaMathLibrary.extremad(Math.abs(caught.motionX) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
				double a = xstep > 0 ? 0.004 : 0;
				caught.motionX = xstep*speedstep+a;
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
				double base = multiplier*power2*BASESPEED;
				caught.motionY = ystep*ReikaMathLibrary.extremad(Math.abs(caught.motionY) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
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
				double base = multiplier*power2*BASESPEED;
				double speedstep = ReikaMathLibrary.extremad(Math.abs(caught.motionZ) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
				double a = zstep > 0 ? 0.004 : 0;
				caught.motionZ = zstep*speedstep+a;
			}
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
			this.enhanceFinPower(world, editx, edity, editz);
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

	private void enhanceFinPower(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.COOLINGFIN) {
			TileEntityCoolingFin te = (TileEntityCoolingFin)world.getBlockTileEntity(x, y, z);
			int[] tg = te.getTarget();
			TileEntity te2 = world.getBlockTileEntity(tg[0], tg[1], tg[2]);
			if (te2 instanceof TemperatureTE && world.getTotalWorldTime()%20 == 0) {
				int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
				if (((TemperatureTE) te2).getTemperature() > Tamb)
					((TemperatureTE) te2).addTemperature(-1);
			}
		}
	}

	public void rip2(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		Block b = Block.blocksList[id];
		if (b instanceof BlowableCrop && omega >= HARVESTSPEED) {
			float sp = ((BlowableCrop)b).getHarvestingSpeed();
			if (ReikaRandomHelper.doWithChance(0.015*sp))
				this.harvest(world, x, y, z, (BlowableCrop)b);
			return;
		}
		boolean crop = ReikaCropHelper.isCrop(id) || ModCropList.isModCrop(id, meta);
		if (id != Block.snow.blockID && id != Block.web.blockID && id != Block.leaves.blockID && id != Block.tallGrass.blockID &&
				id != Block.fire.blockID && !crop)
			return;
		if ((rand.nextInt(600) > 0 && id != Block.tallGrass.blockID) || (rand.nextInt(200) > 0 && id == Block.tallGrass.blockID))
			return;
		if (id == Block.web.blockID && omega < WEBSPEED)
			return;
		if (id == Block.leaves.blockID && omega < LEAFSPEED)
			return;
		if (id == Block.tallGrass.blockID && omega < GRASSSPEED)
			return;
		if (id == Block.fire.blockID && omega < FIRESPEED)
			return;
		if (id == Block.snow.blockID && omega < FIRESPEED)
			return;
		if (crop && omega < HARVESTSPEED)
			return;
		if (crop) {
			this.harvest(world, x, y, z, meta, id);
			return;
		}
		this.dropBlocks(world, x, y, z, id, meta);
		world.setBlock(x, y, z, 0);
	}

	private void harvest(World world, int x, int y, int z, BlowableCrop b) {
		if (b.isReadyToHarvest(world, x, y, z)) {
			ArrayList<ItemStack> li = b.getHarvestProducts(world, x, y, z);
			if (li != null)
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
			b.setPostHarvest(world, x, y, z);
			MinecraftForge.EVENT_BUS.post(new FanHarvestEvent(this, x, y, z));
		}
	}

	private void harvest(World world, int x, int y, int z, int meta, int id) {
		ModCropList mod = ModCropList.getModCrop(id, meta);
		ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
		int metato = 0;
		if (mod != null && mod.isRipe(world, x, y, z)) {
			if (mod.destroyOnHarvest()) {
				Block b = Block.blocksList[id];
				ArrayList<ItemStack> li = b.getBlockDropped(world, x, y, z, meta, 0);
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
				world.setBlock(x, y, z, 0);
			}
			else {
				ArrayList<ItemStack> li = mod.getDrops(world, x, y, z, 0);
				mod.removeOneSeed(li);
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
				metato = mod.getHarvestedMetadata(world, x, y, z);
				if (mod.isTileEntity())
					mod.runTEHarvestCode(world, x, y, z);
				else
					world.setBlockMetadataWithNotify(x, y, z, metato, 3);
			}
		}
		if (crop != null && crop.isRipe(meta)) {
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, crop.getDrops(world, x, y, z, 0));
			metato = crop.getHarvestedMeta(meta);
			world.setBlockMetadataWithNotify(x, y, z, metato, 3);
		}
		MinecraftForge.EVENT_BUS.post(new FanHarvestEvent(this, x, y, z));
	}

	public void dropBlocks(World world, int x, int y, int z, int id, int meta) {
		if (id != 0)
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, Block.blocksList[id].getBlockDropped(world, x, y, z, meta, 0));
		world.setBlock(x, y, z, 0);
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
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		phi += 3*ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FAN;
	}

	@Override
	public int getMaxRange() {
		return distancelimit;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void onEMP() {}
}
