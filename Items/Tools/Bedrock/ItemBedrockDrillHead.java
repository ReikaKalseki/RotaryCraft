package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.BlockRegistry;

import blusunrize.immersiveengineering.api.tool.IDrillHead;

@Strippable("blusunrize.immersiveengineering.api.tool.IDrillHead")
public class ItemBedrockDrillHead extends ItemRotaryTool implements IDrillHead {

	public ItemBedrockDrillHead(int index) {
		super(index);
	}

	@Override
	public boolean beforeBlockbreak(ItemStack drill, ItemStack head, EntityPlayer player) {
		return false;
	}

	@Override
	public void afterBlockbreak(ItemStack drill, ItemStack head, EntityPlayer player) {

	}

	@Override
	public int getMiningSize(ItemStack head) {
		return 5;
	}

	@Override
	public int getMiningDepth(ItemStack head) {
		return 9;
	}

	@Override
	public int getMiningLevel(ItemStack head) {
		return Integer.MAX_VALUE;
	}

	@Override
	public float getMiningSpeed(ItemStack head) {
		return 18;
	}

	@Override
	public float getAttackDamage(ItemStack head) {
		return 8;
	}

	@Override
	public int getHeadDamage(ItemStack head) {
		return 0;
	}

	@Override
	public int getMaximumHeadDamage(ItemStack head) {
		return 0;
	}

	@Override
	public void damageHead(ItemStack head, int damage) {

	}

	@Override
	public IIcon getDrillTexture(ItemStack drill, ItemStack head) {
		return BlockRegistry.LIGHT.getBlockInstance().getIcon(0, 0);
	}

}
