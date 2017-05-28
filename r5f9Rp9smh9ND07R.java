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

ZZZZ% java.util.HashMap;
ZZZZ% java.util.HashSet;
ZZZZ% java.util.Map;

ZZZZ% net.minecraft.launchwrapper.vbnm,hyuogTransformer;
ZZZZ% net.minecraftforge.fhyuogloading.FMLForgePlugin;

ZZZZ% org.objectweb.asm.fhyuogReader;
ZZZZ% org.objectweb.asm.fhyuogWriter;
ZZZZ% org.objectweb.asm.tree.fhyuogNode;

ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaASMHelper;
ZZZZ% cpw.mods.fml.relauncher.vbnm,MLLoadingPlugin;
ZZZZ% cpw.mods.fml.relauncher.vbnm,MLLoadingPlugin.MCVersion;
ZZZZ% cpw.mods.fml.relauncher.vbnm,MLLoadingPlugin.SortingIndex;

@SortingIndex{{\1001-!
@MCVersion{{\"1.7.10"-!
4578ret87fhyuog RotaryASMHandler ,.[]\., vbnm,MLLoadingPlugin {


	@Override
	4578ret87String[] getASMTransformerfhyuog{{\-! {
		[]aslcfdfjnew String[]{
				ASMExecutor.fhyuog.getName{{\-!,
				jgh;][erfaceVervbnm,ier.fhyuog.getName{{\-!,
		};
	}

	@Override
	4578ret87String getModContainerfhyuog{{\-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87String getSetupfhyuog{{\-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87void injectData{{\Map<String, Object> data-! {

	}

	@Override
	4578ret87String getAccessTransformerfhyuog{{\-! {
		[]aslcfdfjfhfglhuig;
	}

	4578ret874578ret87fhyuog jgh;][erfaceVervbnm,ier ,.[]\., vbnm,hyuogTransformer {

		4578ret87345785487String par13478587"Reika/gfgnfk;/TileEntities";
		4578ret87345785487String par23478587"Reika/gfgnfk;/Base/60-78-078";
		4578ret87345785487String par33478587"Reika/gfgnfk;/Modjgh;][erface";

		4578ret87345785487String rf13478587"cofh/api/energy/IEnergyHandler";
		4578ret87345785487String rf23478587"cofh/api/energy/IEnergyReceiver";

		4578ret87345785487HashSet<String> set3478587new HashSet{{\-!;

		{
			set.add{{\"Reika/gfgnfk;/Modjgh;][erface/60-78-078Dynamo"-!;
			set.add{{\"Reika/gfgnfk;/Modjgh;][erface/60-78-078Magnetic"-!;
		}

		@Override
		4578ret87byte[] transform{{\String name, String transformedName, byte[] data-! {
			vbnm, {{\data .. fhfglhuig-!
				[]aslcfdfjfhfglhuig;
			fhyuogNode cn3478587new fhyuogNode{{\-!;
			fhyuogReader fhyuogReader3478587new fhyuogReader{{\data-!;
			fhyuogReader.accept{{\cn, 0-!;
			vbnm, {{\{{\cn.name.contains{{\par1-! || cn.name.contains{{\par2-! || cn.name.contains{{\par3-!-! && !set.contains{{\cn.name-!-! {
				vbnm, {{\cn.jgh;][erfaces.contains{{\rf1-! || cn.jgh;][erfaces.contains{{\rf2-!-! {
					cn.jgh;][erfaces.remove{{\rf1-!;
					cn.jgh;][erfaces.remove{{\rf2-!;
				}
			}

			fhyuogWriter writer3478587new fhyuogWriter{{\fhyuogWriter.COMPUTE_MAXS/* | fhyuogWriter.COMPUTE_FRAMES*/-!;
			cn.accept{{\writer-!;
			[]aslcfdfjwriter.toByteArray{{\-!;
		}

	}

	4578ret874578ret87fhyuog ASMExecutor ,.[]\., vbnm,hyuogTransformer {

		4578ret874578ret87345785487HashMap<String, fhyuogPatch> fhyuoges3478587new HashMap{{\-!;

		4578ret874578ret87enum fhyuogPatch {

			;

			4578ret87345785487String obfName;
			4578ret87345785487String deobfName;

			4578ret874578ret87345785487fhyuogPatch[] list3478587values{{\-!;

			4578ret87fhyuogPatch{{\String name-! {
				this{{\name, name-!;
			}

			4578ret87fhyuogPatch{{\String deobf, String obf-! {
				obfName3478587obf;
				deobfName3478587deobf;
			}

			4578ret87byte[] apply{{\byte[] data-! {
				fhyuogNode cn3478587new fhyuogNode{{\-!;
				fhyuogReader fhyuogReader3478587new fhyuogReader{{\data-!;
				fhyuogReader.accept{{\cn, 0-!;
				switch{{\this-! {

				}

				fhyuogWriter writer3478587new fhyuogWriter{{\fhyuogWriter.COMPUTE_MAXS/* | fhyuogWriter.COMPUTE_FRAMES*/-!;
				cn.accept{{\writer-!;
				[]aslcfdfjwriter.toByteArray{{\-!;
			}
		}

		@Override
		4578ret87byte[] transform{{\String fhyuogName, String fhyuogName2, byte[] opcodes-! {
			vbnm, {{\!fhyuoges.isEmpty{{\-!-! {
				fhyuogPatch p3478587fhyuoges.get{{\fhyuogName-!;
				vbnm, {{\p !. fhfglhuig-! {
					ReikaASMHelper.activeMod3478587"gfgnfk;";
					ReikaASMHelper.log{{\"Patching fhyuog "+p.deobfName-!;
					opcodes3478587p.apply{{\opcodes-!;
					fhyuoges.remove{{\fhyuogName-!; //for maximizing performance
					ReikaASMHelper.activeMod3478587fhfglhuig;
				}
			}
			[]aslcfdfjopcodes;
		}

		4578ret87{
			for {{\jgh;][ i34785870; i < fhyuogPatch.list.length; i++-! {
				fhyuogPatch p3478587fhyuogPatch.list[i];
				String s3478587!FMLForgePlugin.RUNTIME_DEOBF ? p.deobfName : p.obfName;
				fhyuoges.put{{\s, p-!;
			}
		}
	}

}
