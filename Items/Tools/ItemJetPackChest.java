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

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.IO.ReikaKeyHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaReflectionHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Fuelable;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetPackChest extends ItemRotaryArmor implements Fuelable {

	public ItemJetPackChest(int ID, EnumArmorMaterial mat, int tex, int render) {
		super(ID, mat, tex, render, 1);
	}

	public int getFuel(ItemStack is) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			return 0;
		return nbt.getInteger("fuel");
	}

	public void use(ItemStack is, int amount) {
		int newFuel = this.getFuel(is) - amount;
		if (newFuel < 0)
			newFuel = 0;

		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null)
			is.stackTagCompound = new NBTTagCompound();
		nbt.setInteger("fuel", newFuel);
	}

	public boolean useJetpack(EntityPlayer player)
	{
		int px = (int) Math.floor(player.posX);
		int py = (int) Math.floor(player.posY);
		double y = player.posY;
		int pz = (int) Math.floor(player.posZ);

		ItemStack jetpack = player.inventory.armorInventory[2];

		int chg = this.getFuel(jetpack);
		if (chg == 0)
			return false;
		if (player.ridingEntity != null)
			return false;

		float power = 0.03875F;

		double max = this.getMaxFuel(jetpack);

		int maxh = player.worldObj.provider.getActualHeight();
		if (y > maxh)
			power = 0.005F;
		else if (maxh-y < 20) {
			double factor = (maxh-y)/8D;
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

		boolean bool = ReikaReflectionHelper.getPrivateBoolean(player, DragonAPICore.isDeObfEnvironment() ? "isJumping" : "field_70703_bu", RotaryCraft.logger);
		//ReikaJavaLibrary.pConsole(bool+" on "+FMLCommonHandler.instance().getEffectiveSide());
		if (bool) {
			jetpackUsed = this.useJetpack(player);
		}

		if (!world.isRemote && (toggleTimer > 0)) {
			toggleTimer = (byte)(toggleTimer - 1);

			nbtData.setByte("toggleTimer", toggleTimer);
		}
	}

	public int getMaxFuel(ItemStack is) {
		return 30000;
	}
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		int ch = is.stackTagCompound.getInteger("fuel");
		li.add(String.format("Fuel: %d mB", ch));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		Enchantment ench = ((ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance()).getDefaultEnchantment();
		if (ench != null)
			is.addEnchantment(ench, 4);
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("fuel", this.getMaxFuel(is));
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir.isAvailableInCreativeInventory())
			li.add(is);
	}

	public String getArmorTextureFile(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Misc/bedrock_jet.png";
	}

	@Override
	public Fluid getFuel() {
		return FluidRegistry.getFluid("rc ethanol");
	}

	@Override
	public int getCapacity(ItemStack is) {
		return this.getMaxFuel(is);
	}

	@Override
	public int getCurrentFuel(ItemStack is) {
		return this.getFuel(is);
	}

	@Override
	public int addFuel(ItemStack is, int amt) {
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null) {
			is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setInteger("fuel", amt);
			return amt;
		}
		else {
			int cap = this.getCapacity(is);
			int cur = nbt.getInteger("fuel");
			int sum = cur+amt;
			if (sum > cap) {
				int diff = sum-cap;
				is.stackTagCompound.setInteger("fuel", cap);
				return sum-diff;
			}
			else {
				is.stackTagCompound.setInteger("fuel", sum);
				return amt;
			}
		}
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		ItemRegistry ir = ItemRegistry.getEntry(item);
		return ir != null ? ir.getTextureIndex() : 0;
	}
}
