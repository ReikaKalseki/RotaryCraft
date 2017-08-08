/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.ChromatiCraft.API.RitualAPI;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher;
import Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher.Key;
import Reika.DragonAPI.Interfaces.Item.MultiLayerItemSprite;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Interfaces.Fillable;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelArmor;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IArmorApiarist;

@Strippable(value={"forestry.api.apiculture.IArmorApiarist"})
public class ItemJetPack extends ItemRotaryArmor implements Fillable, MultiLayerItemSprite, IArmorApiarist {

	public ItemJetPack(ArmorMaterial mat, int tex, int render) {
		super(mat, render, 1, tex);
	}

	public static enum PackUpgrades {
		WING("Winged"),
		JET("Thrust Boost"),
		COOLING("Fin-Cooled");

		public final String label;

		private static final PackUpgrades[] list = values();

		private PackUpgrades(String s) {
			label = s;
		}

		public boolean existsOn(ItemStack is) {
			return is.stackTagCompound != null && is.stackTagCompound.getBoolean(this.getNBT());
		}

		private String getNBT() {
			return this.name().toLowerCase(Locale.ENGLISH);
		}

		public void enable(ItemStack is, boolean set) {
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setBoolean(this.getNBT(), set);
			if (this == WING)
				is.stackTagCompound.setBoolean("wingon", true);
		}
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

		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		this.setFuel(is, this.getCurrentFluid(is), newFuel);
	}
	/*
	public Fluid getFuelType() {
		return ConfigRegistry.JETFUELPACK.getState() ? FluidRegistry.getFluid("rc jet fuel") : FluidRegistry.getFluid("rc ethanol");
	}*/

	@Override
	protected final ItemStack onSneakClicked(ItemStack is, EntityPlayer ep) {
		if (!PackUpgrades.WING.existsOn(is))
			return is;
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setBoolean("wingon", !is.stackTagCompound.getBoolean("wingon"));
		return is;
	}

	private static final boolean wingEnabled(ItemStack is) {
		return PackUpgrades.WING.existsOn(is) && (is.stackTagCompound == null || is.stackTagCompound.getBoolean("wingon"));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack is) {
		boolean flying = this.useJetpack(player, is);
		boolean fuel = this.getCurrentFillLevel(is) > 0;

		if (!PackUpgrades.COOLING.existsOn(is)) {
			if (fuel) {
				if (player.handleLavaMovement() && world.difficultySetting != EnumDifficulty.PEACEFUL) {
					this.explode(world, player);
				}
				else if (player.isBurning() && world.difficultySetting.ordinal() > 1 && flying) {
					this.explode(world, player);
				}
			}
		}

		if (flying && world.difficultySetting != EnumDifficulty.PEACEFUL && itemRand.nextInt(4) == 0) {
			int x = MathHelper.floor_double(player.posX);
			int y = MathHelper.floor_double(player.posY);
			int z = MathHelper.floor_double(player.posZ);
			int dx = ReikaRandomHelper.getRandomPlusMinus(x, 1);
			int dz = ReikaRandomHelper.getRandomPlusMinus(z, 1);
			int dy = ReikaRandomHelper.getRandomBetween(y-2, y);
			Fluid f = ReikaWorldHelper.getFluid(world, dx, dy, dz);
			if (f != null && ReikaFluidHelper.isFlammable(f)) {
				ReikaWorldHelper.ignite(world, dx, dy, dz);
			}
		}

		player.getEntityData().setBoolean("jetpack", flying);
	}

	private boolean useJetpack(EntityPlayer ep, ItemStack is) {
		boolean isFlying = KeyWatcher.instance.isKeyDown(ep, Key.JUMP);
		boolean hoverMode = isFlying && ep.isSneaking();
		boolean jetbonus = !ConfigRegistry.JETFUELPACK.getState() && this.isJetFueled(is);
		boolean horiz = KeyWatcher.instance.isKeyDown(ep, Key.FOWARD) || KeyWatcher.instance.isKeyDown(ep, Key.BACK);
		horiz = horiz || KeyWatcher.instance.isKeyDown(ep, Key.LEFT) || KeyWatcher.instance.isKeyDown(ep, Key.RIGHT);
		float maxSpeed = jetbonus ? 3 : 1.25F;
		double hspeed = ReikaMathLibrary.py3d(ep.motionX, 0, ep.motionZ);
		boolean winged = this.wingEnabled(is);
		boolean propel = PackUpgrades.JET.existsOn(is) && this.isJetFueled(is);
		boolean floatmode = !hoverMode;
		float thrust = winged ? 0.15F : hoverMode ? 0.05F : 0.1F;
		if (propel)
			thrust *= hoverMode ? 2 : 4;
		if (jetbonus)
			thrust *= 1.25F;
		if (ep.ridingEntity != null)
			thrust *= 1.25F;

		boolean canFly = !hoverMode || (!ep.onGround && ep.motionY < 0);
		if (isFlying && canFly) {
			if (!ep.worldObj.isRemote && !ep.capabilities.isCreativeMode && !ep.capabilities.isFlying) {
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
					if (jetbonus && !horiz) {
						deltav *= 1.5;
					}
					if (ep.ridingEntity != null)
						deltav *= 1.5;
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
							((EntityPlayerMP)ep).playerNetServerHandler.floatingTickCount = 0;
						}
					}
				}

