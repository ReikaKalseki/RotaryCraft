/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.Extras.IconPrefabs;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Effects.EntityBlurFX;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.RefrigeratorAttachment;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.ProcessingMachine;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityRefrigerator extends InventoriedPowerLiquidProducer implements MultiOperational, ProcessingMachine, BreakAction {

	public int time;
	private StepTimer timer = new StepTimer(20);
	private StepTimer soundTimer = new StepTimer(20);

	private final RefrigeratorAttachment[] attachments = new RefrigeratorAttachment[6];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		timer.setCap(this.getOperationTime());

		if (this.canProgress()) {
			soundTimer.update();
			if (soundTimer.checkCap()) {
				SoundRegistry.FRIDGE.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.25F : 1, 0.88F);
			}
		}
		else {
			soundTimer.reset();
		}

		int n = this.getNumberConsecutiveOperations();
		for (int i = 0; i < n; i++)
			this.doOperation(n > 1);

		if (!world.isRemote)
			time = timer.getTick();
	}

	private void doOperation(boolean multiple) {
		if (this.canProgress()) {
			timer.update();
			if (multiple || timer.checkCap()) {
				if (!worldObj.isRemote)
					this.cycle();
			}
		}
		else {
			timer.reset();
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 0:
				read = ForgeDirection.EAST;
				break;
			case 1:
				read = ForgeDirection.WEST;
				break;
			case 2:
				read = ForgeDirection.SOUTH;
				break;
			case 3:
				read = ForgeDirection.NORTH;
				break;
		}
	}

	private boolean canProgress() {
		if (power < MINPOWER || torque < MINTORQUE)
			return false;
		if (inv[0] == null)
			return false;
		if (!tank.canTakeIn(this.getProducedLN2()))
			return false;
		return ReikaItemHelper.matchStackWithBlock(inv[0], Blocks.ice) && (inv[1] == null || inv[1].stackSize < inv[1].getMaxStackSize());
	}

	private void cycle() {
		ReikaInventoryHelper.decrStack(0, inv);
		int amt = this.getProducedLN2();
		tank.addLiquid(amt, FluidRegistry.getFluid("rc liquid nitrogen"));
		if (amt > 0) {
			for (RefrigeratorAttachment r : attachments) {
				if (r != null) {
					r.onCompleteCycle(amt);
				}
			}
			if (rand.nextInt(4) == 0) {
				int n = rand.nextInt(20) == 0 ? 4 : (rand.nextInt(4) == 0 ? 2 : 1);
				if (inv[1] != null)
					n = Math.min(n, ItemStacks.dryice.getMaxStackSize()-inv[1].stackSize);
				ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ItemStacks.dryice, n), inv, 1);
			}
		}
	}

	private int getProducedLN2() {
		int over = torque/MINTORQUE;
		return Math.min(2000, 100*over*over);
	}

	public void setLevel(int lvl) {
		if (!tank.isEmpty())
			tank.setContents(lvl, tank.getActualFluid());
	}

	public int getProgressScaled(int a) {
		return time * a / this.getOperationTime();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == 0 && ReikaItemHelper.matchStackWithBlock(is, Blocks.ice);
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 12000;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.REFRIGERATOR;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.REFRIGERATOR.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.REFRIGERATOR.getNumberOperations(omega);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		time = NBT.getInteger("timer");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("timer", time);
	}

	public int getLiquidScaled(int i) {
		return tank.getLevel() * i / tank.getCapacity();
	}

	public void addAttachment(RefrigeratorAttachment te, ForgeDirection dir) {
		attachments[dir.ordinal()] = te;
	}

	@Override
	protected void onPlacedNextToThis(TileEntity te, ForgeDirection dir) {
		attachments[dir.ordinal()] = null;
	}

	@Override
	public void breakBlock() {
		float f = tank.getFraction();
		if (f > 0.1) {
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.fizz", 1.2F, 0.8F);
			float hearts = f*4;
			AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(this).expand(5, 5, 5);
			List<EntityLivingBase> li = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box);
			RotaryCraft.freezeDamage.lastMachine = this;
			for (EntityLivingBase e : li) {
				e.attackEntityFrom(RotaryCraft.freezeDamage, hearts*2);
			}
			ReikaPacketHelper.sendDataPacketWithRadius(RotaryCraft.packetChannel, PacketRegistry.FRIDGEBREAK.ordinal(), this, 24);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void doBreakFX(World world, int x, int y, int z) {
		for (int i = 0; i < 15; i++) {
			double dx = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double dz = ReikaRandomHelper.getRandomPlusMinus(0, 0.5);
			double dy = ReikaRandomHelper.getRandomPlusMinus(0, 0.25);
			double v = 0.04;
			EntityBlurFX fx = new EntityBlurFX(world, x+0.5+dx, y+0.5+dy, z+0.5+dz, dx*v, dy*v, dz*v, IconPrefabs.FADE_GENTLE.getIcon());
			fx.setColor(0xBFB2FF).setScale(3+rand.nextFloat()*2).setRapidExpand().setAlphaFading().setLife(30+rand.nextInt(31)).setColliding();
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}

	@Override
	public boolean hasWork() {
		return this.areConditionsMet();
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && ReikaItemHelper.matchStackWithBlock(inv[0], Blocks.ice);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Ice";
	}

}
