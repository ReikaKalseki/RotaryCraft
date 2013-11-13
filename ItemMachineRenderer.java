/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public class ItemMachineRenderer implements IItemRenderer {

	private int Renderid;
	private int metadata;

	public ItemMachineRenderer() {

	}

	public ItemMachineRenderer(int id) {
		Renderid = id;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Map map = EnchantmentHelper.getEnchantments(item);
		boolean enchant = map != null && !map.isEmpty();
		if (Renderid == -1) {
			RenderBlocks rb = new RenderBlocks();
			//ModLoader.getMinecraftInstance().renderEngine.bindTexture("/terrain.png");
			rb.renderBlockAsItem(Block.lockedChest, 0, 1);
			return;
		}
		float a = 0; float b = 0;
		if (item.itemID == RotaryCraft.engineitems.itemID) {
			TileEntityEngine eng = new TileEntityEngine();
			eng.type = EngineType.DC;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(eng, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.gbxitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGearbox(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.advgearitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityAdvancedGear(), a, -0.1D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.flywheelitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFlywheel(), a, 0.0D, b, 500-1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.shaftitems.itemID) {
			if (type == type.ENTITY) {
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				a = -0.5F; b = -0.5F;
			}
			if (item.getItemDamage() == RotaryNames.getNumberShaftTypes()-1)
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -10000F);
			else
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.machineplacer.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F;
				b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			if (item.getItemDamage() >= MachineRegistry.machineList.length)
				return;
			MachineRegistry machine = MachineRegistry.machineList[item.getItemDamage()];
			if (machine.hasModel()) {
				TileEntity te = machine.createTEInstanceForRender();
				if (machine.isEnchantable()) {
					EnchantableMachine em = (EnchantableMachine)te;
					em.applyEnchants(item);
				}
				TileEntityRenderer.instance.renderTileEntityAt(te, a, -0.1D, b, 0.0F);
			}
			else {
				RenderBlocks rb = new RenderBlocks();
				ReikaTextureHelper.bindTerrainTexture();
				if (type == type.EQUIPPED || type == type.EQUIPPED_FIRST_PERSON) {
					double d = 0.5;
					GL11.glTranslated(d, d, d);
				}
				rb.renderBlockAsItem(MachineRegistry.machineList[item.getItemDamage()].getBlockVariable(), MachineRegistry.machineList[item.getItemDamage()].getMachineMetadata(), 1);
			}
		}
		if (enchant) {
			ReikaRenderHelper.disableLighting();
			RenderHelper.enableStandardItemLighting();
		}
	}
}
