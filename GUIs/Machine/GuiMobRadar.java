/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;

public class GuiMobRadar extends GuiPowerOnlyMachine
{

	private TileEntityMobRadar radar;

	public static final int UNIT = 4;

	public GuiMobRadar(EntityPlayer p5ep, TileEntityMobRadar te)
	{
		super(new CoreContainer(p5ep, te), te);
		radar = te;
		ySize = 223;
		xSize = 214;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		if (radar.isJammed()) {
			api.renderStatic(7, 16, 206, 215);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		//this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+5+radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+5-radar.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2-radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
		//this.drawRect(j+xSize/2+radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);

		int a = j+7;
		int b = k+16;

		this.drawRadar(j, k);
		fontRendererObj.drawString(radar.getRange()+"m", a+102, b, 0xaaffaa);
		fontRendererObj.drawString((int)(0.63*radar.getRange())+"m", a+102, b+37, 0xaaffaa);
		fontRendererObj.drawString(MathHelper.ceiling_double_int(0.31*radar.getRange())+"m", a+102, b+69, 0xaaffaa);
	}

	private void drawRadar(int a, int b) {
		List<EntityLivingBase> li = radar.getEntities();
		for (EntityLivingBase e : li) {
			int dx = 100+MathHelper.floor_double(100*(e.posX-radar.xCoord-0.5)/radar.getRange());
			int dz = 100+MathHelper.floor_double(100*(e.posZ-radar.zCoord-0.5)/radar.getRange());
			this.drawMobIcon(a+7, b+16, dx, dz, ReikaEntityHelper.getEntityID(e), dx, dz);
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
	protected String getGuiTexture() {
		return "mobradargui";
	}
}
