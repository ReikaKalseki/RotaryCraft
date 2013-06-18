/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class ItemCannonKey extends ItemBasic {

	public ItemCannonKey(int ID, int tex) {
		super(ID, tex, 1);
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
		is.stackTagCompound.setString("owner", ep.getEntityName());
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
		if (!is.hasTagCompound())
			return false;
		if (!is.stackTagCompound.hasKey("owner"))
			return false;
		TileEntityAimedCannon can = (TileEntityAimedCannon)world.getBlockTileEntity(x, y, z);
		String owner = is.stackTagCompound.getString("owner");
		if (!owner.equals(can.placer)) {
			ReikaChatHelper.write("The key is for "+owner+"'s machines, but this machine is owned by "+can.placer+"!");
			return false;
		}
		String name = ep.getEntityName();
		if (can.playerIsSafe(name)) {
			ReikaChatHelper.write(name+" is already on the whitelist!");
			return false;
		}
		can.addPlayerToWhiteList(name);
		if (!ep.capabilities.isCreativeMode)
			ep.setCurrentItemOrArmor(0, null);
		return true;
	}

}
