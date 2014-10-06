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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;

public class GuiWorktable extends GuiNonPoweredMachine {

	TileEntityWorktable table;
	private int rollout = 8;
	private int rstep = 0;

	public GuiWorktable(EntityPlayer pl, TileEntityWorktable te, World world) {
		super(new ContainerWorktable(pl, te, world), te);
		ep = pl;
		table = te;
	}

	public int getRollout() {
		return rollout;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);/*
		int x = 179;
		int y = 84;
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				ItemStack is = table.getProgrammedSlot(i, k);
				if (is != null) {
					api.drawItemStack(itemRender, fontRendererObj, is, x+k*18, y+i*18);
				}
			}
		}*/
		if (rollout <= 8) {
			int j = (width - xSize) / 2;
			int k = (height - ySize) / 2;
			int x1 = 176;
			int x2 = 183;
			if (api.isMouseInBox(j+x1, j+x2, k+78, k+141)) {
				api.drawTooltipAt(fontRendererObj, "Set Pattern", a-40, b-10);
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int x1 = rollout > 8 ? 232 : 176;
		int x2 = rollout > 8 ? 239 : 183;
		if (api.isMouseInBox(j+x1, j+x2, k+78, k+141)) {
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5F, 0.5F);
			rstep = rollout > 8 ? -1 : 1;
		}
	}

	@Override
	public boolean isMouseOverSlot(Slot slot, int w, int h)
	{
		return this.renderSlot(slot) && super.isMouseOverSlot(slot, w, h);
	}

	@Override
	protected boolean renderSlot(Slot slot) {
		return (slot.slotNumber < 18 || slot.slotNumber >= table.getSizeInventory()) || rollout == 64;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		rollout += rstep;

		if (rollout > 64) {
			rollout = 64;
			rstep = 0;
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5F, 1.5F);
		}
		if (rollout < 8) {
			rollout = 8;
			rstep = 0;
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5F, 1.5F);
		}

		int u = rollout <= 8 ? 240 : 176;
		this.drawTexturedModalRect(j+176, k+78, u, 78, rollout, 64);

		if (!table.craftable)
			return;
		this.drawTexturedModalRect(j+79, k+35, 176, 35, 18, 15);
		api.drawItemStackWithTooltip(itemRender, fontRendererObj, table.getToCraft(), j+116, k+35);
	}

	@Override
	public String getGuiTexture() {
		return "worktablegui";
	}
}
