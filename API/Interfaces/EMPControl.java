package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.tileentity.TileEntity;

/** Implement this on a TileEntity to give it custom behavior when hit with a RotaryCraft EMP. */
public interface EMPControl {

	/** Called during the actual firing of the EMP. The tile passed in is the EMP itself. */
	public void onHitWithEMP(TileEntity te);

}
