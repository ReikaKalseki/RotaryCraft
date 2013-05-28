/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.MultiModel;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelGearbox;
import Reika.RotaryCraft.Models.ModelGearbox16;
import Reika.RotaryCraft.Models.ModelGearbox4;
import Reika.RotaryCraft.Models.ModelGearbox8;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;

public class RenderGearbox extends RotaryTERenderer implements MultiModel
{

    private ModelGearbox GearboxModel = new ModelGearbox();
    private ModelGearbox4 GearboxModel4 = new ModelGearbox4();
    private ModelGearbox8 GearboxModel8 = new ModelGearbox8();
    private ModelGearbox16 GearboxModel16 = new ModelGearbox16();
    private int itemMetadata = 0;

    private TileEntity te;

    /**
     * Renders the TileEntity for the position.
     */
    public void renderTileEntityGearboxAt(TileEntityGearbox tile, double par2, double par4, double par6, float par8)
    {
    	te = tile;
        int var9;

        if (!tile.isInWorld())
        {
            var9 = 0;
        }
        else
        {

            var9 = tile.getBlockMetadata();


            {
                //((BlockGearboxBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
                var9 = tile.getBlockMetadata();
            }
        }

        if (true)
        {
            ModelGearbox var14 = GearboxModel;
            ModelGearbox4 var15 = GearboxModel4;
            ModelGearbox8 var16 = GearboxModel8;
            ModelGearbox16 var17 = GearboxModel16;

            this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            int var11 = 0;	 //used to rotate the model about metadata

            if (tile.isInWorld()) {
            	if (tile.type == null)
            		return;
            	switch(tile.type) {
	            	case WOOD:
	            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexw.png");
	            	break;
	            	case STONE:
	            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexs.png");
	            	break;
	            	case STEEL:
	            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
	            	break;
	            	case DIAMOND:
	            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexd.png");
	            	break;
	            	case BEDROCK:
	            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexb.png");
	            	break;
            	}

	            switch(tile.getBlockMetadata()%4) {
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
            	this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
            	switch(itemMetadata) {
            	case 1:
            	case 6:
            	case 11:
            	case 16:
            	case 21:
            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexw.png");
            	break;
            	case 2:
            	case 7:
            	case 12:
            	case 17:
            	case 22:
            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexs.png");
            	break;
            	case 3:
            	case 8:
            	case 13:
            	case 18:
            	case 23:
            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttex.png");
            	break;
            	case 4:
            	case 9:
            	case 14:
            	case 19:
            	case 24:
            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexd.png");
            	break;
            	case 5:
            	case 10:
            	case 15:
            	case 20:
            	case 25:
            		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/shafttexb.png");
            	break;
            	}
            	switch(itemMetadata) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                	var14.renderAll(null, 0);
                break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                	var15.renderAll(null, 0);
                break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                	var16.renderAll(null, 0);
                break;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                	var17.renderAll(null, 0);
                break;
            	}
            	if (tile.isInWorld())
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glPopMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	return;
            }

            //GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            //float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
            float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
            switch(tile.ratio) {
            case 2:
            	var14.renderAll(null, -tile.phi);
            break;
            case 4:
            	var15.renderAll(null, -tile.phi);
            break;
            case 8:
            	var16.renderAll(null, -tile.phi);
            break;
            case 16:
            	var17.renderAll(null, -tile.phi);
            break;
            }
            if (tile.isInWorld())
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
    {
    	if (par8 <= -999F) {
	    	itemMetadata = (int)-par8/1000;
	    	par8 = 0F;
	    	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.itemMetadata));
    	}
    	if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
        this.renderTileEntityGearboxAt((TileEntityGearbox)tile, par2, par4, par6, par8);
        if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
        	IORenderer.renderIO(tile, par2, par4, par6);
    }

	@Override
	public String getImageFileName(RenderFetcher te) {
		if (te == null)
			return null;
		String name;
		TileEntityGearbox tile = (TileEntityGearbox)te;
        if (tile.isInWorld()) {
        	switch(tile.type) {
            	case WOOD:
            		name = "shafttexw.png";
            	break;
            	case STONE:
            		name = "shafttexs.png";
            	break;
            	case STEEL:
            		name = "shafttex.png";
            	break;
            	case DIAMOND:
            		name = "shafttexd.png";
            	break;
            	case BEDROCK:
            		name = "shafttexb.png";
            	break;
            	default:
            		name = null;
        	}

        }
        else {
        	name = "shafttex.png";
        	switch(itemMetadata) {
        	case 1:
        	case 6:
        	case 11:
        	case 16:
        	case 21:
        		name = "shafttexw.png";
        	break;
        	case 2:
        	case 7:
        	case 12:
        	case 17:
        	case 22:
        		name = "shafttexs.png";
        	break;
        	case 3:
        	case 8:
        	case 13:
        	case 18:
        	case 23:
        		name = "shafttex.png";
        	break;
        	case 4:
        	case 9:
        	case 14:
        	case 19:
        	case 24:
        		name = "shafttexd.png";
        	break;
        	case 5:
        	case 10:
        	case 15:
        	case 20:
        	case 25:
        		name = "shafttexb.png";
        	break;
        	default:
        		name = null;
        	}
        }
		return name;
	}
}
