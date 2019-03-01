/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.GeoStrata.Registry.RockShapes;
import Reika.GeoStrata.Registry.RockTypes;
import Reika.RotaryCraft.API.Event.PileDriverImpactEvent;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityPileDriver extends TileEntityPowerReceiver {


	public int step = 0;
	private int step2 = 0;
	private boolean climbing  = false;
	private boolean active = false;
	private boolean smashed = false;

	public static final int BASEPOWER = 16384; //16 kW per meter to lift the hammer (P = mg x dh/dt)
	public static final int MINTIME = 1;
	public static final int BASESPEED = 300;

	private static final BlockMap<Integer> HITS_PER_BLOCK = new BlockMap();
	private static final BlockMap<BlockKey> BLOCK_CONVERSION = new BlockMap();

	private final HashMap<Coordinate, HitCount> numHits = new HashMap();

	private static int BITMETA = 4;

	static {
		HITS_PER_BLOCK.put(Blocks.obsidian, 5);
		HITS_PER_BLOCK.put(Blocks.netherrack, -2);
		HITS_PER_BLOCK.put(Blocks.glass, -4);
		HITS_PER_BLOCK.put(Blocks.glowstone, -3);
		HITS_PER_BLOCK.put(Blocks.wool, -1);

		BlockKey to = new BlockKey(Blocks.air);
		BLOCK_CONVERSION.put(Blocks.bedrock, new BlockKey(Blocks.bedrock));
		BLOCK_CONVERSION.put(Blocks.stone, new BlockKey(Blocks.cobblestone));
		BLOCK_CONVERSION.put(BlockRegistry.DECO.getBlockInstance(), ItemStacks.shieldblock.getItemDamage(), new BlockKey(BlockRegistry.DECO.getBlockInstance(), ItemStacks.shieldblock.getItemDamage()));
		BLOCK_CONVERSION.put(BlockRegistry.MININGPIPE.getBlockInstance(), 3, new BlockKey(BlockRegistry.MININGPIPE.getBlockInstance(), 3));
		BLOCK_CONVERSION.put(Blocks.stonebrick, 0, new BlockKey(Blocks.stonebrick, 2));

		if (ModList.GEOSTRATA.isLoaded()) {
			for (int i = 0; i < RockTypes.rockList.length; i++) {
				RockTypes r = RockTypes.rockList[i];
				for (int k = 0; k < RockShapes.shapeList.length; k++) {
					RockShapes s = RockShapes.shapeList[k];
					ItemStack is = r.getItem(s);
					Block b = Block.getBlockFromItem(is.getItem());
					if (s != RockShapes.COBBLE) {
						BLOCK_CONVERSION.put(b, is.getItemDamage(), new BlockKey(r.getItem(RockShapes.COBBLE)));
					}
					if (r == RockTypes.GRANITE || r == RockTypes.HORNFEL) {
						HITS_PER_BLOCK.put(b, is.getItemDamage(), 3);
					}
					else if (r == RockTypes.PERIDOTITE || r == RockTypes.GNEISS || r == RockTypes.SCHIST) {
						HITS_PER_BLOCK.put(b, is.getItemDamage(), 2);
					}
					else if (r == RockTypes.SHALE || r == RockTypes.LIMESTONE) {
						HITS_PER_BLOCK.put(b, is.getItemDamage(), -1);
					}
				}
			}
		}
	}

	private static int getHitCount(Block b, int meta) {
		Integer get = HITS_PER_BLOCK.get(b, meta);
		return get != null ? get.intValue() : 0;
	}

	private void addNausea(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(15, 15, 15); // 5m radius
		List<EntityPlayer> sick = world.getEntitiesWithinAABB(EntityPlayer.class, box);
		for (EntityPlayer ep : sick) {
			if (!ep.capabilities.isCreativeMode)
				ep.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 10));
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(true);
		int speed = BASESPEED;
		int minpower = BASEPOWER*(step+1);
		if (power < minpower || torque < MINTORQUE) {
			return;
		}
		if (power > minpower)
			speed = Math.max(BASESPEED/((int)(power/minpower)), MINTIME);
		tickcount++;

		if (!this.drawPile3(world, x, y, z, speed) && step != 0)
			return;
		climbing = true;
		tickcount = 0;
		if (this.smash(world, x, y-step-1, z)) {
			step += 1;
		}
		this.bounce(world, x, y-step-1, z);
		this.dealDamage(world, x, y-step-1, z);
		this.addNausea(world, x, y-step-1, z);
		SoundRegistry.PILEDRIVER.playSoundAtBlock(world, x, y, z, 1, 1);
	}

	private void bounce(World world, int x, int y, int z) { //bounce entities
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-2, y, z-2, x+3, y+1, z+3).expand(24, 24, 24);
		List<Entity> inzone = world.getEntitiesWithinAABB(Entity.class, zone);
		for (Entity ent : inzone) {
			if (ent != null) {
				if (ent.onGround && !world.isRemote)
					ent.motionY += 0.5 / ReikaMathLibrary.doubpow(ReikaMathLibrary.py3d(ent.posX-x, ent.posY-y, ent.posZ-z), 0.5);
				ent.velocityChanged = true;
			}
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 1:
				read = ForgeDirection.EAST;
				read2 = ForgeDirection.WEST;
				break;
			case 0:
				read = ForgeDirection.NORTH;
				read2 = ForgeDirection.SOUTH;
				break;
		}
	}

	private void dealDamage(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(0.5, 2, 0.5);
		List killed = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < killed.size(); i++) {
			EntityLivingBase el = (EntityLivingBase)killed.get(i);
			if (el != null) {
				float dmg = el.getMaxHealth()*el.getTotalArmorValue();
				if (dmg <= 0)
					dmg = Float.MAX_VALUE;
				el.attackEntityFrom(DamageSource.inWall, dmg); //will kill anything
			}
		}
	}

	private void breakGlass(World world, int x, int y, int z) {
		int range = 5;
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = -range; k <= range; k++) {
					Block b = world.getBlock(x+i, y+j, z+k);
					if (b != Blocks.air) {
						//ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b);
						this.breakGlass_do(world, x+i, y+j, z+k, b);
					}
				}
			}
		}
		AxisAlignedBB nearby = AxisAlignedBB.getBoundingBox(x-range, y-range, z-range, x+1+range, y+1+range, z+1+range);
		List<EntityHanging> inzone = world.getEntitiesWithinAABB(EntityHanging.class, nearby);
		for (EntityHanging ep : inzone) {
			ep.attackEntityFrom(DamageSource.outOfWorld, 100);
		}
	}

	private void breakGlass_do(World world, int x, int y, int z, Block id) {
		ItemStack drop = null;
		int meta = world.getBlockMetadata(x, y, z);
		if (id == Blocks.glass || id == Blocks.glass_pane || id == Blocks.glowstone) {
			id.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
			//world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F);
		}
		if (id == Blocks.cactus || id == Blocks.reeds || id == Blocks.vine ||
				id == Blocks.waterlily || id == Blocks.tallgrass || id == Blocks.sapling ||
				id == Blocks.flower_pot || id == Blocks.skull) {
			id.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
		if (id == Blocks.ice) {
			world.setBlock(x, y, z, Blocks.flowing_water);
			//world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F);
			drop = new ItemStack(Blocks.ice);
		}
		if (id == Blocks.web) {
			world.setBlockToAir(x, y, z);
			drop = new ItemStack(Items.string);
		}/*
    	if (id == Blocks.tnt) {
    		world.setBlockToAir(x, y, z);
            EntityTNTPrimed var6 = new EntityTNTPrimed(world, x+0.5D, y+0.5D, z+0.5D);
            world.spawnEntityInWorld(var6);
            world.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
    	}*/
		if (id instanceof BlockFalling)
			this.makeFall(world, x, y, z, (BlockFalling)id);
		/*if (id == RotaryCraft.miningpipe.blockID && dropmeta != 4)
			world.setBlockToAir(x, y, z);*/
		if (drop == null)
			return;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		EntityItem ent = new EntityItem(world, x, y, z, drop);
		world.spawnEntityInWorld(ent);
	}

	private void makeFall(World world, int x, int y, int z, BlockFalling id) {
		BlockFalling tofall = id;
		if (tofall.func_149831_e(world, x, y-1, z)) {
			byte var8 = 32;
			if (!tofall.fallInstantly && world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8)) {
				if (!world.isRemote) {
					EntityFallingBlock var9 = new EntityFallingBlock(world, x + 0.5F, y + 0.5F, z + 0.5F, tofall, world.getBlockMetadata(x, y, z));
					//tofall.onStartFalling(var9);
					world.spawnEntityInWorld(var9);
				}
			}
			else {
				world.setBlockToAir(x, y, z);
				while (tofall.func_149831_e(world, x, y-1, z) && y > 0)
					--y;
				if (y > 0)
					world.setBlock(x, y, z, tofall);
			}
		}
	}

	private ArrayList<ItemStack> getDrops(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		;/*
		if (TileEntityBorer.isMineableBedrock(world, x, y, z))
			return ReikaJavaLibrary.makeListFrom(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust.copy(), DifficultyEffects.BEDROCKDUST.getInt()));
		else*/
		return b != null ? b.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0) : new ArrayList();
	}

	private BlockKey getBlockProduct(World world, int x, int y, int z, Block id, int meta) {
		BlockKey to = BLOCK_CONVERSION.get(id, meta);
		if (to == null)
			to = new BlockKey(Blocks.air);
		if (ReikaBlockHelper.isLiquid(id)) {
			to = new BlockKey(id, meta);
		}
		return to;
	}

	private boolean drawPile3(World world, int x, int y, int z, int speed) {
		if (climbing && tickcount > speed) {
			if (world.getBlock(x, y-step2-2, z) == BlockRegistry.MININGPIPE.getBlockInstance())
				world.setBlockToAir(x, y-step2-2, z);
			step2--;
			if (world.getBlock(x, y-step2, z) == this.getBlockType()) {
				climbing = false;
				//step2++;
				smashed = false;
			}
			else
				world.setBlockToAir(x, y-step2-1, z);
			tickcount = 0;
		}
		if (climbing && tickcount <= speed) {
			//if (world.getBlock(x, y-step2-2, z) == RotaryCraft.miningpipe.blockID)
			//world.setBlock(x, y-step2-2, z, 0);
			if (step2 >= step)
				step2--;
			if (world.getBlock(x, y-step2, z) == this.getBlockType()) {
				climbing = false;
				//step2++;
				smashed = false;
			}
			else
				world.setBlockToAir(x, y-step2-1, z);
			//this.tickcount = 0;
		}
		world.markBlockForUpdate(x, y-step2-1, z);
		if (!climbing){
			if (ReikaWorldHelper.getMaterial(world, x, y-step2-1, z) == Material.water) {
				world.spawnParticle("splash", x, y-step2+1, z, -0.2, 0.4, -0.2);
				world.spawnParticle("splash", x+0.5, y-step2+1, z, 0, 0.4, -0.2);
				world.spawnParticle("splash", x+1, y-step2+1, z, 0.2, 0.4, -0.2);
				world.spawnParticle("splash", x, y-step2+1, z+0.5, -0.2, 0.4, 0);
				world.spawnParticle("splash", x, y-step2+1, z+1, -0.2, 0.4, 0.2);
				world.spawnParticle("splash", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2);
				world.spawnParticle("splash", x+1, y-step2+1, z+0.5, 0.2, 0.4, 0);
				world.spawnParticle("splash", x+1, y-step2+1, z+1, 0.2, 0.4, 0.2);

				world.spawnParticle("splash", x, y-step2+1, z, -0.2+0.4*rand.nextFloat(), 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("splash", x+0.5, y-step2+1, z, 0, 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("splash", x+1, y-step2+1, z, 0.2-0.4*rand.nextFloat(), 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("splash", x, y-step2+1, z+0.5, -0.2+0.4*rand.nextFloat(), 0.4, 0);
				world.spawnParticle("splash", x, y-step2+1, z+1, -0.2+0.4*rand.nextFloat(), 0.4, 0.2-0.4*rand.nextFloat());
				world.spawnParticle("splash", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2+0.4*rand.nextFloat());
				world.spawnParticle("splash", x+1, y-step2+1, z+0.5, 0.2-0.4*rand.nextFloat(), 0.4, 0);
				world.spawnParticle("splash", x+1, y-step2+1, z+1, 0.2-0.4*rand.nextFloat(), 0.4, 0.2-0.4*rand.nextFloat());

				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("splash", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

				world.playSoundEffect(x+0.5, y-step2, z+0.5, "random.splash", 1F, 1F);
			}
			if (ReikaWorldHelper.getMaterial(world, x, y-step2-1, z) == Material.lava) {
				world.spawnParticle("lava", x, y-step2+1, z, -0.2, 0.4, -0.2);
				world.spawnParticle("lava", x+0.5, y-step2+1, z, 0, 0.4, -0.2);
				world.spawnParticle("lava", x+1, y-step2+1, z, 0.2, 0.4, -0.2);
				world.spawnParticle("lava", x, y-step2+1, z+0.5, -0.2, 0.4, 0);
				world.spawnParticle("lava", x, y-step2+1, z+1, -0.2, 0.4, 0.2);
				world.spawnParticle("lava", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2);
				world.spawnParticle("lava", x+1, y-step2+1, z+0.5, 0.2, 0.4, 0);
				world.spawnParticle("lava", x+1, y-step2+1, z+1, 0.2, 0.4, 0.2);

				world.spawnParticle("lava", x, y-step2+1, z, -0.2+0.4*rand.nextFloat(), 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("lava", x+0.5, y-step2+1, z, 0, 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("lava", x+1, y-step2+1, z, 0.2-0.4*rand.nextFloat(), 0.4, -0.2+0.4*rand.nextFloat());
				world.spawnParticle("lava", x, y-step2+1, z+0.5, -0.2+0.4*rand.nextFloat(), 0.4, 0);
				world.spawnParticle("lava", x, y-step2+1, z+1, -0.2+0.4*rand.nextFloat(), 0.4, 0.2-0.4*rand.nextFloat());
				world.spawnParticle("lava", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2+0.4*rand.nextFloat());
				world.spawnParticle("lava", x+1, y-step2+1, z+0.5, 0.2-0.4*rand.nextFloat(), 0.4, 0);
				world.spawnParticle("lava", x+1, y-step2+1, z+1, 0.2-0.4*rand.nextFloat(), 0.4, 0.2-0.4*rand.nextFloat());

				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				world.spawnParticle("lava", x+rand.nextFloat(), y-step2+1, z+rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

				world.playSoundEffect(x+0.5, y-step2, z+0.5, "random.fizz", 1F, 1F);
			}
			world.setBlock(x, y-step2-1, z, BlockRegistry.MININGPIPE.getBlockInstance(), BITMETA, 3);
			step2++;
		}/*
		if (step2 == step) {
			if (world.getBlock(x, y-step2-2, z) == 0)
				world.setBlock(x, y-step2-2, z, RotaryCraft.miningpipe.blockID, BITMETA);
		}*/
		if (world.getBlock(x, y-step2-1, z) == Blocks.air) {
			while(world.getBlock(x, y-step2-2, z) == Blocks.air && y-step2-2 > 0 && step == step2) {
				step++;
				step2 = step;
			}
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d    %d", step, step2, y-step2-2));
		return (step2 == step);
	}

	private boolean smash(World world, int x, int y, int z) {
		MinecraftForge.EVENT_BUS.post(new PileDriverImpactEvent(this, x, y, z));
		boolean cleared = true;
		smashed = true;
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				Block id = world.getBlock(x+i, y, z+j);
				int meta = world.getBlockMetadata(x+i, y, z+j);
				if (!world.isRemote && ReikaPlayerAPI.playerCanBreakAt((WorldServer)world, x+i, y, z+j, id, meta, this.getServerPlacer())) {
					if (id != Blocks.air && i*j != 4 && i*j != -4) {
						if (id == Blocks.mob_spawner) {
							TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getTileEntity(x+i, y, z+j);
							if (spw != null) {
								this.spawnSpawner(world, x+i, y, z+j, spw);
							}
						}
						for (int h = 1; h <= 4; h++) {
							Block id2 = world.getBlock(x+i, y-h, z+j);
							int meta2 = world.getBlockMetadata(x+i, y-h, z+j);
							int hits = this.getHitCount(id2, meta2);
							if (hits < 0 && Math.abs(hits) >= h) {
								this.checkIncrementAndBreak(world, x+i, y-h, z+j, id2, meta2);
							}
						}
						this.checkIncrementAndBreak(world, x+i, y, z+j, id, meta);
					}
				}
			}
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("FDD");
		SoundRegistry.PILEDRIVER.playSoundAtBlock(world, x, y, z, 1, 1);
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				if (i*j != 4 && i*j != -4 && world.getBlock(x+i, y, z+j) != Blocks.air && ReikaWorldHelper.getMaterial(world, x+i, y, z+j) != Material.water && ReikaWorldHelper.getMaterial(world, x+i, y, z+j) != Material.lava) {
					cleared = false;
					//world.setBlock(x, y, z, RotaryCraft.miningpipe.blockID, BITMETA);
				}
			}
		}
		this.breakGlass(world, x, y, z);
		return cleared;
	}

	private void checkIncrementAndBreak(World world, int x, int y, int z, Block id, int meta) {
		Coordinate loc = new Coordinate(x, y, z);
		HitCount c = numHits.get(loc);
		boolean flag = false;
		if (c != null) {
			if (c.hit()) {
				numHits.remove(loc);
				flag = true;
			}
		}
		else {
			int ct = this.getHitCount(id, meta);
			if (ct <= 0) {
				flag = true;
			}
			else {
				c = new HitCount(ct);
				numHits.put(loc, c);
			}
		}
		if (flag) {
			BlockKey blockTo = this.getBlockProduct(world, x, y, z, id, meta);
			ArrayList<ItemStack> li = this.getDrops(world, x, y, z);
			if (!world.isRemote)
				blockTo.place(world, x, y, z);
			if (blockTo.blockID == Blocks.air) {
				//Blocks.blocksList[id].dropBlockAsItem(world, x+i, y, z+j, meta, 0);
				ReikaItemHelper.dropItems(world, x, y, z, li);
			}
			world.markBlockForUpdate(x, y, z);
		}
	}

	private void spawnSpawner(World world, int x, int y, int z, TileEntityMobSpawner spw) {
		if (world.isRemote)
			return;
		ItemStack is = ItemRegistry.SPAWNER.getStackOf();
		ReikaSpawnerHelper.addMobNBTToItem(is, spw);
		EntityItem ent = new EntityItem(world, x, y, z, is);
		world.spawnEntityInWorld(ent);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("step", step);
		NBT.setInteger("step2", step2);
		NBT.setBoolean("active", active);
		NBT.setBoolean("climbing", climbing);
		NBT.setBoolean("smashed", smashed);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		step = NBT.getInteger("step");
		step2 = NBT.getInteger("step2");
		climbing = NBT.getBoolean("climbing");
		active = NBT.getBoolean("active");
		smashed = NBT.getBoolean("smashed");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < BASEPOWER*(step+1) || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PILEDRIVER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private static class HitCount {

		private final int maxHits;
		private int hits;

		private HitCount(int max) {
			maxHits = max;
		}

		private boolean hit() {
			hits++;
			return hits >= maxHits;
		}

	}

}
