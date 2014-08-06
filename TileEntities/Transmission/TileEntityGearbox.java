/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.ChromatiCraft.API.SpaceRift;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityGearbox extends TileEntity1DTransmitter implements PipeConnector, IFluidHandler {

	public boolean reduction = true; // Reduction gear if true, accelerator if false

	private int damage = 0;

	private MaterialRegistry type;

	private HybridTank tank = new HybridTank("gear", 24000);
	private boolean failed;

	private static final int MAX_DAMAGE = 480;

	private boolean lastPower;

	public TileEntityGearbox(MaterialRegistry type) {
		if (type == null)
			type = MaterialRegistry.WOOD;
		this.type = type;
	}

	public TileEntityGearbox() {
		this(MaterialRegistry.WOOD);
	}

	public MaterialRegistry getGearboxType() {
		return type != null ? type : MaterialRegistry.WOOD;
	}

	public int getMaxLubricant() {
		switch(type) {
		case BEDROCK:
			return 0;
		case DIAMOND:
			return 1000;
		case STEEL:
			return 24000;
		case STONE:
			return 8000;
		case WOOD:
			return 3000;
		default:
			return 0;
		}
	}

	public int getDamage() {
		return damage;
	}

	public double getDamagedPowerFactor() {
		return Math.pow(0.99, damage);
	}

	public int getDamagePercent() {
		return (int)(100*(1-Math.pow(0.99, damage)));
	}

	@Override
	protected void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (reduction) {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = spl.omega/ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2*ratio;
					return;
				}
				if (favorbent) {
					torque = spl.torque/sratio*ratio;
				}
				else {
					torque = ratio*(int)(spl.torque*((sratio-1D)/(sratio)));
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = spl.omega/ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2*ratio;
					return;
				}
				if (favorbent) {
					torque = ratio*(int)(spl.torque*((sratio-1D)/(sratio)));
				}
				else {
					torque = spl.torque/sratio*ratio;
				}
			}
			else { //We are not one of its write-to blocks
				torque = 0;
				omega = 0;
				power = 0;
				return;
			}
		}
		else {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = spl.omega*ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2/ratio;
					return;
				}
				if (favorbent) {
					torque = spl.torque/sratio/ratio;
				}
				else {
					torque = (int)(spl.torque*((sratio-1D))/sratio)/(ratio);
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = spl.omega*ratio; //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = spl.torque/2/ratio;
					return;
				}
				if (favorbent) {
					torque = (int)(spl.torque*((sratio-1D)/(sratio)))/ratio;
				}
				else {
					torque = spl.torque/sratio/ratio;
				}
			}
			else { //We are not one of its write-to blocks
				torque = 0;
				omega = 0;
				power = 0;
				return;
			}
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);

		if ((world.getWorldTime()&31) == 0)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower))
			ratio = -ratio;

		this.transferPower(world, x, y, z, meta);
		power = (long)omega*(long)torque;
		this.getLube(world, x, y, z, meta);

		this.basicPowerReceiver();
		lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
	}

	public void getLube(World world, int x, int y, int z, int metadata) {
		int oldlube = 0;
		if (type.isDamageableGear() && omegain > 0) {
			if (tank.isEmpty()) {
				if (!world.isRemote && damage < MAX_DAMAGE && rand.nextInt(40) == 0) {
					damage++;
					RotaryAchievements.DAMAGEGEARS.triggerAchievement(this.getPlacer());
				}
				if (rand.nextDouble()*rand.nextDouble() > this.getDamagedPowerFactor()) {
					if (type.isFlammable())
						ReikaWorldHelper.ignite(world, x, y, z);
					world.spawnParticle("crit", xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
					if (rand.nextInt(5) == 0) {
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, type.getDamageNoise(), 1F, 1F);
					}
				}
			}
			else if (!world.isRemote && type.consumesLubricant()) {
				if (tickcount >= 80) {
					tank.removeLiquid(Math.max(1, (int)ReikaMathLibrary.logbase(omegain, 2)/4));
					tickcount = 0;
				}
			}
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		while (metadata > 3)
			metadata -= 4;
		super.getIOSides(world, x, y, z, metadata, false);
	}

	private void calculateRatio() {
		int tratio = 1+this.getBlockMetadata()/4;
		ratio = (int)ReikaMathLibrary.intpow(2, tratio);
	}

	@Override
	protected void readFromCross(TileEntityShaft cross) {
		if (cross.isWritingTo(this)) {
			if (reduction) {
				omega = cross.readomega[0]/ratio;
				torque = cross.readtorque[0]*ratio;
			}
			else {
				omega = cross.readomega[0]*ratio;
				torque = cross.readtorque[0]/ratio;
			}
		}
		else if (cross.isWritingTo2(this)) {
			if (reduction) {
				omega = cross.readomega[1]/ratio;
				torque = cross.readtorque[1]*ratio;
			}
			else {
				omega = cross.readomega[1]*ratio;
				torque = cross.readtorque[1]/ratio;
			}
		}
		else {
			omega = torque = 0;
			return; //not its output
		}
	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		this.calculateRatio();
		omegain = torquein = 0;
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getBlockTileEntity(dx, dy, dz);
		if (this.isProvider(te)) {
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.isCross()) {
					this.readFromCross(devicein);
					return;
				}
				if (devicein.isWritingTo(this)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te instanceof SimpleProvider) {
				this.copyStandardPower(te);
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein);
					return;
				}
				else if (devicein.isWritingTo(this)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		else if (te instanceof SpaceRift) {
			SpaceRift sr = (SpaceRift)te;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null)
				this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta);
		}
		else {
			omega = 0;
			torque = 0;
			power = 0;
			return;
		}

		if (reduction) {
			omega = omegain / ratio;
			if (torquein <= RotaryConfig.torquelimit/ratio)
				torque = torquein * ratio;
			else {
				torque = RotaryConfig.torquelimit;
				world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, type.getDamageNoise(), 0.1F, 1F);
			}
		}
		else {
			if (omegain <= RotaryConfig.omegalimit/ratio)
				omega = omegain * ratio;
			else {
				omega = RotaryConfig.omegalimit;
				world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, type.getDamageNoise(), 0.1F, 1F);
			}
			torque = torquein / ratio;
		}
		torque *= this.getDamagedPowerFactor();
		if (torque <= 0)
			omega = 0;

		if (!type.isInfiniteStrength())
			this.testFailure();
	}

	public void fail(World world, int x, int y, int z) {
		failed = true;
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 1F, true);
		ItemStack item = null;
		switch(type) {
		case WOOD:
			item = new ItemStack(ItemStacks.sawdust.itemID, 1, ItemStacks.sawdust.getItemDamage());
			break;
		case STONE:
			item = new ItemStack(Block.gravel, 1, 0);
			break;
		case STEEL:
			item = new ItemStack(ItemStacks.scrap.itemID, 1, ItemStacks.scrap.getItemDamage());
			break;
		case DIAMOND:
			item = new ItemStack(Item.diamond, 1, 0);
			break;
		case BEDROCK:
			item = new ItemStack(ItemStacks.bedrockdust.itemID, 1, ItemStacks.bedrockdust.getItemDamage());
			break;
		}
		for (int i = 0; i < this.getRatio(); i++) {
			ReikaItemHelper.dropItem(world, x+0.5, y+1.25, z+0.5, item);
		}
		world.setBlock(x, y, z, 0);
	}

	public void repair(int dmg) {
		damage -= dmg;
		if (damage < 0)
			damage = 0;
		failed = false;
	}

	public void setDamage(int dmg) {
		damage = dmg;
	}

	public void testFailure() {
		if (ReikaEngLibrary.mat_rotfailure(type.getDensity(), 0.0625, ReikaMathLibrary.doubpow(Math.max(omega, omegain), 1-(0.11D*type.ordinal())), type.getTensileStrength())) {
			this.fail(worldObj, xCoord, yCoord, zCoord);
		}
		else if (ReikaEngLibrary.mat_twistfailure(Math.max(torque, torquein), 0.0625, type.getShearStrength()/16D)) {
			this.fail(worldObj, xCoord, yCoord, zCoord);
		}
	}

	public int getLubricantScaled(int par1)
	{
		if (this.getMaxLubricant() == 0)
			return 0;
		return tank.getLevel()*par1/this.getMaxLubricant();
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("reduction", reduction);
		NBT.setInteger("damage", damage);
		NBT.setBoolean("fail", failed);

		tank.writeToNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		NBT.setInteger("type", type.ordinal());
		super.writeToNBT(NBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		type = MaterialRegistry.setType(NBT.getInteger("type"));
		super.readFromNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		reduction = NBT.getBoolean("reduction");
		damage = NBT.getInteger("damage");
		failed = NBT.getBoolean("fail");

		tank.readFromNBT(NBT);
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
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getRedstoneOverride() {
		return 15*tank.getLevel()/this.getMaxLubricant();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side != ForgeDirection.DOWN;
	}

	@Override
	public void onEMP() {}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid())) {
			int space = this.getMaxLubricant()-this.getLubricant();
			if (space > 0) {
				if (resource.amount > space)
					resource = new FluidStack(resource.getFluid(), space);
				return tank.fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP && fluid.equals(FluidRegistry.getFluid("lubricant"));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLubricant() {
		return tank.getLevel();
	}

	public void setLubricant(int amt) {
		tank.setContents(amt, FluidRegistry.getFluid("lubricant"));
	}

	public void fillWithLubricant() {
		this.setLubricant(this.getMaxLubricant());
	}

	public boolean canTakeLubricant(int amt) {
		return tank.getLevel()+amt <= this.getMaxLubricant();
	}

	public void addLubricant(int amt) {
		tank.addLiquid(amt, FluidRegistry.getFluid("lubricant"));
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side != ForgeDirection.UP ? Flow.INPUT : Flow.NONE;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GEARBOX;
	}
}
