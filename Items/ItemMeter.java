/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.EnumEngineType;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityPump;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

public class ItemMeter extends ItemRotaryTool
{
	public ItemMeter(int ID) {
		super(ID, 16);
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
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace clicked = (TileEntityBlastFurnace)world.getBlockTileEntity(x, y, z);
			if (clicked == null)
				return false;
			ReikaChatHelper.writeString(String.format("Temperature: %dC.", clicked.temperature));
			if (clicked.temperature < clicked.SMELTTEMP)
				ReikaChatHelper.writeString("Insufficient Temperature!");
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
					ReikaChatHelper.writeString(String.format("The ground is %.3f percent caves here.", ratio));
				}
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
				ReikaChatHelper.writeString(String.format("Water: %dkL. Lava: %dkL.", clicked.waterLevel, clicked.lavaLevel));
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
					ReikaChatHelper.writeString(String.format("Range %dm, dealing %ds of burn damage.", ReikaMathLibrary.extrema(8+(int)(power-clicked.MINPOWER)/clicked.FALLOFF, RotaryConfig.maxheatrayrange, "absmin"), 2+(int)(4*power/clicked.MINPOWER)));
				torque = omega = 0;
				if (power < clicked.MINPOWER)
					ReikaChatHelper.writeString("Insufficient Power!");
				return true;
			}
			if (m == MachineRegistry.FLOODLIGHT) {
				TileEntityFloodlight clicked = (TileEntityFloodlight)world.getBlockTileEntity(x, y, z);
				if (clicked == null)
					return false;
				torque = clicked.torque;
				omega = clicked.omega;
				power = torque*omega;
				if (power >= 1000000)
					ReikaChatHelper.writeString(String.format("Floodlight Receiving %.3f MW @ %d rad/s. Outputting light level 15.", power/1000000.0D, omega));
				if (power >= 1000 && power < 1000000)
					ReikaChatHelper.writeString(String.format("Floodlight Receiving %.3f kW @ %d rad/s. Outputting light level %d.", power/1000.0D, omega, ReikaMathLibrary.extrema(ReikaMathLibrary.extrema(-1+(int)power/1024, 0, "max"), 15, "absmin")));
				if (power < 1000)
					ReikaChatHelper.writeString(String.format("Floodlight Receiving %.3f W @ %d rad/s. Insufficient power!", power, omega));
				torque = omega = 0;
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
				if (clicked.getBlockMetadata() >= 8) {
					long energy = clicked.energy;
					if (energy >= 1000000000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f GJ.", energy/1000000000.0D, omega));
					if (energy >= 1000000 && energy < 1000000000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f MJ.", energy/1000000.0D, omega));
					if (energy >= 1000 && energy < 1000000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %.3f kJ.", energy/1000.0D, omega));
					if (energy < 1000)
						ReikaChatHelper.writeString(String.format("Stored Energy: %d J.", energy, omega));
					torque = omega = 0;
					return true;
				}
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
				lube = clicked.lubricant;
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

			try {
				if (tile.getClass().getField("temperature") != null) {
					ReikaChatHelper.write("Temperature: "+tile.getClass().getDeclaredField("temperature").getInt(tile)+" C.");
				}
			}catch (NoSuchFieldException e) {} catch (SecurityException e) {} catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {}
			try {
				if (tile.getClass().getField("pressure") != null) {
					ReikaChatHelper.write("Pressure: "+tile.getClass().getDeclaredField("pressure").getInt(tile)+" kPa.");
				}
			}catch (NoSuchFieldException e) {} catch (SecurityException e) {} catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {}

			power = omega*torque;
			if (power >= 1000000000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega));
			}
			if (power >= 1000000 && power < 1000000000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega));
			}
			if (power >= 1000 && power < 1000000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega));
			}
			if (power < 1000) {
				if (m == MachineRegistry.GEARBOX)
					ReikaChatHelper.writeString(String.format("%s Outputting %.3f W @ %d rad/s.", name, power, omega));
				if (m == MachineRegistry.SHAFT)
					ReikaChatHelper.writeString(String.format("%s Transmitting %.3f W @ %d rad/s.", name, power, omega));
			}
			if (m == MachineRegistry.GEARBOX) {
				ReikaChatHelper.writeString(String.format("Gearbox %d percent damaged. Lubricant Levels at %d.", (int)(100*(1-ReikaMathLibrary.doubpow(0.99, damage))), lube));
			}
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
