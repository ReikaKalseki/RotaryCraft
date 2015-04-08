package Reika.RotaryCraft.API.Interfaces;

public interface CustomFanEntity {

	/** The required power to be able to blow this entity. */
	public long getBlowPower();

	/** The maximum speed-vector change. */
	public double getMaxDeflection();

}
