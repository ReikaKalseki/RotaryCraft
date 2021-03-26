/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Auxiliary.Trackers.DonatorController;
import Reika.DragonAPI.Auxiliary.Trackers.DonatorController.Donator;
import Reika.DragonAPI.Auxiliary.Trackers.PatreonController;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerSpecificRenderer;
import Reika.DragonAPI.Auxiliary.Trackers.SettingInterferenceTracker;
import Reika.DragonAPI.IO.Shaders.ShaderProgram;
import Reika.DragonAPI.IO.Shaders.ShaderRegistry;
import Reika.DragonAPI.IO.Shaders.ShaderRegistry.ShaderDomain;
import Reika.DragonAPI.Instantiable.Rendering.ForcedTextureArmorModel;
import Reika.DragonAPI.Instantiable.Rendering.ItemSpriteSheetRenderer;
import Reika.DragonAPI.Instantiable.Rendering.MultiSheetItemRenderer;
import Reika.DragonAPI.Instantiable.Rendering.SpawnerRenderer;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.DonatorGearRender;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Entities.EntityCustomTNT;
import Reika.RotaryCraft.Entities.EntityDischarge;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityFlakShot;
import Reika.RotaryCraft.Entities.EntityFlameTurretShot;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityGatlingShot;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityLiquidBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Entities.EntitySonicShot;
import Reika.RotaryCraft.Entities.RenderDischarge;
import Reika.RotaryCraft.Entities.RenderFlakShot;
import Reika.RotaryCraft.Entities.RenderFlameTurretShot;
import Reika.RotaryCraft.Entities.RenderFreezeGunShot;
import Reika.RotaryCraft.Entities.RenderGatlingShot;
import Reika.RotaryCraft.Entities.RenderIceBlock;
import Reika.RotaryCraft.Entities.RenderLiquidBlock;
import Reika.RotaryCraft.Entities.RenderRailGunShot;
import Reika.RotaryCraft.Entities.RenderSonicShot;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	private static final ItemSpriteSheetRenderer[] items = {
			new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items.png"),
			new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items2.png"),
			new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items3.png"),
			new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items4.png"),
	};

	private static final MultiSheetItemRenderer[] multisheets = {
			new MultiSheetItemRenderer(RotaryCraft.instance, RotaryCraft.class),
	};

	//public static final ItemSpriteSheetRenderer terrain = new ItemSpriteSheetRenderer(RotaryCraft.class, "Textures/GUI/mobradargui.png", RotaryAux.terrainpng);

	public static final ItemMachineRenderer machineItems = new ItemMachineRenderer();
	public static final DecoTankItemRenderer decotank = new DecoTankItemRenderer();
	public static final SpawnerRenderer spawner = new SpawnerRenderer();

	public static PipeBodyRenderer pipe;
	public static CubicalMachineRenderer cube;
	public static ConnectedGlassRenderer connected;

	private static ShaderProgram heatRipple;
	//private static ShaderProgram heatGlow;

	//public static final ForcedTextureArmorModel bed1 = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png");
	//public static final ForcedTextureArmorModel bed2 = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/bedrock_2.png");
	//public static final ForcedTextureArmorModel hsla1 = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/steel_1.png");
	//public static final ForcedTextureArmorModel hsla2 = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/steel_2.png");
	//public static final ForcedTextureArmorModel io = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/IOGoggles.png");
	//public static final ForcedTextureArmorModel nvg = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/NVGoggles.png");
	//public static final ForcedTextureArmorModel nvh = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/NVHelmet.png");
	//public static final ForcedTextureArmorModel jet = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/jet.png");
	//public static final ForcedTextureArmorModel bedjet = new ForcedTextureArmorModel(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/bedrock_jet.png");

	private static final HashMap<ItemRegistry, ForcedTextureArmorModel> armorTextures = new HashMap();
	private static final HashMap<ItemRegistry, String> armorAssets = new HashMap();

	public static ShaderProgram getHeatRippleShader() {
		return heatRipple;
	}

	@Override
	public void registerSounds() {
		sounds.register();
	}

	public static ItemSpriteSheetRenderer getSpritesheetRenderer(int index) {
		return items[index];
	}

	@Override
	public void registerRenderers() {
		if (heatRipple == null)
			heatRipple = ShaderRegistry.createShader(RotaryCraft.instance, "heatripple", RotaryCraft.class, "Shaders/", ShaderDomain.GLOBALNOGUI).setEnabled(false);
		//heatGlow = ShaderRegistry.createShader(RotaryCraft.instance, "heatglow", RotaryCraft.class, "Shaders/", ShaderDomain.TESR).setEnabled(false);

		if (RotaryCraft.instance.isLocked()) {
			pipeRender = cubeRender = connectedRender = 0;
		}
		else {
			if (pipeRender == 0) {
				pipeRender = RenderingRegistry.getNextAvailableRenderId();
				pipe = new PipeBodyRenderer(pipeRender);
				RenderingRegistry.registerBlockHandler(pipeRender, pipe);
			}

			if (cubeRender == 0) {
				cubeRender = RenderingRegistry.getNextAvailableRenderId();
				cube = new CubicalMachineRenderer(cubeRender);
				RenderingRegistry.registerBlockHandler(cubeRender, cube);
			}

			if (connectedRender == 0) {
				connectedRender = RenderingRegistry.getNextAvailableRenderId();
				connected = new ConnectedGlassRenderer(connectedRender);
				RenderingRegistry.registerBlockHandler(connectedRender, connected);
			}
		}

		if (DragonOptions.NORENDERS.getState()) {
			RotaryCraft.logger.log("Disabling all machine renders for FPS and lag profiling.");
		}
		else {
			this.loadModels();
		}

		RenderingRegistry.registerEntityRenderingHandler(EntityRailGunShot.class, new RenderRailGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveShell.class, new RenderRailGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityFreezeGunShot.class, new RenderFreezeGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlakShot.class, new RenderFlakShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityGatlingShot.class, new RenderGatlingShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlameTurretShot.class, new RenderFlameTurretShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceBlock.class, new RenderIceBlock());
		RenderingRegistry.registerEntityRenderingHandler(EntitySonicShot.class, new RenderSonicShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityLiquidBlock.class, new RenderLiquidBlock());
		RenderingRegistry.registerEntityRenderingHandler(EntityDischarge.class, new RenderDischarge());
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomTNT.class, new RenderTNTPrimed());

		this.registerSpriteSheets();
		this.registerBlockSheets();

		MinecraftForgeClient.registerItemRenderer(ItemRegistry.SPAWNER.getItemInstance(), spawner);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DECOTANK.getBlockInstance()), decotank);
	}

	@Override
	public void addArmorRenders() {
		NVHelmet = RenderingRegistry.addNewArmourRendererPrefix("NVHelmet");
		NVGoggles = RenderingRegistry.addNewArmourRendererPrefix("NVGoggles");
		IOGoggles = RenderingRegistry.addNewArmourRendererPrefix("IOGoggles");
		armor = RenderingRegistry.addNewArmourRendererPrefix("Bedrock");
		SteelArmor = RenderingRegistry.addNewArmourRendererPrefix("HSLA");
		/*
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/bedrock_1.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/bedrock_2.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/steel_1.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/steel_2.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/IOGoggles.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/NVGoggles.png");
		ReikaTextureHelper.forceArmorTexturePath("/Reika/RotaryCraft/Textures/Misc/NVHelmet.png");*/

		addArmorTexture(ItemRegistry.JETPACK, "/Reika/RotaryCraft/Textures/Misc/jet.png");
		addArmorTexture(ItemRegistry.BEDPACK, "/Reika/RotaryCraft/Textures/Misc/bedrock_jet.png");
		addArmorTexture(ItemRegistry.STEELPACK, "/Reika/RotaryCraft/Textures/Misc/hsla_jet.png");
		addArmorTexture(ItemRegistry.JUMP, "/Reika/RotaryCraft/Textures/Misc/jump.png");
		addArmorTexture(ItemRegistry.BEDJUMP, "/Reika/RotaryCraft/Textures/Misc/bedrock_jump.png");
		addArmorTexture(ItemRegistry.NVG, "/Reika/RotaryCraft/Textures/Misc/NVGoggles.png");
		//addArmorTexture(ItemRegistry.NVH, "/Reika/RotaryCraft/Textures/Misc/NVHelmet.png");
		addArmorTexture(ItemRegistry.IOGOGGLES, "/Reika/RotaryCraft/Textures/Misc/IOGoggles.png");
		addArmorTexture(ItemRegistry.BEDHELM, "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png");
		addArmorTexture(ItemRegistry.BEDREVEAL, "/Reika/RotaryCraft/Textures/Misc/bedreveal.png");
		addArmorTexture(ItemRegistry.BEDCHEST, "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png");
		addArmorTexture(ItemRegistry.BEDBOOTS, "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png");
		addArmorTexture(ItemRegistry.BEDLEGS, "/Reika/RotaryCraft/Textures/Misc/bedrock_2.png");
		addArmorTexture(ItemRegistry.STEELBOOTS, "/Reika/RotaryCraft/Textures/Misc/steel_1.png");
		addArmorTexture(ItemRegistry.STEELHELMET, "/Reika/RotaryCraft/Textures/Misc/steel_1.png");
		addArmorTexture(ItemRegistry.STEELCHEST, "/Reika/RotaryCraft/Textures/Misc/steel_1.png");
		addArmorTexture(ItemRegistry.STEELLEGS, "/Reika/RotaryCraft/Textures/Misc/steel_2.png");
	}

	private static void addArmorTexture(ItemRegistry item, String tex) {
		RotaryCraft.logger.log("Adding armor texture for "+item+": "+tex);
		armorTextures.put(item, new ForcedTextureArmorModel(RotaryCraft.class, tex, item.getArmorType()));
		String[] s = tex.split("/");
		String file = s[s.length-1];
		String defaultTex = "rotarycraft:textures/models/armor/"+file;
		//ReikaJavaLibrary.pConsole(defaultTex);
		armorAssets.put(item, defaultTex);
	}

	public static ForcedTextureArmorModel getArmorRenderer(ItemRegistry item) {
		return armorTextures.get(item);
	}

	public static String getArmorTextureAsset(ItemRegistry item) {
		return armorAssets.get(item);
	}

	public void loadModels() {

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList.get(i);
			if (m.hasRender() && !m.isPipe()) {
				RotaryTERenderer render = RotaryRenderList.instantiateRenderer(m);
				//int[] renderLists = render.createLists();
				//GLListData.addListData(m, renderLists);
				ClientRegistry.bindTileEntitySpecialRenderer(m.getTEClass(), render);
			}
		}

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry ir = ItemRegistry.itemList[i];
			if (ir.isPlacer()) {
				MinecraftForgeClient.registerItemRenderer(ir.getItemInstance(), RotaryCraft.instance.isLocked() ? null : machineItems);
			}
		}

		//MachineISBRH.renderID = RenderingRegistry.getNextAvailableRenderId();
		//RenderingRegistry.registerBlockHandler(MachineISBRH.renderID, new MachineISBRH());
	}


	private void registerBlockSheets() {
		//RenderingRegistry.registerBlockHandler(BlockSheetTexRenderID, block);
	}

	private void registerSpriteSheets() {
		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			//ReikaJavaLibrary.pConsole("Registering Item Spritesheet for "+ItemRegistry.itemList[i].name()+" at ID "+(ItemRegistry.itemList[i].getItemInstance()+256)+" with sheet "+ItemRegistry.itemList[i].getTextureSheet());
			ItemRegistry ir = ItemRegistry.itemList[i];
			if (!ir.isPlacer()) {
				if (RotaryCraft.instance.isLocked()) {
					MinecraftForgeClient.registerItemRenderer(ir.getItemInstance(), null);
				}
				else {
					if (ir.isMultiSheet()) {
						MinecraftForgeClient.registerItemRenderer(ir.getItemInstance(), multisheets[-ir.getTextureSheet()]);
					}
					else {
						MinecraftForgeClient.registerItemRenderer(ir.getItemInstance(), items[ir.getTextureSheet()]);
					}
				}
			}
		}
	}

	@Override
	public void initClasses() {
		super.initClasses();
		ReikaJavaLibrary.initClass(HandbookRegistry.class);
		ReikaJavaLibrary.initClass(SoundRegistry.class);
		SettingInterferenceTracker.instance.registerSettingHandler(SettingInterferenceTracker.muteInterference);
	}

	// Override any other methods that need to be handled differently client side.

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void loadDonatorRender() {
		Collection<Donator> donators = new ArrayList();
		donators.addAll(DonatorController.instance.getReikasDonators());
		donators.addAll(PatreonController.instance.getModPatrons("Reika"));
		for (Donator s : donators) {
			if (s.ingameName != null)
				PlayerSpecificRenderer.instance.registerRenderer(s.ingameName, DonatorGearRender.instance);
			else
				RotaryCraft.logger.logError("Donator "+s.displayName+" UUID could not be found! Cannot give special render!");
		}
	}

}
