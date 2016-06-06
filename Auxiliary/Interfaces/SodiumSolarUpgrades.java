package Reika.RotaryCraft.Auxiliary.Interfaces;


public interface SodiumSolarUpgrades {

	boolean isActive();

	public static interface SodiumSolarReceiver extends SodiumSolarUpgrades {

		public void tick(int mirrorCount, float totalBrightness);

	}

	public static interface SodiumSolarOutput extends SodiumSolarUpgrades {

		int receiveSodium(int amt);

	}

}
