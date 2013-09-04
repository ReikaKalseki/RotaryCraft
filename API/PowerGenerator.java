package Reika.RotaryCraft.API;

/** Implement this if your TE "spawns" power */
public interface PowerGenerator {

	public long getMaxPower();

	public long getCurrentPower();

}
