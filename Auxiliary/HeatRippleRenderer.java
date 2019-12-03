package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.IO.Shaders.ShaderHook;
import Reika.DragonAPI.IO.Shaders.ShaderProgram;
import Reika.DragonAPI.Instantiable.RayTracer;
import Reika.RotaryCraft.ClientProxy;

public class HeatRippleRenderer implements ShaderHook {

	public static final HeatRippleRenderer instance = new HeatRippleRenderer();

	private final RayTracer LOS = RayTracer.getVisualLOS();

	private HeatRippleRenderer() {
		ClientProxy.getHeatRippleShader().setHook(this);
	}

	public void onPreRender(ShaderProgram s) {

	}

	public void onPostRender(ShaderProgram s) {
		s.setEnabled(s.hasOngoingFoci());
	}

	public void updateEnabled(ShaderProgram s) {

	}

	public boolean addHeatRippleEffectIfLOS(TileEntity tile, double x, double y, double z, float f, float fac, float scale, float centerFade) {
		EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
		double dist = ep.getDistance(x, y, z);
		return this.addHeatRippleEffectIfLOS(tile, x, y, z, ep, dist, f, fac, scale, centerFade);
	}

	public boolean addHeatRippleEffectIfLOS(TileEntity tile, double x, double y, double z, EntityPlayer ep, double dist, float f, float fac, float scale, float centerFade) {
		LOS.setOrigins(x, y, z, ep.posX, ep.posY, ep.posZ);
		if (LOS.isClearLineOfSight(tile.worldObj)) {
			HeatRippleRenderer.instance.addHeatRippleEffect(tile, dist, f, fac, scale, centerFade);
			return true;
		}
		return false;
	}

	public void addHeatRippleEffect(TileEntity tile, double dist, float f, float fac, float scale, float centerFade) {
		HashMap<String, Object> map = new HashMap();
		map.put("distance", dist*dist);
		map.put("factor", fac);
		map.put("scale", scale);
		map.put("fade", centerFade);
		ClientProxy.getHeatRippleShader().addFocus(tile);
		ClientProxy.getHeatRippleShader().modifyLastCompoundFocus(f, map);

		ClientProxy.getHeatRippleShader().setEnabled(true);
		ClientProxy.getHeatRippleShader().setIntensity(1);
	}
}
