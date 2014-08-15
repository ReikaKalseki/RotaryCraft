/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFuelConverter extends RotaryTERenderer
{

	private ModelFuelConverter FuelConverterModel = new ModelFuelConverter();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityFuelConverterAt(TileEntityFuelConverter tile, double par2, double par4, double par6, float par8)
	{
		ModelFuelConverter var14;
		var14 = FuelConverterModel;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/fuelconverttex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;
		float var13;


		var14.renderAll(tile, null, -tile.phi, 0);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityFuelConverterAt((TileEntityFuelConverter)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderFuels((TileEntityFuelConverter)tile, par2, par4, par6, ((TileEntityFuelConverter)tile).BC_FUEL);
			this.renderFuels((TileEntityFuelConverter)tile, par2, par4, par6, ((TileEntityFuelConverter)tile).JET_FUEL);
		}
	}

	private void renderFuels(TileEntityFuelConverter tile, double par2, double par4, double par6, Fluid fuel) {

		FluidStack liquid = new FluidStack(fuel, 1);

		int amount = tile.getFuel(liquid);
		if (amount == 0)
			return;

		int[] displayList = ReikaLiquidRenderer.getGLLists(liquid, tile.worldObj, false);

		if (displayList == null) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.DEFAULT.apply();

		ReikaLiquidRenderer.bindFluidTexture(fuel);
		ReikaLiquidRenderer.setFluidColor(liquid);

		GL11.glTranslated(par2, par4, par6);

		GL11.glTranslated(0, tile.getLiquidModelOffset(liquid), 0);

		GL11.glTranslated(0, 0.001, 0);
		GL11.glScaled(1, 1/3D, 1);
		GL11.glScaled(0.99, 0.95, 0.99);

		GL11.glCallList(displayList[(int)(amount / ((double)tile.CAPACITY) * (ReikaLiquidRenderer.LEVELS - 1))]);

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "fuelconverttex.png";
	}
}