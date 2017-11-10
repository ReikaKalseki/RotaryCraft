/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import Reika.DragonAPI.Instantiable.Rendering.LODModelPart;
import Reika.DragonAPI.Interfaces.TileModel;
import Reika.RotaryCraft.RotaryCraft;

public abstract class RotaryModelBase extends ModelBase implements TileModel {

	public static boolean allowRendering = true;

	protected final float f5 = 0.0625F;
	protected int pass;

	private boolean canBeCompiled = LODModelPart.allowCompiling && this.calcDefaultCompilability();

	private final ArrayList<LODModelPart> renderGroups = new ArrayList();

	public abstract void renderAll(TileEntity te, ArrayList conditions, float phi, float theta);

	private boolean calcDefaultCompilability() {
		String n = this.getClass().getName();
		return !n.contains("Animated") && !n.contains("Engine") && !n.contains("Turret");
	}

	public void setRenderPass() {
		pass = MinecraftForgeClient.getRenderPass();
	}

	public void setCompilable(boolean flag) {
		canBeCompiled = LODModelPart.allowCompiling && flag;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f6) {}

	@Override
	public final void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f6) {}

	public final void renderAll(TileEntity te, ArrayList conditions) {
		this.renderAll(te, conditions, 0);
	}

	public final void renderAll(TileEntity te, ArrayList conditions, float phi) {
		if (!allowRendering)
			return;
		if (canBeCompiled && phi == 0 && !GuiScreen.isCtrlKeyDown() && (conditions == null || conditions.isEmpty())) {
			this.renderList(te);
		}
		else {
			this.renderAll(te, conditions, phi, 0);
		}
	}

	protected final void setRotation(LODModelPart model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
		if (canBeCompiled) {
			if (renderGroups.isEmpty())
				this.setNewRenderingGroup();
			LODModelPart group = renderGroups.get(renderGroups.size()-1);
			group.setTextureSize((int)model.textureWidth, (int)model.textureHeight);
			group.mirror = false;
			model.mirror = false;
			group.addBox(model);
		}
	}

	protected final void renderList(TileEntity te) {
		if (!LODModelPart.allowCompiling) {
			RotaryCraft.logger.logError("Attempt to call compiled model for tile "+te+" without compiling enabled!");
		}
		for (LODModelPart part : renderGroups)
			part.render(te, f5);
	}

	protected final void renderSubList(TileEntity te, int idx) {
		renderGroups.get(idx).render(te, f5);
	}

	protected final int setNewRenderingGroup() {
		renderGroups.add(new LODModelPart(this, 0, 0));
		return renderGroups.size()-1;
	}
}
