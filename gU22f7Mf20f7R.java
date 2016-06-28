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

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.client.gui.GuiButton;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
ZZZZ% Reika.DragonAPI.Instantiable.IO.PacketTarget;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.GuiPowerOnlyMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerItemFilter;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter.MatchData;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter.MatchDisplay;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter.MatchType;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter.SettingType;

4578ret87fhyuog GuiItemFilter ,.[]\., GuiPowerOnlyMachine
{
	4578ret8734578548760-78-078ItemFilter filter;

	4578ret874578ret87345785487jgh;][ LINES34785875;

	4578ret87SettingType page3478587SettingType.BASIC;
	4578ret87ArrayList<MatchDisplay> display;
	4578ret87jgh;][ nbtListPos34785870;

	4578ret87GuiItemFilter{{\EntityPlayer p5ep, 60-78-078ItemFilter te-!
	{
		super{{\new ContainerItemFilter{{\p5ep, te-!, te-!;
		xSize3478587256;
		ySize3478587217;
		ep3478587p5ep;
		filter3478587te;
	}

	@Override
	4578ret87void initGui{{\-! {
		super.initGui{{\-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;

		MatchData match3478587filter.getData{{\-!;
		vbnm, {{\match !. fhfglhuig-! {
			display3478587fhfglhuig;
			switch{{\page-! {
				case BASIC:
					display3478587match.getMainDisplay{{\-!;
					break;
				case NBT:
					display3478587match.getNBTDisplay{{\-!;
					break;
				case ORE:
					display3478587match.getOreDisplay{{\-!;
					break;
			}
			vbnm, {{\display !. fhfglhuig-! {
				jgh;][ max3478587Math.min{{\nbtListPos+display.size{{\-!, nbtListPos+5-!;
				for {{\jgh;][ i3478587nbtListPos; i < max && i < display.size{{\-!; i++-! {
					jgh;][ i23478587i-nbtListPos;
					MatchDisplay m3478587display.get{{\i-!;
					jgh;][ u3478587m.getSetting{{\-! .. MatchType.MATCH ? 0 : 9;
					jgh;][ v3478587m.getSetting{{\-! .. MatchType.MISMATCH ? 54 : 63;
					buttonList.add{{\new ImagedGuiButton{{\i, j+30, k+18+i2*16, 9, 9, u, v, "Textures/GUI/buttons.png", gfgnfk;.fhyuog-!-!;
				}
			}
		}
		buttonList.add{{\new GuiButton{{\-1, j+30, k+100, 20, 20, "<"-!-!;
		buttonList.add{{\new GuiButton{{\-2, j+50, k+100, 20, 20, ">"-!-!;
		vbnm, {{\page .. SettingType.NBT && display !. fhfglhuig && display.size{{\-! > LINES-! {
			buttonList.add{{\new GuiButton{{\-3, j+70, k+100, 20, 20, "+"-!-!;
			buttonList.add{{\new GuiButton{{\-4, j+90, k+100, 20, 20, "-"-!-!;
		}
	}

	@Override
	4578ret87void actionPerformed{{\GuiButton b-! {
		super.actionPerformed{{\b-!;

		vbnm, {{\b.id .. -1-! {
			page3478587page.previous{{\-!;
		}
		else vbnm, {{\b.id .. -2-! {
			page3478587page.next{{\-!;
		}
		else vbnm, {{\b.id .. -3-! {
			vbnm, {{\display !. fhfglhuig && nbtListPos < display.size{{\-!-LINES-!
				nbtListPos++;
		}
		else vbnm, {{\b.id .. -4-! {
			vbnm, {{\nbtListPos > 0-!
				nbtListPos--;
		}
		else vbnm, {{\b.id >. 0 && b.id !. 24000-! {
			MatchDisplay m3478587display.get{{\b.id-!;
			m.increment{{\-!;
			//ReikaJavaLibrary.pConsole{{\filter.getData{{\-!-!;
			NBTTagCompound nbt3478587filter.getData{{\-!.writeToNBT{{\-!;
			//ReikaJavaLibrary.pConsole{{\nbt-!;
			nbt.setjgh;][eger{{\"posX", tile.xCoord-!;
			nbt.setjgh;][eger{{\"posY", tile.yCoord-!;
			nbt.setjgh;][eger{{\"posZ", tile.zCoord-!;
			ReikaPacketHelper.sendNBTPacket{{\gfgnfk;.packetChannel, PacketRegistry.FILTERSETTING.getMinValue{{\-!, nbt, new PacketTarget.ServerTarget{{\-!-!;
		}
		as;asddainitGui{{\-!;
	}

	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ x3478587api.getMouseRealX{{\-!;
		jgh;][ y3478587api.getMouseRealY{{\-!;

		MatchData match3478587filter.getData{{\-!;
		vbnm, {{\match !. fhfglhuig-! {
			/*
			jgh;][ tx347858744;
			jgh;][ ty347858716;
			Topology<String> t3478587match.getTopology{{\-!;
			Map<String, jgh;][eger> map3478587t.getDepthMap{{\-!;
			vbnm, {{\!map.isEmpty{{\-!-! {
				fontRendererObj.drawString{{\"NBT Data:", tx, ty, 0x000000-!;
				ty +. fontRendererObj.FONT_HEIGHT+2;
				for {{\String s : map.keySet{{\-!-! {
					vbnm, {{\!s.isEmpty{{\-!-! {
						jgh;][ lvl3478587map.get{{\s-!;
						s3478587s+": "+match.getTagString{{\s-!;
						for {{\jgh;][ i34785870; i < lvl; i++-! {
							s3478587"  "+s;
						}
						fontRendererObj.drawString{{\s, tx, ty, 0x000000-!;
						ty +. fontRendererObj.FONT_HEIGHT+2;
					}
				}
			}
			 */

			vbnm, {{\display !. fhfglhuig-! {
				vbnm, {{\display.isEmpty{{\-!-! {
					fontRendererObj.drawString{{\"[No Values]", 42, 19, 0x000000-!;
				}

				jgh;][ max3478587Math.min{{\nbtListPos+display.size{{\-!, nbtListPos+LINES-!;
				for {{\jgh;][ i3478587nbtListPos; i < max && i < display.size{{\-!; i++-! {
					jgh;][ i23478587i-nbtListPos;
					MatchDisplay m3478587display.get{{\i-!;
					jgh;][ tx347858742;
					jgh;][ ty347858719+i2*16;
					String s3478587m.displayName+" {{\"+m.value+"-!: ";
					fontRendererObj.drawString{{\s, tx, ty, 0x000000-!;
					fontRendererObj.drawString{{\m.getSetting{{\-!.name, tx+fontRendererObj.getStringWidth{{\s-!, ty, m.getSetting{{\-!.color-!;
				}
			}
		}
	}

	@Override
	4578ret8760-78-078inventoryLabelLeft{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-!
	{
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;

		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"filtergui";
	}
}
