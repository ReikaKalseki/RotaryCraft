/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.EnumEngineType;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMulti extends ItemBasic implements IndexedItemSprites {

	private int type;

	private Icon[][] ico = new Icon[16][16];
	private String[][] tex = new String[16][16];

	public ItemMulti(int ID, int par2type) {
		super(ID, 0); //Returns super constructor: par1 is ID
		////setItemName("shaftcraft"); //Sets the incode name of the item, make sure it doesn't clash with other items, weird stuff happens
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		type = par2type;
		maxStackSize = 64;
		//this.setIconCoord(0, 0);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
	}

	public ItemMulti(int ID, int par2type, int max) {
		super(ID, 0); //Returns super constructor: par1 is ID
		////setItemName("shaftcraft"); //Sets the incode name of the item, make sure it doesn't clash with other items, weird stuff happens
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		type = par2type;
		maxStackSize = max;
		//this.setIconCoord(0, 0);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity e, int par4, boolean par5) {
		if (is.itemID == ItemStacks.shaftcore.itemID && is.getItemDamage() == ItemStacks.shaftcore.getItemDamage()) {
			if (is.stackTagCompound != null) {
				int mag = is.stackTagCompound.getInteger("magnet");
				if (mag > 0) {
					int x = (int)e.posX;
					int y = (int)e.posY;
					int z = (int)e.posZ;
					for (int i = -6; i <= 6; i++) {
						for (int j = -6; j <= 6; j++) {
							for (int k = -6; k <= 6; k++) {
								if (world.getBlockTileEntity(x+i, y+j, z+k) instanceof TileEntityEngine) {
									TileEntityEngine te = (TileEntityEngine)world.getBlockTileEntity(x+i, y+j, z+k);
									if (te.type == EnumEngineType.AC) {
										double dx = x-te.xCoord-0.5;
										double dy = y-te.yCoord-0.5;
										double dz = z-te.zCoord-0.5;
										double dd = ReikaMathLibrary.py3d(dx, dy, dz);
										double v = ReikaMathLibrary.py3d(e.motionX, e.motionY, e.motionZ);
										te.torque = (int)(0.125*ReikaMathLibrary.logbase(mag, 2)*te.type.getTorque()/dd);
										te.omega = (int)(0.125*ReikaMathLibrary.logbase(mag, 2)*te.type.getSpeed()/dd/4D);
										te.power = te.omega*te.torque;
										te.soundtick = 1;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		if (is.stackTagCompound.hasKey("magnet")) {
			if (is.stackTagCompound.getInteger("magnet") >= 1000000)
				par3List.add("Magnetized to "+String.format("%.3f", is.stackTagCompound.getInteger("magnet")/1000000D)+" T");
			else if (is.stackTagCompound.getInteger("magnet") >= 1000)
				par3List.add("Magnetized to "+String.format("%.3f", is.stackTagCompound.getInteger("magnet")/1000D)+" mT");
			else
				par3List.add("Magnetized to "+is.stackTagCompound.getInteger("magnet")+" microTeslas");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		int j;
		switch (type) {
		case 0: //shaftcraft
			j = RotaryNames.shaftPartNames.length;
			break;
		case 1: //enginecraft
			j = RotaryNames.enginePartNames.length;
			break;
		case 2: //heatcraft
			j = RotaryNames.heatPartNames.length;
			break;
		case 3: //misccraft 2
			j = RotaryNames.borerPartNames.length;
			break;
		case 4: //extracts
			j = RotaryNames.extractNames.length;
			break;
		case 6: //compacts
			j = RotaryNames.compactNames.length;
			break;
		case 7: //engines
			j = RotaryNames.engineNames.length;
			break;
		case 8: //powders
			j = RotaryNames.powderNames.length;
			break;
		case 9: //spawner
			j = RotaryNames.spawnerNames.length;
			break;
		case 10: //pipe items
			j = RotaryNames.pipeNames.length;
			break;
		case 11: //shafts
			j = RotaryNames.shaftItemNames.length;
			break;
		case 12: //gearboxes
			j = RotaryNames.gearboxItemNames.length;
			break;
		case 23: //gearunits
			j = RotaryNames.gearUnitNames.length;
			break;
		default:
			j = 0;
			break;
		}
		for (int i = 0; i < j; i++) {
			ItemStack item = new ItemStack(par1, 1, i);
			if (type == 12) {
				if (item.stackTagCompound == null)
					item.setTagCompound(new NBTTagCompound());
				item.stackTagCompound.setInteger("damage", 0);
			}
			if (type != 6 || (type == 6 && (i < 4 || i > 6)))
				par3List.add(item);
			if (item.itemID == ItemStacks.shaftcore.itemID && item.getItemDamage() == ItemStacks.shaftcore.getItemDamage()) {
				ItemStack mag = item.copy();
				mag.stackTagCompound = new NBTTagCompound();
				mag.stackTagCompound.setInteger("magnet", Integer.MAX_VALUE/4);
				par3List.add(mag);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int i) {
		this.setIcons();
		int ty = type;/*
		int ty = this.type;
		while (ty >= 16)
			ty -= 16;
		i += this.type*16;
		//return i;
		return null;*/
		if (i >= 16) {
			ty++;
			i -= 16;
		}
		while (ty >= 16)
			ty -= 16;
		return ico[ty][i];
	}

	private void setIcons() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				tex[i][j] = null;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1)
	{
		for (int i = 0; i < 16; ++i)
			for (int j = 0; j < 16; ++j) {
				tex[i][j] = "RotaryCraft:items"; //backup plan read RotaryNames for filename
				ico[i][j] = par1.registerIcon(tex[i][j]);
				GL11.glColor3f(1, 1, 0);
			}
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		switch(type) {
		case 0:
			return super.getUnlocalizedName() + "." + RotaryNames.shaftPartNames[d];
		case 1:
			return super.getUnlocalizedName() + "." + RotaryNames.enginePartNames[d];
		case 2:
			return super.getUnlocalizedName() + "." + RotaryNames.heatPartNames[d];
		case 3:
			return super.getUnlocalizedName() + "." + RotaryNames.borerPartNames[d];
		case 4:
			return super.getUnlocalizedName() + "." + RotaryNames.extractNames[d];
		case 6:
			return super.getUnlocalizedName() + "." + RotaryNames.compactNames[d];
		case 7:
			return super.getUnlocalizedName() + "." + RotaryNames.engineNames[d];
		case 8:
			return super.getUnlocalizedName() + "." + RotaryNames.powderNames[d];
		case 9:
			return super.getUnlocalizedName() + "." + RotaryNames.spawnerNames[d];
		case 10:
			return super.getUnlocalizedName() + "." + RotaryNames.pipeNames[d];
		case 11:
			return super.getUnlocalizedName() + "." + RotaryNames.shaftNames[d];
		case 12:
			return super.getUnlocalizedName() + "." + RotaryNames.gearboxItemNames[d];
		case 23:
			return super.getUnlocalizedName() + "." + RotaryNames.gearUnitNames[d];
		}
		return super.getUnlocalizedName();
	}

	public static String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/GUI/items.png"; //return the block texture where the block texture is saved in
	}

	public int getType() {
		return type;
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int offset = 0;
		int ty = type;
		while (ty >= 16)
			ty -= 16;
		if (item.getItemDamage() == ItemStacks.silverflakes.getItemDamage() && item.itemID == ItemStacks.silverflakes.itemID) {
			ty++;
			offset = -1;
		}
		return (16*ty+item.getItemDamage()+offset);
	}

}
