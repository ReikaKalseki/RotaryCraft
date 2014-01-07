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

import java.util.HashMap;
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

import Reika.DragonAPI.Libraries.IO.ReikaKeyHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.Java.ReikaReflectionHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Fuelable;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetPack extends ItemRotaryArmor implements Fuelable {

	private static final HashMap<String, Long> onGround = new HashMap();

	public ItemJetPack(int ID, EnumArmorMaterial mat, int tex, int render) {
		super(ID, mat, render, 1, tex);
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
		boolean flying = false;

		//ReikaJavaLibrary.pConsole(player.onGround+":"+Keyboard.isKeyDown(Keyboard.KEY_SPACE));

		if (!ConfigRegistry.CONSERVEPACK.getState() || this.canFly(player)) {
			flying = ReikaReflectionHelper.getPrivateBoolean(player, ReikaObfuscationHelper.getLabelName("isJumping"), RotaryCraft.logger);
			//ReikaJavaLibrary.pConsole(bool+" on "+FMLCommonHandler.instance().getEffectiveSide());
			if (flying) {
				jetpackUsed = this.useJetpack(player);
			}
		}

		if (!world.isRemote && (toggleTimer > 0)) {
			toggleTimer = (byte)(toggleTimer - 1);

			nbtData.setByte("toggleTimer", toggleTimer);
		}

		if (ConfigRegistry.EXPLODEPACK.getState()) {
			if (this.getCurrentFuel(is) > 0) {
				if (player.handleLavaMovement() && world.difficultySetting != 0) {
					this.explode(world, player);
				}
				else if (player.isBurning() && world.difficultySetting > 1 && flying) {
					this.explode(world, player);
				}
			}
		}
	}

	private boolean canFly(EntityPlayer player) {
		long millis = System.currentTimeMillis();
		if (player.onGround) {
			onGround.put(player.getEntityName(), millis);
			return false;
		}
		Long prev = onGround.get(player.getEntityName());
		if (prev == null) {
			prev = new Long(0);
			onGround.put(player.getEntityName(), prev);
			return true;
		}
		else {
			long diff = millis - prev.longValue();
			ReikaJavaLibrary.pConsole(diff, FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && Keyboard.isKeyDown(Keyboard.KEY_SPACE));
			return diff > 120;
		}
	}

	private void explode(World world, EntityPlayer player) {
		player.setCurrentItemOrArmor(3, null);
		world.createExplosion(player, player.posX, player.posY, player.posZ, 2, false);
		double v = 4;
		double ang = itemRand.nextDouble()*360;
		double vx = v*Math.cos(Math.toRadians(ang));
		double vz = v*Math.sin(Math.toRadians(ang));
		player.addVelocity(vx, 1.25, vz);
		player.velocityChanged = true;
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
		if (itemID == ItemRegistry.BEDPACK.getShiftedID()) {
			Enchantment ench = ((ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance()).getDefaultEnchantment();
			if (ench != null)
				is.addEnchantment(ench, 4);
		}
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("fuel", this.getMaxFuel(is));
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir.isAvailableInCreativeInventory())
			li.add(is);
	}
	/*
	@Override
	public String getArmorTexture(ItemStack is, Entity e, int slot, String nulll) {
		ItemRegistry i = ItemRegistry.getEntry(is);
		if (i == ItemRegistry.BEDPACK)
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_jet.png";
		if (i == ItemRegistry.JETPACK)
			return "/Reika/RotaryCraft/Textures/Misc/jet.png";
		return "";
	}*/

	@Override
	public Fluid getFuelType(ItemStack is) {
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
	public int addFuel(ItemStack is, Fluid f, int amt) {
		if (f == null || !f.equals(FluidRegistry.getFluid("rc ethanol")))
			return 0;
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
				is.stackTagCompound.setInteger("fuel", cap);
				return cap-cur;
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

	@Override
	public boolean providesProtection() {
		return itemID == ItemRegistry.BEDPACK.getShiftedID();
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier() {
		return 1;
	}
}
