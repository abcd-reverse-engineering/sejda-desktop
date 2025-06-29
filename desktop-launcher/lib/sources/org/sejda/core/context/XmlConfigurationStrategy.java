package org.sejda.core.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.Sejda;
import org.sejda.core.notification.strategy.AsyncNotificationStrategy;
import org.sejda.core.notification.strategy.NotificationStrategy;
import org.sejda.core.notification.strategy.SyncNotificationStrategy;
import org.sejda.model.exception.ConfigurationException;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/XmlConfigurationStrategy.class */
final class XmlConfigurationStrategy implements ConfigurationStrategy {
    private static final String ROOT_NODE = "/sejda";
    private static final String VALIDATION_ATTRIBUTENAME = "validation";
    private static final String IGNORE_XML_CONFIG_VALIDATION_ATTRIBUTENAME = "ignore_xml_config";
    private static final String NOTIFICATION_XPATH = "/notification";
    private static final String NOTIFICATION_ASYNC_ATTRIBUTENAME = "async";
    private static final String TASKS_XPATH = "/tasks/task";
    private static final String TASK_PARAM_ATTRIBUTENAME = "parameters";
    private static final String TASK_VALUE_ATTRIBUTENAME = "task";
    private static final String DEFAULT_SEJDA_CONFIG = "sejda.xsd";
    private Class<? extends NotificationStrategy> notificationStrategy;
    private Map<Class<? extends TaskParameters>, Class<? extends Task>> tasks;
    private XPathFactory xpathFactory = XPathFactory.newInstance();
    private boolean validation = false;
    private boolean ignoreXmlConfig = true;

    private XmlConfigurationStrategy(InputStream input) throws ParserConfigurationException, SAXException, ConfigurationException, IOException {
        initializeFromInputStream(input);
    }

    private void initializeFromInputStream(InputStream input) throws ParserConfigurationException, SAXException, ConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            initializeSchemaValidation(factory);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(input);
            this.notificationStrategy = getNotificationStrategy(document);
            this.tasks = getTasksMap(document);
            this.validation = getValidation(document);
            this.ignoreXmlConfig = getIgnoreXmlConfig(document);
        } catch (IOException | SAXException e) {
            throw new ConfigurationException(e);
        } catch (ParserConfigurationException | XPathExpressionException e2) {
            throw new ConfigurationException("Unable to create DocumentBuilder.", e2);
        }
    }

    private void initializeSchemaValidation(DocumentBuilderFactory factory) throws SAXException {
        if (Boolean.getBoolean(Sejda.PERFORM_SCHEMA_VALIDATION_PROPERTY_NAME)) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_SEJDA_CONFIG))}));
            factory.setNamespaceAware(true);
        }
    }

    @Override // org.sejda.core.context.ConfigurationStrategy
    public Class<? extends NotificationStrategy> getNotificationStrategy() {
        return this.notificationStrategy;
    }

    @Override // org.sejda.core.context.ConfigurationStrategy
    public Map<Class<? extends TaskParameters>, Class<? extends Task>> getTasksMap() {
        return this.tasks;
    }

    @Override // org.sejda.core.context.ConfigurationStrategy
    public boolean isValidation() {
        return this.validation;
    }

    @Override // org.sejda.core.context.ConfigurationStrategy
    public boolean isIgnoreXmlConfiguration() {
        return this.ignoreXmlConfig;
    }

    private Map<Class<? extends TaskParameters>, Class<? extends Task>> getTasksMap(Document document) throws XPathExpressionException, ConfigurationException, ClassNotFoundException {
        Map<Class<? extends TaskParameters>, Class<? extends Task>> retMap = new HashMap<>();
        NodeList nodes = (NodeList) this.xpathFactory.newXPath().evaluate("/sejda/tasks/task", document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Class<? extends TaskParameters> paramClass = getClassFromNode(node, TASK_PARAM_ATTRIBUTENAME, TaskParameters.class);
            Class<? extends Task> taksClass = getClassFromNode(node, TASK_VALUE_ATTRIBUTENAME, Task.class);
            retMap.put(paramClass, taksClass);
        }
        return retMap;
    }

    private <T> Class<? extends T> getClassFromNode(Node node, String str, Class<T> cls) throws ConfigurationException, ClassNotFoundException {
        String strNullSafeGetStringAttribute = nullSafeGetStringAttribute(node, str);
        if (StringUtils.isNotBlank(strNullSafeGetStringAttribute)) {
            try {
                Class<?> cls2 = Class.forName(strNullSafeGetStringAttribute.trim());
                if (cls.isAssignableFrom(cls2)) {
                    return (Class<? extends T>) cls2.asSubclass(cls);
                }
                throw new ConfigurationException(String.format("The configured %s is not a subtype of %s", cls2, cls));
            } catch (ClassNotFoundException e) {
                throw new ConfigurationException(String.format("Unable to find the configured %s", strNullSafeGetStringAttribute), e);
            }
        }
        throw new ConfigurationException(String.format("Missing %s configuration parameter.", str));
    }

    private Class<? extends NotificationStrategy> getNotificationStrategy(Document document) throws XPathExpressionException {
        Node node = (Node) this.xpathFactory.newXPath().evaluate("/sejda/notification", document, XPathConstants.NODE);
        if (nullSafeGetBooleanAttribute(node, NOTIFICATION_ASYNC_ATTRIBUTENAME)) {
            return AsyncNotificationStrategy.class;
        }
        return SyncNotificationStrategy.class;
    }

    private boolean getValidation(Document document) throws XPathExpressionException {
        Node node = (Node) this.xpathFactory.newXPath().evaluate(ROOT_NODE, document, XPathConstants.NODE);
        return nullSafeGetBooleanAttribute(node, VALIDATION_ATTRIBUTENAME);
    }

    private boolean getIgnoreXmlConfig(Document document) throws XPathExpressionException {
        Node node = (Node) this.xpathFactory.newXPath().evaluate(ROOT_NODE, document, XPathConstants.NODE);
        return nullSafeGetBooleanAttribute(node, IGNORE_XML_CONFIG_VALIDATION_ATTRIBUTENAME, true);
    }

    static XmlConfigurationStrategy newInstance(ConfigurationStreamProvider provider) throws ConfigurationException {
        InputStream stream = null;
        try {
            stream = provider.getConfigurationStream();
            XmlConfigurationStrategy xmlConfigurationStrategy = new XmlConfigurationStrategy(stream);
            IOUtils.closeQuietly(stream);
            return xmlConfigurationStrategy;
        } catch (Throwable th) {
            IOUtils.closeQuietly(stream);
            throw th;
        }
    }

    private static String nullSafeGetStringAttribute(Node node, String attributeName) {
        return (String) Optional.ofNullable(node).map((v0) -> {
            return v0.getAttributes();
        }).map(m -> {
            return m.getNamedItem(attributeName);
        }).map((v0) -> {
            return v0.getNodeValue();
        }).orElse(null);
    }

    private static boolean nullSafeGetBooleanAttribute(Node node, String attributeName) {
        return nullSafeGetBooleanAttribute(node, attributeName, false);
    }

    private static boolean nullSafeGetBooleanAttribute(Node node, String attributeName, boolean defaultValue) {
        return ((Boolean) Optional.ofNullable(nullSafeGetStringAttribute(node, attributeName)).filter((v0) -> {
            return StringUtils.isNotBlank(v0);
        }).map(Boolean::parseBoolean).orElse(Boolean.valueOf(defaultValue))).booleanValue();
    }
}
