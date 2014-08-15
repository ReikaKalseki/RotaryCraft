/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Placers;

import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPortalShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

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
			Block b = world.getBlock(x, y, z);
			if (ReikaBlockHelper.isPortalBlock(world, x, y, z)) {
				TileEntityShaft sha = new TileEntityShaft();
				sha.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
				sha.getIOSides(world, x, y, z, sha.getBlockMetadata());
				sha.setWorldObj(world);
				sha.xCoord = x;
				sha.yCoord = y;
				sha.zCoord = z;
				int dx = x+sha.getReadDirection().offsetX;
				int dy = y+sha.getReadDirection().offsetY;
				int dz = z+sha.getReadDirection().offsetZ;
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.SHAFT) {
					TileEntityShaft te = (TileEntityShaft)world.getTileEntity(dx, dy, dz);
					if (te.isWritingToCoordinate(x, y, z)) {
						world.setBlock(dx, dy, dz, MachineRegistry.PORTALSHAFT.getBlock(), MachineRegistry.PORTALSHAFT.getMachineMetadata(), 3);
						TileEntityPortalShaft ps = new TileEntityPortalShaft();
						world.setTileEntity(dx, dy, dz, ps);
						ps.setBlockMetadata(te.getBlockMetadata());
						ps.setPortalType(world, x, y, z);
						ps.material = te.getShaftType();
					}
				}
			}
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
		else
		{
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			world.setBlock(x, y, z, MachineRegistry.SHAFT.getBlock(), is.getItemDamage(), 3);
			if (is.getItemDamage() == ItemStacks.shaftcross.getItemDamage()) {
				TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
				if (sha != null) {
					//sha.type = MaterialRegistry.STEEL;
					sha.setBlockMetadata(6+RotaryAux.get4SidedMetadataFromPlayerLook(ep));
				}
				return true;
			}
			TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
			if (sha != null) {
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F);
				//sha.type = MaterialRegistry.setType(is.getItemDamage());
			}
		}
		TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
		sha.setBlockMetadata(RotaryAux.get6SidedMetadataFromPlayerLook(ep));
		sha.setPlacer(ep);
		if (RotaryAux.shouldSetFlipped(world, x, y, z)) {
			sha.isFlipped = true;
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		if (MachineRegistry.SHAFT.isAvailableInCreativeInventory()) {
			for (int i = 0; i < RotaryNames.getNumberShaftTypes(); i++) {
				ItemStack item = new ItemStack(id, 1, i);
				list.add(item);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean verbose) {
		int i = is.getItemDamage();
		if (i < MaterialRegistry.matList.length) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				MaterialRegistry mat = MaterialRegistry.matList[i];
				double torque = mat.getMaxShaftTorque();
				double speed = mat.getMaxShaftSpeed();
				li.add(String.format("Max Speed: %.3f %srad/s", ReikaMathLibrary.getThousandBase(speed), ReikaEngLibrary.getSIPrefix(speed)));
				li.add(String.format("Max Torque: %.3f %sNm", ReikaMathLibrary.getThousandBase(torque), ReikaEngLibrary.getSIPrefix(torque)));
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
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		return ItemRegistry.getEntry(is).getMultiValuedName(is.getItemDamage());
	}
}