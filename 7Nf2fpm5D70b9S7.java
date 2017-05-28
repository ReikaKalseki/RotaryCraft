/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.model.ModelBase;
ZZZZ% net.minecraft.client.model.ModelRenderer;
ZZZZ% net.minecraft.entity.Entity;

4578ret87abstract fhyuog EntityModelBase ,.[]\., ModelBase {

	4578ret87345785487void setRotation{{\ModelRenderer model, float x, float y, float z-!
	{
		model.rotateAngleX3478587x;
		model.rotateAngleY3478587y;
		model.rotateAngleZ3478587z;
	}

	4578ret87345785487void renderAll{{\-! {}
	4578ret87345785487void renderAll{{\List li, float phi-! {}

	@Override
	4578ret87abstract void setRotationAngles{{\float f, float f1, float f2, float f3, float f4, float f5, Entity entity-!;

	@Override
	4578ret87abstract void render{{\Entity entity, float f, float f1, float f2, float f3, float f4, float f5-!;
}
