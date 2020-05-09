package Reika.RotaryCraft.Registry;

import java.util.Locale;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum GearboxTypes {

	WOOD(1, MaterialRegistry.WOOD),
	STONE(2, MaterialRegistry.STONE),
	STEEL(0, MaterialRegistry.STEEL),
	TUNGSTEN(7, MaterialRegistry.TUNGSTEN),
	DIAMOND(3, MaterialRegistry.DIAMOND),
	BEDROCK(4, MaterialRegistry.BEDROCK),
	LIVINGWOOD(5, MaterialRegistry.WOOD, ModList.BOTANIA),
	LIVINGROCK(6, MaterialRegistry.STONE, ModList.BOTANIA);

	public final int metaOffset;
	public final MaterialRegistry material;

	private final ModList dependency;

	public static final GearboxTypes[] typeList = values();

	private GearboxTypes(int row, MaterialRegistry mat) {
		this(row, mat, null);
	}

	private GearboxTypes(int row, MaterialRegistry mat, ModList mod) {
		metaOffset = row;
		material = mat;
		dependency = mod;
	}

	public boolean isLoadable() {
		return dependency == null || dependency.isLoaded();
	}

	public boolean needsLubricant() {
		return this != WOOD && this.isDamageableGear();
	}

	public boolean consumesLubricant(int omega) {
		return this.needsLubricant() && this != DIAMOND;
	}

	public boolean generatesHeat(int omega, int Tamb) {
		switch(this) {
			case WOOD:
				return true;
			case LIVINGWOOD:
				return omega >= 2048 || Tamb >= 100;
			case STONE:
				return omega >= 8192;
			default:
				return false;
		}
	}

	public boolean takesTemperatureDamage() {
		return this == WOOD || this == STONE;
	}

	public boolean isDamageableGear() {
		return !material.isInfiniteStrength();
	}

	public String getBaseGearboxTexture() {
		String tex = "geartex";
		switch(this) {
			case BEDROCK:
				tex = tex+"b";
				break;
			case DIAMOND:
				tex = tex+"d";
				break;
			case STONE:
				tex = tex+"s";
				break;
			case TUNGSTEN:
				tex = tex+"t";
				break;
			case WOOD:
				tex = tex+"w";
				break;
			case LIVINGROCK:
				tex = tex+"s_living";
				break;
			case LIVINGWOOD:
				tex = tex+"w_living";
				break;
			default:
				break;
		}
		return tex+".png";
	}

	public ItemStack getGearItem() {
		return ItemRegistry.GEARCRAFT.getStackOfMetadata(metaOffset*16+1);
	}

	public ItemStack getGearUnitItem(int ratio) {
		int log = ReikaMathLibrary.logbase2(ratio);
		return ItemRegistry.GEARCRAFT.getStackOfMetadata(metaOffset*16+log+1);
	}

	public ItemStack getShaftUnitItem() {
		if (this == WOOD)
			return new ItemStack(Items.stick);
		if (this == TUNGSTEN)
			return STEEL.getShaftUnitItem();
		return ItemRegistry.GEARCRAFT.getStackOfMetadata(metaOffset*16);
	}

	public Object getMountItem() {
		if (this == WOOD)
			return "plankWood";
		if (this == STONE)
			return ReikaItemHelper.stoneSlab.asItemStack();
		return ItemStacks.mount;
	}

	public int getMaxLubricant() {
		switch(this) {
			case BEDROCK:
				return 0;
			case DIAMOND:
				return 1000;
			case STEEL:
			case TUNGSTEN:
				return 24000;
			case STONE:
			case LIVINGROCK:
				return 8000;
			case WOOD:
			case LIVINGWOOD:
				return 0;//3000;
		}
		return 0;
	}

	public ItemStack getGearboxItem(int ratio) {
		ItemStack is = MachineRegistry.GEARBOX.getCraftedMetadataProduct(ReikaMathLibrary.logbase2(ratio)-1);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("type", this.name());
		return is;
	}

	public String getLocalizedGearboxName(int ratio) {
		return StatCollector.translateToLocal("material."+this.name().toLowerCase(Locale.ENGLISH))+" "+ratio+":1 "+MachineRegistry.GEARBOX.getName();
	}

	public static GearboxTypes getMaterialFromGearboxItem(ItemStack is) {
		if (is.stackTagCompound != null && is.stackTagCompound.hasKey("type"))
			return GearboxTypes.valueOf(is.stackTagCompound.getString("type"));
		return getFromMaterial(MaterialRegistry.matList[is.getItemDamage()]);
	}

	public static GearboxTypes getMaterialFromCraftingItem(ItemStack is) {
		int idx = is.getItemDamage()/16;
		for (GearboxTypes g : typeList) {
			if (g.metaOffset == idx)
				return g;
		}
		return null;
	}

	public static GearboxTypes getFromMaterial(MaterialRegistry mat) {
		switch(mat) {
			case BEDROCK:
				return GearboxTypes.BEDROCK;
			case DIAMOND:
				return GearboxTypes.DIAMOND;
			case STEEL:
				return GearboxTypes.STEEL;
			case STONE:
				return GearboxTypes.STONE;
			case WOOD:
				return GearboxTypes.WOOD;
			case TUNGSTEN:
				return GearboxTypes.TUNGSTEN;
		}
		return null;
	}

	public static int getRatioFromPartItem(ItemStack is) {
		if (is == null)
			return 0;
		if (is.getItem() == Items.stick)
			return 1;
		if (!ItemRegistry.GEARCRAFT.matchItem(is))
			return 0;
		int meta = is.getItemDamage()%16;
		switch(meta) {
			case 0:
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 4;
			case 4:
				return 8;
			case 5:
				return 16;
		}
		return 0;
	}

	public double getOmegaForRotFailure(int omega, int omegain) {
		return ReikaMathLibrary.doubpow(Math.max(omega, omegain), material.getSpeedForceExponent());
	}

	public static enum GearPart {
		SHAFT(),
		GEAR(),
		UNIT2(),
		UNIT4(),
		UNIT8(),
		UNIT16();

		public static final GearPart[] list = values();

		public int getMetaOffset() {
			return this.ordinal();
		}

		public String getLocalizedName(GearboxTypes material) {
			String s = "";
			switch(this) {
				case SHAFT:
					s = "crafting.shaft";
					break;
				case GEAR:
					s = "crafting.gear";
					break;
				case UNIT2:
					s = "crafting.gear2x";
					break;
				case UNIT4:
					s = "crafting.gear4x";
					break;
				case UNIT8:
					s = "crafting.gear8x";
					break;
				case UNIT16:
					s = "crafting.gear16x";
					break;
			}
			return StatCollector.translateToLocal("material."+material.name().toLowerCase(Locale.ENGLISH))+" "+StatCollector.translateToLocal(s);
		}
	}

}
