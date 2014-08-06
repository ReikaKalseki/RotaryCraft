/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Auxiliary.KeyWatcher;
import Reika.DragonAPI.Auxiliary.KeyWatcher.Key;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetPack extends ItemRotaryArmor implements Fillable {

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

	public Fluid getFuelType() {
		return ConfigRegistry.JETFUELPACK.getState() ? FluidRegistry.getFluid("jet fuel") : FluidRegistry.getFluid("rc ethanol");
	}
	/*
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

		boolean hover = false;
		boolean fwd = false;

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && Keyboard.isKeyDown(ReikaKeyHelper.getForwardKey())) {
			//power += 100;
			fwd = true;
		}

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && Keyboard.isKeyDown(ReikaKeyHelper.getSneakKey())) {
			hover = true;
		}

		player.playSound(SoundRegistry.JETPACK.getPlayableReference(), 0.5F, 1);

		int id = PacketRegistry.JETPACK.getMinValue();
		if (hover && fwd)
			ReikaPacketHelper.sendFloatPacket(RotaryCraft.packetChannel, id+2, player.worldObj, px, py, pz, power);
		else if (hover)
			ReikaPacketHelper.sendFloatPacket(RotaryCraft.packetChannel, id+1, player.worldObj, px, py, pz, power);
		else
			ReikaPacketHelper.sendFloatPacket(RotaryCraft.packetChannel, id, player.worldObj, px, py, pz, power);

		//player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);

		return true;
	}*/

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack is)
	{
		boolean flying = this.useJetpack(player, is);

		if (ConfigRegistry.EXPLODEPACK.getState()) {
			if (this.getCurrentFillLevel(is) > 0) {
				if (player.handleLavaMovement() && world.difficultySetting != 0) {
					this.explode(world, player);
				}
				else if (player.isBurning() && world.difficultySetting > 1 && flying) {
					this.explode(world, player);
				}
			}
		}
	}

	private boolean useJetpack(EntityPlayer ep, ItemStack is) {
		boolean isFlying = KeyWatcher.instance.isKeyDown(ep, Key.JUMP);
		boolean hoverMode = isFlying && KeyWatcher.instance.isKeyDown(ep, Key.SNEAK);
		float maxSpeed = 1.25F;
		double hspeed = ReikaMathLibrary.py3d(ep.motionX, 0, ep.motionZ);
		float thrust = hoverMode ? 0.1F : 0.2F;

		boolean canFly = !hoverMode || (!ep.onGround && ep.motionY < 0);
		if (isFlying && canFly) {
			if (!ep.worldObj.isRemote && !ep.capabilities.isCreativeMode) {
				if (ep.worldObj.getTotalWorldTime()%2 == 0)
					this.use(is, (hoverMode ? 2 : 1)*this.getFuelUsageMultiplier());
			}

			if (this.getFuel(is) > 0) {
				if (hoverMode) {
					if (ep.motionY > 0)
						ep.motionY = Math.max(ep.motionY*0.75, 0);
					else
						ep.motionY = Math.min(ep.motionY+0.15, 0);
				}
				else {
					double deltav = ep.motionY > 0 ? Math.min(0.2, Math.max(0.05, (maxSpeed-ep.motionY)*0.25)) : 0.2;
					ep.motionY = Math.min(ep.motionY+deltav, maxSpeed);
				}

				if (KeyWatcher.instance.isKeyDown(ep, Key.FOWARD)) {
					ep.moveFlying(0, thrust, thrust);
					if (ep.worldObj.getTotalWorldTime()%2 == 0 && !ep.capabilities.isCreativeMode)
						this.use(is, this.getFuelUsageMultiplier());
				}
				if (KeyWatcher.instance.isKeyDown(ep, Key.BACK)) {
					ep.moveFlying(0, -thrust, thrust);
					if (ep.worldObj.getTotalWorldTime()%2 == 0 && !ep.capabilities.isCreativeMode)
						this.use(is, this.getFuelUsageMultiplier());
				}
				if (KeyWatcher.instance.isKeyDown(ep, Key.LEFT)) {
					ep.moveFlying(thrust, 0, thrust);
					if (ep.worldObj.getTotalWorldTime()%2 == 0 && !ep.capabilities.isCreativeMode)
						this.use(is, this.getFuelUsageMultiplier());
				}
				if (KeyWatcher.instance.isKeyDown(ep, Key.RIGHT)) {
					ep.moveFlying(-thrust, 0, thrust);
					if (ep.worldObj.getTotalWorldTime()%2 == 0 && !ep.capabilities.isCreativeMode)
						this.use(is, this.getFuelUsageMultiplier());
				}

				if (!ep.worldObj.isRemote) {
					ep.fallDistance = -2;
					if (ConfigRegistry.KICKFLYING.getState()) {
						if (ep instanceof EntityPlayerMP) {
							((EntityPlayerMP)ep).playerNetServerHandler.ticksForFloatKick = 0;
						}
					}
				}

				float pitch = 1+0.5F*(float)Math.sin((ep.worldObj.getWorldTime()*2)%360);
				SoundRegistry.JETPACK.playSound(ep.worldObj, ep.posX, ep.posY, ep.posZ, 0.75F, pitch);
			}
		}
		return isFlying;
	}

	private int getFuelUsageMultiplier() {
		return itemID == ItemRegistry.BEDPACK.getShiftedID() ? 2 : 1;
	}

	private void explode(World world, EntityPlayer player) {
		ItemStack to = itemID == ItemRegistry.BEDPACK.getShiftedID() ? ItemRegistry.BEDCHEST.getEnchantedStack() : null;
		player.setCurrentItemOrArmor(3, to);
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
			HashMap<Enchantment, Integer> ench = ((ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance()).getDefaultEnchantments();
			ReikaEnchantmentHelper.applyEnchantments(is, ench);
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
	public boolean isValidFluid(Fluid f, ItemStack is) {
		return f.equals(this.getFuelType());
	}

	@Override
	public int getCapacity(ItemStack is) {
		return this.getMaxFuel(is);
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return this.getFuel(is);
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		if (f == null || !f.equals(this.getFuelType()))
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
	public double getDamageMultiplier(DamageSource src) {
		ItemBedrockArmor arm = (ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance();
		return itemID == ItemRegistry.BEDPACK.getShiftedID() ? arm.getDamageMultiplier(src) : 1;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

	@Override
	public Fluid getCurrentFluid(ItemStack is) {
		return this.getCurrentFillLevel(is) > 0 ? this.getFuelType() : null;
	}
}
