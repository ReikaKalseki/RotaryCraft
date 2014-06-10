/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

/** Make your TE implement this to be able to use the RotaryCraft pipe renderer classes on your own pipe. */
public interface RenderableDuct {

	/** The icon used for most of the solid texture */
	public Icon getBlockIcon();

	/** The block type used for most of the solid texture; usually also the main crafting ingredient */
	public Block getPipeBlockType();

	/** Is the pipe able to connect to the given side (relative to the pipe) */
	public boolean isConnectionValidForSide(ForgeDirection dir);

	/** Is the pipe connected to something that is not another pipe of this type, on the given side (relative to the pipe) */
	public boolean isConnectedToNonSelf(ForgeDirection dir);

	/** The contained liquid type. Null if empty. */
	public Fluid getFluidType();

	public Icon getGlassIcon();

	public Icon getOverlayIcon();

	public boolean isFluidPipe();

}
