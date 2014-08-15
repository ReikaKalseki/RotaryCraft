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

import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityParticleEmitter;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class GuiParticle extends GuiNonPoweredMachine {

	private TileEntityParticleEmitter tile;

	private final ArrayList<ReikaParticleHelper> particles = new ArrayList();

	public GuiParticle(EntityPlayer ep, TileEntityParticleEmitter te) {
		super(new OneSlotContainer(ep, te, 28), te);
		tile = te;
		this.ep = ep;
		ySize = 194;

		for (int i = 0; i < ReikaParticleHelper.values().length; i++) {
			ReikaParticleHelper p = ReikaParticleHelper.values()[i];
			if (tile.isParticleValid(p)) {
				particles.add(p);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		String tex = "/Reika/RotaryCraft/Textures/GUI/buttons.png";
		for (int i = 0; i < particles.size(); i++) {
			ReikaParticleHelper p = particles.get(i);
			int dx = (i%6);
			int dy = (i/6);
			int x = j+8+dx*20;
			if (dx >= 3 && dy < 2)
				x += 41;
			if (dy >= 2) {
				dx = (i-12)%8;
				x = j+8+dx*20;
				dy = 2+(i-12)/8;
			}
			buttonList.add(new ImagedGuiButton(i, x, k+19+dy*20, 18, 18, 0, 36, tex, RotaryCraft.class));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		this.bindParticleTexture();
		for (int i = 0; i < particles.size(); i++) {
			ReikaParticleHelper p = particles.get(i);
			int dx = (i%6);
			int dy = (i/6);
			int x = 10+dx*20;
			int y = 21+dy*20;
			if (dx >= 3 && dy < 2)
				x += 41;
			if (dy >= 2) {
				dx = (i-12)%8;
				dy = 2+(i-12)/8;
				x = 10+dx*20;
				y = 21+dy*20;
			}
			int u = 16*(p.ordinal()%16);
			int v = 16*(p.ordinal()/16);
			this.drawTexturedModalRect(x, y, u, v, 16, 16);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		this.initGui();
		if (button.id < 24000) {
			ReikaParticleHelper p = particles.get(button.id);
			int particle = p.ordinal();
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.PARTICLES.getMinValue(), tile, particle);
		}
	}

	@Override
	public String getGuiTexture() {
		return "particlegui";
	}

	private void bindParticleTexture() {
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/particles.png");
	}



}