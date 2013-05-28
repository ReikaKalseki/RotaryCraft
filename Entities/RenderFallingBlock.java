/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.ModLoader;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaRenderHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Models.ModelBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFallingBlock extends Render
{
	ModelBlock blockModel = new ModelBlock();

	public RenderFallingBlock()
	{
		shadowSize = 0.5F;
	}

	/**
	 * The actual render method that is used in doRender
	 */
	public void doRenderFallingBlock(EntityFallingBlock par1EntityFallingBlock, double par2, double par4, double par6, float par8, float par9)
	{
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("DF");
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);

		//-------------------TEXTURE LOADING-----------------------//

		if (par1EntityFallingBlock.metadata == 1)
			this.loadTexture("/Reika/RotaryCraft/Textures/Entity/spruceleaf.png");
		if (par1EntityFallingBlock.metadata == 0 || par1EntityFallingBlock.metadata == 2)
			this.loadTexture("/Reika/RotaryCraft/Textures/Entity/oakleaf.png");
		if (par1EntityFallingBlock.metadata == 3)
			this.loadTexture("/Reika/RotaryCraft/Textures/Entity/jungleleaf.png");
		/* this.loadTexture("/Reika/RotaryCraft/Entity/oaklog.png");
        this.loadTexture("/Reika/RotaryCraft/Entity/sprucelog.png");
        this.loadTexture("/Reika/RotaryCraft/Entity/birchlog.png");
        this.loadTexture("/Reika/RotaryCraft/Entity/junglelog.png");
        this.loadTexture("/Reika/RotaryCraft/Entity/logtop.png");*/



		Block var10 = null;
		if (par1EntityFallingBlock.blockID == Block.leaves.blockID)
			var10 = Block.blocksList[Block.leaves.blockID];
		if (par1EntityFallingBlock.blockID == Block.wood.blockID)
			var10 = Block.blocksList[Block.wood.blockID];
		if (par1EntityFallingBlock.blockID == RotaryCraft.gravleaves.blockID)
			var10 = RotaryCraft.gravleaves;
		if (par1EntityFallingBlock.blockID == RotaryCraft.gravlog.blockID)
			var10 = RotaryCraft.gravlog;
		World var11 = par1EntityFallingBlock.getWorld();
		//GL11.glDisable(GL11.GL_LIGHTING);
		//Tessellator var12;
		GL11.glEnable(GL11.GL_BLEND);

		float red = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 0);
		float green = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 1);
		float blue = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 2);

		if (par1EntityFallingBlock.blockID == Block.leaves.blockID || par1EntityFallingBlock.blockID == RotaryCraft.gravleaves.blockID)
			if (par1EntityFallingBlock.metadata == 0 || par1EntityFallingBlock.metadata == 3) //Spruce and Birch are immune to biome color
				;
			else if (par1EntityFallingBlock.metadata == 1) { //Spruce
				red = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorPine(), 0);
				green = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorPine(), 1);
				blue = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorPine(), 2);
			}
			else if (par1EntityFallingBlock.metadata == 2) { //Birch
				red = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorBirch(), 0);
				green = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorBirch(), 1);
				blue = ReikaRenderHelper.HextoColorMultiplier(ColorizerFoliage.getFoliageColorBirch(), 2);
			}

		GL11.glColor4f(red*1F, green*1F, blue*1F, 1F);

		GL11.glTranslatef(0, -1, 0);

		if (var10 == null)
			return;

		blockModel.render(par1EntityFallingBlock, 0F, 0F, 0F, 0F, 0F, 0.0625F);

		// GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		/*

        //ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(var10)+String.format("%d", par1EntityFallingBlock.blockID));
        if (var10 != null)
        {
        	int[] color = {255, 255, 255};
        	float red = 1;
        	float green = 1;
        	float blue = 1;
        	int hexcolor = var10.getBlockColor();
        	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("DF3");
            this.sandRenderBlocks.setRenderBoundsFromBlock(var10);
            if (var10.blockID == Block.leaves.blockID || var10.blockID == RotaryCraft.gravleaves.blockID) {/*
            	this.sandRenderBlocks.colorRedBottomLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 0);
            	this.sandRenderBlocks.colorRedBottomRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 0);
            	this.sandRenderBlocks.colorGreenBottomLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 1);
            	this.sandRenderBlocks.colorGreenBottomRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 1);
            	this.sandRenderBlocks.colorBlueBottomLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 2);
            	this.sandRenderBlocks.colorBlueBottomRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 2);

            	this.sandRenderBlocks.colorRedTopLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 0);
            	this.sandRenderBlocks.colorRedTopRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 0);
            	this.sandRenderBlocks.colorGreenTopLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 1);
            	this.sandRenderBlocks.colorGreenTopRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 1);
            	this.sandRenderBlocks.colorBlueTopLeft = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 2);
            	this.sandRenderBlocks.colorBlueTopRight = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par2, (int)par4, "Leaves", 2);

            	ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%f %f %f %f %f %f", this.sandRenderBlocks.colorRedBottomLeft, this.sandRenderBlocks.colorRedBottomRight, this.sandRenderBlocks.colorGreenBottomLeft, this.sandRenderBlocks.colorGreenBottomRight, this.sandRenderBlocks.colorBlueBottomLeft, this.sandRenderBlocks.colorBlueBottomRight));
            	ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%f %f %f %f %f %f", this.sandRenderBlocks.colorRedTopLeft, this.sandRenderBlocks.colorRedTopRight, this.sandRenderBlocks.colorGreenTopLeft, this.sandRenderBlocks.colorGreenTopRight, this.sandRenderBlocks.colorBlueTopLeft, this.sandRenderBlocks.colorBlueTopRight));
		 *//*

            color = ReikaWorldHelper.biomeToRGB(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves");
            red = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 0);
            green = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 1);
            blue = ReikaRenderHelper.biomeToColorMultiplier(par1EntityFallingBlock.worldObj, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posZ, "Leaves", 2);
            }
            this.sandRenderBlocks.renderBlockSandFalling(var10, var11, MathHelper.floor_double(par1EntityFallingBlock.posX), MathHelper.floor_double(par1EntityFallingBlock.posY), MathHelper.floor_double(par1EntityFallingBlock.posZ), par1EntityFallingBlock.metadata);
            //this.sandRenderBlocks.renderStandardBlockWithColorMultiplier(var10, (int)par1EntityFallingBlock.posX, (int)par1EntityFallingBlock.posY, (int)par1EntityFallingBlock.posZ, red, green, blue);
            //this.customRender.renderBlockSandFalling(var10, var11, MathHelper.floor_double(par1EntityFallingBlock.posX), MathHelper.floor_double(par1EntityFallingBlock.posY), MathHelper.floor_double(par1EntityFallingBlock.posZ), par1EntityFallingBlock.metadata);
        }*/

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
	}


	/**
	 * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
	 */
	protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3)
	{
		Minecraft.getMinecraft().thePlayer.addChatMessage("RenderColor!");
		return 0xff00ff;
	}

	protected int func_77061_b(EntityFallingBlock par1Entity, int par2, float par3)
	{
		return -1;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
	{
		Minecraft.getMinecraft().thePlayer.addChatMessage("RenderCall!");
		// this.updateCreeperScale((EntityCreeper)par1EntityLiving, par2);
		this.getColorMultiplier(par1EntityLiving, 1F, par2);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderFallingBlock((EntityFallingBlock)par1Entity, par2, par4, par6, par8, par9);
	}
}
