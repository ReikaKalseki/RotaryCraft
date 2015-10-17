/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

@Deprecated
public class CompoundReservoir {
	/*
	private final BlockArray blocks = new BlockArray();
	private final World world;

	private long lastTick;

	public CompoundReservoir(World world) {
		blocks.setWorld(world);
		this.world = world;
	}

	public CompoundReservoir addReservoir(TileEntityReservoir te) {
		blocks.addBlockCoordinate(te.xCoord, te.yCoord, te.zCoord);
		return this;
	}

	public void removeReservoir(TileEntityReservoir te) {
		blocks.remove(te.xCoord, te.yCoord, te.zCoord);
		for (Coordinate c : blocks.keySet()) {
			if (te instanceof TileEntityReservoir) {
				te.recalculateCompound();
			}
		}
		this.clear();
	}

	public void merge(CompoundReservoir cr) {
		for (Coordinate c : cr.blocks.keySet()) {
			TileEntity te = c.getTileEntity(world);
			if (te instanceof TileEntityReservoir) {
				((TileEntityReservoir)te).setCompound(this);
			}
			blocks.addBlockCoordinate(c.xCoord, c.yCoord, c.zCoord);
		}
		cr.clear();
	}

	public int getSize() {
		return blocks.getSize();
	}

	public void clear() {
		blocks.clear();
	}

	public void tick() {
		long time = world.getTotalWorldTime();
		if (time == lastTick)
			return;
		lastTick = time;
		this.doTick();
	}

	private void doTick() {
		Fluid f = this.getFluid();
		if (f == null)
			return;
		for (Coordinate c : blocks.keySet()) {
			int x = c.xCoord;
			int y = c.yCoord;
			int z = c.zCoord;
			int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
			int temp = this.getFluid().getTemperature(world, x, y, z)-273;
			int dT = temp-Tamb;
			int r = 2;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						double dd = ReikaMathLibrary.py3d(i, j, k)+1;
						int hiT = (int)(Tamb+dT/dd/2D);
						ReikaWorldHelper.temperatureEnvironment(world, x+i, y+j, z+k, hiT);
						if (temp > 2500)
							ReikaSoundHelper.playSoundAtBlock(world, x+i, y+j, z+k, "random.fizz", 0.2F, 1F);
					}
				}
			}
			if (temp > 2500) {
				world.setBlock(x, y, z, Blocks.flowing_lava);
				world.setBlock(x+1, y, z, Blocks.flowing_lava);
				world.setBlock(x-1, y, z, Blocks.flowing_lava);
				world.setBlock(x, y, z+1, Blocks.flowing_lava);
				world.setBlock(x, y, z-1, Blocks.flowing_lava);
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz", 0.4F, 1F);
			}

			boolean hot = Tamb >= 300;
			hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null;
			hot = hot || ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) != null;
			if (hot) {
				boolean flammable = f.equals(FluidRegistry.getFluid("rc ethanol")) || f.equals(FluidRegistry.getFluid("rc jet fuel"));
				flammable = flammable || f.equals(FluidRegistry.getFluid("oil")) || f.equals(FluidRegistry.getFluid("fuel"));
				flammable = flammable || f.equals(FluidRegistry.getFluid("ethanol")) || f.equals(FluidRegistry.getFluid("creosote"));
				flammable = flammable || f.equals(FluidRegistry.getFluid("biofuel")) || f.equals(FluidRegistry.getFluid("bioethanol"));
				if (flammable) {
					world.setBlockToAir(x, y, z);
					world.newExplosion(null, x+0.5, y+0.5, z+0.5, 4, true, true);
				}
			}
		}
	}

	public Fluid getFluid() {
		Coordinate c = blocks.getNthBlock(0);
		if (c == null)
			return null;
		TileEntity te = c.getTileEntity(world);
		if (te instanceof TileEntityReservoir) {
			return ((TileEntityReservoir)te).getFluid();
		}
		return null;
	}

	public int getTotalAmount() {
		int amt = 0;
		for (Coordinate c : blocks.keySet()) {
			TileEntity te = c.getTileEntity(world);
			if (te instanceof TileEntityReservoir) {
				amt += ((TileEntityReservoir)te).getLevel();
			}
		}
		return amt;
	}
	 */
}
