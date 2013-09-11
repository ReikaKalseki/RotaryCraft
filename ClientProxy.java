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

import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Instantiable.BlockSheetTexRenderer;
import Reika.DragonAPI.Instantiable.ItemSpriteSheetRenderer;
import Reika.DragonAPI.Instantiable.SoundLoader;
import Reika.DragonAPI.Instantiable.SpawnerRenderer;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Entities.RenderFreezeGunShot;
import Reika.RotaryCraft.Entities.RenderIceBlock;
import Reika.RotaryCraft.Entities.RenderRailGunShot;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	//public static final int BlockSheetTexRenderID = RenderingRegistry.getNextAvailableRenderId();

	public static final ItemSpriteSheetRenderer[] items = {
		new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items.png", RotaryAux.items1png),
		new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items2.png", RotaryAux.items2png),
		new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/items3.png", RotaryAux.items3png),
		new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/modextracts.png", RotaryAux.modexpng),
		new ItemSpriteSheetRenderer(RotaryCraft.instance, RotaryCraft.class, "Textures/Items/modingots.png", RotaryAux.modingotpng),
	};
	//public static final ItemSpriteSheetRenderer terrain = new ItemSpriteSheetRenderer(RotaryCraft.class, "Textures/GUI/mobradargui.png", RotaryAux.terrainpng);
	public static final BlockSheetTexRenderer block = new BlockSheetTexRenderer(RotaryCraft.class, "Textures/Terrain/textures.png", RotaryAux.terrainpng);

	public static final ItemMachineRenderer machineItems = new ItemMachineRenderer();
	public static final SpawnerRenderer spawner = new SpawnerRenderer();

	@Override
	public void registerSounds() {
		//RotarySounds.addSounds();
		MinecraftForge.EVENT_BUS.register(new SoundLoader(RotaryCraft.instance, SoundRegistry.soundList));
	}

	@Override
	public void registerRenderers() {
		this.loadModels();

		RenderingRegistry.registerEntityRenderingHandler(EntityRailGunShot.class, new RenderRailGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveShell.class, new RenderRailGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityFreezeGunShot.class, new RenderFreezeGunShot());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceBlock.class, new RenderIceBlock());

		this.registerSpriteSheets();
		this.registerBlockSheets();

		MinecraftForgeClient.registerItemRenderer(RotaryCraft.spawner.itemID, spawner);
	}

	@Override
	public void addArmorRenders() {
		NVHelmet = RenderingRegistry.addNewArmourRendererPrefix("NVHelmet");
		NVGoggles = RenderingRegistry.addNewArmourRendererPrefix("NVGoggles");
		IOGoggles = RenderingRegistry.addNewArmourRendererPrefix("IOGoggles");
		BedArmor = RenderingRegistry.addNewArmourRendererPrefix("Bedrock");
	}

	public void loadModels() {

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.hasRender()) {
				RotaryTERenderer render = RotaryRenderList.instantiateRenderer(m);
				//int[] renderLists = render.createLists();
				//GLListData.addListData(m, renderLists);
				ClientRegistry.bindTileEntitySpecialRenderer(m.getTEClass(), render);
			}
		}

		MinecraftForgeClient.registerItemRenderer(RotaryCraft.machineplacer.itemID, machineItems);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.engineitems.itemID, machineItems);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.gbxitems.itemID, machineItems);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.shaftitems.itemID, machineItems);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.advgearitems.itemID, machineItems);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.flywheelitems.itemID, machineItems);
	}


	private void registerBlockSheets() {
		//RenderingRegistry.registerBlockHandler(BlockSheetTexRenderID, block);
	}

	private void registerSpriteSheets() {
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.shaftcraft.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.enginecraft.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.borecraft.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.misccraft.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.powders.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.pipeplacer.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.compacts.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.extracts.itemID, items[0]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.gearunits.itemID, items[0]);

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			//ReikaJavaLibrary.pConsole("Registering Item Spritesheet for "+ItemRegistry.itemList[i].name()+" at ID "+(ItemRegistry.itemList[i].getShiftedID()+256)+" with sheet "+ItemRegistry.itemList[i].getTextureSheet());
			MinecraftForgeClient.registerItemRenderer(ItemRegistry.itemList[i].getShiftedID(), items[ItemRegistry.itemList[i].getTextureSheet()]);
		}

		MinecraftForgeClient.registerItemRenderer(RotaryCraft.modextracts.itemID, items[3]);
		MinecraftForgeClient.registerItemRenderer(RotaryCraft.modingots.itemID, items[4]);
	}

	// Override any other methods that need to be handled differently client side.

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
