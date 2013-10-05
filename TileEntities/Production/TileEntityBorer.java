/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
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
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaTwilightHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityBorer extends TileEntityBeamMachine implements EnchantableMachine, GuiController {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	private static List<Integer> silkTouchBlacklist = new ArrayList<Integer>();

	private int pipemeta2 = 0;

	public boolean drops = true;

	public int reqpow;
	public int mintorque;

	/** Power required to break a block, per 0.1F hardness */
	public static final int DIGPOWER = 64;
	public static final int OBSIDIANTORQUE = 512;

	/** Rate of conversion - one power++ = 1/falloff ++ range - not used by this machine*/
	public static final int FALLOFF = 1024; //1kW per meter right now

	public int step = 1;


	/** Digging Profile */
	public int mode;

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

	@Override
	protected int getActiveTexture() {
		return power > 0 && power >= reqpow && torque >= mintorque ? 1 : 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		//ReikaJavaLibrary.pConsole(this.hasEnchantments());

		//enchantments.put(Enchantment.efficiency, 4);
		//enchantments.put(Enchantment.silkTouch, 1);

		//ReikaJavaLibrary.pConsole(this.getEnchantment(Enchantment.efficiency));

		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		power = omega*torque;
		if (power == 0)
			jammed = false;
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
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F, 1F);
			for (int i = 0; i < 6; i++) {
				world.spawnParticle("smoke", x+par5Random.nextDouble(), y+1+par5Random.nextDouble()*0.2, z+par5Random.nextDouble(), 0, 0, 0);
				world.spawnParticle("crit", x+par5Random.nextDouble(), y+1+par5Random.nextDouble()*0.2, z+par5Random.nextDouble(), 0, 0, 0);
			}
		}

		if (this.hasEnchantments()) {
			for (int i = 0; i < 8; i++) {
				world.spawnParticle("portal", -0.5+x+2*par5Random.nextDouble(), y+par5Random.nextDouble(), -0.5+z+2*par5Random.nextDouble(), 0, 0, 0);
			}
		}

		if (nodig)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(cutShape[0][0]));
		if (omega <= 0)
			return;
		if (this.operationComplete((int)(tickcount*ReikaEnchantmentHelper.getEfficiencyMultiplier(this.getEnchantment(Enchantment.efficiency))), 0)) {
			this.calcReqPower(world, x, y, z, meta);

			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.reqpow));
			if (power > reqpow && reqpow != -1) {
				this.calcReqPower(world, x, y, z, meta);
				this.dig(world, x, y, z, meta);
				jammed = false;
			}
			else {
				jammed = true;
			}
			tickcount = 0;
			if (reqpow == -1) {
				//step = ReikaMathLibrary.extrema(step-1, 1, "max");
			}
		}
	}

	public void reqPowAdd(World world, int xread, int yread, int zread, int metadata) {
		if (world.getBlockId(xread, yread, zread) != 0 && !(world.getBlockMaterial(xread, yread, zread) == Material.water || world.getBlockMaterial(xread, yread, zread) == Material.lava)) {
			reqpow += (int)(DIGPOWER*10*Block.blocksList[world.getBlockId(xread, yread, zread)].getBlockHardness(world, xread, yread, zread));
			if (ReikaMathLibrary.ceil2exp((int)(16*10*Block.blocksList[world.getBlockId(xread, yread, zread)].getBlockHardness(world, xread, yread, zread))) > mintorque)
				mintorque = ReikaMathLibrary.ceil2exp((int)(16*10*Block.blocksList[world.getBlockId(xread, yread, zread)].getBlockHardness(world, xread, yread, zread)));
			if (Block.blocksList[world.getBlockId(xread, yread, zread)].getBlockHardness(world, xread, yread, zread) < 0 && !this.isLabyBedrock(world, xread, yread, zread))
				reqpow = -1;

			if (this.isLabyBedrock(world, xread, yread, zread)) {
				mintorque = 8192;
				reqpow += 262144;
			}
		}
		//ReikaJavaLibrary.pConsole(mintorque);
	}

	private boolean isLabyBedrock(World world, int x, int y, int z) {
		return y > 4 && world.getBlockId(x, y, z) == Block.bedrock.blockID && world.provider.dimensionId == ReikaTwilightHelper.getDimensionID();
	}

	public void calcReqPower(World world, int x, int y, int z, int metadata) {
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
					this.reqPowAdd(world, xread, yread, zread, metadata);
					//ReikaJavaLibrary.pConsole(reqpow+" for "+world.getBlockId(xread, yread, zread)+":"+world.getBlockMetadata(xread, yread, zread));
				}
			}
		}

		lowtorque = mintorque;

		if (torque < lowtorque)
			reqpow = -1;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", lowtorque));
	}



	public void support(World world, int x, int y, int z, int metadata) {
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
					int id = world.getBlockId(xread, yread+1, zread);
					if (id == Block.sand.blockID || id == Block.gravel.blockID)
						if (this.checkTop(i, j)) {
							if (id == Block.sand.blockID)
								ReikaWorldHelper.legacySetBlockWithNotify(world, xread, yread+1, zread, Block.sandStone.blockID);
							else
								ReikaWorldHelper.legacySetBlockWithNotify(world, xread, yread+1, zread, Block.stone.blockID);
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

	public boolean dropBlocks(int xread, int yread, int zread, World world, int x, int y, int z, int id, int meta) {
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.getInstance().mazeStoneID)
			RotaryAchievements.CUTKNOT.triggerAchievement(this.getPlacer());
		TileEntity tile = world.getBlockTileEntity(xread, yread, zread);
		if (tile instanceof RotaryCraftTileEntity)
			return false;
		if (drops && id != 0) {
			if (this.isLabyBedrock(world, xread, yread, zread)) {
				ItemStack is = ItemStacks.bedrockdust.copy();
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, is);
				}
				return true;
			}
			if (id == Block.mobSpawner.blockID) {
				TileEntityMobSpawner spw = (TileEntityMobSpawner)tile;
				if (spw != null) {
					ItemStack is = new ItemStack(RotaryCraft.spawner);
					ReikaSpawnerHelper.addMobNBTToItem(is, spw);
					if (!this.chestCheck(world, x, y, z, is))
						ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, is);
					return true;
				}
			}
			if (tile instanceof IInventory) {
				IInventory ii = (IInventory)tile;
				List<ItemStack> contents = ReikaInventoryHelper.getWholeInventory(ii);
				ReikaInventoryHelper.clearInventory(ii);
				//ReikaJavaLibrary.pConsole(FMLCommonHandler.instance().getEffectiveSide()+" for "+contents);
				for (int i = 0; i < contents.size(); i++) {
					ItemStack is = contents.get(i);
					boolean fits = this.chestCheck(world, x, y, z, is);
					//ReikaJavaLibrary.pConsole(fits+" for "+is+" on "+FMLCommonHandler.instance().getEffectiveSide());
					if (!fits) {
						ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, is);
					}
				}
			}
			int metaread = world.getBlockMetadata(xread, yread, zread);
			if (this.getEnchantment(Enchantment.silkTouch) > 0 && this.canSilk(id, metaread)) {
				int ismeta = 0;
				if (this.matchMeta(id))
					ismeta = metaread;
				ItemStack is = new ItemStack(id, 1, ismeta);
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, is);
				}
				return true;
			}
			ArrayList<ItemStack> items = Block.blocksList[id].getBlockDropped(world, xread, yread, zread, metaread, this.getEnchantment(Enchantment.fortune));
			for (int i = 0; i < items.size(); i++) {
				ItemStack is = items.get(i);
				if (!this.chestCheck(world, x, y, z, is)) {
					ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, is);
				}
			}
		}
		return true;
	}

	private boolean matchMeta(int id) {
		if (id == Block.torchWood.blockID)
			return false;
		return true;
	}

	private boolean canSilk(int id, int meta) {
		if (id == 0)
			return false;
		Block b = Block.blocksList[id];
		if (b.blockMaterial == Material.water)
			return false;
		if (b.blockMaterial == Material.lava)
			return false;
		if (id == RotaryCraft.miningpipe.blockID)
			return false;
		if (BlockRegistry.isMachineBlock(id))
			return false;
		if (b instanceof BlockFluid)
			return false;
		if (b.hasTileEntity(meta))
			return false;
		if (silkTouchBlacklist.contains(id))
			return false;
		return true;
	}

	private boolean chestCheck(World world, int x, int y, int z, ItemStack is) {
		if (is == null)
			return false;
		if (world.isRemote)
			return false;
		TileEntity te = world.getBlockTileEntity(x+1, y, z);
		IInventory ii;
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		te = world.getBlockTileEntity(x-1, y, z);
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		te = world.getBlockTileEntity(x, y+1, z);
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		te = world.getBlockTileEntity(x, y-1, z);
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		te = world.getBlockTileEntity(x, y, z+1);
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		te = world.getBlockTileEntity(x, y, z-1);
		if (te instanceof IInventory) {
			ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		return false;
	}

	public void dig(World world, int x, int y, int z, int metadata) {
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
					xread = x+step*xstep+a*(i-3); yread = y+step*ystep+(4-j); zread = z+step*zstep+b*(i-3);
					if (this.dropBlocks(xread, yread, zread, world, x, y, z, world.getBlockId(xread, yread, zread), world.getBlockMetadata(xread, yread, zread)))
						world.setBlock(xread, yread, zread, RotaryCraft.miningpipe.blockID, pipemeta, 3);
					else {
						step--;
					}
				}
			}
		}
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
		return accepted;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.fortune);
		li.add(Enchantment.silkTouch);
		li.add(Enchantment.efficiency);
		return li;
	}

	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("mode", mode);
		NBT.setBoolean("drops", drops);
		NBT.setInteger("step", step);
		NBT.setBoolean("jam", jammed);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++)
				NBT.setBoolean("cut"+String.valueOf(i*7+j), cutShape[i][j]);
		}
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		mode = NBT.getInteger("mode");
		drops = NBT.getBoolean("drops");
		step = NBT.getInteger("step");
		jammed = NBT.getBoolean("jam");
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++)
				cutShape[i][j] = NBT.getBoolean("cut"+String.valueOf(i*7+j));
		}
		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}
	}

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BORER.ordinal();
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
}
