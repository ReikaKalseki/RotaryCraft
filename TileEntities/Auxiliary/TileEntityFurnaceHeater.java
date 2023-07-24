/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaThaumHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.TinkerSmelteryHandler;
import Reika.DragonAPI.ModInteract.DeepInteract.TinkerSmelteryHandler.SmelteryWrapper;
import Reika.DragonAPI.ModInteract.DeepInteract.TransvectorHandler;
import Reika.RotaryCraft.API.Interfaces.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater.FrictionRecipe;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityFurnaceHeater extends TileEntityPowerReceiver implements TemperatureTE, ConditionalOperation {

	public static final int MAXTEMP = 2000;

	private FrictionRecipe activeRecipe;
	private Coordinate furnaceLocation;

	private int temperature;
	private int smeltTime = 0;
	private int soundtick = 0;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if (torque >= MINTORQUE && power >= MINPOWER && omega > 0 && this.hasHeatableMachine(world)) {
			temperature += 3*ReikaMathLibrary.logbase(omega, 2)*ReikaMathLibrary.logbase(torque, 2);
		}
		int Tamb = power > MINPOWER && torque > MINTORQUE ? 30 : ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z); //prevent nether exploit
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
			if (!world.isRemote && ConfigRegistry.BLOCKDAMAGE.getState() && rand.nextInt(DifficultyEffects.FURNACEMELT.getInt()) == 0)
				this.meltFurnace(world);
		if (temperature < Tamb)
			temperature = Tamb;
	}

	private boolean hasHeatableMachine(World world) {
		if (furnaceLocation == null)
			return false;
		Block id = furnaceLocation.getBlock(world);
		if (id == Blocks.air)
			return false;
		if (id == Blocks.furnace || id == Blocks.lit_furnace)
			return true;
		int meta = furnaceLocation.getBlockMetadata(world);
		MachineRegistry m = MachineRegistry.getMachine(world, furnaceLocation.xCoord, furnaceLocation.yCoord, furnaceLocation.zCoord);
		if (m != null && m.canBeFrictionHeated())
			return true;
		TileEntity te = furnaceLocation.getTileEntity(world);
		if (ModList.THAUMICTINKER.isLoaded()) {
			TileEntity relay = TransvectorHandler.getRelayedTile(te);
			while (relay != te && relay != null) {
				te = relay;
				relay = TransvectorHandler.getRelayedTile(te);
			}
			te = relay;
			if (te != null) {
				furnaceLocation = new Coordinate(te);
			}
		}
		if (ModList.TINKERER.isLoaded()) {
			if (TinkerSmelteryHandler.isSmelteryController(te))
				return true;
		}
		if (ModList.THAUMCRAFT.isLoaded()) {
			if (ReikaThaumHelper.isAlchemicalFurnace(te))
				return true;
		}
		return te instanceof ThermalMachine;
	}

	@Override
	public int getThermalDamage() {
		return power > 0 ? temperature*5/1200 : 0;
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
	public MachineRegistry getTile() {
		return MachineRegistry.FRICTION;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		activeRecipe = null;
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		this.getFurnaceCoordinates(world, x, y, z, meta);
		if (tickcount >= 20) {
			tickcount = 0;
			this.updateTemperature(world, x, y, z, meta);
		}
		if (!this.isActive())
			return;

		if (this.hasFurnace()) {
			this.hijackFurnace(world, x, y, z, meta);
		}
		else {
			TileEntity te = furnaceLocation.getTileEntity(world);
			/*
			if (ModList.THAUMICTINKER.isLoaded()) {
				TileEntity relay = TransvectorHandler.getRelayedTile(te);
				while (relay != te && relay != null) {
					te = relay;
					relay = TransvectorHandler.getRelayedTile(te);
				}
				te = relay;
				if (te != null) {
					fx = te.xCoord;
					fy = te.yCoord;
					fz = te.zCoord;
				}
			}
			 */
			if (te instanceof ThermalMachine) {
				this.heatMachine(world, x, y, z, (ThermalMachine)te);
			}
			else if (ModList.TINKERER.isLoaded() && TinkerSmelteryHandler.isSmelteryController(te)) {
				SmelteryWrapper s = new SmelteryWrapper(te);
				s.fuelLevel = 4000;
				s.meltPower = temperature*25/6; //that allows the friction heater to break pyrotheum temperatures at its 1200C (~800kW)
				s.write(te);
			}
			else if (ModList.THAUMCRAFT.isLoaded() && ReikaThaumHelper.isAlchemicalFurnace(te)) {
				ReikaThaumHelper.setAlchemicalBurnTime(te, 1+temperature/20);
			}
		}
	}

	public boolean isActive() {
		return power >= MINPOWER && torque >= MINTORQUE;
	}

	private void heatMachine(World world, int x, int y, int z, ThermalMachine te) {
		if (te.canBeFrictionHeated()) {
			int tdiff = Math.min(te.getMaxTemperature(), temperature)-te.getTemperature();
			if (tdiff > 0 || (tdiff == 0 && temperature == te.getMaxTemperature())) {
				te.addTemperature(Math.max(1, (int)(tdiff*te.getMultiplier())));
				te.resetAmbientTemperatureTimer();
			}

			if (te.getTemperature() > te.getMaxTemperature()) {
				te.onOverheat(world, furnaceLocation.xCoord, furnaceLocation.yCoord, furnaceLocation.zCoord);
			}

			soundtick++;
			if (soundtick > 49) {
				SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.5F, 1);
				soundtick = 0;
			}
		}
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
		TileEntity te = furnaceLocation.getTileEntity(world);
		TileEntityFurnace tile = (TileEntityFurnace)te;
		boolean flag = tile.furnaceBurnTime > 0;
		int burn = Math.max(this.getBurnTimeFromTemperature(), tile.furnaceBurnTime);
		tile.currentItemBurnTime = burn;
		tile.furnaceBurnTime = burn;
		if (burn > 0 && !flag) {
			BlockFurnace.updateFurnaceBlockState(true, world, furnaceLocation.xCoord, furnaceLocation.yCoord, furnaceLocation.zCoord);
		}
		ItemStack in = tile.getStackInSlot(0);
		int fx = furnaceLocation.xCoord;
		int fy = furnaceLocation.yCoord;
		int fz = furnaceLocation.zCoord;
		if (in != null) {
			ItemStack out = tile.getStackInSlot(2);
			ItemStack smelt = FurnaceRecipes.smelting().getSmeltingResult(in);
			FrictionRecipe special = RecipesFrictionHeater.getRecipes().getSmelting(in, temperature);
			if (special != null && !this.canTileMake(tile, special.getOutput()))
				special = null;
			if (smelt != null || special != null) {
				activeRecipe = special;
				int factor = this.getSpeedFactorFromTemperature();
				if (special != null)
					factor *= this.getAccelerationFactor(special);
				smeltTime += 1+factor;
				int max = special != null ? special.duration : 200;
				tile.furnaceCookTime = Math.min(smeltTime, max-5)*200/max;
				if (smeltTime >= max) {
					int xp = 0;
					if (smelt != null && tile.canSmelt()) {
						tile.smeltItem();
						xp = MathHelper.ceiling_float_int(FurnaceRecipes.smelting().func_151398_b(smelt));
					}
					else if (special != null) {
						ItemStack out2 = special.getOutput();
						ReikaInventoryHelper.decrStack(0, tile, 1);
						int amt = out != null ? out.stackSize+out2.stackSize : out2.stackSize;
						out = ReikaItemHelper.getSizedItemStack(out2, amt);
						tile.setInventorySlotContents(2, out);
						xp = 1;
					}
					if (xp > 0 && ConfigRegistry.FRICTIONXP.getState()) {
						ReikaWorldHelper.splitAndSpawnXP(world, fx+0.5, fy+0.6, fz+0.5, xp, 600);
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

	private float getAccelerationFactor(FrictionRecipe rec) {
		float fac = temperature/(float)rec.requiredTemperature;
		return Math.min(1, (fac*fac)-1);
	}

	private void getFurnaceCoordinates(World world, int x, int y, int z, int meta) {
		furnaceLocation = new Coordinate(this).offset(this.getReadDirection().getOpposite(), 1);
	}

	private void meltFurnace(World world) {
		Block id = furnaceLocation.getBlock(world);
		if (id != Blocks.furnace && id != Blocks.lit_furnace)
			return;
		world.createExplosion(null, furnaceLocation.xCoord+0.5, furnaceLocation.yCoord+0.5, furnaceLocation.zCoord+0.5, 1F, false);
		//world.setBlock(fx, fy, fz, Blocks.flowing_lava.blockID);
		furnaceLocation.setBlock(world, Blocks.air);
		//ItemStack cobb = new ItemStack(Blocks.cobblestone);
		//for (int i = 0; i < 8; i++)
		//	ReikaItemHelper.dropItem(world, fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), cobb);
	}

	public boolean hasFurnace() {
		return furnaceLocation != null && furnaceLocation.getTileEntity(worldObj) instanceof TileEntityFurnace;
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

	public FrictionRecipe getActiveRecipe() {
		return activeRecipe;
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

		if (furnaceLocation != null)
			furnaceLocation.writeToNBT("furnLoc", NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temp");

		if (NBT.hasKey("furnLoc"))
			furnaceLocation = Coordinate.readFromNBT("furnLoc", NBT);
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

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	@Override
	public boolean allowHeatExtraction() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	public static boolean isHijacked(TileEntityFurnace furn) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int dx = furn.xCoord+dir.offsetX;
			int dz = furn.zCoord+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(furn.worldObj, dx, furn.yCoord, dz);
			if (m == MachineRegistry.FRICTION) {
				TileEntityFurnaceHeater te = (TileEntityFurnaceHeater)furn.worldObj.getTileEntity(dx, furn.yCoord, dz);
				if (te.furnaceLocation != null && te.furnaceLocation.equals(furn)) {
					if (te.isActive())
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

}
