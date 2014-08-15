/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.ThermalMachine;
import Reika.RotaryCraft.API.Event.JetEngineEnterFailureEvent;
import Reika.RotaryCraft.API.Event.JetEngineExplosionEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityJetEngine extends TileEntityEngine {

	private boolean isJetFailing = false;

	/** Used in jet engines */
	public int FOD = 0;

	private int dumpx;
	private int dumpz;
	private int dumpvx;
	private int dumpvz;

	private StepTimer jetstarttimer = new StepTimer(479);

	private int chickenCount = 0;

	private boolean isChoking = false;

	@Override
	public int getFuelLevel() {
		return fuel.getLevel();
	}

	@Override
	protected void consumeFuel() {
		fuel.removeLiquid(this.getConsumedFuel());
	}

	@Override
	protected void internalizeFuel() {

	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {
		if (FOD >= 8) {
			jetstarttimer.reset();
			return false;
		}
		if (fuel.getLevel() <= 0) {
			jetstarttimer.reset();
			return false;
		}
		RotaryAchievements.JETENGINE.triggerAchievement(this.getPlacer());
		return true;
	}

	protected void updateTemperature(World world, int x, int y, int z) {

	}

	private void checkJetFailure(World world, int x, int y, int z, int meta) {
		if (isJetFailing)
			this.jetEngineDetonation(world, x, y, z, meta);
		else if (FOD > 0 && rand.nextInt(DifficultyEffects.JETFAILURE.getInt()*(9-FOD)) == 0) {
			RotaryCraft.logger.warn("WARNING: "+this+" just entered failure mode!");
			isJetFailing = true;
			RotaryAchievements.JETFAIL.triggerAchievement(this.getPlacer());
			MinecraftForge.EVENT_BUS.post(new JetEngineEnterFailureEvent(this));
		}
	}

	public float getChokedFraction(World world, int x, int y, int z, int meta) {
		int[] pos = {x, z};
		switch(meta) {
		case 0:
			pos[0] += 1;
			break;
		case 1:
			pos[0] += -1;
			break;
		case 2:
			pos[1] += 1;
			break;
		case 3:
			pos[1] += -1;
			break;
		}
		Block b = world.getBlock(pos[0], y, pos[1]);
		int dmg = world.getBlockMetadata(pos[0], y, pos[1]);
		if (b == Blocks.air)
			return 1;
		if (b.getCollisionBoundingBoxFromPool(world, pos[0], y, pos[1]) == null)
			return 1;
		if (b == Blocks.fence || b == Blocks.nether_brick_fence)
			return 0.75F;
		if (b == Blocks.iron_bars)
			return 1F;
		if (b == Blocks.cobblestone_wall)
			return 0.25F;
		if (b == Blocks.glass_pane)
			return 0.5F;
		;
		if (b.getBlockBoundsMaxX() > 0.875 && b.getBlockBoundsMaxY() > 0.875 && b.getBlockBoundsMaxZ() > 0.875)
			if (b.getBlockBoundsMinX() < 0.125 && b.getBlockBoundsMinY() < 0.125 && b.getBlockBoundsMinZ() < 0.125)
				return 0;
		double frac;
		double dx = b.getBlockBoundsMaxX()-b.getBlockBoundsMinX();
		double dy = b.getBlockBoundsMaxY()-b.getBlockBoundsMinY();
		double dz = b.getBlockBoundsMaxZ()-b.getBlockBoundsMinZ();
		if (b.getBlockBoundsMaxX() <= 0.125 || b.getBlockBoundsMinX() >= 0.875)
			dx = 0;
		if (b.getBlockBoundsMaxY() <= 0.125 || b.getBlockBoundsMinY() >= 0.875)
			dy = 0;
		if (b.getBlockBoundsMaxZ() <= 0.125 || b.getBlockBoundsMinZ() >= 0.875)
			dz = 0;
		if (b.getBlockBoundsMaxY() >= 0.75)
			dy += 0.125;
		//ReikaJavaLibrary.pConsole(dx+"  "+dy+"  "+dz);
		frac = 1-(dx*dy*dz);
		return (float)frac;
	}

	private void heatJet(World world, int x, int y, int z, int meta) {
		int T = 1400*omega/type.getSpeed();
		for (int i = 1; i < 4; i++) {
			int dx = x+write.offsetX*i;
			int dz = z+write.offsetZ*i;
			TileEntity te = this.getTileEntity(dx, y, dz);
			if (te instanceof TemperatureTE) {
				int dT = T-((TemperatureTE)te).getTemperature();
				((TemperatureTE)te).addTemperature(dT);
			}
			else if (te instanceof ThermalMachine) {
				((ThermalMachine)te).setTemperature(T);
			}
		}
		int x1 = write.offsetX != 0 ? write.offsetX > 0 ? x : x-4 : x;
		int x2 = write.offsetX != 0 ? write.offsetX > 0 ? x+5 : x+1 : x+1;
		int z1 = write.offsetZ != 0 ? write.offsetZ > 0 ? z : z-4 : z;
		int z2 = write.offsetZ != 0 ? write.offsetZ > 0 ? z+5 : z+1 : z+1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x1, y, z1, x2, y+1, z2);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < li.size(); i++) {
			EntityLivingBase e = li.get(i);
			e.attackEntityFrom(DamageSource.onFire, 1);
		}
	}

	/** Like BC obsidian pipe - suck in entities in a "funnel" in front of the engine, and deal damage (50 hearts).
	 * Items (including players' inventories and mob drops) will be spat out the back.
	 * Large mobs (Player, creeper, spider, ghast, etc) will cause foreign object damage, necessitating repair.
	 */
	private void ingest(World world, int x, int y, int z, int meta) {
		if (FOD >= 8)
			return;
		for (int step = 0; step < 8; step++) {
			AxisAlignedBB zone = this.getSuctionZone(world, x, y, z, meta, step);
			List inzone = world.getEntitiesWithinAABB(Entity.class, zone);
			for (int i = 0; i < inzone.size(); i++) {
				boolean immune = false;
				float mult = 1;
				if (inzone.get(i) instanceof EntityPlayer) {
					EntityPlayer caughtpl = (EntityPlayer)inzone.get(i);
					if (caughtpl.capabilities.isCreativeMode)
						immune = true;
					ItemStack is = caughtpl.getCurrentArmor(0);
					if (is != null) {
						if (is.getItem() == ItemRegistry.BEDBOOTS.getItemInstance())
							mult = 0.1F;
						if (is.getItem() == ItemRegistry.BEDJUMP.getItemInstance())
							mult = 0.1F;
					}
				}
				if (inzone.get(i) instanceof EntityTurretShot)
					immune = true;
				Entity caught = (Entity)inzone.get(i);
				if (!immune) {
					caught.motionX += (x+0.5D - caught.posX)/20*mult;
					caught.motionY += (y+0.5D - caught.posY)/20*mult;
					caught.motionZ += (z+0.5D - caught.posZ)/20*mult;
					if (!world.isRemote)
						caught.velocityChanged = true;
				}
				if (ReikaMathLibrary.py3d(caught.posX-(x+0.5), caught.posY-(y+0.5), caught.posZ-(z+0.5)) < 1.2) { // Kill the adjacent entities, except items, which are teleported
					if (caught instanceof EntityItem) {
						if (!caught.isDead) {
							ItemStack is = ((EntityItem) caught).getEntityItem();
							caught.setDead();
							int trycount = 0;
							while (trycount < 1 && !ReikaWorldHelper.nonSolidBlocks(world.getBlock(dumpx, y, dumpz))) {
								if (dumpvx == 1)
									dumpx++;
								if (dumpvx == -1)
									dumpx--;
								if (dumpvz == 1)
									dumpz++;
								if (dumpvz == -1)
									dumpz--;
								trycount++;
							}
							EntityItem item = new EntityItem(world, dumpx+0.5D, y+0.375D, dumpz+0.5D, is);
							if (!world.isRemote)
								world.spawnEntityInWorld(item);
							item.motionX = dumpvx*1.5D;
							item.motionY = 0.15;
							item.motionZ = dumpvz*1.5D;
							if (!world.isRemote)
								caught.velocityChanged = true;
							if (is.getItem() == ItemRegistry.SCREWDRIVER.getItemInstance()) {
								caught.setDead();
								FOD = 2;
								isJetFailing = true;
							}
						}
					}
					else if (caught instanceof EntityXPOrb) {
						if (!caught.isDead) {
							int xp = ((EntityXPOrb)caught).getXpValue();
							caught.setDead();
							int trycount = 0;
							while (trycount < 1 && !ReikaWorldHelper.nonSolidBlocks(world.getBlock(dumpx, y, dumpz))) {
								if (dumpvx == 1)
									dumpx++;
								if (dumpvx == -1)
									dumpx--;
								if (dumpvz == 1)
									dumpz++;
								if (dumpvz == -1)
									dumpz--;
								trycount++;
							}
							EntityXPOrb item = new EntityXPOrb(world, dumpx+0.5D, y+0.375D, dumpz+0.5D, xp);
							if (!world.isRemote)
								world.spawnEntityInWorld(item);
							item.motionX = dumpvx*1.5D;
							item.motionY = 0.15;
							item.motionZ = dumpvz*1.5D;
							if (!world.isRemote)
								caught.velocityChanged = true;
						}
					}
					else if (caught instanceof EntityLivingBase && !(caught instanceof EntityPlayer && immune)) {
						caught.setFire(2);
						if (!world.isRemote && ((EntityLivingBase)caught).getHealth() > 0 && this.canDamageEngine(caught))
							this.damageEngine();
						if (FOD > 8)
							FOD = 8;
						if (caught instanceof EntityChicken && !caught.isDead && ((EntityChicken)caught).getHealth() > 0) {
							chickenCount++;
							if (chickenCount >= 50) {
								RotaryAchievements.JETCHICKEN.triggerAchievement(this.getPlacer());
							}
						}
						if (!caught.isDead && !(caught instanceof EntityLivingBase && ((EntityLivingBase)caught).getHealth() < 0))
							SoundRegistry.INGESTION.playSoundAtBlock(world, x, y, z, 1, 1.4F);
						caught.attackEntityFrom(RotaryCraft.jetingest, 10000);
						if (caught instanceof EntityPlayer) {
							RotaryAchievements.SUCKEDINTOJET.triggerAchievement((EntityPlayer)caught);
						}
					}
				}
			}
		}
	}

	private void damageEngine() {
		FOD++;
		//SoundRegistry.JETDAMAGE.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord);
	}

	private boolean canDamageEngine(Entity caught) {
		if (caught instanceof EntityChicken)
			return false;
		if (caught instanceof EntityBat)
			return false;
		if (caught instanceof EntitySilverfish)
			return false;
		if (caught instanceof EntityItem)
			return false;
		if (caught instanceof EntityXPOrb)
			return false;
		String name = caught.getCommandSenderName().toLowerCase();
		if (name.contains("bird"))
			return false;
		if (name.contains("firefly"))
			return false;
		return caught instanceof EntityLivingBase;
	}

	private AxisAlignedBB getSuctionZone(World world, int x, int y, int z, int meta, int step) {
		int minx = 0;
		int miny = 0;
		int minz = 0;
		int maxx = 0;
		int maxy = 0;
		int maxz = 0;

		switch (meta) {
		case 0:
			minx = x+1+step;
			maxx = x+1+step+1;
			miny = y-step;
			maxy = y+step+1;
			minz = z-step;
			maxz = z+step+1;
			dumpx = x-1;
			dumpz = z;
			dumpvx = -1;
			dumpvz = 0;
			break;
		case 1:
			minx = x-1-step;
			maxx = x-1-step+1;
			miny = y-step;
			maxy = y+step+1;
			minz = z-step;
			maxz = z+step+1;
			dumpx = x+1;
			dumpz = z;
			dumpvx = 1;
			dumpvz = 0;
			break;
		case 2:
			minz = z+1+step;
			maxz = z+1+step+1;
			miny = y-step;
			maxy = y+step+1;
			minx = x-step;
			maxx = x+step+1;
			dumpx = x;
			dumpz = z-1;
			dumpvx = 0;
			dumpvz = -1;
			break;
		case 3:
			minz = z-1-step;
			maxz = z-1-step+1;
			miny = y-step;
			maxy = y+step+1;
			minx = x-step;
			maxx = x+step+1;
			dumpx = x;
			dumpz = z+1;
			dumpvx = 0;
			dumpvz = 1;
			break;
		}

		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz).expand(0.25, 0.25, 0.25);
	}

	public void repairJet() {
		FOD = 0;
		isJetFailing = false;
		temperature = ReikaWorldHelper.getAmbientTemperatureAt(worldObj, xCoord, yCoord, zCoord);
	}

	public void repairJetPartial() {
		if (FOD > 0)
			FOD--;
	}

	public void jetEngineDetonation(World world, int x, int y, int z, int meta) {
		AxisAlignedBB zone = this.getFlameZone(world, x, y, z, meta);
		List in = world.getEntitiesWithinAABB(EntityLivingBase.class, zone);
		for (int i = 0; i < in.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)in.get(i);
			e.setFire(2);
		}
		double vx = (x-backx)/2D;
		double vz = (z-backz)/2D;
		for (int i = 0; i < 16; i++) {
			String part;
			if (i%2 == 0)
				part = "flame";
			else
				part = "smoke";
			world.spawnParticle(part, x+0.25+0.5*rand.nextDouble(), y+0.25+0.5*rand.nextDouble(), z+0.25+0.5*rand.nextDouble(), vx-0.1+0.2*rand.nextDouble(), -0.1+0.2*rand.nextDouble(), vz-0.1+0.2*rand.nextDouble());
		}
		int dx = x-backx;
		int dz = z-backz;
		for (int i = 0; i < 16; i++) {
			ReikaWorldHelper.temperatureEnvironment(world, x+dx*i, y, z+dz*i, 800);
		}
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F, 1F);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			if (fuel.getLevel() < FUELCAP/12 && rand.nextInt(10) == 0) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this);
				this.backFire(world, x, y, z);
			}
			if (fuel.getLevel() < FUELCAP/4 && rand.nextInt(20) == 0) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this);
				this.backFire(world, x, y, z);
			}
			else if (rand.nextInt(40) == 0) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this);
				this.backFire(world, x, y, z);
			}
		}
		if (rand.nextInt(2) == 0)
			temperature++;

		if (temperature >= 800) {
			RotaryCraft.logger.warn("WARNING: "+this+" is near explosion!");
		}

		if (temperature > 1000) {
			MinecraftForge.EVENT_BUS.post(new JetEngineExplosionEvent(this));
			int r = 6;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						if (ConfigRegistry.BLOCKDAMAGE.getState())
							ReikaWorldHelper.temperatureEnvironment(world, x+i, y+j, z+k, 1000);
						world.spawnParticle("lava", x+i, y+j, z+k, 0, 0, 0);
						world.spawnParticle("lava", x+i, y+j, z+k, rand.nextDouble()-0.5, rand.nextDouble()-0.5, rand.nextDouble()-0.5);
					}
				}
			}
			if (!world.isRemote) {
				world.newExplosion(null, x+0.5, y+0.5, z+0.5, 12F, true, true);
				for (int m = 0; m < 6; m++)
					world.newExplosion(null, x-4+rand.nextInt(11), y-4+rand.nextInt(11), z-4+rand.nextInt(11), 4F+rand.nextFloat()*2, true, true);
			}
		}
	}

	public void backFire(World world, int x, int y, int z) {
		double vx = (x-backx)/2D;
		double vz = (z-backz)/2D;
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 2*rand.nextFloat(), false);
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 1, 0.5F);
		for (int i = 0; i < 32; i++) {
			String part;
			if (i%2 == 0)
				part = "flame";
			else
				part = "smoke";
			world.spawnParticle(part, x+0.25+0.5*rand.nextDouble(), y+0.25+0.5*rand.nextDouble(), z+0.25+0.5*rand.nextDouble(), -vx-0.1+0.2*rand.nextDouble(), -0.1+0.2*rand.nextDouble(), -vz-0.1+0.2*rand.nextDouble());
		}
	}

	private AxisAlignedBB getFlameZone(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0: //-x
			return AxisAlignedBB.getBoundingBox(x-6, y, z, x+1, y+1, z+1);
		case 1: //+x
			return AxisAlignedBB.getBoundingBox(x, y, z, x+7, y+1, z+1);
		case 2: //-z
			return AxisAlignedBB.getBoundingBox(x, y, z-6, x+1, y+1, z+1);
		case 3: //+z
			return AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+7);
		default:
			return null;
		}
	}

	private void launchEntities(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(8, 8, 8);
		List<Entity> inbox = world.getEntitiesWithinAABB(Entity.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			Entity e = inbox.get(i);
			double dx = e.posX-x-0.5;
			double dy = e.posY-y-0.5;
			double dz = e.posZ-z-0.5;
			double dd = ReikaMathLibrary.py3d(dx, dy, dz);
			e.motionX += 2*dx/dd;
			e.motionY += 2*dy/dd;
			e.motionZ += 2*dz/dd;
			if (!world.isRemote)
				e.velocityChanged = true;
			if (e instanceof EntityPainting || e instanceof EntityItemFrame)
				e.attackEntityFrom(DamageSource.generic, 10);
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("choke", isChoking);
		NBT.setBoolean("jetfail", isJetFailing);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isChoking = NBT.getBoolean("choke");
		isJetFailing = NBT.getBoolean("jetfail");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setInteger("FOD", FOD);
		NBT.setInteger("chickens", chickenCount);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		FOD = NBT.getInteger("FOD");
		chickenCount = NBT.getInteger("chickens");
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier) {
		soundtick++;
		if (FOD > 0 && rand.nextInt(2*(9-FOD)) == 0) {
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F+rand.nextFloat(), 1F);
			world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
		}
		if (!ConfigRegistry.ENGINESOUNDS.getState())
			return;
		float volume = 1;
		if (this.isMuffled(world, x, y, z)) {
			volume = 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		float pitch = 1F;
		pitch = 1F/(0.125F*FOD+1);
		if (jetstarttimer.getTick() >= jetstarttimer.getCap())
			SoundRegistry.JET.playSoundAtBlock(world, x, y, z, volume, pitch*pitchMultiplier);
		else
			soundtick = 2000;
	}

	@Override
	public boolean hasTemperature() {
		return isJetFailing;
	}

	@Override
	public int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return (int)(EngineType.JET.getSpeed()*this.getChokedFraction(world, x, y, z, meta));
	}

	@Override
	protected int getGenTorque(World world, int x, int y, int z, int meta) {
		return EngineType.JET.getTorque()/ReikaMathLibrary.intpow2(2, FOD);
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.checkJetFailure(world, x, y, z, meta);
		this.ingest(world, x, y, z, meta);
		this.heatJet(world, x, y, z, meta);
		//ReikaJavaLibrary.pConsole(lastpower+":"+power, Side.SERVER);
		if (lastpower == 0) {
			SoundRegistry.JETSTART.playSoundAtBlock(world, x, y, z);
		}
		this.spawnSmokeParticles(world, x, y, z, meta);
		jetstarttimer.update();
	}

	private void spawnSmokeParticles(World world, int x, int y, int z, int meta) {
		double dx = x-backx;
		double dz = z-backz;
		dx /= 2;
		dz /= 2;
		double vx = -(x-backx)/2D;
		double vz = -(z-backz)/2D;
		world.spawnParticle("smoke", dx+x+0.25+0.5*rand.nextDouble(), y+0.5*rand.nextDouble(), dz+z+0.25+0.5*rand.nextDouble(), -vx-0.1+0.2*rand.nextDouble(), -0.1+0.2*rand.nextDouble(), -vz-0.1+0.2*rand.nextDouble());
	}

	@Override
	protected void resetPower() {
		super.resetPower();

		jetstarttimer.reset();
	}

	@Override
	protected int getSoundLength(float factor) {
		return super.getSoundLength(factor)+(int)(Math.min(FOD, 7)*11*factor);
	}

	@Override
	public void setDataFromPlacer(ItemStack is) {
		super.setDataFromPlacer(is);

		if (is.stackTagCompound != null)
			FOD = is.stackTagCompound.getInteger("damage");
	}

	@Override
	public boolean isBroken() {
		return FOD >= 8;
	}

}