package Reika.RotaryCraft.API;

import net.minecraftforge.common.util.ForgeDirection;

/** Implement this instead of {@link ShaftPowerReceiver} for more advanced and dynamic control over the power reception capabilities
 * of your machine. To use this, you must implement the methods as you see fit but also set the power to zero at the end of the tick, allowing
 * for it to be set again if the machine remains powered or drop to zero if not. */
public interface AdvancedShaftPowerReceiver extends PowerAcceptor {

	/** Called every tick to add power at a given torque and speed, to a given side. For a block receiving multiple inputs, standard RC
	 * behavior is to sum the torque of all the inputs at the highest speed and ignore all others (eg 512@32+256@256+64@256 = 320@256)
	 * Depending on the intended function and "in-universe" mechanics of your machine, you may choose to do this or do something else.
	 * For those using the mechanical power directly (as opposed to internal conversion into a more nebulous form), it is strongly
	 * recommended to use the RC summation procedure for the sake of consistency and realism. */
	public boolean addPower(int torque, int omega, long power, ForgeDirection side);

}
