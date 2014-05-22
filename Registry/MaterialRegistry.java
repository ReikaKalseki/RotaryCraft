/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.MekToolHandler;
import Reika.DragonAPI.ModInteract.RedstoneArsenalHandler;
import Reika.DragonAPI.ModInteract.TinkerToolHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum MaterialRegistry {

	WOOD(ReikaEngLibrary.Ewood, ReikaEngLibrary.Gwood, ReikaEngLibrary.Twood, ReikaEngLibrary.Swood, ReikaEngLibrary.rhowood),
	STONE(ReikaEngLibrary.Estone, ReikaEngLibrary.Gstone, ReikaEngLibrary.Tstone, ReikaEngLibrary.Sstone, ReikaEngLibrary.rhorock),
	STEEL(ReikaEngLibrary.Esteel, ReikaEngLibrary.Gsteel, ReikaEngLibrary.Tsteel, ReikaEngLibrary.Ssteel, ReikaEngLibrary.rhoiron),
	DIAMOND(ReikaEngLibrary.Ediamond, ReikaEngLibrary.Gdiamond, ReikaEngLibrary.Tdiamond, ReikaEngLibrary.Sdiamond, ReikaEngLibrary.rhodiamond),
	BEDROCK(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, ReikaEngLibrary.rhorock);

	private double Emod;
	private double Smod;
	private double tensile;
	private double shear;
	private double rho;

	public static final MaterialRegistry[] matList = values();

	private MaterialRegistry(double E, double G, double TS, double S, double den) {
		Emod = E;
		Smod = G;
		tensile = TS;
		shear = S;
		rho = den;
	}

	public static MaterialRegistry setType(int type) {
		return matList[type];
	}

	public double getElasticModulus() {
		return Emod;
	}

	public double getShearModulus() {
		return Smod;
	}

	public double getTensileStrength() {
		return tensile;
	}

	public double getShearStrength() {
		return shear;
	}

	public double getDensity() {
		return rho;
	}

	public boolean isDamageableGear() {
		return !this.isInfiniteStrength();
	}

	public boolean isInfiniteStrength() {
		return (this == BEDROCK);
	}

	public boolean consumesLubricant() {
		return this == WOOD || this == STONE || this == STEEL;
	}

	public boolean isFlammable() {
		return this == WOOD;
	}

	public String getDamageNoise() {
		if (this == WOOD)
			return "dig.wood";
		if (this == STONE)
			return "dig.stone";
		return "mob.blaze.hit";
	}

	public boolean isHarvestablePickaxe(ItemStack tool) {
		if (this == WOOD)
			return true;
		if (tool == null)
			return false;
		if (tool.itemID == ItemRegistry.BEDPICK.getShiftedID())
			return true;
		if (tool.itemID == ItemRegistry.STEELPICK.getShiftedID())
			return true;
		if (tool.getItem() instanceof ItemPickaxe) {
			switch(this) {
			case STONE:
				return true;
			case STEEL:
				return tool.getItem().canHarvestBlock(Block.oreIron, tool);
			case DIAMOND:
				return tool.getItem().canHarvestBlock(Block.oreDiamond, tool);
			case BEDROCK:
				return tool.getItem().canHarvestBlock(Block.obsidian, tool);
			default:
				return false;
			}
		}
		if (TinkerToolHandler.getInstance().isPick(tool)) {
			switch(this) {
			case STONE:
				return true;
			case STEEL:
				return TinkerToolHandler.getInstance().isStoneOrBetter(tool);
			case DIAMOND:
				return TinkerToolHandler.getInstance().isIronOrBetter(tool);
			case BEDROCK:
				return TinkerToolHandler.getInstance().isDiamondOrBetter(tool);
			default:
				return false;
			}
		}
		if (MekToolHandler.getInstance().isPickTypeTool(tool)) {
			switch(this) {
			case STONE:
				return true;
			case STEEL:
				return tool.getItem().canHarvestBlock(Block.oreIron, tool);
			case DIAMOND:
				return tool.getItem().canHarvestBlock(Block.oreDiamond, tool);
			case BEDROCK:
				return tool.getItem().canHarvestBlock(Block.obsidian, tool);
			default:
				return false;
			}
		}
		if (tool.itemID == RedstoneArsenalHandler.getInstance().pickID) {
			return RedstoneArsenalHandler.getInstance().pickLevel >= this.ordinal()-1;
		}
		return false;
	}

	public String getName() {
		return StatCollector.translateToLocal("material."+this.name().toLowerCase());
	}

	public double getMaxShaftTorque() {
		if (this.isInfiniteStrength())
			return Double.POSITIVE_INFINITY;
		double r = 0.0625;
		double tau = this.getShearStrength();
		return 0.5*Math.PI*r*r*r*tau/16D;
	}

	public double getMaxShaftSpeed() {
		if (this.isInfiniteStrength())
			return Double.POSITIVE_INFINITY;
		double f = 1D/(1-(0.11D*this.ordinal()));
		double rho = this.getDensity();
		double r = 0.0625;
		double sigma = this.getTensileStrength();
		double base = Math.sqrt(2*sigma/(rho*r*r));
		return Math.pow(base, f);
	}

	public static int[] getAllLimitLoads() {
		int[] loads = new int[matList.length*2-2];
		for (int i = 0; i < loads.length; i += 2) {
			int j = i/2;
			MaterialRegistry m = matList[j];
			loads[i] = (int)m.getMaxShaftTorque();
			loads[i+1] = (int)m.getMaxShaftSpeed();
		}
		return loads;
	}

	public ItemStack getShaftItem() {
		return MachineRegistry.SHAFT.getCraftedMetadataProduct(this.ordinal());
	}

	public ItemStack getGearItem(int ratio) {
		int type = this.ordinal();
		ratio = (int)ReikaMathLibrary.logbase(ratio, 2)-1;
		return MachineRegistry.GEARBOX.getCraftedMetadataProduct(5*ratio+type);
	}

	public static MaterialRegistry getMaterialFromItem(ItemStack is) {
		if (ReikaItemHelper.matchStacks(is, ItemStacks.shaftitem)) {
			return STEEL;
		}
		else if (is.itemID == RotaryCraft.shaftcraft.itemID) {
			if (ReikaMathLibrary.isValueInsideBoundsIncl(ItemStacks.gearunit.getItemDamage(), ItemStacks.gearunit16.getItemDamage(), is.getItemDamage()))
				return STEEL;
		}
		else if (is.itemID == RotaryCraft.gearunits.itemID) {
			int dmg = is.getItemDamage()/4;
			return dmg > 1 ? matList[dmg+1] : matList[dmg];
		}
		else if (is.itemID == Item.stick.itemID) {
			return WOOD;
		}
		else if (ReikaItemHelper.matchStacks(is, ItemStacks.stonerod)) {
			return STONE;
		}
		else if (ReikaItemHelper.matchStacks(is, ItemStacks.diamondshaft)) {
			return DIAMOND;
		}
		else if (ReikaItemHelper.matchStacks(is, ItemStacks.bedrockshaft)) {
			return BEDROCK;
		}
		return null;
	}
}
