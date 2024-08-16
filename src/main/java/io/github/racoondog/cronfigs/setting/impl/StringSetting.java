package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.ImmutableSetting;
import io.github.racoondog.cronfigs.utils.CharPredicate;
import io.github.racoondog.cronfigs.utils.Predicates;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class StringSetting extends ImmutableSetting<String> {
    protected final Predicate<String> filter;

    public StringSetting(String name, String description, String defaultValue, Consumer<String> onChanged, Predicate<String> filter) {
        super(name, description, defaultValue, onChanged);

        this.filter = filter;
    }

    public static StringSettingBuilder create() {
        return new StringSettingBuilder();
    }

    public boolean filter(String string) {
        return this.filter == null || this.filter.test(string);
    }

    @Override
    public void set(String newValue) {
        if (filter(newValue)) super.set(newValue);
    }

    @Override
    public Result<? extends JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.get()));
    }

    @Override
    public Result<String> deserialize(JsonElement element) {
        return Result.of(element::getAsString);
    }

    public static class StringSettingBuilder extends AbstractSettingBuilder<StringSettingBuilder, String, StringSetting> {
        protected Predicate<String> filter;

        public StringSettingBuilder filter(Predicate<String> filter) {
            this.filter = Predicates.and(this.filter, filter);
            return self();
        }

        public StringSettingBuilder filter(CharPredicate filter) {
            this.filter = Predicates.and(this.filter, Predicates.ofCharPredicate(filter));
            return self();
        }

        public StringSettingBuilder maxLength(int maxLength) {
            this.filter = Predicates.and(this.filter, s -> s.length() <= maxLength);
            return self();
        }

        @Override
        public StringSetting build() {
            return new StringSetting(this.name, this.description, this.defaultValue, this.onChanged, this.filter);
        }

        @Override
        protected StringSettingBuilder self() {
            return this;
        }
    }
}
