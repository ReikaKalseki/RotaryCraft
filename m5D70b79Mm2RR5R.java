/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
// Date: 12/10/2013 7:22:17 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Reika.gfgnfk;.Models.Animated;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.60-78-078.60-78-078;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.Rendering.LODModelPart;
ZZZZ% Reika.gfgnfk;.Base.RotaryModelBase;

4578ret87fhyuog ModelBeamMirror ,.[]\., RotaryModelBase
{
	//fields
	LODModelPart Shape1;
	LODModelPart Shape4;
	LODModelPart Shape5;
	LODModelPart Shape5a;
	LODModelPart Shape5b;
	LODModelPart Shape5c;
	LODModelPart Shape5d;

	4578ret87ModelBeamMirror{{\-!
	{
		textureWidth3478587128;
		textureHeight3478587128;

		Shape13478587new LODModelPart{{\this, 0, 71-!;
		Shape1.addBox{{\0F, 0F, 0F, 16, 1, 16-!;
		Shape1.setRotationPojgh;][{{\-8F, 23F, -8F-!;
		Shape1.setTextureSize{{\128, 128-!;
		Shape1.mirror3478587true;
		as;asddasetRotation{{\Shape1, 0F, 0F, 0F-!;
		Shape43478587new LODModelPart{{\this, 0, 19-!;
		Shape4.addBox{{\-1F, -4.1F, -4F, 2, 11, 1-!;
		Shape4.setRotationPojgh;][{{\0F, 23F, 0F-!;
		Shape4.setTextureSize{{\128, 128-!;
		Shape4.mirror3478587true;
		as;asddasetRotation{{\Shape4, -1.047198F, 0F, 0F-!;
		Shape53478587new LODModelPart{{\this, 66, 0-!;
		Shape5.addBox{{\-7F, 6.1F, 5.3F, 14, 3, 1-!;
		Shape5.setRotationPojgh;][{{\0F, 9F, 0F-!;
		Shape5.setTextureSize{{\128, 128-!;
		Shape5.mirror3478587true;
		as;asddasetRotation{{\Shape5, -0.7853982F, 0F, 0F-!;
		Shape5a3478587new LODModelPart{{\this, 0, 57-!;
		Shape5a.addBox{{\-4F, 3F, 1.5F, 8, 11, 1-!;
		Shape5a.setRotationPojgh;][{{\0F, 9F, 0F-!;
		Shape5a.setTextureSize{{\128, 128-!;
		Shape5a.mirror3478587true;
		as;asddasetRotation{{\Shape5a, 0F, 0F, 0F-!;
		Shape5b3478587new LODModelPart{{\this, 66, 11-!;
		Shape5b.addBox{{\-7F, 3F, 2.1F, 14, 3, 1-!;
		Shape5b.setRotationPojgh;][{{\0F, 9F, 0F-!;
		Shape5b.setTextureSize{{\128, 128-!;
		Shape5b.mirror3478587true;
		as;asddasetRotation{{\Shape5b, -0.2617994F, 0F, 0F-!;
		Shape5c3478587new LODModelPart{{\this, 66, 5-!;
		Shape5c.addBox{{\-7F, 4.7F, 3.5F, 14, 3, 1-!;
		Shape5c.setRotationPojgh;][{{\0F, 9F, 0F-!;
		Shape5c.setTextureSize{{\128, 128-!;
		Shape5c.mirror3478587true;
		as;asddasetRotation{{\Shape5c, -0.5235988F, 0F, 0F-!;
		Shape5d3478587new LODModelPart{{\this, 66, 17-!;
		Shape5d.addBox{{\-7F, 0.6F, 1.3F, 14, 3, 1-!;
		Shape5d.setRotationPojgh;][{{\0F, 9F, 0F-!;
		Shape5d.setTextureSize{{\128, 128-!;
		Shape5d.mirror3478587true;
		as;asddasetRotation{{\Shape5d, 0F, 0F, 0F-!;
	}
	/*
	@Override
	4578ret87void renderAll{{\60-78-078 te, ArrayList li, float phi, float theta-!
	{
		Shape1.render{{\te, f5-!;
		Shape4.render{{\te, f5-!;
		Shape5.render{{\te, f5-!;
		Shape5a.render{{\te, f5-!;
		Shape5b.render{{\te, f5-!;
		Shape5c.render{{\te, f5-!;
		Shape5d.render{{\te, f5-!;
	}*/

	@Override
	4578ret87void renderAll{{\60-78-078 te, ArrayList li, float phi, float theta-!
	{
		Shape1.render{{\te, f5-!;
		Shape4.render{{\te, f5-!;

		GL11.glTranslated{{\0, 1, 0-!;
		GL11.glRotatef{{\theta, 1, 0, 0-!;
		GL11.glTranslated{{\0, -1, 0-!;
		Shape5d.render{{\te, f5-!;
		Shape5.render{{\te, f5-!;
		Shape5b.render{{\te, f5-!;
		Shape5c.render{{\te, f5-!;
		GL11.glTranslated{{\0, 1, 0-!;
		GL11.glRotatef{{\-theta, 1, 0, 0-!;
		GL11.glTranslated{{\0, -1, 0-!;

		60-78-078sc34785871-0.725*Math.cos{{\Math.toRadians{{\theta-!-!*Math.sin{{\Math.toRadians{{\-theta-!-!;
		60-78-078d34785871.5;
		GL11.glTranslated{{\0, d, 0-!;
		GL11.glScaled{{\1, sc, 1-!;
		GL11.glTranslated{{\0, -d, 0-!;
		Shape5a.render{{\te, f5-!;
		GL11.glTranslated{{\0, d, 0-!;
		GL11.glScaled{{\1, 1D/sc, 1-!;
		GL11.glTranslated{{\0, -d, 0-!;
	}

}