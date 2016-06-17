package Reika.RotaryCraft.Containers.Machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter;


public class ContainerItemFilter extends CoreContainer {

	private final TileEntityItemFilter filter;

	public ContainerItemFilter(EntityPlayer player, TileEntityItemFilter te) {
		super(player, te);

		filter = te;

		this.addSlot(0, 8, 8);
		this.addSlotNoClick(1, 8, 104);

		this.addPlayerInventoryWithOffset(player, 0, 51);
	}

	@Override
	public ItemStack slotClick(int ID, int par2, int par3, EntityPlayer ep) {
		ItemStack ret = super.slotClick(ID, par2, par3, ep);
		filter.reloadData();
		return ret;
	}

}
