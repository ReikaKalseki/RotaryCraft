/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.Animated.ModelGearbox;
import Reika.RotaryCraft.Models.Animated.ModelGearbox16;
import Reika.RotaryCraft.Models.Animated.ModelGearbox4;
import Reika.RotaryCraft.Models.Animated.ModelGearbox8;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

public class RenderGearbox extends RotaryTERenderer
{

	private ModelGearbox GearboxModel = new ModelGearbox();
	private ModelGearbox4 GearboxModel4 = new ModelGearbox4();
	private ModelGearbox8 GearboxModel8 = new ModelGearbox8();
	private ModelGearbox16 GearboxModel16 = new ModelGearbox16();
	private int itemMetadata = 0;

	private static Field manaIcon;

	static {
		if (ModList.BOTANIA.isLoaded()) {
			try {
				Class c = Class.forName("vazkii.botania.common.block.mana.BlockPool");
				manaIcon = c.getField("manaIcon");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static IIcon getManaIcon() {
		IIcon ret = null;
		try {
			ret = (IIcon)manaIcon.get(null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (ret == null)
			ret = Blocks.water.getIcon(0, 0);
		return ret;
	}

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityGearboxAt(TileEntityGearbox tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelGearbox var14 = GearboxModel;
		ModelGearbox4 var15 = GearboxModel4;
		ModelGearbox8 var16 = GearboxModel8;
		ModelGearbox16 var17 = GearboxModel16;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartex.png");

		this.setupGL(tile, par2, par4, par6);

		int var11 = 0;	 //used to rotate the model about metadata
		if (tile.isInWorld()) {
			switch(tile.getGearboxType()) {
				case WOOD:
					if (tile.isLiving())
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexw_living.png");
					else
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexw.png");
					break;
				case STONE:
					if (tile.isLiving())
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexs_living.png");
					else
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexs.png");
					break;
				case STEEL:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartex.png");
					break;
				case DIAMOND:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexd.png");
					break;
				case BEDROCK:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexb.png");
					break;
			}

			switch(tile.getBlockMetadata()&3) {
				case 0:
					var11 = 0;
					break;
				case 1:
					var11 = 180;
					break;
				case 2:
					var11 = 90;
					break;
				case 3:
					var11 = 270;
					break;
			}
			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);

		}
		else {
			//ReikaChatHelper.write(this.itemMetadata);
			GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartex.png");
			switch(itemMetadata) {
				case 1:
				case 6:
				case 11:
				case 16:
				case 21:
					if (tile.isLiving())
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexw_living.png");
					else
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexw.png");
					break;
				case 2:
				case 7:
				case 12:
				case 17:
				case 22:
					if (tile.isLiving())
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexs_living.png");
					else
						this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexs.png");
					break;
				case 3:
				case 8:
				case 13:
				case 18:
				case 23:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartex.png");
					break;
				case 4:
				case 9:
				case 14:
				case 19:
				case 24:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexd.png");
					break;
				case 5:
				case 10:
				case 15:
				case 20:
				case 25:
					this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/geartexb.png");
					break;
			}
			switch(itemMetadata) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					var14.renderAll(tile, null, 0, 0);
					break;
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					var15.renderAll(tile, null, 0, 0);
					break;
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
					var16.renderAll(tile, null, 0, 0);
					break;
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
					var17.renderAll(tile, null, 0, 0);
					break;
			}

			this.closeGL(tile);
			return;
		}

		switch(tile.getRatio()) {
			case 2:
				var14.renderAll(tile, null, -tile.phi, 0);
				break;
			case 4:
				var15.renderAll(tile, null, -tile.phi, 0);
				break;
			case 8:
				var16.renderAll(tile, null, -tile.phi, 0);
				break;
			case 16:
				var17.renderAll(tile, null, -tile.phi, 0);
				break;
		}

		this.closeGL(tile);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (par8 <= -999F) {
			itemMetadata = (int)-par8/1000;
			par8 = 0F;
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.itemMetadata));
		}
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityGearboxAt((TileEntityGearbox)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderLiquid(tile, par2, par4, par6);
			//this.renderMode((TileEntityGearbox)tile, par2, par4, par6);
		}
		if (!tile.hasWorldObj()) {
			this.renderLiquid(tile, par2, par4, par6);
		}
	}

	private void renderMode(TileEntityGearbox tile, double par2, double par4, double par6) {
		ItemStack is = Minecraft.getMinecraft().thePlayer.getCurrentArmor(3);
		boolean flag = ReikaItemHelper.matchStacks(is, ItemRegistry.IOGOGGLES.getStackOf());
		if (flag) {
			int var11 = 0;
			switch(tile.getBlockMetadata()&3) {
				case 0:
					var11 = 0;
					break;
				case 1:
					var11 = 180;
					break;
				case 2:
					var11 = 90;
					break;
				case 3:
					var11 = 270;
					break;
			}
			GL11.glTranslated(par2, par4, par6);
			double sc = 0.1;
			GL11.glScaled(sc, sc, sc);
			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			String s = tile.reduction ? "Torque" : "Speed";
			Minecraft.getMinecraft().fontRenderer.drawString(s, 0, 0, 0xffffff);
			GL11.glScaled(1/sc, 1/sc, 1/sc);
			GL11.glTranslated(-par2, -par4, -par6);
		}
	}

	private void renderLiquid(TileEntity tile, double par2, double par4, double par6) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glTranslated(par2, par4, par6);
		TileEntityGearbox tr = (TileEntityGearbox)tile;
		if (tr.getLubricant() > 0) {
			Fluid f = FluidRegistry.getFluid("rc lubricant");
			ReikaLiquidRenderer.bindFluidTexture(f);
			GL11.glEnable(GL11.GL_BLEND);
			IIcon ico = f.getIcon();
			int c = 0xffffff;
			if (tr.isLiving()) {
				ico = this.getManaIcon();
				float t = tr.getTicksExisted()+ReikaRenderHelper.getPartialTickTime();
				c = ReikaColorAPI.getModifiedHue(0x0000ff, 192+(int)(32*Math.sin(t/16D)));
			}
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			double h = 0.0625+(4D/16D*tr.getLubricant()/tr.getMaxLubricant())*0.9;
			if (tr.isFlipped) {
				h = 1-h;
				GL11.glDisable(GL11.GL_CULL_FACE);
			}
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			v5.setColorOpaque_I(c);
			v5.setNormal(0, 1, 0);
			v5.addVertexWithUV(0.0625, h, 0.9375, u, dv);
			v5.addVertexWithUV(0.9375, h, 0.9375, du, dv);
			v5.addVertexWithUV(0.9375, h, 0.0625, du, v);
			v5.addVertexWithUV(0.0625, h, 0.0625, u, v);

			if (tr.isFlipped) {
				ico = Blocks.glass.getIcon(0, 0);
				u = ico.getMinU();
				v = ico.getMinV();
				du = ico.getMaxU();
				dv = ico.getMaxV();
				double o = 0.005;
				v5.addVertexWithUV(0.0625, h-o, 0.9375, u, dv);
				v5.addVertexWithUV(0.9375, h-o, 0.9375, du, dv);
				v5.addVertexWithUV(0.9375, h-o, 0.0625, du, v);
				v5.addVertexWithUV(0.0625, h-o, 0.0625, u, v);
			}
			v5.draw();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		if (te == null)
			return null;
		String name;
		TileEntityGearbox tile = (TileEntityGearbox)te;
		if (tile.isInWorld()) {
			switch(tile.getGearboxType()) {
				case WOOD:
					name = "geartexw.png";
					break;
				case STONE:
					name = "geartexs.png";
					break;
				case STEEL:
					name = "geartex.png";
					break;
				case DIAMOND:
					name = "geartexd.png";
					break;
				case BEDROCK:
					name = "geartexb.png";
					break;
				default:
					name = null;
			}

		}
		else {
			name = "geartex.png";
			switch(itemMetadata) {
				case 1:
				case 6:
				case 11:
				case 16:
				case 21:
					name = "geartexw.png";
					break;
				case 2:
				case 7:
				case 12:
				case 17:
				case 22:
					name = "geartexs.png";
					break;
				case 3:
				case 8:
				case 13:
				case 18:
				case 23:
					name = "geartex.png";
					break;
				case 4:
				case 9:
				case 14:
				case 19:
				case 24:
					name = "geartexd.png";
					break;
				case 5:
				case 10:
				case 15:
				case 20:
				case 25:
					name = "geartexb.png";
					break;
				default:
					name = null;
			}
		}
		return name;
	}
}
