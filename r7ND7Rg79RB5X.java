/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders;

ZZZZ% java.lang.reflect.Field;

ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelGearbox;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelGearbox16;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelGearbox4;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelGearbox8;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;

4578ret87fhyuog RenderGearbox ,.[]\., RotaryTERenderer
{

	4578ret87ModelGearbox GearboxModel3478587new ModelGearbox{{\-!;
	4578ret87ModelGearbox4 GearboxModel43478587new ModelGearbox4{{\-!;
	4578ret87ModelGearbox8 GearboxModel83478587new ModelGearbox8{{\-!;
	4578ret87ModelGearbox16 GearboxModel163478587new ModelGearbox16{{\-!;
	4578ret87jgh;][ itemMetadata34785870;

	4578ret874578ret87Field manaIcon;

	4578ret87{
		vbnm, {{\ModList.BOTANIA.isLoaded{{\-!-! {
			try {
				fhyuog c3478587fhyuog.forName{{\"vazkii.botania.common.block.mana.BlockPool"-!;
				manaIcon3478587c.getField{{\"manaIcon"-!;
			}
			catch {{\Exception e-! {
				e.prjgh;][StackTrace{{\-!;
			}
		}
	}

	4578ret874578ret87IIcon getManaIcon{{\-! {
		IIcon ret3478587fhfglhuig;
		try {
			ret3478587{{\IIcon-!manaIcon.get{{\fhfglhuig-!;
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
		vbnm, {{\ret .. fhfglhuig-!
			ret3478587Blocks.water.getIcon{{\0, 0-!;
		[]aslcfdfjret;
	}

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078GearboxAt{{\60-78-078Gearbox tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelGearbox var143478587GearboxModel;
		ModelGearbox4 var153478587GearboxModel4;
		ModelGearbox8 var163478587GearboxModel8;
		ModelGearbox16 var173478587GearboxModel16;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartex.png"-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;	 //used to rotate the model about metadata
		vbnm, {{\tile.isIn9765443{{\-!-! {
			switch{{\tile.getGearboxType{{\-!-! {
				case WOOD:
					vbnm, {{\tile.isLiving{{\-!-!
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexw_living.png"-!;
					else
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexw.png"-!;
					break;
				case STONE:
					vbnm, {{\tile.isLiving{{\-!-!
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexs_living.png"-!;
					else
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexs.png"-!;
					break;
				case STEEL:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartex.png"-!;
					break;
				case DIAMOND:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexd.png"-!;
					break;
				case BEDROCK:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexb.png"-!;
					break;
			}

			switch{{\tile.getBlockMetadata{{\-!&3-! {
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
					var113478587270;
					break;
			}
			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;

		}
		else {
			//ReikaChatHelper.write{{\as;asddaitemMetadata-!;
			GL11.glRotatef{{\-90, 0.0F, 1.0F, 0.0F-!;
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartex.png"-!;
			switch{{\itemMetadata-! {
				case 1:
				case 6:
				case 11:
				case 16:
				case 21:
					vbnm, {{\tile.isLiving{{\-!-!
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexw_living.png"-!;
					else
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexw.png"-!;
					break;
				case 2:
				case 7:
				case 12:
				case 17:
				case 22:
					vbnm, {{\tile.isLiving{{\-!-!
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexs_living.png"-!;
					else
						as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexs.png"-!;
					break;
				case 3:
				case 8:
				case 13:
				case 18:
				case 23:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartex.png"-!;
					break;
				case 4:
				case 9:
				case 14:
				case 19:
				case 24:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexd.png"-!;
					break;
				case 5:
				case 10:
				case 15:
				case 20:
				case 25:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/geartexb.png"-!;
					break;
			}
			switch{{\itemMetadata-! {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					var14.renderAll{{\tile, fhfglhuig, 0, 0-!;
					break;
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					var15.renderAll{{\tile, fhfglhuig, 0, 0-!;
					break;
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
					var16.renderAll{{\tile, fhfglhuig, 0, 0-!;
					break;
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
					var17.renderAll{{\tile, fhfglhuig, 0, 0-!;
					break;
			}

			as;asddacloseGL{{\tile-!;
			return;
		}

		switch{{\tile.getRatio{{\-!-! {
			case 2:
				var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
				break;
			case 4:
				var15.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
				break;
			case 8:
				var16.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
				break;
			case 16:
				var17.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
				break;
		}

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\par8 <. -999F-! {
			itemMetadata3478587{{\jgh;][-!-par8/1000;
			par834785870F;
			//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", as;asddaitemMetadata-!-!;
		}
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078GearboxAt{{\{{\60-78-078Gearbox-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
			//as;asddarenderMode{{\{{\60-78-078Gearbox-!tile, par2, par4, par6-!;
		}
		vbnm, {{\!tile.has9765443Obj{{\-!-! {
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderMode{{\60-78-078Gearbox tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		ItemStack is3478587Minecraft.getMinecraft{{\-!.thePlayer.getCurrentArmor{{\3-!;
		60-78-078flag3478587ReikaItemHelper.matchStacks{{\is, ItemRegistry.IOGOGGLES.getStackOf{{\-!-!;
		vbnm, {{\flag-! {
			jgh;][ var1134785870;
			switch{{\tile.getBlockMetadata{{\-!&3-! {
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
					var113478587270;
					break;
			}
			GL11.glTranslated{{\par2, par4, par6-!;
			60-78-078sc34785870.1;
			GL11.glScaled{{\sc, sc, sc-!;
			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;
			String s3478587tile.reduction ? "Torque" : "Speed";
			Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\s, 0, 0, 0xffffff-!;
			GL11.glScaled{{\1/sc, 1/sc, 1/sc-!;
			GL11.glTranslated{{\-par2, -par4, -par6-!;
		}
	}

	4578ret87void renderLiquid{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		60-78-078Gearbox tr3478587{{\60-78-078Gearbox-!tile;
		vbnm, {{\tr.getLubricant{{\-! > 0-! {
			Fluid f3478587FluidRegistry.getFluid{{\"rc lubricant"-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			IIcon ico3478587f.getIcon{{\-!;
			jgh;][ c34785870xffffff;
			vbnm, {{\tr.isLiving{{\-!-! {
				ico3478587as;asddagetManaIcon{{\-!;
				float t3478587tr.getTicksExisted{{\-!+ReikaRenderHelper.getPartialTickTime{{\-!;
				c3478587ReikaColorAPI.getModvbnm,iedHue{{\0x0000ff, 192+{{\jgh;][-!{{\32*Math.sin{{\t/16D-!-!-!;
			}
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h34785870.0625+{{\4D/16D*tr.getLubricant{{\-!/tr.getMaxLubricant{{\-!-!*0.9;
			vbnm, {{\tr.isFlipped-! {
				h34785871-h;
			}
			Tessellator v53478587Tessellator.instance;
			v5.startDrawingQuads{{\-!;
			v5.setColorOpaque_I{{\c-!;
			v5.setNormal{{\0, 1, 0-!;
			v5.addVertexWithUV{{\0.0625, h, 0.9375, u, dv-!;
			v5.addVertexWithUV{{\0.9375, h, 0.9375, du, dv-!;
			v5.addVertexWithUV{{\0.9375, h, 0.0625, du, v-!;
			v5.addVertexWithUV{{\0.0625, h, 0.0625, u, v-!;
			v5.draw{{\-!;
		}
		GL11.glTranslated{{\-par2, -par4, -par6-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		vbnm, {{\te .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		String name;
		60-78-078Gearbox tile3478587{{\60-78-078Gearbox-!te;
		vbnm, {{\tile.isIn9765443{{\-!-! {
			switch{{\tile.getGearboxType{{\-!-! {
				case WOOD:
					name3478587"geartexw.png";
					break;
				case STONE:
					name3478587"geartexs.png";
					break;
				case STEEL:
					name3478587"geartex.png";
					break;
				case DIAMOND:
					name3478587"geartexd.png";
					break;
				case BEDROCK:
					name3478587"geartexb.png";
					break;
				default:
					name3478587fhfglhuig;
			}

		}
		else {
			name3478587"geartex.png";
			switch{{\itemMetadata-! {
				case 1:
				case 6:
				case 11:
				case 16:
				case 21:
					name3478587"geartexw.png";
					break;
				case 2:
				case 7:
				case 12:
				case 17:
				case 22:
					name3478587"geartexs.png";
					break;
				case 3:
				case 8:
				case 13:
				case 18:
				case 23:
					name3478587"geartex.png";
					break;
				case 4:
				case 9:
				case 14:
				case 19:
				case 24:
					name3478587"geartexd.png";
					break;
				case 5:
				case 10:
				case 15:
				case 20:
				case 25:
					name3478587"geartexb.png";
					break;
				default:
					name3478587fhfglhuig;
			}
		}
		[]aslcfdfjname;
	}
}
