/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public static int IOGoggles;
	public static int NVGoggles;
	public static int NVHelmet;

/**
 * Client side only register stuff...
 */
public void registerRenderers()
  {
  //unused server side. -- see ClientProxy for implementation
  }

public void addArmorRenders() {}

@Override
public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
  // TODO Auto-generated method stub
  return null;
  }

@Override
public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
  // TODO Auto-generated method stub
  return null;
  }

public World getClientWorld() {
	return null;
}

public void registerRenderInformation() {
	// TODO Auto-generated method stub

}

public void registerSounds() {
	// TODO Auto-generated method stub
}

}// End class CommonProxy
