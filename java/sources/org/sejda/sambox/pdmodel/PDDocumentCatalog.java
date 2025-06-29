package org.sejda.sambox.pdmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDDestinationOrAction;
import org.sejda.sambox.pdmodel.common.PDMetadata;
import org.sejda.sambox.pdmodel.common.PDPageLabels;
import org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.sejda.sambox.pdmodel.fixup.AcroFormDefaultFixup;
import org.sejda.sambox.pdmodel.fixup.AcroFormDefaultSamboxFixup;
import org.sejda.sambox.pdmodel.fixup.PDDocumentFixup;
import org.sejda.sambox.pdmodel.graphics.color.PDOutputIntent;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
import org.sejda.sambox.pdmodel.interactive.action.PDActionFactory;
import org.sejda.sambox.pdmodel.interactive.action.PDDocumentCatalogAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.action.PDURIDictionary;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDThread;
import org.sejda.sambox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.sejda.sambox.util.SpecVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocumentCatalog.class */
public class PDDocumentCatalog implements COSObjectable {
    private static final Logger LOG = LoggerFactory.getLogger(PDDocumentCatalog.class);
    private final COSDictionary root;
    private final PDDocument document;
    private PDDocumentFixup acroFormFixupApplied;
    private PDAcroForm cachedAcroForm;

    public PDDocumentCatalog(PDDocument doc) {
        this.document = doc;
        this.root = new COSDictionary();
        this.root.setItem(COSName.TYPE, (COSBase) COSName.CATALOG);
        this.document.getDocument().getTrailer().getCOSObject().setItem(COSName.ROOT, (COSBase) this.root);
    }

    public PDDocumentCatalog(PDDocument doc, COSDictionary rootDictionary) {
        this.document = doc;
        this.root = rootDictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.root;
    }

    public PDAcroForm getAcroForm() {
        return getAcroForm(new AcroFormDefaultSamboxFixup(this.document));
    }

    public PDAcroForm getAcroFromWithFixups() {
        return getAcroForm(new AcroFormDefaultFixup(this.document));
    }

    public PDAcroForm getAcroForm(PDDocumentFixup acroFormFixup) {
        if (acroFormFixup != null && acroFormFixup != this.acroFormFixupApplied) {
            acroFormFixup.apply();
            this.cachedAcroForm = null;
            this.acroFormFixupApplied = acroFormFixup;
        } else if (this.acroFormFixupApplied != null) {
            LOG.debug("AcroForm content has already been retrieved with fixes applied - original content changed because of that");
        }
        if (this.cachedAcroForm == null) {
            COSDictionary dict = (COSDictionary) this.root.getDictionaryObject(COSName.ACRO_FORM, COSDictionary.class);
            this.cachedAcroForm = dict == null ? null : new PDAcroForm(this.document, dict);
        }
        return this.cachedAcroForm;
    }

    public void setAcroForm(PDAcroForm acroForm) {
        this.root.setItem(COSName.ACRO_FORM, acroForm);
        this.cachedAcroForm = null;
    }

    public PDPageTree getPages() {
        return new PDPageTree((COSDictionary) this.root.getDictionaryObject(COSName.PAGES, COSDictionary.class), this.document);
    }

