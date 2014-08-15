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

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class CanolaBee extends BeeSpecies {

	private final AlleleCanola canola = new AlleleCanola();

	public CanolaBee() { //cultivated + meadows
		super("Slippery", "bee.canola", "Mechanica Lubrica", "Reika");
		this.addSpecialty(ItemStacks.slipperyComb, 20);
		this.addSpecialty(ItemRegistry.CANOLA.getStackOf(), 25);
		this.addProduct(ForestryHandler.Combs.HONEY.getItem(), 50);
		this.addProduct(ForestryHandler.Combs.DRIPPING.getItem(), 12);
		this.addProduct(ForestryHandler.Combs.STRINGY.getItem(), 5);
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
			return world.getBlock(x, y, z) == BlockRegistry.CANOLA.getBlockInstance() && world.getBlockMetadata(x, y, z) < 9;
		}

		@Override
		public boolean isAcceptedPollinatable(World world, IPollinatable p) {
			return false;
		}

		@Override
		public boolean growFlower(World world, IIndividual individual, int x, int y, int z) {
			int r = 24;
			boolean flag = false;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						int dx = x+i;
						int dy = y+j;
						int dz = z+k;
						if (dy > 0) {
							Block b = world.getBlock(dx, dy, dz);
							int meta = world.getBlockMetadata(dx, dy, dz);
							if (b == BlockRegistry.CANOLA.getBlockInstance()) {
								if (meta < 9) {
									world.scheduleBlockUpdate(dx, dy, dz, b, 20+rand.nextInt(300));
									flag = true;
								}
							}
						}
					}
				}
			}
			return flag;
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
			return new ItemStack[]{BlockRegistry.CANOLA.getStackOf()};
		}
	}

	@Override
	public String getDescription() {
		return "These bees produce a greasy comb that can be processed into a fluid that seems to aid mechanical motion.";
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
	public int getOutlineColor() {
		return 0xffd500;
	}

	@Override
	public boolean isDominant() {
		return true;
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
	public IAllele getFlowerAllele() {
		return canola;
	}

	@Override
	public Speeds getProductionSpeed() {
		return Speeds.FAST;
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
		return Life.SHORT;
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

	@Override
	public Tolerance getHumidityToleranceDir() {
		return Tolerance.BOTH;
	}

	@Override
	public Tolerance getTemperatureToleranceDir() {
		return Tolerance.BOTH;
	}

	@Override
	public IAllele getEffectAllele() {
		return Effect.NONE.getAllele();
	}

}