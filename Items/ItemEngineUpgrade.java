package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEngineUpgrade extends ItemRotaryTool {

	public ItemEngineUpgrade(int ID, int index) {
		super(ID, index);
		hasSubtypes = true;
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		return super.getItemSpriteIndex(is)+is.getItemDamage();
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		return super.getUnlocalizedName(is)+"."+is.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof UpgradeableMachine) {
			UpgradeableMachine u = (UpgradeableMachine)te;
			if (u.canUpgradeWith(is)) {
				u.upgrade();
				if (!ep.capabilities.isCreativeMode)
					is.stackSize--;
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < Upgrades.values().length; i++) {
			ItemStack is = new ItemStack(par1, 1, i);
			par3List.add(is);
		}
	}

	public static enum Upgrades {

		PERFORMANCE("Performance Engine Upgrade"),
		MAGNETOSTATIC1("Magnetostatic Tier 1 Upgrade"), //Made with ethanol
		MAGNETOSTATIC2("Magnetostatic Tier 2 Upgrade"), //Made in magnetizer
		MAGNETOSTATIC3("Magnetostatic Tier 3 Upgrade"), //Made with pulse jet ingot
		MAGNETOSTATIC4("Magnetostatic Tier 4 Upgrade"), //Made with 4MW extractor product
		MAGNETOSTATIC5("Magnetostatic Tier 5 Upgrade"); //Made with bedrock

		public final String desc;

		private Upgrades(String d) {
			desc = d;
		}
	}

}
