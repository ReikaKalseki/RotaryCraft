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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Interfaces.Block.SemiUnbreakable;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityLandmine extends TileEntitySpringPowered {

	private boolean flaming = false;
	private boolean poison = false;
	private boolean chain = false;
	private boolean shrapnel = false;

	private int explosionDelay = 0;
	private boolean isChainExploding = false;

	@Override
	public int getSizeInventory() {
		return 9;
	}

	private boolean checkForPlayer(World world, int x, int y, int z) {
		AxisAlignedBB above = AxisAlignedBB.getBoundingBox(x, y+1, z, x+1, y+3, z+1);
		List in = world.getEntitiesWithinAABB(EntityLivingBase.class, above);
		for (int i = 0; i < in.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)in.get(i);
			if (e.onGround && !e.isSneaking())
				return true;
		}
		return false;
	}

	private boolean checkForArrow(World world, int x, int y, int z) {
		AxisAlignedBB above = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(1, 1, 1);
		List in = world.getEntitiesWithinAABB(EntityArrow.class, above);
		return in.size() > 0;
	}

	private boolean ageFail() {
		if (rand.nextInt(20) > 0)
			return false;
		int age = this.getAge();
		if (age == 0)
			return false;
		return (rand.nextInt(1+65536-this.getAge()) == 0);
	}

	private void maxPowerExplosion(World world, int x, int y, int z) {
		world.spawnParticle("hugeexplosion", x+0.5, y+0.5, z+0.5, 0, 0, 0);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.explode", 1, 1);
		for (int i = 1; i < 6; i++) {
			for (int k = 0; k < 6; k++) {
				ForgeDirection dir = dirs[k];
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				Block b = world.getBlock(dx, dy, dz);
				if (b instanceof SemiUnbreakable && ((SemiUnbreakable)b).isUnbreakable(world, dx, dy, z, world.getBlockMetadata(dx, dy, dz)))
					continue;
				ReikaWorldHelper.recursiveBreakWithinSphere(world, dx, dy, dz, world.getBlock(dx, dy, dz), -1, x, y, z, 4);
			}
			this.chainedExplosion(world, x, y, z);
		}
	}

	public void detonate(World world, int x, int y, int z) {
		if (chain)
			this.chainedExplosion(world, x, y, z);
		if (inv[1] != null && inv[2] != null && inv[3] != null && inv[4] != null) {
			boolean flag = true;
			for (int i = 1; i <= 4; i++) {
				if (!!ReikaItemHelper.matchStackWithBlock(inv[i], Blocks.tnt))
					flag = false;
			}
			if (flag)
				this.maxPowerExplosion(world, x, y, z);
		}
		float power = this.getExplosionPower();
		world.setBlockToAir(x, y, z);
		if (flaming) {
			if (!world.isRemote)
				world.newExplosion(null, x+0.5, y+0.5, z+0.5, power, true, true);
		}
		else if (!world.isRemote)
			world.createExplosion(null, x+0.5, y+0.5, z+0.5, power, true);
		AxisAlignedBB region = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(2, 2, 2);
		List in = world.getEntitiesWithinAABB(EntityLivingBase.class, region);
		for (int i = 0; i < in.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)in.get(i);
			if (e instanceof EntityPlayer) {
				if (!((EntityPlayer)e).capabilities.isCreativeMode)
					RotaryAchievements.LANDMINE.triggerAchievement((EntityPlayer) e);
			}
			e.attackEntityFrom(DamageSource.setExplosionSource(new Explosion(world, null, e.posX, e.posY, e.posZ, power)), (int)power*4);
			e.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 0));
			e.addPotionEffect(new PotionEffect(Potion.confusion.id, 450, 5));
			if (poison)
				e.addPotionEffect(new PotionEffect(Potion.poison.id, 200, 0));
			if (e instanceof EntityCreeper) {
				world.createExplosion(e, x+0.5, y+0.5, z+0.5, 3, true);
			}
		}
		if (shrapnel) {
			AxisAlignedBB region2 = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(8, 8, 8);
			List in2 = world.getEntitiesWithinAABB(EntityLivingBase.class, region2);
			for (int i = 0; i < in2.size(); i++) {
				EntityLivingBase e = (EntityLivingBase)in2.get(i);
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				int dmg = dd < 4 ? 8 : dd < 8 ? 6 : 4;
				e.attackEntityFrom(DamageSource.generic, dmg);
				ReikaEntityHelper.spawnParticlesAround("crit", e, 8);
			}
		}/*
		for (int i = -8; i <= 8; i++) {
			for (int j = -8; j <= 8; j++) {
				for (int k = -8; k <= 8; k++) {
					if (world.getBlock(x+i, y+j, z+k) == this.getTileEntityBlockID()) {
						TileEntity te = world.getTileEntity(x+i, y+j, z+k);
						if (te instanceof TileEntityLandmine)
							((TileEntityLandmine)te).detonate(world, te.xCoord, te.yCoord, te.zCoord);
					}
				}
			}
		}*/
	}

	private void getExplosionModifiers() {
		for (int i = 5; i <= 8; i++) {
			if (inv[i] != null) {
				if (inv[i].getItem() == Items.blaze_powder)
					flaming = true;
				if (inv[i].getItem() == Items.spider_eye)
					poison = true;
				if (ReikaItemHelper.matchStackWithBlock(inv[i], Blocks.tnt))
					chain = true;
				if (ReikaItemHelper.matchStackWithBlock(inv[i], Blocks.glass))
					shrapnel = true;
			}
		}
	}

	private float getExplosionPower() {
		int num = 0;
		for (int i = 1; i <= 4; i++)
			if (inv[i] != null) {
				if (inv[i].getItem() == Items.gunpowder)
					num++;
			}
		return 2F*num; //Each item is 1/2 block TNT (so capped at 2x)
	}

	private int getAge() {
		return 65536-inv[0].getItemDamage();
	}

	@Override
	public void openInventory() {
		super.openInventory();
		if (inv[0] == null)
			return;
		if (rand.nextInt(65536-this.getAge())/2 == 0)
			this.detonate(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		switch (i) {
			case 0:
				return super.isItemValidForSlot(i, is);
			case 1:
			case 2:
			case 3:
			case 4:
				return is.getItem() == Items.gunpowder;
			case 5:
			case 6:
			case 7:
			case 8:
				return this.isModifier(is);
			default:
				return false;
		}
	}

	private boolean isModifier(ItemStack is) {
		if (is.getItem() == Items.blaze_powder)
			return true;
		if (is.getItem() == Items.spider_eye)
			return true;
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.tnt))
			return true;
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.glass))
			return true;
		return false;
	}

	private void chainedExplosion(World world, int x, int y, int z) {
		for (int i = 0; i < 12; i++) {
			EntityTNTPrimed tnt = new EntityTNTPrimed(world, x-5+rand.nextInt(11), y-5+rand.nextInt(11), z-5+rand.nextInt(11), null);
			tnt.fuse = 5+rand.nextInt(10);
			if (!world.isRemote)
				world.spawnEntityInWorld(tnt);
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LANDMINE;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!this.hasCoil())
			return;
		tickcount++;
		if (tickcount > this.getUnwindTime()) {
			ItemStack is = this.getDecrementedCharged();
			inv[0] = is;
			tickcount = 0;
		}

		this.getExplosionModifiers();
		if (!DragonAPICore.debugtest && this.ageFail())
			this.detonate(world, x, y, z);
		if (this.checkForArrow(world, x, y, z))
			this.detonate(world, x, y, z);
		if (this.checkForPlayer(world, x, y, z))
			this.detonate(world, x, y, z);
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
	public int getBaseDischargeTime() {
		return 360;
	}

}
