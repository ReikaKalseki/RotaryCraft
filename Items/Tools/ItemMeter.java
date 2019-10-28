/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Interfaces.TileEntity.ThermalTile;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Interfaces.PressureTile;
import Reika.RotaryCraft.API.Interfaces.Transducerable;
import Reika.RotaryCraft.API.Power.ShaftMachine;
import Reika.RotaryCraft.API.Power.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Variables;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.IntegratedGearboxable;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.RCToModConverter;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.ModInterface.Conversion.TileEntityDynamo;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;

public class ItemMeter extends ItemRotaryTool
{
	public ItemMeter(int tex) {
		super(tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c)
	{
		if (super.onItemUse(is, ep, world, x, y, z, s, a, b, c))
			return true;
		if (world.isRemote)
			return true;
		if (ConfigRegistry.CLEARCHAT.getState())
			ReikaChatHelper.clearChat((EntityPlayerMP)ep);

		Block bk = world.getBlock(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		String name = tile != null ? (tile instanceof TileEntityBase ? ((TileEntityBase)tile).getName() : tile.getClass().getSimpleName()) : "null";
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);

		if (tile instanceof TileEntityIOMachine) {
			((TileEntityIOMachine)tile).iotick = 512;
		}
		else if (tile instanceof ShaftMachine) {
			ShaftMachine sm = (ShaftMachine)tile;
			sm.setIORenderAlpha(512);
		}
		world.markBlockForUpdate(x, y, z);

		if (tile instanceof Transducerable) {
			ArrayList<String> li = ((Transducerable)tile).getMessages(world, x, y, z, s);
			if (li != null) {
				for (int i = 0; i < li.size(); i++)
					this.sendMessage(ep, li.get(i));
			}
		}
		else if (bk instanceof Transducerable) {
			ArrayList<String> li = ((Transducerable)bk).getMessages(world, x, y, z, s);
			if (li != null) {
				for (int i = 0; i < li.size(); i++)
					this.sendMessage(ep, li.get(i));
			}
		}

		if (tile instanceof ThermalTile) {
			ThermalTile th = (ThermalTile)tile;
			this.sendMessage(ep, String.format("%s %s: %dC", name, Variables.TEMPERATURE, th.getTemperature()));
		}

		if (tile instanceof PressureTile) {
			PressureTile th = (PressureTile)tile;
			this.sendMessage(ep, String.format("%s %s: %d kPa", name, Variables.PRESSURE, th.getPressure()));
		}

		if (tile instanceof RangedEffect) {
			RangedEffect te = (RangedEffect)tile;
			this.sendMessage(ep, Variables.RANGE+": "+te.getRange()+" m; "+"Max Range: "+te.getMaxRange()+" m");
		}

		if (tile instanceof DiscreteFunction) {
			this.sendMessage(ep, Variables.OPERATIONTIME+": "+((DiscreteFunction)tile).getOperationTime()/20F+" s");
		}

		if (tile instanceof IntegratedGearboxable) {
			IntegratedGearboxable te = (IntegratedGearboxable)tile;
			int ratio = te.getIntegratedGear();
			if (ratio != 0) {
				this.sendMessage(ep, "Integrated Gearbox: "+Math.abs(ratio)+" x, "+(ratio > 0 ? "Torque" : "Speed"));
			}
		}

		if (tile instanceof PowerSourceTracker) {
			this.sendMessage(ep, String.format("Power is being received from: %s", ((PowerSourceTracker)tile).getPowerSources((PowerSourceTracker)tile, null)));
		}

		if (tile instanceof TileEntityEngine) {
			TileEntityEngine te = (TileEntityEngine)tile;
			world.markBlockForUpdate(x, y, z);
			long power = te.power;
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			this.sendMessage(ep, String.format("%s producing %.3f %sW @ %d rad/s", name, base, pre, te.omega));
			if (te.getEngineType().isAirBreathing() && te.isDrowned(world, x, y, z))
				this.sendLocalizedMessage(ep, "drowning");
			if (te.getEngineType() == EngineType.JET) {
				if (((TileEntityJetEngine)te).getChokedFraction(world, x, y, z, te.getBlockMetadata()) < 1)
					this.sendLocalizedMessage(ep, "choke");
				if (((TileEntityJetEngine)te).FOD >= 8)
					this.sendLocalizedMessage(ep, "fod");
			}
			if (te.getEngineType().burnsFuel()) {
				int time = te.getFuelDuration();
				String sg = String.format("%s: %s", Variables.FUEL, ReikaFormatHelper.getSecondsAsClock(time));
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType().requiresLubricant()) {
				int amt = te.getLube();
				String sg = String.format("Lubricant: %d mB", amt);
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType().isWaterPiped()) {
				int amt = te.getWater();
				String sg = String.format("Water: %d mB", amt);
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType() == EngineType.HYDRO) {
				if (((TileEntityHydroEngine)te).isStreamPowered()) {
					for (String sg : ((TileEntityHydroEngine)te).getStreamData()) {
						this.sendMessage(ep, sg);
					}
				}
				else {
					String sg = String.format("Detected waterfall height: %d m", ((TileEntityHydroEngine)te).getWaterfallHeightForDisplay());
					this.sendMessage(ep, sg);
				}
			}
		}
		else if (tile instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)tile;
			long power = sp.getPower();
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			this.sendMessage(ep, String.format("%s producing %.3f %sW @ %d rad/s", name, base, pre, sp.getOmega()));
		}

		if (tile instanceof TileEntityPowerReceiver) {
			TileEntityPowerReceiver te = (TileEntityPowerReceiver)tile;
			long power = te.power;
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			this.sendMessage(ep, String.format("%s receiving %.3f %sW @ %d rad/s", name, base, pre, te.omega));
			if (power < te.MINPOWER)
				this.sendLocalizedMessage(ep, "minpower");
			if (power < te.MINSPEED)
				this.sendLocalizedMessage(ep, "mintorque");
			if (power < te.MINTORQUE)
				this.sendLocalizedMessage(ep, "minspeed");
		}
		else if (tile instanceof ShaftPowerReceiver) {
			ShaftPowerReceiver sp = (ShaftPowerReceiver)tile;
			long power = sp.getPower();
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			this.sendMessage(ep, String.format("%s receiving %.3f %sW @ %d rad/s", name, base, pre, sp.getOmega()));
		}

		if (tile instanceof TileEntityTransmissionMachine) {
			TileEntityTransmissionMachine te = (TileEntityTransmissionMachine)tile;
			long power = te.power;
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			this.sendMessage(ep, String.format("%s transmitting %.3f %sW @ %d rad/s", name, base, pre, te.omega));
		}

		if (tile instanceof RCToModConverter) {
			RCToModConverter te = (RCToModConverter)tile;
			int units = te.getGeneratedUnitsPerTick();
			String unit = te.getUnitDisplay();
			this.sendMessage(ep, String.format("Generating %d %s/t", units, unit));
		}

		if (tile instanceof EnergyToPowerBase) {
			EnergyToPowerBase te = (EnergyToPowerBase)tile;
			double p3 = ReikaMathLibrary.getThousandBase(te.power);
			String pe = ReikaEngLibrary.getSIPrefix(te.power);
			int units = te.getConsumedUnitsPerTick();
			String unit = te.getUnitDisplay();
			this.sendMessage(ep, String.format("%s Outputting %.3f%sW @ %d rad/s", name, p3, pe, te.omega));
			this.sendMessage(ep, String.format("Consuming %d %s/t", units, unit));
		}

		if (tile instanceof TileEntityPiping) {
			TileEntityPiping te = (TileEntityPiping)tile;
			Fluid f = te.getFluidType();
			if (f != null)
				this.sendMessage(ep, String.format("Pipe has %d mB of %s", te.getFluidLevel(), f.getLocalizedName()));
			else
				this.sendMessage(ep, "Pipe is empty.");
		}

		if (tile instanceof TileEntitySpringPowered) {
			TileEntitySpringPowered te = (TileEntitySpringPowered)tile;
			this.sendMessage(ep, String.format("Remaining charge: %.2fs", te.getExpectedCoilLife()/20D));
		}

		if (m == MachineRegistry.GPR) {
			TileEntityGPR te = (TileEntityGPR)tile;
			if (ep.isSneaking() && te.power > te.MINPOWER) {
				double ratio = 100*te.getSpongy(world, x, y-1, z);
				this.sendMessage(ep, String.format("The ground is %.3f%s caves here", ratio, "%%"));
			}
		}
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace te = (TileEntityBlastFurnace)tile;
			//no way to say "too cold for recipe"
		}
		if (m == MachineRegistry.PLAYERDETECTOR) {
			TileEntityPlayerDetector te = (TileEntityPlayerDetector)tile;
			this.sendMessage(ep, String.format("Reaction Time: %.2fs", te.getReactionTime()/20F));
		}
		if (m == MachineRegistry.SOLARTOWER) {
			TileEntitySolar te = (TileEntitySolar)tile;
			TileEntitySolar top = (TileEntitySolar)world.getTileEntity(x, te.getTopOfTower(), z);
			TileEntitySolar bottom = (TileEntitySolar)world.getTileEntity(x, te.getBottomOfTower(), z);
			if (bottom.getPlant() == null || bottom.getArraySize() <= 0) {
				this.sendMessage(ep, String.format("Solar plant is unformed"));
			}
			else {
				this.sendMessage(ep, String.format("Solar plant contains %d mirrors and %d active tower pieces (out of %d total)", top.getArraySize(), bottom.getPlant().getEffectiveTowerBlocks(), bottom.getPlant().rawTowerBlocks()));
				this.sendMessage(ep, String.format("Outputting %.3fkW at %d rad/s; Efficiency %.1f%s", bottom.power/1000D, bottom.omega, bottom.getArrayOverallBrightness()*100F, "%%"));
			}
		}
		if (m == MachineRegistry.HEATRAY) {
			TileEntityHeatRay te = (TileEntityHeatRay)tile;
			if (te.power >= te.MINPOWER)
				this.sendMessage(ep, String.format("Dealing %ds of burn damage", te.getBurnTime()));
		}
		if (m == MachineRegistry.PUMP) {
			TileEntityPump te = (TileEntityPump)tile;
			if (te.power >= te.MINPOWER && te.duplicationAmount > 1)
				this.sendMessage(ep, String.format("Duplicating water with a factor of %dx", te.duplicationAmount));
		}
		if (m == MachineRegistry.ADVANCEDGEARS) {
			TileEntityAdvancedGear te = (TileEntityAdvancedGear)tile;
			if (te.getGearType() == TileEntityAdvancedGear.GearType.WORM && te.power > 0) {
				this.sendMessage(ep, String.format("Power Loss: %.2f%s", te.getCurrentLoss(), "%%"));
			}
			else if (te.getGearType().storesEnergy()) {
				long energy = te.getEnergy()/20;
				String pre = ReikaEngLibrary.getSIPrefix(energy);
				double base = ReikaMathLibrary.getThousandBase(energy);
				this.sendMessage(ep, String.format("Stored Energy: %.3f %sJ", base, pre));
			}
		}
		if (m == MachineRegistry.BEVELGEARS) {
			TileEntityBevelGear te = (TileEntityBevelGear)tile;
			this.sendMessage(ep, String.format("Output side: %s", ReikaStringParser.capFirstChar(te.getWriteDirection().name())));
		}
		if (m == MachineRegistry.BORER) {
			TileEntityBorer te = (TileEntityBorer)tile;
			this.sendMessage(ep, String.format("%s head at %d, %d", te.getName(), te.getHeadX(), te.getHeadZ()));
			if (te.isJammed())
				this.sendMessage(ep, String.format("%s is jammed, supply more torque or power!", te.getName()));
		}
		if (m == MachineRegistry.BELT || m == MachineRegistry.CHAIN) {
			TileEntityBeltHub te = (TileEntityBeltHub)tile;
			if (te.isSplitting()) {
				this.sendMessage(ep, "Belt is splitting power");
			}
			if (te.isSlipping()) {
				this.sendMessage(ep, "Belt is slipping and wasting power!");
			}
		}
		if (m == MachineRegistry.DYNAMO) {
			TileEntityDynamo te = (TileEntityDynamo)tile;
			if ((te.torque > (te.isUpgraded() ? te.MAXTORQUE_UPGRADE : te.MAXTORQUE) || te.omega > te.MAXOMEGA))
				this.sendMessage(ep, "Conversion limits exceeded; Power is being wasted");
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox te = (TileEntityGearbox)tile;
			this.sendMessage(ep, String.format("Gearbox %d percent damaged; Lubricant Levels at %d", (int)(100*(1-ReikaMathLibrary.doubpow(0.99, te.getDamage()))), te.getLubricant()));
		}
		if (m == MachineRegistry.FRACTIONATOR) {
			TileEntityFractionator te = (TileEntityFractionator)tile;
			if (te.power >= te.MINPOWER && te.omega >= te.MINSPEED)
				this.sendMessage(ep, String.format("Efficiency: %.3f%%%%", te.getYieldRatio()*100));
		}

		if (m == null && tile instanceof IFluidHandler) {
			FluidTankInfo[] info = ((IFluidHandler)tile).getTankInfo(ForgeDirection.VALID_DIRECTIONS[s]);
			StringBuilder sb = new StringBuilder();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i] != null) {
						sb.append("Tank "+i+": ");
						FluidStack fs = info[i].fluid;
						if (fs != null && fs.getFluid() != null)
							sb.append(fs.amount+" mB of "+fs.getFluid().getLocalizedName()+"/"+info[i].capacity+" mB Capacity");
						else
							sb.append("Empty");
						sb.append("\n");
					}
				}
			}
			else {
				sb.append("No Tank Data");
			}

			this.sendMessage(ep, sb.toString());
		}

		return true;
	}

	private void sendLocalizedMessage(EntityPlayer ep, String s) {
		this.sendMessage(ep, StatCollector.translateToLocal("message."+s));
	}

	private void sendMessage(EntityPlayer ep, String s) {
		//ReikaChatHelper.writeString(s);
		ReikaChatHelper.sendChatToPlayer(ep, s);
	}
}
