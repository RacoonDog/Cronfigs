package io.github.racoondog.cronfigs.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.TreeMap;

public sealed class DataFix {
    public static final DataFix NOOP = new NoOp();
    public final int version;
    private final TreeMap<Integer, Processor> processors = new TreeMap<>();

    public DataFix(int version) {
        this.version = version;
    }

    public void process(JsonObject config) {
        JsonElement elem = config.get("version");
        if (elem == null) return;
        int version = elem.getAsInt();
        if (version >= this.version) return;

        for (Processor processor : processors.headMap(version).values()) {
            processor.process(config);
        }
    }

    public void otherProcess(JsonObject config) {
        config.addProperty("version", this.version);
    }

    private static final class NoOp extends DataFix {
        public NoOp() {
            super(Integer.MAX_VALUE);
        }

        @Override
        public void process(JsonObject object) {}

        @Override
        public void otherProcess(JsonObject object) {}
    }
}
