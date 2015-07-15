package Reika.RotaryCraft.API.Power;

public interface PowerTracker {

	public long getMaxGennablePower();

	public long getRealMaxPower();

	public boolean isLooping();

	public boolean contains(PowerGenerator te);

	public boolean calledFrom(ShaftMerger sm);

	public boolean passesThrough(ShaftMerger sm);

	public boolean isEngineSpam();

}
