/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityEnderFX extends EntityFX {

	private float scale;
	private boolean noSlow = false;
	private boolean rapidExpand = false;
	private AxisAlignedBB bounds = null;
	private double collideAngle;
	private boolean colliding = false;

	private int lifeFreeze;

	private int preColor = -1;
	private int fadeColor = -1;

	public EntityEnderFX(World world, double x, double y, double z, double vx, double vy, double vz, int color) {
		super(world, x, y, z, vx, vy, vz);
		particleGravity = 0;
		noClip = true;
		particleMaxAge = 60;
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		scale = 1F;
		particleRed = ReikaColorAPI.getRed(color)/255F;
		particleGreen = ReikaColorAPI.getGreen(color)/255F;
		particleBlue = ReikaColorAPI.getBlue(color)/255F;
		particleIcon = Items.ender_pearl.getIconFromDamage(0);
	}

	public EntityEnderFX setIcon(IIcon ico) {
		particleIcon = ico;
		return this;
	}

	public EntityEnderFX setScale(float f) {
		scale = f;
		return this;
	}

	public final EntityEnderFX setLife(int time) {
		particleMaxAge = time;
		return this;
	}

	public final EntityEnderFX setNoSlowdown() {
		noSlow = true;
		return this;
	}

	public EntityEnderFX setRapidExpand() {
		rapidExpand = true;
		return this;
	}

	public final EntityEnderFX setGravity(float g) {
		particleGravity = g;
		return this;
	}

	public final EntityEnderFX setColor(int r, int g, int b) {
		particleRed = r/255F;
		particleGreen = g/255F;
		particleBlue = b/255F;
		return this;
	}

	public final EntityEnderFX setColor(int rgb) {
		return this.setColor(ReikaColorAPI.getRed(rgb), ReikaColorAPI.getGreen(rgb), ReikaColorAPI.getBlue(rgb));
	}

	public final EntityEnderFX fadeColors(int c1, int c2) {
		preColor = c1;
		fadeColor = c2;
		return this.setColor(c1);
	}

	public final EntityEnderFX bound(AxisAlignedBB box) {
		bounds = box;
		return this;
	}

	public final EntityEnderFX setColliding() {
		return this.setColliding(rand.nextDouble()*360);
	}

	public final EntityEnderFX setColliding(double ang) {
		noClip = false;
		colliding = true;
		collideAngle = ang;
		return this;
	}

	@Override
	public void onUpdate() {

		if (colliding) {
			if (isCollidedVertically) {
				double v = rand.nextDouble()*0.0625;
				motionX = v*Math.sin(Math.toRadians(collideAngle));
				motionZ = v*Math.cos(Math.toRadians(collideAngle));
				colliding = false;
				this.setNoSlowdown();
				lifeFreeze = 20;
				particleGravity *= 4;
			}
			if (isCollidedHorizontally) {

			}
		}

		if (noSlow) {
			double mx = motionX;
			double my = motionY;
			double mz = motionZ;
			super.onUpdate();
			motionX = mx;
			motionY = my;
			motionZ = mz;
		}
		else {
			super.onUpdate();
		}

		if (lifeFreeze > 0) {
			lifeFreeze--;
			particleAge--;
		}

		if (fadeColor != -1) {
			int c = ReikaColorAPI.mixColors(fadeColor, preColor, particleAge/(float)particleMaxAge);
			this.setColor(c);
		}

		if (rapidExpand)
			particleScale = scale*(particleMaxAge/particleAge >= 12 ? particleAge*12F/particleMaxAge : 1-particleAge/(float)particleMaxAge);
		else
			particleScale = scale*(float)Math.sin(Math.toRadians(180D*particleAge/particleMaxAge));
		//if (particleAge < 10)
		//	particleScale = scale*(particleAge+1)/10F;
		//else if (particleAge > 50)
		//	particleScale = scale*(61-particleAge)/10F;
		//else
		//	particleScale = scale;

		if (bounds != null) {
			if ((posX <= bounds.minX && motionX < 0) || (posX >= bounds.maxX && motionX > 0)) {
				motionX = -motionX;
			}
			if ((posY <= bounds.minY && motionY < 0) || (posY >= bounds.maxY && motionY > 0)) {
				motionY = -motionY;
			}
			if ((posZ <= bounds.minZ && motionZ < 0) || (posZ >= bounds.maxZ && motionZ > 0)) {
				motionZ = -motionZ;
			}
		}
	}

	@Override
	public void renderParticle(Tessellator v5, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		ReikaTextureHelper.bindItemTexture();
		super.renderParticle(v5, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public int getBrightnessForRender(float par1)
	{
		return 240;
	}

	@Override
	public int getFXLayer()
	{
		return 2;
	}

}
