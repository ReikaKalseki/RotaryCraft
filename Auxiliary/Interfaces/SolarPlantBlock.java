package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.RotaryCraft.Auxiliary.SolarPlant;


public interface SolarPlantBlock extends BreakAction {

	public SolarPlant getPlant();

	public void setPlant(SolarPlant p);

	public void searchForPlant(World world, int x, int y, int z);

}
