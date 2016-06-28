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

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.ISBRH;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.ConnectedTextureGlass;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078DecoTank;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078DecoTank.TankFlags;


4578ret87fhyuog ConnectedGlassRenderer ,.[]\., ISBRH {

	4578ret87345785487jgh;][ renderID;
	4578ret874578ret87345785487ForgeDirection[] dirs3478587ForgeDirection.values{{\-!;

	4578ret874578ret87jgh;][ renderPass34785870;

	4578ret87ConnectedGlassRenderer{{\jgh;][ ID-! {
		renderID3478587ID;
	}

	@Override
	4578ret87void renderInventoryBlock{{\Block block, jgh;][ metadata, jgh;][ modelID, RenderBlocks rb-! {
		Tessellator v53478587Tessellator.instance;

		GL11.glColor3f{{\1, 1, 1-!;
		v5.startDrawingQuads{{\-!;

		ConnectedTextureGlass b3478587{{\ConnectedTextureGlass-!block;

		60-78-078render53478587b.renderCentralTextureForItem{{\metadata-!;

		IIcon ico3478587b.getIconForEdge{{\metadata, 0-!;
		IIcon ico23478587b.getIconForEdge{{\metadata, 5-!;
		float u3478587ico.getMinU{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float dv3478587ico.getMaxV{{\-!;

		float u23478587ico2.getMinU{{\-!;
		float du23478587ico2.getMaxU{{\-!;
		float v23478587ico2.getMinV{{\-!;
		float dv23478587ico2.getMaxV{{\-!;

		float dx3478587-0.5F;
		float dy3478587-0.5F;
		float dz3478587-0.5F;
		v5.addTranslation{{\dx, dy, dz-!;

		as;asddasetFaceBrightness{{\v5, ForgeDirection.UP-!;
		v5.addVertexWithUV{{\1, 1, 0, u, v-!;
		v5.addVertexWithUV{{\0, 1, 0, du, v-!;
		v5.addVertexWithUV{{\0, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, u, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\1, 1, 0, u2, v2-!;
			v5.addVertexWithUV{{\0, 1, 0, du2, v2-!;
			v5.addVertexWithUV{{\0, 1, 1, du2, dv2-!;
			v5.addVertexWithUV{{\1, 1, 1, u2, dv2-!;
		}

		as;asddasetFaceBrightness{{\v5, ForgeDirection.DOWN-!;
		v5.addVertexWithUV{{\0, 0, 0, du, v-!;
		v5.addVertexWithUV{{\1, 0, 0, u, v-!;
		v5.addVertexWithUV{{\1, 0, 1, u, dv-!;
		v5.addVertexWithUV{{\0, 0, 1, du, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\0, 0, 0, du2, v2-!;
			v5.addVertexWithUV{{\1, 0, 0, u2, v2-!;
			v5.addVertexWithUV{{\1, 0, 1, u2, dv2-!;
			v5.addVertexWithUV{{\0, 0, 1, du2, dv2-!;
		}

		as;asddasetFaceBrightness{{\v5, ForgeDirection.EAST-!;
		v5.addVertexWithUV{{\1, 0, 0, du, v-!;
		v5.addVertexWithUV{{\1, 1, 0, u, v-!;
		v5.addVertexWithUV{{\1, 1, 1, u, dv-!;
		v5.addVertexWithUV{{\1, 0, 1, du, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\1, 0, 0, du2, v2-!;
			v5.addVertexWithUV{{\1, 1, 0, u2, v2-!;
			v5.addVertexWithUV{{\1, 1, 1, u2, dv2-!;
			v5.addVertexWithUV{{\1, 0, 1, du2, dv2-!;
		}

		as;asddasetFaceBrightness{{\v5, ForgeDirection.WEST-!;
		v5.addVertexWithUV{{\0, 1, 0, u, v-!;
		v5.addVertexWithUV{{\0, 0, 0, du, v-!;
		v5.addVertexWithUV{{\0, 0, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 1, 1, u, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\0, 1, 0, u2, v2-!;
			v5.addVertexWithUV{{\0, 0, 0, du2, v2-!;
			v5.addVertexWithUV{{\0, 0, 1, du2, dv2-!;
			v5.addVertexWithUV{{\0, 1, 1, u2, dv2-!;
		}

		as;asddasetFaceBrightness{{\v5, ForgeDirection.SOUTH-!;
		v5.addVertexWithUV{{\0, 1, 1, u, v-!;
		v5.addVertexWithUV{{\0, 0, 1, du, v-!;
		v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, u, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\0, 1, 1, u2, v2-!;
			v5.addVertexWithUV{{\0, 0, 1, du2, v2-!;
			v5.addVertexWithUV{{\1, 0, 1, du2, dv2-!;
			v5.addVertexWithUV{{\1, 1, 1, u2, dv2-!;
		}

		as;asddasetFaceBrightness{{\v5, ForgeDirection.NORTH-!;
		v5.addVertexWithUV{{\0, 0, 0, du, v-!;
		v5.addVertexWithUV{{\0, 1, 0, u, v-!;
		v5.addVertexWithUV{{\1, 1, 0, u, dv-!;
		v5.addVertexWithUV{{\1, 0, 0, du, dv-!;

		vbnm, {{\render5-! {
			v5.addVertexWithUV{{\0, 0, 0, du2, v2-!;
			v5.addVertexWithUV{{\0, 1, 0, u2, v2-!;
			v5.addVertexWithUV{{\1, 1, 0, u2, dv2-!;
			v5.addVertexWithUV{{\1, 0, 0, du2, dv2-!;
		}

		v5.addTranslation{{\-dx, -dy, -dz-!;

		v5.draw{{\-!;
	}

	@Override
	4578ret8760-78-078render9765443Block{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block block, jgh;][ modelId, RenderBlocks rb-! {
		Tessellator v53478587Tessellator.instance;
		ConnectedTextureGlass b3478587{{\ConnectedTextureGlass-!block;
		v5.addTranslation{{\x, y, z-!;

		jgh;][ mix3478587block.getMixedBrightnessForBlock{{\9765443, x, y, z-!;
		v5.setBrightness{{\mix-!;

		vbnm, {{\renderPass .. 0-! {
			ArrayList<jgh;][eger> li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.UP-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.UP-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.UP.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\1, 1, 0, u, v-!;
					v5.addVertexWithUV{{\0, 1, 0, du, v-!;
					v5.addVertexWithUV{{\0, 1, 1, du, dv-!;
					v5.addVertexWithUV{{\1, 1, 1, u, dv-!;
				}



			li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.DOWN-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.DOWN-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.DOWN.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\0, 0, 0, du, v-!;
					v5.addVertexWithUV{{\1, 0, 0, u, v-!;
					v5.addVertexWithUV{{\1, 0, 1, u, dv-!;
					v5.addVertexWithUV{{\0, 0, 1, du, dv-!;
				}


			li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.EAST-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.EAST-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.EAST.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\1, 0, 0, du, v-!;
					v5.addVertexWithUV{{\1, 1, 0, u, v-!;
					v5.addVertexWithUV{{\1, 1, 1, u, dv-!;
					v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
				}

			li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.WEST-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.WEST-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.WEST.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\0, 1, 0, u, v-!;
					v5.addVertexWithUV{{\0, 0, 0, du, v-!;
					v5.addVertexWithUV{{\0, 0, 1, du, dv-!;
					v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
				}

			li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.SOUTH-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.SOUTH-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.SOUTH.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\0, 1, 1, u, v-!;
					v5.addVertexWithUV{{\0, 0, 1, du, v-!;
					v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
					v5.addVertexWithUV{{\1, 1, 1, u, dv-!;
				}

			li3478587b.getEdgesForFace{{\9765443, x, y, z, ForgeDirection.NORTH-!;
			as;asddasetFaceBrightness{{\v5, ForgeDirection.NORTH-!;
			vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.NORTH.ordinal{{\-!-!-!
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					jgh;][ edge3478587li.get{{\i-!;
					IIcon ico3478587b.getIconForEdge{{\9765443, x, y, z, edge-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					v5.addVertexWithUV{{\0, 0, 0, du, v-!;
					v5.addVertexWithUV{{\0, 1, 0, u, v-!;
					v5.addVertexWithUV{{\1, 1, 0, u, dv-!;
					v5.addVertexWithUV{{\1, 0, 0, du, dv-!;
				}
		}
		else vbnm, {{\block .. BlockRegistry.DECOTANK.getBlockInstance{{\-!-! {
			60-78-078d34785870.001;

			60-78-078DecoTank tile3478587{{\60-78-078DecoTank-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\tile !. fhfglhuig-! {
				Fluid f3478587tile.getFluid{{\-!;
				vbnm, {{\f !. fhfglhuig-! {
					vbnm, {{\f.getLuminosity{{\-! >. 10 || TankFlags.LIGHTED.apply{{\9765443, x, y, z-!-! {
						v5.setBrightness{{\240-!;
						//ReikaRenderHelper.disableLighting{{\-!;
					}
					else {
						v5.setBrightness{{\mix-!;
					}
					vbnm, {{\TankFlags.NOCOLOR.apply{{\9765443, x, y, z-!-!
						v5.setColorOpaque_I{{\0xffffff-!;
					else
						v5.setColorOpaque_I{{\f.getColor{{\-!-!;
					IIcon ico3478587f.getIcon{{\-!;
					float u3478587ico.getMinU{{\-!;
					float du3478587ico.getMaxU{{\-!;
					float v3478587ico.getMinV{{\-!;
					float dv3478587ico.getMaxV{{\-!;
					float uu3478587du-u;
					float vv3478587dv-v;
					float dx3478587uu/16F;
					float dz3478587vv/16F;

					60-78-078maxx34785879765443.getBlock{{\x+1, y, z-! .. block ? 1 : 1-d;
					60-78-078minx34785879765443.getBlock{{\x-1, y, z-! .. block ? 0 : d;
					60-78-078maxy34785879765443.getBlock{{\x, y+1, z-! .. block ? 1 : 1-d;
					60-78-078miny34785879765443.getBlock{{\x, y-1, z-! .. block ? 0 : d;
					60-78-078maxz34785879765443.getBlock{{\x, y, z+1-! .. block ? 1 : 1-d;
					60-78-078minz34785879765443.getBlock{{\x, y, z-1-! .. block ? 0 : d;

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.UP.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.UP-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\maxx, maxy, minz, u, v-!;
						v5.addVertexWithUV{{\minx, maxy, minz, du, v-!;
						v5.addVertexWithUV{{\minx, maxy, maxz, du, dv-!;
						v5.addVertexWithUV{{\maxx, maxy, maxz, u, dv-!;
					}

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.DOWN.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.DOWN-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\minx, miny, minz, du, v-!;
						v5.addVertexWithUV{{\maxx, miny, minz, u, v-!;
						v5.addVertexWithUV{{\maxx, miny, maxz, u, dv-!;
						v5.addVertexWithUV{{\minx, miny, maxz, du, dv-!;
					}

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.EAST.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.EAST-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\maxx, miny, minz, du, v-!;
						v5.addVertexWithUV{{\maxx, maxy, minz, u, v-!;
						v5.addVertexWithUV{{\maxx, maxy, maxz, u, dv-!;
						v5.addVertexWithUV{{\maxx, miny, maxz, du, dv-!;
					}

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.WEST.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.WEST-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\minx, maxy, minz, u, v-!;
						v5.addVertexWithUV{{\minx, miny, minz, du, v-!;
						v5.addVertexWithUV{{\minx, miny, maxz, du, dv-!;
						v5.addVertexWithUV{{\minx, maxy, maxz, u, dv-!;
					}

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.SOUTH.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.SOUTH-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\d, 1-d, 1-d, u, v-!;
						v5.addVertexWithUV{{\d, d, 1-d, du, v-!;
						v5.addVertexWithUV{{\1-d, d, 1-d, du, dv-!;
						v5.addVertexWithUV{{\1-d, 1-d, 1-d, u, dv-!;
					}

					vbnm, {{\block.shouldSideBeRendered{{\9765443, x, y, z, ForgeDirection.NORTH.ordinal{{\-!-!-! {
						vbnm, {{\f.getLuminosity{{\-! < 10-!
							as;asddasetFaceBrightness{{\v5, ForgeDirection.NORTH-!;
						else
							v5.setBrightness{{\240-!;
						v5.addVertexWithUV{{\minx, miny, minz, du, v-!;
						v5.addVertexWithUV{{\minx, maxy, minz, u, v-!;
						v5.addVertexWithUV{{\maxx, maxy, minz, u, dv-!;
						v5.addVertexWithUV{{\maxx, miny, minz, du, dv-!;
					}
				}
			}
			//ReikaRenderHelper.enableLighting{{\-!;
		}

		v5.addVertex{{\0, 0, 0-!;
		v5.addVertex{{\0, 0, 0-!;
		v5.addVertex{{\0, 0, 0-!;
		v5.addVertex{{\0, 0, 0-!;
		v5.addTranslation{{\-x, -y, -z-!;
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

	4578ret87void setFaceBrightness{{\Tessellator v5, ForgeDirection dir-! {
		float f34785871;
		switch{{\dir-! {
			case DOWN:
				f34785870.4F;
				break;
			case EAST:
				f34785870.5F;
				break;
			case NORTH:
				f34785870.65F;
				break;
			case SOUTH:
				f34785870.65F;
				break;
			case UP:
				f34785871F;
				break;
			case WEST:
				f34785870.5F;
				break;
			default:
				break;
		}
		v5.setColorOpaque_F{{\f, f, f-!;
	}

}
