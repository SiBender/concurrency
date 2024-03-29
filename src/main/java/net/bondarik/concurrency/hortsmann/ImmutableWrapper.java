package net.bondarik.concurrency.hortsmann;

import java.util.Collections;
import java.util.List;

public final class ImmutableWrapper {
    private final List<String> data;

    public ImmutableWrapper(List<String> data) {
        this.data = Collections.unmodifiableList(data);
    }

    public List<String> getData() {
        return data;
    }

}
