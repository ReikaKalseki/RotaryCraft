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
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaDateHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Interfaces.PressureTile;
import Reika.RotaryCraft.API.Interfaces.Transducerable;
import Reika.RotaryCraft.API.Power.ShaftMachine;
import Reika.RotaryCraft.API.Power.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
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
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityDistributionClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
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
		if (world.isRemote && ConfigRegistry.CLEARCHAT.getState())
			ReikaChatHelper.clearChat();
		if (world.isRemote)
			return true;

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
			this.sendMessage(ep, String.format("%s %s: %s", name, Variables.TEMPERATURE, RotaryAux.formatTemperature(th.getTemperature())));
		}

		if (tile instanceof PressureTile) {
			PressureTile th = (PressureTile)tile;
			this.sendMessage(ep, String.format("%s %s: %s", name, Variables.PRESSURE, RotaryAux.formatPressure(th.getPressure())));
		}

		if (tile instanceof RangedEffect) {
			RangedEffect te = (RangedEffect)tile;
			this.sendMessage(ep, Variables.RANGE+": "+RotaryAux.formatDistance(te.getRange())+"; "+"Max Range: "+RotaryAux.formatDistance(te.getMaxRange()));
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
			this.sendMessage(ep, "Power is being received from:");
			this.sendMessages(ep, ((PowerSourceTracker)tile).getPowerSources((PowerSourceTracker)tile, null).getMessages());
		}

		if (tile instanceof TileEntityEngine) {
			TileEntityEngine te = (TileEntityEngine)tile;
			world.markBlockForUpdate(x, y, z);
			long power = te.power;
			this.sendMessage(ep, String.format("%s producing %s", name, RotaryAux.formatPowerIO(te)));
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
				String sg = String.format("%s: %s", Variables.FUEL, ReikaDateHelper.getSecondsAsClock(time));
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType().requiresLubricant()) {
				int amt = te.getLube();
				String sg = String.format("Lubricant: %s", RotaryAux.formatLiquidAmount(amt));
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType().isWaterPiped()) {
				int amt = te.getWater();
				String sg = String.format("Water: %s", RotaryAux.formatLiquidAmount(amt));
				this.sendMessage(ep, sg);
			}
			if (te.getEngineType() == EngineType.HYDRO) {
				if (((TileEntityHydroEngine)te).isStreamPowered()) {
					for (String sg : ((TileEntityHydroEngine)te).getStreamData()) {
						this.sendMessage(ep, sg);
					}
				}
				else {
					String sg = String.format("Detected waterfall height: %s", RotaryAux.formatDistance(((TileEntityHydroEngine)te).getWaterfallHeightForDisplay()));
					this.sendMessage(ep, sg);
				}
			}
		}
		else if (tile instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)tile;
			this.sendMessage(ep, String.format("%s producing %s", name, RotaryAux.formatPowerIO(sp)));
		}

		if (tile instanceof TileEntityPowerReceiver) {
			TileEntityPowerReceiver te = (TileEntityPowerReceiver)tile;
			long power = te.power;
			this.sendMessage(ep, String.format("%s receiving %s", name, RotaryAux.formatPowerIO(te)));
			if (power < te.MINPOWER)
				this.sendLocalizedMessage(ep, "minpower");
			if (power < te.MINSPEED)
				this.sendLocalizedMessage(ep, "mintorque");
			if (power < te.MINTORQUE)
				this.sendLocalizedMessage(ep, "minspeed");
		}
		else if (tile instanceof ShaftPowerReceiver) {
			ShaftPowerReceiver sp = (ShaftPowerReceiver)tile;
			this.sendMessage(ep, String.format("%s receiving %s", name, RotaryAux.formatPowerIO(sp)));
		}

		if (m == MachineRegistry.DISTRIBCLUTCH) {
			TileEntityDistributionClutch te = (TileEntityDistributionClutch)tile;
			ForgeDirection read = te.getInputForgeDirection();
			if (read != null) {
				String sname = ReikaStringParser.capFirstChar(read.name());
				long pwr = te.getInputTorque()*te.omega;
				double base = ReikaMathLibrary.getThousandBase(pwr);
				String pre = ReikaEngLibrary.getSIPrefix(pwr);
				this.sendMessage(ep, String.format("Input side %s receiving %.3f%sW @ %d rad/s", sname, base, pre, te.omega));
			}
			for (int i = 0; i < 4; i++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i+2];
				if (te.isOutputtingToSide(dir)) {
					String sname = ReikaStringParser.capFirstChar(dir.name());
					long out = te.getTorqueToSide(dir)*te.omega;
					double base = ReikaMathLibrary.getThousandBase(out);
					String pre = ReikaEngLibrary.getSIPrefix(out);

					if (dir == read.getOpposite()) {
						this.sendMessage(ep, String.format("Output to side %s: %.3f%sW (leftover)", sname, base, pre));
					}
					else {
						int req = te.getTorqueRequest(dir);
						double base2 = ReikaMathLibrary.getThousandBase(req);
						String pre2 = ReikaEngLibrary.getSIPrefix(req);
						this.sendMessage(ep, String.format("Output to side %s: %.3f%sW (requested %.3f%sNm)", sname, base, pre, base2, pre2));
					}
				}
			}
		}
		else if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter te = (TileEntitySplitter)tile;
			if (te.isSplitting()) {
				String sname = ReikaStringParser.capFirstChar(te.getReadDirection().name());
				long pwr = te.torque*te.omega;
				double base = ReikaMathLibrary.getThousandBase(pwr);
				String pre = ReikaEngLibrary.getSIPrefix(pwr);
				this.sendMessage(ep, String.format("Input side %s receiving %.3f%sW @ %d rad/s", sname, base, pre, te.omega));

				sname = ReikaStringParser.capFirstChar(te.getWriteDirection().name());
				long out = te.torqueOut1*te.omega;
				base = ReikaMathLibrary.getThousandBase(out);
				pre = ReikaEngLibrary.getSIPrefix(out);
				this.sendMessage(ep, String.format("Output to side %s: %.3f%sW @ %d rad/s", sname, base, pre, te.omega));

				sname = ReikaStringParser.capFirstChar(te.getWriteDirection2().name());
				out = te.torqueOut2*te.omega;
				base = ReikaMathLibrary.getThousandBase(out);
				pre = ReikaEngLibrary.getSIPrefix(out);
				this.sendMessage(ep, String.format("Output to side %s: %.3f%sW @ %d rad/s", sname, base, pre, te.omega));
			}
			else {
				String sname = ReikaStringParser.capFirstChar(te.getReadDirection().name());
				long pwr = te.getInputTorque1()*te.getInputSpeed1();
				double base = ReikaMathLibrary.getThousandBase(pwr);
				String pre = ReikaEngLibrary.getSIPrefix(pwr);
				this.sendMessage(ep, String.format("Input side %s receiving %.3f%sNm @ %d rad/s", sname, base, pre, te.omega));

				pwr = te.getInputTorque2()*te.getInputSpeed2();
				sname = ReikaStringParser.capFirstChar(te.getReadDirection().name());
				base = ReikaMathLibrary.getThousandBase(pwr);
				pre = ReikaEngLibrary.getSIPrefix(pwr);
				this.sendMessage(ep, String.format("Input side %s receiving %.3f%sW @ %d rad/s", sname, base, pre, te.omega));

				sname = ReikaStringParser.capFirstChar(te.getWriteDirection().name());
				long out = te.torque*te.omega;
				base = ReikaMathLibrary.getThousandBase(out);
				pre = ReikaEngLibrary.getSIPrefix(out);
				this.sendMessage(ep, String.format("Output to side %s: %.3f%sW @ %d rad/s", sname, base, pre, te.omega));
			}

			if (te.isBedrock()) {
				//this.sendMessage(ep, String.format("%s is upgraded.", te.getName()));
			}
		}
		else if (tile instanceof TileEntityTransmissionMachine) {
			TileEntityTransmissionMachine te = (TileEntityTransmissionMachine)tile;
			this.sendMessage(ep, String.format("%s transmitting %s", name, RotaryAux.formatPowerIO(te)));
		}

		if (tile instanceof RCToModConverter) {
			RCToModConverter te = (RCToModConverter)tile;
			int units = te.getGeneratedUnitsPerTick();
			String unit = te.getUnitDisplay();
			this.sendMessage(ep, String.format("Generating %d %s/t", units, unit));
		}

		if (tile instanceof EnergyToPowerBase) {
			EnergyToPowerBase te = (EnergyToPowerBase)tile;
			int units = te.getConsumedUnitsPerTick();
			String unit = te.getUnitDisplay();
			this.sendMessage(ep, String.format("%s Outputting %s", name, RotaryAux.formatPowerIO(te)));
			this.sendMessage(ep, String.format("Consuming %d %s/t", units, unit));
		}

		if (tile instanceof TileEntityPiping) {
			TileEntityPiping te = (TileEntityPiping)tile;
			Fluid f = te.getFluidType();
			if (f != null)
				this.sendMessage(ep, String.format("Pipe has %s of %s", RotaryAux.formatLiquidAmount(te.getFluidLevel()), f.getLocalizedName()));
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
				this.sendMessage(ep, String.format("Outputting %s; Efficiency %.1f%s", RotaryAux.formatPowerIO(bottom), bottom.getArrayOverallBrightness()*100F, "%%"));
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
				this.sendMessage(ep, String.format("Stored Energy: %s", RotaryAux.formatEnergy(energy)));
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
			this.sendMessage(ep, String.format("Gearbox %d percent damaged; Lubricant Levels at %s", (int)(100*(1-ReikaMathLibrary.doubpow(0.99, te.getDamage()))), RotaryAux.formatLiquidAmount(te.getLubricant())));
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
							sb.append(RotaryAux.formatLiquidAmount(fs.amount)+" of "+fs.getFluid().getLocalizedName()+"/"+RotaryAux.formatLiquidAmount(info[i].capacity)+" Capacity");
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

	private void sendMessages(EntityPlayer ep, Collection<String> s) {
		for (String sg : s) {
			this.sendMessage(ep, sg);
		}
	}
}
