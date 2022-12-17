package cn.afternode.afnasmutils.data;

import java.lang.reflect.Modifier;

public class MethodData {
    public static enum AccessType{
        PUBLIC, PROTECTED, PRIVATE
    }

    private final String name;
    private final String descriptor;
    private final AccessType accessType;

    public MethodData(String name, String descriptor, int modifier) {
        this.name = name;
        this.descriptor = descriptor;

        if (Modifier.isPublic(modifier)) {
            accessType = AccessType.PUBLIC;
        } else if (Modifier.isProtected(modifier)) {
            accessType = AccessType.PROTECTED;
        } else if (Modifier.isPrivate(modifier)) {
            accessType = AccessType.PRIVATE;
        } else throw new IllegalArgumentException("Invalid modifier type");
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public AccessType getAccessType() {
        return accessType;
    }
}
