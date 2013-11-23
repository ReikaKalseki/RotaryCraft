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

import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.util.Color;

public class ElectricDischarge {

	public final int charge;
	public final double targetX;
	public final double targetY;
	public final double targetZ;


	public ElectricDischarge(int coul, EntityLivingBase target) {
		charge = coul;
		targetX = target.posX;
		targetY = target.posY;
		targetZ = target.posZ;
	}

	public ElectricDischarge(int coul, int x, int y, int z) {
		charge = coul;
		targetX = x;
		targetY = y;
		targetZ = z;
	}

	public int getCurrent() {
		return charge*20; //1A = 1C/s, 1t = 1/20 s
	}

	public Color getColor() {
		int a = this.getCurrent();
		if (a > 2500) {
			return new Color(127, 0, 255);
		}
		else if (a > 500) {
			return new Color(0, 192, 255);
		}
		else if (a > 100) {
			return new Color(255, 255, 255);
		}
		else if (a > 20) {
			return new Color(255, 255, 0);
		}
		else
			return new Color(255, 127, 0);
	}

}
