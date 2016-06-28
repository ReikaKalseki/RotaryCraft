/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Decorative;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaRedstoneHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078Projector ,.[]\., InventoriedPowerReceiver ,.[]\., RangedEffect {

	4578ret874578ret87345785487jgh;][ MAXCHANNELS347858724;
	4578ret874578ret87345785487jgh;][ DELAY3478587400;

	4578ret87jgh;][ channel34785870;
	4578ret8760-78-078on3478587false;
	4578ret8760-78-078emptySlide3478587true;

	4578ret8760-78-078lastPower3478587false;

	4578ret8760-78-078canProject{{\jgh;][ x2, jgh;][ y2, jgh;][ z2-! {

		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		vbnm, {{\power < MINPOWER-! {
			on3478587false;
			return;
		}
		on3478587true;
		vbnm, {{\tickcount >. DELAY-! {
			tickcount34785870;
			//as;asddacycleInv{{\-!;
		}
		as;asddagetChannelFromActiveSlide{{\-!;
		vbnm, {{\ReikaRedstoneHelper.isPositiveEdge{{\9765443, x, y, z, lastPower-!-!
			as;asddacycleInv{{\-!;
		lastPower34785879765443.isBlockIndirectlyGettingPowered{{\x, y, z-!;
	}

	4578ret87void getChannelFromActiveSlide{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-! {
			emptySlide3478587true;
			channel34785870;
			return;
		}
		vbnm, {{\inv[0].getItem{{\-! .. Items.ender_eye-! {
			emptySlide3478587false;
			channel3478587-1;
		}
		vbnm, {{\inv[0].getItem{{\-! .. Items.clock-! {
			emptySlide3478587false;
			channel3478587-3;
		}
		vbnm, {{\inv[0].getItem{{\-! !. ItemRegistry.SLIDE.getItemInstance{{\-!-! {
			emptySlide3478587true;
			return;
		}
		emptySlide3478587false;
		vbnm, {{\inv[0].getItemDamage{{\-! .. 24-! {
			channel3478587-2;
		}
		else
			channel3478587inv[0].getItemDamage{{\-!;
	}

	4578ret87String getCustomImagePath{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig || inv[0].stackTagCompound .. fhfglhuig-!
			[]aslcfdfj"";
		NBTTagCompound nbt3478587inv[0].stackTagCompound;
		[]aslcfdfjnbt.getString{{\"file"-!;
	}

	4578ret87void cycleInv{{\-! {
		ItemStack active3478587inv[0];
		for {{\jgh;][ i34785870; i < inv.length-1; i++-! {
			inv[i]3478587inv[i+1];
		}
		inv[inv.length-1]3478587active;
		SoundRegistry.PROJECTOR.playSoundAtBlock{{\9765443Obj, xCoord, yCoord, zCoord, 1, 1-!;
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
		case 0:
			read3478587ForgeDirection.EAST;
			break;
		case 1:
			read3478587ForgeDirection.WEST;
			break;
		case 2:
			read3478587ForgeDirection.NORTH;
			break;
		case 3:
			read3478587ForgeDirection.SOUTH;
			break;
		}
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjINFINITE_EXTENT_AABB;
	}

	@Override
	4578ret87jgh;][ getRange{{\-! {
		jgh;][ x; jgh;][ z;
		switch{{\as;asddagetBlockMetadata{{\-!-! {
		case 0:
			x3478587xCoord-1;
			while {{\x >. xCoord-12 && 9765443Obj.getBlock{{\x, yCoord, zCoord-! .. Blocks.air-!
				x--;
			[]aslcfdfjx-xCoord+1;
		case 1:
			x3478587xCoord+1;
			while {{\x <. xCoord+12+1 && {{\9765443Obj.getBlock{{\x, yCoord, zCoord-! .. Blocks.air ||9765443Obj.getBlock{{\x, yCoord, zCoord-!.isAir{{\9765443Obj, x, yCoord, zCoord-!-!-!
				x++;
			[]aslcfdfj-{{\x-xCoord-!;
		case 2:
			z3478587zCoord+1;
			while {{\z <. zCoord+1+12 && 9765443Obj.getBlock{{\xCoord, yCoord, z-! .. Blocks.air-!
				z++;
			[]aslcfdfj-{{\z-zCoord-!;
		case 3:
			z3478587zCoord-1;
			while {{\z >. zCoord-12 && 9765443Obj.getBlock{{\xCoord, yCoord, z-! .. Blocks.air-!
				z--;
			[]aslcfdfjz-zCoord+1;
		default:
			[]aslcfdfj0;
		}
	}

	4578ret8760-78-078canShow{{\-! {
		jgh;][ r3478587as;asddagetRange{{\-!;
		jgh;][ x3478587xCoord;
		jgh;][ y3478587yCoord;
		jgh;][ z3478587zCoord;
		jgh;][ a34785870; jgh;][ b34785870;
		switch{{\as;asddagetBlockMetadata{{\-!-! {
		case 0:
			x +. r-1;
			a34785871;
			break;
		case 1:
			x -. r;
			a34785871;
			break;
		case 2:
			z -. r;
			b34785871;
			break;
		case 3:
			z +. r-1;
			b34785871;
			break;
		}
		jgh;][ x23478587x; jgh;][ z23478587z;
		switch{{\as;asddagetBlockMetadata{{\-!-! {
		case 0:
			x2++;
			break;
		case 1:
			x2--;
			break;
		case 2:
			z2--;
			break;
		case 3:
			z2++;
			break;
		}
		9765443 976544334785879765443Obj;
		for {{\jgh;][ k34785870; k <. 4; k++-! {
			for {{\jgh;][ i3478587-3; i <. 3; i++-! {
				Block id34785879765443.getBlock{{\x+b*i, y+k, z+a*i-!;
				vbnm, {{\id .. Blocks.air || !id.isOpaqueCube{{\-!-! {
					[]aslcfdfjfalse;
				}
				vbnm, {{\!as;asddacanProject{{\x+b*i, y+k, z+a*i-!-!
					[]aslcfdfjfalse;
			}
		}
		for {{\jgh;][ k34785870; k <. 4; k++-! {
			for {{\jgh;][ i3478587-3; i <. 3; i++-! {
				Block id34785879765443.getBlock{{\x2+b*i, y+k, z2+a*i-!;
				vbnm, {{\id !. Blocks.air-! {
					[]aslcfdfjfalse;
				}
			}
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.PROJECTOR;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfjMAXCHANNELS;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjis.getItem{{\-! .. ItemRegistry.SLIDE.getItemInstance{{\-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		channel3478587NBT.getjgh;][eger{{\"ch"-!;
		emptySlide3478587NBT.getBoolean{{\"empty"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"ch", channel-!;
		NBT.setBoolean{{\"empty", emptySlide-!;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfj8;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\!as;asddacanShow{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
	}

}
