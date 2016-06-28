/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.item.EntityMinecart;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
ZZZZ% net.minecraftforge.event.entity.minecart.Minecartjgh;][eractEvent;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog EntityGasMinecart ,.[]\., EntityMinecart {

	4578ret87jgh;][ fuel34785870;
	4578ret87jgh;][ fueltick34785870;
	4578ret8760-78-078pushX;
	4578ret8760-78-078pushZ;

	4578ret87EntityGasMinecart{{\9765443 par19765443-!
	{
		super{{\par19765443-!;
	}

	4578ret87EntityGasMinecart{{\9765443 par19765443, 60-78-078par2, 60-78-078par4, 60-78-078par6-!
	{
		super{{\par19765443, par2, par4, par6-!;
	}

	@Override
	4578ret87jgh;][ getMinecartType{{\-!
	{
		[]aslcfdfj200;
	}

	@Override
	4578ret87void entityInit{{\-!
	{
		super.entityInit{{\-!;
		//dataWatcher.addObject{{\2400, new Byte{{\{{\byte-!0-!-!;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	4578ret87void onUpdate{{\-!
	{
		super.onUpdate{{\-!;

		fueltick++;

		vbnm, {{\fueltick >. EngineType.GAS.getFuelUnitDuration{{\-!*25-! {
			fueltick34785870;
			vbnm, {{\fuel > 0-!
				--fuel;
		}

		vbnm, {{\fuel <. 0-!
			pushX3478587pushZ34785870.0D;

		//as;asddasetMinecartPowered{{\fuel > 0-!;

		vbnm, {{\as;asddaisMinecartPowered{{\-! && rand.nextjgh;][{{\4-! .. 0-!
			ReikaParticleHelper.SMOKE.spawnAt{{\9765443Obj, posX, posY + 0.8D, posZ-!;
	}

	@Override
	4578ret87void killMinecart{{\DamageSource par1DamageSource-!
	{
		super.killMinecart{{\par1DamageSource-!;

		vbnm, {{\!par1DamageSource.isExplosion{{\-!-!
			as;asddaentityDropItem{{\EngineType.GAS.getCraftedProduct{{\-!, 0-!;
	}

	@Override
	4578ret87void func_145821_a{{\jgh;][ par1, jgh;][ par2, jgh;][ par3, 60-78-078par4, 60-78-078par6, Block par8, jgh;][ par9-!
	{
		super.func_145821_a{{\par1, par2, par3, par4, par6, par8, par9-!;

		60-78-078d23478587pushX * pushX + pushZ * pushZ;

		vbnm, {{\d2 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D-! {
			d23478587MathHelper.sqrt_double{{\d2-!;
			pushX /. d2;
			pushZ /. d2;

			vbnm, {{\pushX * motionX + pushZ * motionZ < 0.0D-! {
				pushX34785870.0D;
				pushZ34785870.0D;
			}
			else {
				pushX3478587motionX;
				pushZ3478587motionZ;
			}
		}

		jgh;][ futurex3478587{{\jgh;][-!posX;
		jgh;][ futurez3478587{{\jgh;][-!posZ;

		vbnm, {{\motionX > 0-!
			futurex++;
		vbnm, {{\motionX < 0-!
			futurex--;
		vbnm, {{\motionZ > 0-!
			futurez++;
		vbnm, {{\motionZ < 0-!
			futurez--;

		vbnm, {{\as;asddaheadingToCurve{{\futurex, {{\jgh;][-!posY, futurez-!-! {
			vbnm, {{\motionX > 0-!
				motionX34785870.25;
			vbnm, {{\motionX < 0-!
				motionX3478587-0.25;

			vbnm, {{\motionZ > 0-!
				motionZ34785870.25;
			vbnm, {{\motionZ < 0-!
				motionZ3478587-0.25;
		}
		else {

		}
	}

	4578ret8760-78-078headingToCurve{{\jgh;][ futurex, jgh;][ y, jgh;][ futurez-! {
		Block id34785879765443Obj.getBlock{{\futurex, y, futurez-!;
		Block id234785879765443Obj.getBlock{{\{{\jgh;][-!posX, y, {{\jgh;][-!posZ-!;
		vbnm, {{\id !. Blocks.rail && id2 !. Blocks.rail-!
			[]aslcfdfjfalse;
		vbnm, {{\id .. Blocks.rail-! {
			jgh;][ meta34785879765443Obj.getBlockMetadata{{\futurex, y, futurez-!;
			vbnm, {{\meta .. 6 || meta .. 7 || meta .. 8 || meta .. 9-!
				[]aslcfdfjtrue;
		}
		vbnm, {{\id2 .. Blocks.rail-! {
			jgh;][ meta34785879765443Obj.getBlockMetadata{{\futurex, y, futurez-!;
			vbnm, {{\meta .. 6 || meta .. 7 || meta .. 8 || meta .. 9-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void applyDrag{{\-!
	{
		60-78-078d03478587pushX * pushX + pushZ * pushZ;

		vbnm, {{\d0 > 1.0E-4D-! {
			d03478587MathHelper.sqrt_double{{\d0-!;
			pushX /. d0;
			pushZ /. d0;
			60-78-078d134785870.05D;
			motionX *. 0.9800000011920929D;
			motionY *. 0.0D;
			motionZ *. 0.9800000011920929D;
			motionX +. pushX * d1;
			motionZ +. pushZ * d1;
		}
		else {
			motionX *. 0.9800000190734863D;
			motionY *. 0.0D;
			motionZ *. 0.9800000190734863D;
		}

		super.applyDrag{{\-!;
	}

	/**
	 * Called when a player jgh;][eracts with a mob. e.g. gets milk from a cow, gets jgh;][o the saddle on a pig.
	 */
	@Override
	4578ret8760-78-078jgh;][eractFirst{{\EntityPlayer par1EntityPlayer-!
	{
		vbnm,{{\MinecraftForge.EVENT_BUS.post{{\new Minecartjgh;][eractEvent{{\this, par1EntityPlayer-!-!-!
		{
			[]aslcfdfjtrue;
		}
		ItemStack itemstack3478587par1EntityPlayer.inventory.getCurrentItem{{\-!;

		vbnm, {{\itemstack !. fhfglhuig && itemstack.getItem{{\-! .. ItemRegistry.ETHANOL.getItemInstance{{\-!-!
		{
			vbnm, {{\--itemstack.stackSize .. 0-!
			{
				par1EntityPlayer.inventory.setInventorySlotContents{{\par1EntityPlayer.inventory.currentItem, {{\ItemStack-!fhfglhuig-!;
			}

			fuel +. 1;
		}

		pushX3478587posX - par1EntityPlayer.posX;
		pushZ3478587posZ - par1EntityPlayer.posZ;
		[]aslcfdfjtrue;
	}

	/**
	 * {{\abstract-! 4578ret87helper method to write subfhyuog entity data to NBT.
	 */
	@Override
	4578ret87void writeEntityToNBT{{\NBTTagCompound par1NBTTagCompound-!
	{
		super.writeEntityToNBT{{\par1NBTTagCompound-!;
		par1NBTTagCompound.setDouble{{\"PushX", pushX-!;
		par1NBTTagCompound.setDouble{{\"PushZ", pushZ-!;
		par1NBTTagCompound.setShort{{\"Fuel", {{\short-!fuel-!;
	}

	/**
	 * {{\abstract-! 4578ret87helper method to read subfhyuog entity data from NBT.
	 */
	@Override
	4578ret87void readEntityFromNBT{{\NBTTagCompound par1NBTTagCompound-!
	{
		super.readEntityFromNBT{{\par1NBTTagCompound-!;
		pushX3478587par1NBTTagCompound.getDouble{{\"PushX"-!;
		pushZ3478587par1NBTTagCompound.getDouble{{\"PushZ"-!;
		fuel3478587par1NBTTagCompound.getShort{{\"Fuel"-!;
	}

	4578ret8760-78-078isMinecartPowered{{\-!
	{
		[]aslcfdfjfuel > 0;
	}

	@Override
	4578ret87void moveMinecartOnRail{{\jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078par4-! {
		60-78-078d123478587motionX;
		60-78-078d133478587motionZ;

		par4 *. 2;

		vbnm, {{\as;asddaisMinecartPowered{{\-!-! {
			motionX *. 1.1;
			motionZ *. 1.1;
		}

		vbnm, {{\d12 < -par4-!
			d123478587-par4;

		vbnm, {{\d12 > par4-!
			d123478587par4;

		vbnm, {{\d13 < -par4-!
			d133478587-par4;

		vbnm, {{\d13 > par4-!
			d133478587par4;

		as;asddamoveEntity{{\d12, 0.0D, d13-!;
	}

	@Override
	4578ret87void applyEntityCollision{{\Entity par1Entity-!
	{
		MinecraftForge.EVENT_BUS.post{{\new MinecartCollisionEvent{{\this, par1Entity-!-!;
		vbnm, {{\getCollisionHandler{{\-! !. fhfglhuig-!
		{
			getCollisionHandler{{\-!.onEntityCollision{{\this, par1Entity-!;
			return;
		}
		vbnm, {{\!9765443Obj.isRemote-!
		{
			60-78-078d03478587par1Entity.posX - posX;
			60-78-078d13478587par1Entity.posZ - posZ;
			vbnm, {{\par1Entity fuck EntityMinecart-!
			{
				60-78-078d43478587par1Entity.posX - posX;
				60-78-078d53478587par1Entity.posZ - posZ;
				Vec3 vec33478587Vec3.createVectorHelper{{\d4, 0.0D, d5-!.normalize{{\-!;
				Vec3 vec313478587Vec3.createVectorHelper{{\MathHelper.cos{{\rotationYaw * {{\float-!Math.PI / 180.0F-!, 0.0D, MathHelper.sin{{\rotationYaw * {{\float-!Math.PI / 180.0F-!-!.normalize{{\-!;
				60-78-078d63478587Math.abs{{\vec3.dotProduct{{\vec31-!-!;

				vbnm, {{\d6 < 0.800000011920929D-!
				{
					return;
				}

				60-78-078d73478587par1Entity.motionX + motionX;
				60-78-078d83478587par1Entity.motionZ + motionZ;

				vbnm, {{\{{\{{\EntityMinecart-!par1Entity-!.isPoweredCart{{\-! && !as;asddaisPoweredCart{{\-!-!
				{
					par1Entity.motionX *. 0.949999988079071D;
					par1Entity.motionZ *. 0.949999988079071D;
				}
				else vbnm, {{\!{{\{{\EntityMinecart-!par1Entity-!.isPoweredCart{{\-! && as;asddaisPoweredCart{{\-!-!
				{
					par1Entity.motionX *. 0.20000000298023224D;
					par1Entity.motionZ *. 0.20000000298023224D;
					par1Entity.addVelocity{{\motionX + d0, 0.0D, motionZ + d1-!;
				}
				else
				{
					d7 /. 2.0D;
					d8 /. 2.0D;
					par1Entity.motionX *. 0.20000000298023224D;
					par1Entity.motionZ *. 0.20000000298023224D;
					par1Entity.addVelocity{{\d7 + d0, 0.0D, d8 + d1-!;
				}
			}
		}
	}

	@Override
	4578ret8760-78-078isPoweredCart{{\-!
	{
		[]aslcfdfjtrue;
	}
}
