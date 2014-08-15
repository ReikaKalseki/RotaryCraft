/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.PacketRegistry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiSlide extends GuiScreen {

	private GuiTextField input;

	private String file;

	private final int xSize = 225;
	private final int ySize = 48;

	public GuiSlide(ItemStack in) {
		if (in.stackTagCompound != null)
			file = in.stackTagCompound.getString("file");
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		input = new GuiTextField(fontRendererObj, j-2, k+31, xSize-16, 16);
		input.setMaxStringLength(128);
		input.setFocused(false);
		input.setText(file);
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		int x = Mouse.getX();
		int y = Mouse.getY();
		if (input.getText().isEmpty()) {
			return;
		}
		else
			file = input.getText();
		ReikaPacketHelper.sendStringPacket(RotaryCraft.packetChannel, PacketRegistry.SLIDE.getMinValue(), file);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		String var4 = "/Reika/RotaryCraft/Textures/GUI/slidegui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		input.drawTextBox();

		if (!input.isFocused()) {
			int d = input.getCursorPosition();
			//fontRendererObj.drawStringWithShadow(file.substring(d, Math.min(file.length(), 37+d)), posX+10, posY+ySize-15, 0xaaaaaa);
		}
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, "Select an image file. Be sure to include", posX+xSize/2+1, posY+4, 4210752);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, "C:/ and file extension and use \"/\", not \"\\\".", posX+xSize/2+1, posY+14, 4210752);
		super.drawScreen(x, y, f);
	}

}