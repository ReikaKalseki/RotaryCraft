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

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBedrockHoe extends ItemHoe implements IndexedItemSprites {

	private int index;

	public ItemBedrockHoe(int tex) {
		super(ToolMaterial.EMERALD);
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
						//ReikaItemHelper.dropItem(world, dx+0.5, y+1.2, dz+0.5, new ItemStack(Items.wheat_seeds));
					}
					//ReikaJavaLibrary.pConsole((dx)+", "+y+", "+(dz);
				}
				else {
					int slot = ReikaInventoryHelper.locateIDInInventory(Items.wheat_seeds, ep.inventory);
					if (slot != -1 || ep.capabilities.isCreativeMode) {
						Block id = world.getBlock(dx, y, dz);
						Block id2 = world.getBlock(dx, y+1, dz);
						boolean top = id2 == Blocks.air || id2.isOpaqueCube() == false;
						if (top) {
							if (id != Blocks.air) {
								if (id == Blocks.dirt || id == Blocks.farmland || id.isFertile(world, dx, y, dz)) {
									flag = true;
									world.setBlock(dx, y, dz, Blocks.grass);
									if (slot != -1 && !ep.capabilities.isCreativeMode) {
										ItemStack seed = ep.inventory.getStackInSlot(slot);
										seed.stackSize--;
										if (seed.stackSize <= 0) {
											ep.inventory.setInventorySlotContents(slot, null);
											return flag;
										}
									}
									ReikaSoundHelper.playStepSound(world, dx, y, dz, Blocks.grass, 0.4F, 1);
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		return ItemRegistry.getEntry(is).getBasicName();
	}

}