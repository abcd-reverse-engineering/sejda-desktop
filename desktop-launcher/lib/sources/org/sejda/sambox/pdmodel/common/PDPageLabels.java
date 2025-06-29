package org.sejda.sambox.pdmodel.common;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDPageLabels.class */
public class PDPageLabels implements COSObjectable {
    private Map<Integer, PDPageLabelRange> labels = new TreeMap();

    public PDPageLabels() {
        PDPageLabelRange defaultRange = new PDPageLabelRange();
        defaultRange.setStyle("D");
        this.labels.put(0, defaultRange);
    }

    public PDPageLabels(COSDictionary dict) throws IOException {
        if (Objects.nonNull(dict)) {
            findLabels(new PDNumberTreeNode(dict, PDPageLabelRange.class));
        }
    }

    private void findLabels(PDNumberTreeNode node) throws IOException {
        List<PDNumberTreeNode> kids = node.getKids();
        if (Objects.nonNull(kids)) {
            for (PDNumberTreeNode kid : kids) {
                findLabels(kid);
            }
            return;
        }
        Map<Integer, COSObjectable> numbers = node.getNumbers();
        if (numbers != null) {
            for (Map.Entry<Integer, COSObjectable> i : numbers.entrySet()) {
                if (i.getKey().intValue() >= 0) {
                    this.labels.put(i.getKey(), (PDPageLabelRange) i.getValue());
                }
            }
        }
    }

    public int getPageRangeCount() {
        return this.labels.size();
    }

    public PDPageLabelRange getPageLabelRange(int startPage) {
        return this.labels.get(Integer.valueOf(startPage));
    }

    public void setLabelItem(int startPage, PDPageLabelRange item) {
        RequireUtils.requireArg(startPage >= 0, "Cannot set a label starting from a negative page number");
        this.labels.put(Integer.valueOf(startPage), item);
    }

    public Map<Integer, PDPageLabelRange> getLabels() {
        return Collections.unmodifiableMap(this.labels);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        COSDictionary dict = new COSDictionary();
        COSArray arr = new COSArray();
        for (Map.Entry<Integer, PDPageLabelRange> i : this.labels.entrySet()) {
            arr.add((COSBase) COSInteger.get(i.getKey().intValue()));
            arr.add((COSObjectable) i.getValue());
        }
        dict.setItem(COSName.NUMS, (COSBase) arr);
        return dict;
    }
}
