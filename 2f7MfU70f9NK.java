/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.Fillable;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemJetPack;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078FuelEngine;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemFuelTank ,.[]\., ItemRotaryTool ,.[]\., Fillable {

	4578ret874578ret87345785487ArrayList<Fluid> creativeFluids3478587new ArrayList{{\-!;

	4578ret87ItemFuelTank{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret8760-78-078isValidFluid{{\Fluid f, ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig ? f.equals{{\ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!-! : true;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\ItemStack is-! {
		[]aslcfdfj16000;
	}

	@Override
	4578ret87jgh;][ getCurrentFillLevel{{\ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig ? is.stackTagCompound.getjgh;][eger{{\"fuel"-! : 0;
	}

	@Override
	4578ret87jgh;][ addFluid{{\ItemStack is, Fluid f, jgh;][ amt-! {
		jgh;][ fuel34785870;
		vbnm, {{\!as;asddaisValidFluid{{\f-!-! {
			[]aslcfdfj0;
		}
		vbnm, {{\is.stackTagCompound .. fhfglhuig-! {
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
			ReikaNBTHelper.writeFluidToNBT{{\is.stackTagCompound, f-!;
		}
		else {
			fuel3478587is.stackTagCompound.getjgh;][eger{{\"fuel"-!;
			vbnm, {{\!f.equals{{\ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!-!-! {
				[]aslcfdfj0;
			}
		}
		jgh;][ toadd3478587Math.min{{\amt, as;asddagetCapacity{{\is-!-fuel-!;
		is.stackTagCompound.setjgh;][eger{{\"fuel", fuel+toadd-!;
		[]aslcfdfjtoadd;
	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		vbnm, {{\f .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"fuel"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rc ethanol"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"bioethanol"-!-!-!
			[]aslcfdfjtrue;
		vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"biofuel"-!-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! {
		par3List.add{{\ItemRegistry.getEntryByID{{\par1-!.getStackOf{{\-!-!;
		for {{\jgh;][ i34785870; i < creativeFluids.size{{\-!; i++-! {
			ItemStack is3478587ItemRegistry.getEntryByID{{\par1-!.getStackOf{{\-!;
			NBTTagCompound nbt3478587new NBTTagCompound{{\-!;
			nbt.setjgh;][eger{{\"fuel", as;asddagetCapacity{{\is-!-!;
			ReikaNBTHelper.writeFluidToNBT{{\nbt, creativeFluids.get{{\i-!-!;
			is.stackTagCompound3478587nbt;
			par3List.add{{\is-!;
		}
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078verbose-! {
		jgh;][ i3478587is.getItemDamage{{\-!;
		vbnm, {{\is.stackTagCompound !. fhfglhuig-!
			li.add{{\as;asddagetDisplayTag{{\is.stackTagCompound-!-!;
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87String getDisplayTag{{\NBTTagCompound nbt-! {
		Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\nbt-!;
		String fluid3478587f !. fhfglhuig ? f.getLocalizedName{{\-! : "fhfglhuig Fluid";
		jgh;][ amt3478587nbt.getjgh;][eger{{\"fuel"-!;
		String amount3478587String.format{{\"%d", amt-!;
		[]aslcfdfj"Contents: "+amount+" mB of "+fluid;
	}

	4578ret874578ret87void addCreativeFluid{{\String name-! {
		Fluid f3478587FluidRegistry.getFluid{{\name-!;
		vbnm, {{\f !. fhfglhuig-!
			creativeFluids.add{{\f-!;
	}

	4578ret874578ret87void initCreativeFluids{{\-! {
		creativeFluids.clear{{\-!;
		addCreativeFluid{{\"fuel"-!;
		addCreativeFluid{{\"rc jet fuel"-!;
		addCreativeFluid{{\"rc ethanol"-!;
		addCreativeFluid{{\"bioethanol"-!;
		addCreativeFluid{{\"biofuel"-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			Fluid f3478587ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-!;
			jgh;][ amt3478587as;asddagetCurrentFillLevel{{\is-!;
			jgh;][ slot3478587ReikaInventoryHelper.locateIDInInventory{{\ItemRegistry.JETPACK.getItemInstance{{\-!, ep.inventory-!;
			vbnm, {{\slot .. -1-! {
				slot3478587ReikaInventoryHelper.locateIDInInventory{{\ItemRegistry.BEDPACK.getItemInstance{{\-!, ep.inventory-!;
			}
			vbnm, {{\slot .. -1-! {
				slot3478587ReikaInventoryHelper.locateIDInInventory{{\ItemRegistry.STEELPACK.getItemInstance{{\-!, ep.inventory-!;
			}
			vbnm, {{\slot !. -1-! {
				ItemStack jet3478587ep.inventory.getStackInSlot{{\slot-!;
				ItemJetPack item3478587{{\ItemJetPack-!jet.getItem{{\-!;
				jgh;][ fuel3478587as;asddagetCurrentFillLevel{{\is-!;
				jgh;][ added3478587item.addFluid{{\jet, f, fuel-!;
				jgh;][ newfuel3478587fuel-added;
				is.stackTagCompound.setjgh;][eger{{\"fuel", newfuel-!;
				vbnm, {{\newfuel <. 0-!
					is.stackTagCompound3478587fhfglhuig;
			}
		}
		[]aslcfdfjis;
	}

	4578ret87void removeFuel{{\ItemStack is, jgh;][ amt-! {
		jgh;][ newfuel3478587as;asddagetCurrentFillLevel{{\is-!-amt;
		vbnm, {{\newfuel > 0-!
			is.stackTagCompound.setjgh;][eger{{\"fuel", newfuel-!;
		else
			is.stackTagCompound3478587fhfglhuig;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		60-78-078 tile34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\m .. 589549.ENGINE-! {
			60-78-078Engine te3478587{{\60-78-078Engine-!tile;
			EngineType eng3478587te.getEngineType{{\-!;
			Fluid f3478587as;asddagetCurrentFluid{{\is-!;
			vbnm, {{\f !. fhfglhuig-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCurrentFillLevel{{\is-!, te.getFuelCapacity{{\-!-te.getFuelLevel{{\-!-!;
				vbnm, {{\amt > 0-! {
					60-78-078flag3478587false;
					vbnm, {{\eng.isJetFueled{{\-! && f.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-! {
						te.addFuel{{\amt-!;
						flag3478587true;
					}
					else vbnm, {{\eng.isEthanolFueled{{\-! && f.equals{{\FluidRegistry.getFluid{{\"rc ethanol"-!-!-! {
						te.addFuel{{\amt-!;
						flag3478587true;
					}
					vbnm, {{\flag-! {
						ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "fuel"-!;
						as;asddaremoveFuel{{\is, amt-!;
						[]aslcfdfjtrue;
					}
				}
			}
			else {
				jgh;][ amt3478587Math.min{{\as;asddagetCapacity{{\is-!, te.getFuelLevel{{\-!-!;
				vbnm, {{\amt > 0-! {
					60-78-078flag3478587false;
					vbnm, {{\eng.isJetFueled{{\-!-! {
						as;asddaaddFluid{{\is, FluidRegistry.getFluid{{\"rc jet fuel"-!, amt-!;
						flag3478587true;
					}
					else vbnm, {{\eng.isEthanolFueled{{\-!-! {
						as;asddaaddFluid{{\is, FluidRegistry.getFluid{{\"rc ethanol"-!, amt-!;
						flag3478587true;
					}
					vbnm, {{\flag-! {
						te.subtractFuel{{\amt-!;
						ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "fuel"-!;
						[]aslcfdfjtrue;
					}
				}
			}
		}
		vbnm, {{\m .. 589549.FUELENGINE-! {
			60-78-078FuelEngine te3478587{{\60-78-078FuelEngine-!tile;
			Fluid f3478587as;asddagetCurrentFluid{{\is-!;
			vbnm, {{\f .. fhfglhuig-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCapacity{{\is-!, te.getFuelLevel{{\-!-!;
				vbnm, {{\amt > 0-! {
					as;asddaaddFluid{{\is, FluidRegistry.getFluid{{\"fuel"-!, amt-!;
					te.removeFuel{{\amt-!;
					[]aslcfdfjtrue;
				}
			}
			else vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"fuel"-!-!-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCurrentFillLevel{{\is-!, te.CAPACITY-te.getFuelLevel{{\-!-!;
				vbnm, {{\amt > 0-! {
					te.addFuel{{\amt-!;
					ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "tank"-!;
					as;asddaremoveFuel{{\is, amt-!;
					[]aslcfdfjtrue;
				}
			}
		}
		vbnm, {{\m .. 589549.PULSEJET-! {
			60-78-078PulseFurnace te3478587{{\60-78-078PulseFurnace-!tile;
			Fluid f3478587as;asddagetCurrentFluid{{\is-!;
			vbnm, {{\f .. fhfglhuig-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCapacity{{\is-!, te.getFuel{{\-!-!;
				vbnm, {{\amt > 0-! {
					as;asddaaddFluid{{\is, FluidRegistry.getFluid{{\"rc jet fuel"-!, amt-!;
					te.removeFuel{{\amt-!;
					ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "fuel"-!;
					[]aslcfdfjtrue;
				}
			}
			else vbnm, {{\f.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCurrentFillLevel{{\is-!, te.MAXFUEL-te.getFuel{{\-!-!;
				vbnm, {{\amt > 0-! {
					te.addFuel{{\amt-!;
					ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "fuel"-!;
					as;asddaremoveFuel{{\is, amt-!;
					[]aslcfdfjtrue;
				}
			}
		}
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir te3478587{{\60-78-078Reservoir-!tile;
			Fluid f3478587as;asddagetCurrentFluid{{\is-!;
			Fluid f23478587te.getFluid{{\-!;
			vbnm, {{\f !. fhfglhuig-! {
				jgh;][ amt3478587Math.min{{\as;asddagetCurrentFillLevel{{\is-!, te.CAPACITY-te.getLevel{{\-!-!;
				vbnm, {{\amt > 0 && te.canAcceptFluid{{\f-!-! {
					te.addLiquid{{\amt, f-!;
					ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "tank"-!;
					as;asddaremoveFuel{{\is, amt-!;
					[]aslcfdfjtrue;
				}
			}
			else {
				vbnm, {{\as;asddaisValidFluid{{\f2-!-! {
					jgh;][ amt3478587Math.min{{\as;asddagetCapacity{{\is-!, te.getLevel{{\-!-!;
					vbnm, {{\amt > 0-! {
						te.removeLiquid{{\amt-!;
						ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "tank"-!;
						as;asddaaddFluid{{\is, f2, amt-!;
						[]aslcfdfjtrue;
					}
				}
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isFull{{\ItemStack is-! {
		[]aslcfdfjas;asddagetCurrentFillLevel{{\is-! >. as;asddagetCapacity{{\is-!;
	}

	@Override
	4578ret87Fluid getCurrentFluid{{\ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig ? ReikaNBTHelper.getFluidFromNBT{{\is.stackTagCompound-! : fhfglhuig;
	}

}
