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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.IO.ReikaKeyHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaReflectionHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemJetPackChest extends ItemBedrockArmor implements IElectricItem { //does not work in smp!!

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
		int px = (int) Math.floor(player.posX);
		int py = (int) Math.floor(player.posY);
		double y = player.posY;
		int pz = (int) Math.floor(player.posZ);

		ItemStack jetpack = player.inventory.armorInventory[2];

		int chg = this.getCharge(jetpack);
		if (chg == 0)
			return false;
		if (player.ridingEntity != null)
			return false;

		float power = 0.03875F;

		double max = this.getMaxCharge(jetpack);

		int hscale = 160;

		if (this.getCharge(jetpack) <= max/6) {
			hscale -= (max/6-chg)/(max/6)*160;
		}
		if (hscale < 10)
			hscale = 10;
		else if (hscale < 20)
			hscale = 20;

		int maxh = player.worldObj.getTopSolidOrLiquidBlock(px, pz)+hscale;
		if (y > maxh)
			power = 0.005F;
		else if (maxh-y < 20) {
			double factor = (maxh-y)/20D;
			power = 0.005F+(float)(power*factor*factor);
		}

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && Keyboard.isKeyDown(ReikaKeyHelper.getForwardKey())) {
			//power += 100;
		}

		boolean electric = true;
		player.playSound(SoundRegistry.JETPACK.getPlayableReference(), 0.5F, 1);

		ReikaPacketHelper.sendFloatPacket(RotaryCraft.packetChannel, PacketRegistry.JETPACK.getMinValue(), player.worldObj, px, py, pz, power);

		//player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);

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

		boolean bool = ReikaReflectionHelper.getPrivateBoolean(player, "isJumping", RotaryCraft.logger);
		//ReikaJavaLibrary.pConsole(bool+" on "+FMLCommonHandler.instance().getEffectiveSide());
		if (bool) {
			jetpackUsed = this.useJetpack(player);
		}

		if (!world.isRemote && (toggleTimer > 0)) {
			toggleTimer = (byte)(toggleTimer - 1);

			nbtData.setByte("toggleTimer", toggleTimer);
		}
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
