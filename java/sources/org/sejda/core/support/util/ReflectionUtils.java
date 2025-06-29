package org.sejda.core.support.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/util/ReflectionUtils.class */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static Class inferParameterClass(Class clazz, String methodName) throws SecurityException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName) && !method.isBridge()) {
                Type[] types = method.getGenericParameterTypes();
                for (Type type : types) {
                    if ((type instanceof Class) && !((Class) type).isInterface()) {
                        return (Class) type;
                    }
                }
            }
        }
        return null;
    }
}
