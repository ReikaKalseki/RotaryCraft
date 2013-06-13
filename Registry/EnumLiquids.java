package Reika.RotaryCraft.Registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.RegistrationException;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum EnumLiquids {

	WATER(Item.bucketWater.itemID),
	LAVA(Item.bucketLava.itemID),
	LUBRICANT(ItemStacks.lubebucket.itemID, ItemStacks.lubebucket.getItemDamage()),
	JETFUEL(ItemStacks.fuelbucket.itemID, ItemStacks.fuelbucket.getItemDamage());
	//ETHANOL(ItemStacks.ethanolbucket.itemID, ItemStacks.ethanolbucket.getItemDamage());

	public static final EnumLiquids[] liquidList = EnumLiquids.values();

	private int liquidID;
	private int liquidMeta;

	private EnumLiquids(int id) {
		liquidID = id;
		liquidMeta = -1;
	}

	private EnumLiquids(int id, int meta) {
		liquidID = id;
		liquidMeta = meta;
	}

	public boolean isMetadata() {
		return liquidMeta > -1;
	}

	public static EnumLiquids getLiquidFromIDAndMetadata(int id, int meta) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == id && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == meta))
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid ID "+id+" and metadata "+meta+"!");
	}

	public int getLiquidBlockID() {
		if (this == WATER)
			return 9;
		if (this == LAVA)
			return 11;
		throw new RegistrationException(RotaryCraft.instance, "Liquid "+this+" is not registered to have a block form and yet was called!");
	}

	public static EnumLiquids getLiquidFromBlock(int block) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].getLiquidBlockID() == block)
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid for block "+block+"!");
	}

	public static boolean hasLiquid(EnumLiquids liq, ItemStack[] inv) {
		if (liq.isMetadata())
			return ReikaInventoryHelper.checkForItemStack(liq.liquidID, liq.liquidMeta, inv);
		else
			return ReikaInventoryHelper.checkForItem(liq.liquidID, inv);
	}

	public static boolean isLiquidItem(ItemStack is) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == is.itemID && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == is.getItemDamage()))
				return true;
		}
		return false;
	}
}
