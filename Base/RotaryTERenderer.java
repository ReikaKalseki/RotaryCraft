/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RotaryTERenderer extends TileEntitySpecialRenderer implements TextureFetcher {

	/*
	public RotaryTERenderer(TileEntity tile) {
		te = tile;
	}*/

	BufferedImage tex = null;
	HashMap textures = new HashMap();
	float phi = 0;

    @Override
	public void bindTextureByName(String file) {/*
        RenderEngine renderengine = this.tileEntityRenderer.renderEngine;
        //String filename = RotaryCraft.class.getResource(file).getPath();
        //ReikaJavaLibrary.pConsole(getClass().getClassLoader().getResource(".").getPath());
        String filename;
        if (this.getClass() == null)
        	return;
        if (this.getClass().getClassLoader() == null)
        	return;
        if (this.getClass().getClassLoader().getResource(".") == null)
        	filename = null;
        else {
	        String base = getClass().getClassLoader().getResource(".").getPath();
	        String path = base.substring(1, base.length()-1);
	        filename = path+file;
        }
        //ReikaJavaLibrary.pConsole(filename);
        if (renderengine != null)
        {
        	int img = 0;
        	String name = this.getImageFileName();
        	if (textures.containsKey(name)) {
        		img = (Integer)textures.get(name);
        		//ReikaJavaLibrary.pConsole(68);
        	}
        	else {
        		tex = ReikaPNGLoader.readTextureImage(filename, RotaryAux.tileentdir+name);
            	img = renderengine.allocateAndSetupTexture(tex);
        		textures.put(name, img);
        	}
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, img);
        }*/
    	String filename = file.substring(18, file.length());
    	super.bindTextureByName(file);
    }

    //public abstract String getImageFileName(RotaryCraftTileEntity te);

    public final boolean isValidMachineRenderpass(RotaryCraftTileEntity te) {
    	if (!te.isInWorld())
    		return true;
    	int pass = MinecraftForgeClient.getRenderPass();
    	if (!te.shouldRenderInPass(pass))
    		return false;
    	if (pass == 0)
    		return true;
    	if (pass == 1)
    		return (te.hasModelTransparency());
    	return false;
    }
}
