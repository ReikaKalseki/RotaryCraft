/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftMachine;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.API.ThermalMachine;
import Reika.RotaryCraft.API.Transducerable;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Variables;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.ModInterface.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.TileEntityDynamo;
import Reika.RotaryCraft.ModInterface.TileEntityFuelConverter;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHose;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemMeter extends ItemRotaryTool
{
	public ItemMeter(int tex) {
		super(tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10)
	{
		if (ConfigRegistry.CLEARCHAT.getState())
			ReikaChatHelper.clearChat();
		int ratioclicked = 1;
		String geartype = null;
		boolean reductionclicked = true;
		long torque = 0;
		long omega = 0;
		double power;
		int damage = -1;
		int lube = -453;
		TileEntity tile = world.getTileEntity(x, y, z);
		Block b = world.getBlock(x, y, z);
		String name;
		if (tile instanceof RotaryCraftTileEntity)
			name = ((RotaryCraftTileEntity)tile).getMultiValuedName();
		else
			name = "";
		if (tile instanceof ShaftMachine) {
			ShaftMachine sm = (ShaftMachine)tile;
			sm.setIORenderAlpha(512);
		}
		if (tile instanceof ThermalMachine) {
			ThermalMachine th = (ThermalMachine)tile;
			ReikaChatHelper.writeString(String.format("%s %s: %dC", th.getName(), Variables.TEMPERATURE, th.getTemperature()));
		}
		boolean flag = false;
		boolean flag1 = false;
		if (tile instanceof Transducerable) {
			ArrayList<String> li = ((Transducerable)tile).getMessages(world, x, y, z, s);
			if (li != null) {
				for (int i = 0; i < li.size(); i++)
					ReikaChatHelper.writeString(li.get(i));
			}
			//flag = tile instanceof ShaftPowerEmitter;
			//flag1 = tile instanceof ShaftPowerReceiver;
		}
		else if (b instanceof Transducerable) {
			ArrayList<String> li = ((Transducerable)b).getMessages(world, x, y, z, s);
			if (li != null) {
				for (int i = 0; i < li.size(); i++)
					ReikaChatHelper.writeString(li.get(i));
			}
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace clicked = (TileEntityBlastFurnace)tile;
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("%s: %dC.", Variables.TEMPERATURE, clicked.getTemperature()));
			if (clicked.getTemperature() < clicked.SMELTTEMP)
				RotaryAux.writeMessage("mintemp");
			return true;
		}
		if (m == MachineRegistry.COOLINGFIN) {
			TileEntityCoolingFin clicked = (TileEntityCoolingFin)tile;
			clicked.ticks = 512;
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir clicked = (TileEntityReservoir)tile;
			if (!clicked.isEmpty())
				ReikaChatHelper.writeString(String.format("Reservoir contains %d mB of %s.", clicked.getLevel(), clicked.getFluid().getLocalizedName()));
			else
				RotaryAux.writeMessage("emptyres");
		}
		if (m == MachineRegistry.GASTANK) {
			TileEntityFluidCompressor clicked = (TileEntityFluidCompressor)tile;
			if (clicked.isEmpty())
				ReikaChatHelper.writeString(String.format("%s is empty.", m.getName()));
			else
				ReikaChatHelper.writeString(String.format("%s contains %.3fB of %s.", m.getName(), clicked.getLevel()/1000D, clicked.getFluid().getLocalizedName()));
		}
		if (m == MachineRegistry.PIPE) {
			TileEntityPipe clicked = (TileEntityPipe)tile;
			if (clicked == null)
				return false;
			if (clicked.getFluidType() == null || clicked.getFluidLevel() <= 0) {
				RotaryAux.writeMessage("emptypipe");
				return true;
			}
			ReikaChatHelper.writeString(String.format("%s contains %.3f m^3 of %s, with %s %.3f kPa.", m.getName(), clicked.getFluidLevel()/1000D, clicked.getFluidType().getLocalizedName().toLowerCase(), Variables.PRESSURE, clicked.getPressure()/1000D));
			return true;
		}
		if (m == MachineRegistry.FUELLINE) {
			TileEntityFuelLine clicked = (TileEntityFuelLine)tile;
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("%s contains %.3f m^3 of fuel.", m.getName(), clicked.getFluidLevel()/1000D));
			return true;
		}
		if (m == MachineRegistry.HOSE) {
			TileEntityHose clicked = (TileEntityHose)tile;
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("%s contains %.3f m^3 of lubricant.", m.getName(), clicked.getFluidLevel()/1000D));
			return true;
		}/*
		if (m == MachineRegistry.HYDRAULICLINE) {
			TileEntityHydraulicLine clicked = (TileEntityHydraulicLine)tile;
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("%s carrying %dmB/s of hydraulic fluid at %s %d kPa.", m.getName(), clicked.getFlowRate(), Variables.PRESSURE, clicked.getPressure()));
			return true;
		}*/
		if (!flag && tile instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)tile;
			power = sp.getPower();
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			ReikaChatHelper.writeString(String.format("%s producing %.3f %sW @ %d rad/s.", sp.getName(), base, pre, sp.getOmega()));
			return true;
		}
		if (!flag1 && tile instanceof ShaftPowerReceiver) {
			ShaftPowerReceiver sp = (ShaftPowerReceiver)tile;
			power = sp.getPower();
			String pre = ReikaEngLibrary.getSIPrefix(power);
			double base = ReikaMathLibrary.getThousandBase(power);
			ReikaChatHelper.writeString(String.format("%s receiving %.3f %sW @ %d rad/s.", sp.getName(), base, pre, sp.getOmega()));
			return true;
		}
		if (tile instanceof TileEntityIOMachine) {

			((TileEntityIOMachine)tile).iotick = 512;
			world.markBlockForUpdate(x, y, z);
			if (m == MachineRegistry.ENGINE) {
				TileEntityEngine clicked = (TileEntityEngine)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				clicked.iotick = 512;
				world.markBlockForUpdate(x, y, z);
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f W @ %d rad/s.", name, power, omega));
				torque = omega = 0;
				if (clicked.getEngineType().isAirBreathing() && clicked.isDrowned(world, x, y, z))
					RotaryAux.writeMessage("drowning");
				if (clicked.getEngineType() == EngineType.JET) {
					if (((TileEntityJetEngine)clicked).getChokedFraction(world, x, y, z, clicked.getBlockMetadata()) < 1)
						RotaryAux.writeMessage("choke");
					if (((TileEntityJetEngine)clicked).FOD >= 8)
						RotaryAux.writeMessage("fod");
				}
				if (clicked.hasTemperature()) {
					ReikaChatHelper.writeString(String.format("%s: %dC", Variables.TEMPERATURE, clicked.temperature));
				}
				if (clicked.getEngineType().burnsFuel()) {
					int time = clicked.getFuelDuration();
					String sg = String.format("%s: %s", Variables.FUEL, ReikaFormatHelper.getSecondsAsClock(time));
					ReikaChatHelper.writeString(sg);
				}
				if (clicked.getEngineType().requiresLubricant()) {
					int amt = clicked.getLube();
					String sg = String.format("Lubricant: %d mB", amt);
					ReikaChatHelper.writeString(sg);
				}
				if (clicked.getEngineType().isWaterPiped()) {
					int amt = clicked.getWater();
					String sg = String.format("Water: %d mB", amt);
					ReikaChatHelper.writeString(sg);
				}
				return true;
			}
			if (m == MachineRegistry.PLAYERDETECTOR) {
				TileEntityPlayerDetector clicked = (TileEntityPlayerDetector)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Detector Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Detector Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Detector Receiving %.3f W @ %d rad/s.", power, omega));
				torque = omega = 0;
				ReikaChatHelper.writeString(String.format("Maximum Range: %dm. Reaction Time: %.2fs", clicked.getMaxRange(), clicked.getReactionTime()/20F));
				if (power < clicked.MINPOWER)
					RotaryAux.writeMessage("minpower");
				return true;

			}
			if (m == MachineRegistry.GPR) {
				TileEntityGPR clicked = (TileEntityGPR)tile;
				if (ep.isSneaking() && clicked.power > clicked.MINPOWER) {
					double ratio = 100*clicked.getSpongy(world, x, y-1, z);
					ReikaChatHelper.writeString(String.format("The ground is %.3f%s caves here.", ratio, "%%"));
				}
				return true;
			}
			if (m == MachineRegistry.SOLARTOWER) {
				TileEntitySolar clicked = (TileEntitySolar)tile;
				TileEntitySolar top = (TileEntitySolar)world.getTileEntity(x, clicked.getTopOfTower(), z);
				TileEntitySolar bottom = (TileEntitySolar)world.getTileEntity(x, clicked.getBottomOfTower(), z);
				ReikaChatHelper.writeString(String.format("Solar plant contains %d mirrors and %d active tower pieces.", top.getArraySize(), bottom.getTowerHeight()));
				ReikaChatHelper.writeString(String.format("Outputting %.3fkW at %d rad/s. Efficiency %.1f%s", bottom.power/1000D, bottom.omega, bottom.getArrayOverallBrightness()*100F, "%%"));
				return true;
			}
			if (m == MachineRegistry.WINDER) {
				TileEntityWinder clicked = (TileEntityWinder)tile;
				String text;
				if (clicked.winding)
					text = "Receiving";
				else
					text = "Outputting";
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Winder %s %.3f MW @ %d rad/s.", text, power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Winder %s %.3f kW @ %d rad/s.", text, power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Winder %s %.3f W @ %d rad/s.", text, power, omega));
				torque = omega = 0;
				return true;
			}
			if (m == MachineRegistry.OBSIDIAN) {
				TileEntityObsidianMaker clicked = (TileEntityObsidianMaker)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f MW @ %d rad/s.", m.getName(), power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f kW @ %d rad/s.", m.getName(), power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f W @ %d rad/s.", m.getName(), power, omega));
				ReikaChatHelper.writeString(String.format("Water: %dmB. Lava: %dmB.", clicked.getWater(), clicked.getLava()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					RotaryAux.writeMessage("minpower");
				return true;
			}
			if (m == MachineRegistry.HEATRAY) {
				TileEntityHeatRay clicked = (TileEntityHeatRay)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f MW @ %d rad/s.", m.getName(), power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f kW @ %d rad/s.", m.getName(), power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f W @ %d rad/s.", m.getName(), power, omega));
				if (power >= clicked.MINPOWER)
					ReikaChatHelper.writeString(String.format("%s %dm, dealing %ds of burn damage.", Variables.RANGE.toString(), clicked.getRange(), clicked.getBurnTime()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					RotaryAux.writeMessage("minpower");
				return true;
			}
			if (m == MachineRegistry.BUCKETFILLER) {
				TileEntityBucketFiller clicked = (TileEntityBucketFiller)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f MW @ %d rad/s.", m.getName(), power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f kW @ %d rad/s.", m.getName(), power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("%s Receiving %.3f W @ %d rad/s.", m.getName(), power, omega));
				if (power >= clicked.MINPOWER)
					ReikaChatHelper.writeString(String.format("Liquid Contents:\n%dmB of %s", clicked.getLevel(), clicked.getContainedFluid().getLocalizedName()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					RotaryAux.writeMessage("minpower");
				return true;
			}
			if (m == MachineRegistry.PUMP) {
				TileEntityPump clicked = (TileEntityPump)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Pump Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Pump Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Pump Receiving %.3f W @ %d rad/s.", power, omega));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					RotaryAux.writeMessage("minpower");
				return true;
			}
			if (m == MachineRegistry.ADVANCEDGEARS) {
				TileEntityAdvancedGear clicked = (TileEntityAdvancedGear)tile;
				if (clicked.getGearType().storesEnergy()) {
					long energy = clicked.getEnergy();
					if (energy/20D >= 1000000000000L)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f TJ.", energy/20D/1000000000000.0D, omega));
					else if (energy/20D >= 1000000000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f GJ.", energy/20D/1000000000.0D, omega));
					else if (energy/20D >= 1000000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f MJ.", energy/20D/1000000.0D, omega));
					else if (energy/20D >= 1000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f kJ.", energy/20D/1000.0D, omega));
					else
						ReikaChatHelper.writeString(String.format("Stored Energy: %d J.", energy, omega));
					torque = omega = 0;
					return true;
				}

				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;

				if (power >= 1000000000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
				}
				else if (power >= 1000000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				}
				else if (power >= 1000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				}
				else {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f W @ %d rad/s.", name, power, omega));
				}

				if (clicked.getGearType() == TileEntityAdvancedGear.GearType.WORM && power > 0) {
					ReikaChatHelper.writeString(String.format("Power Loss: %.2f%s", clicked.getCurrentLoss(), "%%"));
				}
			}
			if (m == MachineRegistry.BEVELGEARS) {
				TileEntityBevelGear clicked = (TileEntityBevelGear)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
				}
				else if (power >= 1000000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				}
				else if (power >= 1000) {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				}
				else {
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f W @ %d rad/s.", name, power, omega));
				}

				int dx = clicked.getWriteDirection().offsetX;
				int dy = clicked.getWriteDirection().offsetY;
				int dz = clicked.getWriteDirection().offsetZ;
				String sdx = String.valueOf(dx);
				String sdy = String.valueOf(dy);
				String sdz = String.valueOf(dz);
				if (dx >= 0)
					sdx = "+"+sdx;
				if (dy >= 0)
					sdy = "+"+sdy;
				if (dz >= 0)
					sdz = "+"+sdz;
				ReikaChatHelper.writeString(String.format("Output side: x%s : y%s : z%s", sdx, sdy, sdz));
			}
			if (m == MachineRegistry.COMPRESSOR) {
				TileEntityAirCompressor clicked = (TileEntityAirCompressor)tile;
				ReikaChatHelper.writeString(String.format("%s generating %.3f MJ/t.", clicked.getName(), clicked.getGenMJ()));
			}
			if (tile instanceof EnergyToPowerBase) {
				EnergyToPowerBase te = (EnergyToPowerBase)tile;
				double p3 = ReikaMathLibrary.getThousandBase(te.power);
				String pe = ReikaEngLibrary.getSIPrefix(te.power);
				int units = te.getConsumedUnitsPerTick();
				String unit = te.getUnitDisplay();
				ReikaChatHelper.writeString(String.format("%s Outputting %.3f%sW @ %d rad/s.", name, p3, pe, omega));
				ReikaChatHelper.writeString(String.format("Consuming %d %s/t.", units, unit));
				return true;
			}
			if (m == MachineRegistry.BORER) {
				TileEntityBorer clicked = (TileEntityBorer)tile;
				ReikaChatHelper.writeString(String.format("%s head at %d, %d", clicked.getName(), clicked.getHeadX(), clicked.getHeadZ()));
				if (clicked.isJammed())
					ReikaChatHelper.writeString(String.format("%s is jammed, supply more torque or power!", clicked.getName()));
			}
			if (m == MachineRegistry.BELT || m == MachineRegistry.CHAIN) {
				TileEntityBeltHub clicked = (TileEntityBeltHub)tile;
				if (clicked.isSplitting()) {
					torque = ((TileEntityPowerReceiver)tile).torque*2;
					omega = ((TileEntityPowerReceiver)tile).omega;
					power = torque*omega;
					if (power >= 1000000)
						ReikaChatHelper.writeString(name+String.format(" Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
					if (power >= 1000 && power < 1000000)
						ReikaChatHelper.writeString(name+String.format(" Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
					if (power < 1000)
						ReikaChatHelper.writeString(name+String.format(" Receiving %.3f W @ %d rad/s.", power, omega));
					return false;
				}
			}
			if (m == MachineRegistry.FUELENGINE) {
				TileEntityFuelEngine clicked = (TileEntityFuelEngine)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				clicked.iotick = 512;
				world.markBlockForUpdate(x, y, z);
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f W @ %d rad/s.", name, power, omega));
				torque = omega = 0;
			}
			if (m == MachineRegistry.DYNAMO) {
				TileEntityDynamo clicked = (TileEntityDynamo)tile;
				ReikaChatHelper.writeString(String.format("%s generating %d RF/t.", clicked.getName(), clicked.getGenRF()));
				if ((clicked.torque > clicked.MAXTORQUE || clicked.omega > clicked.MAXOMEGA))
					ReikaChatHelper.writeString("Conversion limits exceeded; Power is being wasted.");
			}
			if (m == MachineRegistry.FLYWHEEL) {
				ratioclicked = 16;
				TileEntityFlywheel clicked = (TileEntityFlywheel)tile;
				ReikaChatHelper.writeString(String.format("Flywheel rotating at %d rad/s.", clicked.omega));
			}
			if (m == MachineRegistry.SHAFT) {
				ratioclicked = 1;
				TileEntityShaft clicked = (TileEntityShaft)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				if (clicked.getBlockMetadata() >= 6) {
					power = ReikaMathLibrary.extrema(clicked.readtorque[0]*clicked.readomega[0], clicked.readtorque[1]*clicked.readomega[1], "max");
					if (power >= 1000000)
						ReikaChatHelper.writeString(String.format("Shaft Transmitting %.3f MW and %.3f MW\nat %d rad/s and %d rad/s.", (double)clicked.readtorque[0]*clicked.readomega[0]/1000000D, (double)clicked.readtorque[1]*clicked.readomega[1]/1000000D, clicked.readomega[0], clicked.readomega[1]));
					if (power >= 1000 && power < 1000000)
						ReikaChatHelper.writeString(String.format("Shaft Transmitting %.3f kW and %.3f kW\nat %d rad/s and %d rad/s.", (double)clicked.readtorque[0]*clicked.readomega[0]/1000D, (double)clicked.readtorque[1]*clicked.readomega[1]/1000D, clicked.readomega[0], clicked.readomega[1]));
					if (power < 1000)
						ReikaChatHelper.writeString(String.format("Shaft Transmitting %d W and %d W\nat %d rad/s and %d rad/s.", clicked.readtorque[0]*clicked.readomega[0], clicked.readtorque[1]*clicked.readomega[1], clicked.readomega[0], clicked.readomega[1]));
					torque = omega = 0;
					return true;
				}
			}

			if (m == MachineRegistry.GEARBOX) { // gearboxes
				TileEntityGearbox clicked = (TileEntityGearbox)tile;
				torque = clicked.torque;
				omega = clicked.omega;
				ratioclicked = clicked.getRatio();
				damage = clicked.getDamage();
				lube = clicked.getLubricant();
				reductionclicked = clicked.reduction;
				if (reductionclicked)
					geartype = "Reduction";
				else
					geartype = "Acceleration";
			}
			if (tile instanceof TileEntityPowerReceiver) {
				TileEntityPowerReceiver te = (TileEntityPowerReceiver)tile;
				power = te.power;
				omega = te.omega;
				torque = te.torque;
				if (power < te.MINPOWER)
					ReikaChatHelper.write(RotaryAux.getMessage("minpower")+" "+name+" requires "+te.MINPOWER+" W.");
				if (!te.machine.isMinPowerOnly()) {
					if (torque < te.MINTORQUE && !te.machine.hasNoDirectMinTorque())
						ReikaChatHelper.writeString(RotaryAux.getMessage("mintorque")+" "+name+" requires "+te.MINTORQUE+" Nm.");
					if (omega < te.MINSPEED && !te.machine.hasNoDirectMinSpeed())
						ReikaChatHelper.writeString(RotaryAux.getMessage("minspeed")+" "+name+" requires "+te.MINSPEED+" rad/s.");
				}
			}

			power = omega*torque;
			if (power >= 1000000000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
			}
			else if (power >= 1000000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
			}
			else if (power >= 1000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
			}
			else {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f W @ %d rad/s.", name, power, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f W @ %d rad/s.", name, power, omega));
			}
			if (m == MachineRegistry.GEARBOX) {
				ReikaChatHelper.writeString(String.format("Gearbox %d percent damaged. Lubricant Levels at %d.", (int)(100*(1-ReikaMathLibrary.doubpow(0.99, damage))), lube));
			}
			if (m == MachineRegistry.FUELENHANCER) {
				TileEntityFuelConverter clicked = (TileEntityFuelConverter)tile;
				ReikaChatHelper.writeString(String.format("%s contains %.3f m^3 of fuel and %.3f m^3 of jet fuel.", clicked.getName(), clicked.getBCFuel()/1000D, clicked.getJetFuel()/1000D));
			}

			ReikaChatHelper.writeString(String.format("Power is being received from: %s", ((TileEntityIOMachine)tile).getPowerSources((TileEntityIOMachine) tile, null)));
		}

		if (tile instanceof TemperatureTE) {
			ReikaChatHelper.writeString(Variables.TEMPERATURE+": "+((TemperatureTE)(tile)).getTemperature()+" C.");
		}
		if (tile instanceof PressureTE) {
			ReikaChatHelper.writeString(Variables.PRESSURE+": "+((PressureTE)(tile)).getPressure()+" kPa.");
		}
		if (tile instanceof RangedEffect) {
			ReikaChatHelper.writeString(Variables.RANGE+": "+((RangedEffect)(tile)).getRange()+" m. "+"Max Range: "+((RangedEffect)(tile)).getMaxRange()+" m.");
		}
		//ReikaChatHelper.writeString(String.format("Clicked coords at %d, %d, %d; ID %d.", x, y, z, m));
		if (tile instanceof TileEntityPowerReceiver) {
			((TileEntityPowerReceiver)tile).iotick = 512;
			//ReikaChatHelper.writeString(String.format("%d", ((TileEntityPowerReceiver)tile).iotick));
			torque = ((TileEntityPowerReceiver)tile).torque;
			omega = ((TileEntityPowerReceiver)tile).omega;
			power = torque*omega;
			if (power >= 1000000)
				ReikaChatHelper.writeString(name+String.format(" Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
			if (power >= 1000 && power < 1000000)
				ReikaChatHelper.writeString(name+String.format(" Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
			if (power < 1000)
				ReikaChatHelper.writeString(name+String.format(" Receiving %.3f W @ %d rad/s.", power, omega));
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
				sb.append("No Tank Data.");
			}

			ReikaChatHelper.writeString(sb.toString());
		}

		return true;
	}
}