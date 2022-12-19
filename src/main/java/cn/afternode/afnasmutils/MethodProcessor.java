package cn.afternode.afnasmutils;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Method;
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

    public static boolean hasReference(MethodNode mn, Method m) {
        for (AbstractInsnNode aInsn: mn.instructions) {
            if (aInsn.getType() == AbstractInsnNode.METHOD_INSN) {
                MethodInsnNode mInsn = (MethodInsnNode) aInsn;
                if (mInsn.name.equals(m.getName())) return true;
            }
        }
        return false;
    }

    public static boolean hasReferenceName(MethodNode mn, String name) {
        for (AbstractInsnNode aInsn: mn.instructions) {
            if (aInsn.getType() == AbstractInsnNode.METHOD_INSN) {
                MethodInsnNode mInsn = (MethodInsnNode) aInsn;
                if (mInsn.name.equals(name)) return true;
            }
        }
        return false;
    }

    public static String[] getReferences(ClassNode cn, String methodName, String descriptor) throws NoSuchMethodException {
        String[] s = new String[0];
        boolean methodFound = false;
        for (MethodNode mn: cn.methods) {
            if (mn.name.equals(methodName) && mn.desc.equals(descriptor)) {
                methodFound = true;
                s = getReferences(cn, mn);
            }
        }
        if (!methodFound) throw new NoSuchMethodException();
        return s;
    }

    public static String[] getReferences(ClassNode cn, MethodNode mn) {
        List<String> s = new ArrayList<>();
        for (AbstractInsnNode aInsn: mn.instructions.toArray()){
            if (aInsn.getType() == AbstractInsnNode.METHOD_INSN) {
                MethodInsnNode mInsn = (MethodInsnNode) aInsn;
                s.add(mInsn.name + " " + mInsn.desc);
            }
        }
        return s.toArray(new String[0]);
    }
}
