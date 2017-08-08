/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Statements;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ModInteract.Statements.StatementBase;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import buildcraft.api.statements.IStatementParameter;

public class StatementHasWork extends StatementBase {

	public StatementHasWork() {
		super("RC:HasWork", questionIcon);
	}

	@Override
	public String getDescription() {
		return "Returns true if the machine has work to do.";
	}

	@Override
	protected int getArgCount() {
		return 0;
	}

	@Override
	protected boolean evaluate(TileEntity te, ForgeDirection dir, TileEntity caller, IStatementParameter[] args) {
		return te instanceof ConditionalOperation && ((ConditionalOperation)te).areConditionsMet();
	}

}
