package org.sejda.core.context;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.core.Sejda;
import org.sejda.core.notification.strategy.NotificationStrategy;
import org.sejda.model.exception.ConfigurationException;
import org.sejda.model.exception.SejdaRuntimeException;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskNotFoundException;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/DefaultSejdaConfiguration.class */
public final class DefaultSejdaConfiguration implements SejdaConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSejdaConfiguration.class);
    private Class<? extends NotificationStrategy> notificationStrategy;
    private final TasksRegistry tasksRegistry = new DefaultTasksRegistry();
    private boolean validation;
    private boolean ignoreXmlConfiguration;

    DefaultSejdaConfiguration() {
        LOG.info("Configuring Sejda {}", Sejda.VERSION);
        initialize();
        if (LOG.isTraceEnabled()) {
            LOG.trace("Configured tasks:");
            this.tasksRegistry.getTasks().forEach((p, t) -> {
                LOG.trace(String.format("%s executed by -> %s", p, t));
            });
        }
    }

    private void initialize() {
        try {
            ConfigurationStrategy configStrategy = XmlConfigurationStrategy.newInstance(new XmlConfigurationStreamProvider());
            this.notificationStrategy = configStrategy.getNotificationStrategy();
            LOG.trace("Notification strategy: {}", this.notificationStrategy);
            this.validation = configStrategy.isValidation();
            LOG.trace("Validation: {}", Boolean.valueOf(this.validation));
            this.ignoreXmlConfiguration = configStrategy.isIgnoreXmlConfiguration();
            LOG.trace("Validation, ignore xml configuration: {}", Boolean.valueOf(this.ignoreXmlConfiguration));
            Map<Class<? extends TaskParameters>, Class<? extends Task>> tasksMap = configStrategy.getTasksMap();
            TasksRegistry tasksRegistry = this.tasksRegistry;
            Objects.requireNonNull(tasksRegistry);
            tasksMap.forEach(tasksRegistry::addTask);
        } catch (ConfigurationException e) {
            throw new SejdaRuntimeException("Unable to complete Sejda configuration ", e);
        }
    }

    public static SejdaConfiguration getInstance() {
        return DefaultSejdaConfigurationHolder.CONFIGURATION;
    }

    @Override // org.sejda.core.context.SejdaConfiguration
    public Class<? extends NotificationStrategy> getNotificationStrategy() {
        return this.notificationStrategy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.sejda.core.context.SejdaConfiguration
    public Task<? extends TaskParameters> getTask(TaskParameters parameters) throws TaskException {
        Class<?> cls = parameters.getClass();
        Class<? extends Task> taskClass = (Class) Optional.ofNullable(this.tasksRegistry.getTask(cls)).orElseThrow(() -> {
            return new TaskNotFoundException(String.format("Unable to find a Task class able to execute %s", cls));
        });
        try {
            return (Task) taskClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | InvocationTargetException e) {
            throw new TaskException("Error instantiating the task", e);
        } catch (NoSuchMethodException e2) {
            throw new TaskException(String.format("The task %s doesn't define a public no-args contructor.", taskClass), e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.sejda.core.context.SejdaConfiguration
    public Class<? extends Task> getTaskClass(TaskParameters parameters) throws TaskException {
        Class<?> cls = parameters.getClass();
        return (Class) Optional.ofNullable(this.tasksRegistry.getTask(cls)).orElseThrow(() -> {
            return new TaskNotFoundException(String.format("Unable to find a Task class able to execute %s", cls));
        });
    }

    @Override // org.sejda.core.context.SejdaConfiguration
    public boolean isValidation() {
        return this.validation;
    }

    @Override // org.sejda.core.context.SejdaConfiguration
    public boolean isValidationIgnoringXmlConfiguration() {
        return this.ignoreXmlConfiguration;
    }

    TasksRegistry getTasksRegistry() {
        return this.tasksRegistry;
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/DefaultSejdaConfiguration$DefaultSejdaConfigurationHolder.class */
    private static final class DefaultSejdaConfigurationHolder {
        static final SejdaConfiguration CONFIGURATION = new DefaultSejdaConfiguration();

        private DefaultSejdaConfigurationHolder() {
        }
    }
}
