/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCannonKey extends ItemRotaryTool {

	public ItemCannonKey(int tex) {
		super(tex);
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity e, int par4, boolean par5) {
		if (!(e instanceof EntityPlayer))
			return;
		if (!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		if (is.stackTagCompound.hasKey("owner"))
			return;
		EntityPlayer ep = (EntityPlayer)e;
		is.stackTagCompound.setString("owner", ep.getCommandSenderName());
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		if (is.stackTagCompound.hasKey("owner"))
			par3List.add(is.stackTagCompound.getString("owner")+"'s Cannon Key");
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == null)
			return false;
		if (!m.isCannon())
			return false;
		TileEntityAimedCannon can = (TileEntityAimedCannon)world.getTileEntity(x, y, z);
		String name = ep.getCommandSenderName();
		if (DragonOptions.DEBUGMODE.getState()) {
			ReikaChatHelper.write("Key is held by "+name+"; machine was placed by "+can.getPlacerName());
			ReikaChatHelper.write("name.equals(placer): "+name.equals(can.getPlacerName()));
			ReikaChatHelper.write("name.compareTo(placer): "+name.compareTo(can.getPlacerName()));
		}
		if (name.equals(can.getPlacerName())) {
			ep.openGui(RotaryCraft.instance, GuiRegistry.SAFEPLAYERS.ordinal(), world, x, y, z);
			return true;
		}
		else {
			if (!is.hasTagCompound())
				return false;
			if (!is.stackTagCompound.hasKey("owner"))
				return false;
			String owner = is.stackTagCompound.getString("owner");
			if (DragonOptions.DEBUGMODE.getState()) {
				ReikaChatHelper.write("Key is made by "+owner+"; machine was placed by "+can.getPlacerName());
				ReikaChatHelper.write("owner.equals(placer): "+owner.equals(can.getPlacerName()));
				ReikaChatHelper.write("owner.compareTo(placer): "+owner.compareTo(can.getPlacerName()));
			}
			if (!owner.equals(can.getPlacerName())) {
				ReikaChatHelper.write("The key is for "+owner+"'s machines, but this machine is owned by "+can.getPlacerName()+"!");
				return false;
			}
			if (can.playerIsSafe(ep)) {
				ReikaChatHelper.write(name+" is already on the whitelist!");
				return false;
			}
			can.addPlayerToWhiteList(name);
			if (!ep.capabilities.isCreativeMode)
				ep.setCurrentItemOrArmor(0, null);
		}

		return true;
	}

}