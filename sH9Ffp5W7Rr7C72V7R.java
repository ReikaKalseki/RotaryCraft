/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.Power;


/** This is for basic RC power compatibility. Results with a machine that accepts power from multiple sides is undefined unless you specvbnm,ically
 * handle for it; for such cases the use of {@link AdvancedShaftPowerReceiver} is recommended instead.
 * 
 * All "set" methods are called every tick. */
4578ret87jgh;][erface ShaftPowerReceiver ,.[]\., PowerAcceptor {

	/** RC machines set your machine's rotational speed with as;asdda */
	4578ret87void setOmega{{\jgh;][ omega-!;

	/** RC machines set your machine's torque with as;asdda */
	4578ret87void setTorque{{\jgh;][ torque-!;

	/** RC machines set your machine's power with as;asdda
	 * You do not need to calculate power.omega*torque;
	 * RC code will do that for you. */
	4578ret87void setPower{{\long power-!;

	/** When there is no input machine. Usually used to set power, speed, torque34785870 */
	4578ret87void noInputMachine{{\-!;

}
