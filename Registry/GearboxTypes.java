package Reika.RotaryCraft.Registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum GearboxTypes {

	WOOD(1, MaterialRegistry.WOOD),
	STONE(2, MaterialRegistry.STONE),
	STEEL(0, MaterialRegistry.STEEL),
	TUNGSTEN(7, MaterialRegistry.TUNGSTEN),
	DIAMOND(3, MaterialRegistry.DIAMOND),
	BEDROCK(4, MaterialRegistry.BEDROCK),
	LIVINGWOOD(5, MaterialRegistry.WOOD),
	LIVINGROCK(6, MaterialRegistry.STONE);

	public final int spriteRow;
	public final MaterialRegistry material;

	private GearboxTypes(int row, MaterialRegistry mat) {
		spriteRow = row;
		material = mat;
	}

	public boolean needsLubricant() {
		return this != WOOD && this.isDamageableGear();
	}

	public boolean consumesLubricant() {
		return this.needsLubricant() && this != DIAMOND;
	}

	public boolean takesTemperatureDamage() {
		return this == WOOD || this == STONE;
	}

	public boolean isDamageableGear() {
		return !material.isInfiniteStrength();
	}

	public String getBaseGearboxTexture() {

	}

	public String getBaseShaftTexture() {

	}

	public ItemStack getShaftItem() {
		return MachineRegistry.SHAFT.getCraftedMetadataProduct(this.INDEX());
	}

	public ItemStack getGearboxItem(int ratio) {
		int type = this.INDEX();
		ratio = (int)ReikaMathLibrary.logbase(ratio, 2)-1;
		return MachineRegistry.GEARBOX.getCraftedMetadataProduct(5*ratio+type);
	}

	public ItemStack getGearItem() {
		switch(this) {
			case BEDROCK:
				return ItemStacks.bedrockgear;
			case DIAMOND:
				return ItemStacks.diamondgear;
			case STEEL:
				return ItemStacks.steelgear;
			case STONE:
				return ItemStacks.stonegear;
			case WOOD:
				return ItemStacks.woodgear;
			case LIVINGROCK:
				return ItemStacks.livingrockgear;
			case LIVINGWOOD:
				return ItemStacks.livingwoodgear;
			case TUNGSTEN:
				return ItemStacks.tungstengear;
		}
		return null;
	}

	public ItemStack getGearUnitItem(int ratio) {
		int log = ReikaMathLibrary.logbase2(ratio)-1;
		if (this == STEEL) {
			return ItemRegistry.SHAFTCRAFT.getStackOfMetadata(5+log);
		}
		else {
			int o = this == BEDROCK || this == DIAMOND ? this.INDEX()-1 : this.INDEX();
			return ItemRegistry.GEARUNITS.getStackOfMetadata(log+o*4);
		}
	}

	public ItemStack getShaftUnitItem() {
		switch(this) {
			case BEDROCK:
				return ItemStacks.bedrockshaft;
			case DIAMOND:
				return ItemStacks.diamondshaft;
			case STEEL:
				return ItemStacks.shaftitem;
			case STONE:
				return ItemStacks.stonerod;
			case WOOD:
				return new ItemStack(Items.stick);
			case LIVINGROCK:
				return ItemStacks.livingrockrod;
			case LIVINGWOOD:
				return ItemStacks.livingwoodrod;
			case TUNGSTEN:
				return ItemStacks.shaftitem;//ItemStacks.tungstenshaft;
		}
		return null;
	}

}
