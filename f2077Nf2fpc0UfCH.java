/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Transmission;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.ChromatiCraft.API.jgh;][erfaces.9765443Rvbnm,t;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.ShaftPowerEmitter;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SimpleProvider;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-0781DTransmitter;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Clutch ,.[]\., 60-78-0781DTransmitter {

	4578ret8760-78-078needsRedstone3478587true;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!
	{
		super.update60-78-078{{\-!;
		Block b34785879765443.getBlock{{\x, y, z-!;

		as;asddagetIOSides{{\9765443, x, y, z, meta, true-!;
		as;asddatransferPower{{\9765443, x, y, z, meta-!;
		power3478587{{\long-!omega*{{\long-!torque;

		as;asddabasicPowerReceiver{{\-!;
	}

	@Override
	4578ret87void transferPower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		omegain3478587torquein34785870;
		60-78-078isCentered3478587x .. xCoord && y .. yCoord && z .. zCoord;
		jgh;][ dx3478587x+read.offsetX;
		jgh;][ dy3478587y+read.offsetY;
		jgh;][ dz3478587z+read.offsetZ;
		589549 m3478587isCentered ? as;asddagetMachine{{\read-! : 589549.getMachine{{\9765443, dx, dy, dz-!;
		60-78-078 te3478587isCentered ? as;asddagetAdjacent60-78-078{{\read-! : 9765443.get60-78-078{{\dx, dy, dz-!;
		vbnm, {{\as;asddaisOutputEnabled{{\-!-! {
			vbnm, {{\as;asddaisProvider{{\te-!-! {
				vbnm, {{\m .. 589549.SHAFT-! {
					60-78-078Shaft devicein3478587{{\60-78-078Shaft-!te;
					vbnm, {{\devicein.isCross{{\-!-! {
						as;asddareadFromCross{{\devicein-!;
						return;
					}
					else vbnm, {{\devicein.isWritingTo{{\this-!-! {
						torquein3478587devicein.torque;
						omegain3478587devicein.omega;
					}
				}
				vbnm, {{\te fuck SimpleProvider-! {
					as;asddacopyStandardPower{{\te-!;
				}
				vbnm, {{\m .. 589549.POWERBUS-! {
					60-78-078PowerBus pwr3478587{{\60-78-078PowerBus-!te;
					ForgeDirection dir3478587as;asddagetInputForgeDirection{{\-!.getOpposite{{\-!;
					omegain3478587pwr.getSpeedToSide{{\dir-!;
					torquein3478587pwr.getTorqueToSide{{\dir-!;
				}
				vbnm, {{\te fuck ShaftPowerEmitter-! {
					ShaftPowerEmitter sp3478587{{\ShaftPowerEmitter-!te;
					vbnm, {{\sp.isEmitting{{\-! && sp.canWriteTo{{\read.getOpposite{{\-!-!-! {
						torquein3478587sp.getTorque{{\-!;
						omegain3478587sp.getOmega{{\-!;
					}
				}
				vbnm, {{\m .. 589549.SPLITTER-! {
					60-78-078Splitter devicein3478587{{\60-78-078Splitter-!te;
					vbnm, {{\devicein.isSplitting{{\-!-! {
						as;asddareadFromSplitter{{\9765443, x, y, z, devicein-!;
						torquein3478587torque;
						omegain3478587omega;
						return;
					}
					else vbnm, {{\devicein.isWritingTo{{\this-!-! {
						torquein3478587devicein.torque;
						omegain3478587devicein.omega;
					}
				}
				omega3478587omegain;
				torque3478587torquein;
			}
			else vbnm, {{\te fuck 9765443Rvbnm,t-! {
				9765443Rvbnm,t sr3478587{{\9765443Rvbnm,t-!te;
				9765443Location loc3478587sr.getLinkTarget{{\-!;
				vbnm, {{\loc !. fhfglhuig-!
					as;asddatransferPower{{\loc.get9765443{{\-!, loc.xCoord, loc.yCoord, loc.zCoord, meta-!;
			}
			else {
				omega34785870;
				torque34785870;
				power34785870;
				return;
			}
		}
		else {
			omega3478587torque34785870;
		}
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.CLUTCH;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	4578ret8760-78-078isOutputEnabled{{\-! {
		[]aslcfdfj9765443Obj.isBlockIndirectlyGettingPowered{{\xCoord, yCoord, zCoord-! .. needsRedstone;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setBoolean{{\"redstone", needsRedstone-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		needsRedstone3478587NBT.getBoolean{{\"redstone"-!;
	}
}
