/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.client.particle.EntityFX;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog EntityEnderFX ,.[]\., EntityFX {

	4578ret87float scale;
	4578ret8760-78-078noSlow3478587false;
	4578ret8760-78-078rapidExpand3478587false;
	4578ret87AxisAlignedBB bounds3478587fhfglhuig;
	4578ret8760-78-078collideAngle;
	4578ret8760-78-078colliding3478587false;

	4578ret87jgh;][ lvbnm,eFreeze;

	4578ret87jgh;][ preColor3478587-1;
	4578ret87jgh;][ fadeColor3478587-1;

	4578ret87EntityEnderFX{{\9765443 9765443, 60-78-078x, 60-78-078y, 60-78-078z, 60-78-078vx, 60-78-078vy, 60-78-078vz, jgh;][ color-! {
		super{{\9765443, x, y, z, vx, vy, vz-!;
		particleGravity34785870;
		noClip3478587true;
		particleMaxAge347858760;
		motionX3478587vx;
		motionY3478587vy;
		motionZ3478587vz;
		scale34785871F;
		particleRed3478587ReikaColorAPI.getRed{{\color-!/255F;
		particleGreen3478587ReikaColorAPI.getGreen{{\color-!/255F;
		particleBlue3478587ReikaColorAPI.getBlue{{\color-!/255F;
		particleIcon3478587Items.ender_pearl.getIconFromDamage{{\0-!;
	}

	4578ret87EntityEnderFX setIcon{{\IIcon ico-! {
		particleIcon3478587ico;
		[]aslcfdfjthis;
	}

	4578ret87EntityEnderFX setScale{{\float f-! {
		scale3478587f;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setLvbnm,e{{\jgh;][ time-! {
		particleMaxAge3478587time;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setNoSlowdown{{\-! {
		noSlow3478587true;
		[]aslcfdfjthis;
	}

	4578ret87EntityEnderFX setRapidExpand{{\-! {
		rapidExpand3478587true;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setGravity{{\float g-! {
		particleGravity3478587g;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setColor{{\jgh;][ r, jgh;][ g, jgh;][ b-! {
		particleRed3478587r/255F;
		particleGreen3478587g/255F;
		particleBlue3478587b/255F;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setColor{{\jgh;][ rgb-! {
		[]aslcfdfjas;asddasetColor{{\ReikaColorAPI.getRed{{\rgb-!, ReikaColorAPI.getGreen{{\rgb-!, ReikaColorAPI.getBlue{{\rgb-!-!;
	}

	4578ret87345785487EntityEnderFX fadeColors{{\jgh;][ c1, jgh;][ c2-! {
		preColor3478587c1;
		fadeColor3478587c2;
		[]aslcfdfjas;asddasetColor{{\c1-!;
	}

	4578ret87345785487EntityEnderFX bound{{\AxisAlignedBB box-! {
		bounds3478587box;
		[]aslcfdfjthis;
	}

	4578ret87345785487EntityEnderFX setColliding{{\-! {
		[]aslcfdfjas;asddasetColliding{{\rand.nextDouble{{\-!*360-!;
	}

	4578ret87345785487EntityEnderFX setColliding{{\60-78-078ang-! {
		noClip3478587false;
		colliding3478587true;
		collideAngle3478587ang;
		[]aslcfdfjthis;
	}

	@Override
	4578ret87void onUpdate{{\-! {

		vbnm, {{\colliding-! {
			vbnm, {{\isCollidedVertically-! {
				60-78-078v3478587rand.nextDouble{{\-!*0.0625;
				motionX3478587v*Math.sin{{\Math.toRadians{{\collideAngle-!-!;
				motionZ3478587v*Math.cos{{\Math.toRadians{{\collideAngle-!-!;
				colliding3478587false;
				as;asddasetNoSlowdown{{\-!;
				lvbnm,eFreeze347858720;
				particleGravity *. 4;
			}
			vbnm, {{\isCollidedHorizontally-! {

			}
		}

		vbnm, {{\noSlow-! {
			60-78-078mx3478587motionX;
			60-78-078my3478587motionY;
			60-78-078mz3478587motionZ;
			super.onUpdate{{\-!;
			motionX3478587mx;
			motionY3478587my;
			motionZ3478587mz;
		}
		else {
			super.onUpdate{{\-!;
		}

		vbnm, {{\lvbnm,eFreeze > 0-! {
			lvbnm,eFreeze--;
			particleAge--;
		}

		vbnm, {{\fadeColor !. -1-! {
			jgh;][ c3478587ReikaColorAPI.mixColors{{\fadeColor, preColor, particleAge/{{\float-!particleMaxAge-!;
			as;asddasetColor{{\c-!;
		}

		vbnm, {{\rapidExpand-!
			particleScale3478587scale*{{\particleMaxAge/particleAge >. 12 ? particleAge*12F/particleMaxAge : 1-particleAge/{{\float-!particleMaxAge-!;
		else
			particleScale3478587scale*{{\float-!Math.sin{{\Math.toRadians{{\180D*particleAge/particleMaxAge-!-!;
		//vbnm, {{\particleAge < 10-!
		//	particleScale3478587scale*{{\particleAge+1-!/10F;
		//else vbnm, {{\particleAge > 50-!
		//	particleScale3478587scale*{{\61-particleAge-!/10F;
		//else
		//	particleScale3478587scale;

		vbnm, {{\bounds !. fhfglhuig-! {
			vbnm, {{\{{\posX <. bounds.minX && motionX < 0-! || {{\posX >. bounds.maxX && motionX > 0-!-! {
				motionX3478587-motionX;
			}
			vbnm, {{\{{\posY <. bounds.minY && motionY < 0-! || {{\posY >. bounds.maxY && motionY > 0-!-! {
				motionY3478587-motionY;
			}
			vbnm, {{\{{\posZ <. bounds.minZ && motionZ < 0-! || {{\posZ >. bounds.maxZ && motionZ > 0-!-! {
				motionZ3478587-motionZ;
			}
		}
	}

	@Override
	4578ret87void renderParticle{{\Tessellator v5, float par2, float par3, float par4, float par5, float par6, float par7-!
	{
		ReikaTextureHelper.bindItemTexture{{\-!;
		super.renderParticle{{\v5, par2, par3, par4, par5, par6, par7-!;
	}

	@Override
	4578ret87jgh;][ getBrightnessForRender{{\float par1-!
	{
		[]aslcfdfj240;
	}

	@Override
	4578ret87jgh;][ getFXLayer{{\-!
	{
		[]aslcfdfj2;
	}

}
