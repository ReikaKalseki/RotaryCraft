/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemRotaryArmor extends ItemArmor implements IndexedItemSprites, ISpecialArmor {

	private int index;

	public ItemRotaryArmor(ArmorMaterial par2, int par3, int par4, int ind) {
		super(par2, par3, par4);
		maxStackSize = 1;
		this.setIndex(ind);
		if (!RotaryCraft.instance.isLocked())
			this.setCreativeTab(RotaryCraft.tabRotaryTools);
		else
			this.setCreativeTab(null);
	}

	public abstract boolean providesProtection();

	public abstract boolean canBeDamaged();

	@Override
	public final boolean isValidArmor(ItemStack stack, int type, Entity e) {
		return armorType == type;
	}

	@Override
	public final ItemStack onItemRightClick(ItemStack is, World par2World, EntityPlayer par3Entity) {
		return is;
	}

	@Override
	public abstract void onArmorTick(World world, EntityPlayer ep, ItemStack is);

	public int getItemSpriteIndex(ItemStack item) {
		return index;
	}

	public void setIndex(int a) {
		index = a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IIconRegister ico) {}


	@Override
	public final IIcon getIconFromDamage(int dmg) {
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Items.iron_chestplate.getIconFromDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final ModelBiped getArmorModel(EntityLivingBase elb, ItemStack is, int armorSlot)
	{
		return null;//ClientProxy.getArmorRenderer(ItemRegistry.getEntry(is));
	}

	@Override
	public final String getArmorTexture(ItemStack is, Entity entity, int slot, String type) {
		ItemRegistry item = ItemRegistry.getEntry(is);
		String sg = ClientProxy.getArmorTextureAsset(item);
		return sg;
	}

	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (this.providesProtection()) {
			return damageReduceAmount;
		}
		else {
			return 0;
		}
	}

	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource src, double damage, int slot) {
		double protection = this.providesProtection() && this.isVulnerableTo(src) ? damageReduceAmount/100D/this.getDamageMultiplier(src) : 0;
		ArmorProperties prop = new ArmorProperties(Integer.MAX_VALUE, protection, Integer.MAX_VALUE);
		return prop;
	}

	public abstract double getDamageMultiplier(DamageSource src);

	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if (this.canBeDamaged() && this.isVulnerableTo(source)) {
			stack.damageItem(damage, entity);
		}
	}

	public boolean isVulnerableTo(DamageSource src) {
		return !src.isUnblockable();
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		ItemRegistry ir = ItemRegistry.getEntry(is);
		return ir.hasMultiValuedName() ? ir.getMultiValuedName(is.getItemDamage()) : ir.getBasicName();
	}

}