package cn.afternode.afnasmutils.jar;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Load a jar from file
 */
public final class ASMJar {
    private final HashMap<String, byte[]> classes;
    private final JarFile jf;

    public ASMJar(File f) throws IOException {
        classes = new HashMap<>();
        jf = new JarFile(f);

        Enumeration<JarEntry> entries = jf.entries();
        while(entries.hasMoreElements()) {
            JarEntry je = entries.nextElement();
            if (je.getName().endsWith(".class")) {
                try {
                    String name = je.getName().split("\\.class")[0].replace("/", ".");
                    byte[] b = IOUtils.toByteArray(jf.getInputStream(je));
                    classes.put(name, b);
                } catch (Exception ignored) {}
            }
        }
    }

    public JarFile getJar() {
        return jf;
    }

    public byte[] getClassBytes(String name) {
        return classes.get(name);
    }

    public ClassReader getClassReader(String name) {
        if (!classes.containsKey(name)) return null;
        return new ClassReader(classes.get(name));
    }
}
