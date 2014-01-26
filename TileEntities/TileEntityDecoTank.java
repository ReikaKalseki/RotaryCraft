package Reika.RotaryCraft.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;

public class TileEntityDecoTank extends TileEntityBase {

	private Fluid f;

	@Override
	public int getTileEntityBlockID() {
		return RotaryCraft.decoTank.blockID;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected String getTEName() {
		return RotaryCraft.decoTank.getLocalizedName();
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		ReikaNBTHelper.writeFluidToNBT(NBT, f);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		f = ReikaNBTHelper.getFluidFromNBT(NBT);
	}

	public Fluid getFluid() {
		return f;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public void setLiquid(ItemStack is) {
		if (is.stackTagCompound != null) {
			if (is.stackTagCompound.getInteger("level") >= ItemBlockDecoTank.FILL)
				f = ReikaNBTHelper.getFluidFromNBT(is.stackTagCompound);
		}
	}

}
