package cn.afternode.afnasmutils;

import cn.afternode.commonutil.io.FileUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class LibraryLoader {
    public static void loadJar(byte[] jarBytes) throws IOException {
        JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(jarBytes));
        JarEntry jarEntry;
        while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
            if (jarEntry.getName().endsWith(".class")) {
                ClassReader classReader = new ClassReader(jarInputStream);
                ClassNode classNode = new ClassNode();
                classReader.accept(classNode, 0);
            }
        }
    }

    public static void loadJar(File jar) throws IOException {
        loadJar(FileUtil.readBytes(jar));
    }
}

