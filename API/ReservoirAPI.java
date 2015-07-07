/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.lang.reflect.Field;
import java.util.Collection;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

/** Use this to register custom reservoir handlers, so that a reservoir can perform specialized actions such as special recipe types. */
public class ReservoirAPI {

	private static Collection<TankHandler> list;

	public static void registerHandler(TankHandler th) {
		try {
			list.add(th);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static interface TankHandler {

		/** Returns the amount of liquid to drain. Do not drain the liquidstack directly, but you can modify it in other ways. */
		public int onTick(TileEntity te, FluidStack stored);

	}

	static {
		try {
			Class c = Class.forName("Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir");
			Field f = c.getDeclaredField("tankHandlers");
			f.setAccessible(true);
			list = (Collection<TankHandler>)f.get(null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
