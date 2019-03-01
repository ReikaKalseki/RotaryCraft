/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.RayTracer;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Effects.EntityBlockTexFX;
import Reika.DragonAPI.Instantiable.Effects.EntityLiquidParticleFX;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.JetEngineEnterFailureEvent;
import Reika.RotaryCraft.API.Event.JetEngineExplosionEvent;
import Reika.RotaryCraft.API.Interfaces.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Base.EntityTurretShot;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.VoidMonster.Entity.EntityVoidMonster;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.entities.monster.EntityWisp;

public class TileEntityJetEngine extends TileEntityEngine implements NBTMachine, UpgradeableMachine {

	private boolean isJetFailing = false;

	/** Used in jet engines */
	public int FOD = 0;

	private int dumpx;
	private int dumpz;
	private int dumpvx;
	private int dumpvz;

	private StepTimer jetstarttimer = new StepTimer(479);
	private int afterburnTick = 2000;

	private int chickenCount = 0;

	private boolean isChoking = false;

	private boolean canAfterBurn;
	private boolean burnerActive;

	private static final RayTracer tracer;

	static {
		tracer = new RayTracer(0, 0, 0, 0, 0, 0);
	}

	@Override
	public int getFuelLevel() {
		return fuel.getLevel();
	}

	@Override
	protected void consumeFuel() {
		fuel.removeLiquid(this.getConsumedFuel());
	}

	@Override
	protected int getConsumedFuel() {
		return this.isAfterburning() ? 25 : 10;
	}

	@Override
	protected void internalizeFuel() {

	}

	@Override
	protected boolean canBeThrottled() {
		return !this.isAfterburning();
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
		if (power > 0)
			RotaryAchievements.JETENGINE.triggerAchievement(this.getPlacer());
		return true;
	}

	protected void updateTemperature(World world, int x, int y, int z) {

	}

