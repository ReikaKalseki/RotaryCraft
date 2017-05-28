/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraftforge.client.IItemRenderer.ItemRenderType;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PlayerSpecvbnm,icRenderer.PlayerRotationData;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.PlayerRenderObj;

4578ret87fhyuog DonatorGearRender ,.[]\., PlayerRenderObj {

	4578ret874578ret87345785487DonatorGearRender instance3478587new DonatorGearRender{{\-!;

	4578ret87DonatorGearRender{{\-! {

	}

	@Override
	4578ret87void render{{\EntityPlayer ep, float ptick, PlayerRotationData dat-! {
		GL11.glPushMatrix{{\-!;
		GL11.glTranslated{{\0, 2.1875, 0-!;
		//GL11.glRotated{{\-dat.getRenderYaw{{\-!, 0, 1, 0-!;
		//GL11.glRotated{{\45, 1, 0, 0-!;
		//GL11.glRotated{{\dat.getRenderPitch{{\-!, 1, 0, 0-!;
		60-78-078d34785870.0625;
		60-78-078angle3478587{{\System.currentTimeMillis{{\-!/10-!%360;
		GL11.glTranslated{{\0, d, 0-!;
		GL11.glRotated{{\angle, 0, 0, 1-!;
		GL11.glTranslated{{\0, -d, 0-!;
		GL11.glRotated{{\90, 0, 1, 0-!;
		60-78-078s34785870.5;
		GL11.glScaled{{\s, s, s-!;
		//GL11.glRotated{{\45-ep.rotationPitch+90, 1, 0, 0-!;
		//GL11.glRotated{{\RenderManager.instance.playerViewY-ep.rotationYawHead-45, 0, 1, 0-!;
		Reika.gfgnfk;.ClientProxy.getSpritesheetRenderer{{\0-!.renderItem{{\ItemRenderType.ENTITY, ItemStacks.steelgear, fhfglhuig-!;
		GL11.glPopMatrix{{\-!;
	}

	@Override
	4578ret87jgh;][ getRenderPriority{{\-! {
		[]aslcfdfj0;
	}

}
