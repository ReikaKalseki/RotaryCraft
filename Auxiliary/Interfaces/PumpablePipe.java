package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraftforge.common.ForgeDirection;


public interface PumpablePipe {

	public int getFluidLevel();

	public boolean canTransferTo(PumpablePipe p, ForgeDirection dir);

	public void transferFrom(PumpablePipe from, int amt);

}
