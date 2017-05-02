/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.Registry.CropType.CropMethods;
import Reika.DragonAPI.Interfaces.Registry.ModCrop;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.API.Event.FanHarvestEvent;
import Reika.RotaryCraft.API.Interfaces.CustomFanEntity;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;

public class TileEntityFan extends TileEntityBeamMachine implements RangedEffect, UpgradeableMachine, BreakAction {

	public int distancelimit = Math.max(32, ConfigRegistry.FANRANGE.getValue());

	public static final long MAXPOWER = 2097152;
	public static final int FALLOFF = 1024;
	public static final int FALLOFF_WIDE = 2048;
	public static final double AXISSPEEDCAP = 1; //40 m/s
	public static final double BASESPEED = 0.000125;

	/** Minimum speeds required to rip up blocks */
	public static final int WEBSPEED = 256;
	public static final int LEAFSPEED = 4096;
	public static final int GRASSSPEED = 1024;
	public static final int FIRESPEED = 64;
	public static final int FIRESPREADSPEED = 16;
	public static final int HARVESTSPEED = 512;

	private final StepTimer sound = new StepTimer(27);

	public boolean wideAreaHarvest = true;
	public boolean wideAreaBlow = false;

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

	private void spreadFire(World world, int x, int y, int z, int meta, int range) {
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null) {
			int a = 0;
			if (meta > 1)
				a = 1;
			int b = 1-a;
			int editx;
			int edity;
			int editz;
			for (int i = 1; i <= range; i++) {
				editx = x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = -1*a+x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = -1*a+x+i*facing.offsetX; edity = 1+y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);

				editx = -1*a+x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = x+i*facing.offsetX; edity = 1+y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);

