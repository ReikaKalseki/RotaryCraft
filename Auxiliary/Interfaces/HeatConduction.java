package Reika.RotaryCraft.Auxiliary.Interfaces;

import Reika.DragonAPI.Interfaces.TileEntity.ThermalTile;

public interface HeatConduction extends ThermalTile {

	public abstract boolean canBeCooledWithFins();

	public abstract boolean allowExternalHeating();

	public abstract boolean allowHeatExtraction();

	public abstract double heatEnergyPerDegree();

	public int getAmbientTemperature();

}
