package io.github.racoondog.cronfigs.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.setting.Setting;

import java.util.function.Consumer;

public class LongSetting extends Setting<Long> {
    private volatile long value;

    public LongSetting(String name, String description, Long defaultValue, Consumer<Long> onChanged) {
        super(name, description, defaultValue, onChanged);
    }

    public static LongSettingBuilder create() {
        return new LongSettingBuilder();
    }

    public long getLong() {
        return this.value;
    }

    public void setLong(long newValue) {
        this.value = newValue;
    }

    @Deprecated
    @Override
    public Long get() {
        return this.getLong();
    }

    @Deprecated
    @Override
    public void set(Long newValue) {
        this.setLong(newValue);
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }

    @Override
    public Result<JsonElement> serialize() {
        return Result.of(new JsonPrimitive(this.getLong()));
    }

    @Override
    public Result<Long> deserialize(JsonElement element) {
        return Result.of(element::getAsLong);
    }

    public static class LongSettingBuilder extends AbstractSettingBuilder<LongSettingBuilder, Long, LongSetting> {
        @Override
        public LongSetting build() {
            return new LongSetting(this.name, this.description, this.defaultValue, this.onChanged);
        }

        @Override
        protected LongSettingBuilder self() {
            return this;
        }
    }
}