				editx = x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = 1*a+x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = 1*b+z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
				editx = 1*a+x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = 1*b+z+i*facing.offsetZ;
				if (rand.nextInt(60) == 0 && ReikaWorldHelper.softBlocks(world, editx, edity, editz))
					world.setBlock(editx, edity, editz, Blocks.fire);
			}
		}
	}

	public int getRange() {
		if (power < MINPOWER)
			return 0;
		long power2 = Math.min(power - MINPOWER, MAXPOWER);
		int range = 8+(int)(power2-MINPOWER)/(wideAreaBlow ? FALLOFF_WIDE : FALLOFF);
		if (range > this.getMaxRange())
			range = this.getMaxRange();
		return range;
	}

	private boolean isStoppedBy(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		if (b == Blocks.air || b.isAir(world, x, y, z))
			return false;
		int meta = world.getBlockMetadata(x, y, z);
		if (ReikaCropHelper.isCrop(b) || ModCropList.isModCrop(b, meta))
			return false;
		if (b.isOpaqueCube() || b.renderAsNormalBlock())
			return true;
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.LAWNSPRINKLER || m == MachineRegistry.SPRINKLER)
			return false;
		if (b.getMaterial().isSolid())
			return true;
		return false;
	}

	@Override
	protected void makeBeam(World world, int x, int y, int z, int meta) {
		if (power < MINPOWER)
			return;
		long power2 = Math.min(power, MAXPOWER);
		int range = this.getRange();
		boolean blocked = false;
		for (int i = 1; i <= range && !blocked; i++) { //Limit range to line-of-sight
			if (this.isStoppedBy(world, x+i*facing.offsetX, y+i*facing.offsetY, z+i*facing.offsetZ)) {
				blocked = true;
				range = i;
			}
		}
		AxisAlignedBB zone = wideAreaBlow ? this.getWideBlowZone(meta, range) : this.getBlowZone(meta, range);
		List<Entity> inzone = world.getEntitiesWithinAABB(Entity.class, zone);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", inzone.size()));
		for (Entity caught : inzone) {
			if (this.canBlowEntity(caught)) {
				double mass = ReikaEntityHelper.getEntityMass(caught);
				if (caught instanceof EntityLivingBase)
					mass += ReikaEntityHelper.getCarriedMass((EntityLivingBase)caught);
				if (caught.motionX < AXISSPEEDCAP && facing.offsetX != 0) {
					double d = caught.posX-x;
					if (d == 0)
						d = 1;
					double multiplier = 1/(d-this.getMaxRange());
					if (d-this.getMaxRange() > 12)
						multiplier = 0;
					if (multiplier > 1 || multiplier < 0)
						multiplier = 1;
					double base = multiplier*power2*BASESPEED*(wideAreaBlow ? 0.125 : 1);
					double speedstep = ReikaMathLibrary.extremad(Math.abs(caught.motionX) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
					double a = facing.offsetX > 0 ? 0.004 : 0;
					caught.motionX = facing.offsetX*speedstep+a;
				}
				if (caught.motionY < AXISSPEEDCAP && facing.offsetY != 0) {
					double d = caught.posY-y;
					if (d == 0)
						d = 1;
					double multiplier = 1/(d-this.getMaxRange());
					if (d-this.getMaxRange() > 12)
						multiplier = 0;
					if (multiplier > 1 || multiplier < 0)
						multiplier = 1;
					double base = multiplier*power2*BASESPEED*(wideAreaBlow ? 0.125 : 1);
					caught.motionY = facing.offsetY*ReikaMathLibrary.extremad(Math.abs(caught.motionY) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
				}
				if (caught.motionZ < AXISSPEEDCAP && facing.offsetZ != 0) {
					double d = caught.posZ-z;
					if (d == 0)
						d = 1;
					double multiplier = 1/(d-this.getMaxRange());
					if (d-this.getMaxRange() > 12)
						multiplier = 0;
					if (multiplier > 1 || multiplier < 0)
						multiplier = 1;
					double base = multiplier*power2*BASESPEED*(wideAreaBlow ? 0.125 : 1);
					double speedstep = ReikaMathLibrary.extremad(Math.abs(caught.motionZ) + base/(mass*Math.abs(d)), AXISSPEEDCAP, "absmin");
					double a = facing.offsetZ > 0 ? 0.004 : 0;
					caught.motionZ = facing.offsetZ*speedstep+a;
				}
			}
		}
		this.clearBlocks(world, x, y, z, meta, range);
		this.spreadFire(world, x, y, z, meta, range);
	}

	private boolean canBlowEntity(Entity e) {
		if (e instanceof CustomFanEntity) {
			CustomFanEntity c = (CustomFanEntity)e;
			if (c.getBlowPower() > power)
				return false;
			double ang = ReikaMathLibrary.py3d(Math.signum(e.motionX)-facing.offsetX, Math.signum(e.motionY)-facing.offsetY, Math.signum(e.motionZ)-facing.offsetZ);
			if (ang > c.getMaxDeflection())
				return false;
			return true;
		}
		return true;
	}

	private void clearBlocks(World world, int x, int y, int z, int meta, int range) {
		int a = 0;
		if (meta > 1)
			a = 1;
		int b = 1-a;
		int editx;
		int edity;
		int editz;
		for (int i = 1; i <= range; i++) {
			editx = x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = z+i*facing.offsetZ;
			this.rip2(world, editx, edity, editz);
			this.enhanceFinPower(world, editx, edity, editz);

			if (wideAreaHarvest) {
				editx = -1*a+x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
				editx = -1*a+x+i*facing.offsetX; edity = 1+y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);

				editx = -1*a+x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = -1*b+z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
				editx = x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
				editx = x+i*facing.offsetX; edity = 1+y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);

				editx = x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
				editx = 1*a+x+i*facing.offsetX; edity = y+i*facing.offsetY; editz = 1*b+z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
				editx = 1*a+x+i*facing.offsetX; edity = 2+y+i*facing.offsetY; editz = 1*b+z+i*facing.offsetZ;
				this.rip2(world, editx, edity, editz);
			}
		}
	}

	private void enhanceFinPower(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.COOLINGFIN) {
			TileEntityCoolingFin te = (TileEntityCoolingFin)world.getTileEntity(x, y, z);
			int[] tg = te.getTarget();
			TileEntity te2 = world.getTileEntity(tg[0], tg[1], tg[2]);
			if (te2 instanceof TemperatureTE && world.getTotalWorldTime()%20 == 0) {
				int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
				if (((TemperatureTE) te2).getTemperature() > Tamb)
					((TemperatureTE) te2).addTemperature(-1);
			}
		}
	}

	public void rip2(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		/*
		if (id instanceof BlowableCrop && omega >= HARVESTSPEED) {
			float sp = ((BlowableCrop)id).getHarvestingSpeed();
			if (ReikaRandomHelper.doWithChance(0.015*sp))
				this.harvest(world, x, y, z, (BlowableCrop)id);
			return;
		}
		 */
		//ReikaJavaLibrary.pConsole(id+":"+ModCropList.getModCrop(id, meta), id != Blocks.air);
		boolean crop = ReikaCropHelper.isCrop(id) || ModCropList.isModCrop(id, meta);
		if (id != Blocks.snow && id != Blocks.web && id != Blocks.leaves && id != Blocks.leaves2 && id != Blocks.tallgrass &&
				id != Blocks.fire && !crop)
			return;
		if ((rand.nextInt(600) > 0 && id != Blocks.tallgrass) || (rand.nextInt(200) > 0 && id == Blocks.tallgrass))
			return;
		if (id == Blocks.web && omega < WEBSPEED)
			return;
		if ((id == Blocks.leaves || id == Blocks.leaves2) && omega < LEAFSPEED)
			return;
		if (id == Blocks.tallgrass && omega < GRASSSPEED)
			return;
		if (id == Blocks.fire && omega < FIRESPEED)
			return;
		if (id == Blocks.snow && omega < FIRESPEED)
			return;
		if (crop && omega < HARVESTSPEED)
			return;
		if (crop) {
			this.harvest(world, x, y, z, meta, id);
			return;
		}
		this.dropBlocks(world, x, y, z, id, meta);
		world.setBlockToAir(x, y, z);
	}
	/*
	private void harvest(World world, int x, int y, int z, BlowableCrop b) {
		if (b.isReadyToHarvest(world, x, y, z)) {
			ArrayList<ItemStack> li = b.getHarvestProducts(world, x, y, z);
			if (li != null)
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
			b.setPostHarvest(world, x, y, z);
			MinecraftForge.EVENT_BUS.post(new FanHarvestEvent(this, x, y, z));
		}
	}
	 */
	//Meta is block meta, not fan meta
	private void harvest(World world, int x, int y, int z, int meta, Block id) {
		ModCrop mod = ModCropList.getModCrop(id, meta);
		ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
		if (mod != null && mod.isRipe(world, x, y, z)) {
			if (mod.destroyOnHarvest()) {
				ArrayList<ItemStack> li = id.getDrops(world, x, y, z, meta, 0);
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
				world.setBlockToAir(x, y, z);
			}
			else {
				ArrayList<ItemStack> li = mod.getDrops(world, x, y, z, 0);
				CropMethods.removeOneSeed(mod, li);
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
				mod.setHarvested(world, x, y, z);
			}
		}
		if (crop != null && crop.isRipe(meta)) {
			ArrayList<ItemStack> li = crop.getDrops(world, x, y, z, 0);
			CropMethods.removeOneSeed(crop, li);
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
			crop.setHarvested(world, x, y, z);
		}
		MinecraftForge.EVENT_BUS.post(new FanHarvestEvent(this, x, y, z));
	}

	private void dropBlocks(World world, int x, int y, int z, Block id, int meta) {
		if (id != Blocks.air)
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, id.getDrops(world, x, y, z, meta, 0));
		world.setBlockToAir(x, y, z);
	}

	public AxisAlignedBB getBlowZone(int meta, int range) {
		return ReikaAABBHelper.getBeamBox(xCoord, yCoord, zCoord, facing, range);//.expand(0.0, 0.0, 0.0);
	}

	public AxisAlignedBB getWideBlowZone(int meta, int range) {
		AxisAlignedBB box = this.getBlowZone(meta, range);
		int ex = ReikaMathLibrary.isValueInsideBoundsIncl(0, 1, meta) ? 0 : 1;
		int ey = ReikaMathLibrary.isValueInsideBoundsIncl(4, 5, meta) ? 0 : 1;
		int ez = ReikaMathLibrary.isValueInsideBoundsIncl(2, 3, meta) ? 0 : 1;
		return box.expand(ex, ey, ez);
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

	@Override
	public void upgrade(ItemStack item) {
		if (!wideAreaBlow && ReikaItemHelper.matchStacks(item, ItemStacks.diffuser)) {
			wideAreaBlow = true;
		}
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return ReikaItemHelper.matchStacks(item, ItemStacks.diffuser);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("wideh", wideAreaHarvest);
		NBT.setBoolean("wideb", wideAreaBlow);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		wideAreaBlow = NBT.getBoolean("wideb");
		wideAreaHarvest = NBT.getBoolean("wideh");
	}

	@Override
	public void breakBlock() {
		if (wideAreaBlow) {
			ReikaItemHelper.dropItem(worldObj, xCoord, yCoord, zCoord, ItemStacks.diffuser);
		}
	}
}
