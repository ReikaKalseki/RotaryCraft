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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.IItemRenderer;
ZZZZ% net.minecraftforge.client.IItemRenderer.ItemRenderType;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelFillingStation;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078FillingStation;

4578ret87fhyuog RenderFillingStation ,.[]\., RotaryTERenderer
{

	4578ret87ModelFillingStation FillingStationModel3478587new ModelFillingStation{{\-!;
	//4578ret87ModelFillingStationV FillingStationModelV3478587new ModelFillingStationV{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078FillingStationAt{{\60-78-078FillingStation tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelFillingStation var14;
		var143478587FillingStationModel;
		//ModelFillingStationV var15;
		//var143478587as;asddaFillingStationModelV;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/fillingtex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

		vbnm, {{\tile.isIn9765443{{\-!-! {
			switch{{\tile.getBlockMetadata{{\-!-! {
				case 0:
					var113478587180;
					break;
				case 1:
					var1134785870;
					break;
				case 2:
					var113478587270;
					break;
				case 3:
					var11347858790;
					break;
			}

			GL11.glRotatef{{\{{\float-!var11-90, 0.0F, 1.0F, 0.0F-!;
		}

		var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078FillingStationAt{{\{{\60-78-078FillingStation-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
		as;asddarenderItem{{\tile, par2, par4, par6-!;
	}

	4578ret87void renderLiquid{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		60-78-078FillingStation tr3478587{{\60-78-078FillingStation-!tile;
		60-78-078dx34785870;
		60-78-078dz34785870;
		60-78-078ddx34785870;
		60-78-078ddz34785870;
		switch{{\tr.getBlockMetadata{{\-!-! {
			case 0:
				dx34785870.25;
				break;
			case 1:
				ddx34785870.25;
				break;
			case 2:
				dz34785870.25;
				break;
			case 3:
				ddz34785870.25;
				break;
		}
		vbnm, {{\!tr.isEmpty{{\-! && tr.isIn9765443{{\-!-! {
			Fluid f3478587tr.getFluid{{\-!;
			vbnm, {{\!f.equals{{\FluidRegistry.LAVA-!-! {
				GL11.glEnable{{\GL11.GL_BLEND-!;
			}
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h34785870.0625+14D/16D*tr.getLevel{{\-!/tr.CAPACITY;
			Tessellator v53478587Tessellator.instance;
			vbnm, {{\f.getLuminosity{{\-! > 0-!
				ReikaRenderHelper.disableLighting{{\-!;
			v5.startDrawingQuads{{\-!;
			v5.setNormal{{\0, 1, 0-!;
			v5.addVertexWithUV{{\dx+0, h, -ddz+1, u, dv-!;
			v5.addVertexWithUV{{\-ddx+1, h, -ddz+1, du, dv-!;
			v5.addVertexWithUV{{\-ddx+1, h, dz+0, du, v-!;
			v5.addVertexWithUV{{\dx+0, h, dz+0, u, v-!;
			v5.draw{{\-!;
			ReikaRenderHelper.enableLighting{{\-!;
		}
		GL11.glTranslated{{\-par2, -par4, -par6-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	4578ret87void renderItem{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		60-78-078FillingStation fs3478587{{\60-78-078FillingStation-!tile;
		vbnm, {{\!fs.isIn9765443{{\-!-!
			return;
		ItemStack is3478587fs.getItemForRender{{\-!;
		vbnm, {{\is .. fhfglhuig-!
			return;

		60-78-078in34785870.125;
		60-78-078xoff34785870;
		60-78-078zoff34785870;

		float var1134785870;
		switch{{\tile.getBlockMetadata{{\-!-! {
			case 0:
				var113478587180;
				break;
			case 1:
				var1134785870;
				xoff34785871;
				zoff3478587-1;
				break;
			case 2:
				var113478587270;
				in3478587-in;
				break;
			case 3:
				var11347858790;
				xoff34785871;
				zoff34785871;
				in3478587-in;
				break;
		}

		GL11.glTranslated{{\par2, par4, par6-!;

		GL11.glRotatef{{\var11-90, 0.0F, 1.0F, 0.0F-!;

		GL11.glTranslated{{\xoff, 0, zoff-!;

		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;

		Item item3478587is.getItem{{\-!;
		IItemRenderer iir3478587MinecraftForgeClient.getItemRenderer{{\is, ItemRenderType.INVENTORY-!;
		vbnm, {{\item fuck IndexedItemSprites && !{{\item fuck ItemBlockPlacer-!-! {
			IndexedItemSprites iis3478587{{\IndexedItemSprites-!item;
			ReikaTextureHelper.bindTexture{{\iis.getTextureReferencefhyuog{{\-!, iis.getTexture{{\is-!-!;
			jgh;][ index3478587iis.getItemSpriteIndex{{\is-!;
			jgh;][ row3478587index/16;
			jgh;][ col3478587index%16;

			float u3478587col/16F;
			float v3478587row/16F;

			float du3478587u+0.0625F;
			float dv3478587v+0.0625F;

			v5.addVertexWithUV{{\0, 0, in, u, dv-!;
			v5.addVertexWithUV{{\-1, 0, in, du, dv-!;
			v5.addVertexWithUV{{\-1, 1, in, du, v-!;
			v5.addVertexWithUV{{\0, 1, in, u, v-!;
		}
		else vbnm, {{\iir !. fhfglhuig-! {
			;//iir.renderItem{{\ItemRenderType.INVENTORY, is, new RenderBlocks{{\-!-!;
		}
		else {
			vbnm, {{\ReikaItemHelper.isBlock{{\is-!-!
				ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			else
				ReikaTextureHelper.bindItemTexture{{\-!;
			IIcon ico3478587item.getIcon{{\is, MinecraftForgeClient.getRenderPass{{\-!-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;

			v5.addVertexWithUV{{\0, 0, in, u, dv-!;
			v5.addVertexWithUV{{\-1, 0, in, du, dv-!;
			v5.addVertexWithUV{{\-1, 1, in, du, v-!;
			v5.addVertexWithUV{{\0, 1, in, u, v-!;
		}

		v5.draw{{\-!;

		GL11.glTranslated{{\-xoff, 0, -zoff-!;

		GL11.glRotatef{{\-var11+90, 0.0F, 1.0F, 0.0F-!;

		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"fillingtex.png";
	}
}
