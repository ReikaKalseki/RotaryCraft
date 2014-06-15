/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Auxiliary.ReikaSpriteSheets;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public class ItemMachineRenderer implements IItemRenderer {

	private int Renderid;
	private int metadata;

	private final RenderBlocks rb = new RenderBlocks();

	public TileEntity getRenderingInstance(MachineRegistry m, int offset) {
		return m.createTEInstanceForRender(offset);
	}

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
			rb.renderBlockAsItem(Block.lockedChest, 0, 1);
			return;
		}
		float a = 0; float b = 0;
		if (item.itemID == RotaryCraft.engineitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.ENGINE, item.getItemDamage());
			TileEntityEngine eng = (TileEntityEngine)te;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(eng, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.gbxitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.GEARBOX, item.getItemDamage());
			TileEntityGearbox gbx = (TileEntityGearbox)te;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(gbx, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.advgearitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.ADVANCEDGEARS, item.getItemDamage());
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)te;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			if (item.stackTagCompound != null && item.stackTagCompound.getBoolean("bedrock"))
				adv.setBedrock(true);
			else
				adv.setBedrock(false);
			TileEntityRenderer.instance.renderTileEntityAt(adv, a, -0.1D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.flywheelitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.FLYWHEEL, item.getItemDamage());
			TileEntityFlywheel fly = (TileEntityFlywheel)te;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(fly, a, 0.0D, b, 500-1000F*(item.getItemDamage()+1));
		}/*
		else if (item.itemID == RotaryCraft.hydraulicitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.HYDRAULIC);
			TileEntityHydraulicPump hyd = (TileEntityHydraulicPump)te;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(hyd, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}*/
		else if (item.itemID == RotaryCraft.shaftitems.itemID) {
			TileEntity te = this.getRenderingInstance(MachineRegistry.SHAFT, item.getItemDamage());
			TileEntityShaft sha = (TileEntityShaft)te;
			if (type == type.ENTITY) {
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				a = -0.5F; b = -0.5F;
			}
			if (item.getItemDamage() == RotaryNames.getNumberShaftTypes()-1)
				TileEntityRenderer.instance.renderTileEntityAt(sha, a, 0.0D, b, -10000F);
			else
				TileEntityRenderer.instance.renderTileEntityAt(sha, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
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
			if (machine.isPipe()) {
				if (type == type.EQUIPPED || type == type.EQUIPPED_FIRST_PERSON) {
					double d = 0.5;
					GL11.glTranslated(d, d, d);
				}
				rb.renderBlockAsItem(BlockRegistry.PIPING.getBlockVariable(), machine.getMachineMetadata(), 1);
			}
			else if (machine.hasModel()) {
				TileEntity te = this.getRenderingInstance(machine, 0);
				if (machine.isEnchantable()) {
					EnchantableMachine em = (EnchantableMachine)te;
					em.getEnchantments().clear();
					em.applyEnchants(item);
				}
				if (machine.hasNBTVariants()) {
					((NBTMachine)te).setDataFromItemStackTag(item.stackTagCompound);
				}
				TileEntityRenderer.instance.renderTileEntityAt(te, a, -0.1D, b, 0.0F);
			}
			else {
				ReikaTextureHelper.bindTerrainTexture();
				if (type == type.EQUIPPED || type == type.EQUIPPED_FIRST_PERSON) {
					double d = 0.5;
					GL11.glTranslated(d, d, d);
				}
				rb.renderBlockAsItem(MachineRegistry.machineList[item.getItemDamage()].getBlockVariable(), MachineRegistry.machineList[item.getItemDamage()].getMachineMetadata(), 1);
				if (enchant) {
					GL11.glRotated(90, 0, 0, 1);
					ReikaSpriteSheets.renderEffect(type, item);
					GL11.glRotated(-90, 0, 0, 1);
				}
			}
		}
	}
}
