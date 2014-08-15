/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public abstract class EntityModelBase extends ModelBase {

	protected final void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public final void renderAll() {}
	public final void renderAll(List li, float phi) {}

	@Override
	public abstract void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity);

	@Override
	public abstract void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5);
}