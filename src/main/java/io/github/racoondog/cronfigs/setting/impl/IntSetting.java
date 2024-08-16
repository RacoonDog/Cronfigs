package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.Setting;

import java.util.function.Consumer;

public class IntSetting extends Setting<Integer> {
    private volatile int value;

    public IntSetting(String name, String description, Integer defaultValue, Consumer<Integer> onChanged) {
        super(name, description, defaultValue, onChanged);
    }

    public static IntSettingBuilder create() {
        return new IntSettingBuilder();
    }

    public int getInt() {
        return this.value;
    }

    public void setInt(int newValue) {
        this.value = newValue;
        this.onChanged();
    }

    @Deprecated
    @Override
    public Integer get() {
        return this.getInt();
    }

    @Deprecated
    @Override
    public void set(Integer newValue) {
        this.setInt(newValue);
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }

    @Override
    public Result<JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.getInt()));
    }

    @Override
    public Result<Integer> deserialize(JsonElement element) {
        return Result.of(element::getAsInt);
    }

    public static class IntSettingBuilder extends AbstractSettingBuilder<IntSettingBuilder, Integer, IntSetting> {
        @Override
        public IntSetting build() {
            return new IntSetting(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected IntSettingBuilder self() {
            return this;
        }
    }
}
