/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntitySpyCam;

public class GuiSpyCam extends GuiNonPoweredMachine
{

	private TileEntitySpyCam cam;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	public static final int UNIT = 4;
	private int direction;

	public GuiSpyCam(EntityPlayer p5ep, TileEntitySpyCam spycam) {
		super(new CoreContainer(p5ep, spycam), spycam);
		cam = spycam;
		ySize = 222;
		xSize = 222;
		ep = p5ep;
		direction = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (direction > 3)
			direction -= 4;
		while (direction < 0)
			direction += 4;
	}

	@Override
	public boolean labelInventory() {
		return false;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		//this.drawRect(j+xSize/2-1-cam.getRange()*UNIT, k+ySize/2+5+cam.getRange()*UNIT, j+xSize/2+1+cam.getRange()*UNIT, k+ySize/2+6+cam.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2-1-cam.getRange()*UNIT, k+ySize/2+4-cam.getRange()*UNIT, j+xSize/2+1+cam.getRange()*UNIT, k+ySize/2+5-cam.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2-1-cam.getRange()*UNIT, k+ySize/2+4-cam.getRange()*UNIT, j+xSize/2-cam.getRange()*UNIT, k+ySize/2+6+cam.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2+cam.getRange()*UNIT, k+ySize/2+4-cam.getRange()*UNIT, j+xSize/2+1+cam.getRange()*UNIT, k+ySize/2+6+cam.getRange()*UNIT, 0xffffffff);

		this.drawRadar(j, k);
	}

	private void drawRadar(int a, int b) {
		int max = cam.getBounds()[1]*UNIT;
		for (int i = cam.getBounds()[0]; i <= cam.getBounds()[1]; i++) {
			for (int j = cam.getBounds()[0]; j <= cam.getBounds()[1]; j++) {
				float br = 1-(cam.yCoord - cam.getHeightAt(i, j))/(float)cam.yCoord*1.25F;
				if (br < 0)
					br = 0;
				this.drawRect(a+17+max-(UNIT*j), b+19+UNIT*i, a+17+max-(UNIT+UNIT*j), b+19+UNIT*i+UNIT, ReikaColorAPI.getColorWithBrightnessMultiplier(cam.getTopBlockAt(i, j), br));
			}
		}
		for (int i = cam.getBounds()[0]; i <= cam.getBounds()[1]; i++) {
			for (int j = cam.getBounds()[0]; j <= cam.getBounds()[1]; j++) {
				this.drawMobIcon(a+13, b+19, max-(UNIT*i), UNIT*j, cam.getMobAt(i, j), i, j);
			}
		}
	}

	private void drawMobIcon(int a, int b, int px, int py, int id, int i, int j) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		int v = 2*UNIT*(id/16);
		int u = 2*UNIT*(id-(v/UNIT/2)*16);
		if (id == -1) {
			u = 2*UNIT;
			v = 0;
		}
		this.drawTexturedModalRect(a+px-UNIT/2, b+py-UNIT/2, u, v, UNIT*2, UNIT*2);
	}


	@Override
	public String getGuiTexture() {
		return "spycamgui";
	}
}
