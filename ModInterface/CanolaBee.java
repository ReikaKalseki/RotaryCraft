/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.ModularLogger;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Bees.BasicFlowerProvider;
import Reika.DragonAPI.ModInteract.Bees.BasicGene;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Effect;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Fertility;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Flowering;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Life;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Speeds;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Territory;
import Reika.DragonAPI.ModInteract.Bees.BeeAlleleRegistry.Tolerance;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies;
import Reika.DragonAPI.ModInteract.ItemHandlers.AgriCraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.FlowerManager;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerAcceptableRule;
import forestry.api.genetics.IFlowerGrowthHelper;
import forestry.api.genetics.IFlowerProvider;

public class CanolaBee extends BeeSpecies {

	private static final String LOGGER_ID = "CanolaBee";

	private final AlleleCanola canola = new AlleleCanola();

	static {
		ModularLogger.instance.addLogger(RotaryCraft.instance, LOGGER_ID);
	}

	public CanolaBee() { //cultivated + meadows
		super("Slippery", "bee.canola", "Mechanica Lubrica", "Reika", new BeeBranch("branch.rotary", "Mechanica", "Mechanica", "These bees seem to be made to aid mechanical devices."));
		this.addSpecialty(ItemStacks.slipperyComb, 15);
		this.addSpecialty(ItemStacks.canolaSeeds, 25);
		this.addProduct(ForestryHandler.Combs.HONEY.getItem(), 40);
		this.addProduct(ForestryHandler.Combs.DRIPPING.getItem(), 10);
		this.addProduct(ForestryHandler.Combs.STRINGY.getItem(), 2.5F);
		if (ConfigRegistry.enableBeeYeast()) {
			this.addSpecialty(ItemRegistry.YEAST.getStackOf(), 10);
		}
	}

	private final class AlleleCanola extends BasicGene implements IAlleleFlowers {

		private final FlowerProviderCanola flowers = new FlowerProviderCanola();

		public AlleleCanola() {
			super("flower.canola", "Canola", EnumBeeChromosome.FLOWER_PROVIDER);
		}

		@Override
		public IFlowerProvider getProvider() {
			return flowers;
		}
	}

	private final class FlowerProviderCanola extends BasicFlowerProvider implements IFlowerAcceptableRule {

		private FlowerProviderCanola() {
			super(BlockRegistry.CANOLA.getBlockInstance(), "canola");
			FlowerManager.flowerRegistry.registerAcceptableFlowerRule(this, this.getFlowerType());
		}
		/*
		@Override
		public boolean isAcceptedFlower(World world, IIndividual individual, int x, int y, int z) {
			return super.isAcceptedFlower(world, individual, x, y, z) && BlockCanola.canGrowAt(world, x, y, z);
		}
		 */
		@Override
		public boolean growFlower(IFlowerGrowthHelper helper, String flowerType, World world, int x, int y, int z) {
			if (ModularLogger.instance.isEnabled(LOGGER_ID))
				ModularLogger.instance.log(LOGGER_ID, "Canola bee @ "+x+", "+y+", "+z+" running growFlower");
			int r = 24;
			int n = 4;
			boolean flag = false;
			for (int i = 0; i < n; i++) {
				//for (int i = -r; i <= r; i++) {
				//	for (int j = -r; j <= r; j++) {
				//		for (int k = -r; k <= r; k++) {
				int dx = ReikaRandomHelper.getRandomPlusMinus(x, r);//x+i;
				int dy = ReikaRandomHelper.getRandomPlusMinus(y, r);//y+j;
				int dz = ReikaRandomHelper.getRandomPlusMinus(z, r);//z+k;
				if (dy > 0) {
					Block b = world.getBlock(dx, dy, dz);
					int meta = world.getBlockMetadata(dx, dy, dz);
					if (b == BlockRegistry.CANOLA.getBlockInstance()) {
						if (meta < 9) {
							//world.scheduleBlockUpdate(dx, dy, dz, b, 20+rand.nextInt(20)); //was 20+rand(300)
							BlockTickEvent.fire(b, world, dx, dy, dz, DragonAPICore.rand, UpdateFlags.FORCED);
							flag = true;
						}
					}
				}
				//		}
				//	}
				//}
			}
			return true;//flag;
		}

		@Override
		public String getDescription() {
			return "Canola Plants";
		}
		/*
		@Override
		public ItemStack[] getItemStacks() {
			return new ItemStack[]{BlockRegistry.CANOLA.getStackOf()};
		}
		 */
		@Override
		public boolean isAcceptableFlower(String flowerType, World world, int x, int y, int z) {
			if (!ModList.AGRICRAFT.isLoaded())
				return false;
			Block b = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			return AgriCraftHandler.getInstance().isCrop(b, meta) && AgriCraftHandler.getInstance().getCropObject(world, x, y, z) == AgriCanola.instance;
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
		ChunkCoordinates c = ibh.getCoordinates();
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, c.posX, c.posY, c.posZ);
		return Tamb > 15 && Tamb < 35;
	}

	@Override
	public IAlleleFlowers getFlowerAllele() {
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
	public IAlleleBeeEffect getEffectAllele() {
		return Effect.NONE.getAllele();
	}

	@Override
	public boolean isTolerantFlyer() {
		return false;
	}

}
