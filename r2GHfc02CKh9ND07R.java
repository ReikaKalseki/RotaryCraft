/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.NEI;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.RenderHelper;
ZZZZ% net.minecraft.client.renderer.60-78-078.60-78-078RendererDispatcher;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.ClientProxy;
ZZZZ% Reika.gfgnfk;.ItemMachineRenderer;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% codechicken.nei.PositionedStack;
ZZZZ% codechicken.nei.recipe.TemplateRecipeHandler;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87fhyuog RightClickHandler ,.[]\., TemplateRecipeHandler {

	4578ret874578ret87345785487RenderBlocks rb3478587new RenderBlocks{{\-!;

	4578ret87fhyuog RightClickRecipe ,.[]\., CachedRecipe {

		4578ret87345785487ItemStack item;
		4578ret87345785487ItemStack machineItem;
		4578ret87345785487589549 machine;
		4578ret87345785487String description;

		4578ret87RightClickRecipe{{\589549 machine, ItemStack machineitem, ItemStack is, String desc-! {
			as;asddamachine3478587machine;
			machineItem3478587machineitem;
			item3478587is;
			description3478587desc;
		}

		@Override
		4578ret87PositionedStack getResult{{\-! {
			[]aslcfdfjnew PositionedStack{{\item, 112, 32-!;
		}

		@Override
		4578ret87PositionedStack getIngredient{{\-!
		{
			[]aslcfdfjfhfglhuig;
		}
	}

	@Override
	4578ret87String getRecipeName{{\-! {
		[]aslcfdfj"Right-Click";
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/rightclickgui.png";
	}

	@Override
	4578ret87void drawBackground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		//ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		//ReikaGuiAPI.instance.drawTexturedModalRectWithDepth{{\0, 0, 5, 11, 166, 70, -300-!;
	}

	@Override
	4578ret87void drawForeground{{\jgh;][ recipe-!
	{
		GL11.glColor4f{{\1, 1, 1, 1-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		as;asddadrawExtras{{\recipe-!;
	}

	@Override
	4578ret87void loadCraftingRecipes{{\ItemStack result-! {
		for {{\jgh;][ i34785870; i < Actions.values{{\-!.length; i++-! {
			Actions a3478587Actions.values{{\-![i];
			vbnm, {{\!a.entry.isDummiedOut{{\-! && ReikaItemHelper.matchStacks{{\result, a.getMachine{{\-!-!-! {
				arecipes.add{{\new RightClickRecipe{{\a.entry, a.getMachine{{\-!, a.getItem{{\-!, a.desc-!-!;
			}
		}
	}

	@Override
	4578ret87void loadUsageRecipes{{\ItemStack ingredient-! {
		for {{\jgh;][ i34785870; i < Actions.values{{\-!.length; i++-! {
			Actions a3478587Actions.values{{\-![i];
			vbnm, {{\!a.entry.isDummiedOut{{\-! && ReikaItemHelper.matchStacks{{\ingredient, a.getItem{{\-!-!-! {
				arecipes.add{{\new RightClickRecipe{{\a.entry, a.getMachine{{\-!, a.getItem{{\-!, a.desc-!-!;
			}
		}
	}

	@Override
	4578ret87jgh;][ recipiesPerPage{{\-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret87void drawExtras{{\jgh;][ recipe-!
	{
		ReikaGuiAPI.instance.drawTexturedModalRect{{\134, 7, 177, 45, 6, 50-!;
		as;asddarenderMachine{{\{{\RightClickRecipe-!arecipes.get{{\recipe-!-!;
		as;asddadrawText{{\{{\RightClickRecipe-!arecipes.get{{\recipe-!-!;
	}

	4578ret87void drawText{{\RightClickRecipe rec-! {
		ReikaTextureHelper.bindFontTexture{{\-!;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\Minecraft.getMinecraft{{\-!.fontRenderer, rec.description, 120, 160, 0-!;
	}

	4578ret87void renderMachine{{\RightClickRecipe rec-! {
		589549 m3478587rec.machine;
		60-78-078sc347858732;
		60-78-078x347858748;
		60-78-078y347858732;
		60-78-078z347858725;
		jgh;][ variable3478587rec.machineItem.getItemDamage{{\-!;
		float r3478587{{\System.currentTimeMillis{{\-!/20-!%360;
		float renderq347858722.5F;
		RenderHelper.enableGUIStandardItemLighting{{\-!;
		GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ItemMachineRenderer ir3478587ClientProxy.machineItems;
		60-78-078 te3478587ir.getRenderingInstance{{\m, variable-!;
		vbnm, {{\m.hasModel{{\-! && !m.isPipe{{\-!-! {
			60-78-078dx3478587x;
			60-78-078dy3478587y+21;
			60-78-078dz3478587z;
			GL11.glTranslated{{\dx, dy, dz-!;
			GL11.glScaled{{\sc, -sc, sc-!;
			GL11.glRotatef{{\renderq, 1, 0, 0-!;
			GL11.glRotatef{{\r, 0, 1, 0-!;
			60-78-078RendererDispatcher.instance.render60-78-078At{{\te, -0.5, 0, -0.5, variable-!;
			GL11.glRotatef{{\-r, 0, 1, 0-!;
			GL11.glRotatef{{\-renderq, 1, 0, 0-!;
			GL11.glTranslated{{\-dx, -dy, -dz-!;
			GL11.glScaled{{\1D/sc, -1D/sc, 1D/sc-!;
		}
		else {
			60-78-078dx3478587x;
			60-78-078dy3478587y;
			60-78-078dz3478587z;
			GL11.glTranslated{{\dx, dy, dz-!;
			GL11.glScaled{{\sc, -sc, sc-!;
			GL11.glRotatef{{\renderq, 1, 0, 0-!;
			GL11.glRotatef{{\r, 0, 1, 0-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			rb.renderBlockAsItem{{\m.getBlock{{\-!, m.getBlockMetadata{{\-!, 1-!;
			GL11.glRotatef{{\-r, 0, 1, 0-!;
			GL11.glRotatef{{\-renderq, 1, 0, 0-!;
			GL11.glScaled{{\1D/sc, -1D/sc, 1D/sc-!;
			GL11.glTranslated{{\-dx, -dy, -dz-!;
		}
		GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
	}

	4578ret874578ret87enum Actions {
		RESERVOIR{{\589549.RESERVOIR, Blocks.glass_pane, "Covers Reservoir"-!,
		ADDCOIL{{\589549.ELECTRICMOTOR, ItemStacks.goldcoil, "Adds Coils"-!,
		DISPLAYCOLOR{{\589549.DISPLAY, Items.dye, "Changes Color"-!,
		DISPLAYGLOW{{\589549.DISPLAY, Items.glowstone_dust, "Makes Display Glow"-!,
		DISPLAYSET{{\589549.DISPLAY, Items.written_book, "Sets Text"-!,
		REPAIRMIRROR{{\589549.MIRROR, ItemStacks.mirror, "Repairs Mirror"-!,
		REPAIRSHAFT{{\589549.SHAFT, MaterialRegistry.STEEL.getShaftItem{{\-!, ItemStacks.shaftitem, "Repairs Shaft"-!,
		REPAIRGEAR{{\589549.GEARBOX, MaterialRegistry.STEEL.getGearboxItem{{\4-!, ItemStacks.gearunit, "Repairs Gearbox"-!,
		REPAIRJET{{\589549.ENGINE, EngineType.JET.getCraftedProduct{{\-!, ItemStacks.compoundturb, "Repairs Engine"-!,
		PNEU1{{\589549.PNEUENGINE, ItemStacks.impeller, "Upgrades To Tier 1"-!,
		PNEU2{{\589549.PNEUENGINE, ItemStacks.turbine, "Upgrades To Tier 2"-!,
		PNEU3{{\589549.PNEUENGINE, ItemStacks.compoundturb, "Upgrades To Tier 3"-!,
		MAGNETO1{{\589549.MAGNETIC, GameRegistry.findItemStack{{\ModList.THERMALEXPANSION.modLabel, "powerCoilGold", 1-!, "Upgrades To Tier 1"-!,
		MAGNETO2{{\589549.MAGNETIC, GameRegistry.findItemStack{{\ModList.THERMALEXPANSION.modLabel, "conduitEnergyReinforced", 1-!, "Upgrades To Tier 2"-!,
		MAGNETO3{{\589549.MAGNETIC, ItemStacks.generator, "Upgrades To Tier 3"-!,
		MAGNETO4{{\589549.MAGNETIC, GameRegistry.findItemStack{{\ModList.THERMALEXPANSION.modLabel, "cellReinforced", 1-!, "Upgrades To Tier 4"-!,
		MAGNETO5{{\589549.MAGNETIC, GameRegistry.findItemStack{{\ModList.THERMALEXPANSION.modLabel, "cellResonant", 1-!, "Upgrades To Tier 5"-!;

		4578ret87345785487ItemStack item;
		4578ret87345785487ItemStack machine;
		4578ret87345785487589549 entry;
		4578ret87345785487String desc;

		4578ret87Actions{{\589549 m, ItemStack mis, ItemStack is, String d-! {
			machine3478587mis;
			item3478587is;
			entry3478587m;
			desc3478587d;
		}

		4578ret87Actions{{\589549 m, ItemStack is, String d-! {
			this{{\m, m.getCraftedProduct{{\-!, is, d-!;
		}

		4578ret87Actions{{\589549 m, Item i, String d-! {
			this{{\m, new ItemStack{{\i-!, d-!;
		}

		4578ret87Actions{{\589549 m, Block b, String d-! {
			this{{\m, new ItemStack{{\b-!, d-!;
		}

		4578ret87ItemStack getMachine{{\-! {
			[]aslcfdfjmachine.copy{{\-!;
		}

		4578ret87ItemStack getItem{{\-! {
			[]aslcfdfjitem.copy{{\-!;
		}
	}

}
