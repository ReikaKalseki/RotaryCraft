/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.IO.ReikaMIDIReader;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;

public class GuiMusic extends GuiNonPoweredMachine
{
    private GuiTextField input;
    private GuiTextField input2;
    private GuiTextField input3;

    private int voice = 1;
    private int noteLength = 0;
    private int volume = 100;
    private int channel = 0;
    private int addednote;

    private static final int NOTESTART = 11;
    private static final int LENGTHSTART = 6;
    private static final int ROWWIDTH = 12;
    private static final int ROWHEIGHT = 5;

    private TileEntityMusicBox music;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiMusic(EntityPlayer player, TileEntityMusicBox MusicBox)
    {
        super(new CoreContainer(player, MusicBox), MusicBox);
        music = MusicBox;
    	ySize = 217;
    	xSize = 256;
    	this.player = player;
    	noteLength = music.getCurrentNoteType();
    	int[] entryData = music.getCurrentEntryData();
    	channel = entryData[0];
    	voice = entryData[1];
    	volume = entryData[2];
    }

    @Override
    public void initGui() {
    	buttonList.clear();
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
        String note = "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png";
        String put = "/Reika/RotaryCraft/Textures/GUI/buttons.png";
        int[] offset = new int[5];
        if (!ReikaMathLibrary.isValueInsideBoundsIncl(0, 4, noteLength))
        	noteLength = 0;
        offset[noteLength] = 80;
        buttonList.add(new ImagedGuiButton(LENGTHSTART, j+xSize/2-52, -1+k+30, 16, 16, "", 0+offset[0], 32, 0, false, note));
        buttonList.add(new ImagedGuiButton(LENGTHSTART+1, j+xSize/2-36, -1+k+30, 16, 16, "", 16+offset[1], 32, 0, false, note));
        buttonList.add(new ImagedGuiButton(LENGTHSTART+2, j+xSize/2-20, -1+k+30, 16, 16, "", 32+offset[2], 32, 0, false, note));
        buttonList.add(new ImagedGuiButton(LENGTHSTART+3, j+xSize/2-4, -1+k+30, 16, 16, "", 48+offset[3], 32, 0, false, note));
        buttonList.add(new ImagedGuiButton(LENGTHSTART+4, j+xSize/2+12, -1+k+30, 16, 16, "", 64+offset[4], 32, 0, false, note));

        for (int i = 0; i < ROWWIDTH; i++) {
        	for (int m = 0; m < ROWHEIGHT; m++) {
        		int u = 0;
        		int color = 0;
        		if (ReikaMIDIReader.NOTE_NAMES[i%12].contains("#") || ReikaMIDIReader.NOTE_NAMES[i%12].contains("b")) {
        			u = 18;
        			color = 0xffffff;
        		}
        		buttonList.add(new ImagedGuiButton(NOTESTART+i+ROWWIDTH*m, 11+j+i*18, -36+k+ySize/2+m*18+8*m, 18, 18, ReikaMIDIReader.NOTE_NAMES[i%12], u, 18, color, false, put));
        	}
        }

        buttonList.add(new GuiButton(1, j+xSize/2-117, k+ySize-16, 64, 20, "Backspace"));
        buttonList.add(new GuiButton(3, j+xSize/2-47, k+ySize-16, 76, 20, "Clear Channel"));
        buttonList.add(new GuiButton(2, j+xSize/2+35, k+ySize-16, 64, 20, "Clear All"));
        buttonList.add(new GuiButton(0, j+xSize-33, k+17, 20, 20, "X"));
        buttonList.add(new GuiButton(4, j-3, k+17, 36, 20, "Save"));
        buttonList.add(new GuiButton(5, j-3+35, k+17, 36, 20, "Read"));

	    input = new GuiTextField(fontRenderer, j+xSize/2-83, k+49, 26, 16);
	    input.setFocused(false);
	    input.setMaxStringLength(3);
	    input2 = new GuiTextField(fontRenderer, j+xSize/2-3, k+49, 26, 16);
	    input2.setFocused(false);
	    input2.setMaxStringLength(3);
	    input3 = new GuiTextField(fontRenderer, j+xSize/2+77, k+49, 26, 16);
	    input3.setFocused(false);
	    input3.setMaxStringLength(3);
    }

