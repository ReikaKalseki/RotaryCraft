/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.ShaftMachine;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.API.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.PressureTE;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.ModInterface.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.TileEntityFuelConverter;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;

public class ItemMeter extends ItemRotaryTool
{
	public ItemMeter(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10)
	{
		ReikaChatHelper.clearChat();
		int ratioclicked = 1;
		String geartype = null;
		boolean reductionclicked = true;
		long torque = 0;
		long omega = 0;
		double power;
		int damage = -1;
		int lube = -453;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
			ReikaChatHelper.writeString(String.format("%s Temperature: %dC", th.getName(), th.getTemperature()));
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace clicked = (TileEntityBlastFurnace)world.getBlockTileEntity(x, y, z);
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("Temperature: %dC.", clicked.getTemperature()));
			if (clicked.getTemperature() < clicked.SMELTTEMP)
				ReikaChatHelper.writeString("Insufficient Temperature!");
			return true;
		}
		if (m == MachineRegistry.COOLINGFIN) {
			TileEntityCoolingFin clicked = (TileEntityCoolingFin)world.getBlockTileEntity(x, y, z);
			clicked.ticks = 512;
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir clicked = (TileEntityReservoir)world.getBlockTileEntity(x, y, z);
			if (!clicked.isEmpty())
				ReikaChatHelper.writeString(String.format("Reservoir contains %d mB of %s.", clicked.getLevel(), clicked.getFluid().getLocalizedName()));
			else
				ReikaChatHelper.writeString("Reservoir is empty.");
		}
		if (m == MachineRegistry.PIPE) {
			TileEntityPipe clicked = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
			if (clicked == null)
				return false;
			if (clicked.liquidID == -1 || clicked.liquidLevel <= 0) {
				ReikaChatHelper.writeString("Pipe contains no liquid.");
				return true;
			}
			ReikaChatHelper.writeString(String.format("Pipe contains %.3f m^3 of %s, with pressure %d kPa.", clicked.liquidLevel/(double)RotaryConfig.MILLIBUCKET, FluidRegistry.lookupFluidForBlock(Block.blocksList[clicked.liquidID]).getLocalizedName().toLowerCase(), clicked.fluidPressure));
			return true;
		}
		if (m == MachineRegistry.FUELLINE) {
			TileEntityFuelLine clicked = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z);
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("Fuel line contains %.3f L of fuel.", clicked.fuel/(double)RotaryConfig.MILLIBUCKET));
			return true;
		}
		if (m == MachineRegistry.HOSE) {
			TileEntityHose clicked = (TileEntityHose)world.getBlockTileEntity(x, y, z);
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("Hose contains %.3f L of lubricant.", clicked.lubricant/(double)RotaryConfig.MILLIBUCKET));
			return true;
		}
		if (tile instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)tile;
			ReikaChatHelper.writeString(String.format("%s producing %.3f kW @ %d rad/s.", sp.getName(), sp.getPower()/1000D, sp.getOmega()));
			return true;
		}
		if (tile instanceof ShaftPowerReceiver) {
			ShaftPowerReceiver sp = (ShaftPowerReceiver)tile;
			ReikaChatHelper.writeString(String.format("%s receiving %.3f kW @ %d rad/s.", sp.getName(), sp.getPower()/1000D, sp.getOmega()));
			return true;
		}
		if (tile instanceof TileEntityIOMachine) {

			((TileEntityIOMachine)tile).iotick = 512;
			world.markBlockForUpdate(x, y, z);
			if (m == MachineRegistry.ENGINE) {
				TileEntityEngine clicked = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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
				if (clicked.type.isAirBreathing() && clicked.isDrowned(world, x, y, z))
					ReikaChatHelper.write("Engine is Drowning!");
				if (clicked.type == EnumEngineType.JET && clicked.getChokedFraction(world, x, y, z, clicked.getBlockMetadata()) < 1)
					ReikaChatHelper.write("Engine Intake is Blocked!");
				if (clicked.FOD >= 8)
					ReikaChatHelper.writeString("Engine Destroyed from Animal Strikes!");
				if (clicked.type.isCooled() || clicked.isJetFailing) {
					ReikaChatHelper.writeString(String.format("Temperature: %dC", clicked.temperature));
				}
				if (clicked.type.burnsFuel()) {
					int time = clicked.getFuelDuration();
					String sg = String.format("Fuel Remaining: %s", ReikaFormatHelper.getSecondsAsClock(time));
					ReikaChatHelper.writeString(sg);
				}
				return true;
			}
			if (m == MachineRegistry.PLAYERDETECTOR) {
				TileEntityPlayerDetector clicked = (TileEntityPlayerDetector)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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
					ReikaChatHelper.writeString("Insufficient Power!");
				return true;

			}
			if (m == MachineRegistry.GPR) {
				TileEntityGPR clicked = (TileEntityGPR)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				if (ep.isSneaking() && clicked.power > clicked.MINPOWER) {
					double ratio = 100*clicked.getSpongy(world, x, y-1, z);
					ReikaChatHelper.writeString(String.format("The ground is %.3f%s caves here.", ratio, "%%"));
				}
				return true;
			}
			if (m == MachineRegistry.SOLARTOWER) {
				TileEntitySolar clicked = (TileEntitySolar)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				TileEntitySolar top = (TileEntitySolar)world.getBlockTileEntity(x, clicked.getTopOfTower(), z);
				TileEntitySolar bottom = (TileEntitySolar)world.getBlockTileEntity(x, clicked.getBottomOfTower(), z);
				ReikaChatHelper.writeString(String.format("Solar plant contains %d mirrors and %d active tower pieces.", top.getArraySize(), bottom.getTowerHeight()));
				ReikaChatHelper.writeString(String.format("Outputting %.3fkW at %d rad/s. Efficiency %.1f%s", bottom.power/1000D, bottom.omega, bottom.getArrayOverallBrightness()*100F, "%%"));
				return true;
			}
			if (m == MachineRegistry.WINDER) {
				TileEntityWinder clicked = (TileEntityWinder)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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
				TileEntityObsidianMaker clicked = (TileEntityObsidianMaker)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Obsidian Factory Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Obsidian Factory Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Obsidian Factory Receiving %.3f W @ %d rad/s.", power, omega));
				ReikaChatHelper.writeString(String.format("Water: %dkL. Lava: %dkL.", clicked.getWater(), clicked.getLava()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					ReikaChatHelper.writeString("Insufficient Power!");
				return true;
			}
			if (m == MachineRegistry.HEATRAY) {
				TileEntityHeatRay clicked = (TileEntityHeatRay)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Heat Ray Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Heat Ray Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Heat Ray Receiving %.3f W @ %d rad/s.", power, omega));
				if (power >= clicked.MINPOWER)
					ReikaChatHelper.writeString(String.format("Range %dm, dealing %ds of burn damage.", clicked.getRange(), clicked.getBurnTime()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					ReikaChatHelper.writeString("Insufficient Power!");
				return true;
			}
			if (m == MachineRegistry.BUCKETFILLER) {
				TileEntityBucketFiller clicked = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Bucket Filler Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Bucket Filler Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Bucket Filler Receiving %.3f W @ %d rad/s.", power, omega));
				if (power >= clicked.MINPOWER)
					ReikaChatHelper.writeString(String.format("Liquid Contents:\n%dmB of %s", clicked.getLevel(), clicked.getContainedFluid().getLocalizedName()));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					ReikaChatHelper.writeString("Insufficient Power!");
				return true;
			}
			if (m == MachineRegistry.PUMP) {
				TileEntityPump clicked = (TileEntityPump)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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
					ReikaChatHelper.writeString("Insufficient Power!");
				ReikaChatHelper.writeString(String.format("Liquid Pressure at %d kPa.", clicked.liquidPressure));
				return true;
			}
			if (m == MachineRegistry.ADVANCEDGEARS) {
				TileEntityAdvancedGear clicked = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				if (clicked.coil) {
					long energy = clicked.energy;
					if (energy/20D >= 1000000000)
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

				if (clicked.worm && power > 0) {
					double loss = 0;
					TileEntity read = world.getBlockTileEntity(clicked.readx, clicked.ready, clicked.readz);
					if (read instanceof TileEntityIOMachine) {
						TileEntityIOMachine io = (TileEntityIOMachine)read;
						loss = (1-((double)(clicked.omega*TileEntityAdvancedGear.WORMRATIO)/io.omega))*100;
						ReikaChatHelper.writeString(String.format("Power Loss: %.2f%s", loss, "%%"));
					}
				}
			}
			if (m == MachineRegistry.BEVELGEARS) {
				TileEntityGearBevel clicked = (TileEntityGearBevel)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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

				int dx = clicked.writex-clicked.xCoord;
				int dy = clicked.writey-clicked.yCoord;
				int dz = clicked.writez-clicked.zCoord;
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
				ratioclicked = 16;
				TileEntityAirCompressor clicked = (TileEntityAirCompressor)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				ReikaChatHelper.writeString(String.format("Air Compressor generating %.3f MJ/t.", clicked.power/ReikaBuildCraftHelper.getWattsPerMJ()));
			}
			if (m == MachineRegistry.FLYWHEEL) {
				ratioclicked = 16;
				TileEntityFlywheel clicked = (TileEntityFlywheel)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				ReikaChatHelper.writeString(String.format("Flywheel rotating at %d rad/s.", clicked.omega));
			}
			if (m == MachineRegistry.SHAFT) {
				ratioclicked = 1;
				TileEntityShaft clicked = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
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
				TileEntityGearbox clicked = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				torque = clicked.torque;
				omega = clicked.omega;
				ratioclicked = clicked.ratio;
				damage = clicked.damage;
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
					ReikaChatHelper.write("Insufficient Power! "+name+" requires "+te.MINPOWER+" W.");
				if (!te.machine.isMinPowerOnly()) {
					if (torque < te.MINTORQUE && !te.machine.hasNoDirectMinTorque())
						ReikaChatHelper.write("Insufficient Torque! "+name+" requires "+te.MINTORQUE+" Nm.");
					if (omega < te.MINSPEED && !te.machine.hasNoDirectMinSpeed())
						ReikaChatHelper.write("Insufficient Speed! "+name+" requires "+te.MINSPEED+" rad/s.");
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
				TileEntityFuelConverter clicked = (TileEntityFuelConverter)world.getBlockTileEntity(x, y, z);
				ReikaChatHelper.writeString(String.format("%s contains %.3f m^3 of fuel and %.3f m^3 of jet fuel.", clicked.getName(), clicked.getBCFuel()/1000D, clicked.getJetFuel()/1000D));
			}

			ReikaChatHelper.writeString(String.format("Power is being received from: %s", ((TileEntityIOMachine)tile).getPowerSources((TileEntityIOMachine) tile, null)));
		}

		if (tile instanceof TemperatureTE) {
			ReikaChatHelper.write("Temperature: "+((TemperatureTE)(tile)).getTemperature()+" C.");
		}
		if (tile instanceof PressureTE) {
			ReikaChatHelper.write("Pressure: "+((PressureTE)(tile)).getPressure()+" kPa.");
		}
		if (tile instanceof RangedEffect) {
			ReikaChatHelper.write("Range: "+((RangedEffect)(tile)).getRange()+" m. "+"Max Range: "+((RangedEffect)(tile)).getMaxRange()+" m.");
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

		return true;
	}
}
