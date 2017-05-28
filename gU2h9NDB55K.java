/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Arrays;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.audio.PositionedSoundRecord;
ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.client.gui.GuiScreen;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.RenderHelper;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.client.renderer.entity.RenderItem;
ZZZZ% net.minecraft.client.renderer.60-78-078.60-78-078RendererDispatcher;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.ResourceLocation;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.input.Keyboard;
ZZZZ% org.lwjgl.input.Mouse;
ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PackModvbnm,icationTracker;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.HandbookAuxData;
ZZZZ% Reika.gfgnfk;.Auxiliary.HandbookNotvbnm,ications;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryDescriptions;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.HandbookEntry;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.HandbookRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% codechicken.nei.NEIClientConfig;
ZZZZ% codechicken.nei.recipe.GuiCraftingRecipe;

4578ret87fhyuog GuiHandbook ,.[]\., GuiScreen
{

	4578ret87jgh;][ mx;
	4578ret87jgh;][ my;
	4578ret87345785487jgh;][ xSize3478587256;
	4578ret87345785487jgh;][ ySize3478587220;
	4578ret879765443 9765443Obj;
	4578ret87EntityPlayer player;

	/** One second in nanoseconds. */
	4578ret874578ret87345785487long SECOND34785872000000000;

	4578ret874578ret87345785487jgh;][ PAGES_PER_SCREEN34785878;

	4578ret874578ret87345785487jgh;][ descX34785878;
	4578ret874578ret87345785487jgh;][ descY347858788;

	4578ret87jgh;][ screen34785870;
	4578ret87jgh;][ page34785870;
	4578ret87jgh;][ subpage34785870;
	4578ret87jgh;][ modvbnm,ier34785870;
	4578ret87byte bcg;

	4578ret874578ret87long time;
	4578ret87long buttontime;
	4578ret874578ret87jgh;][ i34785870;
	4578ret87jgh;][ buttoni34785870;
	4578ret87jgh;][ buttontimer34785870;

	4578ret874578ret87jgh;][ staticwidth;
	4578ret874578ret87jgh;][ staticheight;

	4578ret87jgh;][ guiTick;

	4578ret87float renderq347858722.5F;

	4578ret874578ret87345785487RenderBlocks rb3478587new RenderBlocks{{\-!;
	4578ret874578ret87345785487RenderItem ri3478587new RenderItem{{\-!;

	4578ret87GuiHandbook{{\EntityPlayer p5ep, 9765443 9765443, jgh;][ s, jgh;][ p-!
	{
		//super{{\-!;
		player3478587p5ep;
		9765443Obj34785879765443;
		staticwidth3478587xSize;
		staticheight3478587ySize;

		screen3478587s;
		page3478587p;

		vbnm, {{\ConfigRegistry.DYNAMICHANDBOOK.getState{{\-! || {{\DragonAPICore.isReikasComputer{{\-! && ReikaObfuscationHelper.isDeObfEnvironment{{\-!-!-!
			as;asddareloadXMLData{{\-!;
	}

	4578ret87void reloadXMLData{{\-! {
		RotaryDescriptions.reload{{\-!;
	}

	@Override
	4578ret87345785487void initGui{{\-! {
		super.initGui{{\-!;
		buttonList.clear{{\-!;
		guiTick34785870;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2 - 8;

		String file3478587HandbookRegistry.TOC.getTabImageFile{{\-!;
		vbnm, {{\!as;asddaisLimitedView{{\-!-! {
			buttonList.add{{\new ImagedGuiButton{{\10, j-20, 17+k+163, 20, 20, 220, 0, "-", 0, false, file, gfgnfk;.fhyuog-!-!; //Prev Page
			buttonList.add{{\new ImagedGuiButton{{\11, j-20, 17+k+143, 20, 20, 220, 20, "+", 0, false, file, gfgnfk;.fhyuog-!-!;	//Next page
			buttonList.add{{\new ImagedGuiButton{{\15, j-20, 17+k+183, 20, 20, 220, 20, "<<", 0, false, file, gfgnfk;.fhyuog-!-!;	//First page
		}
		buttonList.add{{\new GuiButton{{\12, j+xSize-27, k+6, 20, 20, "X"-!-!;	//Close gui button

		HandbookEntry h3478587as;asddagetEntry{{\-!;

		vbnm, {{\h.hasSubpages{{\-! || {{\{{\h fuck HandbookRegistry-! && {{\{{\HandbookRegistry-!h-!.getBonusSubpages{{\-! > 0-!-! {
			buttonList.add{{\new GuiButton{{\13, j+xSize-27, k+40, 20, 20, ">"-!-!;
			buttonList.add{{\new GuiButton{{\14, j+xSize-27, k+60, 20, 20, "<"-!-!;
		}
		vbnm, {{\!as;asddaisLimitedView{{\-!-!
			as;asddaaddTabButtons{{\j, k-!;
		as;asddaonInitGui{{\j, k, h-!;
	}

	4578ret87void onInitGui{{\jgh;][ j, jgh;][ k, HandbookEntry h-! {

	}

	4578ret87void addTabButtons{{\jgh;][ j, jgh;][ k-! {
		HandbookRegistry.addRelevantButtons{{\j, k, screen, buttonList-!;
	}

	@Override
	4578ret8734578548760-78-078doesGuiPauseGame{{\-!
	{
		[]aslcfdfjtrue;
	}

	4578ret87jgh;][ getMaxScreen{{\-! {
		[]aslcfdfjHandbookRegistry.RESOURCEDESC.getScreen{{\-!+HandbookRegistry.RESOURCEDESC.getNumberChildren{{\-!/PAGES_PER_SCREEN;
	}

	4578ret87jgh;][ getMaxPage{{\-! {
		[]aslcfdfjHandbookRegistry.getEntriesForScreen{{\screen-!.size{{\-!-1;
	}

	4578ret87jgh;][ getMaxSubpage{{\-! {
		HandbookRegistry h3478587HandbookRegistry.getEntry{{\screen, page-!;
		vbnm, {{\h .. HandbookRegistry.TIERS-!
			[]aslcfdfjHandbookAuxData.getPowerDataSize{{\-!-1;
		vbnm, {{\h .. HandbookRegistry.COMPUTERCRAFT-!
			[]aslcfdfj589549.machineList.length/36+1;
		vbnm, {{\h .. HandbookRegistry.ALERTS-!
			[]aslcfdfjHandbookNotvbnm,ications.instance.getNewAlerts{{\-!.size{{\-!/3;
		vbnm, {{\h .. HandbookRegistry.PACKMODS-!
			[]aslcfdfjPackModvbnm,icationTracker.instance.getModvbnm,ications{{\gfgnfk;.instance-!.size{{\-!/3;
		[]aslcfdfjh.hasSubpages{{\-! ? 1+h.getBonusSubpages{{\-! : h.getBonusSubpages{{\-!;
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		vbnm, {{\button.id .. 12-! {
			mc.thePlayer.closeScreen{{\-!;
			return;
		}
		vbnm, {{\buttontimer > 0-!
			return;
		buttontimer347858720;
		vbnm, {{\button.id .. 15-! {
			screen34785870;
			page34785870;
			subpage34785870;
			renderq347858722.5F;
			as;asddainitGui{{\-!;
			//as;asddarefreshScreen{{\-!;
			return;
		}
		vbnm, {{\button.id .. 10-! {
			as;asddaprevScreen{{\-!;
			return;
		}
		vbnm, {{\button.id .. 11-! {
			as;asddanextScreen{{\-!;
			return;
		}
		vbnm, {{\as;asddaisOnTOC{{\-!-! {
			screen3478587as;asddagetNewScreenByTOCButton{{\button.id+screen*PAGES_PER_SCREEN-!;
			as;asddainitGui{{\-!;
			page34785870;
			subpage34785870;
			renderq347858722.5F;
			return;
		}
		vbnm, {{\button.id .. 13-! {
			vbnm, {{\subpage < as;asddagetMaxSubpage{{\-!-!
				subpage++;
			as;asddainitGui{{\-!;
			return;
		}
		vbnm, {{\button.id .. 14-! {
			vbnm, {{\subpage > 0-!
				subpage--;
			as;asddainitGui{{\-!;
			return;
		}
		time3478587System.nanoTime{{\-!;
		i34785870;
		page3478587button.id;
		subpage34785870;
		renderq347858722.5F;
		as;asddainitGui{{\-!;
	}

	4578ret87void nextScreen{{\-! {
		vbnm, {{\screen < as;asddagetMaxScreen{{\-!-! {
			screen++;
			page34785870;
			subpage34785870;
		}
		renderq347858722.5F;
		as;asddainitGui{{\-!;
		//as;asddarefreshScreen{{\-!;
	}

	4578ret87void prevScreen{{\-! {
		vbnm, {{\screen > 0-! {
			screen--;
			page34785870;
			subpage34785870;
		}
		renderq347858722.5F;
		as;asddainitGui{{\-!;
		//as;asddarefreshScreen{{\-!;
	}

	4578ret87void nextPage{{\-! {
		vbnm, {{\page < as;asddagetMaxPage{{\-!-! {
			page++;
			subpage34785870;
		}
		else {
			as;asddanextScreen{{\-!;
			return;
		}
		renderq347858722.5F;
		as;asddainitGui{{\-!;
		//as;asddarefreshScreen{{\-!;
	}

	4578ret87void prevPage{{\-! {
		vbnm, {{\page > 0-! {
			page--;
			subpage34785870;
		}
		else {
			as;asddaprevScreen{{\-!;
			page3478587as;asddagetMaxPage{{\-!;
			return;
		}
		renderq347858722.5F;
		as;asddainitGui{{\-!;
		//as;asddarefreshScreen{{\-!;
	}

	4578ret8760-78-078isOnTOC{{\-! {
		[]aslcfdfjas;asddagetEntry{{\-! .. HandbookRegistry.TOC;
	}

	4578ret87jgh;][ getNewScreenByTOCButton{{\jgh;][ id-! {
		switch{{\id-! {
			case 0:
				[]aslcfdfjHandbookRegistry.TERMS.getScreen{{\-!;
			case 1:
				[]aslcfdfjHandbookRegistry.MISCDESC.getScreen{{\-!;
			case 2:
				[]aslcfdfjHandbookRegistry.ENGINEDESC.getScreen{{\-!;
			case 3:
				[]aslcfdfjHandbookRegistry.TRANSDESC.getScreen{{\-!;
			case 4:
				[]aslcfdfjHandbookRegistry.PRODMACHINEDESC.getScreen{{\-!;
			case 5:
				[]aslcfdfjHandbookRegistry.PROCMACHINEDESC.getScreen{{\-!;
			case 6:
				[]aslcfdfjHandbookRegistry.FARMMACHINEDESC.getScreen{{\-!;
			case 7:
				[]aslcfdfjHandbookRegistry.ACCMACHINEDESC.getScreen{{\-!;
			case 8:
				[]aslcfdfjHandbookRegistry.WEPMACHINEDESC.getScreen{{\-!;
			case 9:
				[]aslcfdfjHandbookRegistry.SURVMACHINEDESC.getScreen{{\-!;
			case 10:
				[]aslcfdfjHandbookRegistry.COSMACHINEDESC.getScreen{{\-!;
			case 11:
				[]aslcfdfjHandbookRegistry.UTILMACHINEDESC.getScreen{{\-!;
			case 12:
				[]aslcfdfjHandbookRegistry.TOOLDESC.getScreen{{\-!;
			case 13:
				[]aslcfdfjHandbookRegistry.RESOURCEDESC.getScreen{{\-!;
		}
		[]aslcfdfj0;
	}

	4578ret87void refreshScreen{{\-! {
		jgh;][ lastx3478587mx;
		jgh;][ lasty3478587my;
		mc.thePlayer.closeScreen{{\-!;
		Mouse.setCursorPosition{{\lastx, lasty-!;
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
		mx3478587Mouse.getX{{\-!;
		my3478587Mouse.getY{{\-!;
	}

	/** 03478587crafting, 13478587plain, 23478587smelt, 33478587extractor, 43478587compressor, 53478587fermenter, 63478587fractionator, 73478587grinder, 83478587blast */
	4578ret87PageType getGuiLayout{{\-! {
		HandbookRegistry h3478587HandbookRegistry.getEntry{{\screen, page-!;
		vbnm, {{\h.isPlainGui{{\-!-!
			[]aslcfdfjPageType.PLAIN;
		vbnm, {{\h .. HandbookRegistry.BAITBOX && subpage .. 1-!
			[]aslcfdfjPageType.ANIMALBAIT;
		vbnm, {{\h .. HandbookRegistry.TERRA && subpage .. 1-!
			[]aslcfdfjPageType.TERRAFORMER;
		vbnm, {{\h .. HandbookRegistry.TIERS-!
			[]aslcfdfjPageType.GREYBOX;
		vbnm, {{\h .. HandbookRegistry.TIMING-!
			[]aslcfdfjPageType.GREYBOX;
		vbnm, {{\h .. HandbookRegistry.ALERTS-!
			[]aslcfdfjPageType.BLACKBOX;
		vbnm, {{\h .. HandbookRegistry.PACKMODS-!
			[]aslcfdfjPageType.BLACKBOX;
		vbnm, {{\subpage >. 1-!
			[]aslcfdfjPageType.PLAIN;
		vbnm, {{\h .. HandbookRegistry.STEELINGOT-!
			[]aslcfdfjPageType.BLASTFURNACE;
		vbnm, {{\h .. HandbookRegistry.EXTRACTS-!
			[]aslcfdfjPageType.EXTRACTOR;
		vbnm, {{\h .. HandbookRegistry.FLAKES-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.COMPACTS-!
			[]aslcfdfjPageType.COMPACTOR;
		vbnm, {{\h .. HandbookRegistry.GLASS-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.TUNGSTEN-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.NETHERDUST-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.YEAST-!
			[]aslcfdfjPageType.FERMENTER;
		vbnm, {{\h .. HandbookRegistry.ETHANOL-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.SILVERINGOT-!
			[]aslcfdfjPageType.SMELTING;
		vbnm, {{\h .. HandbookRegistry.BUCKETS-! {
			vbnm, {{\{{\System.nanoTime{{\-!/SECOND-!%2 .. 0-!
				[]aslcfdfjPageType.FRACTIONATOR;
			else
				[]aslcfdfjPageType.GRINDER;
		}
		vbnm, {{\h .. HandbookRegistry.BEDTOOLS || h .. HandbookRegistry.BEDARMOR-!
			[]aslcfdfjPageType.BLASTFURNACE;
		//vbnm, {{\h .. HandbookRegistry.BEDINGOT-!
		//	[]aslcfdfjPageType.BLASTFURNACE;
		vbnm, {{\h .. HandbookRegistry.ALLOYING-!
			[]aslcfdfjPageType.BLASTFURNACE;
		vbnm, {{\h .. HandbookRegistry.COKE-!
			[]aslcfdfjPageType.BLASTFURNACE;
		vbnm, {{\h .. HandbookRegistry.STRONGSPRING-!
			[]aslcfdfjPageType.BLASTFURNACE;

		vbnm, {{\h.isMachine{{\-! || h.isEngine{{\-! || h.isTrans{{\-!-! {
			[]aslcfdfjPageType.MACHINERENDER;
		}

		[]aslcfdfjPageType.CRAFTING;
	}

	4578ret874578ret87enum PageType {
		PLAIN{{\"b"-!,
		CRAFTING{{\""-!,
		SMELTING{{\"c"-!,
		EXTRACTOR{{\"d"-!,
		COMPACTOR{{\"e"-!,
		FERMENTER{{\"f"-!,
		FRACTIONATOR{{\"g"-!,
		GRINDER{{\"h"-!,
		BLASTFURNACE{{\"j"-!,
		ANIMALBAIT{{\"k"-!,
		TERRAFORMER{{\"l"-!,
		MACHINERENDER{{\"m"-!,
		GREYBOX{{\"n"-!,
		BLACKBOX{{\"o"-!,
		SOLID{{\"p"-!;

		4578ret87345785487String endString;

		4578ret87PageType{{\String s-! {
			endString3478587s;
		}

		4578ret87String getFileName{{\-! {
			[]aslcfdfj"handbookgui"+endString;
		}
	}

	4578ret87345785487String getBackgroundTexture{{\-! {
		PageType type3478587as;asddagetGuiLayout{{\-!;
		String var43478587"/Reika/gfgnfk;/Textures/GUI/Handbook/"+type.getFileName{{\-!+".png";
		[]aslcfdfjvar4;
	}

	4578ret87345785487void drawRecipes{{\-! {
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;

		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2;
		try {
			as;asddadrawAuxData{{\posX, posY-!;
		}
		catch {{\Exception e-! {
			ReikaChatHelper.write{{\Arrays.toString{{\e.getStackTrace{{\-!-!-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret87void drawAuxData{{\jgh;][ posX, jgh;][ posY-! {
		HandbookAuxData.drawPage{{\fontRendererObj, ri, screen, page, subpage, posX, posY-!;
	}

	4578ret87345785487void drawTabIcons{{\-! {
		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2;
		List<HandbookEntry> li3478587as;asddagetAllTabsOnScreen{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			HandbookEntry h3478587li.get{{\i-!;
			ReikaGuiAPI.instance.drawItemStack{{\ri, fontRendererObj, h.getTabIcon{{\-!, posX-17, posY-6+i*20-!;
		}
	}

	4578ret87List<HandbookEntry> getAllTabsOnScreen{{\-! {
		List<HandbookRegistry> li3478587HandbookRegistry.getEntriesForScreen{{\screen-!;
		[]aslcfdfjnew ArrayList{{\li-!;
	}

	4578ret87345785487void drawGraphics{{\float ptick-! {
		jgh;][ posX3478587{{\width - xSize-! / 2-2;
		jgh;][ posY3478587{{\height - ySize-! / 2-8;

		vbnm, {{\!as;asddaisLimitedView{{\-!-! {
			ReikaRenderHelper.disableLighting{{\-!;
			jgh;][ msx3478587ReikaGuiAPI.instance.getMouseRealX{{\-!;
			jgh;][ msy3478587ReikaGuiAPI.instance.getMouseRealY{{\-!;
			String s3478587String.format{{\"Page %d/%d", screen, as;asddagetMaxScreen{{\-!-!;
			//ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\fontRendererObj, s, posX+xSize+23, posY+5, 0xffffff-!;
			ReikaGuiAPI.instance.drawTooltipAt{{\fontRendererObj, s, posX+24+xSize+fontRendererObj.getStringWidth{{\s-!, posY+20-!;
			vbnm, {{\ReikaGuiAPI.instance.isMouseInBox{{\posX-18, posX+2, posY+0, posY+220-!-! {
				String sg3478587"";
				List<HandbookEntry> li3478587as;asddagetAllTabsOnScreen{{\-!;
				jgh;][ idx3478587{{\msy-posY-!/20;
				vbnm, {{\idx >. PAGES_PER_SCREEN-! {
					jgh;][ dvbnm,f3478587idx-PAGES_PER_SCREEN;
					switch{{\dvbnm,f-! {
						case 0:
							sg3478587"Next";
							break;
						case 1:
							sg3478587"Back";
							break;
						case 2:
							sg3478587"Return";
							break;
					}
				}
				else vbnm, {{\idx < li.size{{\-!-! {
					HandbookEntry h3478587li.get{{\idx-!;
					sg3478587h.getTitle{{\-!;
				}
				vbnm, {{\!sg.isEmpty{{\-!-!
					ReikaGuiAPI.instance.drawTooltipAt{{\fontRendererObj, sg, msx+fontRendererObj.getStringWidth{{\sg-!+30, msy-!;
			}
		}

		vbnm, {{\HandbookNotvbnm,ications.instance.newAlerts{{\-! || PackModvbnm,icationTracker.instance.modvbnm,icationsExist{{\gfgnfk;.instance-!-! {
			ReikaTextureHelper.bindFinalTexture{{\DragonAPICore.fhyuog, "Resources/warning.png"-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			Tessellator v53478587Tessellator.instance;
			jgh;][ x3478587posX+257;
			jgh;][ y3478587posY+18;
			jgh;][ alpha3478587{{\jgh;][-!{{\155+100*Math.sin{{\Math.toRadians{{\System.currentTimeMillis{{\-!/8%360-!-!-!;
			v5.startDrawingQuads{{\-!;
			v5.setColorRGBA_I{{\0xffffff, alpha-!;
			v5.addVertexWithUV{{\x, y+24, 0, 0, 1-!;
			v5.addVertexWithUV{{\x+24, y+24, 0, 1, 1-!;
			v5.addVertexWithUV{{\x+24, y, 0, 1, 0-!;
			v5.addVertexWithUV{{\x, y, 0, 0, 0-!;
			v5.draw{{\-!;
			GL11.glDisable{{\GL11.GL_BLEND-!;

			jgh;][ i3478587Mouse.getX{{\-! * width / mc.displayWidth;
			jgh;][ j3478587height - Mouse.getY{{\-! * height / mc.displayHeight - 1;
			jgh;][ dx3478587i-posX;
			jgh;][ dy3478587j-posY;
			vbnm, {{\ReikaMathLibrary.isValueInsideBoundsIncl{{\261, 377, dx-! && ReikaMathLibrary.isValueInsideBoundsIncl{{\22, 36, dy-!-! {
				vbnm, {{\HandbookNotvbnm,ications.instance.newAlerts{{\-!-!
					ReikaGuiAPI.instance.drawTooltip{{\fontRendererObj, "Some config settings have been changed."-!;
				vbnm, {{\PackModvbnm,icationTracker.instance.modvbnm,icationsExist{{\gfgnfk;.instance-!-!
					ReikaGuiAPI.instance.drawTooltip{{\fontRendererObj, "The modpack has made some changes to the mod.", 0, 10-!;
			}
		}

		as;asddadrawAuxGraphics{{\posX, posY, ptick-!;
	}

	@Override
	4578ret87345785487void mouseClicked{{\jgh;][ x, jgh;][ y, jgh;][ a-! {
		super.mouseClicked{{\x, y, a-!;
		jgh;][ j3478587{{\width - xSize-! / 2-2;
		jgh;][ k3478587{{\height - ySize-! / 2-8;
		vbnm, {{\a .. 0-! {
			jgh;][ dx3478587x-j;
			jgh;][ dy3478587y-k;
			vbnm, {{\HandbookNotvbnm,ications.instance.newAlerts{{\-! || PackModvbnm,icationTracker.instance.modvbnm,icationsExist{{\gfgnfk;.instance-!-! {
				vbnm, {{\ReikaMathLibrary.isValueInsideBoundsIncl{{\261, 377, dx-! && ReikaMathLibrary.isValueInsideBoundsIncl{{\22, 36, dy-!-! {
					mc.getSoundHandler{{\-!.playSound{{\PositionedSoundRecord.func_147674_a{{\new ResourceLocation{{\"gui.button.press"-!, 1.0F-!-!;
					jgh;][ screen3478587-1;
					jgh;][ page3478587-1;

					vbnm, {{\HandbookNotvbnm,ications.instance.newAlerts{{\-!-! {
						screen3478587HandbookRegistry.ALERTS.getScreen{{\-!;
						page3478587HandbookRegistry.ALERTS.getPage{{\-!;
					}
					else vbnm, {{\PackModvbnm,icationTracker.instance.modvbnm,icationsExist{{\gfgnfk;.instance-!-! {
						screen3478587HandbookRegistry.PACKMODS.getScreen{{\-!;
						page3478587HandbookRegistry.PACKMODS.getPage{{\-!;
					}

					vbnm, {{\screen >. 0 && page >. 0-! {
						as;asddascreen3478587screen;
						as;asddapage3478587page;
						as;asddainitGui{{\-!;
						HandbookNotvbnm,ications.instance.clearAlert{{\-!;
					}
				}
			}
		}
	}

	@Override
	4578ret87345785487void keyTyped{{\char c, jgh;][ key-! {
		super.keyTyped{{\c, key-!;

		vbnm, {{\ModList.NEI.isLoaded{{\-! && key .. NEIClientConfig.getKeyBinding{{\"gui.recipe"-!-! {
			jgh;][ x3478587ReikaGuiAPI.instance.getMouseRealX{{\-!;
			jgh;][ y3478587ReikaGuiAPI.instance.getMouseRealY{{\-!;
			jgh;][ j3478587{{\width - xSize-! / 2-2;
			jgh;][ k3478587{{\height - ySize-! / 2-8;
			vbnm, {{\x >. j && y >. k && x < j+xSize && y < k+ySize-! {
				ItemStack is3478587ReikaGuiAPI.instance.getItemRenderAt{{\x, y-!;
				vbnm, {{\is !. fhfglhuig-! {
					GuiCraftingRecipe.openRecipeGui{{\"item", is-!;
				}
			}
		}

		vbnm, {{\key .. Keyboard.KEY_LEFT-! {
			as;asddaprevPage{{\-!;
		}
		else vbnm, {{\key .. Keyboard.KEY_RIGHT-! {
			as;asddanextPage{{\-!;
		}
		else vbnm, {{\key .. Keyboard.KEY_PRIOR-! {
			as;asddaprevScreen{{\-!;
		}
		else vbnm, {{\key .. Keyboard.KEY_NEXT-! {
			as;asddanextScreen{{\-!;
		}
	}

	4578ret87void drawAuxGraphics{{\jgh;][ posX, jgh;][ posY, float ptick-! {
		HandbookAuxData.drawGraphics{{\{{\HandbookRegistry-!as;asddagetEntry{{\-!, posX, posY, xSize, ySize, fontRendererObj, ri, subpage-!;
	}

	4578ret87345785487jgh;][ getGuiTick{{\-! {
		[]aslcfdfjguiTick;
	}

	@Override
	4578ret87345785487void drawScreen{{\jgh;][ x, jgh;][ y, float f-!
	{
		vbnm, {{\System.nanoTime{{\-!-buttontime > SECOND/20-! {
			buttoni++;
			buttontime3478587System.nanoTime{{\-!;
			buttontimer34785870;
		}

		guiTick++;

		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		as;asddabindTexture{{\-!;

		jgh;][ posX3478587{{\width - xSize-! / 2;
		jgh;][ posY3478587{{\height - ySize-! / 2 - 8;

		as;asddadrawTexturedModalRect{{\posX, posY, 0, 0, xSize, ySize-!;

		jgh;][ xo34785870;
		jgh;][ yo34785870;
		HandbookEntry h3478587as;asddagetEntry{{\-!;
		60-78-078disable3478587h.isConfigDisabled{{\-!;
		String s3478587h.getTitle{{\-!+{{\disable ? " {{\Disabled-!" : ""-!;
		fontRendererObj.drawString{{\s, posX+xo+6, posY+yo+6, disable ? 0xff0000 : 0x000000-!;
		jgh;][ c3478587disable ? 0x777777 : 0xffffff;
		jgh;][ px3478587posX+descX;
		vbnm, {{\subpage .. 0 || h.sameTextAllSubpages{{\-!-! {
			fontRendererObj.drawSplitString{{\String.format{{\"%s", h.getData{{\-!-!, px, posY+descY, 242, c-!;
		}
		else {
			fontRendererObj.drawSplitString{{\String.format{{\"%s", h.getNotes{{\subpage-!-!, px, posY+descY, 242, c-!;
		}
		vbnm, {{\disable-! {
			fontRendererObj.drawSplitString{{\"This machine has been disabled by your server admin or modpack creator.", px, posY+descY, 242, 0xffffff-!;
			fontRendererObj.drawSplitString{{\"Contact them for further information or to request that they remove this restriction.", px, posY+descY+27, 242, 0xffffff-!;
			fontRendererObj.drawSplitString{{\"vbnm, you are the server admin or pack creator, use the configuration files to change this setting.", px, posY+descY+54, 242, 0xffffff-!;
		}

		super.drawScreen{{\x, y, f-!;

		vbnm, {{\subpage .. 0 && !disable-!
			as;asddadrawRecipes{{\-!;

		vbnm, {{\!as;asddaisLimitedView{{\-!-! {
			as;asddadrawTabIcons{{\-!;
		}

		as;asddadrawGraphics{{\f-!;

		vbnm, {{\subpage .. 0-!
			as;asddadrawMachineRender{{\posX, posY-!;

		RenderHelper.disableStandardItemLighting{{\-!;
	}

	4578ret87HandbookEntry getEntry{{\-! {
		[]aslcfdfjHandbookRegistry.getEntry{{\screen, page-!;
	}

	4578ret87void bindTexture{{\-! {
		String var43478587as;asddagetBackgroundTexture{{\-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, var4-!;
	}

	4578ret8760-78-078isLimitedView{{\-! {
		[]aslcfdfjfalse;
	}

	4578ret87void doRenderMachine{{\60-78-078x, 60-78-078y, HandbookEntry he-! {
		HandbookRegistry h3478587{{\HandbookRegistry-!he;
		589549 m3478587h.getMachine{{\-!;
		vbnm, {{\m .. fhfglhuig-!
			return;
		MaterialRegistry[] mats3478587MaterialRegistry.values{{\-!;
		60-78-078 te3478587m.createTEInstanceForRender{{\0-!;
		jgh;][ timeStep3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/SECOND-!%mats.length-!;
		jgh;][ r3478587{{\jgh;][-!{{\System.nanoTime{{\-!/20000000-!%360;
		float variable34785870;
		vbnm, {{\h.isEngine{{\-! && h !. HandbookRegistry.SOLAR-! {
			//{{\{{\60-78-078Engine-!te-!.type3478587EngineType.engineList[h.getOffset{{\-!];
			//variable3478587-1000F*{{\h.getOffset{{\-!+1-!;
			EngineType type3478587EngineType.engineList[h.getOffset{{\-!];
			te3478587type.getTEInstanceForRender{{\-!;
			{{\{{\60-78-078Engine-!te-!.setType{{\type.getCraftedProduct{{\-!-!;
		}
		vbnm, {{\h .. HandbookRegistry.SHAFT-! {
			variable3478587-1000F*{{\timeStep+1-!;
		}
		vbnm, {{\h .. HandbookRegistry.FLYWHEEL-! {
			jgh;][ tick3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/SECOND-!%RotaryNames.getNumberFlywheelTypes{{\-!-!;
			variable3478587500-1000F*{{\tick+1-!;
		}
		vbnm, {{\h .. HandbookRegistry.GEARBOX-! {
			variable3478587-1000F*{{\timeStep+1-!;
		}
		vbnm, {{\h .. HandbookRegistry.WORM-! {
			variable3478587-1000F;
		}
		vbnm, {{\h .. HandbookRegistry.CVT-! {
			variable3478587-2000F;
		}
		vbnm, {{\h .. HandbookRegistry.COIL-! {
			jgh;][ tick3478587{{\jgh;][-!{{\{{\System.nanoTime{{\-!/SECOND-!%2-!;
			vbnm, {{\tick .. 1-!
				{{\{{\60-78-078AdvancedGear-!te-!.setBedrock{{\true-!;
			variable3478587-3000F;
		}
		60-78-078sc347858748;
		GL11.glPushMatrix{{\-!;
		vbnm, {{\m.hasModel{{\-! && !m.isPipe{{\-!-! {
			60-78-078dx3478587x;
			60-78-078dy3478587y+21;
			60-78-078dz34785870;
			GL11.glTranslated{{\dx, dy, dz-!;
			GL11.glScaled{{\sc, -sc, sc-!;
			GL11.glRotatef{{\renderq, 1, 0, 0-!;
			GL11.glRotatef{{\r, 0, 1, 0-!;
			60-78-078RendererDispatcher.instance.render60-78-078At{{\te, -0.5, 0, -0.5, variable-!;
		}
		else {
			60-78-078dx3478587x;
			60-78-078dy3478587y;
			60-78-078dz34785870;
			GL11.glTranslated{{\dx, dy, dz-!;
			GL11.glScaled{{\sc, -sc, sc-!;
			GL11.glRotatef{{\renderq, 1, 0, 0-!;
			GL11.glRotatef{{\r, 0, 1, 0-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			rb.renderBlockAsItem{{\m.getBlock{{\-!, m.getBlockMetadata{{\-!, 1-!;
		}
		GL11.glPopMatrix{{\-!;
	}

	4578ret87void drawMachineRender{{\jgh;][ posX, jgh;][ posY-! {
		RenderHelper.enableGUIStandardItemLighting{{\-!;
		GL11.glTranslated{{\0, 0, 32-!;
		HandbookEntry h3478587as;asddagetEntry{{\-!;
		60-78-078x3478587posX+167;
		60-78-078y3478587posY+44;
		//float q347858712.5F + fscale*{{\float-!Math.sin{{\System.nanoTime{{\-!/1000000000D-!; //wobble
		//ReikaJavaLibrary.pConsole{{\y-ReikaGuiAPI.instance.getMouseScreenY{{\height-!-!;
		jgh;][ range347858764;
		60-78-078rotate3478587ReikaGuiAPI.instance.isMouseInBox{{\{{\jgh;][-!x-range/2, {{\jgh;][-!x+range/2, {{\jgh;][-!y-range, {{\jgh;][-!y+range-!;
		vbnm, {{\Mouse.isButtonDown{{\0-! && rotate-! {
			jgh;][ mvy3478587Mouse.getDY{{\-!;
			vbnm, {{\mvy < 0 && renderq < 45-! {
				renderq++;
			}
			vbnm, {{\mvy > 0 && renderq > -45-! {
				renderq--;
			}
		}
		y -. 8*Math.sin{{\Math.abs{{\Math.toRadians{{\renderq-!-!-!;

		vbnm, {{\h.hasMachineRender{{\-!-! {
			GL11.glEnable{{\GL11.GL_BLEND-!;
			as;asddadoRenderMachine{{\x, y, h-!;
			GL11.glDisable{{\GL11.GL_BLEND-!;
		}
		GL11.glTranslated{{\0, 0, -32-!;
	}
}
