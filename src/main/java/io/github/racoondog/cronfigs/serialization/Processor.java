package io.github.racoondog.cronfigs.serialization;

import com.google.gson.JsonObject;

public abstract class Processor {
    public abstract void process(JsonObject config);
}
