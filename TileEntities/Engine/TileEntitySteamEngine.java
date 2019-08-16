/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntitySteamEngine extends TileEntityEngine {

	private int dryTicks = 0;

	@Override
	public boolean canConsumeFuel() {
		return water.getLevel() > 0 && temperature >= 100;
	}

	@Override
	public int getMaxTemperature() {
		return 150;
	}

	@Override
	protected void consumeFuel() {
		water.removeLiquid(this.getConsumedFuel());
	}

	@Override
	protected int getConsumedFuel() {
		int amt = 10;
		if (temperature >= 130) {
			amt = 75;
		}
		else if (temperature >= 125) {
			amt = 60;
		}
		else if (temperature >= 120) {
			amt = 50;
		}
		else if (temperature >= 110) {
			amt = 25;
		}
		if (worldObj.getBlock(xCoord, yCoord-1, zCoord) == Blocks.lava)
			amt *= 4;
		return amt;
	}

	@Override
	protected void internalizeFuel() {
		if (water.isEmpty() && temperature >= 100) {
			dryTicks++;
		}
		else {
			if (dryTicks > 900 && !water.isEmpty()) {
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				worldObj.createExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 6, false);
			}
			dryTicks = 0;
		}
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
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
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

		boolean fire = RotaryAux.isAboveFire(world, x, y, z);
		boolean lava = RotaryAux.isAboveLava(world, x, y, z);
		if (biome == BiomeGenBase.hell)
			Tamb = 101;	//boils water, so 101C
		if (fire)
			temperature++;
		if (fire && biome == BiomeGenBase.hell)
			temperature++; //Nether has 50% hotter fire
		if (lava) {
			temperature += 2;
		}
		if (Tamb < 0 && fire)
			Tamb += 30;
		if (temperature < Tamb)
			temperature += ReikaMathLibrary.extrema((Tamb-temperature)/40, 1, "max");
		if (!fire && !lava && temperature > Tamb)
			temperature--;
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/96;
		}
		if (temperature > MAXTEMP)
			this.overheat(world, x, y, z);
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		if (water.isEmpty())
			return;
		temperature = MAXTEMP;
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.copy(), 0, 17, false, 1F, false, true, 2F);
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

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("dry", dryTicks);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		dryTicks = NBT.getInteger("dry");
	}

	@Override
	public boolean canBeCooledWithFins() {
		return true;
	}

	@Override
	public boolean allowExternalHeating() {
		return true;
	}

}
