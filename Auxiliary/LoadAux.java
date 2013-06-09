/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;

public class LoadAux {

	public static void texMsg() {
		String msg = "Ignore the \"TextureManager.createTexture called for file\" warnings below. They are irrelevant and the result of intentional behavior.";
		ReikaJavaLibrary.printLine(msg.length());
		ReikaJavaLibrary.printLine(msg.length());
		ReikaJavaLibrary.pConsole(msg);
		ReikaJavaLibrary.printLine(msg.length());
		ReikaJavaLibrary.printLine(msg.length());
	}

}
