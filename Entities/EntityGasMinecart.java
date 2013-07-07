/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class EntityGasMinecart extends EntityMinecartFurnace {

	private int fuel = 0;
	private int fueltick = 0;
	public double pushX;
	public double pushZ;

	public EntityGasMinecart(World par1World)
	{
		super(par1World);
	}

	public EntityGasMinecart(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	@Override
	public int getMinecartType()
	{
		return 2;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte)0));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		fueltick++;

		if (fueltick >= EnumEngineType.GAS.getFuelUnitDuration()) {
			fueltick = 0;
			if (fuel > 0)
			{
				--fuel;
			}
		}

		if (fuel <= 0)
		{
			pushX = pushZ = 0.0D;
		}

		this.setMinecartPowered(fuel > 0);

		if (this.isMinecartPowered() && rand.nextInt(4) == 0)
		{
			//worldObj.spawnParticle("largesmoke", posX, posY + 0.8D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);

		if (!par1DamageSource.isExplosion())
		{
			this.entityDropItem(EnumEngineType.GAS.getCraftedProduct(), 0);
		}
	}

	@Override
	protected void updateOnTrack(int par1, int par2, int par3, double par4, double par6, int par8, int par9)
	{
		super.updateOnTrack(par1, par2, par3, par4, par6, par8, par9);
		double d2 = pushX * pushX + pushZ * pushZ;

		if (d2 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D)
		{
			d2 = MathHelper.sqrt_double(d2);
			pushX /= d2;
			pushZ /= d2;

			if (pushX * motionX + pushZ * motionZ < 0.0D)
			{
				pushX = 0.0D;
				pushZ = 0.0D;
			}
			else
			{
				pushX = motionX;
				pushZ = motionZ;
			}
		}
	}

	@Override
	protected void applyDrag()
	{
		double d0 = pushX * pushX + pushZ * pushZ;

		if (d0 > 1.0E-4D)
		{
			d0 = MathHelper.sqrt_double(d0);
			pushX /= d0;
			pushZ /= d0;
			double d1 = 0.05D;
			motionX *= 0.800000011920929D;
			motionY *= 0.0D;
			motionZ *= 0.800000011920929D;
			motionX += pushX * d1;
			motionZ += pushZ * d1;
		}
		else
		{
			motionX *= 0.9800000190734863D;
			motionY *= 0.0D;
			motionZ *= 0.9800000190734863D;
		}

		super.applyDrag();
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, par1EntityPlayer)))
		{
			return true;
		}
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if (itemstack != null && itemstack.itemID == ItemRegistry.ETHANOL.getShiftedID())
		{
			if (--itemstack.stackSize == 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			}

			fuel += 1;
		}

		pushX = posX - par1EntityPlayer.posX;
		pushZ = posZ - par1EntityPlayer.posZ;
		return true;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setDouble("PushX", pushX);
		par1NBTTagCompound.setDouble("PushZ", pushZ);
		par1NBTTagCompound.setShort("Fuel", (short)fuel);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		pushX = par1NBTTagCompound.getDouble("PushX");
		pushZ = par1NBTTagCompound.getDouble("PushZ");
		fuel = par1NBTTagCompound.getShort("Fuel");
	}

	@Override
	protected boolean isMinecartPowered()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
}
