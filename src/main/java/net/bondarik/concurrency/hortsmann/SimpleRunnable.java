package net.bondarik.concurrency.hortsmann;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SimpleRunnable {
    public static void main(String[] args) {
        Runnable hellos = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello " + i);
            }
        };

        Runnable goodbyes = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Goodbye " + i);
            }
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hellos);
        executor.execute(goodbyes);
    }
}
