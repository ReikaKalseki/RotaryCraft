/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.RotaryCraft;

public class ItemBedrockHoe extends ItemHoe implements IndexedItemSprites {

	private int index;

	public ItemBedrockHoe(int par1, int tex) {
		super(par1, EnumToolMaterial.EMERALD);
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
	}

	private void setIndex(int tex) {
		index = tex;
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		return index;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

	@Override
	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float ax, float bx, float cx)
	{
		int r = 2;
		boolean flag = false;
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				int dx = x+i;
				int dz = z+j;
				if (!ep.isSneaking()) {
					boolean flag2 = super.onItemUse(is, ep, world, dx, y, dz, s, ax, bx, cx);
					flag = flag2 || flag;
					if (flag2) {
						//world.setBlockMetadataWithNotify(dx, y, dz, 2, 3);
						//ReikaItemHelper.dropItem(world, dx+0.5, y+1.2, dz+0.5, new ItemStack(Item.seeds));
					}
					//ReikaJavaLibrary.pConsole((dx)+", "+y+", "+(dz);
				}
				else {
					int slot = ReikaInventoryHelper.locateIDInInventory(Item.seeds.itemID, ep.inventory);
					if (slot != -1 || ep.capabilities.isCreativeMode) {
						int id = world.getBlockId(dx, y, dz);
						int id2 = world.getBlockId(dx, y+1, dz);
						Block b = Block.blocksList[id];
						boolean top = id2 == 0 || Block.opaqueCubeLookup[id2] == false;
						if (top) {
							if (b != null) {
								if (id == Block.dirt.blockID || id == Block.tilledField.blockID || b.isFertile(world, dx, y, dz)) {
									flag = true;
									world.setBlock(dx, y, dz, Block.grass.blockID);
									if (slot != -1 && !ep.capabilities.isCreativeMode) {
										ItemStack seed = ep.inventory.getStackInSlot(slot);
										seed.stackSize--;
										if (seed.stackSize <= 0) {
											ep.inventory.setInventorySlotContents(slot, null);
											return flag;
										}
									}
									ReikaSoundHelper.playStepSound(world, dx, y, dz, Block.grass, 0.4F, 1);
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}

}
