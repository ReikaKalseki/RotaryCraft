package Reika.RotaryCraft.Registry;

import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;

public enum Flywheels {
	WOOD(16, 2, ReikaEngLibrary.rhowood, ReikaEngLibrary.Twood),		// rho 0.8	-> 1	-> 1
	STONE(128, 5, ReikaEngLibrary.rhorock, 0.9*ReikaEngLibrary.Tstone),		// rho 3	-> 4	-> 8
	IRON(512, 15, ReikaEngLibrary.rhoiron, 5*ReikaEngLibrary.Tiron),		// rho 8	-> 8	-> 32
	GOLD(4096, 40, ReikaEngLibrary.rhogold, ReikaEngLibrary.Tgold),	// rho 19.3	-> 32	-> 256
	TUNGSTEN(8192, 25, RotaryAux.tungstenDensity, 8*ReikaEngLibrary.Ttungsten),
	DEPLETEDU(4096, 40, ReikaEngLibrary.rhouranium, 6*ReikaEngLibrary.Turanium),
	BEDROCK(Integer.MAX_VALUE, 200, 1.75*ReikaEngLibrary.rhoiron, Double.POSITIVE_INFINITY);

	public final int maxTorque;
	public final int maxSpeed;
	public final int decayTime;
	public final double density;
	public final double tensileStrength;

	public static final Flywheels[] list = values();

	private Flywheels(int t, int dec, double rho, double str) {
		maxTorque = t;
		tensileStrength = str;
		decayTime = dec;
		density = rho;
		maxSpeed = this.getLimitLoad();
	}

	private int getLimitLoad() {
		double r = 0.75;
		double s = 100*tensileStrength;
		double frac = 2*s/(density*r*r);
		double base = Math.sqrt(frac);
		return (int)base;
	}

	public int getMinTorque() {
		return this == BEDROCK ? 16384 : maxTorque/TileEntityFlywheel.MINTORQUERATIO;
	}

	public ItemStack getCore() {
		return ItemRegistry.FLYWHEELCRAFT.getStackOfMetadata(this.ordinal());
	}

	public ItemStack getFlywheelItem() {
		return MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(this.ordinal());
	}

	public static Flywheels getMaterialFromFlywheelItem(ItemStack is) {
		int idx = is.getItemDamage();
		if (idx < 0 || idx >= list.length) { //completely invalid
			is.setItemDamage(0);
			is.func_150996_a(Item.getItemFromBlock(Blocks.stone));
			return WOOD;
		}
		return list[idx];
	}

	public String getName() {
		return StatCollector.translateToLocal("flywheel."+this.name().toLowerCase(Locale.ENGLISH));
	}

	public Object getRawMaterial() {
		switch(this) {
			case GOLD:
				return Items.gold_ingot;
			case IRON:
				return Items.iron_ingot;
			case STONE:
				return Blocks.stone;
			case TUNGSTEN:
				return ItemStacks.springtungsten;
			case WOOD:
				return "plankWood";
			case BEDROCK:
				return ItemStacks.bedingot;
			case DEPLETEDU:
				return "depletedUranium";
		}
		return null;
	}

	public static int[] getSpeedLimits() {
		int[] ret = new int[list.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list[i].maxSpeed;
		}
		return ret;
	}

	public static String getLimitsForDisplay() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.length; i++) {
			Flywheels set = list[i];
			sb.append(String.format("%s: %d rad/s", set.getName(), set.maxSpeed));
			if (i < list.length-1)
				sb.append("\n");
		}
		return sb.toString();
	}
}
