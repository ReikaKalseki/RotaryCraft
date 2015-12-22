/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.ASM.InterfaceInjector.Injectable;
import Reika.DragonAPI.ModInteract.ItemHandlers.RailcraftHandler;
import Reika.RotaryCraft.RotaryCraft;

import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Injectable(value = {"minechem.api.IDecomposerControl"})
@Strippable(value = {"com.bioxx.tfc.api.Interfaces.ISize"})
public abstract class ItemBlockPlacer extends ItemBasic implements ISize {

	public ItemBlockPlacer() {
		super(0);
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		maxStackSize = 64;
		this.setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public abstract boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10);

	protected boolean checkValidBounds(ItemStack is, EntityPlayer ep, World world, int x, int y, int z) {
		return y >= 0 && y < world.provider.getHeight()-1;
	}

	protected void checkAndBreakAdjacent(World world, int x, int y, int z, EntityPlayer ep) {}

	@Override
	public int getMetadata(int damageValue) {
		return 0;
	}

	@Override
	public final String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + String.valueOf(d);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void getSubItems(Item id, CreativeTabs tab, List list);

	protected void clearBlocks(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		if (b == RailcraftHandler.getInstance().hiddenID)
			world.setBlockToAir(x, y, z);
	}

	@ModDependent(ModList.MINECHEM)
	public double getDecomposerMultiplier(ItemStack is) {
		return MathHelper.clamp_double(1-this.getBrokenFraction(is), 0, 1);
	}

	protected double getBrokenFraction(ItemStack is) {
		return 0;
	}

	@Override
	@ModDependent(ModList.TFC)
	public final EnumSize getSize(ItemStack is) {
		return EnumSize.LARGE;
	}

	@Override
	@ModDependent(ModList.TFC)
	public final EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}

	@Override
	@ModDependent(ModList.TFC)
	public final EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.MEDIUM;
	}

	@Override
	@ModDependent(ModList.TFC)
	public final boolean canStack() {
		return true;
	}
}
