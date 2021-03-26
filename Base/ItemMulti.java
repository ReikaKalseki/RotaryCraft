/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;
import java.util.Locale;

import com.google.common.base.Strings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.Flywheels;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.GearboxTypes.GearPart;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityACEngine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;

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
		if (super.onItemUse(is, ep, world, x, y, z, s, a, b, c))
			return true;
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (ReikaItemHelper.matchStacks(is, ItemStacks.compost)/* && !world.isRemote*/) {
			ItemDye.applyBonemeal(is, world, x, y, z, ep);
		}
		else if (this.isProperBelt(m, is)) {
			return this.tryBeltConnection(world, x, y, z, is, ep);
		}
		else
			is.stackTagCompound = null;
		return false;
	}

	private boolean tryBeltConnection(World world, int x, int y, int z, ItemStack is, EntityPlayer ep) {
		TileEntityBeltHub te = (TileEntityBeltHub)world.getTileEntity(x, y, z);
		if (is.stackTagCompound == null) {
			is.stackTagCompound = new NBTTagCompound();
			new Coordinate(te).writeToNBT("end1", is.stackTagCompound);
			return true;
		}
		Coordinate c1 = Coordinate.readFromNBT("end1", is.stackTagCompound);
		if (c1 == null) {
			ReikaChatHelper.writeString("No valid other end found");
			return false;
		}
		TileEntity te0 = c1.getTileEntity(world);
		if (!(te0 instanceof TileEntityBeltHub)) {
			ReikaChatHelper.writeString("Tile at other end is invalid");
			return false;
		}
		int dl = c1.getTaxicabDistanceTo(x, y, z)-1;

		//ReikaJavaLibrary.pConsole(dl);
		if (is.stackSize >= dl || ep.capabilities.isCreativeMode) {
			is.stackTagCompound = null;
			TileEntityBeltHub bb = (TileEntityBeltHub)te0;
			bb.resetOther();
			te.resetOther();
			bb.reset();
			te.reset();
			//ReikaJavaLibrary.pConsole(src+":"+tg, Side.SERVER);
			if (te.tryConnect(world, bb.xCoord, bb.yCoord, bb.zCoord) && bb.tryConnect(world, x, y, z)) {
				//ReikaJavaLibrary.pConsole("connected", Side.SERVER);
				if (!ep.capabilities.isCreativeMode)
					is.stackSize -= dl;
				return true;
			}
			else {
				ReikaChatHelper.writeString("Connection is invalid");
			}
		}
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
		if (ReikaItemHelper.matchStacks(is, ItemStacks.shaftcore) || ReikaItemHelper.matchStacks(is, ItemStacks.tungstenshaftcore)) {
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
				else {
					is.stackTagCompound.removeTag("magnet");
					if (is.stackTagCompound.hasNoTags())
						is.stackTagCompound = null;
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		/*
		if (ReikaItemHelper.matchStacks(is, ItemStacks.scrap)) {
			int val = ItemStacks.getScrapValue(is);
			par3List.add(String.format("Value: %d + %d/9 ingots each", val/9, val%9));
			return;
		}
		 */
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
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) { //Adds the metadata blocks to the creative inventory
		int j;
		switch (ItemRegistry.getEntryByID(this)) {
			case SHAFTCRAFT: //shaftcraft
				j = RotaryNames.shaftPartNames.length;
				break;
			case ENGINECRAFT: //enginecraft
				j = RotaryNames.enginePartNames.length;
				break;
			case MISCCRAFT: //heatcraft
				j = RotaryNames.miscPartNames.length;
				break;
			case BORECRAFT: //misccraft 2
				j = RotaryNames.borerPartNames.length;
				break;
			case EXTRACTS: //extracts
				j = RotaryNames.extractNames.length;
				break;
			case COMPACTS: //compacts
				j = RotaryNames.compactNames.length;
				break;
			case ENGINE: //engines
				j = EngineType.engineList.length;
				break;
			case POWDERS: //powders
				j = RotaryNames.powderNames.length;
				break;
			case MODINTERFACE: //mod interface
				j = RotaryNames.interfaceNames.length;
				break;
			case SHAFT: //shafts
				j = MaterialRegistry.matList.length;
				break;
			case GEARBOX: //gearboxes
				j = 0;//j = RotaryNames.getNumberGearTypes();
				break;
			case GEARCRAFT: //gearunits
				j = GearboxTypes.typeList.length*16;
				break;
			case FLYWHEELCRAFT:
				j = Flywheels.list.length;
				break;
			default:
				j = 0;
				break;
		}/*
		if (ItemRegistry.GEARBOX.getItemInstance() == par1) {
			for (GearboxTypes gear : GearboxTypes.typeList) {
				for (int r = 2; r <= 16; r *= 2) {
					ItemStack item = gear.getGearboxItem(r);
					if (item.stackTagCompound == null)
						item.stackTagCompound = new NBTTagCompound();
					item.stackTagCompound.setInteger("damage", 0);
					par3List.add(item);
				}
			}
		}
		else {*/
		for (int i = 0; i < j; i++) {
			ItemStack item = new ItemStack(par1, 1, i);
			if (ItemRegistry.GEARCRAFT.matchItem(item)) {
				if (!GearboxTypes.getMaterialFromCraftingItem(item).isLoadable())
					continue;
				if (item.getItemDamage()%16 >= GearPart.list.length)
					continue;
				if (ReikaItemHelper.matchStacks(item, GearboxTypes.WOOD.getPart(GearPart.SHAFT))) //stick
					continue;
			}
			if (ItemRegistry.SHAFTCRAFT.matchItem(item)) {
				if (RotaryNames.shaftPartNames[item.getItemDamage()].isEmpty())
					continue;
			}
			String unloc = this.getUnlocalizedName(item);
			if (Strings.isNullOrEmpty(unloc) || unloc.endsWith("."))
				continue;
			par3List.add(item);
			if (ReikaItemHelper.matchStacks(item, ItemStacks.shaftcore) || ReikaItemHelper.matchStacks(item, ItemStacks.tungstenshaftcore)) {
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
			//}
		}
	}

	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		int d = is.getItemDamage();
		String s = super.getUnlocalizedName();
		try {
			switch (ItemRegistry.getEntryByID(this)) {
				case SHAFTCRAFT:
					s = super.getUnlocalizedName() + "." + RotaryNames.shaftPartNames[d];
					break;
				case ENGINECRAFT:
					s = super.getUnlocalizedName() + "." + RotaryNames.enginePartNames[d];
					break;
				case MISCCRAFT:
					s = super.getUnlocalizedName() + "." + RotaryNames.miscPartNames[d];
					break;
				case BORECRAFT:
					s = super.getUnlocalizedName() + "." + RotaryNames.borerPartNames[d];
					break;
				case EXTRACTS:
					s = super.getUnlocalizedName() + "." + RotaryNames.extractNames[d];
					break;
				case COMPACTS:
					s = super.getUnlocalizedName() + "." + RotaryNames.compactNames[d];
					break;
				case ENGINE:
					s = super.getUnlocalizedName() + "." + RotaryNames.getEngineName(d);
					break;
				case POWDERS:
					s = super.getUnlocalizedName() + "." + RotaryNames.powderNames[d];
					break;
				case SHAFT:
					s = MaterialRegistry.matList[d].getShaftUnlocName();
					break;
				case GEARBOX:
					//s = super.getUnlocalizedName() + "." + RotaryNames.getGearboxName(d);
					break;
				case MODINTERFACE:
					s = super.getUnlocalizedName() + "." + RotaryNames.interfaceNames[d];
					break;
				case GEARCRAFT:
					s = super.getUnlocalizedName() + "." + GearboxTypes.getMaterialFromCraftingItem(is)+"."+GearPart.list[d%16];
					break;
				default:
					break;
			}
			return ReikaStringParser.stripSpaces(s.toLowerCase(Locale.ENGLISH));
		}
		catch (Exception e) {
			return "Invalid item: "+e.toString();
		}
	}

	/*
	@SideOnly(Side.CLIENT)
	private boolean offsetDustName(ItemStack is) {
		EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
		return (ep.posY < 15 || ep.worldObj.provider.hasNoSky) && ep.getCommandSenderName().equals("Freyskol");
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		if (ReikaItemHelper.matchStacks(item, ItemStacks.bedrockdust) && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && this.offsetDustName(item)) {
			return ((ItemMulti)ItemStacks.salt.getItem()).getItemStackDisplayName(ItemStacks.salt);
		}
		return super.getItemStackDisplayName(item);
	}
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getItemSpriteIndex(ItemStack item) {
		//if (ReikaItemHelper.matchStacks(item, ItemStacks.bedrockdust) && this.offsetDustName(item)) {
		//	return ((ItemMulti)ItemStacks.salt.getItem()).getItemSpriteIndex(ItemStacks.salt);
		//}
		if (ItemRegistry.GEARCRAFT.matchItem(item)) {
			return /*GearboxTypes.getMaterialFromCraftingItem(item).metaOffset+*/item.getItemDamage();
		}
		if (ItemRegistry.FLYWHEELCRAFT.matchItem(item)) {
			return /*GearboxTypes.getMaterialFromCraftingItem(item).metaOffset+*/type+item.getItemDamage();
		}
		int row = type%16+item.getItemDamage()/16;
		if (ItemRegistry.EXTRACTS.matchItem(item) && item.getItemDamage() > 31)
			return 16*9+item.getItemDamage()-32;
		if (ItemRegistry.ENGINECRAFT.matchItem(item) && item.getItemDamage() >= 16) {
			row += 9;
		}
		if (ItemRegistry.POWDERS.matchItem(item) && item.getItemDamage() >= 16) {
			row += 3;
		}
		return 16*row+item.getItemDamage()%16;
	}

}
