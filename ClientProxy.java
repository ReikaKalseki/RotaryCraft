/*******************************************************************************
 * @author Reika
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
import Reika.DragonAPI.BlockSheetTexRenderer;
import Reika.DragonAPI.ItemSpriteSheetRenderer;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.RotaryTERenderer;
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
import Reika.RotaryCraft.Renders.PipeRenderer;
import Reika.RotaryCraft.Renders.RenderAdvGear;
import Reika.RotaryCraft.Renders.RenderAerosolizer;
import Reika.RotaryCraft.Renders.RenderBaitBox;
import Reika.RotaryCraft.Renders.RenderBedrockBreaker;
import Reika.RotaryCraft.Renders.RenderBevel;
import Reika.RotaryCraft.Renders.RenderBreeder;
import Reika.RotaryCraft.Renders.RenderBridge;
import Reika.RotaryCraft.Renders.RenderCCTV;
import Reika.RotaryCraft.Renders.RenderCCTVScreen;
import Reika.RotaryCraft.Renders.RenderCannon;
import Reika.RotaryCraft.Renders.RenderCaveFinder;
import Reika.RotaryCraft.Renders.RenderChunkLoader;
import Reika.RotaryCraft.Renders.RenderClutch;
import Reika.RotaryCraft.Renders.RenderCompactor;
import Reika.RotaryCraft.Renders.RenderContainment;
import Reika.RotaryCraft.Renders.RenderDetector;
import Reika.RotaryCraft.Renders.RenderExtractor;
import Reika.RotaryCraft.Renders.RenderFan;
import Reika.RotaryCraft.Renders.RenderFlywheel;
import Reika.RotaryCraft.Renders.RenderForceField;
import Reika.RotaryCraft.Renders.RenderFraction;
import Reika.RotaryCraft.Renders.RenderFreezeGun;
import Reika.RotaryCraft.Renders.RenderGearbox;
import Reika.RotaryCraft.Renders.RenderGrinder;
import Reika.RotaryCraft.Renders.RenderHRay;
import Reika.RotaryCraft.Renders.RenderHarvester;
import Reika.RotaryCraft.Renders.RenderHeater;
import Reika.RotaryCraft.Renders.RenderIodide;
import Reika.RotaryCraft.Renders.RenderLamp;
import Reika.RotaryCraft.Renders.RenderMagnetizer;
import Reika.RotaryCraft.Renders.RenderMobRadar;
import Reika.RotaryCraft.Renders.RenderMonitor;
import Reika.RotaryCraft.Renders.RenderObsidian;
import Reika.RotaryCraft.Renders.RenderPileDriver;
import Reika.RotaryCraft.Renders.RenderProjector;
import Reika.RotaryCraft.Renders.RenderPulseFurnace;
import Reika.RotaryCraft.Renders.RenderPump;
import Reika.RotaryCraft.Renders.RenderRailGun;
import Reika.RotaryCraft.Renders.RenderReservoir;
import Reika.RotaryCraft.Renders.RenderSEngine;
import Reika.RotaryCraft.Renders.RenderScaleChest;
import Reika.RotaryCraft.Renders.RenderShaft;
import Reika.RotaryCraft.Renders.RenderSmokeDetector;
import Reika.RotaryCraft.Renders.RenderSonic;
import Reika.RotaryCraft.Renders.RenderSpawner;
import Reika.RotaryCraft.Renders.RenderSplitter;
import Reika.RotaryCraft.Renders.RenderSprinkler;
import Reika.RotaryCraft.Renders.RenderVacuum;
import Reika.RotaryCraft.Renders.RenderWinder;
import Reika.RotaryCraft.Renders.RenderWoodcutter;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	//public static final int BlockSheetTexRenderID = RenderingRegistry.getNextAvailableRenderId();

	public static final ItemSpriteSheetRenderer items1 = new ItemSpriteSheetRenderer(mod_RotaryCraft.class, "Textures/GUI/items.png", RotaryAux.items1png);
	public static final ItemSpriteSheetRenderer items2 = new ItemSpriteSheetRenderer(mod_RotaryCraft.class, "Textures/GUI/items2.png", RotaryAux.items2png);
	public static final ItemSpriteSheetRenderer terrain = new ItemSpriteSheetRenderer(mod_RotaryCraft.class, "Textures/GUI/mobradargui.png", RotaryAux.terrainpng);
	public static final BlockSheetTexRenderer block = new BlockSheetTexRenderer(mod_RotaryCraft.class, "Textures/Terrain/textures.png", RotaryAux.terrainpng);

	public static final RotaryTERenderer[] renders = {
		new RenderBedrockBreaker(),
		new RenderSEngine(),
		new RenderFlywheel(),
		new RenderShaft(),
		new RenderBevel(),
		new RenderGearbox(),
		new RenderSplitter(),
		null,
		new RenderLamp(),
		new RenderClutch(),
		new RenderMonitor(),
		new RenderGrinder(),
		new RenderHRay(),
		new PipeRenderer(),
		null,
		new RenderBridge(),
		new RenderPump(),
		new PipeRenderer(),
		new RenderReservoir(),
		new RenderAerosolizer(),
		new RenderExtractor(),
		new RenderPulseFurnace(),
		new RenderCompactor(),
		new RenderFan(),
		new PipeRenderer(),
		new RenderFraction(),
		null,
		new RenderObsidian(),
		new RenderPileDriver(),
		new RenderVacuum(),
		null,
		new RenderSprinkler(),
		new RenderWoodcutter(),
		new RenderSpawner(),
		new RenderDetector(),
		new RenderHeater(),
		new RenderBaitBox(),
		new RenderBreeder(),
		null,
		new RenderSmokeDetector(),
		new RenderMobRadar(),
		new RenderWinder(),
		new RenderAdvGear(),
		new RenderCannon(),
		new RenderSonic(),
		null,
		new RenderForceField(),
		null,
		new PipeRenderer(),
		new RenderChunkLoader(),
		new RenderHarvester(),
		new RenderCCTV(),
		new RenderProjector(),
		new RenderRailGun(),
		new RenderIodide(),
		null,
		new RenderFreezeGun(),
		new RenderCaveFinder(),
		new RenderScaleChest(),
		null,
		new RenderMagnetizer(),
		new RenderContainment(),
		new RenderCCTVScreen(),
		null
	};

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

		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.machineplacer.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.engineitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.gbxitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.shaftitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.advgearitems.itemID, new ItemMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.flywheelitems.itemID, new ItemMachineRenderer());
	}


	private void registerBlockSheets() {
		//RenderingRegistry.registerBlockHandler(BlockSheetTexRenderID, block);
	}

	private void registerSpriteSheets() {
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.shaftcraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.enginecraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.borecraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.heatcraft.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.spawner.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.powders.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.pipeplacer.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.compacts.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.extracts.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.screwdriver.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.meter.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.infobook.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.gearunits.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.yeast.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.ethanol.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.canolaseed.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.wind.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.ultra.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.motiontracker.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.vac.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.stun.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.gravelgun.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.fireball.itemID, items2);
		//MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.calc.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.bedpick.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.bedaxe.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.bedshov.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.nvg.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.nvh.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.handcraft.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.railammo.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.debug.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.worldedit.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.fuelbucket.itemID, items1);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.target.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.iogoggles.itemID, items2);
		MinecraftForgeClient.registerItemRenderer(mod_RotaryCraft.slides.itemID, items2);
	}

	// Override any other methods that need to be handled differently client side.

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
