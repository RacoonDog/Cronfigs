package io.github.racoondog.cronfigs.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.racoondog.cronfigs.serialization.Result;
import io.github.racoondog.cronfigs.utils.DelegatedCollection;
import io.github.racoondog.cronfigs.serialization.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Settings implements Serializable<Settings>, DelegatedCollection<SettingGroup> {
    private SettingGroup defaultGroup;
    private final List<SettingGroup> settingGroups = new ArrayList<>();

    public SettingGroup getDefaultGroup() {
        if (this.defaultGroup != null) return this.defaultGroup;
        return this.defaultGroup = this.create("General");
    }

    public SettingGroup create(String name) {
        SettingGroup group = new SettingGroup(name);
        this.settingGroups.add(group);
        return group;
    }

    @Override
    public Result<? extends JsonElement> serialize() {
        JsonObject object = new JsonObject();
        for (SettingGroup group : this.settingGroups) {
            group.serialize().ifPresent(value -> object.add(group.name, value));
        }
        return Result.of(object);
    }

    @Override
    public Result<Settings> deserialize(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        for (SettingGroup group : this.settingGroups) {
            JsonElement innerElement = object.get(group.name);
            if (innerElement != null) group.deserialize(innerElement);
        }
        return null;
    }

    @Override
    public Collection<SettingGroup> delegate() {
        return this.settingGroups;
    }
}
