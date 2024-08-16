package io.github.racoondog.cronfigs.utils;

import java.util.function.Predicate;

public final class Predicates {
    private Predicates() {}

    public static <T> Predicate<T> and(Predicate<T> one, Predicate<T> other) {
        return one == null ? other : one.and(other);
    }

    public static Predicate<String> ofCharPredicate(CharPredicate predicate) {
        return s -> {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!predicate.test(c)) return false;
            }
            return true;
        };
    }
}
