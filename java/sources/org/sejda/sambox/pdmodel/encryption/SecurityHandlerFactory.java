package org.sejda.sambox.pdmodel.encryption;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/SecurityHandlerFactory.class */
public final class SecurityHandlerFactory {
    public static final SecurityHandlerFactory INSTANCE = new SecurityHandlerFactory();
    private final Map<String, Class<? extends SecurityHandler>> nameToHandler = new HashMap();
    private final Map<Class<? extends ProtectionPolicy>, Class<? extends SecurityHandler>> policyToHandler = new HashMap();

    private SecurityHandlerFactory() {
        registerHandler(StandardSecurityHandler.FILTER, StandardSecurityHandler.class, StandardProtectionPolicy.class);
        registerHandler(PublicKeySecurityHandler.FILTER, PublicKeySecurityHandler.class, PublicKeyProtectionPolicy.class);
    }

    public void registerHandler(String name, Class<? extends SecurityHandler> securityHandler, Class<? extends ProtectionPolicy> protectionPolicy) {
        if (this.nameToHandler.containsKey(name)) {
            throw new IllegalStateException("The security handler name is already registered");
        }
        this.nameToHandler.put(name, securityHandler);
        this.policyToHandler.put(protectionPolicy, securityHandler);
    }

    public SecurityHandler newSecurityHandlerForPolicy(ProtectionPolicy policy) {
        Class<? extends SecurityHandler> handlerClass = this.policyToHandler.get(policy.getClass());
        if (handlerClass == null) {
            return null;
        }
        Class<?>[] argsClasses = {policy.getClass()};
        Object[] args = {policy};
        return newSecurityHandler(handlerClass, argsClasses, args);
    }

    public SecurityHandler newSecurityHandlerForFilter(String name) {
        Class<? extends SecurityHandler> handlerClass = this.nameToHandler.get(name);
        if (handlerClass == null) {
            return null;
        }
        Class<?>[] argsClasses = new Class[0];
        Object[] args = new Object[0];
        return newSecurityHandler(handlerClass, argsClasses, args);
    }

    private SecurityHandler newSecurityHandler(Class<? extends SecurityHandler> handlerClass, Class<?>[] argsClasses, Object[] args) throws NoSuchMethodException, SecurityException {
        try {
            Constructor<? extends SecurityHandler> ctor = handlerClass.getDeclaredConstructor(argsClasses);
            return ctor.newInstance(args);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
