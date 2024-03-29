package net.bondarik.concurrency.hortsmann;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutableWrapperWithCopy {
    private final List<String> data;

    public ImmutableWrapperWithCopy(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return new ArrayList<>(data); //защитное копирование
    }

}
