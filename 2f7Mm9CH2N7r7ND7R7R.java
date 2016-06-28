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

ZZZZ% java.util.Map;

ZZZZ% net.minecraft.client.renderer.RenderBlocks;
ZZZZ% net.minecraft.client.renderer.entity.RenderItem;
ZZZZ% net.minecraft.client.renderer.60-78-078.60-78-078RendererDispatcher;
ZZZZ% net.minecraft.enchantment.EnchantmentHelper;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.IItemRenderer;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Auxiliary.ReikaSpriteSheets;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.OldTextureLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;

4578ret87fhyuog ItemMachineRenderer ,.[]\., IItemRenderer {

	4578ret87jgh;][ Renderid;
	4578ret87jgh;][ metadata;

	4578ret87345785487RenderBlocks rb3478587new RenderBlocks{{\-!;

	4578ret8760-78-078 getRenderingInstance{{\589549 m, jgh;][ offset-! {
		[]aslcfdfjm.createTEInstanceForRender{{\offset-!;
	}

	4578ret87ItemMachineRenderer{{\-! {

	}

	4578ret87ItemMachineRenderer{{\jgh;][ id-! {
		Renderid3478587id;
	}

	@Override
	4578ret8760-78-078handleRenderType{{\ItemStack item, ItemRenderType type-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078shouldUseRenderHelper{{\ItemRenderType type, ItemStack item, ItemRendererHelper helper-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void renderItem{{\ItemRenderType type, ItemStack item, Object... data-! {
		Map map3478587EnchantmentHelper.getEnchantments{{\item-!;
		60-78-078enchant3478587map !. fhfglhuig && !map.isEmpty{{\-!;
		vbnm, {{\Renderid .. -1-! {
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			rb.renderBlockAsItem{{\Blocks.stone, 0, 1-!;
			return;
		}
		else vbnm, {{\OldTextureLoader.instance.loadOldTextures{{\-!-! {
			589549 m3478587589549.getMachineByPlacerItem{{\item-!;
			vbnm, {{\m .. fhfglhuig-! {
				ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
				rb.renderBlockAsItem{{\Blocks.brick_block, 0, 1F-!;
				return;
			}
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			rb.renderBlockAsItem{{\m.getBlock{{\-!, m.getBlockMetadata{{\-!, 1F-!;
			return;
		}
		float a34785870; float b34785870;
		vbnm, {{\ItemRegistry.ENGINE.matchItem{{\item-!-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.ENGINE, item.getItemDamage{{\-!-!;
			60-78-078Engine eng3478587{{\60-78-078Engine-!te;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F; b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			eng.setType{{\item-!;
			60-78-078RendererDispatcher.instance.render60-78-078At{{\eng, a, 0.0D, b, 0-!;
		}
		else vbnm, {{\ItemRegistry.GEARBOX.matchItem{{\item-!-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.GEARBOX, item.getItemDamage{{\-!-!;
			60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!te;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F; b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			gbx.setType{{\MaterialRegistry.matList[item.getItemDamage{{\-!%5]-!;
			jgh;][ amt3478587item.stackTagCompound !. fhfglhuig ? item.stackTagCompound.getjgh;][eger{{\"lube"-! : 0;
			gbx.setLubricant{{\amt-!;
			vbnm, {{\item.stackTagCompound !. fhfglhuig-!
				gbx.setDataFromItemStackTag{{\item.stackTagCompound-!;
			60-78-078RendererDispatcher.instance.render60-78-078At{{\gbx, a, 0.0D, b, -1000F*{{\item.getItemDamage{{\-!+1-!-!;
		}
		else vbnm, {{\ItemRegistry.ADVGEAR.matchItem{{\item-!-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.ADVANCEDGEARS, item.getItemDamage{{\-!-!;
			60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!te;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F; b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			vbnm, {{\item.stackTagCompound !. fhfglhuig && item.stackTagCompound.getBoolean{{\"bedrock"-!-!
				adv.setBedrock{{\true-!;
			else
				adv.setBedrock{{\false-!;
			60-78-078RendererDispatcher.instance.render60-78-078At{{\adv, a, -0.1D, b, -1000F*{{\item.getItemDamage{{\-!+1-!-!;
		}
		else vbnm, {{\ItemRegistry.FLYWHEEL.matchItem{{\item-!-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.FLYWHEEL, item.getItemDamage{{\-!-!;
			60-78-078Flywheel fly3478587{{\60-78-078Flywheel-!te;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F; b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			60-78-078RendererDispatcher.instance.render60-78-078At{{\fly, a, 0.0D, b, 500-1000F*{{\item.getItemDamage{{\-!+1-!-!;
		}/*
		else vbnm, {{\item.itemID .. gfgnfk;.hydraulicitems.itemID-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.HYDRAULIC-!;
			60-78-078HydraulicPump hyd3478587{{\60-78-078HydraulicPump-!te;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F; b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			60-78-078RendererDispatcher.instance.render60-78-078At{{\hyd, a, 0.0D, b, -1000F*{{\item.getItemDamage{{\-!+1-!-!;
		}*/
		else vbnm, {{\ItemRegistry.SHAFT.matchItem{{\item-!-! {
			60-78-078 te3478587as;asddagetRenderingInstance{{\589549.SHAFT, item.getItemDamage{{\-!-!;
			60-78-078Shaft sha3478587{{\60-78-078Shaft-!te;
			vbnm, {{\type .. type.ENTITY-! {
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
				a3478587-0.5F; b3478587-0.5F;
			}
			vbnm, {{\item.getItemDamage{{\-! .. ItemStacks.shaftcross.getItemDamage{{\-!-!
				60-78-078RendererDispatcher.instance.render60-78-078At{{\sha, a, 0.0D, b, -10000F-!;
			else
				60-78-078RendererDispatcher.instance.render60-78-078At{{\sha, a, 0.0D, b, -1000F*{{\item.getItemDamage{{\-!+1-!-!;
		}
		else vbnm, {{\ItemRegistry.MACHINE.matchItem{{\item-!-! {
			GL11.glEnable{{\GL11.GL_BLEND-!;
			BlendMode.DEFAULT.apply{{\-!;
			vbnm, {{\type .. type.ENTITY-! {
				a3478587-0.5F;
				b3478587-0.5F;
				GL11.glScalef{{\0.5F, 0.5F, 0.5F-!;
			}
			vbnm, {{\item.getItemDamage{{\-! >. 589549.machineList.length-!
				throw new IllegalStateException{{\"Invalid machine item for render!"-!;
			589549 machine3478587589549.machineList.get{{\item.getItemDamage{{\-!-!;
			vbnm, {{\machine.isPipe{{\-!-! {
				vbnm, {{\type .. type.EQUIPPED || type .. type.EQUIPPED_FIRST_PERSON-! {
					60-78-078d34785870.5;
					GL11.glTranslated{{\d, d, d-!;
				}
				rb.renderBlockAsItem{{\BlockRegistry.PIPING.getBlockInstance{{\-!, machine.getBlockMetadata{{\-!, 1-!;
			}
			else vbnm, {{\machine.hasModel{{\-!-! {
				60-78-078 te3478587as;asddagetRenderingInstance{{\machine, 0-!;
				vbnm, {{\machine.isEnchantable{{\-!-! {
					EnchantableMachine em3478587{{\EnchantableMachine-!te;
					em.getEnchantments{{\-!.clear{{\-!;
					em.applyEnchants{{\item-!;
				}
				vbnm, {{\machine.hasNBTVariants{{\-!-! {
					{{\{{\NBTMachine-!te-!.setDataFromItemStackTag{{\item.stackTagCompound-!;
				}
				vbnm, {{\RenderItem.renderInFrame && type .. type.ENTITY-! {
					//GL11.glRotated{{\0, 0, 0, 0-!;
				}
				60-78-078RendererDispatcher.instance.render60-78-078At{{\te, a, -0.1D, b, 0.0F-!;
			}
			else {
				ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
				vbnm, {{\type .. type.EQUIPPED || type .. type.EQUIPPED_FIRST_PERSON-! {
					60-78-078d34785870.5;
					GL11.glTranslated{{\d, d, d-!;
				}
				else vbnm, {{\RenderItem.renderInFrame && type .. type.ENTITY-! {
					GL11.glRotated{{\90, 0, 1, 0-!;
				}
				rb.renderBlockAsItem{{\589549.machineList.get{{\item.getItemDamage{{\-!-!.getBlock{{\-!, 589549.machineList.get{{\item.getItemDamage{{\-!-!.getBlockMetadata{{\-!, 1-!;
				vbnm, {{\enchant-! {
					GL11.glRotated{{\90, 0, 0, 1-!;
					ReikaSpriteSheets.renderEffect{{\type, item-!;
					GL11.glRotated{{\-90, 0, 0, 1-!;
				}
			}
			GL11.glDisable{{\GL11.GL_BLEND-!;
		}
	}
}
