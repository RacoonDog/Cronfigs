package io.github.racoondog.cronfigs.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.utils.DelegatedCollection;
import io.github.racoondog.cronfigs.serialization.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SettingGroup implements Serializable<SettingGroup>, DelegatedCollection<Setting<?>> {
    public final String name;
    private final List<Setting<?>> settings = new ArrayList<>();

    public SettingGroup(String name) {
        this.name = name;
    }

    public <T, S extends Setting<T>> S add(S setting) {
        this.settings.add(setting);
        return setting;
    }

    @Override
    public Result<JsonElement> serialize() {
        JsonObject object = new JsonObject();
        for (Setting<?> setting : this.settings) setting.serialize().ifPresent(value -> object.add(setting.name, value));
        return Result.of(object);
    }

    @Override
    public Result<SettingGroup> deserialize(JsonElement element) {
        Result.of(element::getAsJsonObject).ifPresent(object -> {
            for (Setting<?> setting : this.settings) {
                JsonElement innerElement = object.get(setting.name);
                if (innerElement != null) deserialize(setting, innerElement);
            }
        });

        return Result.of(this);
    }

    private <T> void deserialize(Setting<T> setting, JsonElement jsonElement) {
        setting.deserialize(jsonElement).ifPresent(setting::set);
    }

    @Override
    public Collection<Setting<?>> delegate() {
        return this.settings;
    }
}
