/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntitySteamEngine extends TileEntityEngine {

	@Override
	public boolean canConsumeFuel() {
		return water.getLevel() > 0 && temperature >= 100;
	}

	@Override
	protected void consumeFuel() {
		water.removeLiquid(this.getConsumedFuel());
	}

	@Override
	protected void internalizeFuel() {

	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		if (temperature < 100) //water boiling point
			return false;
		if (water.isEmpty())
			return false;

		RotaryAchievements.STEAMENGINE.triggerAchievement(this.getPlacer());
		return true;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier) {
		soundtick++;
		if (!ConfigRegistry.ENGINESOUNDS.getState())
			return;
		float volume = 1;
		if (this.isMuffled(world, x, y, z)) {
			volume = 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		SoundRegistry.STEAM.playSoundAtBlock(world, x, y, z, 0.7F*volume, 1F*pitchMultiplier);
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		super.updateTemperature(world, x, y, z, meta);

		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

		if (biome == BiomeGenBase.hell)
			Tamb = 101;	//boils water, so 101C
		if (world.getBlockId(x, y-1, z) == Block.fire.blockID)
			temperature++;
		if (world.getBlockId(x, y-1, z) == Block.fire.blockID && biome == BiomeGenBase.hell)
			temperature++; //Nether has 50% hotter fire
		if (world.getBlockMaterial(x, y-1, z) == Material.lava)
			temperature += 2;
		if (Tamb < 0 && world.getBlockId(x, y-1, z) == Block.fire.blockID)
			Tamb += 30;
		if (temperature < Tamb)
			temperature += ReikaMathLibrary.extrema((Tamb-temperature)/40, 1, "max");
		if (world.getBlockId(x, y-1, z) != Block.fire.blockID && world.getBlockMaterial(x, y-1, z) != Material.lava && temperature > Tamb)
			temperature--;
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/96;
		}
		if (temperature > MAXTEMP)
			this.overheat(world, x, y, z);
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		temperature = MAXTEMP;
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 17, false, 1F, false, true, 2F);
		RotaryAchievements.OVERPRESSURE.triggerAchievement(this.getPlacer());
		world.setBlockToAir(x, y, z);
	}

	@Override
	public int getFuelLevel() {
		return water.getLevel();
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {

	}

}
