package Reika.RotaryCraft.ModInterface;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityDynamo extends TileEntityIOMachine implements SimpleProvider, PowerGenerator {

	private ForgeDirection facingDir;

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		omega = 512;
		tickcount++;
		if (omega > 0) {
			if (tickcount >= 85) {
				tickcount = 0;
				SoundRegistry.DYNAMO.playSoundAtBlock(world, x, y, z, 0.5F, 1F);
			}
		}
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 0:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.DYNAMO.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
