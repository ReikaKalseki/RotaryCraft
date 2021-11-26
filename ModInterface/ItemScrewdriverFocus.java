package Reika.RotaryCraft.ModInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.ClassReparenter.Reparent;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;

@Reparent(value = {"thaumcraft.api.wands.ItemFocusBasic", "net.minecraft.item.Item"})
public class ItemScrewdriverFocus extends ItemFocusBasic {

	public ItemScrewdriverFocus(int idx) {
		this.setCreativeTab(RotaryCraft.tabRotaryTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString() {
		return "rotarycraft:screw-focus";
	}

	@Override
	@ModDependent(ModList.THAUMCRAFT)
	public AspectList getVisCost(ItemStack focusStack) {
		return new AspectList().add(Aspect.ORDER, 1);
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack wand, World world, EntityPlayer player, MovingObjectPosition mov) {
		if (mov == null)
			return null;
		ItemRegistry.SCREWDRIVER.getItemInstance().onItemUse(ItemRegistry.SCREWDRIVER.getStackOf(), player, world, mov.blockX, mov.blockY, mov.blockZ, mov.sideHit, (float)mov.hitVec.xCoord, (float)mov.hitVec.yCoord, (float)mov.hitVec.zCoord);
		return null;
	}

	@Override
	public EnumRarity getRarity(ItemStack focusstack) {
		return EnumRarity.uncommon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int par1) {
		return itemIcon;
	}

	/**
	 * What color will the focus orb be rendered on the held wand
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getFocusColor(ItemStack focusstack) {
		return 0xCACBF2;
	}

	/**
	 * Does the focus have ornamentation like the focus of the nine hells. Ornamentation is a standard icon rendered in a cross around the focus
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getOrnament(ItemStack focusstack) {
		return null;
	}

	/**
	 * An icon to be rendered inside the focus itself
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
		return null;
	}

}
