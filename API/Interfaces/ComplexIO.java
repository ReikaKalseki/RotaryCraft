package Reika.RotaryCraft.API.Interfaces;

import net.minecraftforge.common.util.ForgeDirection;


public interface ComplexIO {

	public int getSpeedToSide(ForgeDirection dir);
	public int getTorqueToSide(ForgeDirection dir);

}
