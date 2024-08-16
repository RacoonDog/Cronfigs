package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.Setting;

import java.util.function.Consumer;

public class FloatSetting extends Setting<Float> {
    private volatile float value;

    public FloatSetting(String name, String description, Float defaultValue, Consumer<Float> onChanged) {
        super(name, description, defaultValue, onChanged);
    }

    public static FloatSettingBuilder create() {
        return new FloatSettingBuilder();
    }

    public float getFloat() {
        return this.value;
    }

    public void setFloat(float newValue) {
        this.value = newValue;
    }

    @Deprecated
    @Override
    public Float get() {
        return this.getFloat();
    }

    @Deprecated
    @Override
    public void set(Float newValue) {
        this.setFloat(newValue);
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }

    @Override
    public Result<JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.getFloat()));
    }

    @Override
    public Result<Float> deserialize(JsonElement element) {
        return Result.of(element::getAsFloat);
    }

    public static class FloatSettingBuilder extends AbstractSettingBuilder<FloatSettingBuilder, Float, FloatSetting> {
        @Override
        public FloatSetting build() {
            return new FloatSetting(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected FloatSettingBuilder self() {
            return this;
        }
    }
}
