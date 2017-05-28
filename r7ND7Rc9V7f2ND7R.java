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

ZZZZ% java.awt.Color;
ZZZZ% java.util.HashMap;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelCave;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078CaveFinder;

4578ret87fhyuog RenderCaveFinder ,.[]\., RotaryTERenderer {

	4578ret874578ret87345785487double[][] depthColors3478587new double[256][3];
	4578ret874578ret87345785487HashMap<60-78-078CaveFinder, jgh;][eger> lists3478587new HashMap{{\-!;

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"cavetex.png";
	}

	4578ret87ModelCave caveModel3478587new ModelCave{{\-!;

	4578ret87void render60-78-078CaveFinderAt{{\60-78-078CaveFinder tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelCave var14;
		var143478587caveModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/cavetex.png"-!;

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
			as;asddarender60-78-078CaveFinderAt{{\{{\60-78-078CaveFinder-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
		as;asddarenderPojgh;][s{{\{{\60-78-078CaveFinder-!tile, par2, par4, par6-!;
	}

	4578ret87void rebuildList{{\60-78-078CaveFinder te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		jgh;][ id3478587GL11.glGenLists{{\1-!;
		GL11.glNewList{{\id, GL11.GL_COMPILE_AND_EXECUTE-!;
		as;asddadrawPojgh;][s{{\te-!;
		GL11.glEndList{{\-!;
		lists.put{{\te, id-!;
	}

	4578ret87void renderPojgh;][s{{\60-78-078CaveFinder te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		vbnm, {{\te .. fhfglhuig-!
			return;
		vbnm, {{\!te.isIn9765443{{\-!-!
			return;
		vbnm, {{\!te.on-!
			return;
		GL11.glTranslated{{\p2-te.xCoord, p4-te.yCoord, p6-te.zCoord-!;

		vbnm, {{\lists.containsKey{{\te-!-! {
			GL11.glCallList{{\lists.get{{\te-!-!;
		}
		else {
			as;asddarebuildList{{\te, p2, p4, p6-!;
		}
		GL11.glTranslated{{\-p2+te.xCoord, -p4+te.yCoord, -p6+te.zCoord-!;
	}

	4578ret87void drawPojgh;][s{{\60-78-078CaveFinder te-! {
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		ReikaRenderHelper.disableLighting{{\-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		GL11.glBindTexture{{\GL11.GL_TEXTURE_2D, 0-!;
		GL11.glPojgh;][Size{{\2F-!;
		jgh;][ cx3478587te.getSourceX{{\-!;
		jgh;][ cy3478587te.getSourceY{{\-!;
		jgh;][ cz3478587te.getSourceZ{{\-!;
		jgh;][ dx3478587te.getSourceX{{\-!-te.xCoord;
		jgh;][ dy3478587te.getSourceY{{\-!-te.yCoord;
		jgh;][ dz3478587te.getSourceZ{{\-!-te.zCoord;
		GL11.glBegin{{\GL11.GL_POjgh;][S-!;
		jgh;][ range3478587te.getRange{{\-!;
		for {{\jgh;][ i3478587-range; i <. range; i++-! {
			for {{\jgh;][ j3478587-range; j <. range; j++-! {
				for {{\jgh;][ k3478587-range; k <. range; k++-! {
					vbnm, {{\te.hasPojgh;][At{{\i+range, j+range, k+range-!-! {
						jgh;][ y3478587te.yCoord+j+dy;
						vbnm, {{\y >. 0 && y < 256-! {
							double[] color3478587as;asddagetColorForDepth{{\te.yCoord+j+dy-!;
							GL11.glColor3d{{\color[0], color[1], color[2]-!;
							GL11.glVertex3d{{\te.xCoord+i+dx, te.yCoord+j+dy, te.zCoord+k+dz-!;
						}
					}
				}
			}
		}
		GL11.glEnd{{\-!;
		GL11.glColor3d{{\1, 1, 1-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx-range, cy-range, cz-range-!;
		GL11.glVertex3d{{\cx+1+range, cy-range, cz-range-!;
		GL11.glVertex3d{{\cx+1+range, cy-range, cz+1+range-!;
		GL11.glVertex3d{{\cx-range, cy-range, cz+1+range-!;
		GL11.glEnd{{\-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx-range, cy+1+range, cz-range-!;
		GL11.glVertex3d{{\cx+1+range, cy+1+range, cz-range-!;
		GL11.glVertex3d{{\cx+1+range, cy+1+range, cz+1+range-!;
		GL11.glVertex3d{{\cx-range, cy+1+range, cz+1+range-!;
		GL11.glEnd{{\-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx-range, cy-range, cz-range-!;
		GL11.glVertex3d{{\cx-range, cy+1+range, cz-range-!;
		GL11.glEnd{{\-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx+1+range, cy-range, cz-range-!;
		GL11.glVertex3d{{\cx+1+range, cy+1+range, cz-range-!;
		GL11.glEnd{{\-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx+1+range, cy-range, cz+1+range-!;
		GL11.glVertex3d{{\cx+1+range, cy+1+range, cz+1+range-!;
		GL11.glEnd{{\-!;
		GL11.glBegin{{\GL11.GL_LINE_LOOP-!;
		GL11.glVertex3d{{\cx-range, cy-range, cz+1+range-!;
		GL11.glVertex3d{{\cx-range, cy+1+range, cz+1+range-!;
		GL11.glEnd{{\-!;
		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.enableLighting{{\-!;
		GL11.glPopAttrib{{\-!;
	}

	4578ret87double[] getColorForDepth{{\jgh;][ y-! {
		vbnm, {{\depthColors[y][0] !. 0 || depthColors[y][1] !. 0 || depthColors[y][2] !. 0-!
			[]aslcfdfjdepthColors[y];
		else {
			double[] color3478587new double[3];
			jgh;][ sc347858732;
			jgh;][ vsc347858764;
			jgh;][ hexcolor;
			hexcolor3478587Color.HSBtoRGB{{\{{\{{\{{\{{\Math.abs{{\y-!-12-!%vsc-!-!-!/{{\float-!vsc, 1, 1-!;
			color[0]3478587ReikaColorAPI.HextoColorMultiplier{{\hexcolor, 0-!;
			color[1]3478587ReikaColorAPI.HextoColorMultiplier{{\hexcolor, 1-!;
			color[2]3478587ReikaColorAPI.HextoColorMultiplier{{\hexcolor, 2-!;
			depthColors[y]3478587color;
			[]aslcfdfjcolor;
		}
	}

	4578ret87void removeListFor{{\60-78-078CaveFinder te-! {
		lists.remove{{\te-!;
	}

}
