package Reika.RotaryCraft.API;

public interface ShaftMachine {

	/** For fetching the current rotational speed */
	public int getOmega();

	/** For fetching the current torque */
	public int getTorque();

	/** For fetching the current power value */
	public long getPower();

	/** For when to write it to chat or the like */
	public String getName();

	/** Analogous to TileEntityIOMachine's "iotick". Used to control I/O render opacity. */
	public int getIORenderAlpha();

	/** Analogous to TileEntityIOMachine's "iotick". Used to control I/O render opacity. This one is called by tools. */
	public void setIORenderAlpha(int io);

	/** Usually returns this.xCoord */
	public int getMachineX();

	/** Usually returns this.yCoord */
	public int getMachineY();

	/** Usually returns this.zCoord */
	public int getMachineZ();

}
