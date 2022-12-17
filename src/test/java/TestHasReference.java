import cn.afternode.afnasmutils.LibraryLoader;
import cn.afternode.afnasmutils.MethodProcessor;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.nio.file.Files;

public class TestHasReference {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(IOUtils.toByteArray(Files.newInputStream(new File("JVMUtil.class").toPath())));
        ClassNode cn = new ClassNode(Opcodes.ASM9);
        cr.accept(cn, 0);

        System.out.println("Methods: ");
        for (MethodNode mn: cn.methods) {
            System.out.println(mn.name + " " + mn.desc);
        }

        System.out.println("References of forceExit: ");
        for (String s: MethodProcessor.getReferences(cn, "forceExit", "(I)V")) {
            System.out.println(s);
        }
    }
}
