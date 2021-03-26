package Reika.RotaryCraft.Registry;

import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

import cpw.mods.fml.common.registry.GameRegistry;

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

	public boolean consumesLubricant() {
		return this.needsLubricant() && this != DIAMOND;
	}

	public boolean acceptsDiamondUpgrade() {
		return this.consumesLubricant() && this != STONE;
	}

	public float getLubricantConsumeRate(int omegain) {
		switch(this) {
			case STONE:
				return Math.max(1, 1+omegain/8192F);
			case TUNGSTEN:
				return Math.min(1, 0.5F+Math.max(0, 0.03125F*(ReikaMathLibrary.logbase2(omegain)-2)));
			default:
				return 1;
		}
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

	public ItemStack getPart(GearPart part) {
		return ItemRegistry.GEARCRAFT.getStackOfMetadata(metaOffset*16+part.ordinal());
	}

	public ItemStack getShaftUnitItem() {
		if (this == WOOD)
			return new ItemStack(Items.stick);
		if (this == TUNGSTEN)
			return STEEL.getShaftUnitItem();
		return ItemRegistry.GEARCRAFT.getStackOfMetadata(metaOffset*16);
	}

	public Object getBaseItem() {
		switch(this) {
			case WOOD:
				return "plankWood";
			case STONE:
				return new ItemStack(Blocks.stone);
			case LIVINGWOOD:
				return new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingwood"));
			case LIVINGROCK:
				return new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingrock"));
			case STEEL:
				return ItemStacks.steelingot;
			case TUNGSTEN:
				return ItemStacks.springtungsten;
			case DIAMOND:
				return Items.diamond;
			case BEDROCK:
				return ItemStacks.bedingot;
			default:
				return Blocks.air;
		}
	}

	public Object getMountItem() {
		switch(this) {
			case WOOD:
				return "plankWood";
			case STONE:
				return ReikaItemHelper.stoneSlab.asItemStack();
			case LIVINGWOOD:
				return new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingwood0Slab"));
			case LIVINGROCK:
				return new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingrock0Slab"));
			default:
				return ItemStacks.mount;
		}
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

	public boolean acceptsBearingUpgrade(GearboxTypes mat) {
		switch(this) {
			case BEDROCK:
			case LIVINGWOOD:
			case LIVINGROCK:
				return false;
			case WOOD:
				return false;//mat == GearboxTypes.STONE;
			default:
				return mat != WOOD && mat.material.ordinal() <= material.ordinal()+2 && !mat.isSpecialType();
		}
	}

	private boolean isSpecialType() {
		switch(this) {
			case LIVINGWOOD:
			case LIVINGROCK:
				return true;
			default:
				return false;
		}
	}

	public ItemStack getGearboxItemByIndex(int index) {
		ItemStack is = MachineRegistry.GEARBOX.getCraftedMetadataProduct(index);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("type", this.name());
		return is;
	}

	public ItemStack getGearboxItem(int ratio) {
		return this.getGearboxItemByIndex(ReikaMathLibrary.logbase2(ratio)-1);
	}

	public String getLocalizedGearboxName(int ratio) {
		return StatCollector.translateToLocal("material."+this.name().toLowerCase(Locale.ENGLISH))+" "+ratio+":1 "+MachineRegistry.GEARBOX.getName();
	}

	public static GearboxTypes getMaterialFromGearboxItem(ItemStack is) {
		if (is.stackTagCompound != null && is.stackTagCompound.hasKey("type"))
			return GearboxTypes.valueOf(is.stackTagCompound.getString("type"));

		//legacy
		int idx = is.getItemDamage()%MaterialRegistry.matList.length-1;
		if (idx >= MaterialRegistry.TUNGSTEN.ordinal())
			idx++;
		if (idx < 0) { //completely invalid
			is.setItemDamage(0);
			is.func_150996_a(Item.getItemFromBlock(Blocks.stone));
			return WOOD;
		}
		return getFromMaterial(MaterialRegistry.matList[idx]);
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
		UNIT16(),
		BEARING(),
		SHAFTCORE();

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
				case BEARING:
					s = "crafting.bearing";
					break;
				case SHAFTCORE:
					s = "crafting.shaftcore";
					break;
			}
			return StatCollector.translateToLocal("material."+material.name().toLowerCase(Locale.ENGLISH))+" "+StatCollector.translateToLocal(s);
		}

		public static GearPart getGearUnitPartItemFromRatio(int r) {
			switch(r) {
				case 2:
					return GearPart.UNIT2;
				case 4:
					return GearPart.UNIT4;
				case 8:
					return GearPart.UNIT8;
				case 16:
					return GearPart.UNIT16;
			}
			throw new IllegalArgumentException("Invalid gear ratio!");
		}

		public boolean isItemOfType(ItemStack is) {
			for (GearboxTypes type : GearboxTypes.typeList) {
				if (ReikaItemHelper.matchStacks(type.getPart(this), is))
					return true;
			}
			return false;
		}
	}

}
