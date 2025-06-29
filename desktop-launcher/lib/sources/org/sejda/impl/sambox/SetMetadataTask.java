package org.sejda.impl.sambox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.SetMetadataParameters;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDDocumentCatalog;
import org.sejda.sambox.pdmodel.PDDocumentInformation;
import org.sejda.sambox.pdmodel.common.PDMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SetMetadataTask.class */
public class SetMetadataTask extends BaseTask<SetMetadataParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SetMetadataTask.class);
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SetMetadataParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SetMetadataTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SetMetadataParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        for (int sourceIndex = 0; sourceIndex < parameters.getSourceList().size(); sourceIndex++) {
            PdfSource<?> source = parameters.getSourceList().get(sourceIndex);
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            try {
                LOG.debug("Opening {}", source);
                executionContext().notifiableTaskMetadata().setCurrentSource(source);
                this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
                this.documentHandler.setUpdateProducerModifiedDate(parameters.isUpdateProducerModifiedDate());
                if (parameters.isUpdateProducerModifiedDate()) {
                    this.documentHandler.setCreatorOnPDDocument();
                }
                File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                PDDocument doc = this.documentHandler.getUnderlyingPDDocument();
                doc.setOnBeforeWriteAction(() -> {
                    LOG.debug("Setting metadata on temporary document.");
                    PDDocument doc1 = this.documentHandler.getUnderlyingPDDocument();
                    PDDocumentCatalog catalog = doc1.getDocumentCatalog();
                    if (parameters.isRemoveAllMetadata()) {
                        doc1.setDocumentInformation(new PDDocumentInformation());
                        catalog.setMetadata(null);
                        return;
                    }
                    PDDocumentInformation actualMeta = doc1.getDocumentInformation();
                    for (Map.Entry<String, String> meta : parameters.getMetadata().entrySet()) {
                        LOG.trace("'{}' -> '{}'", meta.getKey(), meta.getValue());
                        actualMeta.setCustomMetadataValue(meta.getKey(), meta.getValue());
                    }
                    for (String keyToRemove : parameters.getToRemove()) {
                        LOG.trace("Removing '{}'", keyToRemove);
                        actualMeta.removeMetadataField(keyToRemove);
                    }
                    if (catalog.getMetadata() != null) {
                        LOG.debug("Document has XMP metadata stream");
                        try {
                            updateXmpMetadata(catalog, actualMeta);
                        } catch (RuntimeException ex) {
                            if (exceptionStackContains("Namespace for prefix 'xmp' has not been declared", ex) || exceptionStackContains("Namespace for prefix 'rdf' has not been declared", ex)) {
                                fixMissingXmpOrRdfNamespace(catalog);
                                updateXmpMetadata(catalog, actualMeta);
                                return;
                            }
                            throw ex;
                        }
                    }
                });
                this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
                this.documentHandler.setCompress(parameters.isCompress());
                this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                });
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(totalSteps);
            } catch (Throwable th) {
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Metadata set and written to {}", parameters.getOutput());
    }

    private XPathFactory newXPathFactory() throws XPathFactoryConfigurationException {
        try {
            XPathFactory f = XPathFactory.newInstance();
            f.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            return f;
        } catch (XPathFactoryConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDateNode(String tagName, Document document, Calendar calendar) throws XPathExpressionException, DOMException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String value = "";
        if (calendar != null) {
            value = dateFormat.format(calendar.getTime());
        }
        updateTextNode(tagName, document, value);
    }

    private void updateTextNode(String tagName, Document document, String value) throws XPathExpressionException, DOMException {
        XPath xPath = newXPathFactory().newXPath();
        Node node = (Node) xPath.compile("//*[name()='" + tagName + "']").evaluate(document, XPathConstants.NODE);
        if (value == null) {
            value = "";
        }
        if (node != null) {
            String sanitized = value.replaceAll("��", "");
            node.setTextContent(sanitized);
        }
    }

    private boolean exceptionStackContains(String msg, Throwable ex) {
        if (ex != null && ex.getMessage() != null && ex.getMessage().contains(msg)) {
            return true;
        }
        if (ex != null && ex.getCause() != null) {
            return exceptionStackContains(msg, ex.getCause());
        }
        return false;
    }

    private void deleteAttr(String path, String attrName, Document document) throws XPathExpressionException, DOMException {
        XPath xPath = newXPathFactory().newXPath();
        Node node = (Node) xPath.compile(path).evaluate(document, XPathConstants.NODE);
        if (node != null && node.getAttributes().getNamedItem(attrName) != null) {
            node.getAttributes().removeNamedItem(attrName);
        }
    }

    private void fixMissingXmpOrRdfNamespace(PDDocumentCatalog catalog) throws IOException {
        InputStream is = catalog.getMetadata().createInputStream();
        try {
            String metadataAsString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            if (!metadataAsString.contains("xmlns:xmp=\"http://ns.adobe.com/xap/1.0/\"")) {
                LOG.warn("Metadata seems to be missing xmlns:xmp namespace definition, adding it");
                metadataAsString = metadataAsString.replaceAll("<rdf:Description", "<rdf:Description xmlns:xmp=\"http://ns.adobe.com/xap/1.0/\"");
            }
            if (!metadataAsString.contains("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"")) {
                LOG.warn("Metadata seems to be missing xmlns:rdf namespace definition, adding it");
                metadataAsString = metadataAsString.replaceAll("<rdf:RDF", "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
            }
            if (!metadataAsString.equals(metadataAsString)) {
                catalog.setMetadata(new PDMetadata(new ByteArrayInputStream(metadataAsString.getBytes(StandardCharsets.UTF_8))));
            }
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void updateXmpMetadata(PDDocumentCatalog catalog, PDDocumentInformation metadata) throws TransformerException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, IOException, IllegalArgumentException {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            f.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
            f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            f.setFeature("http://xml.org/sax/features/external-general-entities", false);
            f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            f.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            f.setXIncludeAware(false);
            f.setExpandEntityReferences(false);
            DocumentBuilder b = f.newDocumentBuilder();
            Document document = b.parse(catalog.getMetadata().createInputStream());
            deleteAttr("//*[@CreateDate]", "xmp:CreateDate", document);
            deleteAttr("//*[@ModifyDate]", "xmp:ModifyDate", document);
            deleteAttr("//*[@Producer]", "pdf:Producer", document);
            deleteAttr("//*[@CreatorTool]", "xmp:CreatorTool", document);
            deleteAttr("//*[@Keywords]", "pdf:Keywords", document);
            deleteAttr("//*[@MetadataDate]", "xmp:MetadataDate", document);
            updateDateNode("xmp:CreateDate", document, metadata.getCreationDate());
            updateDateNode("xmp:ModifyDate", document, metadata.getModificationDate());
            updateTextNode("pdf:Producer", document, metadata.getProducer());
            updateTextNode("xmp:CreatorTool", document, metadata.getCreator());
            updateTextNode("pdf:Keywords", document, metadata.getKeywords());
            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.setTime(new Date());
            updateDateNode("xmp:MetadataDate", document, nowCalendar);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
            transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
            transformerFactory.setURIResolver(new NoopURIResolver());
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(writer);
            transformer.transform(domSource, streamResult);
            String updatedXml = writer.getBuffer().toString();
            catalog.setMetadata(new PDMetadata(new ByteArrayInputStream(updatedXml.getBytes(StandardCharsets.UTF_8))));
        } catch (SAXParseException ex) {
            LOG.warn("Failed to parse XMP metadata, skipping update", ex);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning("Some metadata elements could not be updated");
        } catch (Exception ex2) {
            throw new RuntimeException("Failed to update XMP metadata", ex2);
        }
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SetMetadataTask$NoopURIResolver.class */
    private static class NoopURIResolver implements URIResolver {
        private NoopURIResolver() {
        }

        @Override // javax.xml.transform.URIResolver
        public Source resolve(String href, String base) throws TransformerException {
            return null;
        }
    }
}
