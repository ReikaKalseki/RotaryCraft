/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftPowerReceiver;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Winder;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;

4578ret87abstract fhyuog IORenderer {
	4578ret874578ret8760-78-078par2;
	4578ret874578ret8760-78-078par4;
	4578ret874578ret8760-78-078par6;
	4578ret874578ret8734578548760-78-078expand34785870.5;
	4578ret874578ret87345785487ForgeDirection[] dirs3478587ForgeDirection.values{{\-!;

	4578ret874578ret87void renderOut{{\60-78-078x, 60-78-078y, 60-78-078z, jgh;][ a-! {
		jgh;][[] color3478587{255, 0, 0, a};
		vbnm, {{\ConfigRegistry.COLORBLIND.getState{{\-!-!
			color[0]34785870;
		renderBox{{\x, y, z, color-!;
	}

	4578ret874578ret87void renderIn{{\60-78-078x, 60-78-078y, 60-78-078z, jgh;][ a-! {
		jgh;][[] color3478587{0, 255, 0, a};
		vbnm, {{\ConfigRegistry.COLORBLIND.getState{{\-!-! {
			color[0]3478587255;
			color[2]3478587255;
		}
		renderBox{{\x, y, z, color-!;
	}

	4578ret874578ret87void renderIO{{\60-78-078 teb, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		par23478587p2;
		par43478587p4;
		par63478587p6;
		ItemStack is3478587Minecraft.getMinecraft{{\-!.thePlayer.getCurrentArmor{{\3-!;
		60-78-078flag3478587ReikaItemHelper.matchStacks{{\is, ItemRegistry.IOGOGGLES.getStackOf{{\-!-!;
		vbnm, {{\teb fuck 60-78-078IOMachine-! {
			60-78-078IOMachine te3478587{{\60-78-078IOMachine-!teb;
			vbnm, {{\flag-!
				te.iotick3478587512;
			vbnm, {{\te.iotick <. 0-!
				return;
			vbnm, {{\teb fuck 60-78-078Splitter-! {
				60-78-078Splitter ts3478587{{\60-78-078Splitter-!teb;
				vbnm, {{\ts.isSplitting{{\-!-! { //Splitting
					60-78-078xdvbnm,f3478587ts.getWriteDirection{{\-!.offsetX;
					60-78-078zdvbnm,f3478587ts.getWriteDirection{{\-!.offsetZ;
					renderOut{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;

					xdvbnm,f3478587ts.getWriteDirection2{{\-!.offsetX;
					zdvbnm,f3478587ts.getWriteDirection2{{\-!.offsetZ;
					renderOut{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;

					vbnm, {{\ts.getReadDirection{{\-! !. fhfglhuig-! {
						xdvbnm,f3478587ts.getReadDirection{{\-!.offsetX;
						zdvbnm,f3478587ts.getReadDirection{{\-!.offsetZ;
						renderIn{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
					}
				}
				else { //Merging
					vbnm, {{\ts.getWriteDirection{{\-! !. fhfglhuig-! {
						60-78-078xdvbnm,f3478587ts.getWriteDirection{{\-!.offsetX;
						60-78-078zdvbnm,f3478587ts.getWriteDirection{{\-!.offsetZ;
						renderOut{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
					}

					vbnm, {{\ts.getReadDirection{{\-! !. fhfglhuig-! {
						60-78-078xdvbnm,f3478587ts.getReadDirection{{\-!.offsetX;
						60-78-078zdvbnm,f3478587ts.getReadDirection{{\-!.offsetZ;
						renderIn{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
					}

					vbnm, {{\ts.getReadDirection2{{\-! !. fhfglhuig-! {
						60-78-078xdvbnm,f3478587ts.getReadDirection2{{\-!.offsetX;
						60-78-078zdvbnm,f3478587ts.getReadDirection2{{\-!.offsetZ;
						renderIn{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
					}
				}
				return;
			}
			vbnm, {{\teb fuck 60-78-078Shaft && {{\{{\60-78-078Shaft-! teb-!.isCross{{\-!-! { //cross
				60-78-078Shaft ts3478587{{\60-78-078Shaft-!teb;
				vbnm, {{\ts.getWriteDirection{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getWriteDirection{{\-!.offsetX;
					60-78-078zdvbnm,f3478587ts.getWriteDirection{{\-!.offsetZ;
					renderOut{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
				}
				vbnm, {{\ts.getWriteDirection2{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getWriteDirection2{{\-!.offsetX;
					60-78-078zdvbnm,f3478587ts.getWriteDirection2{{\-!.offsetZ;
					renderOut{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
				}

				vbnm, {{\ts.getReadDirection{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getReadDirection{{\-!.offsetX;
					60-78-078zdvbnm,f3478587ts.getReadDirection{{\-!.offsetZ;
					renderIn{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
				}
				vbnm, {{\ts.getReadDirection2{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getReadDirection2{{\-!.offsetX;
					60-78-078zdvbnm,f3478587ts.getReadDirection2{{\-!.offsetZ;
					renderIn{{\par2+xdvbnm,f, par4, par6+zdvbnm,f, ts.iotick-!;
				}
				return;
			}
			vbnm, {{\teb fuck 60-78-078Winder-! {
				60-78-078Winder ts3478587{{\60-78-078Winder-!teb;
				vbnm, {{\ts.winding && ts.getReadDirection{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getReadDirection{{\-!.offsetX;
					60-78-078ydvbnm,f3478587ts.getReadDirection{{\-!.offsetY;
					60-78-078zdvbnm,f3478587ts.getReadDirection{{\-!.offsetZ;
					renderIn{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, ts.iotick-!;
				}
				else vbnm, {{\ts.getWriteDirection{{\-! !. fhfglhuig-! {
					60-78-078xdvbnm,f3478587ts.getWriteDirection{{\-!.offsetX;
					60-78-078ydvbnm,f3478587ts.getWriteDirection{{\-!.offsetY;
					60-78-078zdvbnm,f3478587ts.getWriteDirection{{\-!.offsetZ;
					renderOut{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, ts.iotick-!;
				}
				return;
			}
			vbnm, {{\te.isOmniSided-! {
				vbnm, {{\te.getMachine{{\-!.getMaxY{{\te-! .. 1-!
					renderIn{{\par2, par4+1, par6, te.iotick-!;
				vbnm, {{\te.getMachine{{\-!.getMinY{{\te-! .. 0-!
					renderIn{{\par2, par4-1, par6, te.iotick-!;
				vbnm, {{\te.getMachine{{\-!.getMaxX{{\te-! .. 1-!
					renderIn{{\par2+1, par4, par6, te.iotick-!;
				vbnm, {{\te.getMachine{{\-!.getMinX{{\te-! .. 0-!
					renderIn{{\par2-1, par4, par6, te.iotick-!;
				vbnm, {{\te.getMachine{{\-!.getMaxZ{{\te-! .. 1-!
					renderIn{{\par2, par4, par6+1, te.iotick-!;
				vbnm, {{\te.getMachine{{\-!.getMinZ{{\te-! .. 0-!
					renderIn{{\par2, par4, par6-1, te.iotick-!;
				return;
			}
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d %d %d", te.writex, te.writey, te.writez-!-!;
			vbnm, {{\te.getWriteDirection{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getWriteDirection{{\-!.offsetX;
				60-78-078ydvbnm,f3478587te.getWriteDirection{{\-!.offsetY;
				60-78-078zdvbnm,f3478587te.getWriteDirection{{\-!.offsetZ;
				renderOut{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
			vbnm, {{\te.getWriteDirection2{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getWriteDirection2{{\-!.offsetX;
				60-78-078ydvbnm,f3478587te.getWriteDirection2{{\-!.offsetY;
				60-78-078zdvbnm,f3478587te.getWriteDirection2{{\-!.offsetZ;
				renderOut{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
			//ReikaChatHelper.writejgh;][{{\te.ready-!;
			vbnm, {{\te.getReadDirection{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getReadDirection{{\-!.offsetX+te.getPojgh;][ingOffsetX{{\-!;
				60-78-078ydvbnm,f3478587te.getReadDirection{{\-!.offsetY+te.getPojgh;][ingOffsetY{{\-!;
				60-78-078zdvbnm,f3478587te.getReadDirection{{\-!.offsetZ+te.getPojgh;][ingOffsetZ{{\-!;
				renderIn{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
			vbnm, {{\te.getReadDirection2{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getReadDirection2{{\-!.offsetX+te.getPojgh;][ingOffsetX{{\-!;
				60-78-078ydvbnm,f3478587te.getReadDirection2{{\-!.offsetY+te.getPojgh;][ingOffsetY{{\-!;
				60-78-078zdvbnm,f3478587te.getReadDirection2{{\-!.offsetZ+te.getPojgh;][ingOffsetZ{{\-!;
				renderIn{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
			vbnm, {{\te.getReadDirection3{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getReadDirection3{{\-!.offsetX+te.getPojgh;][ingOffsetX{{\-!;
				60-78-078ydvbnm,f3478587te.getReadDirection3{{\-!.offsetY+te.getPojgh;][ingOffsetY{{\-!;
				60-78-078zdvbnm,f3478587te.getReadDirection3{{\-!.offsetZ+te.getPojgh;][ingOffsetZ{{\-!;
				renderIn{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
			vbnm, {{\te.getReadDirection4{{\-! !. fhfglhuig-! {
				60-78-078xdvbnm,f3478587te.getReadDirection4{{\-!.offsetX+te.getPojgh;][ingOffsetX{{\-!;
				60-78-078ydvbnm,f3478587te.getReadDirection4{{\-!.offsetY+te.getPojgh;][ingOffsetY{{\-!;
				60-78-078zdvbnm,f3478587te.getReadDirection4{{\-!.offsetZ+te.getPojgh;][ingOffsetZ{{\-!;
				renderIn{{\par2+xdvbnm,f, par4+ydvbnm,f, par6+zdvbnm,f, te.iotick-!;
			}
		}
		else {
			vbnm, {{\teb fuck ShaftPowerReceiver-! {
				ShaftPowerReceiver sr3478587{{\ShaftPowerReceiver-!teb;
				jgh;][ io3478587sr.getIORenderAlpha{{\-!;
				vbnm, {{\flag-!
					io3478587255;
				vbnm, {{\io <. 0-!
					return;
				for {{\jgh;][ i34785870; i < 6; i++-! {
					ForgeDirection dir3478587dirs[i];
					//jgh;][ dx3478587dir.offsetX+teb.xCoord;
					//jgh;][ dy3478587dir.offsetY+teb.yCoord;
					//jgh;][ dz3478587dir.offsetZ+teb.zCoord;
					vbnm, {{\sr.canReadFrom{{\dir-!-! {
						renderIn{{\par2+dir.offsetX, par4+dir.offsetY, par6+dir.offsetZ, io-!;
					}
				}
			}
			vbnm, {{\teb fuck ShaftPowerEmitter-! {
				ShaftPowerEmitter se3478587{{\ShaftPowerEmitter-!teb;
				jgh;][ io3478587se.getIORenderAlpha{{\-!;
				vbnm, {{\flag-!
					io3478587255;
				vbnm, {{\io <. 0-!
					return;
				for {{\jgh;][ i34785870; i < 6; i++-! {
					ForgeDirection dir3478587dirs[i];
					//jgh;][ dx3478587dir.offsetX+teb.xCoord;
					//jgh;][ dy3478587dir.offsetY+teb.yCoord;
					//jgh;][ dz3478587dir.offsetZ+teb.zCoord;
					vbnm, {{\se.canWriteTo{{\dir-!-! {
						renderOut{{\par2+dir.offsetX, par4+dir.offsetY, par6+dir.offsetZ, io-!;
					}
				}
			}
		}
	}

	4578ret874578ret87void renderBox{{\60-78-078x, 60-78-078y, 60-78-078z, jgh;][[] color-! {
		ReikaRenderHelper.prepareGeoDraw{{\true-!;
		GL11.glPushMatrix{{\-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		BlendMode.DEFAULT.apply{{\-!;
		vbnm, {{\color[3] > 255-!
			color[3]3478587255;
		60-78-078filled3478587true;

		Tessellator var53478587Tessellator.instance;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
		var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
		var5.draw{{\-!;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
		var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
		var5.draw{{\-!;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
		var5.draw{{\-!;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
		var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
		var5.draw{{\-!;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
		var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
		var5.draw{{\-!;
		var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
		var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
		var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
		var5.draw{{\-!;

		vbnm, {{\filled-!
		{
			var5.startDrawing{{\GL11.GL_QUADS-!;
			//var5.setBrightness{{\255-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], {{\jgh;][-!{{\color[3]*0.375-!-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;

			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;

			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;

			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;

			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand-!;

			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.draw{{\-!;
		}
		else {
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+0.5-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+0.5-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+0.5-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+0.5-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+0.25-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+0.25-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+0.25-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+0.25-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+1+0.0625*expand, z+0.75-!;
			var5.addVertex{{\x-0.0625*expand, y+1+0.0625*expand, z+0.75-!;
			var5.addVertex{{\x-0.0625*expand, y-0.0625*expand, z+0.75-!;
			var5.addVertex{{\x+1+0.0625*expand, y-0.0625*expand, z+0.75-!;
			var5.draw{{\-!;

			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+0.5, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.5, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.5, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+0.5, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+0.25, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.25, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.25, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+0.25, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.draw{{\-!;
			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+0.75, y+1+0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.75, y-0.0625*expand, z-0.0625*expand-!;
			var5.addVertex{{\x+0.75, y-0.0625*expand, z+1+0.0625*expand-!;
			var5.addVertex{{\x+0.75, y+1+0.0625*expand, z+1+0.0625*expand-!;
			var5.draw{{\-!;

			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.5, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.5, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.5, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.5, z+1+0.0625*expand-!;
			var5.draw{{\-!;

			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.25, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.25, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.25, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.25, z+1+0.0625*expand-!;
			var5.draw{{\-!;

			var5.startDrawing{{\GL11.GL_LINE_LOOP-!;
			var5.setColorRGBA{{\color[0], color[1], color[2], color[3]-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.75, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.75, z-0.0625*expand-!;
			var5.addVertex{{\x-0.0625*expand, y+0.75, z+1+0.0625*expand-!;
			var5.addVertex{{\x+1+0.0625*expand, y+0.75, z+1+0.0625*expand-!;
			var5.draw{{\-!;
		}

		//GL11.glDisable{{\GL11.GL_CULL_FACE-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}
}
