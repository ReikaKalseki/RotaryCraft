/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Interfaces.TileEntity.AdjacentUpdateWatcher;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.VDGAttackEvent;
import Reika.RotaryCraft.API.Interfaces.Shockable;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Entities.EntityDischarge;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityVanDeGraff extends TileEntityPowerReceiver implements RangedEffect, AdjacentUpdateWatcher {

	private final WeightedRandom<ForgeDirection> sideMap = new WeightedRandom();

	//In coloumbs
	private int charge;

	private void updateSidedMappings(World world, int x, int y, int z) {
		sideMap.clear();
		for (int i = 1; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			Block b = world.getBlock(dx, dy, dz);
			int metadata = world.getBlockMetadata(dx, dy, dz);
			if (b.isAir(world, dx, dy, dz)) {
				sideMap.addEntry(dir, 0);
			}
			else {
				Material mat = b.getMaterial();
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.VANDEGRAFF) {
					sideMap.addEntry(dir, 0);
				}
				else {
					TileEntity te = this.getAdjacentTileEntity(dir);
					if (te instanceof Shockable) {
						sideMap.addEntry(dir, 1000);
					}
					else if (mat == Material.iron || mat == Material.anvil) {
						sideMap.addEntry(dir, 50);
					}
					else if (mat == Material.water) {
						sideMap.addEntry(dir, 20);
					}
					else if (b == Blocks.tnt) {
						sideMap.addEntry(dir, 100);
					}
				}
			}
		}
		//ReikaJavaLibrary.pConsole(sideMap, Side.SERVER);
		sideMap.addEntry(ForgeDirection.UNKNOWN, 1);
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		this.updateSidedMappings(world, x, y, z);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		charge += 4*Math.sqrt(power);

		int r = this.getRange();

		if (r > 0) {
			ForgeDirection dir = sideMap.getRandomEntry();
			//ReikaJavaLibrary.pConsole(dir+": "+sideMap, Side.SERVER);
			if (dir != null && dir != ForgeDirection.UNKNOWN) {
				this.shock(world, x, y, z, dir);
				return;
			}

			for (int i = 2; i < 4; i++) {
				TileEntity te = this.getTileEntity(x, y+i, z);
				if (te instanceof Shockable && ((Shockable)te).canDischargeLongRange()) {
					this.dischargeToBlock(x, y+i, z, (Shockable)te);
					return;
				}
			}
		}
		if (charge <= 0)
			return;

		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(r, r, r);
		EntityLivingBase e = ReikaWorldHelper.getClosestLivingEntityNoPlayers(world, x+0.5, y+0.75, z+0.5, box, true);
		if (e != null) {
			EntityDischarge d = new EntityDischarge(world, x+0.5, y+0.75, z+0.5, charge, e.posX, e.posY+e.getEyeHeight()*0.8, e.posZ);
			if (!world.isRemote) {
				this.shock(e);
				world.spawnEntityInWorld(d);
			}
			charge = 0;
		}
		if (charge > 2097152 && !world.isRemote) {
			this.detonate(world, x, y, z);
		}

		if (world.isRaining() && world.canLightningStrikeAt(x, y+1, z)) {
			charge *= 0.5;
		}
	}

	private void shock(World world, int x, int y, int z, ForgeDirection dir) {
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		TileEntity te = this.getAdjacentTileEntity(dir);
		Block b = world.getBlock(dx, dy, dz);
		if (b.isAir(world, dx, dy, dz))
			return;
		this.dischargeToBlock(dx, dy, dz, te instanceof Shockable ? (Shockable)te : null);
		if (b == Blocks.tnt) {
			world.setBlockToAir(dx, dy, dz);
			EntityTNTPrimed e = new EntityTNTPrimed(world, dx+0.5D, dy+0.5D, dz+0.5D, null);
			if (!world.isRemote)
				world.spawnEntityInWorld(e);
			world.playSoundAtEntity(e, "random.fuse", 1.0F, 1.0F);
			world.spawnParticle("lava", dx+rand.nextFloat(), dy+rand.nextFloat(), dz+rand.nextFloat(), 0, 0, 0);
		}
	}

	private void detonate(World world, int x, int y, int z) {
		EntityLightningBolt b = new EntityLightningBolt(world, x+0.5, y, z+0.5);
		//world.spawnEntityInWorld(b);
		world.addWeatherEffect(b);
		charge = 0;
		world.setBlockToAir(x, y, z);
		world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4F, true, true);
	}

	public void dischargeToBlock(int x, int y, int z, Shockable s) {
		float dx = 0.5F;
		float dy = 0.5F;
		float dz = 0.5F;
		if (s != null) {
			int min = s.getMinDischarge();
			if (charge < min)
				return;
			s.onDischarge(charge, ReikaMathLibrary.py3d(xCoord-x, yCoord-y, zCoord-z));
			dx = s.getAimX();
			dy = s.getAimY();
			dz = s.getAimZ();
		}
		SoundRegistry.SPARK.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.25F, 1F);
		EntityDischarge d = new EntityDischarge(worldObj, xCoord+0.5, yCoord+0.75, zCoord+0.5, charge, x+dx, y+dy, z+dz);
		if (!worldObj.isRemote)
			worldObj.spawnEntityInWorld(d);
		charge = 0;
	}

	private void shock(EntityLivingBase e) {
		int dmg = this.getAttackDamage();

		if (ReikaEntityHelper.isEntityWearingFullSuitOf(e, ArmorMaterial.CHAIN))
			dmg = 0;
		else if (ReikaEntityHelper.isEntityWearingFullSuitOf(e, ArmorMaterial.CLOTH))
			dmg /= 2;

		if (dmg > 0) {
			e.attackEntityFrom(RotaryCraft.shock, dmg);
			if (e instanceof EntityCreeper) {
				worldObj.createExplosion(e, e.posX, e.posY, e.posZ, 3F, true);
				e.attackEntityFrom(DamageSource.magic, Integer.MAX_VALUE);
			}
		}
		MinecraftForge.EVENT_BUS.post(new VDGAttackEvent(this, charge, dmg));
		SoundRegistry.SPARK.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 1.25F, 1F);
	}

	private int getAttackDamage() {
		return 1+(int)(Math.pow(charge, 2)/(4194304*8));
	}

	public int getCharge() {
		return charge;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.VANDEGRAFF;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getRange() {
		return Math.min(charge/1024, this.getMaxRange());
	}

	@Override
	public int getMaxRange() {
		return 16;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("c", charge);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		charge = NBT.getInteger("c");
	}

	@Override
	public void onAdjacentUpdate(World world, int x, int y, int z, Block b) {
		this.updateSidedMappings(world, x, y, z);
	}

}
