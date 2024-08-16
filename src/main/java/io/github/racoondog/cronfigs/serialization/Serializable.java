package io.github.racoondog.cronfigs.serialization;

import com.google.gson.JsonElement;

public interface Serializable<T> {
    Result<? extends JsonElement> serialize();
    Result<T> deserialize(JsonElement element);
}
