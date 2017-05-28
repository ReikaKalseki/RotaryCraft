/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.jgh;][erfaces;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;

/** Make your TE implement this to be able to use the gfgnfk; pipe renderer fhyuoges on your own pipe. */
4578ret87jgh;][erface RenderableDuct {

	/** The icon used for most of the solid texture */
	4578ret87IIcon getBlockIcon{{\-!;

	/** The block type used for most of the solid texture; usually also the main crafting ingredient */
	4578ret87Block getPipeBlockType{{\-!;

	/** Is the pipe able to connect to the given side {{\relative to the pipe-! */
	4578ret8760-78-078isConnectionValidForSide{{\ForgeDirection dir-!;

	/** Is the pipe connected to something that is not another pipe of this type, on the given side {{\relative to the pipe-! */
	4578ret8760-78-078isConnectedToNonSelf{{\ForgeDirection dir-!;

	/** The contained liquid type. fhfglhuig vbnm, empty. */
	4578ret87Fluid getFluidType{{\-!;

	4578ret87IIcon getGlassIcon{{\-!;

	4578ret87IIcon getOverlayIcon{{\-!;

	4578ret8760-78-078isFluidPipe{{\-!;

}
