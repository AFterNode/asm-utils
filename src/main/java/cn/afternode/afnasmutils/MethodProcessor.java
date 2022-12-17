package cn.afternode.afnasmutils;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.List;

public class MethodProcessor {
    public static boolean hasReference(ClassNode classNode, String methodName, String calledMethodName) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(methodName)) {
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
                        MethodInsnNode methodInsn = (MethodInsnNode) insn;
                        if (methodInsn.name.equals(calledMethodName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String[] getReferences(ClassNode cn, String methodName, String descriptor) throws NoSuchMethodException {
        List<String> s = new ArrayList<>();
        boolean methodFound = false;
        for (MethodNode mn: cn.methods) {
            if (mn.name.equals(methodName) && mn.desc.equals(descriptor)) {
                methodFound = true;
                for (AbstractInsnNode ainsn: mn.instructions.toArray()) {
                    if (ainsn.getType() == AbstractInsnNode.METHOD_INSN) {
                        MethodInsnNode mInsn = (MethodInsnNode) ainsn;
                        s.add(mInsn.name + " " + mInsn.desc);
                    }
                }
            }
        }
        if (!methodFound) throw new NoSuchMethodException();
        return s.toArray(new String[0]);
    }
}
