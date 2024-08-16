package io.github.racoondog.cronfigs.setting;

import java.util.function.Consumer;

public abstract class ImmutableSetting<T> extends Setting<T> {
    private volatile T value;

    public ImmutableSetting(String name, String description, T defaultValue, Consumer<T> onChanged) {
        super(name, description, defaultValue, onChanged);
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public void set(T newValue) {
        this.value = newValue;
        this.onChanged();
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }
}
