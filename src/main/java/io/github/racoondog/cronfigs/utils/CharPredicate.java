package io.github.racoondog.cronfigs.utils;

import java.util.function.Predicate;

@FunctionalInterface
public interface CharPredicate extends Predicate<Character> {
    boolean test(char c);

    @Deprecated
    @Override
    default boolean test(Character character) {
        return this.test(character.charValue());
    }

    default CharPredicate and(CharPredicate other) {
        return t -> test(t) && other.test(t);
    }

    default CharPredicate negate() {
        return t -> !test(t);
    }

    default CharPredicate or(CharPredicate other) {
        return t -> test(t) || other.test(t);
    }
}
