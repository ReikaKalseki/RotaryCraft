package Reika.RotaryCraft.Base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.Block.ShearablePlant;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;


public abstract class ItemRotaryShears extends ItemShears implements IndexedItemSprites {

	private int index;

	protected ItemRotaryShears(int tex) {
		super();
		this.setIndex(tex);
		maxStackSize = 1;
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public final boolean isItemTool(ItemStack is) {
		return true;
	}

	private void setIndex(int tex) {
		index = tex;
	}

	@Override
	public final int getItemSpriteIndex(ItemStack is) {
		return index;
	}

	@Override
	public final String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

	@Override
	public final Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public final String getItemStackDisplayName(ItemStack is) {
		return ItemRegistry.getEntry(is).getBasicName();
	}

	@Override
	public final boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float ax, float bx, float cx) {
		if (!ReikaWorldHelper.softBlocks(world, x, y, z) && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.water && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.lava) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
		}
		Block b = world.getBlock(x, y, z);
		if (b instanceof ShearablePlant) {
			((ShearablePlant)b).shearAll(world, x, y, z, ep);
			ReikaSoundHelper.playBreakSound(world, x, y, z, b);
			return true;
		}
		return false;
	}

}
