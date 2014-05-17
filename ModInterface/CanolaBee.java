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

import java.awt.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class CanolaBee extends BeeSpecies {

	public CanolaBee() { //cultivated + meadows
		super();
		this.addSpecialty(ItemStacks.slipperyComb, 40); //make produce normal waxy combs;
		this.addBreeding("Meadows", "Cultivated", 20);
		AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(new BranchCanola());
	}

	private final class AlleleCanola implements IAlleleFlowers {

		public AlleleCanola() {
			AlleleManager.alleleRegistry.registerAllele(this);
		}

		@Override
		public String getUID() {
			return "flower.canola";
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
		public IFlowerProvider getProvider() {
			return new FlowerProviderCanola();
		}
	}

	private final class FlowerProviderCanola implements IFlowerProvider {

		@Override
		public boolean isAcceptedFlower(World world, IIndividual individual, int x, int y, int z) {
			return world.getBlockId(x, y, z) == RotaryCraft.canola.blockID;
		}

		@Override
		public boolean isAcceptedPollinatable(World world, IPollinatable p) {
			return false;
		}

		@Override
		public boolean growFlower(World world, IIndividual individual, int x, int y, int z) {
			return true;
		}

		@Override
		public String getDescription() {
			return "Canola Plants";
		}

		@Override
		public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products) {
			return products;
		}

		@Override
		public ItemStack[] getItemStacks() {
			return new ItemStack[]{new ItemStack(RotaryCraft.canola)};
		}
	}

	private final class BranchCanola implements IClassification {

		@Override
		public EnumClassLevel getLevel() {
			return EnumClassLevel.GENUS;
		}

		@Override
		public String getUID() {
			return "bees.canola";
		}

		@Override
		public String getName() {
			return "Slippery";
		}

		@Override
		public String getScientific() {
			return "Lubrica";
		}

		@Override
		public String getDescription() {
			return "These bees produce a greasy comb that can be processed into a fluid that seems to aid mechanical motion.";
		}

		@Override
		public IClassification[] getMemberGroups() {
			return new IClassification[0];
		}

		@Override
		public void addMemberGroup(IClassification icl) {

		}

		@Override
		public IAlleleSpecies[] getMemberSpecies() {
			return new IAlleleSpecies[0];
		}

		@Override
		public void addMemberSpecies(IAlleleSpecies ias) {

		}

		@Override
		public IClassification getParent() {
			return null;
		}

		@Override
		public void setParent(IClassification icl) {

		}

	}

	@Override
	public String getDescription() {
		return "These bees produce a greasy comb that can be processed into a fluid that seems to aid mechanical motion.";
	}

	@Override
	public String getBinomial() {
		return "Lubrica";
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
		return Color.WHITE.getRGB();
	}

	@Override
	public String getUID() {
		return "bee.canola";
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getName() {
		return "Slippery";
	}

	@Override
	public boolean isNocturnal() {
		return false;
	}

	@Override
	public boolean isJubilant(IBeeGenome ibg, IBeeHousing ibh) {
		World world = ibh.getWorld();
		int x = ibh.getXCoord();
		int y = ibh.getYCoord();
		int z = ibh.getZCoord();
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		return Tamb > 15 && Tamb < 35;
	}

	@Override
	public String getEntityTexture() {
		return "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(short ps) {
		return ReikaTextureHelper.getMissingIcon();
	}

	@Override
	public IAllele getFlowerAllele() {
		return new AlleleCanola();
	}

	@Override
	public Speeds getProductionSpeed() {
		return Speeds.FASTER;
	}

	@Override
	public Fertility getFertility() {
		return Fertility.NORMAL;
	}

	@Override
	public Flowering getFloweringRate() {
		return Flowering.FASTER;
	}

	@Override
	public Life getLifespan() {
		return Life.SHORTENED;
	}

	@Override
	public Territory getTerritorySize() {
		return Territory.DEFAULT;
	}

	@Override
	public boolean isCaveDwelling() {
		return false;
	}

	@Override
	public int getTemperatureTolerance() {
		return 1;
	}

	@Override
	public int getHumidityTolerance() {
		return 1;
	}

}
