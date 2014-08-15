/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityACEngine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMulti extends ItemBasic {

	private int type;

	public ItemMulti(int par2type) {
		super(0);
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		type = par2type;
		maxStackSize = 64;
		//this.setIconCoord(0, 0);
	}

	public ItemMulti(int par2type, int max) {
		super(0); //Returns super constructor: par1 is ID
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		type = par2type;
		maxStackSize = max;
		//this.setIconCoord(0, 0);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (this.isProperBelt(m, is)) {
			TileEntityBeltHub te = (TileEntityBeltHub)world.getTileEntity(x, y, z);
			if (is.stackTagCompound == null) {
				is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setInteger("ex", Integer.MIN_VALUE);
				is.stackTagCompound.setInteger("ey", Integer.MIN_VALUE);
				is.stackTagCompound.setInteger("ez", Integer.MIN_VALUE);
				is.stackTagCompound.setInteger("rx", Integer.MIN_VALUE);
				is.stackTagCompound.setInteger("ry", Integer.MIN_VALUE);
				is.stackTagCompound.setInteger("rz", Integer.MIN_VALUE);
			}
			if (te.isEmitting) {
				is.stackTagCompound.setInteger("ex", x);
				is.stackTagCompound.setInteger("ey", y);
				is.stackTagCompound.setInteger("ez", z);
			}
			else {
				is.stackTagCompound.setInteger("rx", x);
				is.stackTagCompound.setInteger("ry", y);
				is.stackTagCompound.setInteger("rz", z);
			}
			int ex = is.stackTagCompound.getInteger("ex");
			int ey = is.stackTagCompound.getInteger("ey");
			int ez = is.stackTagCompound.getInteger("ez");
			int rx = is.stackTagCompound.getInteger("rx");
			int ry = is.stackTagCompound.getInteger("ry");
			int rz = is.stackTagCompound.getInteger("rz");

			int dl = Math.abs(ex-rx+ey-ry+ez-rz)-1;

			//ReikaJavaLibrary.pConsole(dl);
			if (is.stackSize >= dl || ep.capabilities.isCreativeMode) {
				if (rx != Integer.MIN_VALUE && ry != Integer.MIN_VALUE && rz != Integer.MIN_VALUE) {
					if (ex != Integer.MIN_VALUE && ey != Integer.MIN_VALUE && ez != Integer.MIN_VALUE) {
						TileEntityBeltHub em = (TileEntityBeltHub)world.getTileEntity(ex, ey, ez);
						TileEntityBeltHub rec = (TileEntityBeltHub)world.getTileEntity(rx, ry, rz);

						//ReikaJavaLibrary.pConsole(rec+"\n"+em);
						if (em == null) {
							ReikaChatHelper.writeString("Belt Hub missing at "+ex+", "+ey+", "+ez);
							is.stackTagCompound = null;
							return false;
						}
						if (rec == null) {
							ReikaChatHelper.writeString("Belt Hub missing at "+rx+", "+ry+", "+rz);
							is.stackTagCompound = null;
							return false;
						}
						rec.resetOther();
						em.resetOther();
						em.reset();
						rec.reset();
						boolean src = em.setSource(rx, ry, rz);
						boolean tg = rec.setTarget(ex, ey, ez);
						//ReikaJavaLibrary.pConsole(src+":"+tg, Side.SERVER);
						if (src && tg) {
							//ReikaJavaLibrary.pConsole("connected", Side.SERVER);
							if (!ep.capabilities.isCreativeMode)
								is.stackSize -= dl;
						}
						is.stackTagCompound = null;
					}
				}
			}
		}
		else
			is.stackTagCompound = null;
		return false;
	}

	private boolean isProperBelt(MachineRegistry m, ItemStack is) {
		if (m == MachineRegistry.BELT && ReikaItemHelper.matchStacks(is, ItemStacks.belt))
			return true;
		if (m == MachineRegistry.CHAIN && ReikaItemHelper.matchStacks(is, ItemStacks.chain))
			return true;
		return false;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity e, int par4, boolean par5) {
		if (ReikaItemHelper.matchStacks(is, ItemStacks.shaftcore)) {
			if (is.stackTagCompound != null) {
				int mag = is.stackTagCompound.getInteger("magnet");
				if (mag > 0) {
					int x = (int)e.posX;
					int y = (int)e.posY;
					int z = (int)e.posZ;
					for (int i = -6; i <= 6; i++) {
						for (int j = -6; j <= 6; j++) {
							for (int k = -6; k <= 6; k++) {
								if (world.getTileEntity(x+i, y+j, z+k) instanceof TileEntityACEngine) {
									TileEntityACEngine te = (TileEntityACEngine)world.getTileEntity(x+i, y+j, z+k);
									double dx = x-te.xCoord-0.5;
									double dy = y-te.yCoord-0.5;
									double dz = z-te.zCoord-0.5;
									double dd = ReikaMathLibrary.py3d(dx, dy, dz);
									double v = ReikaMathLibrary.py3d(e.motionX, e.motionY, e.motionZ);
									te.magneticInterference(mag, dd);
									te.soundtick = 1;
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
		par3List.add("Additional Data Present.");
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
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
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
			j = RotaryNames.miscPartNames.length;
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
			j = EngineType.engineList.length;
			break;
		case 8: //powders
			j = RotaryNames.powderNames.length;
			break;
		case 14: //mod interface
			j = RotaryNames.interfaceNames.length;
			break;
		case 11: //shafts
			j = RotaryNames.getNumberShaftTypes();
			break;
		case 12: //gearboxes
			j = RotaryNames.getNumberGearTypes();
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
			if (ItemRegistry.GEARBOX.matchItem(item)) {
				if (item.stackTagCompound == null)
					item.stackTagCompound = new NBTTagCompound();
				item.stackTagCompound.setInteger("damage", 0);
			}
			if (ItemRegistry.COMPACTS.matchItem(item)) {
				par3List.add(item);
			}
			else {
				par3List.add(item);
			}
			if (ReikaItemHelper.matchStacks(item, ItemStacks.shaftcore)) {
				ItemStack mag = item.copy();
				if (DragonAPICore.isReikasComputer()) {
					mag.stackTagCompound = new NBTTagCompound();
					mag.stackTagCompound.setInteger("magnet", 32);
					par3List.add(mag);

					mag = item.copy();
					mag.stackTagCompound = new NBTTagCompound();
					mag.stackTagCompound.setInteger("magnet", 64000);
					par3List.add(mag);

					mag = item.copy();
				}
				mag.stackTagCompound = new NBTTagCompound();
				mag.stackTagCompound.setInteger("magnet", Integer.MAX_VALUE/4);
				par3List.add(mag);
			}
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
		String s = super.getUnlocalizedName();
		switch(type) {
		case 0:
			s = super.getUnlocalizedName() + "." + RotaryNames.shaftPartNames[d];
			break;
		case 1:
			s = super.getUnlocalizedName() + "." + RotaryNames.enginePartNames[d];
			break;
		case 2:
			s = super.getUnlocalizedName() + "." + RotaryNames.miscPartNames[d];
			break;
		case 3:
			s = super.getUnlocalizedName() + "." + RotaryNames.borerPartNames[d];
			break;
		case 4:
			s = super.getUnlocalizedName() + "." + RotaryNames.extractNames[d];
			break;
		case 6:
			s = super.getUnlocalizedName() + "." + RotaryNames.compactNames[d];
			break;
		case 7:
			s = super.getUnlocalizedName() + "." + RotaryNames.getEngineName(d);
			break;
		case 8:
			s = super.getUnlocalizedName() + "." + RotaryNames.powderNames[d];
			break;
		case 14:
			s = super.getUnlocalizedName() + "." + RotaryNames.interfaceNames[d];
			break;
		case 10:
			s = super.getUnlocalizedName() + "." + RotaryNames.pipeNames[d];
			break;
		case 11:
			s = super.getUnlocalizedName() + "." + RotaryNames.getShaftName(d);
			break;
		case 12:
			s = super.getUnlocalizedName() + "." + RotaryNames.getGearboxName(d);
			break;
		case 23:
			s = super.getUnlocalizedName() + "." + RotaryNames.gearUnitNames[d];
			break;
		}
		return ReikaStringParser.stripSpaces(s.toLowerCase());
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int row = type+item.getItemDamage()/16;
		while (row >= 16)
			row -= 16;
		if (ItemRegistry.EXTRACTS.matchItem(item) && item.getItemDamage() > 31)
			return 16*9+item.getItemDamage()-32;
		return 16*row+item.getItemDamage()%16;
	}

}