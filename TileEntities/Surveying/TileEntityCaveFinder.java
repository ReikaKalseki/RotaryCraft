/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Renders.M.RenderCaveFinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityCaveFinder extends TileEntityPowerReceiver implements RangedEffect {

	private int[] src = new int[3];
	private int rendermode = 0;
	public String owner;
	public boolean on;
	private boolean[][][] points = new boolean[this.getRange()*2+1][this.getRange()*2+1][this.getRange()*2+1];
	private boolean needsCalc = true;

	private Scanner scanner = new Scanner(this);

	private final class Scanner implements Runnable {

		private final TileEntityCaveFinder tile;

		private Scanner(TileEntityCaveFinder te) {
			tile = te;
		}

		@Override
		public void run() {
			int r = tile.getRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						int x = tile.getSourceX()+i;
						int y = tile.getSourceY()+j;
						int z = tile.getSourceZ()+k;
						if (ReikaWorldHelper.cornerHasAirAdjacent(worldObj, x, y, z)) {
							tile.points[i+r][j+r][k+r] = true;
							//ReikaJavaLibrary.pConsole(x+", "+y+", "+z);
							//ReikaJavaLibrary.pConsole((i+r)+", "+(j+r)+", "+(k+r));
						}
						else {
							tile.points[i+r][j+r][k+r] = false;
						}
					}
				}
			}
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		if (power < MINPOWER) {
			on = false;
			return;
		}
		on = true;

		if (src[0] == 0 && src[1] == 0 && src[2] == 0 && this.getTicksExisted() < 2)
			this.setSrc(x, y, z);

		if (rendermode == 0) {

		}
		else if (rendermode == 1) {
			EntityPlayer ep = world.getClosestPlayer(x, y, z, -1);
			if (ep == null)
				return;
			int px = (int)ep.posX;
			int py = (int)ep.posY;
			int pz = (int)ep.posZ;
			this.setSrc(px, py, pz);
		}

		int t = this.getUpdateFrequency();
		if (needsCalc || (world.getTotalWorldTime()&t) == 0)
			this.calculatePoints();

		//ReikaJavaLibrary.pConsole(Arrays.deepToString(points));
	}

	private int getUpdateFrequency() {
		int r = this.getRange();
		if (r < 16)
			return 1;
		else if (r < 32)
			return 3;
		else if (r < 64)
			return 7;
		else if (r < 128)
			return 15;
		return 31;
	}

	private void calculatePoints() {
		Thread t = new Thread(scanner);
		t.start();
		needsCalc = false;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			RenderCaveFinder rcf = (RenderCaveFinder)this.getRenderer();
			rcf.removeListFor(this);
		}
	}

	public boolean hasPointAt(int dx, int dy, int dz) {
		int r = this.getRange();
		if (Math.abs(dx) > r*2 || Math.abs(dy) > r*2 || Math.abs(dz) > r*2) {
			//ReikaJavaLibrary.pConsole(dx+", "+dy+", "+dz);
			return false;
		}
		if (Math.abs(dx) < 0 || Math.abs(dy) < 0 || Math.abs(dz) < 0) {
			//ReikaJavaLibrary.pConsole(dx+", "+dy+", "+dz);
			return false;
		}
		try {
			return points[dx][dy][dz];
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Exception at "+dx+", "+dy+", "+dz+"!");
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
	public double getMaxRenderDistanceSquared() {
		return 65536D;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public int getRange() {
		return Math.max(4, ConfigRegistry.CAVEFINDERRANGE.getValue());
	}

	public int getSourceX() {
		return src[0];
	}

	public int getSourceY() {
		return src[1];
	}

	public int getSourceZ() {
		return src[2];
	}

	public void setSrc(int x, int y, int z) {
		src[0] = x;
		src[1] = y;
		src[2] = z;
		needsCalc = true;
	}

	public void moveSrc(int num, ForgeDirection dir) {
		switch(dir) {
		case DOWN:
			src[1] -= num;
			break;
		case UP:
			src[1] += num;
			break;
		case WEST:
			src[0] -= num;
			break;
		case EAST:
			src[0] += num;
			break;
		case NORTH:
			src[2] -= num;
			break;
		case SOUTH:
			src[2] += num;
			break;
		default:
			break;
		}
		needsCalc = true;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setIntArray("Source", src);
		NBT.setBoolean("calc", needsCalc);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		src = NBT.getIntArray("Source");
		needsCalc = NBT.getBoolean("calc");
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CAVESCANNER;
	}

	@Override
	public int getMaxRange() {
		return 128;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}