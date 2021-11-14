/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Recipe.ItemMatch;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPoweredLiquidIO;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFuelConverter extends InventoriedPoweredLiquidIO {

	public static final int CAPACITY = 5*FluidContainerRegistry.BUCKET_VOLUME;

	public static enum Conversions {
		BCFUEL("fuel", "rc jet fuel", 1, 4, DifficultyEffects.CONSUMEFRAC.getChance()/100D, new ItemMatch(Items.blaze_powder), new ItemMatch(ItemStacks.netherrackdust), new ItemMatch(ItemStacks.tar), new ItemMatch(Items.magma_cream), new ItemMatch(ReikaItemHelper.pinkDye)),
		KEROSENE("kerosene", "rc jet fuel", 1, 4, DifficultyEffects.CONSUMEFRAC.getChance()/100D, new ItemMatch(Items.blaze_powder), new ItemMatch(ItemStacks.netherrackdust), new ItemMatch(ItemStacks.tar), new ItemMatch(Items.magma_cream), new ItemMatch(ReikaItemHelper.pinkDye)),
		;

		public final Fluid input;
		public final Fluid output;

		public final int speedFactor;
		public final int fluidRatio;

		public final double itemConsumptionChance;

		private final ItemMatch[] ingredients;

		private static final HashMap<String, Conversions> conversionMap = new HashMap();
		private static final HashMap<String, Conversions> conversionOutputMap = new HashMap();

		public static Conversions[] list = values();

		private Conversions(String in, String out, int sp, int r, double f, ItemMatch... items) {
			input = FluidRegistry.getFluid(in);
			output = FluidRegistry.getFluid(out);

			speedFactor = sp;
			fluidRatio = r;

			itemConsumptionChance = f;

			ingredients = items;
		}

		public boolean isValid() {
			return input != null && output != null;
		}

		public boolean isValidItem(ItemStack is) {
			for (int i = 0; i < ingredients.length; i++) {
				if (ingredients[i].match(is))
					return true;
			}
			return false;
		}

		static {
			for (int i = 0; i < list.length; i++) {
				Conversions c = list[i];
				if (c.input != null && c.output != null) {
					conversionMap.put(c.input.getName(), c);
					conversionOutputMap.put(c.output.getName(), c);
				}
			}
		}

		public static void addRecipe(String name, String in, String out, int speed, int fluidRatio, double itemConsumeChance, ItemMatch... items) {
			Conversions c = EnumHelper.addEnum(Conversions.class, name.toUpperCase(), new Class[]{String.class, String.class, int.class, int.class, double.class, ItemMatch[].class}, new Object[]{in, out, speed, fluidRatio, itemConsumeChance, items});
			conversionMap.put(in, c);
			conversionOutputMap.put(out, c);
			list = values();
		}

		public static Collection<Conversions> getByInput(ItemStack is) {
			Collection<Conversions> li = new ArrayList();
			for (Conversions c : conversionMap.values()) {
				if (c.isValidItem(is))
					li.add(c);
			}
			return li;
		}

		public static Collection<Conversions> getByInput(Fluid f) {
			Collection<Conversions> li = new ArrayList();
			for (Conversions c : conversionMap.values()) {
				if (c.input == f)
					li.add(c);
			}
			return li;
		}

		public static Collection<Conversions> getByOutput(Fluid f) {
			Collection<Conversions> li = new ArrayList();
			for (Conversions c : conversionMap.values()) {
				if (c.output == f)
					li.add(c);
			}
			return li;
		}

		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getIngredientsForDisplay() {
			Collection<ItemStack> c = new ArrayList();
			for (ItemMatch m : ingredients) {
				c.add(m.getCycledItem());
			}
			return c;
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FUELENHANCER;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		if (input.isEmpty())
			return 15;
		if (output.isFull())
			return 15;
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();

		//ReikaJavaLibrary.pConsole(input+":"+output);

		//ReikaJavaLibrary.pConsoleSideOnly("BC: "+this.getBCFuel()+"    JET: "+this.getJetFuel(), Side.CLIENT);

		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (world.isRemote)
			return;

		Conversions c = this.getConversion();
		if (c != null && this.getInputLevel() >= c.fluidRatio*c.speedFactor && this.hasItems(c) && output.canTakeIn(c.speedFactor)) {
			input.removeLiquid(c.fluidRatio*c.speedFactor);
			output.addLiquid(c.speedFactor, c.output);
			this.consumeItems(c);
		}
	}

	private Conversions getConversion() {
		return !input.isEmpty() ? Conversions.conversionMap.get(input.getActualFluid().getName()) : null;
	}

	private boolean hasItems(Conversions c) {
		for (int i = 0; i < c.ingredients.length; i++) {
			if (!ReikaInventoryHelper.checkForItemStack(c.ingredients[i], inv)) {
				return false;
			}
		}
		return true;
	}

	private void consumeItems(Conversions c) {
		for (int i = 0; i < c.ingredients.length; i++) {
			if (ReikaRandomHelper.doWithChance(c.itemConsumptionChance))
				ReikaInventoryHelper.decrStack(ReikaInventoryHelper.locateInInventory(c.ingredients[i], inv), inv);
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.getConversionByItem(is) != null;
	}

	public static Conversions getConversionByItem(ItemStack is) {
		for (int i = 0; i < Conversions.list.length; i++) {
			Conversions c = Conversions.list[i];
			if (c.isValidItem(is)) {
				return c;
			}
		}
		return null;
	}

	public double getLiquidModelOffset(boolean in) {
		return in ? 10/16D : 1/16D;
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m.isStandardPipe();
	}

	public Fluid getInputFluidType() {
		return input.getActualFluid();
	}

	public Fluid getOutputFluidType() {
		return output.getActualFluid();
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return Conversions.conversionMap.get(f.getName()) != null;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to.offsetY == 0;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from == ForgeDirection.UP;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return p.isStandardPipe();
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.FUELLINE;
	}

}
