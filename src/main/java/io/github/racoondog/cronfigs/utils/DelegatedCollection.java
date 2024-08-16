package io.github.racoondog.cronfigs.utils;

import java.util.Collection;
import java.util.Iterator;

public interface DelegatedCollection<T> extends Collection<T> {
    Collection<T> delegate();

    @Override
    default int size() {
        return this.delegate().size();
    }

    @Override
    default boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return this.delegate().contains(o);
    }

    @Override
    default Iterator<T> iterator() {
        return this.delegate().iterator();
    }

    @Override
    default Object[] toArray() {
        return this.delegate().toArray();
    }

    @Override
    default <T1> T1[] toArray(T1[] a) {
        return this.delegate().toArray(a);
    }

    @Override
    default boolean add(T t) {
        return this.delegate().add(t);
    }

    @Override
    default boolean remove(Object o) {
        return this.delegate().remove(o);
    }

    @Override
    default boolean containsAll(java.util.Collection<?> c) {
        return this.delegate().containsAll(c);
    }

    @Override
    default boolean addAll(java.util.Collection<? extends T> c) {
        return this.delegate().addAll(c);
    }

    @Override
    default boolean removeAll(java.util.Collection<?> c) {
        return this.delegate().removeAll(c);
    }

    @Override
    default boolean retainAll(java.util.Collection<?> c) {
        return this.delegate().retainAll(c);
    }

    @Override
    default void clear() {
        this.delegate().clear();
    }
}
