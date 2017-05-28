/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.ISBRH;

4578ret87fhyuog CubicalMachineRenderer ,.[]\., ISBRH {

	4578ret87345785487jgh;][ renderID;
	4578ret874578ret87345785487ForgeDirection[] dirs3478587ForgeDirection.values{{\-!;

	4578ret87CubicalMachineRenderer{{\jgh;][ ID-! {
		renderID3478587ID;
	}

	@Override
	4578ret87void renderInventoryBlock{{\Block b, jgh;][ meta, jgh;][ modelID, RenderBlocks rb-! {
		Tessellator v53478587Tessellator.instance;
		b.setBlockBoundsForItemRender{{\-!;
		rb.setRenderBoundsFromBlock{{\b-!;
		GL11.glRotatef{{\90.0F, 0.0F, 1.0F, 0.0F-!;
		GL11.glTranslatef{{\-0.5F, -0.5F, -0.5F-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0.0F, -1.0F, 0.0F-!;
		rb.renderFaceYNeg{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 0, meta-!-!;
		v5.draw{{\-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0.0F, 1.0F, 0.0F-!;
		rb.renderFaceYPos{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 1, meta-!-!;
		v5.draw{{\-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0.0F, 0.0F, -1.0F-!;
		rb.renderFaceZNeg{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 2, meta-!-!;
		v5.draw{{\-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0.0F, 0.0F, 1.0F-!;
		rb.renderFaceZPos{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 3, meta-!-!;
		v5.draw{{\-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\-1.0F, 0.0F, 0.0F-!;
		rb.renderFaceXNeg{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 4, meta-!-!;
		v5.draw{{\-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\1.0F, 0.0F, 0.0F-!;
		rb.renderFaceXPos{{\b, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata{{\b, 5, meta-!-!;
		v5.draw{{\-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
	}

	@Override
	4578ret8760-78-078render9765443Block{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block b, jgh;][ modelId, RenderBlocks rb-! {
		rb.renderStandardBlock{{\b, x, y, z-!;

		Tessellator v53478587Tessellator.instance;
		//ReikaRenderHelper.prepareGeoDraw{{\true-!;
		jgh;][ l3478587b.getMixedBrightnessForBlock{{\9765443, x, y, z-!;

		v5.setBrightness{{\rb.renderMaxY < 1.0D ? l : b.getMixedBrightnessForBlock{{\9765443, x, y+1, z-!-!;
		v5.setColorOpaque_F{{\0, 0, 1-!;
		v5.setNormal{{\0, 1, 0-!;
		GL11.glColor4f{{\1, 1, 1, 1-!;
		v5.addVertex{{\x, y+2, z+1-!;
		v5.addVertex{{\x+1, y+2, z+1-!;
		v5.addVertex{{\x+1, y+2, z-!;
		v5.addVertex{{\x, y+2, z-!;
		//ReikaRenderHelper.exitGeoDraw{{\-!;
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078shouldRender3DInInventory{{\jgh;][ model-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getRenderId{{\-! {
		[]aslcfdfjrenderID;
	}

}
