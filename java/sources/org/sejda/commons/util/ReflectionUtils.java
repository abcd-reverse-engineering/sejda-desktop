package org.sejda.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/util/ReflectionUtils.class */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Field field;
        Class<?> superclass = clazz;
        loop0: while (true) {
            Class<?> searchType = superclass;
            if (Object.class != searchType && searchType != null) {
                Field[] fields = getDeclaredFields(searchType);
                int length = fields.length;
                for (int i = 0; i < length; i++) {
                    field = fields[i];
                    if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                        break loop0;
                    }
                }
                superclass = searchType.getSuperclass();
            } else {
                return null;
            }
        }
        return field;
    }

    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException) {
            throw ((RuntimeException) ex);
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            RuntimeException re = (RuntimeException) ex;
            throw re;
        }
        if (ex instanceof Error) {
            Error e = (Error) ex;
            throw e;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) {
            field.trySetAccessible();
        }
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
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
