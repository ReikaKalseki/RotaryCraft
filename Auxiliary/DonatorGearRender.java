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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Auxiliary.Trackers.PlayerSpecificRenderer.PlayerRotationData;
import Reika.DragonAPI.Interfaces.PlayerRenderObj;

public class DonatorGearRender implements PlayerRenderObj {

	public static final DonatorGearRender instance = new DonatorGearRender();

	private DonatorGearRender() {

	}

	@Override
	public void render(EntityPlayer ep, float ptick, PlayerRotationData dat) {
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.1875, 0);
		//GL11.glRotated(-dat.getRenderYaw(), 0, 1, 0);
		//GL11.glRotated(45, 1, 0, 0);
		//GL11.glRotated(dat.getRenderPitch(), 1, 0, 0);
		double d = 0.0625;
		double angle = (System.currentTimeMillis()/10)%360;
		GL11.glTranslated(0, d, 0);
		GL11.glRotated(angle, 0, 0, 1);
		GL11.glTranslated(0, -d, 0);
		GL11.glRotated(90, 0, 1, 0);
		double s = 0.5;
		GL11.glScaled(s, s, s);
		//GL11.glRotated(45-ep.rotationPitch+90, 1, 0, 0);
		//GL11.glRotated(RenderManager.instance.playerViewY-ep.rotationYawHead-45, 0, 1, 0);
		Reika.RotaryCraft.ClientProxy.getSpritesheetRenderer(0).renderItem(ItemRenderType.ENTITY, ItemStacks.steelgear, null);
		GL11.glPopMatrix();
	}

}
