package org.sejda.core.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.Sejda;
import org.sejda.model.exception.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/XmlConfigurationStreamProvider.class */
class XmlConfigurationStreamProvider implements ConfigurationStreamProvider {
    private static final Logger LOG = LoggerFactory.getLogger(XmlConfigurationStreamProvider.class);
    private static final List<String> CONFIG_FILES = Arrays.asList("sejda.xml", "sejda.pro.xml", "sejda.default.xml");

    XmlConfigurationStreamProvider() {
    }

    @Override // org.sejda.core.context.ConfigurationStreamProvider
    public InputStream getConfigurationStream() throws ConfigurationException {
        return (InputStream) Optional.ofNullable(getConfiguration()).orElseThrow(() -> {
            return new ConfigurationException("Unable to find xml configuration file");
        });
    }

    private InputStream getConfiguration() throws ConfigurationException {
        String userConfigFileName = System.getProperty(Sejda.USER_CONFIG_FILE_PROPERTY_NAME);
        if (StringUtils.isNotBlank(userConfigFileName)) {
            return getCustomConfigurationStream(userConfigFileName);
        }
        return getDefaultConfigurationStream();
    }

    private InputStream getCustomConfigurationStream(String userConfigFileName) throws ConfigurationException {
        LOG.trace("Loading Sejda configuration form {}", userConfigFileName);
        InputStream configStream = ClassLoader.getSystemResourceAsStream(userConfigFileName);
        if (Objects.nonNull(configStream)) {
            return configStream;
        }
        try {
            LOG.trace("Searching Sejda configuration on filesystem");
            return new FileInputStream(userConfigFileName);
        } catch (FileNotFoundException e) {
            throw new ConfigurationException(String.format("Unable to access the provided configuration file [%s]", userConfigFileName), e);
        }
    }

    private InputStream getDefaultConfigurationStream() {
        for (String configFile : CONFIG_FILES) {
            LOG.trace("Loading Sejda configuration form {}", configFile);
            InputStream result = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            if (Objects.nonNull(result)) {
                return result;
            }
            LOG.trace("Couldn't find configuration file {}", configFile);
        }
        return null;
    }
}
