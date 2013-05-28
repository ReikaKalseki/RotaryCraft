/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import Reika.RotaryCraft.GuiHandler;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;

public class RotaryAux {
	public static boolean[] liquidReceiver = new boolean[256];

	public static int blockModel;

	public static final String items1png = "C:/Users/Reika/Downloads/mcp744/src/minecraft/Reika/RotaryCraft/Textures/GUI/items.png";
	public static final String items2png = "C:/Users/Reika/Downloads/mcp744/src/minecraft/Reika/RotaryCraft/Textures/GUI/items2.png";
	public static final String terrainpng = "C:/Users/Reika/Downloads/mcp744/src/minecraft/Reika/RotaryCraft/Textures/Terrain/textures.png";
	public static final String tileentdir = "C:/Users/Reika/Downloads/mcp744/src/minecraft/Reika/RotaryCraft/Textures/TileEntityTex/";
	public static final String mididir = "C:/Users/Reika/Downloads/mcp744/src/minecraft/Reika/RotaryCraft/MIDIs/";

	public static void initializeModel (BaseMod mod) {
		 blockModel = ModLoader.getUniqueBlockModelID(mod, true);
	}

	public static final boolean hasGui(World world, int x, int y, int z, EntityPlayer ep) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
			if (te == null)
				return false;
			if (te.type == null)
				return false;
			if (!te.type.hasGui())
				return false;
			return true;
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter te = (TileEntitySplitter)world.getBlockTileEntity(x, y, z);
			return (te.getBlockMetadata() >= 8);
		}
		if (m == MachineRegistry.SCREEN)
			return !ep.isSneaking();
		Object GUI = GuiHandler.instance.getClientGuiElement(9, ep, world, x, y, z);
		if (GUI != null)
			return true;
		return false;
	}

	public static int get4SidedMetadataFromPlayerLook(EntityLiving ep) {
		int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;
		switch (i) {
		case 0:
			return 2;
		case 1:
			return 1;
		case 2:
			return 3;
		case 3:
			return 0;
		}
		return -1;
	}

	public static int get6SidedMetadataFromPlayerLook(EntityLiving ep) {
		if (MathHelper.abs(ep.rotationPitch) < 60) {
			int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
			while (i > 3)
				i -= 4;
			while (i < 0)
				i += 4;
			switch (i) {
			case 0:
				return 2;
			case 1:
				return 1;
			case 2:
				return 3;
			case 3:
				return 0;
			}
		}
		else { //Looking up/down
			if (ep.rotationPitch > 0)
				return 4; //set to up
			else
				return 5; //set to down
		}
		return -1;
	}

	public static int get2SidedMetadataFromPlayerLook(EntityLiving ep) {
		int i = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 0;
		case 3:
			return 1;
		}
		return -1;
	}

	public static void flipXMetadatas(TileEntity t) {
		if (!(t instanceof RotaryCraftTileEntity))
			return;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)t;
		int m = te.getBlockMetadata();
		switch (m) {
		case 0:
			te.setBlockMetadata(1);
			break;
		case 1:
			te.setBlockMetadata(0);
			break;
		}
	}

	public static void flipZMetadatas(TileEntity t) {
		if (!(t instanceof RotaryCraftTileEntity))
			return;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)t;
		int m = te.getBlockMetadata();
		switch (m) {
		case 2:
			te.setBlockMetadata(3);
			break;
		case 3:
			te.setBlockMetadata(2);
			break;
		}
	}


	public static void dropInventory(World world, int x, int y, int z)
	{
		IInventory ii = (IInventory)world.getBlockTileEntity(x, y, z);
		Random par5Random = new Random();
		if (ii != null) {
			label0:
			for (int i = 0; i < ii.getSizeInventory(); i++){
				ItemStack itemstack = ii.getStackInSlot(i);
				if (itemstack == null)
					continue;
				float f = par5Random.nextFloat() * 0.8F + 0.1F;
				float f1 = par5Random.nextFloat() * 0.8F + 0.1F;
				float f2 = par5Random.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.stackSize <= 0)
						continue label0;
					int j = par5Random.nextInt(21) + 10;
					if (j > itemstack.stackSize)
						j = itemstack.stackSize;
					itemstack.stackSize -= j;
					EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
					if (itemstack.hasTagCompound())
						entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					float f3 = 0.05F;
					entityitem.motionX = (float)par5Random.nextGaussian() * f3;
					entityitem.motionY = (float)par5Random.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float)par5Random.nextGaussian() * f3;
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
				}
				while (true);
			}
		}
	}
}
