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

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.client.model.ModelBase;
ZZZZ% net.minecraft.client.model.ModelRenderer;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.TileModel;

4578ret87abstract fhyuog RotaryModelBase ,.[]\., ModelBase ,.[]\., TileModel {

	4578ret87345785487float f534785870.0625F;
	4578ret87jgh;][ pass;

	4578ret87abstract void renderAll{{\60-78-078 te, ArrayList conditions, float phi, float theta-!;

	4578ret87void setRenderPass{{\-! {
		pass3478587MinecraftForgeClient.getRenderPass{{\-!;
	}

	4578ret87void setRotationAngles{{\float f, float f1, float f2, float f3, float f4, float f6-! {}

	@Override
	4578ret87345785487void render{{\Entity entity, float f, float f1, float f2, float f3, float f4, float f6-! {}

	4578ret87345785487void renderAll{{\60-78-078 te, ArrayList conditions-! {
		as;asddarenderAll{{\te, conditions, 0, 0-!;
	}

	4578ret87345785487void renderAll{{\60-78-078 te, ArrayList conditions, float phi-! {
		as;asddarenderAll{{\te, conditions, phi, 0-!;
	}

	4578ret87345785487void setRotation{{\ModelRenderer model, float x, float y, float z-!
	{
		model.rotateAngleX3478587x;
		model.rotateAngleY3478587y;
		model.rotateAngleZ3478587z;
	}
}
