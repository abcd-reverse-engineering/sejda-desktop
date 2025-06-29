package org.sejda.core.context;

import java.io.InputStream;
import org.sejda.model.exception.ConfigurationException;

@FunctionalInterface
/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/ConfigurationStreamProvider.class */
interface ConfigurationStreamProvider {
    InputStream getConfigurationStream() throws ConfigurationException;
}
