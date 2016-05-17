/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBasic extends Item implements IndexedItemSprites {

	protected Random par5Random = new Random();

	private int index;

	public ItemBasic(int tex) {
		maxStackSize = 64;
		this.setCreativeTab(this.isAvailableInCreativeMode() ? this.getCreativePage() : null);
		this.setIndex(tex);
	}

	public ItemBasic(int ID, int tex, int max) {
		maxStackSize = max;
		if (max == 1);
		hasSubtypes = true;
		if (this.isAvailableInCreativeMode())
			this.setCreativeTab(RotaryCraft.tabRotaryItems);
		else
			this.setCreativeTab(null);
		this.setIndex(tex);
	}

	protected CreativeTabs getCreativePage() {
		return RotaryCraft.tabRotaryItems;
	}

	private boolean isAvailableInCreativeMode() {
		if (RotaryCraft.instance.isLocked())
			return false;
		return true;
	}

	public int getItemSpriteIndex(ItemStack item) {
		return index;
	}

	protected final int getRootIndex() {
		return index;
	}

	public void setIndex(int a) {
		index = a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IIconRegister ico) {}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer ep) {
		this.checkAchievements(ep, is);
	}

	private void checkAchievements(EntityPlayer player, ItemStack item) {
		if (ReikaItemHelper.matchStacks(item, MachineRegistry.RAILGUN.getCraftedProduct()))
			RotaryAchievements.MAKERAILGUN.triggerAchievement(player);
		//if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.engineitems.itemID, 1, EngineType.JET.ordinal())))
		//	RotaryAchievements.MAKEJET.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, MachineRegistry.WORKTABLE.getCraftedProduct()))
			RotaryAchievements.WORKTABLE.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(ItemRegistry.SHAFT.getItemInstance(), 1, MaterialRegistry.STEEL.ordinal())))
			RotaryAchievements.STEELSHAFT.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, ItemStacks.pcb))
			RotaryAchievements.PCB.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(ItemRegistry.SHAFT.getItemInstance(), 1, MaterialRegistry.BEDROCK.ordinal())))
			RotaryAchievements.BEDROCKSHAFT.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(ItemRegistry.ADVGEAR.getItemInstance(), 1, 1)))
			RotaryAchievements.CVT.triggerAchievement(player);
		//if (ItemRegistry.isRegistered(item) && ItemRegistry.getEntry(item).isBedrockTool())
		//	RotaryAchievements.BEDROCKTOOLS.triggerAchievement(player);
		for (int i = 0; i < 4; i++) {
			if (ReikaItemHelper.matchStacks(item, new ItemStack(ItemRegistry.GEARBOX.getItemInstance(), 1, MaterialRegistry.DIAMOND.ordinal()+i*5)))
				RotaryAchievements.DIAMONDGEARS.triggerAchievement(player);
		}
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		int index = ItemRegistry.getEntry(is).getTextureSheet();
		String s = index > 0 ? String.valueOf(index) : "";
		return "/Reika/RotaryCraft/Textures/Items/items"+s+".png";
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		ItemRegistry ir = ItemRegistry.getEntry(is);
		return ir.hasMultiValuedName() ? ir.getMultiValuedName(is.getItemDamage()) : ir.getBasicName();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof UpgradeableMachine) {
			UpgradeableMachine u = (UpgradeableMachine)te;
			if (u.canUpgradeWith(is)) {
				u.upgrade(is);
				if (te instanceof TileEntityBase)
					((TileEntityBase)te).syncAllData(true);
				if (!ep.capabilities.isCreativeMode)
					is.stackSize--;
				return true;
			}
		}
		return false;
	}
}
