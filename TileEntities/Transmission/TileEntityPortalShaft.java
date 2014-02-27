package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import com.xcompwiz.mystcraft.api.MystObjects;

public class TileEntityPortalShaft extends TileEntityPowerReceiver {

	private PortalType type;

	public static enum PortalType {
		NETHER(),
		END(),
		TWILIGHT(),
		MYSTCRAFT();
	}

	public int getCurrentDimID() {
		return worldObj.provider.dimensionId;
	}

	public int getTargetDimID() {
		int id = this.getCurrentDimID();
		switch(type) {
		case END:
			return id == 0 ? 1 : 0;
		case MYSTCRAFT: //portal has a book slot?
			return 0;
		case NETHER:
			return id == 0 ? -1 : 0;
		case TWILIGHT:
			return id == 0 ? TwilightForestHandler.getInstance().dimensionID : 0;
		default:
			return id;
		}
	}

	public void setPortalType(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id == Block.portal.blockID)
			type = PortalType.NETHER;
		if (id == Block.endPortal.blockID)
			type = PortalType.END;
		if (ModList.MYSTCRAFT.isLoaded() && MystObjects.portal != null && MystObjects.portal.blockID == id)
			type = PortalType.MYSTCRAFT;
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.getInstance().portalID)
			type = PortalType.TWILIGHT;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		//use dimensionmanager to set power
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PORTALSHAFT;
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
