package org.sejda.core.service;

import jakarta.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.sejda.core.context.DefaultSejdaConfiguration;
import org.sejda.core.context.SejdaConfiguration;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.validation.DefaultValidationContext;
import org.sejda.model.exception.InvalidTaskParametersException;
import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.NotifiableTaskMetadata;
import org.sejda.model.task.TaskExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/service/DefaultTaskExecutionService.class */
public final class DefaultTaskExecutionService implements TaskExecutionService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTaskExecutionService.class);
    private final SejdaConfiguration configuration;

    public DefaultTaskExecutionService() {
        this(DefaultSejdaConfiguration.getInstance());
    }

    public DefaultTaskExecutionService(SejdaConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override // org.sejda.core.service.TaskExecutionService
    public void execute(TaskParameters parameters) {
        TaskExecutionContext executionContext = null;
        LOG.trace("Starting execution for {}", parameters);
        try {
            validateIfRequired(parameters);
            executionContext = new TaskExecutionContext(this.configuration.getTask(parameters), parameters.isLenient());
            LOG.info("Starting task ({}) execution.", executionContext.task());
            preExecution(executionContext);
            actualExecution(parameters, executionContext);
            postExecution(executionContext);
        } catch (RuntimeException e) {
            executionFailed(e, executionContext);
            throw e;
        } catch (InvalidTaskParametersException i) {
            LOG.error("Task execution failed due to invalid parameters: " + String.join(". ", i.getReasons()), i);
            executionFailed(i, executionContext);
        } catch (TaskException e2) {
            LOG.error(String.format("Task (%s) execution failed", Optional.ofNullable(executionContext).map(c -> {
                return c.task().toString();
            }).orElse("")), e2);
            executionFailed(e2, executionContext);
        }
    }

    private void executionFailed(Exception e, TaskExecutionContext executionContext) {
        if (executionContext == null) {
            ApplicationEventsNotifier.notifyEvent(NotifiableTaskMetadata.NULL).taskFailed(e);
        } else {
            ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).taskFailed(e);
        }
    }

    private void validateIfRequired(TaskParameters parameters) throws InvalidTaskParametersException {
        if (this.configuration.isValidation()) {
            LOG.debug("Validating parameters");
            validate(parameters);
        } else {
            LOG.info("Validation skipped");
        }
    }

    public void validate(TaskParameters parameters) throws InvalidTaskParametersException {
        Set<ConstraintViolation<TaskParameters>> violations = DefaultValidationContext.getContext().getValidator().validate(parameters, new Class[0]);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder(String.format("Input parameters (%s) are not valid: ", parameters));
            List<String> reasons = new ArrayList<>();
            for (ConstraintViolation<TaskParameters> violation : violations) {
                sb.append(String.format("\"(%s=%s) %s\" ", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()));
                reasons.add(violation.getMessage());
            }
            throw new InvalidTaskParametersException(sb.toString(), reasons);
        }
    }

    private void preExecution(TaskExecutionContext context) {
        context.taskStart();
        ApplicationEventsNotifier.notifyEvent(context.notifiableTaskMetadata()).taskStarted();
    }

    private void postExecution(TaskExecutionContext context) {
        context.taskEnded();
        ApplicationEventsNotifier.notifyEvent(context.notifiableTaskMetadata()).taskCompleted(context.executionTime());
    }

    private void actualExecution(TaskParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        try {
            executionContext.task().before(parameters, executionContext);
            executionContext.task().execute(parameters);
        } finally {
            try {
                executionContext.task().after();
            } catch (RuntimeException e) {
                LOG.warn("An unexpected error occurred during the execution of the 'after' phase.", e);
            }
        }
    }
}
