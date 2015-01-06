/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import Reika.DragonAPI.Command.DragonCommandBase;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class FindMachinesCommand extends DragonCommandBase {

	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		MachineRegistry tile = null;
		try {
			tile = MachineRegistry.valueOf(args[0].toUpperCase());
		}
		catch (IllegalArgumentException e) {

		}
		EntityPlayerMP ep = this.getCommandSenderAsPlayer(ics);
		if (tile == null) {
			ReikaChatHelper.sendChatToPlayer(ep, EnumChatFormatting.RED+"'"+args[0]+"' is not a valid RC machine.");
			return;
		}
		Class c = tile.getTEClass();
		Collection<TileEntity> tiles = new ArrayList();
		Collection<World> worlds = this.getWorlds(ep, args);
		for (World world : worlds) {
			for (Object o : world.loadedTileEntityList) {
				if (c.isAssignableFrom(o.getClass())) {
					tiles.add((TileEntity)o);
				}
			}
			char e = tiles.isEmpty() ? '.' : ':';
			ReikaChatHelper.sendChatToPlayer(ep, tiles.size()+" "+tile.getName()+" found in world '"+world.provider.getDimensionName()+"'"+e);
			if (!tiles.isEmpty()) {
				for (TileEntity te : tiles)
					ReikaChatHelper.sendChatToPlayer(ep, te.toString());
			}
		}
	}

	private Collection<World> getWorlds(EntityPlayerMP ep, String[] args) {
		Collection<World> li = new ArrayList();
		if (args.length == 1)
			li.add(ep.worldObj);
		else {
			if (args[1].equals("*")) {
				WorldServer[] ids = DimensionManager.getWorlds();
				for (int i = 0; i < ids.length; i++) {
					li.add(ids[i]);
				}
			}
			else {
				try {
					int dim = Integer.parseInt(args[1]);
					World world = DimensionManager.getWorld(dim);
					if (world != null) {
						li.add(world);
					}
					else {
						ReikaChatHelper.sendChatToPlayer(ep, EnumChatFormatting.RED.toString()+"No world exists with dimension ID "+dim+".");
					}
				}
				catch (NumberFormatException e) {
					ReikaChatHelper.sendChatToPlayer(ep, EnumChatFormatting.RED.toString()+"\""+args[1]+"\" is not an integer.");
				}
			}
		}
		return li;
	}

	@Override
	public String getCommandString() {
		return "findmachines";
	}

	@Override
	protected boolean isAdminOnly() {
		return true;
	}

}
