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

import ic2.api.item.IElectricItem;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.ReikaKeyHelper;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemJetPackChest extends ItemBedrockArmor implements IElectricItem {

	private static boolean lastJetpackUsed = false;

	public ItemJetPackChest(int ID, int tex, int render, int type) {
		super(ID, tex, render, type);
	}

	public int getCharge(ItemStack is) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			return 0;
		return nbt.getInteger("charge");
	}

	public void use(ItemStack is, int amount) {
		int newCharge = this.getCharge(is) - amount;
		if (newCharge < 0)
			newCharge = 0;

		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			is.stackTagCompound = new NBTTagCompound();
		nbt.setInteger("charge", newCharge);
	}

	public boolean useJetpack(EntityPlayer player)
	{
		ItemStack jetpack = player.inventory.armorInventory[2];
		player.playSound(SoundRegistry.JETPACK.getPlayableReference(), 0.5F, 1);

		if (this.getCharge(jetpack) == 0)
			return false;
		if (player.ridingEntity != null)
			return false;

		boolean electric = true;

		float power = 0.03875F;
		float dropPercentage = 0.05F;

		if (this.getCharge(jetpack) / this.getMaxCharge(jetpack) <= dropPercentage) {
			power *= this.getCharge(jetpack) / (this.getMaxCharge(jetpack) * dropPercentage);
		}

		if (Keyboard.isKeyDown(ReikaKeyHelper.getForwardKey())) {
			float retruster = 0.30F;

			float forwardpower = power * retruster * 2.0F;

			if (forwardpower > 0.0F) {
				player.moveFlying(0.0F, 0.4F * forwardpower, 0.02F);
			}

		}

		int worldHeight = player.worldObj.provider.getHeight();
		int maxFlightHeight = worldHeight*this.getCharge(jetpack)/this.getMaxCharge(jetpack);

		double y = player.posY;

		if (y > maxFlightHeight - 25) {
			if (y > maxFlightHeight) y = maxFlightHeight;

			power = (float)(power * ((maxFlightHeight - y) / 25.0D));
		}

		double prevmotion = player.motionY;
		player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);

		int consume = 4;

		if (player.capabilities.isCreativeMode)
			consume = 0;

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
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean jetpackUsed = false;

		if (Keyboard.isKeyDown(ReikaKeyHelper.getJumpKey())) {
			jetpackUsed = this.useJetpack(player);
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

	@Override
	public boolean canProvideEnergy(ItemStack is) {
		return false;
	}

	@Override
	public int getChargedItemId(ItemStack is) {
		return itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack is) {
		return itemID;
	}

	@Override
	public int getMaxCharge(ItemStack is) {
		return 30000;
	}

	@Override
	public int getTier(ItemStack is) {
		return 1;
	}

	@Override
	public int getTransferLimit(ItemStack is) {
		return 60;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		int ch = is.stackTagCompound.getInteger("charge");
		li.add(String.format("Charge: %d EU", ch));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		Enchantment ench = this.getDefaultEnchantment();
		if (ench != null)
			is.addEnchantment(ench, 4);
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("charge", this.getMaxCharge(is));
		if (ItemRegistry.JETCHEST.isAvailableInCreativeInventory())
			li.add(is);
	}

	@Override
	public String getArmorTextureFile(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Misc/bedrock_jet.png";
	}
}
