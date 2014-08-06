/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityFurnaceHeater extends TileEntityPowerReceiver implements TemperatureTE, ConditionalOperation {
	//give ability to heat blast furnace
	private int temperature;
	public int fx;
	public int fy;
	public int fz;

	public static final int MAXTEMP = 2000;
	private int smeltTime = 0;
	private int soundtick = 0;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if (torque >= MINTORQUE && power >= MINPOWER && omega > 0 && this.hasHeatableMachine(world)) {
			temperature += 3*ReikaMathLibrary.logbase(omega, 2)*ReikaMathLibrary.logbase(torque, 2);
		}
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/5;
		}
		else {
			temperature += (temperature-Tamb)/5;
		}
		if (temperature - Tamb <= 4 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
		if (temperature >= MAXTEMP)
			if (!world.isRemote && rand.nextInt(DifficultyEffects.FURNACEMELT.getInt()) == 0 && ConfigRegistry.BLOCKDAMAGE.getState())
				this.meltFurnace(world);
		if (temperature < Tamb)
			temperature = Tamb;
	}

	public boolean hasHeatableMachine(World world) {
		int id = world.getBlockId(fx, fy, fz);
		int meta = world.getBlockMetadata(fx, fy, fz);
		if (id == 0)
			return false;
		if (id == Block.furnaceIdle.blockID || id == Block.furnaceBurning.blockID)
			return true;
		Block b = Block.blocksList[id];
		MachineRegistry m = MachineRegistry.getMachine(world, fx, fy, fz);
		if (m != null && m.canBeFrictionHeated())
			return true;
		TileEntity te = this.getTileEntity(fx, fy, fz);
		return te instanceof ThermalMachine;
	}

	@Override
	public int getThermalDamage() {
		return temperature*5/1200;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		if (torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FRICTION;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		this.getFurnaceCoordinates(world, x, y, z, meta);
		if (tickcount >= 20) {
			tickcount = 0;
			this.updateTemperature(world, x, y, z, meta);
		}
		if (power < MINPOWER)
			return;
		if (torque < MINTORQUE)
			return;

		if (!this.hasFurnace(world)) {
			if (this.hasHeatable(world)) {
				this.heatMachine(world, x, y, z);
			}
			else {
				TileEntity te = this.getTileEntity(fx, fy, fz);
				if (te instanceof ThermalMachine) {
					ThermalMachine tm = (ThermalMachine)te;
					if (tm.canBeFrictionHeated()) {
						int tdiff = temperature-tm.getTemperature();
						if (tdiff > 0)
							tm.addTemperature(tdiff);
						if (tm.getTemperature() > tm.getMaxTemperature()) {
							tm.onOverheat(world, fx, fy, fz);
						}
					}
				}
			}
			return;
		}
		this.hijackFurnace(world, x, y, z, meta);
	}

	private void heatMachine(World world, int x, int y, int z) {
		FrictionHeatable te = (FrictionHeatable)this.getTileEntity(fx, fy, fz);
		int tdiff = Math.min(te.getMaxTemperature(), temperature)-te.getTemperature();
		te.addTemperature(tdiff);

		soundtick++;
		if (soundtick > 49) {
			SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.5F, 1);
			soundtick = 0;
		}
	}

	private boolean hasHeatable(World world) {
		return this.getTileEntity(fx, fy, fz) instanceof FrictionHeatable;
	}

	private boolean canTileMake(TileEntityFurnace tile, ItemStack is) {
		ItemStack out = tile.getStackInSlot(2);
		if (out == null)
			return true;
		if (ReikaItemHelper.matchStacks(is, out) && is.stackSize+out.stackSize <= is.getMaxStackSize())
			return true;
		return false;
	}

	private void hijackFurnace(World world, int x, int y, int z, int meta) {
		TileEntity te = this.getTileEntity(fx, fy, fz);
		TileEntityFurnace tile = (TileEntityFurnace)te;
		int burn = Math.max(this.getBurnTimeFromTemperature(), tile.furnaceBurnTime);
		this.setFurnaceBlock(world, burn > 0);
		tile.currentItemBurnTime = burn;
		tile.furnaceBurnTime = burn;
		ItemStack in = tile.getStackInSlot(0);
		if (in != null) {
			ItemStack out = tile.getStackInSlot(2);
			ItemStack smelt = FurnaceRecipes.smelting().getSmeltingResult(in);
			ItemStack special = RecipesFrictionHeater.getRecipes().getSmelting(in, temperature);
			if (!this.canTileMake(tile, special))
				special = null;

			if (smelt != null || special != null) {
				this.smeltCalculation();
				smeltTime++;
				tile.furnaceCookTime = Math.min(smeltTime, 195);
				if (smeltTime >= 200) {
					if (smelt != null) {
						tile.smeltItem();
					}
					else if (special != null) {
						ReikaInventoryHelper.decrStack(0, tile, 1);
						int amt = out != null ? out.stackSize+1 : 1;
						out = ReikaItemHelper.getSizedItemStack(special, amt);
						tile.setInventorySlotContents(2, out);
					}
					smeltTime = 0;
				}
			}
			else {
				tile.furnaceCookTime = 0;
			}
		}
		else {
			tile.furnaceCookTime = 0;
		}
		//ReikaJavaLibrary.pConsole(smeltTime+" , "+tile.furnaceCookTime);
		soundtick++;
		if (soundtick > 49) {
			SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.5F, 1);
			soundtick = 0;
		}
		// world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.gravel", 1F, 2F);
		switch(meta) {
		case 0:
			world.spawnParticle("crit", x, fy+rand.nextDouble(), fz+rand.nextDouble(), -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
			break;
		case 1:
			world.spawnParticle("crit", x+1, fy+rand.nextDouble(), fz+rand.nextDouble(), -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
			break;
		case 2:
			world.spawnParticle("crit", fx+rand.nextDouble(), fy+rand.nextDouble(), z, -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
			break;
		case 3:
			world.spawnParticle("crit", fx+rand.nextDouble(), fy+rand.nextDouble(), z+1, -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
			break;
		}
	}

	private void setFurnaceBlock(World world, boolean isOn) {
		int id = world.getBlockId(fx, fy, fz);
		Block b = Block.blocksList[id];
		BlockFurnace furn = (BlockFurnace)b;
		//furn.updateFurnaceBlockState(isOn, world, fx, fy, fz);
	}

	private void smeltCalculation() {
		int factor = this.getSpeedFactorFromTemperature();
		smeltTime += factor;
	}

	private void getFurnaceCoordinates(World world, int x, int y, int z, int meta) {
		fy = y;
		fx = x;
		fz = z;
		switch(meta) {
		case 0:
			fx = x-1;
			break;
		case 1:
			fx = x+1;
			break;
		case 2:
			fz = z-1;
			break;
		case 3:
			fz = z+1;
			break;
		}
	}

	private void meltFurnace(World world) {
		int id = world.getBlockId(fx, fy, fz);
		if (id != Block.furnaceIdle.blockID && id != Block.furnaceBurning.blockID)
			return;
		world.createExplosion(null, fx+0.5, fy+0.5, fz+0.5, 1F, false);
		//world.setBlock(fx, fy, fz, Block.lavaMoving.blockID);
		world.setBlock(fx, fy, fz, 0);
		//ItemStack cobb = new ItemStack(Block.cobblestone);
		//for (int i = 0; i < 8; i++)
		//	ReikaItemHelper.dropItem(world, fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), cobb);
	}

	public boolean hasFurnace(World world) {
		return this.getTileEntity(fx, fy, fz) instanceof TileEntityFurnace;
	}

	private int getBurnTimeFromTemperature() {
		if (temperature < 300)
			return 0;
		return (temperature-300)*2;
	}

	private int getSpeedFactorFromTemperature() {
		if (temperature < 500)
			return 1;
		if (temperature == 2000)
			return 2000;
		return 1+(int)Math.sqrt((Math.pow(2, ((temperature-500)/100F))));
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return temperature/100;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temp", temperature);

		NBT.setInteger("furnx", fx);
		NBT.setInteger("furny", fy);
		NBT.setInteger("furnz", fz);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temp");

		fx = NBT.getInteger("furnx");
		fy = NBT.getInteger("furny");
		fz = NBT.getInteger("furnz");
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public void onEMP() {}

	@Override
	public boolean areConditionsMet() {
		return this.hasHeatableMachine(worldObj);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Heatable Machine";
	}

}
