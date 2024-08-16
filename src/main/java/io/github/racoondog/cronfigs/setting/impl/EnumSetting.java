package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.ImmutableSetting;

import java.util.function.Consumer;

public class EnumSetting<T extends Enum<T>> extends ImmutableSetting<T> {
    public final T[] values;

    public EnumSetting(String name, String description, T defaultValue, Consumer<T> onChanged) {
        super(name, description, defaultValue, onChanged);

        this.values = defaultValue.getDeclaringClass().getEnumConstants();
    }

    public static <T extends Enum<T>> EnumSettingBuilder<T> create() {
        return new EnumSettingBuilder<>();
    }

    @Override
    public Result<JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.get().name()));
    }

    @Override
    public Result<T> deserialize(JsonElement element) {
        return Result.of(element::getAsString)
            .map(strValue -> {
                for (T value : this.values) {
                    if (strValue.equals(value.name())) return value;
                }
                return null;
            });
    }

    public static class EnumSettingBuilder<T extends Enum<T>> extends AbstractSettingBuilder<EnumSettingBuilder<T>, T, EnumSetting<T>> {
        @Override
        public EnumSetting<T> build() {
            return new EnumSetting<>(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected EnumSettingBuilder<T> self() {
            return this;
        }
    }
}
