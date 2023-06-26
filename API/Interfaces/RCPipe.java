package Reika.RotaryCraft.API.Interfaces;

import net.minecraftforge.fluids.Fluid;

/** RC pipes, rather than being IFluidHandlers, implement this interface. Use it to push or pull fluids directly from a pipe. */
public interface RCPipe {

	public boolean addFluid(Fluid f, int amt);
	public int removeLiquid(int max);
	public Fluid getFluidType();
	public int getFluidLevel();
	public boolean isValidFluid(Fluid f);

}
