package org.sejda.sambox.pdmodel.common.function.type4;

import java.util.Stack;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators.class */
class RelationalOperators {
    private RelationalOperators() {
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Eq.class */
    static class Eq implements Operator {
        Eq() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.Operator
        public void execute(ExecutionContext context) {
            Stack<Object> stack = context.getStack();
            Object op2 = stack.pop();
            Object op1 = stack.pop();
            boolean result = isEqual(op1, op2);
            stack.push(Boolean.valueOf(result));
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected boolean isEqual(java.lang.Object r4, java.lang.Object r5) {
            /*
                r3 = this;
                r0 = 0
                r6 = r0
                r0 = r4
                boolean r0 = r0 instanceof java.lang.Number
                if (r0 == 0) goto L33
                r0 = r4
                java.lang.Number r0 = (java.lang.Number) r0
                r7 = r0
                r0 = r5
                boolean r0 = r0 instanceof java.lang.Number
                if (r0 == 0) goto L33
                r0 = r5
                java.lang.Number r0 = (java.lang.Number) r0
                r8 = r0
                r0 = r7
                float r0 = r0.floatValue()
                r1 = r8
                float r1 = r1.floatValue()
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 != 0) goto L2e
                r0 = 1
                goto L2f
            L2e:
                r0 = 0
            L2f:
                r6 = r0
                goto L39
            L33:
                r0 = r4
                r1 = r5
                boolean r0 = r0.equals(r1)
                r6 = r0
            L39:
                r0 = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.Eq.isEqual(java.lang.Object, java.lang.Object):boolean");
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$AbstractNumberComparisonOperator.class */
    private static abstract class AbstractNumberComparisonOperator implements Operator {
        protected abstract boolean compare(Number number, Number number2);

        private AbstractNumberComparisonOperator() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.Operator
        public void execute(ExecutionContext context) {
            Stack<Object> stack = context.getStack();
            Object op2 = stack.pop();
            Object op1 = stack.pop();
            Number num1 = (Number) op1;
            Number num2 = (Number) op2;
            boolean result = compare(num1, num2);
            stack.push(Boolean.valueOf(result));
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Ge.class */
    static class Ge extends AbstractNumberComparisonOperator {
        Ge() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.AbstractNumberComparisonOperator
        protected boolean compare(Number num1, Number num2) {
            return num1.floatValue() >= num2.floatValue();
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Gt.class */
    static class Gt extends AbstractNumberComparisonOperator {
        Gt() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.AbstractNumberComparisonOperator
        protected boolean compare(Number num1, Number num2) {
            return num1.floatValue() > num2.floatValue();
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Le.class */
    static class Le extends AbstractNumberComparisonOperator {
        Le() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.AbstractNumberComparisonOperator
        protected boolean compare(Number num1, Number num2) {
            return num1.floatValue() <= num2.floatValue();
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Lt.class */
    static class Lt extends AbstractNumberComparisonOperator {
        Lt() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.AbstractNumberComparisonOperator
        protected boolean compare(Number num1, Number num2) {
            return num1.floatValue() < num2.floatValue();
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/RelationalOperators$Ne.class */
    static class Ne extends Eq {
        Ne() {
        }

        @Override // org.sejda.sambox.pdmodel.common.function.type4.RelationalOperators.Eq
        protected boolean isEqual(Object op1, Object op2) {
            boolean result = super.isEqual(op1, op2);
            return !result;
        }
    }
}
