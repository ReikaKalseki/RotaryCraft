package Reika.RotaryCraft.ModInterface;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidProducer;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBusController;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public class RotaryWailaProvider implements IWailaDataProvider {

	public static final RotaryWailaProvider instance = new RotaryWailaProvider();

	private RotaryWailaProvider() {

	}

	public static void registerObjects(IWailaRegistrar reg) {
		//reg.registerHeadProvider(b, c);con
		reg.registerBodyProvider(instance, RotaryWailaProvider.class);
		//reg.registerTailProvider(b, c);
		//reg.registerStackProvider(b, c);
	}

	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return MachineRegistry.getMachineFromIDandMetadata(accessor.getBlock(), accessor.getMetadata()).getCraftedProduct();
	}

	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		World world = acc.getWorld();
		MovingObjectPosition mov = acc.getPosition();
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			currenttip.add(EnumChatFormatting.WHITE+acc.getBlock().getPickBlock(mov, world, x, y, z).getDisplayName());
		}
		return currenttip;
	}

	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)acc.getTileEntity();
		te.syncAllData(false);
		if (te instanceof TemperatureTE)
			currenttip.add(String.format("Temperature: %dC", ((TemperatureTE) te).getTemperature()));
		if (te instanceof PressureTE)
			currenttip.add(String.format("Pressure: %dkPa", ((PressureTE) te).getPressure()));
		if (te instanceof TileEntitySplitter) {
			TileEntitySplitter spl = (TileEntitySplitter)te;
			if (spl.isSplitting()) {
				currenttip.add("Splitting Power");
				currenttip.add(spl.getRatioForDisplay());
			}
			else {
				currenttip.add("Merging Power");
			}
		}
		if (te instanceof EnergyToPowerBase) {
			EnergyToPowerBase e = (EnergyToPowerBase)te;
			currenttip.add(String.format("Consuming %d %s/t", e.getConsumedUnitsPerTick(), e.getUnitDisplay()));
			currenttip.add(String.format("Contains %d %s", e.getStoredPower(), e.getUnitDisplay()));
			//currenttip.add(String.format("Lubricant: %d mB", e.getLubricant()));
		}
		if (te instanceof PoweredLiquidIO) {
			PoweredLiquidIO liq = (PoweredLiquidIO)te;
			Fluid in = liq.getFluidInInput();
			Fluid out = liq.getFluidInOutput();
			int amtin = liq.getInputLevel();
			int amtout = liq.getOutputLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amtin, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			String output = out != null ? String.format("%d/%d mB of %s", amtout, liq.getCapacity(), out.getLocalizedName()) : "Empty";
			currenttip.add("Input Tank: "+input);
			currenttip.add("Output Tank: "+output);
		}
		else if (te instanceof PoweredLiquidReceiver) {
			PoweredLiquidReceiver liq = (PoweredLiquidReceiver)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		else if (te instanceof PoweredLiquidProducer) {
			PoweredLiquidProducer liq = (PoweredLiquidProducer)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		else if (te instanceof IFluidHandler) {
			FluidTankInfo[] tanks = ((IFluidHandler)te).getTankInfo(ForgeDirection.UP);
			if (tanks != null) {
				for (int i = 0; i < tanks.length; i++) {
					FluidTankInfo info = tanks[i];
					FluidStack fs = info.fluid;
					String input = fs != null ? String.format("%d/%d mB of %s", fs.amount, info.capacity, fs.getFluid().getLocalizedName(fs)) : "Empty";
					currenttip.add("Tank "+i+": "+input);
				}
			}
		}
		if (te instanceof DiscreteFunction) {
			int ticks = ((DiscreteFunction)te).getOperationTime();
			float sec = Math.max(0.05F, ticks/20F);
			currenttip.add(String.format("Operation Time: %.2fs", sec));
		}
		if (te instanceof TileEntityPiping) {
			TileEntityPiping tp = (TileEntityPiping)te;
			Fluid f = tp.getFluidType();
			if (f != null) {
				currenttip.add(String.format("%s", f.getLocalizedName()));
				if (tp instanceof TileEntityPipe) {
					int p = ((TileEntityPipe)tp).getPressure();
					double val = ReikaMathLibrary.getThousandBase(p);
					String sg = ReikaEngLibrary.getSIPrefix(p);
					currenttip.add(String.format("Pressure: %.3f%sPa", val, sg));
				}
			}
		}
		if (te instanceof TileEntityBusController) {
			ShaftPowerBus bus = ((TileEntityBusController)te).getBus();
			if (bus != null) {
				currenttip.add("Power Bus Receiving "+bus.getInputTorque()+" Nm @ "+bus.getSpeed()+" rad/s");
				currenttip.add(bus.getInputPower()+"W is being split to "+bus.getTotalOutputSides()+" devices");
				currenttip.add("(Power per side is "+bus.getInputPower()/bus.getTotalOutputSides()+"W)");
			}
		}
		if (te.getMachine().isEnchantable()) {
			if (((EnchantableMachine)te).hasEnchantments()) {
				currenttip.add("Enchantments: ");
				ArrayList<Enchantment> li = ((EnchantableMachine)te).getValidEnchantments();
				for (int i = 0; i < li.size(); i++) {
					Enchantment e = li.get(i);
					int level = ((EnchantableMachine)te).getEnchantment(e);
					if (level > 0)
						currenttip.add("  "+EnumChatFormatting.LIGHT_PURPLE.toString()+e.getTranslatedName(level));
				}
			}
		}
		if (te instanceof ConditionalOperation) {
			currenttip.add(((ConditionalOperation) te).getOperationalStatus());
		}
		if (te instanceof TileEntityPulseFurnace) {
			TileEntityPulseFurnace tpf = (TileEntityPulseFurnace)te;
			int lvl = tpf.getAccelerant();
			if (lvl > 0) {
				Fluid f = tpf.getAccelerantType();
				currenttip.add(String.format("Accelerant: %dmB of %s", lvl, f.getLocalizedName()));
			}
			else
				currenttip.add("Accelerant: Empty");
		}
		return currenttip;
	}

	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");
		return currenttip;
	}

}
