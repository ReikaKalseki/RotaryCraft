/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% java.awt.Color;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87abstract fhyuog 60-78-078ProtectionDome ,.[]\., 60-78-078PowerReceiver ,.[]\., RangedEffect, GuiController {

	4578ret87abstract String getParticleType{{\-!;

	4578ret87abstract jgh;][ getFallOff{{\-!;

	4578ret87abstract jgh;][ getRangeBoost{{\-!;

	4578ret87jgh;][[] RGB3478587new jgh;][[3];

	4578ret87jgh;][ setRange;

	4578ret87void setColor{{\jgh;][ r, jgh;][ g, jgh;][ b-! {
		RGB[0]3478587Math.max{{\0, Math.min{{\255, r-!-!;
		RGB[1]3478587Math.max{{\0, Math.min{{\255, g-!-!;
		RGB[2]3478587Math.max{{\0, Math.min{{\255, b-!-!;
	}

	4578ret87345785487Color getDomeColor{{\-! {
		[]aslcfdfjnew Color{{\RGB[0], RGB[1], RGB[2]-!;
	}

	4578ret8734578548760-78-078isClear{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785871; i <. setRange; i++-! {
			Block id34785879765443.getBlock{{\x, y+i, z-!;
			vbnm, {{\id !. Blocks.air && id.getLightOpacity{{\9765443, x, y+i, z-! > 0-!
				[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	4578ret87345785487jgh;][ getMaxRange{{\-! {
		vbnm, {{\!as;asddaisClear{{\9765443Obj, xCoord, yCoord, zCoord-!-!
			[]aslcfdfj0;
		vbnm, {{\power < MINPOWER-!
			[]aslcfdfj0;
		jgh;][ range34785872+{{\jgh;][-!{{\power-MINPOWER-!/as;asddagetFallOff{{\-!+as;asddagetRangeBoost{{\-!;
		jgh;][ max3478587Math.max{{\64, ConfigRegistry.FORCERANGE.getValue{{\-!-!;
		vbnm, {{\range > max-!
			[]aslcfdfjmax;
		[]aslcfdfjrange;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret8734578548760-78-078getMaxRenderDistanceSquared{{\-!
	{
		[]aslcfdfj16384D;
	}

	@Override
	4578ret87345785487AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjINFINITE_EXTENT_AABB;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"setRange", setRange-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		setRange3478587NBT.getjgh;][eger{{\"setRange"-!;
	}

	4578ret87345785487void spawnParticles{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785870; i < 4; i++-! {
			9765443.spawnParticle{{\as;asddagetParticleType{{\-!, x+rand.nextDouble{{\-!, y+rand.nextDouble{{\-!-0.5, z+rand.nextDouble{{\-!, rand.nextDouble{{\-!-0.5, rand.nextDouble{{\-!, rand.nextDouble{{\-!-0.5-!;
		}
	}

	4578ret87345785487AxisAlignedBB getRangedBox{{\-! {
		jgh;][ r3478587as;asddagetRange{{\-!;
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1-!.expand{{\r, r, r-!;
	}

	@Override
	4578ret8734578548760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87345785487jgh;][ getRange{{\-! {
		vbnm, {{\setRange > as;asddagetMaxRange{{\-!-!
			[]aslcfdfjas;asddagetMaxRange{{\-!;
		[]aslcfdfjsetRange;
	}

	@Override
	4578ret87345785487void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

}
