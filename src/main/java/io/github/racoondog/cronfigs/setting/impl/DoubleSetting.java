package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.Setting;

import java.util.function.Consumer;

public class DoubleSetting extends Setting<Double> {
    private volatile double value;

    public DoubleSetting(String name, String description, Double defaultValue, Consumer<Double> onChanged) {
        super(name, description, defaultValue, onChanged);
    }

    public static DoubleSettingBuilder create() {
        return new DoubleSettingBuilder();
    }

    public double getDouble() {
        return this.value;
    }

    public void setDouble(double newValue) {
        this.value = newValue;
    }

    @Deprecated
    @Override
    public Double get() {
        return this.getDouble();
    }

    @Deprecated
    @Override
    public void set(Double newValue) {
        this.setDouble(newValue);
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }

    @Override
    public Result<JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.getDouble()));
    }

    @Override
    public Result<Double> deserialize(JsonElement element) {
        return Result.of(element::getAsDouble);
    }

    public static class DoubleSettingBuilder extends AbstractSettingBuilder<DoubleSettingBuilder, Double, DoubleSetting> {
        @Override
        public DoubleSetting build() {
            return new DoubleSetting(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected DoubleSettingBuilder self() {
            return this;
        }
    }
}
