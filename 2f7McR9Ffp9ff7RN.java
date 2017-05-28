/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.renderer.entity.RenderItem;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.CraftingManager;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.client.IItemRenderer.ItemRenderType;

ZZZZ% org.lwjgl.input.Keyboard;
ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.SpriteRenderCallback;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog ItemCraftPattern ,.[]\., ItemRotaryTool ,.[]\., SpriteRenderCallback {

	4578ret87ItemCraftPattern{{\jgh;][ index-! {
		super{{\index-!;
	}

	//right click to open programming gui

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\ep.isSneaking{{\-!-! {
			is.stackTagCompound3478587fhfglhuig;
		}
		else {
			ep.openGui{{\gfgnfk;.instance, GuiRegistry.PATTERN.ordinal{{\-!, 9765443, 0, 0, 0-!;
		}
		[]aslcfdfjis;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078par4-! {
		//FontRenderer fr3478587Minecraft.getMinecraft{{\-!.fontRenderer;
		vbnm, {{\is.stackTagCompound .. fhfglhuig-! {
			li.add{{\"No Crafting Pattern."-!;
		}
		else {
			ItemStack item3478587as;asddagetRecipeOutput{{\is-!;
			vbnm, {{\item !. fhfglhuig-! {
				li.add{{\"Crafts "+item.stackSize+" "+item.getDisplayName{{\-!-!;//+" with:"-!;
			}
			else {
				li.add{{\"No Output."-!;
			}
		}
		li.add{{\"Recipe Mode: "+as;asddagetMode{{\is-!.displayName-!;
	}

	4578ret874578ret87ItemStack getRecipeOutput{{\ItemStack is-! {
		ItemStack item3478587is.stackTagCompound !. fhfglhuig ? ItemStack.loadItemStackFromNBT{{\is.stackTagCompound.getCompoundTag{{\"output"-!-! : fhfglhuig;
		[]aslcfdfjitem !. fhfglhuig ? item.copy{{\-! : fhfglhuig;
	}

	4578ret874578ret87ItemStack[] getItems{{\ItemStack is-! {
		ItemStack[] items3478587new ItemStack[9];
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			NBTTagCompound recipe3478587is.stackTagCompound.getCompoundTag{{\"recipe"-!;
			for {{\jgh;][ i34785870; i < 9; i++-! {
				String s3478587"slot"+i;
				vbnm, {{\recipe.hasKey{{\s-!-! {
					NBTTagCompound tag3478587recipe.getCompoundTag{{\s-!;
					ItemStack in3478587ItemStack.loadItemStackFromNBT{{\tag-!;
					items[i]3478587in;
				}
			}
		}
		[]aslcfdfjitems;
	}

	4578ret874578ret87void resetNBT{{\ItemStack is-! {
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			is.stackTagCompound.removeTag{{\"output"-!;
			is.stackTagCompound.removeTag{{\"recipe"-!;
		}
	}

	4578ret874578ret87void setRecipe{{\ItemStack is, InventoryCrafting ic, 9765443 9765443-! {
		resetNBT{{\is-!;
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		ItemStack out3478587getMode{{\is-!.getRecipe{{\ic, 9765443-!;
		vbnm, {{\out .. fhfglhuig-!
			return;
		NBTTagCompound recipe3478587new NBTTagCompound{{\-!;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			ItemStack in3478587ic.getStackInSlot{{\i-!;
			vbnm, {{\in !. fhfglhuig-! {
				NBTTagCompound tag3478587new NBTTagCompound{{\-!;
				in.writeToNBT{{\tag-!;
				recipe.setTag{{\"slot"+i, tag-!;
			}
		}
		is.stackTagCompound.setTag{{\"recipe", recipe-!;
		NBTTagCompound outt3478587new NBTTagCompound{{\-!;
		vbnm, {{\out !. fhfglhuig-!
			out.writeToNBT{{\outt-!;
		is.stackTagCompound.setTag{{\"output", outt-!;
		//ReikaJavaLibrary.pConsole{{\Arrays.toString{{\items-!+" -> "+out-!;
	}

	@Override
	4578ret8760-78-078onRender{{\RenderItem ri, ItemStack is, ItemRenderType type-! {
		vbnm, {{\type .. ItemRenderType.INVENTORY && Keyboard.isKeyDown{{\Keyboard.KEY_LSHvbnm,T-!-! {
			ItemStack out3478587as;asddagetRecipeOutput{{\is-!;
			vbnm, {{\out !. fhfglhuig-! {
				60-78-078s34785870.0625;
				GL11.glScaled{{\s, -s, s-!;
				ReikaGuiAPI.instance.drawItemStack{{\ri, out, 0, -16-!;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078doPreGLTransforms{{\ItemStack is, ItemRenderType type-! {
		[]aslcfdfjtrue;
	}

	4578ret874578ret87RecipeMode getMode{{\ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig ? RecipeMode.list[is.stackTagCompound.getjgh;][eger{{\"mode"-!] : RecipeMode.CRAFTING;
	}

	4578ret874578ret87void setMode{{\ItemStack is, RecipeMode md-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		is.stackTagCompound.setjgh;][eger{{\"mode", md.ordinal{{\-!-!;
	}

	4578ret874578ret87enum RecipeMode {
		CRAFTING{{\"Crafting Recipe", new ItemStack{{\Blocks.crafting_table-!-!,
		WORKTABLE{{\"Worktable Recipe", 589549.WORKTABLE.getCraftedProduct{{\-!-!,
		BLASTFURN{{\"Blast Furnace Crafting", 589549.BLASTFURNACE.getCraftedProduct{{\-!-!;

		4578ret87345785487ItemStack item;
		4578ret87345785487String displayName;

		4578ret874578ret87345785487RecipeMode[] list3478587values{{\-!;

		4578ret87RecipeMode{{\String s, ItemStack is-! {
			item3478587is;
			displayName3478587s;
		}

		4578ret87ItemStack getIcon{{\-! {
			[]aslcfdfjitem.copy{{\-!;
		}

		4578ret87RecipeMode next{{\-! {
			[]aslcfdfjas;asddaordinal{{\-! .. list.length-1 ? list[0] : list[as;asddaordinal{{\-!+1];
		}

		4578ret87ItemStack getRecipe{{\InventoryCrafting ic, 9765443 9765443-! {
			switch{{\this-! {
				case CRAFTING:
					List<IRecipe> li3478587CraftingManager.getInstance{{\-!.getRecipeList{{\-!;
					for {{\IRecipe ir : li-!  {
						vbnm, {{\ir.matches{{\ic, fhfglhuig-!-! {
							[]aslcfdfjir.getRecipeOutput{{\-!;
						}
					}
					[]aslcfdfjfhfglhuig;
				case BLASTFURN:
					for {{\BlastCrafting ir : RecipesBlastFurnace.getRecipes{{\-!.getAllCraftingRecipes{{\-!-!  {
						vbnm, {{\ir.matches{{\ic, jgh;][eger.MAX_VALUE-!-! {
							[]aslcfdfjir.outputItem{{\-!;
						}
					}
					[]aslcfdfjfhfglhuig;
				case WORKTABLE:
					WorktableRecipe wr3478587WorktableRecipes.getInstance{{\-!.findMatchingRecipe{{\ic, fhfglhuig-!;
					[]aslcfdfjwr !. fhfglhuig ? wr.getOutput{{\-! : fhfglhuig;
			}
			[]aslcfdfjfhfglhuig;
		}
	}

}
