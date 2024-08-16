package io.github.racoondog.cronfigs.setting;

import io.github.racoondog.cronfigs.serialization.Serializable;

import java.util.function.Consumer;

public abstract class Setting<T> implements Serializable<T> {
    public final String name;
    public final String description;
    public final T defaultValue;
    protected final Consumer<T> onChanged;

    public Setting(String name, String description, T defaultValue, Consumer<T> onChanged) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.onChanged = onChanged;

        this.set(defaultValue);
    }

    public abstract T get();
    public abstract void set(T newValue);

    public abstract void reset();

    protected final void onChanged() {
        if (this.onChanged != null) this.onChanged.accept(this.get());
    }

    public abstract static class AbstractSettingBuilder<B extends AbstractSettingBuilder<B, T, S>, T, S extends Setting<T>> {
        protected String name;
        protected String description;
        protected T defaultValue;
        protected Consumer<T> onChanged;

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B description(String description) {
            this.description = description;
            return self();
        }

        public B defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return self();
        }

        public B onChanged(Consumer<T> callback) {
            this.onChanged = callback;
            return self();
        }

        public abstract S build();

        protected abstract B self();
    }
}
