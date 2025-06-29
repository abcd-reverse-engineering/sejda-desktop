package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDWindowsLaunchParams.class */
public class PDWindowsLaunchParams implements COSObjectable {
    public static final String OPERATION_OPEN = "open";
    public static final String OPERATION_PRINT = "print";
    protected COSDictionary params;

    public PDWindowsLaunchParams() {
        this.params = new COSDictionary();
    }

    public PDWindowsLaunchParams(COSDictionary p) {
        this.params = p;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.params;
    }

    public String getFilename() {
        return this.params.getString(COSName.F);
    }

    public void setFilename(String file) {
        this.params.setString(COSName.F, file);
    }

    public String getDirectory() {
        return this.params.getString(COSName.D);
    }

    public void setDirectory(String dir) {
        this.params.setString(COSName.D, dir);
    }

    public String getOperation() {
        return this.params.getString(COSName.O, OPERATION_OPEN);
    }

    public void setOperation(String op) {
        this.params.setString(COSName.D, op);
    }

    public String getExecuteParam() {
        return this.params.getString(COSName.P);
    }

    public void setExecuteParam(String param) {
        this.params.setString(COSName.P, param);
    }
}
