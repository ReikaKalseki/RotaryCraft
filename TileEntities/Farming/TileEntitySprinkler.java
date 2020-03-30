/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.ModularLogger;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap.CollectionType;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
import Reika.DragonAPI.Interfaces.Registry.CropType;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.ReactorCraft.Entities.EntityRadiation;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.SprinklerBlock;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySprinkler extends SprinklerBlock {

	private static final String LOGGER_ID = "sprinkler_apple";

	private final FieldCache cache = new FieldCache();

	static {
		ModularLogger.instance.addLogger(RotaryCraft.instance, LOGGER_ID);
	}

	@Override
	public void performEffects(World world, int x, int y, int z) {
		RotaryAchievements.SPRINKLER.triggerAchievement(this.getPlacer());
		if (!world.isRemote) {
			this.hydrate(world, x, y, z);
			if (ModList.REACTORCRAFT.isLoaded() && rand.nextInt(2400) == 0)
				this.clearRadiation(world, x, y, z);
		}
		else {
			this.spawnParticles(world, x, y, z);
		}
	}

	@ModDependent(ModList.REACTORCRAFT)
	private void clearRadiation(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, -4, 0).expand(r, 4, r);
		List<EntityRadiation> li = world.getEntitiesWithinAABB(EntityRadiation.class, box);
		for (EntityRadiation e : li) {
			e.clean();
			if (rand.nextBoolean())
				break;
		}
	}

	public void hydrate(World world, int x, int y, int z) {
		int range = this.getRange();
		boolean flag = false;
		for (int i = -range; i <= range; i++) {
			for (int k = -range; k <= range; k++) {
				int dx = x+i;
				int dz = z+k;
				float f = 0.5F+1.5F*(i*i+k*k)/(range*range); //2x rate at center, 0.5x rate at edge
				FieldColumn fe = cache.getOrFindLevel(world, dx, dz, y);
				if (fe.tick(world, f)) {
					fe.recalulateLevel(world);
					flag = true;
				}
				//this.sendParticle(dx, fe.calculatedY, dz, fe.doDripParticles);
			}
		}
		if (cache.tickDirty()) {
			this.syncAllData(true);
		}
	}

	private void sendParticle(int dx, int dy, int dz, boolean drip) {
		ReikaPacketHelper.sendDataPacketWithRadius(RotaryCraft.packetChannel, PacketRegistry.SPRINKLER.ordinal(), this, 48, dx, dy, dz, drip ? 1 : 0);
	}

	@SideOnly(Side.CLIENT)
	public void doParticle(World world, int dx, int dy, int dz, boolean drip) {
		int d = Math.max(1, 5-ConfigRegistry.SPRINKLER.getValue());
		if (rand.nextInt(d) == 0)
			ReikaParticleHelper.RAIN.spawnAt(world, dx+rand.nextDouble(), dy+1, dz+rand.nextDouble());
		if (drip) {
			ReikaParticleHelper.DRIPWATER.spawnAt(world, dx+rand.nextDouble(), dy, dz+rand.nextDouble());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("cache", cache.writeToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		cache.readFromNBT(NBT.getTagList("cache", NBTTypes.COMPOUND.ID));
	}

	/*
	private void sendParticle(ReikaParticleHelper type, double dx, double dy, double dz) {
		ArrayList<Integer> li = ReikaJavaLibrary.makeListFrom(type.ordinal(), 1);
		li.addAll(ReikaJavaLibrary.makeIntListFromArray(ReikaJavaLibrary.splitDoubleToInts(dx)));
		li.addAll(ReikaJavaLibrary.makeIntListFromArray(ReikaJavaLibrary.splitDoubleToInts(dy)));
		li.addAll(ReikaJavaLibrary.makeIntListFromArray(ReikaJavaLibrary.splitDoubleToInts(dz)));
		ReikaPacketHelper.sendDataPacket(DragonAPIInit.packetChannel, PacketIDs.PARTICLEWITHPOS.ordinal(), new PacketTarget.RadiusTarget(this, 32), li);
	}*/

	@SideOnly(Side.CLIENT)
	public void spawnParticles(World world, int x, int y, int z) {

		int d = Math.max(0, ConfigRegistry.SPRINKLER.getValue());
		double ypos = y+0.125;
		double vel;
		double r = this.getRange()/10D;

		double py = y-0.1875D+0.5;
		int n = (rand.nextInt(2) == 0 ? 1 : 0)+rand.nextInt(1+d);
		for (int i = 0; i < n; i++) {
			double px = x-1+2*rand.nextFloat();
			double pz = z-1+2*rand.nextFloat();
			world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
		}

		for (vel = 0; vel < r; vel += 0.1) {
			py = y-0.1875D+0.5;
			n = (rand.nextInt(2) == 0 ? 1 : 0)+rand.nextInt(1+d*4);
			for (int i = 0; i < n; i++) {
				double vx = vel*(-1+rand.nextFloat()*2);
				vx *= 1.05;
				double vz = vel*(-1+rand.nextFloat()*2);
				vz *= 1.05;
				double px = x-1+2*rand.nextFloat();
				double pz = z-1+2*rand.nextFloat();
				world.spawnParticle("splash", px+0.5, py, pz+0.5, vx, 0, vz);
			}
		}

		cache.doParticles(world);
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
		return MachineRegistry.SPRINKLER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 180;
	}

	@Override
	public int getWaterConsumption() {
		return 3;
	}

	@Override
	public ForgeDirection getPipeDirection() {
		return ForgeDirection.UP;
	}

	private static boolean isOpaque(Block b) {
		return b.isOpaqueCube() && !(b instanceof BlockLeavesBase);
	}

	private static enum Effects {
		FIREEXTINGUISH(20),
		CROPTICK(80),
		HYDRATEFARMLAND(15),
		APPLETICK(40);

		private final int randomChance;

		private static final Effects[] list = values();

		private Effects(int c) {
			randomChance = c;
		}

		/** Factor < 1 -> higher rate */
		private boolean doChance(Random rand, float factor) {
			return rand.nextInt(Math.max(1, (int)(randomChance*factor))) == 0;
		}

		private void doEffect(World world, int x, int y, int z, Block b) {
			switch(this) {
				case APPLETICK:
					if (b.getMaterial() == Material.leaves) {
						if (ModularLogger.instance.isEnabled(LOGGER_ID))
							ModularLogger.instance.log(LOGGER_ID, "Found leaf @ "+x+", "+y+", "+z);
						Block b2 = world.getBlock(x, y-1, z);
						if (b2.isAir(world, x, y-1, z)) { //space for an apple; only stop moving down if this
							b.updateTick(world, x, y, z, rand);
							BlockTickEvent.fire(world, x, y, z, b, UpdateFlags.FORCED.flag);
							if (ModularLogger.instance.isEnabled(LOGGER_ID))
								ModularLogger.instance.log(LOGGER_ID, "Ticked apple leaf @ "+x+", "+y+", "+z);
						}
					}
					else if (b.getMaterial() == Material.plants) {
						b.updateTick(world, x, y, z, rand);
						BlockTickEvent.fire(world, x, y, z, b, UpdateFlags.FORCED.flag);
						if (ModularLogger.instance.isEnabled(LOGGER_ID))
							ModularLogger.instance.log(LOGGER_ID, "Ticked apple block @ "+x+", "+y+", "+z);
					}
					break;
				case CROPTICK:
					CropType crop = ReikaCropHelper.getCrop(b);
					int meta = world.getBlockMetadata(x, y, z);
					if (crop == null)
						crop = ModCropList.getModCrop(b, meta);
					if (crop != null) {
						b.updateTick(world, x, y, z, rand);
						BlockTickEvent.fire(world, x, y, z, b, UpdateFlags.FORCED.flag);
					}
					else {
						ReikaPlantHelper p = ReikaPlantHelper.getPlant(b);
						if (p != null && p.grows()) {
							b.updateTick(world, x, y, z, rand);
							BlockTickEvent.fire(world, x, y, z, b, UpdateFlags.FORCED.flag);
						}
						else if (p == null) {
							if (b instanceof BlockSapling || ModWoodList.isModSapling(b, meta)) {
								b.updateTick(world, x, y, z, rand);
								BlockTickEvent.fire(world, x, y, z, b, UpdateFlags.FORCED.flag);
							}
						}
					}
					break;
				case FIREEXTINGUISH:
					world.playSoundEffect(x, y, z, "random.fizz", 0.6F+0.4F*rand.nextFloat(), 0.5F+0.5F*rand.nextFloat());
					world.setBlockToAir(x, y, z);
					break;
				case HYDRATEFARMLAND:
					ReikaWorldHelper.hydrateFarmland(world, x, y, z, false);
					break;
			}
		}
	}

	private static class FieldCache {

		private final HashMap<Coordinate, FieldColumn> levels = new HashMap();
		private int dirtyRate;

		private FieldColumn getOrFindLevel(World world, int dx, int dz, int y) {
			Coordinate c = new Coordinate(dx, 0, dz);
			FieldColumn f = levels.get(c);
			if (f == null || f.age >= 20) {
				if (f == null) {
					f = new FieldColumn(dx, dz, y);
					levels.put(c, f);
				}
			}
			else {
				f.age++;
			}
			return f;
		}

		private void clear() {
			levels.clear();
		}

		@SideOnly(Side.CLIENT)
		private void doParticles(World world) {
			for (FieldColumn f : levels.values()) {
				f.doParticles(world);
			}
		}

		private NBTTagList writeToNBT() {
			NBTTagList li = new NBTTagList();
			for (Coordinate c : levels.keySet()) {
				FieldColumn f = levels.get(c);
				NBTTagCompound tag = new NBTTagCompound();
				tag.setTag("location", c.writeToTag());
				tag.setTag("data", f.writeToNBT());
				li.appendTag(tag);
			}
			return li;
		}

		private void readFromNBT(NBTTagList li) {
			this.clear();
			for (Object o : li.tagList) {
				NBTTagCompound tag = (NBTTagCompound)o;
				Coordinate c = Coordinate.readFromNBT("location", tag);
				FieldColumn f = FieldColumn.readFromNBT(tag.getCompoundTag("data"));
				levels.put(c, f);
			}
		}

		private boolean tickDirty() {
			dirtyRate++;
			if (dirtyRate >= 8) {
				dirtyRate = 0;
				return true;
			}
			return false;
		}

	}

	private static class FieldColumn {

		private int age = 0;
		private final int xCoord;
		private final int zCoord;
		private final int sprinklerY;

		//private boolean doDripParticles;
		//private int calculatedY;

		private MultiMap<Integer, ColumnAction> effectMap = new MultiMap(CollectionType.HASHSET).setNullEmpty();

		private FieldColumn(int dx, int dz, int y) {
			xCoord = dx;
			zCoord = dz;
			sprinklerY = y;
		}

		private NBTTagCompound writeToNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("x", xCoord);
			tag.setInteger("z", zCoord);
			tag.setInteger("y", sprinklerY);

			NBTTagList li = new NBTTagList();
			for (int y : effectMap.keySet()) {
				Collection<ColumnAction> cc = effectMap.get(y);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("y", y);
				NBTTagList li2 = new NBTTagList();
				for (ColumnAction ca : cc) {
					li2.appendTag(ca.writeToNBT());
				}
				nbt.setTag("entries", li2);
				li.appendTag(nbt);
			}
			tag.setTag("map", li);
			return tag;
		}

		private static FieldColumn readFromNBT(NBTTagCompound tag) {
			int x = tag.getInteger("x");
			int y = tag.getInteger("y");
			int z = tag.getInteger("z");

			FieldColumn ret = new FieldColumn(x, z, y);

			NBTTagList li = tag.getTagList("map", NBTTypes.COMPOUND.ID);
			for (Object o : li.tagList) {
				NBTTagCompound nbt = (NBTTagCompound)o;
				int dy = nbt.getInteger("y");
				Collection<ColumnAction> cc = new HashSet();
				NBTTagList li2 = nbt.getTagList("entries", NBTTypes.COMPOUND.ID);
				for (Object o2 : li2.tagList) {
					NBTTagCompound nbt2 = (NBTTagCompound)o2;
					cc.add(ColumnAction.readFromNBT(nbt2));
				}
				ret.effectMap.put(dy, cc);
			}

			return ret;
		}

		private void recalulateLevel(World world) {
			age = 0;
			int dy = sprinklerY;
			boolean flag = false;
			//doDripParticles = false;
			effectMap.clear();
			while (dy > 0 && sprinklerY-dy < 12 && !flag) {
				Block b = world.getBlock(xCoord, dy, zCoord);
				if (!b.isAir(world, xCoord, dy, zCoord)) {
					boolean stopMoving = false;
					if (b == Blocks.fire) {
						effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.FIREEXTINGUISH, false, false, false));
						stopMoving = true;
					}
					else if (b == Blocks.farmland) {
						effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.HYDRATEFARMLAND, false, false, true));
						stopMoving = true;
					}
					if (!stopMoving) {
						CropType crop = ReikaCropHelper.getCrop(b);
						int meta = world.getBlockMetadata(xCoord, dy, zCoord);
						if (crop == null)
							crop = ModCropList.getModCrop(b, meta);
						if (crop != null) {
							effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.CROPTICK, false, false, true));
							//stopMoving = true; continue downwards to hydrate farmland
						}
						else {
							ReikaPlantHelper p = ReikaPlantHelper.getPlant(b);
							if (p != null && p.grows()) {
								effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.CROPTICK, false, p == ReikaPlantHelper.SUGARCANE, true));
								stopMoving = p == ReikaPlantHelper.CACTUS || p == ReikaPlantHelper.SUGARCANE;
							}
							else if (p == null) {
								if (b instanceof BlockSapling || ModWoodList.isModSapling(b, meta)) {
									b.updateTick(world, xCoord, dy, zCoord, rand);
									BlockTickEvent.fire(world, xCoord, dy, zCoord, b, UpdateFlags.FORCED.flag);
									stopMoving = true;
								}
							}
						}
					}
					if (!stopMoving) {
						if (b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants) {
							String n = b.getClass().getName().toLowerCase(Locale.ENGLISH);
							if (n.startsWith("growthcraft.apples")) {
								if (b.getMaterial() == Material.leaves && n.endsWith("leaves")) {
									if (ModularLogger.instance.isEnabled(LOGGER_ID))
										ModularLogger.instance.log(LOGGER_ID, "Found leaf @ "+xCoord+", "+dy+", "+zCoord);
									Block b2 = world.getBlock(xCoord, dy-1, zCoord);
									if (isOpaque(b2)) {
										stopMoving = true;
									}
									else if (b2.isAir(world, xCoord, dy-1, zCoord)) { //space for an apple; only stop moving down if this
										effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.APPLETICK, true, false, true));
										if (ModularLogger.instance.isEnabled(LOGGER_ID))
											ModularLogger.instance.log(LOGGER_ID, "Ticked apple leaf @ "+xCoord+", "+dy+", "+zCoord);
										stopMoving = true;
									}
									//doDripParticles = b2 != b && !isOpaque(b2);
								}
								else if (b.getMaterial() == Material.plants && n.endsWith("apple")) {
									effectMap.addValue(dy, new ColumnAction(b, xCoord, dy, zCoord, Effects.APPLETICK, false, true, false));
									stopMoving = true;
									if (ModularLogger.instance.isEnabled(LOGGER_ID))
										ModularLogger.instance.log(LOGGER_ID, "Ticked apple block @ "+xCoord+", "+dy+", "+zCoord);
								}
								if (ModularLogger.instance.isEnabled(LOGGER_ID))
									ModularLogger.instance.log(LOGGER_ID, "Read GC block '"+n+"', flag2="+stopMoving);
							}
						}
					}
					flag = isOpaque(b) || stopMoving;
				}
				if (!flag)
					dy--;
			}
			//ReikaJavaLibrary.pConsole(dy+" : "+world.getBlock(xCoord, dy, zCoord));
		}

		private boolean tick(World world, float chanceFactor) {
			boolean flag = age >= 20;
			for (int y : effectMap.keySet()) {
				Block b = world.getBlock(xCoord, y, zCoord);
				for (ColumnAction ca : effectMap.get(y)) {
					if (ca.block == b) {
						if (ca.effect.doChance(rand, chanceFactor)) {
							ca.effect.doEffect(world, xCoord, y, zCoord, b);
						}
					}
					else {
						flag = true;
					}
				}
			}
			return flag;
		}

		@SideOnly(Side.CLIENT)
		private void doParticles(World world) {
			for (ColumnAction ca : effectMap.allValues(false)) {
				if (ca.doDripParticles && rand.nextInt(8) == 0) {
					ReikaParticleHelper.DRIPWATER.spawnAt(world, ca.xCoord+rand.nextDouble(), ca.yCoord, ca.zCoord+rand.nextDouble());
				}
				if (ca.doDripParticlesUp && rand.nextInt(8) == 0) {
					ReikaParticleHelper.DRIPWATER.spawnAt(world, ca.xCoord+rand.nextDouble(), ca.yCoord+0.9375, ca.zCoord+rand.nextDouble());
				}
				if (ca.doSplashParticles) {
					int d = Math.max(1, 5-ConfigRegistry.SPRINKLER.getValue());
					if (rand.nextInt(d) == 0)
						ReikaParticleHelper.RAIN.spawnAt(world, ca.xCoord+rand.nextDouble(), ca.yCoord+1, ca.zCoord+rand.nextDouble());
				}
			}
		}

	}

	private static class ColumnAction {

		private final Block block;
		private final int xCoord;
		private final int yCoord;
		private final int zCoord;
		private final Effects effect;
		private final boolean doDripParticles;
		private final boolean doDripParticlesUp;
		private final boolean doSplashParticles;

		private ColumnAction(Block b, int x, int y, int z, Effects e, boolean drip, boolean drip2, boolean splash) {
			block = b;
			xCoord = x;
			yCoord = y;
			zCoord = z;
			effect = e;
			doDripParticles = drip;
			doDripParticlesUp = drip2;
			doSplashParticles = splash;
		}

		private NBTTagCompound writeToNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("x", xCoord);
			tag.setInteger("z", zCoord);
			tag.setInteger("y", yCoord);

			tag.setInteger("effect", effect.ordinal());
			tag.setInteger("block", Block.getIdFromBlock(block));

			tag.setBoolean("drip", doDripParticles);
			tag.setBoolean("drip2", doDripParticlesUp);
			tag.setBoolean("splash", doSplashParticles);

			return tag;
		}

		private static ColumnAction readFromNBT(NBTTagCompound tag) {
			return new ColumnAction(Block.getBlockById(tag.getInteger("block")), tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), Effects.list[tag.getInteger("effect")], tag.getBoolean("drip"), tag.getBoolean("drip2"), tag.getBoolean("splash"));
		}

	}
}
