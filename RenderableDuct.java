package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

/** Make your TE implement this to be able to use the RotaryCraft pipe renderer classes on your own pipe. */
public interface RenderableDuct {

	/** The icon used for most of the solid texture */
	public Icon getBlockIcon();

	/** The block type used for most of the solid texture; usually also the main crafting ingredient */
	public Block getBlockType();

	/** Return xCoord */
	public int getX();

	/** Return yCoord */
	public int getY();

	/** Return zCoord */
	public int getZ();

	/** Is the pipe able to connect to the given side (relative to the pipe) */
	public boolean isConnectionValidForSide(ForgeDirection dir);

	/** Return worldObj */
	public World getWorld();

	/** Is the pipe connected to something that is not another pipe of this type, on the given side (relative to the pipe) */
	public boolean isConnectedToNonSelf(ForgeDirection dir);

	/** The contained liquid type. Null if empty. */
	public Fluid getLiquidType();

}