				float pitch = 1+0.5F*(float)Math.sin((ep.worldObj.getWorldTime()*2)%360);
				SoundRegistry.JETPACK.playSound(ep.worldObj, ep.posX, ep.posY, ep.posZ, 0.75F, pitch);
				if (propel) {
					SoundRegistry.SHORTJET.playSound(ep.worldObj, ep.posX, ep.posY, ep.posZ, 0.15F, 1F);
				}
			}
		}

		if (ep.motionY < 0 && winged && floatmode && !ep.isPlayerSleeping()) {
			if (!ModList.CHROMATICRAFT.isLoaded() || !RitualAPI.isPlayerUndergoingRitual(ep)) {
				boolean sneak = ep.isSneaking();
				double ang = Math.cos(Math.toRadians(ep.rotationPitch));
				double d = ep.motionY <= -2 ? 0.0625 : ep.motionY <= -1 ? 0.125 : ep.motionY <= -0.5 ? 0.25 : 0.5; //gives curve
				if (sneak)
					d *= 0.125; //was 0.25
				double fac = 1-(d*ang);
				ep.motionY *= fac;
				fac *= sneak ? 0.999 : 0.9;
				ep.fallDistance *= fac;
				//double diff = 0.5*ang*ep.motionY;
				//double maxdecel = jetbonus ? 0.0625 : 0.03125;
				//ep.motionY -= Math.min(diff, maxdecel);
				double dv = /*sneak ? 0.15 :*/ 0.05;
				double vh = ep.onGround ? 0 : dv*ang;
				double vx = Math.cos(Math.toRadians(ep.rotationYawHead + 90))*vh;
				double vz = Math.sin(Math.toRadians(ep.rotationYawHead + 90))*vh;
				ep.motionX += vx;
				ep.motionZ += vz;
			}
		}

		if (isFlying && ep.ridingEntity != null) {
			ep.ridingEntity.motionX = ep.motionX;
			ep.ridingEntity.motionY = ep.motionY;
			ep.ridingEntity.motionZ = ep.motionZ;
			ep.ridingEntity.fallDistance = ep.fallDistance;
		}

		return isFlying;
	}

	public ItemStack getMaterial() {
		return this.isBedrock() ? ItemStacks.bedingot : ItemStacks.steelingot;
	}

	public boolean isBedrock() {
		return this == ItemRegistry.BEDPACK.getItemInstance();
	}

	public boolean isSteel() {
		return this == ItemRegistry.STEELPACK.getItemInstance();
	}

	private int getFuelUsageMultiplier() {
		return this.isBedrock() ? 2 : 1;
	}

	private void explode(World world, EntityPlayer player) {
		ItemStack to = this.isBedrock() ? ItemRegistry.BEDCHEST.getEnchantedStack() : this.isSteel() ? ItemRegistry.STEELCHEST.getStackOf() : null;
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
		for (int i = 0; i < PackUpgrades.list.length; i++) {
			PackUpgrades pack = PackUpgrades.list[i];
			if (pack.existsOn(is)) {
				li.add(pack.label);
				if (pack == PackUpgrades.WING && !this.wingEnabled(is))
					li.add(EnumChatFormatting.RED+"[Wing Disabled]");
			}
		}
		int ch = is.stackTagCompound != null ? is.stackTagCompound.getInteger("fuel") : 0;
		li.add(ch > 0 ? String.format("Fuel: %d mB of %s", ch, this.getCurrentFluid(is).getLocalizedName()) : "No Fuel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		if (this.isBedrock()) {
			HashMap<Enchantment, Integer> ench = ((ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance()).getDefaultEnchantments();
			ReikaEnchantmentHelper.applyEnchantments(is, ench);
		}
		ItemStack is2 = is.copy();
		ItemStack is3 = is.copy();
		Fluid f = ConfigRegistry.JETFUELPACK.getState() ? FluidRegistry.getFluid("rc jet fuel") : FluidRegistry.getFluid("rc ethanol");
		this.setFuel(is, f, this.getMaxFuel(is));
		this.setFuel(is3, FluidRegistry.getFluid("rc jet fuel"), this.getMaxFuel(is3));
		ItemStack is5 = is3.copy();
		PackUpgrades.WING.enable(is3, true);
		for (int i = 0; i < PackUpgrades.list.length; i++) {
			PackUpgrades pack = PackUpgrades.list[i];
			pack.enable(is3, true);
		}
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir.isAvailableInCreativeInventory()) {
			li.add(is2);
			li.add(is);
			li.add(is5);
			li.add(is3);
		}
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
		Fluid f2 = this.getCurrentFluid(is);
		if (f2 != null && !f.equals(f2))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc jet fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rocket fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return !ConfigRegistry.JETFUELPACK.getState();
		return false;
	}

	@Override
	public int getCapacity(ItemStack is) {
		return this.getMaxFuel(is);
	}

	@Override
	public int getCurrentFillLevel(ItemStack is) {
		return this.getFuel(is);
	}

	private void setFuel(ItemStack is, Fluid f, int amt) {
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("fuel", amt);
		if (amt > 0) {
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, f);
		}
		else {
			ReikaNBTHelper.writeFluidToNBT(is.stackTagCompound, null);
		}
	}

	@Override
	public int addFluid(ItemStack is, Fluid f, int amt) {
		if (f == null || !this.isValidFluid(f, is))
			return 0;
		NBTTagCompound nbt = is.stackTagCompound;
		if (nbt == null) {
			is.stackTagCompound = new NBTTagCompound();
			this.setFuel(is, f, amt);
			return amt;
		}
		else {
			int cap = this.getCapacity(is);
			int cur = nbt.getInteger("fuel");
			int sum = cur+amt;
			if (sum > cap) {
				this.setFuel(is, f, cap);
				return cap-cur;
			}
			else {
				this.setFuel(is, f, sum);
				return amt;
			}
		}
	}
	/*
	@Override
	public int getItemSpriteIndex(ItemStack item) {
		ItemRegistry ir = ItemRegistry.getEntry(item);
		return ir != null ? ir.getTextureIndex() : 0;
	}
	 *//*
	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int a = this.isWinged(item) ? 32 : 0;
		return a+super.getItemSpriteIndex(item);
	}*/

	@Override
	public boolean providesProtection() {
		return this.isBedrock() || this.isSteel();
	}

	@Override
	public boolean canBeDamaged() {
		return this.isSteel();
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		if (this.isBedrock())
			return ((ItemBedrockArmor)ItemRegistry.BEDCHEST.getItemInstance()).getDamageMultiplier(src);
		else if (this.isSteel())
			return ((ItemSteelArmor)ItemRegistry.STEELCHEST.getItemInstance()).getDamageMultiplier(src);
		else
			return 1;
	}

	@Override
	public boolean isFull(ItemStack is) {
		return this.getCurrentFillLevel(is) >= this.getCapacity(is);
	}

	@Override
	public Fluid getCurrentFluid(ItemStack is) {
		if (is.stackTagCompound == null)
			return null;
		int lvl = this.getCurrentFillLevel(is);
		Fluid f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
		if (lvl > 0 && f == null) {
			this.setFuel(is, null, 0);
			return null;
		}
		return lvl > 0 ? f : null;
	}

	public boolean isJetFueled(ItemStack is) {
		Fluid f = this.getCurrentFluid(is);
		return f != null && f.equals(FluidRegistry.getFluid("rc jet fuel"));
	}

	@Override
	public int[] getIndices(ItemStack is) {
		ArrayList li = new ArrayList();
		li.add(this.getItemSpriteIndex(is));
		if (PackUpgrades.WING.existsOn(is)) {
			int w = this.isBedrock() ? 59 : this.isSteel() ? 61 : 60;
			li.add(w);
		}
		return ReikaArrayHelper.intListToArray(li);
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack item) {
		return tool.getItem() == this && this.isSteel() && ReikaItemHelper.matchStacks(item, ItemStacks.steelingot);
	}

	@Override
	public boolean protectEntity(EntityLivingBase entity, ItemStack armor, String cause, boolean doProtect) {
		ItemStack head = entity.getEquipmentInSlot(4);
		return head != null && head.getItem() instanceof IArmorApiarist && ((IArmorApiarist)head.getItem()).protectEntity(entity, head, cause, doProtect);
	}

	@Override
	@Deprecated
	public boolean protectPlayer(EntityPlayer player, ItemStack armor, String cause, boolean doProtect) {
		return this.protectEntity(player, armor, cause, doProtect);
	}

	@Override
	public final void setDamage(ItemStack stack, int damage) {

	}
}
