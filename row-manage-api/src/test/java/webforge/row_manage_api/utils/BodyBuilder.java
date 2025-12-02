package webforge.row_manage_api.utils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;


public class BodyBuilder {
    private final Map<String, Object> body;

    public BodyBuilder(Map<String, Object> body) {
        this.body = body;
    }

    public static BodyBuilder with(String path, Object value) {
        var builder = new BodyBuilder(new HashMap<>());
        builder.body.put(path, value);
        return builder;
    }

    public static BodyBuilder with(String path, BodyBuilder object) {
        var builder = new BodyBuilder(new HashMap<>());
        builder.body.put(path, object.build());
        return builder;
    }

    public static BodyBuilder withList(String path, BodyBuilder... elements) {
        var builder = new BodyBuilder(new HashMap<>());
        builder.body.put(path, stream(elements).map(BodyBuilder::build).collect(toList()));
        return builder;
    }

    public BodyBuilder and(String path, Object value) {
        body.put(path, value);
        return this;
    }

    public BodyBuilder and(String path, BodyBuilder value) {
        body.put(path, value.build());
        return this;
    }

    public BodyBuilder andList(String path, BodyBuilder... elements) {
        body.put(path, stream(elements).map(BodyBuilder::build).collect(toList()));
        return this;
    }

    public BodyBuilder andList(String path, Object... elements) {
        body.put(path, Arrays.asList(elements));
        return this;
    }

    public Object build() {
        return body;
    }
}
