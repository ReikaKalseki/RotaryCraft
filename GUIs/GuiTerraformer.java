/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.liquids.LiquidStack;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerTerraformer;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityTerraformer;

public class GuiTerraformer extends GuiPowerOnlyMachine {

	TileEntityTerraformer terra;

	public GuiTerraformer(EntityPlayer pl, TileEntityTerraformer te) {
		super(new ContainerTerraformer(pl, te), te);
		ep = pl;
		terra = te;
		xSize = 240;
		ySize = 222;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String tex = "/Reika/RotaryCraft/Textures/GUI/biomes.png";

		List<BiomeGenBase> li = terra.getValidTargetBiomes(terra.getCentralBiome());

		for (int i = 0; i < li.size(); i++) {
			BiomeGenBase b = li.get(i);
			buttonList.add(new ImagedGuiButton(i, j+8, k+17+39*i, 32, 32, 32*(b.biomeID%8), 32*(b.biomeID/8), 0, tex));
		}
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		this.initGui();

		List<BiomeGenBase> li = terra.getValidTargetBiomes(terra.getCentralBiome());
		if (b.id < li.size()) {
			BiomeGenBase biome = li.get(b.id);
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.TERRAFORMER.getMinValue(), terra, ep, biome.biomeID);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par2, int par3) {
		super.drawGuiContainerForegroundLayer(par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		BiomeGenBase from = terra.getCentralBiome();
		List<BiomeGenBase> li = terra.getValidTargetBiomes(from);

		for (int i = 0; i < li.size(); i++) {
			BiomeGenBase to = li.get(i);
			LiquidStack liq = terra.getReqLiquidForTransform(from, to);
			if (liq != null) {
				ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, liq.asItemStack(), 48, 17+i*39);
				ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, String.format("%d", liq.amount), 56, 21+i*39, 0);
			}
			else {
				ReikaGuiAPI.instance.drawLine(48, 17+i*39, 16+48, 16+17+i*39, 0);
				ReikaGuiAPI.instance.drawLine(16+48, 17+i*39, 48, 16+17+i*39, 0);
			}
			List<ItemStack> items = terra.getItemsForTransform(from, to);
			if (items != null && !items.isEmpty()) {
				int step = (int)((System.nanoTime()/500000000)%items.size());
				ItemStack is = items.get(step);
				ReikaGuiAPI.instance.drawItemStack(itemRenderer, fontRenderer, is, 48, 19+16+i*39);
			}
			else {
				ReikaGuiAPI.instance.drawLine(48, 18+17+i*39, 16+48, 18+16+17+i*39, 0);
				ReikaGuiAPI.instance.drawLine(16+48, 18+17+i*39, 48, 18+16+17+i*39, 0);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
	}

	@Override
	public String getGuiTexture() {
		return "terraformergui";
	}

}
