/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.Block.SemiTransparent;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Event.HeatRayNetherDetonationEvent;
import Reika.RotaryCraft.API.Interfaces.Laserable;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityHeatRay extends TileEntityBeamMachine implements RangedEffect {

	/** Rate of conversion - one power++ = 1/falloff ++ blocks range */
	public static final int FALLOFF = 256;

	private static final HashMap<BlockKey, LaserEffect> blockEffects = new HashMap();
	private static final LaserEffect deletionEffect = new DeletionLaserEffect();
	private static final LaserEffect meltEffectLava = new MeltLaserEffect(FluidRegistry.LAVA);
	private static final LaserEffect meltEffectWater = new MeltLaserEffect(FluidRegistry.WATER);
	private static final LaserEffect igniteEffect = new IgnitionLaserEffect();
	private static final LaserEffect glassifyEffect = new BlockChangeLaserEffect(Blocks.glass);
	private static final LaserEffect sandifyEffect = new BlockChangeLaserEffect(Blocks.sand);

	static {
		addBlockEffect(Blocks.tallgrass, deletionEffect);
		addBlockEffect(Blocks.red_mushroom, deletionEffect);
		addBlockEffect(Blocks.brown_mushroom, deletionEffect);
		addBlockEffect(Blocks.red_flower, deletionEffect);
		addBlockEffect(Blocks.yellow_flower, deletionEffect);
		addBlockEffect(Blocks.wheat, deletionEffect);
		addBlockEffect(Blocks.pumpkin_stem, deletionEffect);
		addBlockEffect(Blocks.melon_stem, deletionEffect);
		addBlockEffect(Blocks.potatoes, deletionEffect);
		addBlockEffect(Blocks.carrots, deletionEffect);
		addBlockEffect(Blocks.deadbush, deletionEffect);
		addBlockEffect(Blocks.vine, deletionEffect);
		addBlockEffect(Blocks.waterlily, deletionEffect);
		addBlockEffect(Blocks.web, deletionEffect);
		addBlockEffect(Blocks.snow_layer, deletionEffect);

		addBlockEffect(Blocks.water, deletionEffect);
		addBlockEffect(Blocks.flowing_water, deletionEffect);

		addBlockEffect(Blocks.leaves, igniteEffect);
		addBlockEffect(Blocks.leaves2, igniteEffect);
		addBlockEffect(Blocks.log, igniteEffect);
		addBlockEffect(Blocks.log2, igniteEffect);

		addBlockEffect(Blocks.cobblestone, meltEffectLava);
		addBlockEffect(Blocks.stone, meltEffectLava);
		addBlockEffect(Blocks.sandstone, meltEffectLava);
		addBlockEffect(Blocks.stonebrick, meltEffectLava);

		addBlockEffect(Blocks.ice, meltEffectWater);
		addBlockEffect(Blocks.snow, meltEffectWater);

		addBlockEffect(Blocks.gravel, new BlockChangeLaserEffect(Blocks.cobblestone));
		addBlockEffect(Blocks.mossy_cobblestone, new BlockChangeLaserEffect(Blocks.cobblestone));

		addBlockEffect(Blocks.grass, new BlockChangeLaserEffect(Blocks.dirt));
		addBlockEffect(Blocks.mycelium, new BlockChangeLaserEffect(Blocks.dirt));

		addBlockEffect(Blocks.dirt, sandifyEffect);
		addBlockEffect(Blocks.farmland, sandifyEffect);

		addBlockEffect(Blocks.sand, glassifyEffect);

		addBlockEffect(Blocks.tnt, new LaserEffect() {
			@Override
			public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
				world.setBlockToAir(x, y, z);
				EntityTNTPrimed var6 = new EntityTNTPrimed(world, x+0.5D, y+0.5D, z+0.5D, null);
				if (!world.isRemote)
					world.spawnEntityInWorld(var6);
				world.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
				world.spawnParticle("lava", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), 0, 0, 0);
				return false;
			}

			@Override
			public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
				return 0;
			}
		});

		addBlockEffect(Blocks.netherrack, new LaserEffect() {
			@Override
			public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
				if (!world.isRemote) {
					world.newExplosion(null, x+0.5, y+0.5, z+0.5, 5F, true, true);
					MinecraftForge.EVENT_BUS.post(new HeatRayNetherDetonationEvent(world, x, y, z));
					if (world.provider.dimensionId == -1 && range >= 500)
						RotaryAchievements.NETHERHEATRAY.triggerAchievement(te.getPlacer());
				}
				return true;
			}

			@Override
			public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
				return tickcount < 6 ? -1 : 0;
			}
		});
	}

	private static void addBlockEffect(Block b, LaserEffect e) {
		blockEffects.put(new BlockKey(b), e);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		power = (long)omega*(long)torque;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		//if ((world.getTotalWorldTime()&2) == 2) //halves load
		this.makeBeam(world, x, y, z, meta);
	}

	@Override
	protected void makeBeam(World world, int x, int y, int z, int metadata) {
		boolean blocked = false;
		int step;
		if (power >= MINPOWER) { //2MW+ (real military laser)
			int maxdist = this.getRange();
			Random r = new Random(new WorldLocation(this).hashCode() | tickcount << 24);
			r.nextBoolean();
			r.nextBoolean();
			for (step = 1; step < maxdist && (step < this.getMaxRange() || this.getMaxRange() == -1) && !blocked; step++) {
				int dx = x+step*facing.offsetX;
				int dy = y+step*facing.offsetY;
				int dz = z+step*facing.offsetZ;
				Block id = world.getBlock(dx, dy, dz);
				int meta2 = world.getBlockMetadata(dx, dy, dz);
				if (id != Blocks.air && id.isFlammable(world, dx, dy, dz, ForgeDirection.UP))
					ReikaWorldHelper.ignite(world, dx, dy, dz);
				//ReikaJavaLibrary.pConsole(Blocks.blocksList[id]);
				if (this.affectBlock(world, dx, dy, dz, step, id, meta2, maxdist, r)) {
					blocked = true;
					tickcount = 0;
				}
				if (id != world.getBlock(dx, dy, dz) || meta2 != world.getBlockMetadata(dx, dy, dz))
					world.markBlockForUpdate(dx, dy, dz);
				if (id instanceof SemiTransparent) {
					SemiTransparent st = (SemiTransparent)id;
					if (st.isOpaque(meta2))
						blocked = true;
				}
				else if (id.isOpaqueCube())
					blocked = true; //break loop
			}
			AxisAlignedBB zone = this.getBurnZone(metadata, step);
			List<Entity> inzone = worldObj.getEntitiesWithinAABB(Entity.class, zone);
			for (Entity caught : inzone) {
				if (!(caught instanceof EntityItem)) //Do not burn drops
					caught.setFire(this.getBurnTime());	// 1 Hearts worth of fire at min power, +1 heart for every 65kW extra
				if (caught instanceof EntityTNTPrimed)
					world.spawnParticle("lava", caught.posX+rand.nextFloat(), caught.posY+rand.nextFloat(), caught.posZ+rand.nextFloat(), 0, 0, 0);
				if (caught instanceof Laserable) {
					((Laserable)caught).whenInBeam(world, MathHelper.floor_double(caught.posX), MathHelper.floor_double(caught.posY), MathHelper.floor_double(caught.posZ), power, step);
				}
			}
		}
	}

	public int getBurnTime() {
		return 2+(int)(16L*power/MINPOWER);
	}

	public int getRange() {
		int r = (int)(8L+(power-MINPOWER)/FALLOFF);
		if (r > this.getMaxRange())
			return this.getMaxRange();
		return r;
	}

	private AxisAlignedBB getBurnZone(int meta, int step) {
		int minx = 0;
		int miny = 0;
		int minz = 0;
		int maxx = 0;
		int maxy = 0;
		int maxz = 0;

		switch (meta) {
			case 0:
				minx = xCoord-step;
				maxx = xCoord-1;
				miny = yCoord;
				maxy = yCoord;
				minz = zCoord;
				maxz = zCoord;
				break;
			case 1:
				minx = xCoord+1;
				maxx = xCoord+step;
				miny = yCoord;
				maxy = yCoord+1;
				minz = zCoord;
				maxz = zCoord+1;
				break;
			case 2:
				maxz = zCoord+step;
				minz = zCoord+1;
				miny = yCoord;
				maxy = yCoord+1;
				minx = xCoord;
				maxx = xCoord+1;
				break;
			case 3:
				maxz = zCoord-1;
				minz = zCoord-step;
				miny = yCoord;
				maxy = yCoord+1;
				minx = xCoord;
				maxx = xCoord+1;
				break;
			case 4:
				miny = yCoord;
				maxz = zCoord+1;
				miny = yCoord+1;
				maxy = yCoord+step;
				minx = xCoord;
				maxx = xCoord+1;
				break;
			case 5:
				minz = zCoord;
				maxz = zCoord+1;
				miny = yCoord-1;
				maxy = yCoord-step-1;
				minx = xCoord;
				maxx = xCoord+1;
				break;
		}
		/*ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, miny, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, minx, maxy, minz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, maxy, maxz, 20);
    	ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, maxx, miny, maxz, 20);*/
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);//.expand(0.25D, 0.25D, 0.25D);
	}

	private boolean affectBlock(World world, int dx, int dy, int dz, int step, Block id, int metadata, int maxdist, Random r) {
		if (id == Blocks.air)
			return false;
		if (id.hasTileEntity(metadata)) {
			TileEntity te = world.getTileEntity(dx, dy, dz);
			if (te instanceof Laserable) {
				((Laserable)te).whenInBeam(world, dx, dy, dz, power, step);
				if (((Laserable)te).blockBeam(world, dx, dy, dz, power))
					return true;
			}
		}
		if (id instanceof Laserable) {
			((Laserable)id).whenInBeam(world, dx, dy, dz, power, step);
			return ((Laserable)id).blockBeam(world, dx, dy, dz, power);
		}
		else if (ConfigRegistry.ATTACKBLOCKS.getState()) {
			LaserEffect e = blockEffects.get(new BlockKey(id, metadata));
			if (e != null) {
				long surp = power/MINPOWER;//ReikaMathLibrary.logbase2(this.power-)
				int n = e.getChanceDenom(id, metadata, surp, step, tickcount);
				if (n < 0)
					return true;
				n = Math.max(1, n);
				if (r.nextInt(n) == 0) {
					return e.doEffect(world, dx, dy, dz, power, step, tickcount, this);
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.HEATRAY;
	}

	@Override
	public int getMaxRange() {
		return Math.max(64, ConfigRegistry.HEATRAYRANGE.getValue());
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private static class BlockChangeLaserEffect implements LaserEffect {

		private final BlockKey replacement;

		private BlockChangeLaserEffect(Block bk) {
			this(new BlockKey(bk, 0));
		}

		private BlockChangeLaserEffect(BlockKey bk) {
			replacement = bk;
		}

		@Override
		public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
			replacement.place(world, x, y, z);
			return true;
		}

		@Override
		public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
			return (int)Math.min(Integer.MAX_VALUE, (32 * dist)/surplus);
		}

	}

	private static class IgnitionLaserEffect implements LaserEffect {

		@Override
		public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
			ReikaWorldHelper.ignite(world, x, y, z);
			return false;
		}

		@Override
		public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
			return 0;
		}

	}

	private static class MeltLaserEffect implements LaserEffect {

		private final Fluid fluid;

		private MeltLaserEffect(Fluid f) {
			fluid = f;
		}

		@Override
		public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
			world.setBlock(x, y, z, this.getBlock(), 0, 3);
			world.getBlock(x, y, z).onNeighborBlockChange(world, x, y, z, Blocks.air);
			if (fluid == FluidRegistry.LAVA)
				world.spawnParticle("lava", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), 0, 0, 0);
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
			return false;
		}

		private Block getBlock() {
			if (fluid == FluidRegistry.WATER)
				return Blocks.flowing_water;
			if (fluid == FluidRegistry.LAVA)
				return Blocks.flowing_lava;
			return fluid.getBlock();
		}

		@Override
		public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
			int d = 2;
			if (fluid == FluidRegistry.WATER)
				d = 8;
			return (int)Math.min(Integer.MAX_VALUE, (32 * dist / (d*surplus)));
		}

	}

	private static class DeletionLaserEffect implements LaserEffect {

		@Override
		public boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te) {
			world.setBlockToAir(x, y, z);
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
			return false;
		}

		@Override
		public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount) {
			return (int)Math.min(Integer.MAX_VALUE, (32 * dist / (8 * surplus)));
		}

	}

	private static interface LaserEffect {

		public int getChanceDenom(Block b, int meta, long surplus, int dist, int tickcount);

		boolean doEffect(World world, int x, int y, int z, long power, int range, int tickcount, TileEntityHeatRay te);

	}
}
