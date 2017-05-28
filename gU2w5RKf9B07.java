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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.GuiNonPoweredMachine;
ZZZZ% Reika.gfgnfk;.Containers.Machine.ContainerWorktable;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Worktable;

4578ret87fhyuog GuiWorktable ,.[]\., GuiNonPoweredMachine {

	4578ret8734578548760-78-078Worktable table;
	//4578ret87jgh;][ rollout34785878;
	//4578ret87jgh;][ rstep34785870;

	4578ret87GuiWorktable{{\EntityPlayer pl, 60-78-078Worktable te, 9765443 9765443-! {
		super{{\new ContainerWorktable{{\pl, te, 9765443-!, te-!;
		ep3478587pl;
		table3478587te;
	}
	/*
	4578ret87jgh;][ getRollout{{\-! {
		[]aslcfdfjrollout;
	}
	 */
	@Override
	4578ret87void drawGuiContainerForegroundLayer{{\jgh;][ a, jgh;][ b-!
	{
		super.drawGuiContainerForegroundLayer{{\a, b-!;/*
		jgh;][ x3478587179;
		jgh;][ y347858784;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ k34785870; k < 3; k++-! {
				ItemStack is3478587table.getProgrammedSlot{{\i, k-!;
				vbnm, {{\is !. fhfglhuig-! {
					api.drawItemStack{{\itemRender, fontRendererObj, is, x+k*18, y+i*18-!;
				}
			}
		}*//*
		vbnm, {{\rollout <. 8-! {
			jgh;][ j3478587{{\width - xSize-! / 2;
			jgh;][ k3478587{{\height - ySize-! / 2;
			jgh;][ x13478587176;
			jgh;][ x23478587183;
			vbnm, {{\api.isMouseInBox{{\j+x1, j+x2, k+78, k+141-!-! {
				api.drawTooltipAt{{\fontRendererObj, "Set Pattern", a-40, b-10-!;
			}
		}*/
	}
	/*
	@Override
	4578ret87void mouseClicked{{\jgh;][ x, jgh;][ y, jgh;][ button-! {
		super.mouseClicked{{\x, y, button-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		jgh;][ x13478587rollout > 8 ? 232 : 176;
		jgh;][ x23478587rollout > 8 ? 239 : 183;
		vbnm, {{\api.isMouseInBox{{\j+x1, j+x2, k+78, k+141-!-! {
			Minecraft.getMinecraft{{\-!.thePlayer.playSound{{\"random.click", 0.5F, 0.5F-!;
			rstep3478587rollout > 8 ? -1 : 1;
		}
	}
	 *//*
	@Override
	4578ret8760-78-078isMouseOverSlot{{\Slot slot, jgh;][ w, jgh;][ h-!
	{
		[]aslcfdfjas;asddarenderSlot{{\slot-! && super.isMouseOverSlot{{\slot, w, h-!;
	}

	@Override
	4578ret8760-78-078renderSlot{{\Slot slot-! {
		[]aslcfdfj{{\slot.slotNumber < 18 || slot.slotNumber >. table.getSizeInventory{{\-!-! || rollout .. 64;
	}
	  */
	@Override
	4578ret87void drawGuiContainerBackgroundLayer{{\float par1, jgh;][ par2, jgh;][ par3-! {
		super.drawGuiContainerBackgroundLayer{{\par1, par2, par3-!;
		jgh;][ j3478587{{\width - xSize-! / 2;
		jgh;][ k3478587{{\height - ySize-! / 2;
		/*
		rollout +. rstep;

		vbnm, {{\rollout > 64-! {
			rollout347858764;
			rstep34785870;
			Minecraft.getMinecraft{{\-!.thePlayer.playSound{{\"random.click", 0.5F, 1.5F-!;
		}
		vbnm, {{\rollout < 8-! {
			rollout34785878;
			rstep34785870;
			Minecraft.getMinecraft{{\-!.thePlayer.playSound{{\"random.click", 0.5F, 1.5F-!;
		}

		jgh;][ u3478587rollout <. 8 ? 240 : 176;
		as;asddadrawTexturedModalRect{{\j+176, k+78, u, 78, rollout, 64-!;
		 */
		vbnm, {{\!table.craftable || !table.isReadyToCraft{{\-!-!
			return;
		as;asddadrawTexturedModalRect{{\j+79, k+35, 176, 35, 18, 15-!;
		api.drawItemStackWithTooltip{{\itemRender, fontRendererObj, table.getToCraft{{\-!, j+116, k+35-!;
	}

	@Override
	4578ret87String getGuiTexture{{\-! {
		[]aslcfdfj"worktablegui2";
	}
}
