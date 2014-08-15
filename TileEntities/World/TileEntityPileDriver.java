/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
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

	public static int BITMETA = 4;

	public void addNausea(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(15, 15, 15); // 5m radius
		List sick = world.getEntitiesWithinAABB(EntityPlayer.class, box);
		for (int k = 0; k < sick.size(); k++) {
			EntityPlayer ep = (EntityPlayer)sick.get(k);
			if (ep != null)
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
			speed = ReikaMathLibrary.extrema(BASESPEED/((int)(power/minpower)), MINTIME, "max");
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

	public void bounce(World world, int x, int y, int z) { //bounce entities
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-2, y, z-2, x+3, y+1, z+3).expand(24, 24, 24);
		List inzone = world.getEntitiesWithinAABB(Entity.class, zone);
		for (int i = 0; i < inzone.size(); i++) {
			Entity ent = (Entity)inzone.get(i);
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

	public void dealDamage(World world, int x, int y, int z) {
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

	public void breakGlass(World world, int x, int y, int z) {
		int range = 5;
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = -range; k <= range; k++) {
					Block b = world.getBlock(x+i, y+j, z+k);
					this.breakGlass_do(world, x+i, y+j, z+k, b);
				}
			}
		}
		AxisAlignedBB nearby = AxisAlignedBB.getBoundingBox(x-range, y-range, z-range, x+1+range, y+1+range, z+1+range);
		List inzone = world.getEntitiesWithinAABB(EntityPainting.class, nearby);
		for (int p = 0; p < inzone.size(); p++) {
			EntityPainting ep = (EntityPainting)inzone.get(p);
			if (ep != null)
				ep.attackEntityFrom(DamageSource.outOfWorld, 100);
		}
		inzone = world.getEntitiesWithinAABB(EntityItemFrame.class, nearby);
		for (int p = 0; p < inzone.size(); p++) {
			EntityItemFrame eif = (EntityItemFrame)inzone.get(p);
			if (eif != null)
				eif.attackEntityFrom(DamageSource.outOfWorld, 100);
		}
	}

	public void breakGlass_do(World world, int x, int y, int z, Block id) {
		ItemStack drop = null;
		int meta = world.getBlockMetadata(x, y, z);
		if (id == Blocks.glass || id == Blocks.glass_pane || id == Blocks.glowstone) {
			id.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F);
		}
		if (id == Blocks.cactus || id == Blocks.reeds || id == Blocks.vine ||
				id == Blocks.waterlily || id == Blocks.tallgrass || id == Blocks.sapling ||
				id == Blocks.flower_pot || id == Blocks.skull) {
			id.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
		if (id == Blocks.ice) {
			world.setBlock(x, y, z, Blocks.flowing_water);
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F);
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
		if (id == Blocks.sand || id == Blocks.gravel)
			this.makeFall(world, x, y, z, id);
		/*if (id == RotaryCraft.miningpipe.blockID && dropmeta != 4)
			world.setBlockToAir(x, y, z);*/
		if (drop == null)
			return;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		EntityItem ent = new EntityItem(world, x, y, z, drop);
		world.spawnEntityInWorld(ent);
	}

	public void makeFall(World world, int x, int y, int z, Block id) {
		BlockSand tofall = (BlockSand)id;
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

	public ItemStack getBlockProduct(World world, int x, int y, int z, Block id, int meta) {
		ItemStack to = new ItemStack(Blocks.air);
		if (id == Blocks.bedrock/* && !TileEntityBorer.isMineableBedrock(world, x, y, z)*/) //does not break bedrock unless TF
			to = new ItemStack(id);
		if (id == BlockRegistry.DECO.getBlockInstance() && meta == ItemStacks.shieldblock.getItemDamage()) {
			to = new ItemStack(id);
			to.setItemDamage(meta);
		}
		if (id == Blocks.stone)
			to = new ItemStack(Blocks.cobblestone);
		if (ModList.GEOSTRATA.isLoaded()) {
			RockShapes s = RockShapes.getShape(id, meta);
			RockTypes r = RockTypes.getTypeFromID(id);
			if (s == RockShapes.SMOOTH) {
				to = r.getItem(RockShapes.COBBLE);
			}
		}
		if (id == Blocks.stonebrick && meta == 0) {
			to = new ItemStack(id);
			to.setItemDamage(2);
		}
		if (id == Blocks.obsidian) {
			if (meta < 4) {
				to = new ItemStack(id);
				to.setItemDamage(meta+1);
			}
			else {

			}
		}
		if (id == Blocks.flowing_water || id == Blocks.water ||
				id == Blocks.flowing_lava || id == Blocks.lava) {
			to = new ItemStack(id);
			to.setItemDamage(meta);
		}
		if (id == BlockRegistry.MININGPIPE.getBlockInstance() && meta == 3) {
			to = new ItemStack(id);
			to.setItemDamage(meta);
		}
		return to;
	}

	public boolean drawPile3(World world, int x, int y, int z, int speed) {
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

	public boolean smash(World world, int x, int y, int z) {
		MinecraftForge.EVENT_BUS.post(new PileDriverImpactEvent(this, x, y, z));
		boolean cleared = true;
		smashed = true;
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				Block id = world.getBlock(x+i, y, z+j);
				int meta = world.getBlockMetadata(x+i, y, z+j);
				if (!world.isRemote && ReikaPlayerAPI.playerCanBreakAt((WorldServer)world, x+i, y, z+j, id, meta, this.getPlacer())) {
					if (id != Blocks.air && i*j != 4 && i*j != -4) {
						if (id == Blocks.mob_spawner) {
							TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getTileEntity(x+i, y, z+j);
							if (spw != null) {
								this.spawnSpawner(world, x+i, y, z+j, spw);
							}
						}
						if (world.getBlock(x+i, y-1, z+j) == Blocks.netherrack) {
							Blocks.netherrack.dropBlockAsItem(world, x+i, y-1, z+j, 0, 0);
							world.setBlockToAir(x+i, y-1, z+j);
							world.markBlockForUpdate(x+i, y-1, z+j);
							//this.step++;
						}
						if (world.getBlock(x+i, y-2, z+j) == Blocks.netherrack) {
							Blocks.netherrack.dropBlockAsItem(world, x+i, y-2, z+j, 0, 0);
							world.setBlockToAir(x+i, y-2, z+j);
							world.markBlockForUpdate(x+i, y-2, z+j);
							//this.step++;
						}
						ItemStack blockTo = this.getBlockProduct(world, x+i, y, z+j, id, meta);
						ArrayList<ItemStack> li = this.getDrops(world, x+i, y, z+j);
						if (!world.isRemote)
							ReikaWorldHelper.setBlock(world, x+i, y, z+j, blockTo);
						if (ReikaItemHelper.isAirItem(blockTo)) {
							//Blocks.blocksList[id].dropBlockAsItem(world, x+i, y, z+j, meta, 0);
							ReikaItemHelper.dropItems(world, x+i, y, z+j, li);
						}
						world.markBlockForUpdate(x+i, y, z+j);
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

	public void spawnSpawner(World world, int x, int y, int z, TileEntityMobSpawner spw) {
		if (world.isRemote)
			return;
		ItemStack is = ItemRegistry.SPAWNER.getStackOf();
		ReikaSpawnerHelper.addMobNBTToItem(is, spw);
		EntityItem ent = new EntityItem(world, x, y, z, is);
		world.spawnEntityInWorld(ent);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
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

	/**
	 * Reads a tile entity from NBT.
	 */
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

}