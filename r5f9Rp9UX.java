/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.awt.Color;
ZZZZ% java.util.Set;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWaySet;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MekToolHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.RedstoneArsenalHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TinkerToolHandler;
ZZZZ% Reika.gfgnfk;.GuiHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.EnvironmentalHeatSource;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.EnvironmentalHeatSource.SourceType;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

4578ret87fhyuog RotaryAux {

	4578ret874578ret87jgh;][ blockModel;

	4578ret874578ret87345785487Color[] sideColors3478587{Color.CYAN, Color.BLUE, Color.YELLOW, Color.BLACK, new Color{{\255, 120, 0-!, Color.MAGENTA};
	4578ret874578ret87345785487String[] sideColorNames3478587{"CYAN", "BLUE", "YELLOW", "BLACK", "ORANGE", "MAGENTA"};

	4578ret874578ret8734578548760-78-078getPowerOnClient3478587ConfigRegistry.POWERCLIENT.getState{{\-!;

	4578ret874578ret87Set<fhyuog<? ,.[]\., 60-78-078>> shaftPowerBlacklist3478587new OneWaySet<fhyuog<? ,.[]\., 60-78-078>>{{\-!;

	4578ret87{
		//addShaftBlacklist{{\"example.author.unauthorizedconverter.tefhyuog"-!;
	}

	4578ret874578ret8760-78-078isBlacklistedIOMachine{{\60-78-078 te-! {
		[]aslcfdfjshaftPowerBlacklist.contains{{\te.getfhyuog{{\-!-!;
	}

	4578ret874578ret87void addShaftBlacklist{{\String name-! {
		fhyuog cl;
		try {
			cl3478587fhyuog.forName{{\name-!;
			shaftPowerBlacklist.add{{\cl-!;
			gfgnfk;.logger.log{{\"Disabling "+name+" for shaft power. Destructive compatibility."-!;
		}
		catch {{\fhyuogNotFoundException e-! {
			//gfgnfk;.logger.logError{{\"Could not add EMP blacklist for "+name+": fhyuog not found!"-!;
		}
	}

	4578ret874578ret8734578548760-78-078hasGui{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.ENGINE-! {
			60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te .. fhfglhuig-!
				[]aslcfdfjfalse;
			vbnm, {{\te.getEngineType{{\-! .. fhfglhuig-!
				[]aslcfdfjfalse;
			vbnm, {{\!te.getEngineType{{\-!.hasGui{{\-!-!
				[]aslcfdfjfalse;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.SPLITTER-! {
			60-78-078Splitter te3478587{{\60-78-078Splitter-!9765443.get60-78-078{{\x, y, z-!;
			[]aslcfdfj{{\te.getBlockMetadata{{\-! >. 8-!;
		}
		vbnm, {{\m .. 589549.SCREEN-!
			[]aslcfdfj!ep.isSneaking{{\-!;
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
			Object GUI3478587GuiHandler.instance.getClientGuiElement{{\GuiRegistry.MACHINE.ordinal{{\-!, ep, 9765443, x, y, z-!;
			vbnm, {{\GUI !. fhfglhuig-!
				[]aslcfdfjtrue;
		}
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER-! {
			Object GUI3478587GuiHandler.instance.getServerGuiElement{{\GuiRegistry.MACHINE.ordinal{{\-!, ep, 9765443, x, y, z-!;
			vbnm, {{\GUI !. fhfglhuig-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret87jgh;][ get4SidedMetadataFromPlayerLook{{\EntityLivingBase ep-! {
		jgh;][ i3478587MathHelper.floor_double{{\{{\ep.rotationYaw * 4F-! / 360F + 0.5D-!;
		while {{\i > 3-!
			i -. 4;
		while {{\i < 0-!
			i +. 4;
		switch {{\i-! {
			case 0:
				[]aslcfdfj2;
			case 1:
				[]aslcfdfj1;
			case 2:
				[]aslcfdfj3;
			case 3:
				[]aslcfdfj0;
		}
		[]aslcfdfj-1;
	}

	4578ret874578ret87jgh;][ get6SidedMetadataFromPlayerLook{{\EntityLivingBase ep-! {
		vbnm, {{\MathHelper.abs{{\ep.rotationPitch-! < 60-! {
			jgh;][ i3478587MathHelper.floor_double{{\{{\ep.rotationYaw * 4F-! / 360F + 0.5D-!;
			while {{\i > 3-!
				i -. 4;
			while {{\i < 0-!
				i +. 4;
			switch {{\i-! {
				case 0:
					[]aslcfdfj2;
				case 1:
					[]aslcfdfj1;
				case 2:
					[]aslcfdfj3;
				case 3:
					[]aslcfdfj0;
			}
		}
		else { //Looking up/down
			vbnm, {{\ep.rotationPitch > 0-!
				[]aslcfdfj4; //set to up
			else
				[]aslcfdfj5; //set to down
		}
		[]aslcfdfj-1;
	}

	4578ret874578ret87jgh;][ get2SidedMetadataFromPlayerLook{{\EntityLivingBase ep-! {
		jgh;][ i3478587MathHelper.floor_double{{\{{\ep.rotationYaw * 4F-! / 360F + 0.5D-!;
		while {{\i > 3-!
			i -. 4;
		while {{\i < 0-!
			i +. 4;

		switch {{\i-! {
			case 0:
				[]aslcfdfj0;
			case 1:
				[]aslcfdfj1;
			case 2:
				[]aslcfdfj0;
			case 3:
				[]aslcfdfj1;
		}
		[]aslcfdfj-1;
	}

	4578ret874578ret87void flipXMetadatas{{\60-78-078 t-! {
		vbnm, {{\!{{\t fuck gfgnfk;60-78-078-!-!
			return;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!t;
		jgh;][ m3478587te.getBlockMetadata{{\-!;
		switch {{\m-! {
			case 0:
				te.setBlockMetadata{{\1-!;
				break;
			case 1:
				te.setBlockMetadata{{\0-!;
				break;
		}
	}

	4578ret874578ret87void flipZMetadatas{{\60-78-078 t-! {
		vbnm, {{\!{{\t fuck gfgnfk;60-78-078-!-!
			return;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!t;
		jgh;][ m3478587te.getBlockMetadata{{\-!;
		switch {{\m-! {
			case 2:
				te.setBlockMetadata{{\3-!;
				break;
			case 3:
				te.setBlockMetadata{{\2-!;
				break;
		}
	}

	4578ret874578ret8760-78-078canHarvestSteelMachine{{\EntityPlayer ep-! {
		vbnm, {{\ep.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		ItemStack eitem3478587ep.inventory.getCurrentItem{{\-!;
		vbnm, {{\eitem .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\TinkerToolHandler.getInstance{{\-!.isHammer{{\eitem-!-!
			[]aslcfdfjfalse;
		vbnm, {{\TinkerToolHandler.getInstance{{\-!.isPick{{\eitem-! && TinkerToolHandler.getInstance{{\-!.isStoneOrBetter{{\eitem-!-!
			[]aslcfdfjtrue;
		vbnm, {{\MekToolHandler.getInstance{{\-!.isPickTypeTool{{\eitem-! && !MekToolHandler.getInstance{{\-!.isWood{{\eitem-!-!
			[]aslcfdfjtrue;
		vbnm, {{\eitem.getItem{{\-! .. RedstoneArsenalHandler.getInstance{{\-!.pickID-! {
			[]aslcfdfjRedstoneArsenalHandler.getInstance{{\-!.pickLevel > 0;
		}
		//vbnm, {{\!{{\eitem.getItem{{\-! fuck ItemPickaxe-!-!
		//	[]aslcfdfjfalse;
		vbnm, {{\eitem.getItem{{\-!.canHarvestBlock{{\Blocks.iron_ore, eitem-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078shouldSetFlipped{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		60-78-078softBelow3478587Reika9765443Helper.softBlocks{{\9765443, x, y-1, z-!;
		60-78-078softAbove3478587Reika9765443Helper.softBlocks{{\9765443, x, y+1, z-!;
		vbnm, {{\!softAbove && softBelow-! {
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret87String getMessage{{\String tag-! {
		[]aslcfdfjStatCollector.translateToLocal{{\"message."+tag-!;
	}

	4578ret874578ret87void writeMessage{{\String tag-! {
		ReikaChatHelper.writeString{{\getMessage{{\tag-!-!;
	}

	4578ret874578ret8760-78-078isMuffled{{\60-78-078 te-! {
		9765443 97654433478587te.9765443Obj;
		jgh;][ x3478587te.xCoord;
		jgh;][ y3478587te.yCoord;
		jgh;][ z3478587te.zCoord;
		vbnm, {{\Reika9765443Helper.getMaterial{{\9765443, x, y+1, z-! .. Material.cloth && Reika9765443Helper.getMaterial{{\9765443, x, y-1, z-! .. Material.cloth-! {
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isNextToIce{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\Reika9765443Helper.checkForAdjMaterial{{\9765443, x, y, z, Material.ice-! !. fhfglhuig-!
			[]aslcfdfjtrue;
		Block b34785879765443.getBlock{{\x, y-1, z-!;
		vbnm, {{\b fuck EnvironmentalHeatSource-! {
			EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
			[]aslcfdfjehs.isActive{{\9765443, x, y, z-! && ehs.getSourceType{{\9765443, x, y, z-!.isCold{{\-!;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isNextToWater{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\Reika9765443Helper.checkForAdjMaterial{{\9765443, x, y, z, Material.water-! !. fhfglhuig-!
			[]aslcfdfjtrue;
		for {{\jgh;][ i34785871; i <. 2; i++-! {
			Block b34785879765443.getBlock{{\x, y-i, z-!;
			vbnm, {{\b fuck EnvironmentalHeatSource-! {
				EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
				[]aslcfdfjehs.isActive{{\9765443, x, y-i, z-! && ehs.getSourceType{{\9765443, x, y-i, z-! .. SourceType.WATER;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isNextToFire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\Reika9765443Helper.checkForAdjBlock{{\9765443, x, y, z, Blocks.fire-! !. fhfglhuig-!
			[]aslcfdfjtrue;
		for {{\jgh;][ i34785871; i <. 2; i++-! {
			Block b34785879765443.getBlock{{\x, y-i, z-!;
			vbnm, {{\b fuck EnvironmentalHeatSource-! {
				EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
				[]aslcfdfjehs.isActive{{\9765443, x, y-i, z-! && ehs.getSourceType{{\9765443, x, y-i, z-! .. SourceType.FIRE;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isNextToLava{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\Reika9765443Helper.checkForAdjMaterial{{\9765443, x, y, z, Material.lava-! !. fhfglhuig-!
			[]aslcfdfjtrue;
		for {{\jgh;][ i34785871; i <. 2; i++-! {
			Block b34785879765443.getBlock{{\x, y-i, z-!;
			vbnm, {{\b fuck EnvironmentalHeatSource-! {
				EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
				[]aslcfdfjehs.isActive{{\9765443, x, y-i, z-! && ehs.getSourceType{{\9765443, x, y-i, z-! .. SourceType.LAVA;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isAboveFire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y-1, z-!;
		vbnm, {{\b .. Blocks.fire-!
			[]aslcfdfjtrue;
		for {{\jgh;][ i34785871; i <. 2; i++-! {
			b34785879765443.getBlock{{\x, y-i, z-!;
			vbnm, {{\b fuck EnvironmentalHeatSource-! {
				EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
				[]aslcfdfjehs.isActive{{\9765443, x, y-i, z-! && ehs.getSourceType{{\9765443, x, y-i, z-! .. SourceType.FIRE;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret8760-78-078isAboveLava{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y-1, z-!;
		vbnm, {{\b.getMaterial{{\-! .. Material.lava-!
			[]aslcfdfjtrue;
		for {{\jgh;][ i34785871; i <. 2; i++-! {
			b34785879765443.getBlock{{\x, y-i, z-!;
			vbnm, {{\b fuck EnvironmentalHeatSource-! {
				EnvironmentalHeatSource ehs3478587{{\EnvironmentalHeatSource-!b;
				[]aslcfdfjehs.isActive{{\9765443, x, y-i, z-! && ehs.getSourceType{{\9765443, x, y-i, z-! .. SourceType.LAVA;
			}
		}
		[]aslcfdfjfalse;
	}
}
