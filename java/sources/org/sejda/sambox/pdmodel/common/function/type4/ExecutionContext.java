package org.sejda.sambox.pdmodel.common.function.type4;

import java.util.Stack;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/type4/ExecutionContext.class */
public class ExecutionContext {
    private final Operators operators;
    private final Stack<Object> stack = new Stack<>();

    public ExecutionContext(Operators operatorSet) {
        this.operators = operatorSet;
    }

    public Stack<Object> getStack() {
        return this.stack;
    }

    public Operators getOperators() {
        return this.operators;
    }

    public Number popNumber() {
        return (Number) this.stack.pop();
    }

    public int popInt() {
        return ((Integer) this.stack.pop()).intValue();
    }

    public float popReal() {
        return ((Number) this.stack.pop()).floatValue();
    }
}
