/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Models.ModelBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;

public class RenderBreeder extends RotaryTERenderer
{
    
    private ModelBreeder AutoBreederModel = new ModelBreeder();
    
    private TileEntity te;
    //private ModelAutoBreederV AutoBreederModelV = new ModelAutoBreederV();

    /**
     * Renders the TileEntity for the position.
     */
    public void renderTileEntityAutoBreederAt(TileEntityAutoBreeder te, double par2, double par4, double par6, float par8)
    {
    	this.te = te;
        int var9;

        if (!te.isInWorld())
        {
            var9 = 0;
        }
        else
        {
            Block var10 = te.getBlockType();
            var9 = te.getBlockMetadata();

            
            {
                //((BlockAutoBreederBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
                var9 = te.getBlockMetadata();
            }
        }

        if (true)
        {
            ModelBreeder var14;
            var14 = this.AutoBreederModel;
            //ModelAutoBreederV var15;
            //var14 = this.AutoBreederModelV;
            this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/emptybreedertex.png");
            if (ReikaInventoryHelper.checkForItem(Item.wheat.itemID, te.inventory))
            	this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/breedertex.png");            
            GL11.glPushMatrix();
            
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            int var11 = 0;	 //used to rotate the model about metadata
            
            //float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
            float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
           // if (tile.getBlockMetadata() < 4)
            var14.renderAll(ReikaJavaLibrary.makeListFromArray(this.getConditions(te)), 0);
            
            if (te.isInWorld())
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
    {
    	if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
        this.renderTileEntityAutoBreederAt((TileEntityAutoBreeder)tile, par2, par4, par6, par8);
        if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
        	IORenderer.renderIO((TileEntityIOMachine)tile, par2, par4, par6);
    }
    
    private Object[] getConditions(TileEntityAutoBreeder te) {
    	Object[] vals = new Object[5];
    	vals[0] = true;
    	vals[1] = ReikaInventoryHelper.checkForItem(Item.carrot.itemID, te.inventory);
    	vals[2] = ReikaInventoryHelper.checkForItem(Item.porkRaw.itemID, te.inventory);
    	vals[3] = ReikaInventoryHelper.checkForItem(Item.fishRaw.itemID, te.inventory);
    	vals[4] = ReikaInventoryHelper.checkForItem(Item.seeds.itemID, te.inventory);
        return vals;
    }
    
	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityAutoBreeder tb = (TileEntityAutoBreeder)te;
		if (ReikaInventoryHelper.checkForItem(Item.wheat.itemID, tb.inventory))
			return "breedertex.png";
		return "emptybreedertex.png";
	}
}
