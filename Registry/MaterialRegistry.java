/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.ReikaEngLibrary;
import Reika.DragonAPI.ModInteract.TinkerToolHandler;

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

	private MaterialRegistry(double E, double G, double TS, double S, double den) {
		Emod = E;
		Smod = G;
		tensile = TS;
		shear = S;
		rho = den;
	}

	public static MaterialRegistry setType(int type) {
		return values()[type];
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

	public boolean isHarvestablePickaxe(ItemStack tool) {
		if (this == WOOD)
			return true;
		if (tool == null)
			return false;
		if (tool.itemID == ItemRegistry.BEDPICK.getShiftedID())
			return true;
		if (tool.getItem() instanceof ItemPickaxe) {
			switch(this) {
			case STONE:
				return true;
			case STEEL:
				return tool.getItem().canHarvestBlock(Block.oreIron);
			case DIAMOND:
				return tool.getItem().canHarvestBlock(Block.oreDiamond);
			case BEDROCK:
				return tool.getItem().canHarvestBlock(Block.obsidian);
			default:
				return false;
			}
		}
		if (TinkerToolHandler.getInstance().isPick(tool)) {
			switch(this) {
			case STONE:
				return true;
			case STEEL:
				return TinkerToolHandler.getInstance().isStoneOrBetterPick(tool);
			case DIAMOND:
				return TinkerToolHandler.getInstance().isIronOrBetterPick(tool);
			case BEDROCK:
				return TinkerToolHandler.getInstance().isDiamondOrBetterPick(tool);
			default:
				return false;
			}
		}
		return false;
	}
}
