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

public class EntityIconLoader {

	public static int getIconX(int id) {
		return id%16;
	}

	public static int getIconY(int id) {
		return id/16;
	}

}
