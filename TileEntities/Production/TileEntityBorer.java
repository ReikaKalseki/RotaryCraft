/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.BlockTieredResource;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Interfaces.SemiUnbreakable;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.TwilightForestHandler;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.BorerDigEvent;
import Reika.RotaryCraft.API.Interfaces.IgnoredByBorer;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PartialInventory;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Blocks.BlockMiningPipe;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityBorer extends TileEntityBeamMachine implements EnchantableMachine, GuiController, DiscreteFunction {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	private int pipemeta2 = 0;

	public boolean drops = true;

	private int reqpow;
	private int mintorque;

	/** Power required to break a block, per 0.1F hardness */
	public static final int DIGPOWER = 64;
	public static final int OBSIDIANTORQUE = 512;

	private static final int genRange = ConfigRegistry.BORERGEN.getValue();

	private int step = 1;

	public boolean[][] cutShape = new boolean[7][5]; // 7 cols, 5 rows

	private boolean jammed = false;

	private boolean isMiningAir = false;

	private boolean hitProtection = false;
	private int notifiedPlayer = 0;

	private int durability = ConfigRegistry.BORERMAINTAIN.getState() ? 256 : Integer.MAX_VALUE;

	private int soundtick = 0;

	@Override
	public int getTextureStateForSide(int s) {
		switch(this.getBlockMetadata()) {
		case 0:
			return s == 4 ? this.getActiveTexture() : 0;
		case 1:
			return s == 5 ? this.getActiveTexture() : 0;
		case 3:
			return s == 2 ? this.getActiveTexture() : 0;
		case 2:
			return s == 3 ? this.getActiveTexture() : 0;
		}
		return 0;
	}

	@Override
	public void onRedirect() {
		this.reset();
	}

	public boolean repair() {
		if (durability > 0)
			return false;
		durability = ConfigRegistry.BORERMAINTAIN.getState() ? 256 : Integer.MAX_VALUE;
		return true;
	}

	public boolean isJammed() {
		return jammed;
	}

	public void reset() {
		step = 1;
	}

	public int getHeadX() {
		return xCoord+facing.offsetX*step;
	}

	public int getHeadZ() {
		return zCoord+facing.offsetZ*step;
	}

	@Override
	protected int getActiveTexture() {
		return power > 0 && power >= reqpow && torque >= mintorque ? 1 : 0;
	}

	private void setJammed(boolean jam) {
		boolean old = jammed;
		jammed = jam;
		if (old != jammed) {
			ReikaWorldHelper.causeAdjacentUpdates(worldObj, xCoord, yCoord, zCoord);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (this.hasEnchantments()) {
			for (int i = 0; i < 6; i++) {
				world.spawnParticle("portal", -0.5+x+2*rand.nextDouble(), y+rand.nextDouble(), -0.5+z+2*rand.nextDouble(), 0, 0, 0);
			}
		}

		power = (long)omega*(long)torque;
		if (power <= 0) {
			this.setJammed(false);
			return;
		}

		if (hitProtection && notifiedPlayer < 10) {
			if (world.getTotalWorldTime()%100 == 0) {
				EntityPlayer ep = this.getPlacer();
				if (ep != null) {
					notifiedPlayer++;
					int hx = this.getHeadX();
					int hz = this.getHeadZ();
					String sg = "Your "+this+" has hit a protected area at "+hx+", "+hz+" and has jammed.";
					ReikaChatHelper.sendChatToPlayer(ep, sg);
				}
			}
		}

		if (durability <= 0) {
			if (tickcount%5 == 0) {
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.75F, 0.05F);
				for (int i = 0; i < 6; i++) {
					world.spawnParticle("smoke", x+rand.nextDouble(), y+1+rand.nextDouble()*0.2, z+rand.nextDouble(), 0, 0, 0);
					world.spawnParticle("crit", x+rand.nextDouble(), y+1+rand.nextDouble()*0.2, z+rand.nextDouble(), 0, 0, 0);
				}
			}
			return;
		}

		boolean nodig = true;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j]) {
					nodig = false;
					i = j = 7;
				}
			}
		}

		if (jammed && tickcount%5 == 0) {
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.75F, 1F);
			for (int i = 0; i < 6; i++) {
				world.spawnParticle("smoke", x+rand.nextDouble(), y+1+rand.nextDouble()*0.2, z+rand.nextDouble(), 0, 0, 0);
				world.spawnParticle("crit", x+rand.nextDouble(), y+1+rand.nextDouble()*0.2, z+rand.nextDouble(), 0, 0, 0);
			}
		}

		if (nodig)
			return;
		if (omega <= 0)
			return;

		if (tickcount == 1 || step == 1) {
			isMiningAir = this.checkMiningAir(world, x, y, z, meta);
		}

		//ReikaJavaLibrary.pConsole(isMiningAir+":"+tickcount+"/"+this.getOperationTime(), Side.SERVER);

		if (soundtick > 0)
			soundtick--;

		if (tickcount >= this.getOperationTime() || (isMiningAir && tickcount%5 == 0)) {
			this.skipMiningPipes(world, x, y, z, meta, 0, 16);
			this.calcReqPowerSafe(world, x, y, z, meta);
			if (power >= reqpow && reqpow != -1) {
				this.setJammed(false);
				if (!world.isRemote) {
					ReikaWorldHelper.forceGenAndPopulate(world, x+step*facing.offsetX, y, z+step*facing.offsetZ, meta, genRange);
					this.safeDig(world, x, y, z, meta);
					if (!isMiningAir) {
						if (soundtick == 0) {
							SoundRegistry.RUMBLE.playSoundAtBlock(this);
							soundtick = 5;
						}
						durability--;
					}
				}
			}
			else {
				this.setJammed(true);
			}
			tickcount = 0;
			mintorque = 0;
			reqpow = 0;
			isMiningAir = false;
		}
	}

	public String getCurrentRequiredPower() {
		if (reqpow < 0)
			return "Infinity - Blocked";
		double d1 = ReikaMathLibrary.getThousandBase(reqpow);
		double d2 = ReikaMathLibrary.getThousandBase(mintorque);
		String s1 = ReikaEngLibrary.getSIPrefix(reqpow);
		String s2 = ReikaEngLibrary.getSIPrefix(mintorque);
		return String.format("Power: %.3f%sW; Torque: %.3f%sNm", d1, s1, d2, s2);
	}

	private void safeDig(World world, int x, int y, int z, int meta) {
		try {
			this.dig(world, x, y, z, meta);
		}
		catch (RuntimeException e) {
			RotaryCraft.logger.logError(this+" triggered an exception mining a chunk, probably during worldgen!");
			e.printStackTrace();
		}
	}

	private boolean checkMiningAir(World world, int x, int y, int z, int meta) {
		int a = 0;
		if (meta > 1)
			a = 1;
		int b = 1-a;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j] || step == 1) {
					int xread = x+step*facing.offsetX+a*(i-3);
					int yread = y+step*facing.offsetY+(4-j);
					int zread = z+step*facing.offsetZ+b*(i-3);
					if (world.getBlock(xread, yread, zread) != Blocks.air) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void skipMiningPipes(World world, int x, int y, int z, int meta, int stepped, int max) {
		if (stepped >= max)
			return;
		int a = 0;
		if (meta > 1)
			a = 1;
		int b = 1-a;
		boolean allpipe = true;
		boolean haspipe = false;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j] || step == 1) {
					int xread = x+step*facing.offsetX+a*(i-3);
					int yread = y+step*facing.offsetY+(4-j);
					int zread = z+step*facing.offsetZ+b*(i-3);
					//ReikaJavaLibrary.pConsole(xread+","+yread+","+zread);
					if (world.getBlock(xread, yread, zread) == BlockRegistry.MININGPIPE.getBlockInstance()) {
						haspipe = true;
						int meta2 = world.getBlockMetadata(xread, yread, zread);
						ForgeDirection dir = BlockMiningPipe.getDirectionFromMeta(meta2);
						if (meta2 == 3 || Math.abs(dir.offsetX) == Math.abs(facing.offsetX) && Math.abs(dir.offsetZ) == Math.abs(facing.offsetZ)) {

						}
						else {
							allpipe = false;
						}
					}
				}
			}
		}
		if (haspipe && allpipe) {
			step++;
			this.skipMiningPipes(world, x, y, z, meta, stepped+1, max);
		}
	}

	private boolean ignoreBlockExistence(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		if (b == Blocks.air)
			return true;
		Material mat = ReikaWorldHelper.getMaterial(world, x, y, z);
		if (mat == Material.water || mat == Material.lava)
			return true;
		if (b.isAir(world, x, y, z))
			return true;
		if (b instanceof BlockLiquid || b instanceof BlockFluidBase)
			return true;
		if (b instanceof IgnoredByBorer)
			return ((IgnoredByBorer)b).ignoreHardness(world, world.provider.dimensionId, x, y, z, world.getBlockMetadata(x, y, z));
		return false;
	}

	private void calcReqPowerSafe(World world, int x, int y, int z, int metadata) {
		try {
			this.calcReqPower(world, x, y, z, metadata);
		}
		catch (RuntimeException e) {
			RotaryCraft.logger.logError(this+" triggered an exception mining a chunk, probably during worldgen!");
			e.printStackTrace();
			reqpow = -1;
		}
	}

	private void calcReqPower(World world, int x, int y, int z, int metadata) {
		reqpow = 0;
		int lowtorque = -1;
		int a = 0;
		if (metadata > 1)
			a = 1;
		int b = 1-a;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j] || step == 1) {
					int xread = x+step*facing.offsetX+a*(i-3);
					int yread = y+step*facing.offsetY+(4-j);
					int zread = z+step*facing.offsetZ+b*(i-3);
					this.reqPowAdd(world, xread, yread, zread);
				}
			}
		}

		lowtorque = mintorque;

		//ReikaJavaLibrary.pConsole(mintorque, Side.SERVER);

		if (torque < lowtorque)
			reqpow = -1;
	}


	private void reqPowAdd(World world, int xread, int yread, int zread) {
		if (xread > 3000000 || zread > 3000000) {
			reqpow = -1;
			return;
		}
		if (!this.ignoreBlockExistence(world, xread, yread, zread)) {
			Block id = world.getBlock(xread, yread, zread);
			int meta = world.getBlockMetadata(xread, yread, zread);
			float hard = id.getBlockHardness(world, xread, yread, zread);
			/*
			if (this.isMineableBedrock(world, xread, yread, zread)) {
				mintorque += PowerReceivers.BEDROCKBREAKER.getMinTorque();
				reqpow += PowerReceivers.BEDROCKBREAKER.getMinPower();
			}
			else */if (TwilightForestHandler.getInstance().isToughBlock(id)) {
				mintorque += 2048;
				reqpow += 65536;
			}
			else if (hard < 0) {
				reqpow = -1;
			}
			else if (id == BlockRegistry.DECO.getBlockInstance() && meta == ItemStacks.shieldblock.getItemDamage()) {
				reqpow = -1;
			}
			else if (id instanceof SemiUnbreakable && ((SemiUnbreakable)id).isUnbreakable(world, xread, yread, zread, meta)) {
				reqpow = -1;
			}
			else {
				reqpow += (int)(DIGPOWER*10*hard);
				mintorque += ReikaMathLibrary.ceil2exp((int)(10*hard));
			}
		}
	}

	private void support(World world, int x, int y, int z, int metadata) {
		int a = 0;
		if (metadata > 1)
			a = 1;
		int b = 1-a;
		int xread;
		int yread;
		int zread;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j] || step == 1) {
					xread = x+step*facing.offsetX+a*(i-3);
					yread = y+step*facing.offsetY+(4-j);
					zread = z+step*facing.offsetZ+b*(i-3);
					Block id = world.getBlock(xread, yread+1, zread);
					if (id == Blocks.sand || id == Blocks.gravel)
						if (this.checkTop(i, j)) {
							if (id == Blocks.sand)
								world.setBlock(xread, yread+1, zread, Blocks.sandstone);
							else
								world.setBlock(xread, yread+1, zread, Blocks.stone);
						}
				}
			}
		}
	}

	private boolean checkTop(int i, int j) {
		while (j > 0) {
			j--;
			if (cutShape[i][j])
				return false;
		}
		return true;
	}

	private boolean dropBlocks(int xread, int yread, int zread, World world, int x, int y, int z, Block id, int meta) {
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.BlockEntry.MAZESTONE.getBlock())
			RotaryAchievements.CUTKNOT.triggerAchievement(this.getPlacer());
		if (id == Blocks.bedrock || id == Blocks.end_portal_frame)
			return false;
		if (!world.isRemote && !ReikaPlayerAPI.playerCanBreakAt((WorldServer)world, xread, yread, zread, id, meta, this.getServerPlacer())) {
			hitProtection = true;
			return false;
		}
		TileEntity tile = this.getTileEntity(xread, yread, zread);
		if (tile instanceof RotaryCraftTileEntity)
			return false;
		if (drops && id != Blocks.air) {
			/*
			if (this.isMineableBedrock(world, xread, yread, zread)) {
				ItemStack is = ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust.copy(), DifficultyEffects.BEDROCKDUST.getInt());
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
				}
				return true;
			}*/
			if (id == Blocks.mob_spawner) {
				TileEntityMobSpawner spw = (TileEntityMobSpawner)tile;
				if (spw != null) {
					ItemStack is = ItemRegistry.SPAWNER.getStackOf();
					ReikaSpawnerHelper.addMobNBTToItem(is, spw);
					if (!this.chestCheck(world, x, y, z, is))
						ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
					return true;
				}
			}
			if (tile instanceof IInventory) {
				IInventory ii = (IInventory)tile;
				List<ItemStack> contents = ReikaInventoryHelper.getWholeInventory(ii);
				ReikaInventoryHelper.clearInventory(ii);
				for (int i = 0; i < contents.size(); i++) {
					ItemStack is = contents.get(i);
					boolean fits = this.chestCheck(world, x, y, z, is);
					if (!fits) {
						ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
					}
				}
			}
			if (this.getEnchantment(Enchantment.silkTouch) > 0 && this.canSilk(world, xread, yread, zread)) {
				ItemStack is = new ItemStack(id, 1, this.getSilkTouchMetaDropped(id, meta));
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
				}
				return true;
			}
			int fortune = this.getEnchantment(Enchantment.fortune);
			Collection<ItemStack> items = id.getDrops(world, xread, yread, zread, meta, fortune);
			if (id instanceof BlockTieredResource) {
				EntityPlayer ep = this.getPlacer();
				BlockTieredResource bt = (BlockTieredResource)id;
				boolean harvest = ep != null && bt.isPlayerSufficientTier(world, xread, yread, zread, ep);
				items = harvest ? bt.getHarvestResources(world, xread, yread, zread, fortune, ep) : bt.getNoHarvestResources(world, xread, yread, zread, fortune, ep);
			}
			if (items != null) {
				for (ItemStack is : items) {
					if (!this.chestCheck(world, x, y, z, is)) {
						ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
					}
				}
			}
		}
		return true;
	}

	private int getSilkTouchMetaDropped(Block id, int meta) {
		if (id == Blocks.torch)
			return 0;
		if (id == Blocks.redstone_torch || id == Blocks.unlit_redstone_torch)
			return 0;
		if (id == Blocks.leaves || id == Blocks.log || id == Blocks.leaves2 || id == Blocks.log2)
			return meta&3;
		if (id == Blocks.sapling)
			return meta&3;
		if (id == Blocks.vine)
			return 0;
		if (id == Blocks.waterlily)
			return 0;
		if (id == Blocks.sticky_piston || id == Blocks.piston)
			return 0;
		if (ReikaBlockHelper.isStairBlock(id))
			return 0;
		ModWoodList wood = ModWoodList.getModWood(id, meta);
		if (wood != null) {
			return wood.getLogMetadatas().get(0);
		}
		wood = ModWoodList.getModWoodFromLeaf(id, meta);
		if (wood != null) {
			return wood.getLeafMetadatas().get(0);
		}
		return meta;
	}

	private boolean canSilk(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		if (id == Blocks.air)
			return false;
		if (id == Blocks.fire)
			return false;
		if (id == Blocks.cauldron)
			return false;
		if (id == Blocks.reeds)
			return false;
		if (id == Blocks.powered_comparator || id == Blocks.unpowered_comparator)
			return false;
		if (id == Blocks.powered_repeater || id == Blocks.unpowered_repeater)
			return false;
		if (id == Blocks.redstone_wire)
			return false;
		if (id == Blocks.piston_extension || id == Blocks.piston_head)
			return false;
		if (id == Blocks.wooden_door || id == Blocks.iron_door)
			return false;
		if (BlockRegistry.isTechnicalBlock(id))
			return false;
		if (id.isAir(world, x, y, z))
			return false;
		if (id instanceof BlockLiquid || id instanceof BlockFluidBase)
			return false;
		if (id instanceof BlockTieredResource)
			return false;
		if (id.hasTileEntity(world.getBlockMetadata(x, y, z)))
			return false;
		return true;
	}

	private boolean chestCheck(World world, int x, int y, int z, ItemStack is) {
		if (is == null)
			return false;
		if (world.isRemote)
			return false;
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			TileEntity te = this.getAdjacentTileEntity(dir);
			if (te instanceof IInventory) {
				boolean flag = true;
				if (te instanceof PartialInventory) {
					if (!((PartialInventory)te).hasInventory())
						flag = false;
				}
				if (flag) {
					if (ReikaInventoryHelper.addToIInv(is, (IInventory)te))
						return true;
				}
			}
		}
		return false;
	}

	private void dig(World world, int x, int y, int z, int metadata) {
		if (step == 1)
			RotaryAchievements.BORER.triggerAchievement(this.getPlacer());
		this.support(world, x, y, z, metadata);
		int a = 0;
		if (metadata > 1)
			a = 1;
		int b = 1-a;
		int xread;
		int yread;
		int zread;

		if (step == 1) {
			pipemeta2 = pipemeta;
			pipemeta = 3;
		}
		else if (pipemeta > 2 && pipemeta2 != 3)
			pipemeta = pipemeta2;

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				if (cutShape[i][j] || step == 1) {
					xread = x+step*facing.offsetX+a*(i-3);
					yread = y+step*facing.offsetY+(4-j);
					zread = z+step*facing.offsetZ+b*(i-3);
					Block bk = world.getBlock(xread, yread, zread);
					if (this.dropBlocks(xread, yread, zread, world, x, y, z, bk, world.getBlockMetadata(xread, yread, zread))) {
						ReikaSoundHelper.playBreakSound(world, xread, yread, zread, bk);
						world.setBlock(xread, yread, zread, BlockRegistry.MININGPIPE.getBlockInstance(), pipemeta, 3);
					}
					else {
						step--;
					}
				}
			}
		}
		MinecraftForge.EVENT_BUS.post(new BorerDigEvent(this, step, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, this.hasEnchantment(Enchantment.silkTouch)));
		step++;
	}

	public boolean applyEnchants(ItemStack is) {
		boolean accepted = false;
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.fortune, is)) {
			enchantments.put(Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			enchantments.put(Enchantment.silkTouch, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.efficiency, is))	 {
			enchantments.put(Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.sharpness, is))	 {
			enchantments.put(Enchantment.sharpness, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness, is));
			accepted = true;
		}
		return accepted;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.fortune);
		li.add(Enchantment.silkTouch);
		li.add(Enchantment.efficiency);
		li.add(Enchantment.sharpness);
		return li;
	}

	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("step", step);
		NBT.setBoolean("jam", jammed);
		NBT.setInteger("dura", durability);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		step = NBT.getInteger("step");
		jammed = NBT.getBoolean("jam");
		durability = NBT.getInteger("dura");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBT.setBoolean("drops", drops);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				if (lvl > 0)
					NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++)
				NBT.setBoolean("cut"+String.valueOf(i*7+j), cutShape[i][j]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		drops = NBT.getBoolean("drops");

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++)
				cutShape[i][j] = NBT.getBoolean("cut"+String.valueOf(i*7+j));
		}
	}

	@Override
	protected void makeBeam(World world, int x, int y, int z, int meta) {}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BORER;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return this.isJammed() ? 15 : 0;
	}

	static {

	}

	@Override
	public int getOperationTime() {
		int base = DurationRegistry.BORER.getOperationTime(omega);
		float ench = ReikaEnchantmentHelper.getEfficiencyMultiplier(this.getEnchantment(Enchantment.efficiency));
		return (int)(base/ench);
	}
}
