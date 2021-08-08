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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ClassDependent;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.Trackers.ModLockController.ModReVerifyEvent;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Instantiable.Event.BlockConsumedByFireEvent;
import Reika.DragonAPI.Instantiable.Event.EnderLookAggroEvent;
import Reika.DragonAPI.Instantiable.Event.EntityPushOutOfBlocksEvent;
import Reika.DragonAPI.Instantiable.Event.FarmlandTrampleEvent;
import Reika.DragonAPI.Instantiable.Event.FurnaceUpdateEvent;
import Reika.DragonAPI.Instantiable.Event.LivingFarDespawnEvent;
import Reika.DragonAPI.Instantiable.Event.MTReloadEvent;
import Reika.DragonAPI.Instantiable.Event.PlayerPlaceBlockEvent;
import Reika.DragonAPI.Instantiable.Event.SetBlockEvent;
import Reika.DragonAPI.Instantiable.Event.SlotEvent.AddToSlotEvent;
import Reika.DragonAPI.Instantiable.Event.SlotEvent.RemoveFromSlotEvent;
import Reika.DragonAPI.Instantiable.Event.TileEntityMoveEvent;
import Reika.DragonAPI.Instantiable.Event.Client.EntityRenderingLoopEvent;
import Reika.DragonAPI.Instantiable.Event.Client.PlayerInteractEventClient;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.Java.ReikaReflectionHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.AtmosphereHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.API.Power.ShaftMachine;
import Reika.RotaryCraft.Auxiliary.EMPSparkRenderer;
import Reika.RotaryCraft.Auxiliary.HarvesterDamage;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.MachineDamage;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.ReservoirComboRecipe;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Items.Tools.Charged.ItemSpringBoots;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHose;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityMultiCannon;

