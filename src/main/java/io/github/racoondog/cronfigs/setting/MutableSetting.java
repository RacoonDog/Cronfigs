package io.github.racoondog.cronfigs.setting;

import java.util.function.Consumer;

public abstract class MutableSetting<T> extends Setting<T> {
    private final T value;

    public MutableSetting(String name, String description, T defaultValue, Consumer<T> onChanged, T initialValue) {
        super(name, description, defaultValue, onChanged);

        this.value = initialValue;

        this.set(defaultValue);
    }

    protected abstract void setFrom(T other);

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public void set(T newValue) {
        if (newValue != this.get()) {
            this.setFrom(newValue);
            this.onChanged();
        }
    }

    @Override
    public void reset() {
        this.setFrom(defaultValue);
    }
}
