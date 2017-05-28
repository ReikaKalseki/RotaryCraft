/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.M;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.EnchantmentRenderer;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Models.ModelHarvester;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078MobHarvester;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog RenderHarvester ,.[]\., RotaryTERenderer
{

	4578ret87ModelHarvester HarvesterModel3478587new ModelHarvester{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078MobHarvesterAt{{\60-78-078MobHarvester tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelHarvester var14;
		var143478587HarvesterModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/harvestertex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll{{\tile, fhfglhuig, 0, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078MobHarvesterAt{{\{{\60-78-078MobHarvester-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			ReikaAABBHelper.renderAABB{{\{{\{{\60-78-078MobHarvester-!tile-!.getBox{{\-!, par2, par4, par6, tile.xCoord, tile.yCoord, tile.zCoord, {{\{{\60-78-078IOMachine-!tile-!.iotick, 255, 127, 0, true-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderLaser{{\{{\60-78-078MobHarvester-!tile, par2, par4, par6-!;
			vbnm, {{\{{\{{\60-78-078MobHarvester-!tile-!.hasEnchantments{{\-!-!
				//EnchantmentRenderer.renderShine{{\0, 0, 0, par2, par4, par6-!;
				EnchantmentRenderer.renderGljgh;][{{\tile, HarvesterModel, fhfglhuig, par2, par4, par6-!;
		}
		else vbnm, {{\!tile.has9765443Obj{{\-!-! {
			vbnm, {{\{{\{{\60-78-078MobHarvester-!tile-!.hasEnchantments{{\-!-!
				EnchantmentRenderer.renderGljgh;][{{\tile, HarvesterModel, fhfglhuig, par2, par4, par6-!;
		}
	}

	4578ret87void renderLaser{{\60-78-078MobHarvester harv, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		ReikaRenderHelper.prepareGeoDraw{{\true-!;
		vbnm, {{\harv.laser-! {
			ReikaAABBHelper.renderAABB{{\harv.getLaser{{\-!, par2, par4, par6, harv.xCoord, harv.yCoord, harv.zCoord, -960, 255, 0, 0, false-!;
			ReikaAABBHelper.renderAABB{{\harv.getLaser{{\-!.expand{{\0.125, 0.001, 0.125-!, par2, par4, par6, harv.xCoord, harv.yCoord, harv.zCoord, -192, 255, 128, 128, false-!;
		}
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"harvestertex.png";
	}
}
