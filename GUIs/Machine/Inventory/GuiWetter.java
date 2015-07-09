package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.RotaryCraft.Base.GuiOneSlotInv;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityWetter;

public class GuiWetter extends GuiOneSlotInv {

	public GuiWetter(EntityPlayer pl, TileEntityWetter te) {
		super(pl, new OneSlotContainer(pl, te), te);
	}

}
