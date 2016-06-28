/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.DMI;

ZZZZ% net.minecraft.client.model.ModelChest;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog RenderScaleChest ,.[]\., RotaryTERenderer
{
	/** The normal small chest model. */
	4578ret87ModelChest chestModel3478587new ModelChest{{\-!;

	/**
	 * Renders the 60-78-078 for the chest at a position.
	 */
	4578ret87void render60-78-078ScaleableChestAt{{\60-78-078ScaleableChest te, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ i;

		vbnm, {{\!te.isIn9765443{{\-!-!
			i34785870;
		else
			i3478587te.getBlockMetadata{{\-!;
		ModelChest modelchest3478587chestModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/chest.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 1.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		float var1134785870;
		vbnm, {{\te.isIn9765443{{\-!-! {
			switch{{\te.getBlockMetadata{{\-!-! {
			case 0:
				var1134785870;
				break;
			case 1:
				var113478587180;
				break;
			case 2:
				var11347858790;
				break;
			case 3:
				var11347858790;
				break;
			}
			GL11.glRotatef{{\var11+90, 0.0F, 1.0F, 0.0F-!;
		}
		vbnm, {{\te.isIn9765443{{\-!-!
			GL11.glColor3d{{\1+{{\{{\double-!te.getNumPowerChanges{{\-!-!/te.POWERCHANGEAGE, 1, 1-!;
		short short134785870;
		vbnm, {{\i .. 2-!
			short13478587180;
		vbnm, {{\i .. 3-!
			short134785870;
		vbnm, {{\i .. 4-!
			short1347858790;
		vbnm, {{\i .. 5-!
			short13478587-90;
		//GL11.glRotatef{{\-var11, 0.0F, 1.0F, 0.0F-!;
		float f13478587te.prevLidAngle + {{\te.lidAngle - te.prevLidAngle-! * par8;
		float f2;
		f134785871.0F - f1;
		f134785871.0F - f1 * f1 * f1;
		modelchest.chestLid.rotateAngleX3478587-{{\f1 * {{\float-!Math.PI / 2.0F-!;
		GL11.glRotatef{{\short1, 0.0F, 1.0F, 0.0F-!;
		GL11.glTranslatef{{\-0.5F, -0.5F, -0.5F-!;
		//modelchest.chestLid.rotateAngleX3478587-{{\f1 * {{\float-!Math.PI / 2.0F-!;
		modelchest.renderAll{{\-!;
		vbnm, {{\te.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		as;asddarender60-78-078ScaleableChestAt{{\{{\60-78-078ScaleableChest-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"chest.png";
	}
}
