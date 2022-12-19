import cn.afternode.afnasmutils.LibraryLoader;
import cn.afternode.afnasmutils.MethodProcessor;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;

public class TestHasReference {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(IOUtils.toByteArray(Files.newInputStream(new File("JVMUtil.class").toPath())));
        ClassNode cn = new ClassNode(Opcodes.ASM9);
        cr.accept(cn, 0);

        System.out.println("Methods: ");
        for (MethodNode mn: cn.methods) {
            System.out.println(mn.name + " " + mn.desc);
        }

        for (MethodNode mn: cn.methods) {
            System.out.println("Reference of: " + mn.name + " " + mn.desc);
            for (String refer: MethodProcessor.getReferences(cn, mn.name, mn.desc)) {
                System.out.println(refer);
            }
            System.out.println("\n");
        }

        Method m = Arrays.class.getDeclaredMethod("asList", Object[].class);
        for (MethodNode mn: cn.methods) {
            if (MethodProcessor.hasReference(mn, m)) {
                System.out.println("Arrays.asList reference at: " + mn.name + " " + mn.desc);
            }
        }
    }
}
