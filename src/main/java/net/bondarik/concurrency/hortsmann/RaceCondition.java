package net.bondarik.concurrency.hortsmann;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RaceCondition {
    private static int count = 0;

    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            Runnable task = () -> {
                for (int j = 1; j <= 1000; j++) {
                    count++;
                }
                System.out.println(taskId + ": " + count);
            };
            executor.execute(task);
        }

    }
}
