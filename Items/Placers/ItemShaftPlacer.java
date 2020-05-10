/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Placers;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShaftPlacer extends ItemBlockPlacer {

	public ItemShaftPlacer() {
		super();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
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
			this.clearBlocks(world, x, y, z);
			if (!ReikaWorldHelper.softBlocks(world, x, y, z) && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.water && ReikaWorldHelper.getMaterial(world, x, y, z) != Material.lava)
				return false;
		}
		this.clearBlocks(world, x, y, z);
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
		List inblock = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		if (inblock.size() > 0)
			return false;
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else {
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, MachineRegistry.SHAFT.getBlock(), is.getItemDamage(), 3);
			TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
			if (sha != null) {
				boolean cross = RotaryAux.isShaftCross(is);
				sha.setMaterialFromItem(is);
				if (cross) {
					sha.setBlockMetadata(6+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
				}
				else {
					sha.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
				}
				sha.setPlacer(ep);
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
				if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
					sha.isFlipped = true;
				}
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		if (MachineRegistry.SHAFT.isAvailableInCreativeInventory()) {
			for (int i = 0; i < MaterialRegistry.matList.length; i++) {
				list.add(MaterialRegistry.matList[i].getShaftItem());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean verbose) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			MaterialRegistry mat = MaterialRegistry.getMaterialFromShaftItem(is);
			double torque = mat.getLimits().maxTorque;
			double speed = mat.getLimits().maxSpeed;
			li.add(String.format("Max Speed: %s", RotaryAux.formatSpeed(speed)));
			li.add(String.format("Max Torque: %s", RotaryAux.formatTorque(torque)));
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("Hold ");
			sb.append(EnumChatFormatting.GREEN.toString());
			sb.append("Shift");
			sb.append(EnumChatFormatting.GRAY.toString());
			sb.append(" for load data");
			li.add(sb.toString());
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		MaterialRegistry type = MaterialRegistry.getMaterialFromShaftItem(is);
		return type != null ? StatCollector.translateToLocal(type.getShaftUnlocName())+" "+MachineRegistry.SHAFT.getName() : "Shaft";
	}
}
