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
import java.util.HashMap;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.HarvesterDamage;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMobHarvester extends TileEntityPowerReceiver implements EnchantableMachine {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	public String owner;
	public boolean laser;

	public List inbox;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		//ReikaJavaLibrary.pConsole(this.hasEnchantments());
		//this.tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		EntityPlayer ep = null;
		if (owner != null)
			ep = world.getPlayerEntityByName(owner);
		if (ep == null)
			ep = world.getClosestPlayer(x, y, z, -1);
		//if (this.tickcount < 5)
		//return;
		//this.tickcount = 0;
		AxisAlignedBB box = this.getBox();
		inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLiving ent = (EntityLiving)inbox.get(i);
			if (!(ent instanceof EntityVillager)) {
				//this.laser = true;
				world.markBlockForRenderUpdate(x, y, z);
				if (ep != null && this.getDamage() > 0) {
					ent.attackEntityFrom(new HarvesterDamage(this), this.getDamage());
					if (this.getEnchantment(Enchantment.silkTouch) > 0 && rand.nextInt(20) == 0)
						ReikaEntityHelper.dropHead(ent);
					//Looting is handled with the LivingDropsEvent
					if (this.getEnchantment(Enchantment.fireAspect) > 0)
						ent.setFire(this.getEnchantment(Enchantment.fireAspect)*2);
				}
				ent.motionY = 0;
			}
		}
		if (inbox.size() == 0 && !(inbox.size() == 1 && (inbox.get(0) instanceof EntityPlayer || inbox.get(0) instanceof EntityVillager)))
			laser = false;
	}

	public int getDamage() {
		int pdiff = 2+(int)(power/MINPOWER);
		int ppdiff = (int)ReikaMathLibrary.intpow(pdiff, 6);
		return (int)ReikaMathLibrary.logbase(ppdiff, 2)+2*this.getEnchantment(Enchantment.sharpness);
	}

	public AxisAlignedBB getBox() {
		//return AxisAlignedBB.getBoundingBox(this.xCoord-4, this.yCoord-4, this.zCoord-4, this.xCoord+5, this.yCoord+5, this.zCoord+5);
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord+1, zCoord, xCoord+1, yCoord+this.getHeight()+1, zCoord+1);
	}

	private int getHeight() {
		return 3;
	}

	public AxisAlignedBB getLaser() {
		return AxisAlignedBB.getBoundingBox(xCoord+0.4, yCoord+1, zCoord+0.4, xCoord+0.6, yCoord+3, zCoord+0.6);
	}

	public boolean applyEnchants(ItemStack is) {
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.sharpness, is)) {
			enchantments.put(Enchantment.sharpness, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness, is));
			return true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.fireAspect, is)) {
			enchantments.put(Enchantment.fireAspect, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect, is));
			return true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			enchantments.put(Enchantment.silkTouch, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch, is));
			return true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.looting, is))	 {
			enchantments.put(Enchantment.looting, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.looting, is));
			return true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.infinity, is))	 {
			enchantments.put(Enchantment.infinity, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.infinity, is));
			return true;
		}
		return false;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.sharpness);
		li.add(Enchantment.fireAspect);
		li.add(Enchantment.silkTouch);
		li.add(Enchantment.looting);
		li.add(Enchantment.infinity);
		return li;
	}

	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("sowner", owner);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		owner = NBT.getString("sowner");

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}
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
		return MachineRegistry.MOBHARVESTER;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
