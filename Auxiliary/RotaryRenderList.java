/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RotaryRenderList {

	private static HashMap<MachineRegistry, RotaryTERenderer> renders = new HashMap<MachineRegistry, RotaryTERenderer>();
	private static HashMap<MachineRegistry, MachineRegistry> overrides = new HashMap<MachineRegistry, MachineRegistry>();

	public static boolean addRender(MachineRegistry m, RotaryTERenderer r) {
		if (!renders.containsValue(r)) {
			renders.put(m, r);
			return true;
		}
		else {
			MachineRegistry parent = ReikaJavaLibrary.getHashMapKeyByValue(renders, r);
			overrides.put(m, parent);
			return false;
		}
	}

	public static RotaryTERenderer getRenderForMachine(MachineRegistry m) {
		if (overrides.containsKey(m))
			return renders.get(overrides.get(m));
		return renders.get(m);
	}

	public static String getRenderTexture(MachineRegistry m, RenderFetcher te) {
		return getRenderForMachine(m).getImageFileName(te);
	}

	public static RotaryTERenderer instantiateRenderer(MachineRegistry m) {
		try {
			RotaryTERenderer r = (RotaryTERenderer)Class.forName(m.getRenderer()).newInstance();
			if (addRender(m, r))
				return r;
			else
				return renders.get(overrides.get(m));
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("Tried to call nonexistent render "+m.getRenderer()+"!");
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Tried to call illegal render "+m.getRenderer()+"!");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RegistrationException(RotaryCraft.instance, "No class found for Renderer "+m.getRenderer()+"!");
		}
	}

}