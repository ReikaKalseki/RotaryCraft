/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaDyeHelper;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDisplay extends TileEntityPowerReceiver implements GuiController {

	private float scroll;
	private int[] rgb = new int[3];
	private int[] Brgb = new int[3];
	private static final int[] ArRGB = {0, 128, 255};
	private static final int[] ArBRGB = {0, 255, 255};
	private ArrayList<String> message = new ArrayList<String>();
	public static final int displayHeight = 12; //in lines
	public static final int displayWidth = 41; //in chars
	public static final int lineHeight = 12;
	public static final int charWidth = 10;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (this.getMessageLength() <= displayHeight)
			return;
		scroll += 0.05F;
		if (scroll > this.getMessageLength())
			scroll = 0;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.DISPLAY.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

	}

	public boolean hasList() {
		if (message == null)
			return false;
		if (message.size() <= 0)
			return false;
		return true;
	}

	public String getMessageLine(int line) {
		String l = message.get(line);
		if (l.length() > displayWidth) {
			String rl = l.substring(0, displayWidth);
			String el = l.substring(displayWidth+1);
			message.add(line+1, el);
			message.set(line, rl);
			return rl;
		}
		return l;
	}

	public int getMessageLength() {
		return message.size();
	}

	public float getScrollPos() {
		return scroll;
	}

	public boolean isReadyToLoadNewLine() {
		float frac = scroll-(int)scroll;
		return frac >= 0.5F;
	}

	public int getRoundedScroll() {
		float frac = scroll-(int)scroll;
		if (frac >= 0.5F)
			return (int)(scroll)+1;
		else
			return (int)(scroll);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public boolean hasSpace() {
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int meta = this.getBlockMetadata();
		for (int j = -2; j <= 2; j++) {
			int a = 0;
			int b = 0;
			if (meta == 0 || meta == 1)
				b = j;
			else
				a = j;
			for (int i = 1; i <= 3; i++) {
				if (world.getBlockId(x+a, y+i, z+b) != 0)
					return false;
			}
		}
		return true;
	}

	public int getRed() {
		return rgb[0];
	}

	public int getGreen() {
		return rgb[1];
	}

	public int getBlue() {
		return rgb[2];
	}

	public int getBorderRed() {
		return Brgb[0];
	}

	public int getBorderGreen() {
		return Brgb[1];
	}

	public int getBorderBlue() {
		return Brgb[2];
	}

	public int getTextColor() {
		return 0xffffff;
	}

	public void addLine(String str) {
		if (str.length() > displayWidth) {
			String rstr = str.substring(0, displayWidth);
			String estr = str.substring(displayWidth+1);
			message.add(rstr);
			message.add(estr);
		}
		else
			message.add(str);
	}

	public void deleteLine(int l) {
		message.remove(l);
	}

	public void clearMessage() {
		message.clear();
	}

	public void setColor(ReikaDyeHelper dye) {
		int r = dye.getRed();
		int g = dye.getGreen();
		int b = dye.getBlue();
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
		Brgb[0] = r;
		Brgb[1] = g;
		Brgb[2] = b;
	}

	public void setFullMessage(String str) {
		this.clearMessage();
		if (str.length() > displayWidth) {
			ArrayList<String> li = new ArrayList<String>();
			while (str != null && str.length() > 0) {
				if (str.length() <= displayWidth) {
					message.add(str);
					return;
				}
				else {
					message.add(str.substring(0, displayWidth));
					str = str.substring(displayWidth+1);
				}
			}
		}
		else
			message.add(str);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("color", rgb);
		NBT.setIntArray("Bcolor", Brgb);
		/*
		if (!message.isEmpty()) {
			for (int i = 0; i < message.size(); i++) {
				String str = message.get(i);
				if (str != null && !str.isEmpty()) {
					NBT.setString("msg"+i, str);
				}
			}
		}*/
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		rgb = NBT.getIntArray("color");
		Brgb = NBT.getIntArray("Bcolor");

		//message = new ArrayList<String>();
	}

	public void readFromFile() {

	}

	public void saveToFile() {

	}
}
