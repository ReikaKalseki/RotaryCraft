package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteelArmor extends ItemRotaryArmor {

	public ItemSteelArmor(int ID, int tex, int render, int type) {
		super(ID, RotaryCraft.BEDROCK, render, type, tex);
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {

	}

	public String getArmorTextureFile(ItemStack is) {
		ItemRegistry item = ItemRegistry.getEntry(is);
		switch(item) {
		case STEELHELMET:
			return "/Reika/RotaryCraft/Textures/Misc/steel_1.png";
		case STEELLEGS:
			return "/Reika/RotaryCraft/Textures/Misc/steel_2.png";
		case STEELCHEST:
			return "/Reika/RotaryCraft/Textures/Misc/steel_1.png";
		case STEELBOOTS:
			return "/Reika/RotaryCraft/Textures/Misc/steel_1.png";
		default:
			return "";
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		li.add(is);
	}

}
