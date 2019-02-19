package Reika.RotaryCraft.Base;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.RailGunAmmo;
import net.minecraft.creativetab.CreativeTabs;

public abstract class ItemRailgunAmmoBase extends ItemBasic implements RailGunAmmo {

	public ItemRailgunAmmoBase(int tex) {
		super(tex);
		maxStackSize = 16;
	}

	@Override
	protected final CreativeTabs getCreativePage() {
		return RotaryCraft.tabRotaryTools;
	}

}
