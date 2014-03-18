/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class EntityGasMinecart extends EntityMinecart {

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
		return 200;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		//dataWatcher.addObject(2400, new Byte((byte)0));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		fueltick++;

		if (fueltick >= EngineType.GAS.getFuelUnitDuration()*25) {
			fueltick = 0;
			if (fuel > 0)
				--fuel;
		}

		if (fuel <= 0)
			pushX = pushZ = 0.0D;

		//this.setMinecartPowered(fuel > 0);

		if (this.isMinecartPowered() && rand.nextInt(4) == 0)
			ReikaParticleHelper.SMOKE.spawnAt(worldObj, posX, posY + 0.8D, posZ);
	}

	@Override
	public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);

		if (!par1DamageSource.isExplosion())
			this.entityDropItem(EngineType.GAS.getCraftedProduct(), 0);
	}

	@Override
	protected void updateOnTrack(int par1, int par2, int par3, double par4, double par6, int par8, int par9)
	{
		super.updateOnTrack(par1, par2, par3, par4, par6, par8, par9);

		double d2 = pushX * pushX + pushZ * pushZ;

		if (d2 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D) {
			d2 = MathHelper.sqrt_double(d2);
			pushX /= d2;
			pushZ /= d2;

			if (pushX * motionX + pushZ * motionZ < 0.0D) {
				pushX = 0.0D;
				pushZ = 0.0D;
			}
			else {
				pushX = motionX;
				pushZ = motionZ;
			}
		}

		int futurex = (int)posX;
		int futurez = (int)posZ;

		if (motionX > 0)
			futurex++;
		if (motionX < 0)
			futurex--;
		if (motionZ > 0)
			futurez++;
		if (motionZ < 0)
			futurez--;

		if (this.headingToCurve(futurex, (int)posY, futurez)) {
			if (motionX > 0)
				motionX = 0.25;
			if (motionX < 0)
				motionX = -0.25;

			if (motionZ > 0)
				motionZ = 0.25;
			if (motionZ < 0)
				motionZ = -0.25;
		}
		else {

		}
	}

	private boolean headingToCurve(int futurex, int y, int futurez) {
		int id = worldObj.getBlockId(futurex, y, futurez);
		int id2 = worldObj.getBlockId((int)posX, y, (int)posZ);
		if (id != Block.rail.blockID && id2 != Block.rail.blockID)
			return false;
		if (id == Block.rail.blockID) {
			int meta = worldObj.getBlockMetadata(futurex, y, futurez);
			if (meta == 6 || meta == 7 || meta == 8 || meta == 9)
				return true;
		}
		if (id2 == Block.rail.blockID) {
			int meta = worldObj.getBlockMetadata(futurex, y, futurez);
			if (meta == 6 || meta == 7 || meta == 8 || meta == 9)
				return true;
		}
		return false;
	}

	@Override
	protected void applyDrag()
	{
		double d0 = pushX * pushX + pushZ * pushZ;

		if (d0 > 1.0E-4D) {
			d0 = MathHelper.sqrt_double(d0);
			pushX /= d0;
			pushZ /= d0;
			double d1 = 0.05D;
			motionX *= 0.9800000011920929D;
			motionY *= 0.0D;
			motionZ *= 0.9800000011920929D;
			motionX += pushX * d1;
			motionZ += pushZ * d1;
		}
		else {
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
	public boolean interactFirst(EntityPlayer par1EntityPlayer)
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

	protected boolean isMinecartPowered()
	{
		return fuel > 0;
	}

	@Override
	public void moveMinecartOnRail(int x, int y, int z, double par4) {
		double d12 = motionX;
		double d13 = motionZ;

		par4 *= 2;

		if (this.isMinecartPowered()) {
			motionX *= 1.1;
			motionZ *= 1.1;
		}

		if (d12 < -par4)
			d12 = -par4;

		if (d12 > par4)
			d12 = par4;

		if (d13 < -par4)
			d13 = -par4;

		if (d13 > par4)
			d13 = par4;

		this.moveEntity(d12, 0.0D, d13);
	}

	@Override
	public void applyEntityCollision(Entity par1Entity)
	{
		MinecraftForge.EVENT_BUS.post(new MinecartCollisionEvent(this, par1Entity));
		if (getCollisionHandler() != null)
		{
			getCollisionHandler().onEntityCollision(this, par1Entity);
			return;
		}
		if (!worldObj.isRemote)
		{
			double d0 = par1Entity.posX - posX;
			double d1 = par1Entity.posZ - posZ;
			if (par1Entity instanceof EntityMinecart)
			{
				double d4 = par1Entity.posX - posX;
				double d5 = par1Entity.posZ - posZ;
				Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool(d4, 0.0D, d5).normalize();
				Vec3 vec31 = worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F), 0.0D, MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F)).normalize();
				double d6 = Math.abs(vec3.dotProduct(vec31));

				if (d6 < 0.800000011920929D)
				{
					return;
				}

				double d7 = par1Entity.motionX + motionX;
				double d8 = par1Entity.motionZ + motionZ;

				if (((EntityMinecart)par1Entity).isPoweredCart() && !this.isPoweredCart())
				{
					par1Entity.motionX *= 0.949999988079071D;
					par1Entity.motionZ *= 0.949999988079071D;
				}
				else if (!((EntityMinecart)par1Entity).isPoweredCart() && this.isPoweredCart())
				{
					par1Entity.motionX *= 0.20000000298023224D;
					par1Entity.motionZ *= 0.20000000298023224D;
					par1Entity.addVelocity(motionX + d0, 0.0D, motionZ + d1);
				}
				else
				{
					d7 /= 2.0D;
					d8 /= 2.0D;
					par1Entity.motionX *= 0.20000000298023224D;
					par1Entity.motionZ *= 0.20000000298023224D;
					par1Entity.addVelocity(d7 + d0, 0.0D, d8 + d1);
				}
			}
		}
	}

	@Override
	public boolean isPoweredCart()
	{
		return true;
	}
}
