/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import Reika.DragonAPI.Instantiable.ItemFilter;
import Reika.DragonAPI.Interfaces.Item.CustomMatchingItem;
import Reika.DragonAPI.Interfaces.Item.SpriteRenderCallback;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

public class ItemMatchFilter extends ItemRotaryTool implements SpriteRenderCallback, CustomMatchingItem {

	public ItemMatchFilter(int index) {
		super(index);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (ep.isSneaking()) {
			is.stackTagCompound = null;
		}
		else {
			ep.openGui(RotaryCraft.instance, GuiRegistry.FILTER.ordinal(), world, 0, 0, 0);
		}
		return is;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		//FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

	}

	private static void resetNBT(ItemStack is) {
		if (is.stackTagCompound != null) {

		}
	}

	@Override
	public boolean onRender(RenderItem ri, ItemStack is, ItemRenderType type) {
		if (type == ItemRenderType.INVENTORY && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			ItemStack out = null;
			if (out != null) {
				double s = 0.0625;
				GL11.glScaled(s, -s, s);
				ReikaGuiAPI.instance.drawItemStack(ri, out, 0, -16);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doPreGLTransforms(ItemStack is, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean match(ItemStack is) {
		//TODO
		return false;
	}

	@Override
	public ItemFilter getFilter(ItemStack is) {
		//TODO
		return null;
	}

}
