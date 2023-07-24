/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Recipe.ItemMatch;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityIgniter extends InventoriedPowerReceiver implements TemperatureTE, RangedEffect, ConditionalOperation {

	private int temperature;
	private IgnitionFuel fuel;

	public static final int ANIMALIGNITION = 280; //Fat ignition temperature
	public static final int MAXTEMP = 2500;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (tickcount >= 40) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
			fuel = IgnitionFuel.getFromItems(inv);
			if (fuel == null)
				return;
			if (temperature < fuel.temperature)
				this.burnFuel();
		}
		if (fuel == null)
			return;
		int spread = this.getRange();
		int yspread = this.getRange()/2;
		int n = 1+temperature/50;
		for (int i = 0; i < n; i++) {
			int fx = ReikaRandomHelper.getRandomPlusMinus(x, spread);
			int fy = ReikaRandomHelper.getRandomPlusMinus(y, yspread);
			int fz = ReikaRandomHelper.getRandomPlusMinus(z, spread);
			this.fire(world, x, y, z, fx, fy, fz);
		}

		if (temperature >= ANIMALIGNITION) {
			AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(this).expand(spread, yspread, spread);
			List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
			for (EntityLivingBase ent : in) {
				ent.setFire(1);
			}
		}
	}

	private void fire(World world, int x, int y, int z, int fx, int fy, int fz) {
		//double dd = ReikaMathLibrary.py3d(x-fx, y-fy, z-fz);
		int d = this.getRange();
		//ReikaWorldHelper.spawnParticleLine(world, x+0.5, y+0.5, z+0.5, fx+0.5, fy+0.5, fz+0.5, "flame", 0, 0, 0, 20);

		if (world.isRemote) {
			this.fireFX(world, x, y, z, fx, fy, fz, d);
		}
		else
			ReikaWorldHelper.temperatureEnvironment(world, fx, fy, fz, this.getAffectiveTemperature());
	}

	@SideOnly(Side.CLIENT)
	private void fireFX(World world, int x, int y, int z, int fx, int fy, int fz, int r) {
		int n = r*r/(1+2*Minecraft.getMinecraft().gameSettings.particleSetting);
		for (int i = 0; i < n; i++) {
			int dx = x-r+rand.nextInt(r*2+1);
			int dz = z-r+rand.nextInt(r*2+1);
			int dy = y-r/2+rand.nextInt(r/2+1);
			world.spawnParticle("flame", dx, dy+1, dz, 0, 0, 0);
			world.spawnParticle("smoke", dx, dy+1, dz, 0, 0, 0);
		}
	}

	private int getAffectiveTemperature() {
		return ConfigRegistry.ATTACKBLOCKS.getState() ? temperature : Math.min(400, temperature);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature > Tamb) {
			int Tdiff = temperature-Tamb;
			temperature -= (int)Math.log(Tdiff);
		}
		if (temperature < Tamb) {
			int Tdiff = Tamb-temperature;
			temperature += Tdiff/24;
		}
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	private void burnFuel() {
		Collection<ItemStack> drops = new ArrayList();
		for (ItemMatch m : fuel.items) {
			int slot = ReikaInventoryHelper.locateInInventory(m, inv);
			ItemStack in = inv[slot];
			ItemStack ret = in.getItem().getContainerItem(in);
			if (fuel == IgnitionFuel.LAVA)
				ret = FluidContainerRegistry.drainFluidContainer(ret);
			if (ret != null) {
				drops.add(ret);
			}
			if (slot != -1)
				ReikaInventoryHelper.decrStack(slot, inv);
		}

		if (temperature < fuel.temperature)
			temperature += (fuel.temperature-temperature)/4;

		for (ItemStack is : drops) {
			int leftover = ReikaInventoryHelper.addToInventoryWithLeftover(is, inv);
			if (leftover > 0) {
				EntityItem ei = new EntityItem(worldObj, xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), is);
				ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
				if (!worldObj.isRemote)
					worldObj.spawnEntityInWorld(ei);
			}
		}
	}

	@Override
	public int getRange() {
		return 16+ReikaMathLibrary.roundUpToX(8, (int)Math.sqrt(temperature*2));
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.IGNITER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return isValidFuel(is);
	}

	private static boolean isValidFuel(ItemStack is) {
		for (int i = 0; i < IgnitionFuel.fuelList.length; i++) {
			IgnitionFuel f = IgnitionFuel.fuelList[i];
			for (ItemMatch m : f.items) {
				if (m.match(is))
					return true;
			}
		}
		return false;
	}

	@Override
	public int getMaxRange() {
		return this.getRange();
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/50;
	}

	@Override
	public int getRedstoneOverride() {
		return fuel != null ? 0 : 15;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Fuel";
	}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public boolean allowHeatExtraction() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	public static enum IgnitionFuel {
		WOOD(400, "plankWood/logWood"),
		COAL(600, Items.coal),
		BLAZE(800, Items.blaze_powder),
		LAVA(1200, FluidRegistry.LAVA),
		THERMITE(2500, "dustAluminum/dustAluminium", "ingotIron/dustIron");

		public final int temperature;
		private final Collection<ItemMatch> items = new ArrayList();

		public static final IgnitionFuel[] fuelList = values();

		private IgnitionFuel(int t, Object... items) {
			temperature = t;
			for (int i = 0; i < items.length; i++) {
				Object o = items[i];
				this.items.add(ItemMatch.createFromObject(o));
			}
		}

		private IgnitionFuel compare(IgnitionFuel f) {
			return compare(this, f);
		}

		private static IgnitionFuel compare(IgnitionFuel f1, IgnitionFuel f2) {
			return f1.temperature > f2.temperature ? f1 : f2;
		}

		private static IgnitionFuel getFromItems(ItemStack[] items) {
			for (int i = fuelList.length-1; i >= 0; i--) {
				boolean valid = true;
				IgnitionFuel f = fuelList[i];
				for (ItemMatch m : f.items) {
					boolean found = false;
					for (int k = 0; k < items.length; k++) {
						ItemStack in = items[k];
						if (in != null && m.match(in)) {
							found = true;
							break;
						}
					}
					if (!found) {
						valid = false;
						break;
					}
				}
				if (valid)
					return f;
			}
			return null;
		}
	}
}
