/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.BlockSheetTexRenderer;
import Reika.DragonAPI.ItemSpriteSheetRenderer;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Entities.EntityCustomTNT;
import Reika.RotaryCraft.Entities.EntityFallingBlock;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Entities.RenderCustomTNT;
import Reika.RotaryCraft.Entities.RenderFallingBlock;
import Reika.RotaryCraft.Entities.RenderFreezeGunShot;
import Reika.RotaryCraft.Entities.RenderIceBlock;
import Reika.RotaryCraft.Entities.RenderRailGunShot;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	//public static final int BlockSheetTexRenderID = RenderingRegistry.getNextAvailableRenderId();

	public static final ItemSpriteSheetRenderer items1 = new ItemSpriteSheetRenderer(RotaryCraft.class, "Textures/GUI/items.png", RotaryAux.items1png);
	public static final ItemSpriteSheetRenderer items2 = new ItemSpriteSheetRenderer(RotaryCraft.class, "Textures/GUI/items2.png", RotaryAux.items2png);
	public static final ItemSpriteSheetRenderer terrain = new ItemSpriteSheetRenderer(RotaryCraft.class, "Textures/GUI/mobradargui.png", RotaryAux.terrainpng);
	public static final BlockSheetTexRenderer block = new BlockSheetTexRenderer(RotaryCraft.class, "Textures/Terrain/textures.png", RotaryAux.terrainpng);

	@Override
	public void registerSounds() {
		RotarySounds.addSounds();
	}

	@Override
	public void registerRenderers() {
		this.loadModels();

		RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlock.class, new RenderFallingBlock());
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomTNT.class, new RenderCustomTNT());
		RenderingRegistry.registerEntityRenderingHandler(EntityRailGunShot.class, new RenderRailGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityFreezeGunShot.class, new RenderFreezeGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceBlock.class, new RenderIceBlock());

		this.registerSpriteSheets();
		this.registerBlockSheets();
	}

	@Override
	public void addArmorRenders() {
		NVHelmet = RenderingRegistry.addNewArmourRendererPrefix("NVHelmet");
		NVGoggles = RenderingRegistry.addNewArmourRendererPrefix("NVGoggles");
		IOGoggles = RenderingRegistry.addNewArmourRendererPrefix("IOGoggles");
	}

	public void loadModels() {

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.hasRender()) {
				try {
					ClientRegistry.bindTileEntitySpecialRenderer(m.getTEClass(), (TileEntitySpecialRenderer)Class.forName(m.getRenderer()).newInstance());
				}
				catch (InstantiationException e) {
					e.printStackTrace();
					throw new RuntimeException("Tried to call nonexistent render "+m.getRenderer()+"!");
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new RuntimeException("Tried to call illegal render "+m.getRenderer()+"!");
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException("No class found for Renderer "+m.getRenderer()+"!");
				}
			}
		}

		MinecraftForgeClient.registerItemRenderer(RotaryCraft.machineplacer.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.engineitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.gbxitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.shaftitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.advgearitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.flywheelitems.itemID, new ItemMachineRenderer());
	}


	private void registerBlockSheets() {
		//RenderingRegistry.registerBlockHandler(BlockSheetTexRenderID, block);
	}

	private void registerSpriteSheets() {
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.shaftcraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.enginecraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.borecraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.heatcraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.spawner.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.powders.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.pipeplacer.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.compacts.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.extracts.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.screwdriver.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.meter.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.infobook.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.gearunits.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.yeast.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.ethanol.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.canolaseed.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.wind.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.ultra.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.motiontracker.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.vac.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.stun.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.gravelgun.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.fireball.itemID, items2);
		//MinecraftForgeClient.registerItemRenderer(RotaryCraft.calc.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.bedpick.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.bedaxe.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.bedshov.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.nvg.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.nvh.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.handcraft.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.railammo.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.debug.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.worldedit.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.fuelbucket.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.target.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.iogoggles.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.slides.itemID, items2);
	}

	// Override any other methods that need to be handled differently client side.

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
