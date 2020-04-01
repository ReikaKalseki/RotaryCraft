package Reika.RotaryCraft.Items.Tools.Bedrock;

import com.cricketcraft.chisel.api.IChiselItem;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.RotaryCraft.Base.ItemRotaryTool;

@Strippable("com.cricketcraft.chisel.api.IChiselItem")
public class ItemBedrockChisel extends ItemRotaryTool implements IChiselItem {

	public ItemBedrockChisel(int index) {
		super(index);
	}

	@Override
	public boolean canOpenGui(World world, EntityPlayer player, ItemStack chisel) {
		return true;
	}

	@Override
	public boolean onChisel(World world, ItemStack chisel, ICarvingVariation target) {
		return false;
	}

	@Override
	public boolean canChisel(World world, ItemStack chisel, ICarvingVariation target) {
		return true;
	}

	@Override
	public boolean canChiselBlock(World world, EntityPlayer player, int x, int y, int z, Block block, int metadata) {
		return true;
	}

	@Override
	public boolean hasModes(ItemStack chisel) {
		return false;
	}

}
