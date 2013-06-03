/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;

public abstract class RotaryModelBase extends ModelBase {

	protected final float f5 = 0.0625F;
	protected int pass;

	public abstract void renderAll(List conditions, float phi);

	public final void renderAll(boolean a) {
		this.renderAll(ReikaJavaLibrary.makeListFrom(a), 0);
	}

	public final void renderAll(boolean a, boolean b) {
		Object[] arr = {a,b};
		this.renderAll(ReikaJavaLibrary.makeListFromArray(arr), 0);
	}

	public final void renderAll(boolean a, boolean b, boolean c) {
		Object[] arr = {a,b,c};
		this.renderAll(ReikaJavaLibrary.makeListFromArray(arr), 0);
	}

	public final void renderAll(boolean a, boolean b, boolean c, boolean d) {
		Object[] arr = {a,b,c,d};
		this.renderAll(ReikaJavaLibrary.makeListFromArray(arr), 0);
	}

	/** To prevent use of the basic method *//*
	  public final void renderAll() {

	  }*/

	public void setRenderPass() {
		pass = MinecraftForgeClient.getRenderPass();
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f6) {}

	@Override
	public final void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f6) {}

	protected final void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
