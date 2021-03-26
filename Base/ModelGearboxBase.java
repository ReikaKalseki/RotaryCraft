package Reika.RotaryCraft.Base;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Instantiable.Rendering.LODModelPart;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class ModelGearboxBase extends RotaryModelBase {

	private final LODModelPart Shape1;
	private final LODModelPart Shape2;
	private final LODModelPart Shape3;
	private final LODModelPart Shape4;
	private final LODModelPart Shape5;
	private final LODModelPart Shape6;
	private final LODModelPart Shape7;
	private final LODModelPart Shape8;
	private final LODModelPart Shape9;
	private final LODModelPart Shape10;
	private final LODModelPart Shape11;

	private final Collection<LODModelPart> base = new ArrayList();
	private final Collection<LODModelPart> supportColumns = new ArrayList();

	protected ModelGearboxBase() {
		textureWidth = 128;
		textureHeight = 32;

		Shape1 = new LODModelPart(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
		Shape1.setRotationPoint(-8F, 23F, -8F);
		Shape1.setTextureSize(128, 32);
		Shape1.mirror = true;
		this.setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new LODModelPart(this, 64, 0);
		Shape2.addBox(0F, 0F, 0F, 1, 12, 16);
		Shape2.setRotationPoint(7F, 11F, -8F);
		Shape2.setTextureSize(128, 32);
		Shape2.mirror = true;
		this.setRotation(Shape2, 0F, 0F, 0F);
		Shape2.mirror = false;
		Shape3 = new LODModelPart(this, 64, 0);
		Shape3.addBox(0F, 0F, 0F, 1, 12, 16);
		Shape3.setRotationPoint(-8F, 11F, -8F);
		Shape3.setTextureSize(128, 32);
		Shape3.mirror = true;
		this.setRotation(Shape3, 0F, 0F, 0F);
		Shape3.mirror = false;
		Shape4 = new LODModelPart(this, 0, 17);
		Shape4.addBox(0F, 0F, 0F, 14, 4, 1);
		Shape4.setRotationPoint(-7F, 19F, 7F);
		Shape4.setTextureSize(128, 32);
		Shape4.mirror = true;
		this.setRotation(Shape4, 0F, 0F, 0F);
		Shape4.mirror = false;
		Shape5 = new LODModelPart(this, 0, 17);
		Shape5.addBox(0F, 0F, 0F, 14, 4, 1);
		Shape5.setRotationPoint(-7F, 19F, -8F);
		Shape5.setTextureSize(128, 32);
		Shape5.mirror = true;
		this.setRotation(Shape5, 0F, 0F, 0F);
		Shape5.mirror = false;
		Shape6 = new LODModelPart(this, 30, 17);
		Shape6.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape6.setRotationPoint(6F, 16F, 7F);
		Shape6.setTextureSize(128, 32);
		Shape6.mirror = true;
		this.setRotation(Shape6, 0F, 0F, 0F);
		Shape6.mirror = false;
		Shape7 = new LODModelPart(this, 30, 17);
		Shape7.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape7.setRotationPoint(6F, 16F, -8F);
		Shape7.setTextureSize(128, 32);
		Shape7.mirror = true;
		this.setRotation(Shape7, 0F, 0F, 0F);
		Shape7.mirror = false;
		Shape8 = new LODModelPart(this, 30, 17);
		Shape8.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape8.setRotationPoint(-7F, 16F, 7F);
		Shape8.setTextureSize(128, 32);
		Shape8.mirror = true;
		this.setRotation(Shape8, 0F, 0F, 0F);
		Shape8.mirror = false;
		Shape9 = new LODModelPart(this, 30, 17);
		Shape9.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape9.setRotationPoint(-7F, 16F, -8F);
		Shape9.setTextureSize(128, 32);
		Shape9.mirror = true;
		this.setRotation(Shape9, 0F, 0F, 0F);
		Shape9.mirror = false;
		Shape10 = new LODModelPart(this, 42, 17);
		Shape10.addBox(0F, 0F, 0F, 1, 3, 10);
		Shape10.setRotationPoint(7F, 8F, -5F);
		Shape10.setTextureSize(128, 32);
		Shape10.mirror = true;
		this.setRotation(Shape10, 0F, 0F, 0F);
		Shape10.mirror = false;
		Shape11 = new LODModelPart(this, 42, 17);
		Shape11.addBox(0F, 0F, 0F, 1, 3, 10);
		Shape11.setRotationPoint(-8F, 8F, -5F);
		Shape11.setTextureSize(128, 32);
		Shape11.mirror = true;
		this.setRotation(Shape11, 0F, 0F, 0F);
		Shape11.mirror = false;

		base.add(Shape1);
		base.add(Shape2);
		base.add(Shape3);
		base.add(Shape4);
		base.add(Shape5);
		base.add(Shape6);
		base.add(Shape7);
		base.add(Shape8);
		base.add(Shape9);
		base.add(Shape10);
		base.add(Shape11);
	}

	protected final void addSupport(LODModelPart p) {
		supportColumns.add(p);
	}

	@Override
	public void renderAll(TileEntity te, ArrayList li, float phi, float theta) {
		Shape1.render(te, f5);
		Shape2.render(te, f5);
		Shape3.render(te, f5);
		Shape4.render(te, f5);
		Shape5.render(te, f5);
		Shape6.render(te, f5);
		Shape7.render(te, f5);
		Shape8.render(te, f5);
		Shape9.render(te, f5);
		Shape10.render(te, f5);
		Shape11.render(te, f5);
	}

	protected final void renderSupports(TileEntity te, ArrayList li) {
		if (li != null && !li.isEmpty()) {
			GearboxTypes type = (GearboxTypes)li.get(0);
			//GL11.glColor4f(0.35F, 0.82F, 1F, 1F);
			RotaryRenderList.getRenderForMachine(MachineRegistry.GEARBOX).bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/Transmission/Gear/"+type.getBaseGearboxTexture());
		}
		for (LODModelPart p : supportColumns)
			p.render(te, f5);
		GL11.glColor4f(1, 1, 1, 1);
	}

}
