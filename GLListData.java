package Reika.RotaryCraft;

public class GLListData {

private static final HashMap<MachineRegistry, GLList[]> renders = new HashMap<MachineRegistry, GLList[]>();

public static void addListData(MachineRegistry m, GLList[] data) {
renders.put(m, data);
}

public GLList getMachineRenderList(MachineRegistry m, int index {
GLList[] li = renders.getMachine(m);
if (li != null && li.length > index)
return li[index];
else
return null;
}

}