    @Override
	public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	input.textboxKeyTyped(c, i);
    	input2.textboxKeyTyped(c, i);
    	input3.textboxKeyTyped(c, i);
    }

    @Override
	public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	input.mouseClicked(i, j, k);
    	input2.mouseClicked(i, j, k);
    	input3.mouseClicked(i, j, k);
    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	boolean valid1 = true;
    	boolean valid2 = true;
    	boolean valid3 = true;
    	x = Mouse.getX();
    	y = Mouse.getY();
	    if (input.getText().isEmpty() && input2.getText().isEmpty() && input3.getText().isEmpty()) {
	    	return;
	    }
	    if (input.getText().isEmpty())
	    	valid1 = false;
	    if (input2.getText().isEmpty())
	    	valid2 = false;
	    if (input3.getText().isEmpty())
	    	valid3 = false;
	    if (!input.getText().isEmpty() && !(input.getText().matches("^[0-9 ]+$"))) {
	    	channel = 0;
	    	input.deleteFromCursor(-1);
	    	valid1 = false;
	    }
	    if (!input2.getText().isEmpty() && !(input2.getText().matches("^[0-9 ]+$"))) {
	    	voice = 0;
	    	input2.deleteFromCursor(-1);
	    	valid2 = false;
	    }
	    if (!input3.getText().isEmpty() && !(input3.getText().matches("^[0-9 ]+$"))) {
	    	volume = 0;
	    	input3.deleteFromCursor(-1);
	    	valid2 = false;
	    }
	    if (!valid1 && !valid2 && !valid3)
	    	return;
	    //ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
	    //System.out.println(input.getText());
	    if (valid1) {
		    channel = Integer.parseInt(input.getText());
		}
	    if (valid2) {
		    voice = Integer.parseInt(input2.getText());
	    }
	    if (valid3) {
		    volume = Integer.parseInt(input3.getText());
	    }
    }

    @Override
	public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void actionPerformed(GuiButton button) {
    	if (ReikaMathLibrary.isValueInsideBoundsIncl(LENGTHSTART, LENGTHSTART+4, button.id)) {
    		noteLength = button.id-LENGTHSTART;
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 21, music, player, noteLength, 0, 0, 0);
        	this.initGui();
    	}
    	if (button.id == 0)
    		player.closeScreen();
    	if (button.id >= NOTESTART) {
    		this.addNote(button.id);
    	}
    	if (button.id == 1) {
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 23, music, player, channel, voice, addednote, volume);
    	}
       	if (button.id == 2) {
       		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 24, music, player, channel, voice, addednote, volume);
    	}
       	if (button.id == 3) {
       		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 25, music, player, channel, 0, 0, 0);
    	}
       	if (button.id == 4) {
       		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 26, music, player, 0, 0, 0, 0);
    	}
       	if (button.id == 5) {
       		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 27, music, player, 0, 0, 0, 0);
    	}
    }

    private void addNote(int note) {
    	note -= NOTESTART;
    	addednote = note;
    	ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 22, music, player, channel, voice, addednote, volume);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Music Box", j+xSize/2-27, k+5, 4210752);

        fontRenderer.drawString("Channel", j+xSize/2-118, k+41, 4210752);
        fontRenderer.drawString("Voice", j+xSize/2-24, k+41, 4210752);
        fontRenderer.drawString("Volume", j+xSize/2+49, k+41, 4210752);
        fontRenderer.drawString("%", j+xSize/2+114, k+41, 4210752);

        if (!input.isFocused())
        	fontRenderer.drawString(String.format("%d", channel), j+57, k+41, 0xffffffff);
        if (!input2.isFocused())
        	fontRenderer.drawString(String.format("%d", voice), j+137, k+41, 0xffffffff);
        if (!input3.isFocused())
        	fontRenderer.drawString(String.format("%d", volume), j+217, k+41, 0xffffffff);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/musicgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

	    input.drawTextBox();
	    input2.drawTextBox();
	    input3.drawTextBox();

    }
}
