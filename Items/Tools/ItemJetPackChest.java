/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import ic2.api.IElectricItem;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;


public class ItemJetPackChest extends ItemBedrockArmor implements IElectricItem {

	private static boolean lastJetpackUsed = false;

	public ItemJetPackChest(int ID, int tex, int render, int type) {
		super(ID, tex, render, type);
	}

	public int getCharge(ItemStack is) {/*
		int ret = this.getMaxCharge(is) - is.getItemDamage() - 1;

		return ret > 0 ? ret : 0;*/
		return -29000;
	}

	public int getMaxCharge(ItemStack is) {
		return is.getMaxDamage() - 2;
	}

	public void use(ItemStack is, int amount) {
		int newCharge = this.getCharge(is) - amount;
		if (newCharge < 0) newCharge = 0;

		is.setItemDamage(1 + is.getMaxDamage() - newCharge);
		//ElectricItem.discharge(is, amount, 2147483647, true, false); ???
	}

	public boolean useJetpack(EntityPlayer player, boolean hoverMode)
	{
		ItemStack jetpack = player.inventory.armorInventory[2];

		if (this.getCharge(jetpack) == 0) return false;

		boolean electric = true;

		float power = 0.7F;
		float dropPercentage = 0.05F;

		if (this.getCharge(jetpack) / this.getMaxCharge(jetpack) <= dropPercentage) {
			power *= this.getCharge(jetpack) / (this.getMaxCharge(jetpack) * dropPercentage);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			float retruster = 0.15F;

			if (hoverMode) retruster = 0.5F;
			if (electric) retruster += 0.15F;

			float forwardpower = power * retruster * 2.0F;

			if (forwardpower > 0.0F) {
				player.moveFlying(0.0F, 0.4F * forwardpower, 0.02F);
			}

		}

		int worldHeight = player.worldObj.provider.getHeight();
		int maxFlightHeight = electric ? (int)(worldHeight / 1.28F) : worldHeight;

		double y = player.posY;

		if (y > maxFlightHeight - 25) {
			if (y > maxFlightHeight) y = maxFlightHeight;

			power = (float)(power * ((maxFlightHeight - y) / 25.0D));
		}

		double prevmotion = player.motionY;
		player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);

		int consume = 7;

		this.use(jetpack, consume);

		player.fallDistance = 0.0F;
		player.distanceWalkedModified = 0.0F;

		//IC2.platform.resetPlayerInAirTime(player);

		return true;
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack is)
	{
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbtData = is.stackTagCompound;
		boolean hoverMode = nbtData.getBoolean("hoverMode");
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean jetpackUsed = false;

		if ((Keyboard.isKeyDown(Keyboard.KEY_SPACE)) || ((hoverMode) && (player.motionY < -0.3499999940395355D))) {
			jetpackUsed = this.useJetpack(player, hoverMode);
		}

		if (!world.isRemote && (toggleTimer > 0)) {
			toggleTimer = (byte)(toggleTimer - 1);

			nbtData.setByte("toggleTimer", toggleTimer);
		}

		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (player == Minecraft.getMinecraft().thePlayer)) {
			if (lastJetpackUsed != jetpackUsed) {
				lastJetpackUsed = jetpackUsed;
			}
		}

		if (jetpackUsed) player.inventoryContainer.detectAndSendChanges();
	}

	public void func_77633_a(int i, CreativeTabs tabs, List itemList)
	{
		itemList.add(new ItemStack(this, 1, 1));
	}

	@Override
	public boolean canProvideEnergy() {
		return false;
	}

	@Override
	public int getChargedItemId() {
		return itemID;
	}

	@Override
	public int getEmptyItemId() {
		return itemID;
	}

	@Override
	public int getMaxCharge() {
		return 30000;
	}

	@Override
	public int getTier() {
		return 1;
	}

	@Override
	public int getTransferLimit() {
		return 60;
	}
	/*
	public int getCharge(ItemStack itemStack)
	{
		return ElectricItem.manager.getCharge(itemStack);
	}

	public void use(ItemStack itemStack, int amount)
	{
		ElectricItem.manager.discharge(itemStack, amount, 2147483647, true, false);
	}*/
}