import WayofTime.alchemicalWizardry.api.event.TeleposeEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RotaryEventManager {

	public static final RotaryEventManager instance = new RotaryEventManager();

	private static Method conduitGui;

	private RotaryEventManager() {

	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void updateStreamHydros(SetBlockEvent.Pre evt) {
		if (!evt.isWorldgen && evt.newBlock != evt.currentBlock) {
			if (InterfaceCache.STREAM.instanceOf(evt.newBlock) || InterfaceCache.STREAM.instanceOf(evt.currentBlock)) {
				for (int i = -1; i <= 1; i++) {
					for (int k = -1; k <= 1; k++) {
						for (Object o : evt.world.getChunkFromChunkCoords(evt.chunkLocation.chunkXPos+i, evt.chunkLocation.chunkZPos+k).chunkTileEntityMap.values()) {
							TileEntity te = (TileEntity)o;
							if (!te.isInvalid() && te instanceof TileEntityHydroEngine) {
								((TileEntityHydroEngine)te).invalidateStream();
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void reverify(ModReVerifyEvent evt) {
		RotaryCraft.proxy.registerRenderers();
	}

	@SubscribeEvent
	public void handleMTReload(MTReloadEvent evt) {
		if (evt.phase == Phase.END)
			RotaryCraft.instance.reinitRecipes();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderEMPSparks(EntityRenderingLoopEvent evt) {
		if (MinecraftForgeClient.getRenderPass() == 1) {
			if (!Minecraft.getMinecraft().isGamePaused())
				EMPSparkRenderer.instance.tick();
			EMPSparkRenderer.instance.render(Tessellator.instance, ReikaRenderHelper.getPartialTickTime());
		}
	}

	@SubscribeEvent
	public void enderVisor(EnderLookAggroEvent evt) {
		ItemStack is = evt.entityPlayer.inventory.armorInventory[3];
		if (is != null && (is.getItem() == ItemRegistry.BEDREVEAL.getItemInstance() || is.getItem() == ItemRegistry.BEDHELM.getItemInstance())) {
			if (ItemBedrockArmor.HelmetUpgrades.VISOR.existsOn(is)) {
				evt.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void chaosProtection(LivingDropsEvent evt) {
		if (evt.entity instanceof EntitySlime) {
			int rounds = evt.entity.getEntityData().getInteger(TileEntityMultiCannon.SLIME_NBT);
			if (rounds > 0) {
				int drop = (int)(rounds*TileEntityMultiCannon.AMMO_PER_SHOT);
				while (drop > 0) {
					int amt = Math.min(64, drop);
					ReikaItemHelper.dropItem(evt.entity, ReikaItemHelper.getSizedItemStack(ItemStacks.ballbearing, amt));
					drop -= amt;
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled = true)
	public void chaosProtection(LivingHurtEvent evt) {
		if (evt.entity instanceof EntityPlayer) {
			if (ItemBedrockArmor.isWearingFullSuitOf(evt.entityLiving)) {
				String n = evt.source.damageType.toLowerCase(Locale.ENGLISH);
				if (evt.source.damageType.startsWith("chaos") || evt.source.damageType.startsWith("damage.de.") || evt.source.damageType.startsWith("de.")) {
					float f = 1-0.08F*Math.min(8, evt.ammount);
					evt.ammount = Math.min(12, evt.ammount*f);
				}
			}
		}
	}

	@SubscribeEvent
	public void noSpringTrample(FarmlandTrampleEvent evt) {
		if (evt.entity instanceof EntityLivingBase) {
			ItemStack is = ((EntityLivingBase)evt.entity).getEquipmentInSlot(1);
			if (is != null && ItemSpringBoots.isSpringBoots(is))
				evt.setResult(Result.DENY);
		}
	}

	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void showScrapValue(ItemSizeTextEvent evt) {
		if (ReikaItemHelper.matchStacks(evt.getItem(), ItemStacks.scrap)) {
			int val = ItemStacks.getScrapValue(evt.getItem());
			//evt.newString = String.format("Value: %d + %d/9 ingots", val/9, val%9);
			evt.newString = String.format("%d.%d", val/9, val%9);
		}
	}
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@ModDependent(ModList.ENDERIO)
	public void openConduitGUIWithScrewdriver(PlayerInteractEventClient evt) {
		if (evt.entityPlayer.isSneaking() && evt.action == Action.LEFT_CLICK_BLOCK && ItemRegistry.SCREWDRIVER.matchItem(evt.entityPlayer.getCurrentEquippedItem())) {
			Block b = evt.world.getBlock(evt.x, evt.y, evt.z);
			if (b.getClass().getName().equals("crazypants.enderio.conduit.BlockConduitBundle")) {
				try {
					conduitGui.invoke(null, evt.world, evt.x, evt.y, evt.z, evt.entityPlayer);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void openConduitGUIWithScrewdriver(PlayerInteractEvent evt) {
		if (!evt.entityPlayer.worldObj.isRemote && evt.entityPlayer.isSneaking() && evt.action == Action.LEFT_CLICK_BLOCK && ItemRegistry.SCREWDRIVER.matchItem(evt.entityPlayer.getCurrentEquippedItem())) {
			Block b = evt.world.getBlock(evt.x, evt.y, evt.z);
			if (b.getClass().getName().equals("crazypants.enderio.conduit.BlockConduitBundle")) {
				try {
					conduitGui.invoke(null, evt.world, evt.x, evt.y, evt.z, evt.entityPlayer);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void preventUncrafting(AddToSlotEvent evt) {
		if (evt.inventory.getClass().getSimpleName().toLowerCase().contains("uncrafting")) {
			ItemStack is = evt.getItem();
			if (is != null && ItemRegistry.getEntry(is) != null && ItemRegistry.getEntry(is).isPlacer())
				Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	 */
	@SubscribeEvent
	public void applyEMPEffects(SetBlockEvent.Post evt) {
		if (!evt.isWorldgen)
			TileEntityEMP.resetCoordinate(evt.world, evt.xCoord, evt.yCoord, evt.zCoord);
	}

	/*
	@SubscribeEvent
	public void cleanUpDecoTankCrafting(PlayerEvent.ItemCraftedEvent evt) {
		if (ReikaItemHelper.matchStacks(evt.crafting, BlockRegistry.DECOTANK.getStackOf())) {
			if (evt.crafting.stackTagCompound != null && evt.crafting.stackTagCompound.getBoolean(DecoTankSettingsRecipe.NBT_TAG)) {
				evt.crafting.stackTagCompound.removeTag(DecoTankSettingsRecipe.NBT_TAG);
			}
		}
	}
	 */
	@SubscribeEvent
	public void cleanUpReservoirCrafting(PlayerEvent.ItemCraftedEvent evt) {
		if (ReikaItemHelper.matchStacks(evt.crafting, MachineRegistry.RESERVOIR.getCraftedProduct())) {
			if (evt.crafting.stackTagCompound != null && evt.crafting.stackTagCompound.getBoolean(ReservoirComboRecipe.NBT_TAG)) {
				if (!ReikaInventoryHelper.addToIInv(MachineRegistry.RESERVOIR.getCraftedProduct(), evt.player.inventory)) {
					ReikaItemHelper.dropItem(evt.player, MachineRegistry.RESERVOIR.getCraftedProduct());
				}
				evt.crafting.stackTagCompound.removeTag(ReservoirComboRecipe.NBT_TAG);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void stopHijackedFurnaces(FurnaceUpdateEvent.Pre evt) {
		if (TileEntityFurnaceHeater.isHijacked(evt.furnace))
			evt.setCanceled(true);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void preventControlledDespawns(LivingFarDespawnEvent evt) {
		//ReikaJavaLibrary.pConsole(evt.entityLiving+" is trying to despawn");
		if (TileEntitySpawnerController.isFlaggedNoDespawn(evt.entity))
			evt.setResult(Result.DENY);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void preventControlledDespawns(LivingSpawnEvent.AllowDespawn evt) {
		if (TileEntitySpawnerController.isFlaggedNoDespawn(evt.entity))
			evt.setResult(Result.DENY);
	}

	@SubscribeEvent
	public void burnLubricantHose(BlockConsumedByFireEvent evt) {
		if (MachineRegistry.getMachine(evt.world, evt.xCoord, evt.yCoord, evt.zCoord) == MachineRegistry.HOSE) {
			((TileEntityHose)evt.getTileEntity()).burn();
		}
	}

	@SubscribeEvent
	public void noItemEntityPush(EntityPushOutOfBlocksEvent evt) {
		Entity e = evt.entity;
		if (e instanceof EntityItem) {
			int x = MathHelper.floor_double(e.posX);
			int y = MathHelper.floor_double(e.posY);
			int z = MathHelper.floor_double(e.posZ);
			MachineRegistry m = MachineRegistry.getMachine(e.worldObj, x, y, z);
			if (m == MachineRegistry.RESERVOIR) {
				evt.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void harvestSpawner(BlockEvent.BreakEvent evt) {
		if (evt.block == Blocks.mob_spawner && ItemRegistry.BEDPICK.matchItem(evt.getPlayer().getCurrentEquippedItem())) {
			evt.setExpToDrop(0);
		}
	}

	@SubscribeEvent
	public void cancelFramez(TileEntityMoveEvent evt) {
		if (!this.isMovable(evt.tile)) {
			evt.setCanceled(true);
		}
	}
	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderItemInSlot(RenderItemInSlotEvent evt) {
		if (evt.getGuiClass() == GuiAutoCrafter.class) {
			if (evt.slotIndex < 18) {
				ItemStack is = evt.getItem();
				if (is != null) {
					ItemStack out = ItemCraftPattern.getRecipeOutput(is);
					ReikaGuiAPI.instance.drawItemStack(new RenderItem(), out, evt.slotX, evt.slotY);
				}
			}
		}
	}*/

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@ModDependent(ModList.BLOODMAGIC)
	@ClassDependent("WayofTime.alchemicalWizardry.api.event.TeleposeEvent")
	public void noTelepose(TeleposeEvent evt) {
		if (!this.isMovable(evt.getInitialTile()) || !this.isMovable(evt.getFinalTile()))
			evt.setCanceled(true);
	}

	private boolean isMovable(TileEntity te) {
		if (!ConfigRegistry.FRAMES.getState()) {
			if (te instanceof ShaftMachine)
				return false;
			if (te instanceof TileEntityIOMachine)
				return false;
		}
		return true;
	}

	@SubscribeEvent
	public void bonemealEvent(BonemealEvent event) {
		if (!event.world.isRemote)  {
			if (event.block == BlockRegistry.CANOLA.getBlockInstance()) {
				World world = event.world;
				int x = event.x;
				int y = event.y;
				int z = event.z;
				event.setResult(Event.Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRemoveArmor(AddToSlotEvent evt) {
		int id = evt.slotID;
		if (evt.inventory instanceof InventoryPlayer && evt.slotID == 36) { //foot armor
			ItemStack is = evt.getItem();
			if (is == null || !ItemSpringBoots.isSpringBoots(is)) {
				((InventoryPlayer)evt.inventory).player.stepHeight = 0.5F;
			}
		}
	}

	@SubscribeEvent
	public void onRemoveArmor(RemoveFromSlotEvent evt) {
		int id = evt.slotID;
		if (evt.slotID == 36) { //foot armor
			ItemStack is = evt.getItem();
			if (is != null && ItemSpringBoots.isSpringBoots(is)) {
				evt.player.stepHeight = 0.5F;
			}
		}
	}

	@SubscribeEvent
	public void fallEvent(LivingFallEvent event)
	{
		EntityLivingBase e = event.entityLiving;
		ItemStack is = e.getEquipmentInSlot(1);

		if (is != null) {
			if (ItemSpringBoots.isSpringBoots(is)) {
				if (is.getItem() == ItemRegistry.BEDJUMP.getItemInstance() || is.getItemDamage() > 0) {
					//ReikaJavaLibrary.pConsole(event.distance);
					event.distance *= 0.6F;
					//ReikaJavaLibrary.pConsole(event.distance);
					if (is.getItem() == ItemRegistry.BEDJUMP.getItemInstance())
						event.distance = Math.min(event.distance, 25);
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void bedrockSaveB(LivingAttackEvent evt) {
		EntityLivingBase e = evt.entityLiving;
		if (evt.ammount < 1000) {
			if (e instanceof EntityPlayer) {
				if (ItemBedrockArmor.isWearingFullSuitOf(e)) {
					if (evt.source == DamageSource.cactus) {
						evt.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void bedrockSave(LivingHurtEvent evt) {
		EntityLivingBase e = evt.entityLiving;
		if (evt.ammount < 1000) {
			if (e instanceof EntityPlayer) {
				if (ItemBedrockArmor.isWearingFullSuitOf(e)) {
					evt.ammount = Math.min(evt.ammount, 5);
					if (evt.ammount <= 1 && evt.source != DamageSource.drown && evt.source != DamageSource.starve && !AtmosphereHandler.isAtmoBreathabilityDamage(evt.source)) {
						evt.ammount = 0;
						evt.setCanceled(true);
					}
					else {
						Entity attacker = evt.source.getSourceOfDamage();
						if (attacker instanceof EntityPlayer) {
							ItemStack held = ((EntityPlayer)attacker).getCurrentEquippedItem();
							if (held != null && held.getItem().getClass().getSimpleName().toLowerCase(Locale.ENGLISH).contains("rapier")) {
								evt.ammount = 0;
								int dmg = held.getItem().getDamage(held);
								held.getItem().setDamage(held, dmg+120);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void enforceHarvesterLooting(LivingDropsEvent ev) {
		if (ev.source instanceof HarvesterDamage) {
			HarvesterDamage dmg = (HarvesterDamage)ev.source;
			int looting = dmg.getLootingLevel();
			EntityLivingBase e = ev.entityLiving;
			ArrayList<EntityItem> li = ev.drops;
			li.clear();
			e.captureDrops = true;
			ReikaObfuscationHelper.invoke("dropFewItems", e, true, looting);
			ReikaObfuscationHelper.invoke("dropEquipment", e, true, dmg.hasInfinity() ? 100 : looting*4);
			int rem = RotaryCraft.rand.nextInt(200) - looting*4;
			if (rem <= 5 || dmg.hasInfinity())
				ReikaObfuscationHelper.invoke("dropRareDrop", e, 1);
			e.captureDrops = false;
		}
	}

	@SubscribeEvent
	public void meatGrinding(LivingDropsEvent ev) {
		if (ev.source == RotaryCraft.grind) {
			ItemStack food = ReikaEntityHelper.getFoodItem(ev.entityLiving);
			ev.drops.clear();
			if (food != null) {
				World world = ev.entityLiving.worldObj;
				Random rand = RotaryCraft.rand;
				MachineDamage md = (MachineDamage)ev.source;
				int n = 4;
				if (md.lastMachine instanceof TileEntityGrinder) {
					MachineEnchantmentHandler ench = ((TileEntityGrinder)md.lastMachine).getEnchantmentHandler();
					n += ench.getEnchantment(Enchantment.looting);
					if (ench.getEnchantment(Enchantment.flame) > 0) {
						food = ReikaItemHelper.cookFood(food);
					}
				}
				int num = n+rand.nextInt(n)+rand.nextInt(n)+rand.nextInt(n);
				ItemStack is = ReikaItemHelper.getSizedItemStack(food, num);
				ReikaItemHelper.dropItem(world, ev.entityLiving.posX, ev.entityLiving.posY, ev.entityLiving.posZ, is);
			}
			ev.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void disallowDespawn(AllowDespawn ad) {
		EntityLivingBase e = ad.entityLiving;
		PotionEffect pe = e.getActivePotionEffect(RotaryCraft.freeze);
		if (pe == null)
			return;
		ad.setResult(Result.DENY);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void buildWorktables(PlayerPlaceBlockEvent evt) {
		if (evt.block == Blocks.crafting_table || evt.block instanceof BlockWorkbench || TinkerToolHandler.getInstance().isWorkbench(evt.block)) {
			this.checkAndBuildWorktable(evt.world, evt.xCoord, evt.yCoord, evt.zCoord);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void buildWorktables(PlayerInteractEvent evt) {
		if (evt.action == Action.RIGHT_CLICK_BLOCK) {
			Block b = evt.world.getBlock(evt.x, evt.y, evt.z);
			if (b == Blocks.crafting_table || b instanceof BlockWorkbench || TinkerToolHandler.getInstance().isWorkbench(b)) {
				if (this.checkAndBuildWorktable(evt.world, evt.x, evt.y, evt.z))
					evt.setCanceled(true);
			}
		}
	}

	private boolean checkAndBuildWorktable(World world, int x, int y, int z) {
		if (!ReikaWorldHelper.matchWithItemStack(world, x, y-1, z, ItemStacks.steelblock))
			return false;
		if (world.getBlock(x, y-2, z) != Blocks.redstone_block)
			return false;
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				if (i != 0 || k != 0) {
					if (world.getBlock(x+i, y-1, z+k) != Blocks.brick_block)
						return false;
					if (!ReikaWorldHelper.matchWithItemStack(world, x+i, y-2, z+k, ReikaItemHelper.stoneDoubleSlab.asItemStack()))
						return false;
				}
			}
		}
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				world.setBlock(x+i, y-1, z+k, Blocks.air);
				world.setBlock(x+i, y-2, z+k, Blocks.air);
			}
		}
		world.setBlock(x, y, z, Blocks.air);
		SoundRegistry.CRAFT.playSoundAtBlock(world, x, y, z);
		world.setBlock(x, y-2, z, MachineRegistry.WORKTABLE.getBlock(), MachineRegistry.WORKTABLE.getBlockMetadata(), 3);
		return true;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void preventDisallowOfCrucialBlocks(BlockEvent ev) {
		Class c = ev.getClass();
		boolean place = c.getSimpleName().contains("BlockPlaceEvent");
		World world = ev.world;
		int x = ev.x;
		int y = ev.y;
		int z = ev.z;
		if (ev.block == null)
			return;
		Block id = ev.block;
		int meta = ev.blockMetadata;
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(id, meta);
		if (place) { //Bukkit Block Place Event
			if (m != null) {
				if (ConfigRegistry.ALLOWBAN.getState()) {
					if (m.isCrucial()) {
						if (m.canBeDisabledInOverworld() && ReikaWorldHelper.otherDimensionsExist() && world.provider.dimensionId == 0) {
							if (ev.isCanceled())
								RotaryCraft.logger.log("Something successfully cancelled the placement of "+m+". This ban applies to the overworld only!");
						}
						else {
							if (ev.isCanceled())
								RotaryCraft.logger.log("Something tried to cancel the placement of "+m+". This machine is essential and its placement may not be disallowed.");
							ev.setCanceled(false);
						}
					}
					else {
						if (ev.isCanceled())
							RotaryCraft.logger.log("Something successfully cancelled the placement of "+m+". Unless this machine really needs to be disabled, it is recommended you remove this placement ban.");
					}
				}
				else {
					if (ev.isCanceled())
						RotaryCraft.logger.log("Something tried to cancel the placement of "+m+". This is permissible, but you must change the configs to allow it.");
					ev.setCanceled(false);
				}
			}
		}

		if (ConfigRegistry.LOGBLOCKS.getState()) {
			if (m != null) {
				EntityPlayer ep = (EntityPlayer)ReikaWorldHelper.getClosestLivingEntityOfClass(EntityPlayer.class, world, x+0.5, y+0.5, z+0.5, 6);
				String s = place ? "placed" : "removed";
				String name = ep != null ? ep.getCommandSenderName() : "<No Player>";
				RotaryCraft.logger.log("A "+m.getName()+" was "+s+" by "+name+" at "+x+", "+y+", "+z+" in world dimension "+world.provider.dimensionId);
			}
		}

	}

	private void hardCancel(Event e, boolean cancel, boolean print) {
		if (!e.isCancelable())
			throw new IllegalArgumentException("Event "+e.getClass().getSimpleName()+" cannot be cancelled!");
		try {
			Field f = ReikaReflectionHelper.getProtectedInheritedField(e, "cancel");
			f.setAccessible(true);
			f.set(e, cancel);
		}
		catch (Exception ex) {
			if (print)
				ex.printStackTrace();
		}
		try {
			Field f = Event.class.getDeclaredField("isCanceled");
			f.setAccessible(true);
			f.set(e, cancel);
		}
		catch (Exception ex) {
			if (print)
				ex.printStackTrace();
		}
	}

	static {
		if (ModList.ENDERIO.isLoaded()) {
			try {
				Class util = Class.forName("crazypants.enderio.conduit.ConduitUtil");
				conduitGui = util.getMethod("openConduitGui", World.class, int.class, int.class, int.class, EntityPlayer.class);
			}
			catch (Exception e) {
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.ENDERIO, e);
				RotaryCraft.logger.logError("Could not load EnderIO Conduit GUI method.");
				e.printStackTrace();
			}
		}
	}
}
