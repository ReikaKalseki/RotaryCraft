/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;

public class CanolaBee implements IAlleleBeeSpecies {

	@Override
	public String getDescription() {
		return "These bees produce a greasy comb that can be processed into a fluid that seems to aid mechanical motion.";
	}

	@Override
	public String getBinomial() {
		return "Illitus";
	}

	@Override
	public String getAuthority() {
		return "Reika";
	}

	@Override
	public IClassification getBranch() {
		return null;
	}

	@Override
	public int getComplexity() {
		return 0;
	}

	@Override
	public float getResearchSuitability(ItemStack is) {
		return 0;
	}

	@Override
	public ItemStack[] getResearchBounty(World paramWorld, String paramString, IIndividual paramIIndividual, int paramInt) {
		return new ItemStack[0];
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	@Override
	public boolean hasEffect() {
		return false;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public boolean isCounted() {
		return true;
	}

	@Override
	public int getIconColour(int paramInt) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() {
		return null;
	}

	@Override
	public String getUID() {
		return null;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getName() {
		return "Canola";
	}

	@Override
	public IBeeRoot getRoot() {
		return null;
	}

	@Override
	public boolean isNocturnal() {
		return false;
	}

	@Override
	public Map getProducts() {
		return null;
	}

	@Override
	public Map getSpecialty() {
		return null;
	}

	@Override
	public boolean isJubilant(IBeeGenome paramIBeeGenome, IBeeHousing paramIBeeHousing) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(EnumBeeType paramEnumBeeType, int paramInt) {
		return null;
	}

	@Override
	public String getEntityTexture() {
		return null;
	}

}
