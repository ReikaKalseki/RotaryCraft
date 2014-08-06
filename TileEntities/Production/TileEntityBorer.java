/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.IgnoredByBorer;
import Reika.RotaryCraft.API.Event.BorerDigEvent;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PartialInventory;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBorer extends TileEntityBeamMachine implements EnchantableMachine, GuiController, DiscreteFunction {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	private int pipemeta2 = 0;

	public boolean drops = true;

	public int reqpow;
	public int mintorque;

	/** Power required to break a block, per 0.1F hardness */
	public static final int DIGPOWER = 64;
	public static final int OBSIDIANTORQUE = 512;

	private int step = 1;

	public boolean[][] cutShape = new boolean[7][5]; // 7 cols, 5 rows

	private boolean jammed = false;

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

	public boolean isJammed() {
		return jammed;
	}

	public void reset() {
		step = 1;
	}

	public int getHeadX() {
		return xCoord+xstep*step;
	}

	public int getHeadZ() {
		return zCoord+zstep*step;
	}

	@Override
	protected int getActiveTexture() {
		return power > 0 && power >= reqpow && torque >= mintorque ? 1 : 0;
	}

	private void setJammed(boolean jam) {
		jammed = jam;
		ReikaWorldHelper.causeAdjacentUpdates(worldObj, xCoord, yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		power = (long)omega*(long)torque;
		if (power == 0)
			this.setJammed(false);
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

		if (this.hasEnchantments()) {
			for (int i = 0; i < 6; i++) {
				world.spawnParticle("portal", -0.5+x+2*rand.nextDouble(), y+rand.nextDouble(), -0.5+z+2*rand.nextDouble(), 0, 0, 0);
			}
		}

		if (nodig)
			return;
		if (omega <= 0)
			return;

		if (tickcount >= this.getOperationTime()) {
			this.calcReqPower(world, x, y, z, meta);
			if (power >= reqpow && reqpow != -1) {
				this.setJammed(false);
				if (!world.isRemote) {
					this.forceGenAndPopulate(world, x, y, z, meta);
					this.dig(world, x, y, z, meta);
				}
			}
			else {
				this.setJammed(true);
			}
			tickcount = 0;
			mintorque = 0;
			reqpow = 0;
		}
	}

	private void forceGenAndPopulate(World world, int x, int y, int z, int meta) {
		int xread = x+step*xstep;
		int zread = z+step*zstep;
		Chunk ch = world.getChunkFromBlockCoords(xread, zread);
		IChunkProvider p = world.getChunkProvider();
		if (!ch.isTerrainPopulated) {
			try {
				p.populate(p, xread >> 4, zread >> 4);
			}
			catch (ConcurrentModificationException e) {
				RotaryCraft.logger.log("Chunk at "+x+", "+z+" failed to allow population due to a ConcurrentModificationException! Contact Reika with information on any mods that might be multithreading worldgen!");
			}
			catch (Exception e) {
				RotaryCraft.logger.log("Chunk at "+x+", "+z+" failed to allow population!");
				e.printStackTrace();
			}
		}
	}

	private boolean ignoreBlockExistence(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id == 0)
			return true;
		Material mat = world.getBlockMaterial(x, y, z);
		if (mat == Material.water || mat == Material.lava)
			return true;
		Block b = Block.blocksList[id];
		if (b.isAirBlock(world, x, y, z))
			return true;
		if (b instanceof BlockFluid || b instanceof BlockFluidBase)
			return true;
		if (b instanceof IgnoredByBorer)
			return ((IgnoredByBorer)b).ignoreHardness(world, world.provider.dimensionId, x, y, z, world.getBlockMetadata(x, y, z));
		return false;
	}

	public static boolean isTwilightForestToughBlock(int id) {
		return id == TwilightForestHandler.getInstance().mazeStoneID || id == TwilightForestHandler.getInstance().shieldID;
	}
	/*
	public static boolean isMineableBedrock(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id != Block.bedrock.blockID && id != FactorizationHandler.getInstance().bedrockID)
			return false;
		/*
		if (y > 4 && y < 40 && world.provider.dimensionId == ReikaTwilightHelper.getDimensionID())
			return true;
		if (y > 122 && world.provider.isHellWorld)
			return true;
	 *//*
	return false;
}*/

	private void calcReqPower(World world, int x, int y, int z, int metadata) {
		reqpow = 0;
		int lowtorque = -1;
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
					xread = x+step*xstep+a*(i-3); yread = y+step*ystep+(4-j); zread = z+step*zstep+b*(i-3);
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
		if (!this.ignoreBlockExistence(world, xread, yread, zread)) {
			int id = world.getBlockId(xread, yread, zread);
			int meta = world.getBlockMetadata(xread, yread, zread);
			float hard = Block.blocksList[id].getBlockHardness(world, xread, yread, zread);
			/*
			if (this.isMineableBedrock(world, xread, yread, zread)) {
				mintorque += PowerReceivers.BEDROCKBREAKER.getMinTorque();
				reqpow += PowerReceivers.BEDROCKBREAKER.getMinPower();
			}
			else */if (this.isTwilightForestToughBlock(id)) {
				mintorque += 2048;
				reqpow += 65536;
			}
			else if (hard < 0) {
				reqpow = -1;
			}
			else if (id == ItemStacks.shieldblock.itemID && meta == ItemStacks.shieldblock.getItemDamage()) {
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
					xread = x+step*xstep+a*(i-3);
					yread = y+step*ystep+(4-j);
					zread = z+step*zstep+b*(i-3);
					int id = world.getBlockId(xread, yread+1, zread);
					if (id == Block.sand.blockID || id == Block.gravel.blockID)
						if (this.checkTop(i, j)) {
							if (id == Block.sand.blockID)
								world.setBlock(xread, yread+1, zread, Block.sandStone.blockID);
							else
								world.setBlock(xread, yread+1, zread, Block.stone.blockID);
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

	private boolean dropBlocks(int xread, int yread, int zread, World world, int x, int y, int z, int id, int meta) {
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.getInstance().mazeStoneID)
			RotaryAchievements.CUTKNOT.triggerAchievement(this.getPlacer());
		if (!ReikaPlayerAPI.playerCanBreakAt(world, xread, yread, zread, id, meta, placer))
			return false;
		TileEntity tile = this.getTileEntity(xread, yread, zread);
		if (tile instanceof RotaryCraftTileEntity)
			return false;
		if (drops && id != 0) {
			/*
			if (this.isMineableBedrock(world, xread, yread, zread)) {
				ItemStack is = ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust.copy(), DifficultyEffects.BEDROCKDUST.getInt());
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
				}
				return true;
			}*/
			if (id == Block.mobSpawner.blockID) {
				TileEntityMobSpawner spw = (TileEntityMobSpawner)tile;
				if (spw != null) {
					ItemStack is = new ItemStack(RotaryCraft.spawner);
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
			ArrayList<ItemStack> items = Block.blocksList[id].getBlockDropped(world, xread, yread, zread, meta, this.getEnchantment(Enchantment.fortune));
			for (int i = 0; i < items.size(); i++) {
				ItemStack is = items.get(i);
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1.125, z+0.5, is, 3);
				}
			}
		}
		return true;
	}

	private int getSilkTouchMetaDropped(int id, int meta) {
		if (id == Block.torchWood.blockID)
			return 0;
		if (id == Block.torchRedstoneActive.blockID || id == Block.torchRedstoneIdle.blockID)
			return 0;
		if (id == Block.leaves.blockID || id == Block.wood.blockID)
			return meta&3;
		if (id == Block.sapling.blockID)
			return meta&3;
		if (id == Block.vine.blockID)
			return 0;
		if (id == Block.waterlily.blockID)
			return 0;
		if (id == Block.pistonStickyBase.blockID || id == Block.pistonStickyBase.blockID)
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
		int id = world.getBlockId(x, y, z);
		if (id == 0)
			return false;
		if (id == Block.fire.blockID)
			return false;
		if (id == Block.cauldron.blockID)
			return false;
		if (id == Block.reed.blockID)
			return false;
		if (id == Block.redstoneComparatorActive.blockID || id == Block.redstoneComparatorIdle.blockID)
			return false;
		if (id == Block.redstoneRepeaterActive.blockID || id == Block.redstoneRepeaterIdle.blockID)
			return false;
		if (id == Block.redstoneWire.blockID)
			return false;
		if (id == Block.pistonExtension.blockID || id == Block.pistonMoving.blockID)
			return false;
		if (id == Block.doorWood.blockID || id == Block.doorIron.blockID)
			return false;
		if (id == RotaryCraft.miningpipe.blockID)
			return false;
		if (BlockRegistry.isMachineBlock(id))
			return false;
		Block b = Block.blocksList[id];
		if (b.isAirBlock(world, x, y, z))
			return false;
		if (b instanceof BlockFluid || b instanceof BlockFluidBase)
			return false;
		if (b.hasTileEntity(world.getBlockMetadata(x, y, z)))
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
					xread = x+step*xstep+a*(i-3);
					yread = y+step*ystep+(4-j);
					zread = z+step*zstep+b*(i-3);
					if (this.dropBlocks(xread, yread, zread, world, x, y, z, world.getBlockId(xread, yread, zread), world.getBlockMetadata(xread, yread, zread)))
						world.setBlock(xread, yread, zread, RotaryCraft.miningpipe.blockID, pipemeta, 3);
					else {
						step--;
					}
				}
			}
		}
		MinecraftForge.EVENT_BUS.post(new BorerDigEvent(this, step, x+step*xstep, y+step*ystep, z+step*zstep, this.hasEnchantment(Enchantment.silkTouch)));
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
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		step = NBT.getInteger("step");
		jammed = NBT.getBoolean("jam");
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
	public void makeBeam(World world, int x, int y, int z, int meta) {}

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
		if (torque < mintorque)
			return 15;
		if (power < reqpow)
			return 15;
		return 0;
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
