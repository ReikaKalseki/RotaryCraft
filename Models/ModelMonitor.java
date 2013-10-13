/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
// Date: 14/03/2013 9:51:16 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Reika.RotaryCraft.Models;

import java.util.List;

import net.minecraft.client.model.ModelRenderer;

import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Base.RotaryModelBase;

public class ModelMonitor extends RotaryModelBase
{
	//fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape6;
	ModelRenderer Shape6a;
	ModelRenderer Shape4a;
	ModelRenderer Shape7;
	ModelRenderer Shape1a;
	ModelRenderer Shape4b;
	ModelRenderer Shape7a;
	ModelRenderer Shape4c;
	ModelRenderer Shape6b;
	ModelRenderer Shape6c;

	public ModelMonitor()
	{
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
		Shape1.setRotationPoint(-8F, 8F, -8F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		this.setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 64, 0);
		Shape2.addBox(0F, 0F, 0F, 1, 14, 16);
		Shape2.setRotationPoint(7F, 9F, -8F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		this.setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 64, 0);
		Shape3.addBox(0F, 0F, 0F, 1, 14, 16);
		Shape3.setRotationPoint(-8F, 9F, -8F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		this.setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 32);
		Shape4.addBox(0F, 0F, 0F, 14, 1, 1);
		Shape4.setRotationPoint(-7F, 9F, -8F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		this.setRotation(Shape4, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 0, 27);
		Shape12.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape12.setRotationPoint(-8.5F, 15F, -1F);
		Shape12.setTextureSize(128, 128);
		Shape12.mirror = true;
		this.setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 0, 27);
		Shape13.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape13.setRotationPoint(-8.5F, 16F, -1.4F);
		Shape13.setTextureSize(128, 128);
		Shape13.mirror = true;
		this.setRotation(Shape13, 0.7853982F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 34);
		Shape6.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape6.setRotationPoint(-7F, 10F, -8F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		this.setRotation(Shape6, 0F, 0F, 0F);
		Shape6a = new ModelRenderer(this, 0, 34);
		Shape6a.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape6a.setRotationPoint(6F, 10F, -8F);
		Shape6a.setTextureSize(128, 128);
		Shape6a.mirror = true;
		this.setRotation(Shape6a, 0F, 0F, 0F);
		Shape4a = new ModelRenderer(this, 0, 32);
		Shape4a.addBox(0F, 0F, 0F, 14, 1, 1);
		Shape4a.setRotationPoint(-7F, 22F, -8F);
		Shape4a.setTextureSize(128, 128);
		Shape4a.mirror = true;
		this.setRotation(Shape4a, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 4, 34);
		Shape7.addBox(0F, 0F, 0F, 14, 14, 1);
		Shape7.setRotationPoint(-7F, 9F, -7F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		this.setRotation(Shape7, 0F, 0F, 0F);
		Shape1a = new ModelRenderer(this, 0, 0);
		Shape1a.addBox(0F, 0F, 0F, 16, 1, 16);
		Shape1a.setRotationPoint(-8F, 23F, -8F);
		Shape1a.setTextureSize(128, 128);
		Shape1a.mirror = true;
		this.setRotation(Shape1a, 0F, 0F, 0F);
		Shape4b = new ModelRenderer(this, 0, 32);
		Shape4b.addBox(0F, 0F, 0F, 14, 1, 1);
		Shape4b.setRotationPoint(-7F, 22F, 7F);
		Shape4b.setTextureSize(128, 128);
		Shape4b.mirror = true;
		this.setRotation(Shape4b, 0F, 0F, 0F);
		Shape7a = new ModelRenderer(this, 4, 34);
		Shape7a.addBox(0F, 0F, 0F, 14, 14, 1);
		Shape7a.setRotationPoint(-7F, 9F, 6F);
		Shape7a.setTextureSize(128, 128);
		Shape7a.mirror = true;
		this.setRotation(Shape7a, 0F, 0F, 0F);
		Shape4c = new ModelRenderer(this, 0, 32);
		Shape4c.addBox(0F, 0F, 0F, 14, 1, 1);
		Shape4c.setRotationPoint(-7F, 9F, 7F);
		Shape4c.setTextureSize(128, 128);
		Shape4c.mirror = true;
		this.setRotation(Shape4c, 0F, 0F, 0F);
		Shape6b = new ModelRenderer(this, 0, 34);
		Shape6b.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape6b.setRotationPoint(6F, 10F, 7F);
		Shape6b.setTextureSize(128, 128);
		Shape6b.mirror = true;
		this.setRotation(Shape6b, 0F, 0F, 0F);
		Shape6c = new ModelRenderer(this, 0, 34);
		Shape6c.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape6c.setRotationPoint(-7F, 10F, 7F);
		Shape6c.setTextureSize(128, 128);
		Shape6c.mirror = true;
		this.setRotation(Shape6c, 0F, 0F, 0F);
	}

	@Override
	public void renderAll(List li, float phi)
	{
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape6.render(f5);
		Shape6a.render(f5);
		Shape4a.render(f5);
		Shape7.render(f5);
		Shape1a.render(f5);
		Shape4b.render(f5);
		Shape7a.render(f5);
		Shape4c.render(f5);
		Shape6b.render(f5);
		Shape6c.render(f5);

		GL11.glTranslated(0, 1, 0);
		GL11.glRotatef(phi, 1, 0, 0);
		GL11.glTranslated(0, -1, 0);
		Shape12.render(f5);
		Shape13.render(f5);
		GL11.glTranslated(0, 1, 0);
		GL11.glRotatef(-phi, 1, 0, 0);
		GL11.glTranslated(0, -1, 0);
	}
}
