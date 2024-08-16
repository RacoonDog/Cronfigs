package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.MutableSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StringListSetting extends MutableSetting<List<String>> {

    public StringListSetting(String name, String description, List<String> defaultValue, Consumer<List<String>> onChanged) {
        super(name, description, defaultValue, onChanged, new ArrayList<>());
    }

    public static StringListSettingBuilder create() {
        return new StringListSettingBuilder();
    }

    @Override
    protected void setFrom(List<String> other) {
        this.get().clear();
        this.get().addAll(other);
    }

    @Override
    public Result<? extends JsonElement> serialize() {
        return Result.of(new JsonArray(this.get().size())).ifPresent(array -> {
            for (String s : this.get()) {
                array.add(s);
            }
        });
    }

    @Override
    public Result<List<String>> deserialize(JsonElement element) {
        return Result.of(element::getAsJsonArray).ifPresent(value -> {
            this.get().clear();
            for (JsonElement innerElement : element.getAsJsonArray()) {
                this.get().add(innerElement.getAsString());
            }
        }).map(value -> this.get());
    }

    public static class StringListSettingBuilder extends AbstractSettingBuilder<StringListSettingBuilder, List<String>, StringListSetting> {
        public StringListSettingBuilder defaultValue(String... defaultValues) {
            return this.defaultValue(Arrays.asList(defaultValues));
        }

        @Override
        public StringListSetting build() {
            return new StringListSetting(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected StringListSettingBuilder self() {
            return this;
        }
    }
}
