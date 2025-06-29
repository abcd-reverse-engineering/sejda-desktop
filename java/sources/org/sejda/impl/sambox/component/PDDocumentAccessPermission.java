package org.sejda.impl.sambox.component;

import org.sejda.core.Sejda;
import org.sejda.model.exception.TaskPermissionsException;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.encryption.AccessPermission;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PDDocumentAccessPermission.class */
public class PDDocumentAccessPermission {
    private AccessPermission permissions;

    PDDocumentAccessPermission(PDDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("Unable to get permissions from null instance.");
        }
        this.permissions = document.getCurrentAccessPermission();
    }

    public void ensureOwnerPermissions() throws TaskPermissionsException {
        if (!Boolean.getBoolean(Sejda.UNETHICAL_READ_PROPERTY_NAME) && !this.permissions.isOwnerPermission()) {
            throw new TaskPermissionsException("Owner permission is required.");
        }
    }

    public void ensurePermission(PdfAccessPermission required) throws TaskPermissionsException {
        if (!Boolean.getBoolean(Sejda.UNETHICAL_READ_PROPERTY_NAME) && !ForwardingPdfAccessPermission.valueFromPdfAccessPermission(required).isAllowed(this.permissions)) {
            throw new TaskPermissionsException(String.format("Permission %s is not granted.", required));
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PDDocumentAccessPermission$ForwardingPdfAccessPermission.class */
    private enum ForwardingPdfAccessPermission {
        MODIFY(PdfAccessPermission.MODIFY) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.1
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canModify();
            }
        },
        ASSEMBLE(PdfAccessPermission.ASSEMBLE) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.2
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canAssembleDocument();
            }
        },
        COPY_AND_EXTRACT(PdfAccessPermission.COPY_AND_EXTRACT) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.3
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canExtractContent();
            }
        },
        DEGRADATED_PRINT(PdfAccessPermission.DEGRADATED_PRINT) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.4
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canPrintDegraded();
            }
        },
        EXTRACTION_FOR_DISABLES(PdfAccessPermission.EXTRACTION_FOR_DISABLES) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.5
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canExtractForAccessibility();
            }
        },
        FILL_FORMS(PdfAccessPermission.FILL_FORMS) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.6
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canFillInForm();
            }
        },
        PRINT(PdfAccessPermission.PRINT) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.7
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canPrint();
            }
        },
        ANNOTATION(PdfAccessPermission.ANNOTATION) { // from class: org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission.8
            @Override // org.sejda.impl.sambox.component.PDDocumentAccessPermission.ForwardingPdfAccessPermission
            boolean isAllowed(AccessPermission permissions) {
                return permissions.canModifyAnnotations();
            }
        };

        private final PdfAccessPermission permission;

        abstract boolean isAllowed(AccessPermission accessPermission);

        ForwardingPdfAccessPermission(PdfAccessPermission permission) {
            this.permission = permission;
        }

        static ForwardingPdfAccessPermission valueFromPdfAccessPermission(PdfAccessPermission accessPermission) {
            for (ForwardingPdfAccessPermission current : values()) {
                if (current.permission == accessPermission) {
                    return current;
                }
            }
            throw new IllegalArgumentException(String.format("No Forwarding access permission found for %s", accessPermission));
        }
    }
}