	private void checkJetFailure(World world, int x, int y, int z, int meta) {
		if (isJetFailing)
			this.jetEngineDetonation(world, x, y, z, meta);
		else if (FOD > 0 && rand.nextInt(DifficultyEffects.JETFAILURE.getInt()*(9-FOD)) == 0) {
			this.triggerJetFailing(world, x, y, z);
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
		int temp = this.isAfterburning() ? 1750 : 1200;
		int T = temp*omega/type.getSpeed();
		int r = this.isAfterburning() ? 6 : 4;
		for (int i = 1; i < r; i++) {
			int dx = x+write.offsetX*i;
			int dz = z+write.offsetZ*i;
			TileEntity te = this.getTileEntity(dx, y, dz);
			if (te instanceof TemperatureTE) {
				if (((TemperatureTE)te).allowExternalHeating()) {
					int dT = T-((TemperatureTE)te).getTemperature();
					((TemperatureTE)te).addTemperature(dT);
				}
			}
			else if (te instanceof ThermalMachine) {
				((ThermalMachine)te).setTemperature(T);
			}
			if (this.isAfterburning()) {
				ReikaWorldHelper.temperatureEnvironment(world, dx, y, dz, Math.min(1400, T));
			}
		}
		int x1 = write.offsetX != 0 ? write.offsetX > 0 ? x : x-4 : x;
		int x2 = write.offsetX != 0 ? write.offsetX > 0 ? x+5 : x+1 : x+1;
		int z1 = write.offsetZ != 0 ? write.offsetZ > 0 ? z : z-4 : z;
		int z2 = write.offsetZ != 0 ? write.offsetZ > 0 ? z+5 : z+1 : z+1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x1, y, z1, x2, y+1, z2);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase e : li) {
			e.attackEntityFrom(DamageSource.onFire, this.isAfterburning() ? 4 : 1);
		}
	}

	/** Like BC obsidian pipe - suck in entities in a "funnel" in front of the engine, and deal damage (50 hearts).
	 * Items (including players' inventories and mob drops) will be spat out the back.
	 * Large mobs (Player, creeper, spider, ghast, etc) will cause foreign object damage, necessitating repair.
	 */
	private void ingest(World world, int x, int y, int z, int meta) {
		if (FOD >= 8)
			return;
		ForgeDirection dir = this.getWriteDirection().getOpposite();
		double px = x+0.5+dir.offsetX*0.49;
		double pz = z+0.5+dir.offsetZ*0.49;
		for (int step = 0; step < 8; step++) {
			AxisAlignedBB zone = this.getSuctionZone(world, x, y, z, meta, step);
			List<Entity> inzone = world.getEntitiesWithinAABB(Entity.class, zone);
			for (Entity caught : inzone) {
				if (this.canSuckTowards(world, x, y, z, caught, px, pz)) {
					float mult = this.getSuctionMultiplier(caught);
					if (mult > 0) {
						caught.motionX += (x+0.5D - caught.posX)/20*mult;
						caught.motionY += (y+0.5D - caught.posY)/20*mult;
						caught.motionZ += (z+0.5D - caught.posZ)/20*mult;
						if (!world.isRemote)
							caught.velocityChanged = true;
					}
					if (ReikaMathLibrary.py3d(caught.posX-px, caught.posY-(y+0.5), caught.posZ-pz) < 1.2) {
						this.ingest(world, x, y, z, caught, mult <= 0);
					}
				}
			}
		}
	}

	private boolean canSuckTowards(World world, int x, int y, int z, Entity e, double px, double pz) {
		int n = 2;
		for (int i = 0; i <= n; i++) {
			tracer.setOrigins(px, y+0.5, pz, e.posX, e.posY+e.height*i/n, e.posZ);
			//ReikaJavaLibrary.pConsole(e+":"+tracer.isClearLineOfSight(world)+" of "+e.height, e instanceof EntityChicken);
			if (tracer.isClearLineOfSight(world))
				return true;
		}
		return false;
	}

	private float getSuctionMultiplier(Entity e) {
		if (e instanceof EntityPlayer) {
			EntityPlayer epl = (EntityPlayer)e;
			if (epl.capabilities.isCreativeMode)
				return 0;
			ItemStack is = epl.getCurrentArmor(0);
			if (is != null) {
				if (is.getItem() == ItemRegistry.BEDBOOTS.getItemInstance())
					return 0.1F;
				if (is.getItem() == ItemRegistry.BEDJUMP.getItemInstance())
					return 0.1F;
			}
		}
		if (e instanceof EntityTurretShot)
			return 0;
		if (ModList.VOIDMONSTER.isLoaded() && e instanceof EntityVoidMonster) {
			if (e.getDistanceSq(xCoord+0.5, yCoord+0.5, zCoord+0.5) < 16) {
				isJetFailing = true;
				temperature += 2;
			}
			return 0;
		}
		return 1;
	}

	private void ingest(World world, int x, int y, int z, Entity e, boolean immune) { // Kill the adjacent entities, except items, which are teleported
		if (e instanceof EntityItem) {
			if (!e.isDead) {
				ItemStack is = ((EntityItem) e).getEntityItem();
				e.setDead();
				int trycount = 0;
				while (trycount < 1 && !ReikaWorldHelper.nonSolidBlocks(world, dumpx, y, dumpz)) {
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
					e.velocityChanged = true;
				if (this.itemDestroysEngine(is)) {
					e.setDead();
					FOD = 2;
					this.triggerJetFailing(world, x, y, z);
				}
			}
		}
		else if (e instanceof EntityXPOrb) {
			if (!e.isDead) {
				int xp = ((EntityXPOrb)e).getXpValue();
				e.setDead();
				int trycount = 0;
				while (trycount < 1 && !ReikaWorldHelper.nonSolidBlocks(world, dumpx, y, dumpz)) {
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
					e.velocityChanged = true;
			}
		}
		else if (e instanceof EntityLivingBase && !(e instanceof EntityPlayer && immune)) {
			e.setFire(2);
			if (!world.isRemote && ((EntityLivingBase)e).getHealth() > 0 && this.canDamageEngine(e))
				this.damageEngine();
			if (FOD > 8)
				FOD = 8;
			if (e instanceof EntityChicken && !e.isDead && ((EntityChicken)e).getHealth() > 0) {
				chickenCount++;
				if (chickenCount >= 50) {
					RotaryAchievements.JETCHICKEN.triggerAchievement(this.getPlacer());
				}
			}
			if (!e.isDead && !(e instanceof EntityLivingBase && ((EntityLivingBase)e).getHealth() < 0))
				SoundRegistry.INGESTION.playSoundAtBlock(world, x, y, z, 1, 1.4F);
			e.attackEntityFrom(RotaryCraft.jetingest, 10000);
			if (e instanceof EntityPlayer && e == this.getPlacer()) {
				RotaryAchievements.SUCKEDINTOJET.triggerAchievement((EntityPlayer)e);
			}
		}
	}

	private void triggerJetFailing(World world, int x, int y, int z) {
		RotaryCraft.logger.warn(this+" just entered failure mode!");
		isJetFailing = true;

		RotaryAchievements.JETFAIL.triggerAchievement(this.getPlacer());
		MinecraftForge.EVENT_BUS.post(new JetEngineEnterFailureEvent(this));
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 2, 0.75F);
		SoundRegistry.INGESTION.playSoundAtBlock(this, 0.5F, 1);

		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).addCoord(write.offsetX*8, y, write.offsetZ*8).expand(3, 3, 3);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		HashSet<Integer> idSet = new HashSet();
		for (EntityLivingBase e : li) {
			e.attackEntityFrom(DamageSource.generic, 8);
			idSet.add(e.getEntityId());
		}
		box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(4, 4, 4);
		li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase e : li) {
			if (!idSet.contains(e.getEntityId()))
				e.attackEntityFrom(DamageSource.generic, 4);
		}

		if (world.isRemote) {

			for (int i = 0; i < 24; i++) {
				EntityItem ei = new EntityItem(world, x+0.5+write.offsetX, y+0.5, z+0.5+write.offsetZ, ItemStacks.scrap);
				double v = ReikaRandomHelper.getRandomBetween(0.8D, 2D);
				ei.motionX = write.offsetX*v;
				ei.motionZ = write.offsetZ*v;
				ei.motionX = ReikaRandomHelper.getRandomPlusMinus(ei.motionX, 0.25);
				ei.motionZ = ReikaRandomHelper.getRandomPlusMinus(ei.motionZ, 0.25);
				ei.motionY = ReikaRandomHelper.getRandomPlusMinus(0, 0.25);
				ei.lifespan = ReikaRandomHelper.getRandomBetween(10, 30);
				world.spawnEntityInWorld(ei);
			}

			int r = 8;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						int dx = x+i;
						int dy = y+j;
						int dz = z+k;
						ReikaRenderHelper.spawnDropParticles(world, x, y, z, world.getBlock(dx, dy, dz), world.getBlockMetadata(dx, dy, dz));
					}
				}
			}
		}
	}

	private boolean itemDestroysEngine(ItemStack is) {
		return is.getItem() == ItemRegistry.SCREWDRIVER.getItemInstance() || InterfaceCache.IWRENCH.instanceOf(is.getItem());
	}

	private void damageEngine() {
		FOD++;
		if (DifficultyEffects.JETINGESTFAIL.testChance()) {
			isJetFailing = true;
			temperature = 800;
		}
		//SoundRegistry.JETDAMAGE.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord);
	}

	private boolean canDamageEngine(Entity caught) {
		if (caught.noClip)
			return false;
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
		if (ModList.THAUMCRAFT.isLoaded() && caught instanceof EntityWisp)
			return false;
		String name = caught.getCommandSenderName().toLowerCase(Locale.ENGLISH);
		if (name.contains("bird"))
			return false;
		if (name.contains("firefly"))
			return false;
		if (name.contains("butterfly"))
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

	private void jetEngineDetonation(World world, int x, int y, int z, int meta) {
		AxisAlignedBB zone = this.getFlameZone(world, x, y, z, meta);
		List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, zone);
		for (EntityLivingBase e : in) {
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
				ReikaPacketHelper.sendDataPacketWithRadius(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this, 64);
				this.backFire(world, x, y, z);
			}
			if (fuel.getLevel() < FUELCAP/4 && rand.nextInt(20) == 0) {
				ReikaPacketHelper.sendDataPacketWithRadius(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this, 64);
				this.backFire(world, x, y, z);
			}
			else if (rand.nextInt(40) == 0) {
				ReikaPacketHelper.sendDataPacketWithRadius(RotaryCraft.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue(), this, 64);
				this.backFire(world, x, y, z);
			}
		}
		if (rand.nextInt(2) == 0)
			temperature++;

		if (temperature >= 800) {
			RotaryCraft.logger.warn("WARNING: "+this+" is near explosion!");
		}

		if (temperature > 1000) {
			this.fail(world, x, y, z);
		}
	}

	private void fail(World world, int x, int y, int z) {
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
		for (Entity e : inbox) {
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
		NBT.setBoolean("burn", canAfterBurn);
		NBT.setBoolean("burning", burnerActive);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isChoking = NBT.getBoolean("choke");
		isJetFailing = NBT.getBoolean("jetfail");
		canAfterBurn = NBT.getBoolean("burn");
		burnerActive = NBT.getBoolean("burning");
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
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		afterburnTick++;
		if (FOD > 0 && rand.nextInt(2*(9-FOD)) == 0) {
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F+rand.nextFloat(), 1F);
			world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
		}
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
		}

		if (this.isAfterburning() && afterburnTick >= 50) {
			afterburnTick = 0;
			float vol = 0.9F;
			if (omega < type.getSpeed()) {
				vol *= Math.pow(0.75, type.getSpeed()/omega);
			}
			SoundRegistry.AFTERBURN.playSoundAtBlock(world, x, y, z, vol, 1);
			SoundRegistry.AFTERBURN.playSoundAtBlock(world, x, y, z, vol, 1);
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

	public boolean isAfterburning() {
		return canAfterBurn && burnerActive;
	}

	@Override
	public boolean hasTemperature() {
		return isJetFailing || this.isAfterburning();
	}

	@Override
	public int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return (int)(EngineType.JET.getSpeed()*this.getChokedFraction(world, x, y, z, meta));
	}

	@Override
	protected boolean canStart() {
		return true; //JumpStart code goes here
	}

	@Override
	protected int getGenTorque(World world, int x, int y, int z, int meta) {
		int amt = EngineType.JET.getTorque();
		if (this.isAfterburning())
			amt *= 2;
		return amt/ReikaMathLibrary.intpow2(2, FOD);
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.checkJetFailure(world, x, y, z, meta);
		this.ingest(world, x, y, z, meta);
		this.fluidIngest(world, x, y, z);
		this.heatJet(world, x, y, z, meta);
		//ReikaJavaLibrary.pConsole(lastpower+":"+power, Side.SERVER);
		if (lastpower == 0) {
			SoundRegistry.JETSTART.playSoundAtBlock(world, x, y, z);
		}
		if (world.isRemote)
			this.spawnSmokeParticles(world, x, y, z, meta);
		jetstarttimer.update();
		this.doAfterburning(world, x, y, z);
	}

	private void fluidIngest(World world, int x, int y, int z) {
		int dx = x+write.getOpposite().offsetX;
		int dz = z+write.getOpposite().offsetZ;
		Block b = world.getBlock(dx, y, dz);
		Fluid f = FluidRegistry.lookupFluidForBlock(b);
		if (f != null) {

			if (worldObj.isRemote) {
				this.fluidIngestParticles(world, x, y, z, f);
			}
			else {
				int temp = f.getTemperature(world, dx, y, dz);
				if (f.getName().toLowerCase(Locale.ENGLISH).contains("fuel")) {
					if (!isJetFailing && rand.nextInt(200) == 0) {
						temperature = 900;
						isJetFailing = true;
						this.jetEngineDetonation(world, x, y, z, this.getBlockMetadata());
					}
				}
				else if (temp >= 500) {
					if (temp >= 2000 || rand.nextInt(1+(2000-temp)/500) == 0) {
						if (FOD < 8 && world.getTotalWorldTime()%20 == 0 && rand.nextInt(1+2*FOD) == 0) {
							this.damageEngine();
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void fluidIngestParticles(World world, int x, int y, int z, Fluid f) {
		int n = 1+rand.nextInt(8);
		for (int i = 0; i < n; i++) {
			double vx = (x-backx)/4D;
			double vy = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
			double vz = (z-backz)/4D;

			vx = ReikaRandomHelper.getRandomPlusMinus(vx, 0.0625);
			vz = ReikaRandomHelper.getRandomPlusMinus(vz, 0.0625);
			EntityFX fx = new EntityLiquidParticleFX(world, x+0.5+write.offsetX*0.25, y+0.5, z+0.5+write.offsetZ*0.25, vx, vy, vz, f);
			fx.noClip = true;
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}

	private void doAfterburning(World world, int x, int y, int z) {
		if (this.isAfterburning()) {
			this.afterBurnParticles(world, x, y, z);
			if (this.getTicksExisted()%40 == 0) {
				temperature += 1;
				if (temperature > 1000) {
					this.fail(world, x, y, z);
				}
				else if (temperature >= 600) {
					ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz");
					ReikaParticleHelper.SMOKE.spawnAroundBlock(world, x, y, z, 8);
				}
			}
		}
	}

	private void afterBurnParticles(World world, int x, int y, int z) {
		double dx = x-backx;
		double dz = z-backz;
		dx /= 2;
		dz /= 2;
		double vx = (x-backx)*6D;
		double vz = (z-backz)*6D;
		for (int i = 0; i < 16; i++) {
			int r = 255;
			int g = 0;
			int b = 0;
			double px = dx+x+0.25+0.5*rand.nextDouble()+vx*rand.nextDouble();
			double pz = dz+z+0.25+0.5*rand.nextDouble()+vz*rand.nextDouble();
			double dd = Math.abs(px-x)+Math.abs(pz-z);
			if (dd < 1.5+rand.nextDouble()) {
				r = 0;
				g = 127;
				b = 255;
			}
			else if (dd < 2.5+rand.nextDouble()) {
				r = 255;
				g = 255;
				b = 255;
			}
			else if (dd < 3+rand.nextDouble()*2) {
				g = 255;
			}
			else if (dd < 5+rand.nextDouble()*3 && rand.nextBoolean()) {
				g = 10;
			}
			ReikaParticleHelper.spawnColoredParticleAt(world, px, y+0.75*rand.nextDouble(), pz, r, g, b);
		}
	}

	@SideOnly(Side.CLIENT)
	private void spawnSmokeParticles(World world, int x, int y, int z, int meta) {
		double dx = x-backx;
		double dz = z-backz;
		dx /= 2;
		dz /= 2;
		double vx = -(x-backx)/2D;
		double vz = -(z-backz)/2D;
		ReikaParticleHelper.SMOKE.spawnAt(world, dx+x+0.25+0.5*rand.nextDouble(), y+0.5*rand.nextDouble(), dz+z+0.25+0.5*rand.nextDouble(), -vx-0.1+0.2*rand.nextDouble(), -0.1+0.2*rand.nextDouble(), -vz-0.1+0.2*rand.nextDouble());

		int n = 1+rand.nextInt(8);
		double w = n/2D;
		dx = write.offsetX == 0 ? ReikaRandomHelper.getRandomPlusMinus(x+0.5, w) : x+0.5-n*write.offsetX;
		double dy = ReikaRandomHelper.getRandomPlusMinus(y+0.5, w);
		dz = write.offsetZ == 0 ? ReikaRandomHelper.getRandomPlusMinus(z+0.5, w) : z+0.5-n*write.offsetZ;

		double v = -0.0625;

		vx = v*(dx-x-0.5);
		double vy = v*(dy-y-0.5);
		vz = v*(dz-z-0.5);

		int bx = MathHelper.floor_double(dx);
		int by = MathHelper.floor_double(dy);
		int bz = MathHelper.floor_double(dz);
		Block b = world.getBlock(bx, by, bz);
		int bmeta = world.getBlockMetadata(bx, by, bz);
		if (b.isAir(world, bx, by, bz)) {
			if (rand.nextInt(3) == 0)
				ReikaParticleHelper.CLOUD.spawnAt(world, dx, dy, dz, vx, vy, vz);
		}
		else {
			EntityBlockTexFX fx = new EntityBlockTexFX(world, dx, dy+1, dz, vx, vy-0.03125, vz, b, bmeta).setGravity(0);
			fx.applyColourMultiplier(bx, by, bz);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}

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

	@Override
	public NBTTagCompound getTagsToWriteToStack() {
		if (canAfterBurn) {
			NBTTagCompound NBT = new NBTTagCompound();
			NBT.setBoolean("burn", canAfterBurn);
			return NBT;
		}
		return null;
	}

	@Override
	public void setDataFromItemStackTag(NBTTagCompound NBT) {
		canAfterBurn = NBT != null && NBT.getBoolean("burn");
	}

	@Override
	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		return new ArrayList();
	}

	@Override
	public ArrayList<String> getDisplayTags(NBTTagCompound NBT) {
		ArrayList<String> li = new ArrayList();
		if (NBT != null && NBT.getBoolean("burn")) {
			li.add("With Afterburner");
		}
		return li;
	}

	@Override
	public void upgrade(ItemStack is) {
		canAfterBurn = true;
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return !canAfterBurn && ItemRegistry.UPGRADE.matchItem(item) && item.getItemDamage() == Upgrades.AFTERBURNER.ordinal();
	}

	public boolean canAfterBurn() {
		return canAfterBurn;
	}

	public boolean burnerActive() {
		return burnerActive;
	}

	public void setBurnerActive(boolean burn) {
		burnerActive = burn;
		if (!burn && !isJetFailing) {
			temperature = ReikaWorldHelper.getAmbientTemperatureAt(worldObj, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void breakBlock() {
		super.breakBlock();
		if (canAfterBurn) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.AFTERBURNER.ordinal()));
		}
	}

}
