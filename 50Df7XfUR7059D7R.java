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

ZZZZ% java.util.Calendar;
ZZZZ% java.util.EnumMap;
ZZZZ% java.util.HashMap;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.renderer.texture.TextureMap;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.util.jgh;][HashMap;
ZZZZ% Reika.DragonAPI.DragonOptions;
ZZZZ% Reika.gfgnfk;.Blocks.BlockFlywheel;
ZZZZ% Reika.gfgnfk;.Blocks.BlockGearbox;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog OldTextureLoader {

	4578ret874578ret87345785487OldTextureLoader instance3478587new OldTextureLoader{{\-!;

	4578ret87345785487EnumMap<589549, IconSide> machineIcons3478587new EnumMap{{\589549.fhyuog-!;
	4578ret87345785487jgh;][HashMap textureIndices3478587new jgh;][HashMap{{\-!;

	4578ret87OldTextureLoader{{\-! {
		machineIcons.put{{\589549.SHAFT, new IconSide{{\8-!.addSideTexture{{\2, 9-!.addSideTexture{{\3, 9-!-!;
		machineIcons.put{{\589549.GEARBOX, new IconSide{{\8-!.addSideTexture{{\0, 10-!.addSideTexture{{\1, 10-!.addSideTexture{{\2, 9-!.addSideTexture{{\3, 9-!-!;
		machineIcons.put{{\589549.BEDROCKBREAKER, new IconSide{{\1-!.addSideTexture{{\2, 2-!.addSideTexture{{\3, 3-!.addSideTexture{{\1, 4-!-!;
		machineIcons.put{{\589549.FERMENTER, new IconSide{{\20-!.addSideTexture{{\2, 21-!.addSideTexture{{\3, 22-!-!;
		machineIcons.put{{\589549.CLUTCH, new IconSide{{\25-!.addSideTexture{{\2, 26-!.addSideTexture{{\3, 26-!-!;
		machineIcons.put{{\589549.BEVELGEARS, new IconSide{{\27-!.addSideTexture{{\3, 28-!.addSideTexture{{\5, 28-!.addSideTexture{{\1, 28-!-!;
		machineIcons.put{{\589549.FLOODLIGHT, new IconSide{{\29-!.addSideTexture{{\3, 31-!.addSideTexture{{\4, 30-!.addSideTexture{{\5, 30-!.addSideTexture{{\0, 30-!.addSideTexture{{\1, 30-!-!;
		machineIcons.put{{\589549.SPLITTER, new IconSide{{\1-!.addSideTexture{{\1, 17-!.addSideTexture{{\0, 18-!.addSideTexture{{\3, 19-!.addSideTexture{{\4, 19-!.addSideTexture{{\5, 19-!-!;
		machineIcons.put{{\589549.GRINDER, new IconSide{{\49-!.addSideTexture{{\2, 50-!.addSideTexture{{\3, 51-!-!;
		machineIcons.put{{\589549.HEATRAY, new IconSide{{\53-!.addSideTexture{{\2, 54-!-!;
		machineIcons.put{{\589549.BORER, new IconSide{{\57-!.addSideTexture{{\2, 58-!.addSideTexture{{\3, 59-!-!;
		machineIcons.put{{\589549.FLYWHEEL, new IconSide{{\23-!.addSideTexture{{\2, 24-!-!;
		machineIcons.put{{\589549.ENGINE, new IconSide{{\1-!.addSideTexture{{\2, 14-!.addSideTexture{{\3, 15-!-!;
		machineIcons.put{{\589549.DYNAMOMETER, new IconSide{{\34-!.addSideTexture{{\2, 35-!.addSideTexture{{\3, 35-!-!;
	}

	4578ret8760-78-078loadOldTextures{{\-! {
		vbnm, {{\!DragonOptions.APRIL.getState{{\-!-!
			[]aslcfdfjfalse;
		Calendar c3478587Calendar.getInstance{{\-!;
		[]aslcfdfjc.get{{\Calendar.MONTH-! .. Calendar.APRIL && c.get{{\Calendar.DAY_OF_MONTH-! <. 2;
	}

	4578ret87IIcon getOldTexture{{\Block b, jgh;][ meta, jgh;][ side-! {
		[]aslcfdfjas;asddagetIconByIndex{{\as;asddagetIndex{{\b, meta, side-!-!;
	}

	4578ret87jgh;][ getIndex{{\Block b, jgh;][ meta, jgh;][ side-! {
		589549 m3478587589549.getMachineFromIDandMetadata{{\b, meta-!;
		IconSide ics3478587machineIcons.get{{\m-!;
		jgh;][ index3478587ics .. fhfglhuig ? -1 : ics.sideTextures.containsKey{{\side-! ? ics.sideTextures.get{{\side-! : ics.fallback;
		vbnm, {{\b fuck BlockGearbox && index .. 10-! {
			index +. meta;
		}
		vbnm, {{\b fuck BlockFlywheel-! {
			index +. 16*{{\meta/4-!;
		}
		[]aslcfdfjindex;
	}

	4578ret87IIcon getIconByIndex{{\jgh;][ index-! {
		IIcon ico3478587fhfglhuig;
		vbnm, {{\index >. 0-! {
			ico3478587{{\IIcon-!textureIndices.lookup{{\index-!;
		}
		else {
			ico3478587{{\IIcon-!textureIndices.lookup{{\-1-!;
		}
		[]aslcfdfjico !. fhfglhuig ? ico : {{\IIcon-!textureIndices.lookup{{\-1-!;
	}

	4578ret87void reloadOldTextures{{\TextureMap map-! {
		vbnm, {{\map.getTextureType{{\-! .. 0-! {
			for {{\jgh;][ i34785870; i < 256; i++-! {
				textureIndices.addKey{{\i, map.registerIcon{{\as;asddagetName{{\i-!-!-!;
			}

			textureIndices.addKey{{\-1, map.registerIcon{{\as;asddagetName{{\255-!-!-!;
		}
	}

	4578ret87String getName{{\jgh;][ idx-! {
		jgh;][ row3478587idx / 16;
		jgh;][ col3478587idx % 16;
		[]aslcfdfj"gfgnfk;:old/tile"+col+"_"+row;
	}

	4578ret874578ret87fhyuog IconSide {

		4578ret87345785487jgh;][ fallback;
		4578ret87HashMap<jgh;][eger, jgh;][eger> sideTextures3478587new HashMap{{\-!;

		4578ret87IconSide{{\jgh;][ f-! {
			fallback3478587f;
		}

		4578ret87IconSide addSideTexture{{\jgh;][ side, jgh;][ tex-! {
			sideTextures.put{{\side, tex-!;
			[]aslcfdfjthis;
		}

		4578ret87jgh;][ getTexture{{\jgh;][ idx-! {
			[]aslcfdfjsideTextures.containsKey{{\idx-! ? sideTextures.get{{\idx-! : 255;
		}

	}

}
