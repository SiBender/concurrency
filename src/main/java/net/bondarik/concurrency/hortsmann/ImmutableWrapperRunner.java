package net.bondarik.concurrency.hortsmann;

import java.time.LocalDateTime;
import java.util.List;

public class ImmutableWrapperRunner {
    public static void main(String[] args) {
        ImmutableWrapper immutableWrapper = new ImmutableWrapper(List.of("a", "b", "c"));
        System.out.println(immutableWrapper.getData());
        immutableWrapper.getData().set(1,"zzz");
        System.out.println(immutableWrapper.getData()); //UnsupportedOperationException

        LocalDateTime dateTime = LocalDateTime.now();
    }
}
