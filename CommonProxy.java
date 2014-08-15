/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.world.World;

public class CommonProxy
{
	public static int IOGoggles;
	public static int NVGoggles;
	public static int NVHelmet;

	public static int armor;
	public static int SteelArmor;

	public static int pipeRender;
	public static int cubeRender;
	public static int connectedRender;

	/**
	 * Client side only register stuff...
	 */
	public void registerRenderers()
	{
		//unused server side. -- see ClientProxy for implementation
	}

	public void addArmorRenders() {}

	public World getClientWorld() {
		return null;
	}

	public void registerRenderInformation() {

	}

	public void registerSounds() {

	}

}// End class CommonProxy