    public PDViewerPreferences getViewerPreferences() {
        return (PDViewerPreferences) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.VIEWER_PREFERENCES, COSDictionary.class)).map(PDViewerPreferences::new).orElse(null);
    }

    public void setViewerPreferences(PDViewerPreferences prefs) {
        this.root.setItem(COSName.VIEWER_PREFERENCES, prefs);
    }

    public PDDocumentOutline getDocumentOutline() {
        return (PDDocumentOutline) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.OUTLINES, COSDictionary.class)).map(PDDocumentOutline::new).orElse(null);
    }

    public void setDocumentOutline(PDDocumentOutline outlines) {
        this.root.setItem(COSName.OUTLINES, outlines);
    }

    public List<PDThread> getThreads() {
        COSArray array = (COSArray) this.root.getDictionaryObject(COSName.THREADS);
        if (array == null) {
            array = new COSArray();
            this.root.setItem(COSName.THREADS, (COSBase) array);
        }
        List<PDThread> pdObjects = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            pdObjects.add(new PDThread((COSDictionary) array.getObject(i)));
        }
        return new COSArrayList(pdObjects, array);
    }

    public void setThreads(List<PDThread> threads) {
        this.root.setItem(COSName.THREADS, (COSBase) COSArrayList.converterToCOSArray(threads));
    }

    public PDMetadata getMetadata() {
        return (PDMetadata) Optional.ofNullable((COSStream) this.root.getDictionaryObject(COSName.METADATA, COSStream.class)).map(PDMetadata::new).orElse(null);
    }

    public void setMetadata(PDMetadata meta) {
        this.root.setItem(COSName.METADATA, meta);
    }

    public void setOpenAction(PDDestinationOrAction action) {
        this.root.setItem(COSName.OPEN_ACTION, action);
    }

    public PDDestinationOrAction getOpenAction() throws IOException {
        COSBase openAction = this.root.getDictionaryObject(COSName.OPEN_ACTION);
        if (Objects.nonNull(openAction)) {
            if (openAction instanceof COSDictionary) {
                return PDActionFactory.createAction((COSDictionary) openAction);
            }
            if (openAction instanceof COSArray) {
                return PDDestination.create(openAction);
            }
            LOG.warn("Invalid OpenAction {}", openAction);
            return null;
        }
        return null;
    }

    public PDDocumentCatalogAdditionalActions getActions() {
        COSDictionary addAction = (COSDictionary) this.root.getDictionaryObject(COSName.AA, COSDictionary.class);
        if (addAction == null) {
            addAction = new COSDictionary();
            this.root.setItem(COSName.AA, (COSBase) addAction);
        }
        return new PDDocumentCatalogAdditionalActions(addAction);
    }

    public void setActions(PDDocumentCatalogAdditionalActions actions) {
        this.root.setItem(COSName.AA, actions);
    }

    public PDDocumentNameDictionary getNames() {
        return (PDDocumentNameDictionary) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.NAMES, COSDictionary.class)).map(PDDocumentNameDictionary::new).orElse(null);
    }

    public PDDocumentNameDestinationDictionary getDests() {
        return (PDDocumentNameDestinationDictionary) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.DESTS, COSDictionary.class)).map(PDDocumentNameDestinationDictionary::new).orElse(null);
    }

    public void setNames(PDDocumentNameDictionary names) {
        this.root.setItem(COSName.NAMES, names);
    }

    public PDMarkInfo getMarkInfo() {
        return (PDMarkInfo) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.MARK_INFO, COSDictionary.class)).map(PDMarkInfo::new).orElse(null);
    }

    public void setMarkInfo(PDMarkInfo markInfo) {
        this.root.setItem(COSName.MARK_INFO, markInfo);
    }

    public List<PDOutputIntent> getOutputIntents() {
        List<PDOutputIntent> retval = new ArrayList<>();
        COSArray array = (COSArray) this.root.getDictionaryObject(COSName.OUTPUT_INTENTS, COSArray.class);
        if (array != null) {
            Iterator<COSBase> it = array.iterator();
            while (it.hasNext()) {
                COSBase cosBase = it.next();
                PDOutputIntent oi = new PDOutputIntent((COSDictionary) cosBase.getCOSObject());
                retval.add(oi);
            }
        }
        return retval;
    }

    public void addOutputIntent(PDOutputIntent outputIntent) {
        COSArray array = (COSArray) this.root.getDictionaryObject(COSName.OUTPUT_INTENTS, COSArray.class);
        if (array == null) {
            array = new COSArray();
            this.root.setItem(COSName.OUTPUT_INTENTS, (COSBase) array);
        }
        array.add(outputIntent.getCOSObject());
    }

    public void setOutputIntents(List<PDOutputIntent> outputIntents) {
        COSArray array = new COSArray();
        for (PDOutputIntent intent : outputIntents) {
            array.add(intent.getCOSObject());
        }
        this.root.setItem(COSName.OUTPUT_INTENTS, (COSBase) array);
    }

    public PageMode getPageMode() {
        String mode = this.root.getNameAsString(COSName.PAGE_MODE);
        if (mode != null) {
            try {
                return PageMode.fromString(mode);
            } catch (IllegalArgumentException e) {
                LOG.debug(String.format("Unrecognized page mode %s", mode));
            }
        }
        return PageMode.USE_NONE;
    }

    public void setPageMode(PageMode mode) {
        this.root.setName(COSName.PAGE_MODE, mode.stringValue());
    }

    public PageLayout getPageLayout() {
        String mode = this.root.getNameAsString(COSName.PAGE_LAYOUT);
        if (mode != null && !mode.isEmpty()) {
            try {
                return PageLayout.fromString(mode);
            } catch (IllegalArgumentException e) {
                LOG.debug(String.format("Unrecognized page layout %s - returning PageLayout.SINGLE_PAGE", mode));
            }
        }
        return PageLayout.SINGLE_PAGE;
    }

    public void setPageLayout(PageLayout layout) {
        this.root.setName(COSName.PAGE_LAYOUT, layout.stringValue());
    }

    public PDURIDictionary getURI() {
        return (PDURIDictionary) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.URI, COSDictionary.class)).map(PDURIDictionary::new).orElse(null);
    }

    public void setURI(PDURIDictionary uri) {
        this.root.setItem(COSName.URI, uri);
    }

    public PDStructureTreeRoot getStructureTreeRoot() {
        return (PDStructureTreeRoot) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.STRUCT_TREE_ROOT, COSDictionary.class)).map(PDStructureTreeRoot::new).orElse(null);
    }

    public void setStructureTreeRoot(PDStructureTreeRoot treeRoot) {
        this.root.setItem(COSName.STRUCT_TREE_ROOT, treeRoot);
    }

    public String getLanguage() {
        return this.root.getString(COSName.LANG);
    }

    public void setLanguage(String language) {
        this.root.setString(COSName.LANG, language);
    }

    public String getVersion() {
        return this.root.getNameAsString(COSName.VERSION);
    }

    public void setVersion(String version) {
        this.root.setName(COSName.VERSION, version);
    }

    public PDPageLabels getPageLabels() throws IOException {
        COSDictionary labels = (COSDictionary) this.root.getDictionaryObject(COSName.PAGE_LABELS, COSDictionary.class);
        if (Objects.nonNull(labels)) {
            return new PDPageLabels(labels);
        }
        return null;
    }

    public void setPageLabels(PDPageLabels labels) {
        this.root.setItem(COSName.PAGE_LABELS, labels);
    }

    public PDOptionalContentProperties getOCProperties() {
        return (PDOptionalContentProperties) Optional.ofNullable((COSDictionary) this.root.getDictionaryObject(COSName.OCPROPERTIES, COSDictionary.class)).map(PDOptionalContentProperties::new).orElse(null);
    }

    public void setOCProperties(PDOptionalContentProperties ocProperties) throws IllegalStateException {
        this.root.setItem(COSName.OCPROPERTIES, ocProperties);
        if (ocProperties != null) {
            this.document.requireMinVersion(SpecVersionUtils.V1_5);
        }
    }

    public PDPageDestination findNamedDestinationPage(PDNamedDestination namedDest) throws IOException {
        PDDocumentNameDestinationDictionary nameDestDict;
        PDPageDestination namesDest = (PDPageDestination) Optional.ofNullable(getNames()).map((v0) -> {
            return v0.getDests();
        }).map(tree -> {
            return tree.getValue(namedDest.getNamedDestination());
        }).orElse(null);
        if (namesDest == null && (nameDestDict = getDests()) != null) {
            return (PDPageDestination) nameDestDict.getDestination(namedDest.getNamedDestination());
        }
        return namesDest;
    }
}
