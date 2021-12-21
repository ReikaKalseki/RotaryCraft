/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.HarvesterDamage;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMobHarvester extends TileEntityPowerReceiver implements EnchantableMachine {

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.infinity).addFilter(Enchantment.sharpness).addFilter(Enchantment.fireAspect).addFilter(Enchantment.silkTouch).addFilter(Enchantment.looting);

	public String owner;
	public boolean laser;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		//ReikaJavaLibrary.pConsole(this.hasEnchantments());
		//this.tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		EntityPlayer ep = this.getPlacer();
		//if (this.tickcount < 5)
		//return;
		//this.tickcount = 0;
		boolean oneplus = false;
		AxisAlignedBB box = this.getBox();
		List<EntityLiving> inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		for (EntityLiving ent : inbox) {
			if (!(ent instanceof EntityVillager)) {
				oneplus = true;
				if (ep != null) {
					int dmg = this.getDamage();
					if (dmg > 0) {
						ent.attackEntityFrom(new HarvesterDamage(this), dmg);
						if (enchantments.hasEnchantment(Enchantment.silkTouch) && rand.nextInt(20) == 0)
							ReikaEntityHelper.dropHead(ent);
						//Looting is handled with the LivingDropsEvent
						if (enchantments.hasEnchantment(Enchantment.fireAspect))
							ent.setFire(enchantments.getEnchantment(Enchantment.fireAspect)*2);
					}
				}
				ent.motionY = 0;
			}
		}
		laser = oneplus;
	}

	public int getDamage() {
		double pdiff = 2+(0.5*power/MINPOWER);
		double ppdiff = ReikaMathLibrary.intpow(pdiff, 6);
		double base = ReikaMathLibrary.logbase(ppdiff, 2)+2*enchantments.getEnchantmentAt(Enchantment.sharpness, this, pink);
		if (ModList.CHROMATICRAFT.isLoaded()) {
			base *= ChromatiAPI.getAPI().adjacency().getFactorSimple(worldObj, xCoord, yCoord, zCoord, "PINK");
		}
		return (int)base;
	}

	public AxisAlignedBB getBox() {
		//return AxisAlignedBB.getBoundingBox(this.xCoord-4, this.yCoord-4, this.zCoord-4, this.xCoord+5, this.yCoord+5, this.zCoord+5);
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord+1, zCoord, xCoord+1, yCoord+this.getHeight()+1, zCoord+1);
	}

	private int getHeight() {
		return Math.min(6, 2+Math.max(0, (int)power/524288));
	}

	public AxisAlignedBB getLaser() {
		return AxisAlignedBB.getBoundingBox(xCoord+0.4, yCoord+1, zCoord+0.4, xCoord+0.6, yCoord+this.getHeight(), zCoord+0.6);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("sowner", owner);

		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		owner = NBT.getString("sowner");

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
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
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}
}
