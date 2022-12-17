package cn.afternode.afnasmutils.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Modify a loaded class
 * Use Instrumentation.addTransformer() to register the transformer, you should use the JavaAgent
 */
public abstract class BytecodeTransformer implements ClassFileTransformer {
    private final Class<?> targetClass;
    private final boolean useComputeMaxs;

    /**
     *
     * @param targetClass Target injection class
     * @param useComputeMaxs Use COMPUTE_MAXS for the ClassWriter, may cause illegal bytecodes
     */
    public BytecodeTransformer(Class<?> targetClass, boolean useComputeMaxs) {
        this.targetClass = targetClass;
        this.useComputeMaxs = useComputeMaxs;
    }

    public BytecodeTransformer(Class<?> targetClass) {
        this.targetClass = targetClass;
        useComputeMaxs = false;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (classBeingRedefined.getName().equals(targetClass.getName())) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw;
            if (useComputeMaxs) {
                cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            } else {
                cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            }
            cr.accept(getClassVisitor(cw), 0);
            return cw.toByteArray();
        } else return classfileBuffer;
    }

    /**
     * Get the class writer
     * e.g. return new MyClassWriter(Opcodes.ASM9, cw);
     */
    protected abstract ClassVisitor getClassVisitor(ClassWriter cw);
}
