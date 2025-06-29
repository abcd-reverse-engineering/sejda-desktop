package org.sejda.commons;

import java.util.Optional;
import java.util.function.Function;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/Either.class */
public abstract class Either<A, B> {
    public abstract <C> C either(Function<? super A, ? extends C> function, Function<? super B, ? extends C> function2);

    private Either() {
    }

    public static <A, B> Either<A, B> left(final A value) {
        return new Either<A, B>() { // from class: org.sejda.commons.Either.1
            @Override // org.sejda.commons.Either
            public <C> C either(Function<? super A, ? extends C> function, Function<? super B, ? extends C> function2) {
                return function.apply((Object) value);
            }
        };
    }

    public static <A, B> Either<A, B> right(final B value) {
        return new Either<A, B>() { // from class: org.sejda.commons.Either.2
            @Override // org.sejda.commons.Either
            public <C> C either(Function<? super A, ? extends C> function, Function<? super B, ? extends C> function2) {
                return function2.apply((Object) value);
            }
        };
    }

    public Optional<A> fromLeft() {
        return (Optional) either(Optional::of, value -> {
            return Optional.empty();
        });
    }

    public Optional<B> fromRight() {
        return (Optional) either(value -> {
            return Optional.empty();
        }, Optional::of);
    }
}
