package org.sejda.sambox.pdmodel.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDStream.class */
public class PDStream implements COSObjectable {
    private COSStream stream;

    public PDStream() {
        this.stream = new COSStream();
    }

    public PDStream(COSStream str) {
        this.stream = str;
    }

    public PDStream(InputStream input) throws IOException {
        this(input, (COSBase) null);
    }

    public PDStream(InputStream input, COSName filter) throws IOException {
        this(input, (COSBase) filter);
    }

    public PDStream(InputStream input, COSArray filters) throws IOException {
        this(input, (COSBase) filters);
    }

    public PDStream(InputStream input, COSBase filter) throws IOException {
        this.stream = new COSStream();
        try {
            OutputStream output = this.stream.createFilteredStream(filter);
            try {
                IOUtils.copy(input, output);
                if (output != null) {
                    output.close();
                }
            } finally {
            }
        } finally {
            IOUtils.close(input);
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSStream getCOSObject() {
        return this.stream;
    }

    public OutputStream createOutputStream() {
        return this.stream.createUnfilteredStream();
    }

    public OutputStream createOutputStream(COSName filter) {
        return this.stream.createFilteredStream(filter);
    }

    public InputStream createInputStream() throws IOException {
        return this.stream.getUnfilteredStream();
    }

    public int getLength() {
        return this.stream.getInt(COSName.LENGTH, 0);
    }

    public List<COSName> getFilters() {
        COSBase filters = this.stream.getFilters();
        if (filters instanceof COSName) {
            COSName name = (COSName) filters;
            return new COSArrayList(name, name, this.stream, COSName.FILTER);
        }
        if (filters instanceof COSArray) {
            COSArray filtersArray = (COSArray) filters;
            return filtersArray.toList();
        }
        return null;
    }

    public void setFilters(List<COSName> filters) {
        COSBase obj = COSArrayList.converterToCOSArray(filters);
        this.stream.setItem(COSName.FILTER, obj);
    }

    public List<Object> getDecodeParms() throws IOException {
        List<Object> retval = null;
        COSBase dp = this.stream.getDictionaryObject(COSName.DECODE_PARMS);
        if (dp == null) {
            dp = this.stream.getDictionaryObject(COSName.DP);
        }
        if (dp instanceof COSDictionary) {
            Map<?, ?> map = COSDictionaryMap.convertBasicTypesToMap((COSDictionary) dp);
            retval = new COSArrayList<>(map, dp, this.stream, COSName.DECODE_PARMS);
        } else if (dp instanceof COSArray) {
            COSArray array = (COSArray) dp;
            List<Object> actuals = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                actuals.add(COSDictionaryMap.convertBasicTypesToMap((COSDictionary) array.getObject(i)));
            }
            retval = new COSArrayList<>(actuals, array);
        }
        return retval;
    }

    public void setDecodeParms(List<?> decodeParams) {
        this.stream.setItem(COSName.DECODE_PARMS, (COSBase) COSArrayList.converterToCOSArray(decodeParams));
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(this.stream.getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification f) {
        this.stream.setItem(COSName.F, f);
    }

    public List<String> getFileFilters() {
        COSBase filters = this.stream.getDictionaryObject(COSName.F_FILTER);
        if (filters instanceof COSName) {
            COSName name = (COSName) filters;
            return new COSArrayList(name.getName(), name, this.stream, COSName.F_FILTER);
        }
        if (filters instanceof COSArray) {
            return COSArrayList.convertCOSNameCOSArrayToList((COSArray) filters);
        }
        return null;
    }

    public void setFileFilters(List<String> filters) {
        COSBase obj = COSArrayList.convertStringListToCOSNameCOSArray(filters);
        this.stream.setItem(COSName.F_FILTER, obj);
    }

    public List<Object> getFileDecodeParams() throws IOException {
        COSBase dp = this.stream.getDictionaryObject(COSName.F_DECODE_PARMS);
        if (dp instanceof COSDictionary) {
            Map<?, ?> map = COSDictionaryMap.convertBasicTypesToMap((COSDictionary) dp);
            return new COSArrayList(map, dp, this.stream, COSName.F_DECODE_PARMS);
        }
        if (dp instanceof COSArray) {
            COSArray array = (COSArray) dp;
            List<Object> actuals = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                actuals.add(COSDictionaryMap.convertBasicTypesToMap((COSDictionary) array.getObject(i)));
            }
            return new COSArrayList(actuals, array);
        }
        return null;
    }

    public void setFileDecodeParams(List<?> decodeParams) {
        this.stream.setItem(COSName.F_DECODE_PARMS, (COSBase) COSArrayList.converterToCOSArray(decodeParams));
    }

    public byte[] toByteArray() throws IOException {
        InputStream is = createInputStream();
        try {
            byte[] byteArray = IOUtils.toByteArray(is);
            if (is != null) {
                is.close();
            }
            return byteArray;
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

    public PDMetadata getMetadata() {
        COSBase mdStream = this.stream.getDictionaryObject(COSName.METADATA);
        if (mdStream != null) {
            if (mdStream instanceof COSStream) {
                return new PDMetadata((COSStream) mdStream);
            }
            if (mdStream instanceof COSNull) {
                return null;
            }
            throw new IllegalStateException("Expected a COSStream but was a " + mdStream.getClass().getSimpleName());
        }
        return null;
    }

    public void setMetadata(PDMetadata meta) {
        this.stream.setItem(COSName.METADATA, meta);
    }

    public int getDecodedStreamLength() {
        return this.stream.getInt(COSName.DL);
    }

    public void setDecodedStreamLength(int decodedStreamLength) {
        this.stream.setInt(COSName.DL, decodedStreamLength);
    }
}
