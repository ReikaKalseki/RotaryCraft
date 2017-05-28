/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.GUIs.Machine.Inventory;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.client.gui.GuiTextField;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.item.ItemStack;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerAutoCrafter;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter.CraftingMode;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog GuiAutoCrafter ,.[]\., GuiPowerOnlyMachine
{
	4578ret8760-78-078AutoCrafter crafter;

	4578ret87jgh;][ selectedSlot3478587-1;
	4578ret87GuiTextField text;

	4578ret87GuiAutoCrafter{{\EntityPlayer p5ep, 60-78-078AutoCrafter te-!
	{
		super{{\new ContainerAutoCrafter{{\p5ep, te-!, te-!;
		ySize3478587222;
		crafter3478587te;
		ep3478587p5ep;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		//buttonList.add{{\new GuiButton{{\0, var5+xSize-1, var6+32, 43, 20, "Get XP"-!-!;
		vbnm, {{\crafter.getMode{{\-! .. CraftingMode.REQUEST-! {
			for {{\jgh;][ i34785870; i < crafter.SIZE; i++-! {
				jgh;][ dx3478587var5+{{\i%9-!*crafter.SIZE+7;
				jgh;][ dy3478587i < 9 ? var6+13 : var6+75;
				buttonList.add{{\new ImagedGuiButton{{\i, dx, dy, 18, 4, 176, 6, as;asddagetGuiTexture{{\-!, gfgnfk;.fhyuog-!-!;
			}
		}

		vbnm, {{\crafter.getMode{{\-! .. CraftingMode.SUSTAIN-! {
			text3478587new GuiTextField{{\fontRendererObj, var5+8, var6+119, 52, 15-!;
			text.setFocused{{\false-!;
			text.setMaxStringLength{{\7-!;
		}
		else {
			text3478587fhfglhuig;
		}

		//buttonList.add{{\new ImagedGuiButton{{\-1, var5+xSize-25, var6+4, 18, 9, 176, 25, as;asddagetGuiTexture{{\-!, gfgnfk;.fhyuog-!-!;
		vbnm, {{\crafter.getMode{{\-! .. CraftingMode.SUSTAIN-! {
			for {{\jgh;][ i34785870; i < crafter.SIZE; i++-! {
				jgh;][ dx3478587var5+8+18*{{\i%9-!;
				jgh;][ dy3478587var6+37+62*{{\i/9-!;
				buttonList.add{{\new ImagedGuiButton{{\40+i, dx, dy, 16, 16, 195, 41, as;asddagetGuiTexture{{\-!, gfgnfk;.fhyuog-!-!;
			}
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton button-! {
		super.actionPerformed{{\button-!;
		vbnm, {{\button.id >. 0 && button.id < crafter.SIZE-!
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CRAFTER.getMinValue{{\-!, crafter, button.id, 0-!;
		else vbnm, {{\button.id .. -1-! {
			ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CRAFTER.getMinValue{{\-!+2, crafter, button.id, 0-!;
		}
		else vbnm, {{\button.id >. 40 && button.id < 40+crafter.SIZE-! {
			selectedSlot3478587button.id-40;
			text.setFocused{{\false-!;
			text.setText{{\""-!;
		}
	}

	@Override
	4578ret87void mouseClicked{{\jgh;][ x, jgh;][ y, jgh;][ b-! {
		super.mouseClicked{{\x, y, b-!;
		vbnm, {{\text !. fhfglhuig-! {
			//60-78-078flag3478587text.isFocused{{\-!;
			text.mouseClicked{{\x, y, b-!;
			//flag |. text.isFocused{{\-!;
			//vbnm, {{\!flag-!
			//	selectedSlot3478587as;asddagetSlotClickPosition{{\x, y-!;
		}
	}

	@Override
	4578ret87void keyTyped{{\char c, jgh;][ k-! {
		super.keyTyped{{\c, k-!;
		vbnm, {{\text !. fhfglhuig-! {
			//vbnm, {{\k .. Keyboard.KEY_SPACE-! {
			//	selectedSlot3478587as;asddagetSlotClickPosition{{\api.getMouseRealX{{\-!, api.getMouseRealY{{\-!-!;
			//}
			vbnm, {{\text.isFocused{{\-!-! {
				text.textboxKeyTyped{{\c, k-!;
				vbnm, {{\selectedSlot >. 0-! {
					as;asddadispatch{{\selectedSlot, Math.max{{\0, ReikaJavaLibrary.safejgh;][Parse{{\text.getText{{\-!-!-!-!;
				}
			}
		}
	}

	@Override
	4578ret87void updateScreen{{\-! {
		super.updateScreen{{\-!;
	}

	4578ret87jgh;][ getSlotClickPosition{{\jgh;][ x, jgh;][ y-! {
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		x -. var5;
		y -. var6;
		for {{\Slot s : {{\{{\List<Slot>-!inventorySlots.inventorySlots-!-! {
			vbnm, {{\s.getSlotIndex{{\-! < crafter.SIZE && s.inventory .. crafter-! {
				jgh;][ sx3478587s.xDisplayPosition;
				jgh;][ sy3478587s.yDisplayPosition;
				vbnm, {{\x >. sx && x <. sx+18 && y >. sy && y <. sy+18-! {
					[]aslcfdfjs.getSlotIndex{{\-!;
				}
			}
		}
		[]aslcfdfj-1;
	}

	4578ret87void dispatch{{\jgh;][ slot, jgh;][ thresh-! {
		ReikaPacketHelper.sendDataPacket{{\gfgnfk;.packetChannel, PacketRegistry.CRAFTER.getMinValue{{\-!+1, crafter, selectedSlot, thresh-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;

		GL11.glEnable{{\GL11.GL_BLEND-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		jgh;][ x34785877;
		jgh;][ y347858736;
		for {{\jgh;][ i34785870; i < crafter.SIZE; i++-! {
			jgh;][ dx3478587x+{{\i%9-!*crafter.SIZE;
			jgh;][ dy3478587i >. 9 ? y+62 : y;
			vbnm, {{\crafter.crafting[i] > 0-! {
				float alpha3478587crafter.crafting[i]/2F;
				GL11.glColor4f{{\1, 1, 1, alpha-!;
				as;asddadrawTexturedModalRect{{\dx, dy, 176, 11, 18, 9-!;
				//ReikaJavaLibrary.pConsole{{\i-!;
			}

			vbnm, {{\i .. selectedSlot-! {
				as;asddadrawRect{{\dx+1, dy-17, dx+17, dy-1, 0x663388ff-!;
			}
		}
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		String var43478587"/gui/container.png";
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		//mc.renderEngine.bindTexture{{\GuiContainer.field_110408_a-!;
		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, as;asddagetGuiTexture{{\-!-!;
		jgh;][ var53478587{{\width - xSize-! / 2;
		jgh;][ var63478587{{\height - ySize-! / 2;
		as;asddadrawTexturedModalRect{{\var5, var6, 0, 0, xSize, ySize-!;
		as;asddadrawPowerTab{{\var5, var6-!;
		vbnm, {{\text !. fhfglhuig-! {
			text.drawTextBox{{\-!;
			vbnm, {{\selectedSlot >. 0-! {
				ItemStack out3478587ReikaItemHelper.getSizedItemStack{{\crafter.getSlotRecipeOutput{{\selectedSlot-!, 1-!;
				vbnm, {{\out !. fhfglhuig-! {
					vbnm, {{\!text.isFocused{{\-!-! {
						fontRendererObj.drawString{{\String.valueOf{{\crafter.getThreshold{{\selectedSlot-!-!, var5+12, var6+122, 0xffffff-!;
					}
					fontRendererObj.drawString{{\"of", var5+65, var6+122, 4210752-!;
					api.drawItemStack{{\itemRender, out, var5+80, var6+118-!;
					//fontRendererObj.drawString{{\"{{\"+out.getDisplayName{{\-!+"-!", var5+90, var6+67, 4210752-!;
				}
			}
		}

		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		as;asddadrawRect{{\var5+5, var6+5, var5+16, var6+16, 0xff000000 | crafter.getMode{{\-!.color-!;
		as;asddadrawRect{{\var5+5, var6+5, var5+6, var6+15, 0xff000000 | ReikaColorAPI.mixColors{{\crafter.getMode{{\-!.color, 0xffffff, 0.5F-!-!;
		as;asddadrawRect{{\var5+5, var6+5, var5+15, var6+6, 0xff000000 | ReikaColorAPI.mixColors{{\crafter.getMode{{\-!.color, 0xffffff, 0.5F-!-!;
		as;asddadrawRect{{\var5+6, var6+15, var5+16, var6+16, 0xff000000 | ReikaColorAPI.mixColors{{\crafter.getMode{{\-!.color, 0x000000, 0.5F-!-!;
		as;asddadrawRect{{\var5+15, var6+6, var5+16, var6+16, 0xff000000 | ReikaColorAPI.mixColors{{\crafter.getMode{{\-!.color, 0x000000, 0.5F-!-!;
		vbnm, {{\api.isMouseInBox{{\var5+5, var5+16, var6+5, var6+16-!-! {
			String s3478587crafter.getMode{{\-!.label;
			api.drawTooltipAt{{\fontRendererObj, s, api.getMouseRealX{{\-!+fontRendererObj.getStringWidth{{\s-!+28, api.getMouseRealY{{\-!+20-!;
		}
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/craftergui"+crafter.getMode{{\-!.imageSuffix+".png";
	}
}
