package io.github.racoondog.cronfigs.serialization;

import java.util.function.Consumer;

public record Result<T>(T value) {
    private static final Result<Object> EMPTY = new Result<>(null);

    @SuppressWarnings("unchecked")
    public static <T> Result<T> empty() {
        return (Result<T>) EMPTY;
    }

    public static <T> Result<T> of(T value) {
        return value == null ? empty() : new Result<>(value);
    }

    public static <T> Result<T> of(Getter<T> getter) {
        try {
            return of(getter.get());
        } catch (Exception e) {
            return empty();
        }
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public <R> Result<R> map(Mapper<T, R> mapper) {
        if (isEmpty()) return empty();
        try {
            return of(mapper.apply(this.value()));
        } catch (Exception e) {
            return empty();
        }
    }

    public <R> Result<R> flatMap(Mapper<T, Result<R>> mapper) {
        if (isEmpty()) return empty();
        try {
            Result<R> result = mapper.apply(this.value());
            return result == null ? empty() : result;
        } catch (Exception e) {
            return empty();
        }
    }

    public <R> Result<R> xmap(Mapper<T, R> mapper, Getter<R> getter) {
        if (isEmpty()) {
            return of(getter);
        } else {
            return this.map(mapper);
        }
    }

    public Result<T> ifPresent(Consumer<T> consumer) {
        if (!isEmpty()) consumer.accept(this.value());
        return this;
    }

    public Result<T> ifAbsent(Runnable runnable) {
        if (isEmpty()) runnable.run();
        return this;
    }

    public interface Getter<T> {
        T get() throws Exception;
    }

    public interface Mapper<T, R> {
        R apply(T value) throws Exception;
    }
}
