package net.bondarik.concurrency.hortsmann;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.List;

public class Atomics {
    private static LongAdder adder = new LongAdder();
    private static LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);

    private static AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        Long start1 = System.currentTimeMillis();
        List<Runnable> tasks = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            tasks.add(buildAddingTask());
        }

        ExecutorService executor = Executors.newFixedThreadPool(16);
        tasks.forEach(executor::submit);
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(1);
        }
        Long finish1 = System.currentTimeMillis();
        System.out.println("AtomicLong finished in " + (finish1 - start1) + " ms");



        Long start2 = System.currentTimeMillis();
        List<Runnable> tasks2 = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            tasks2.add(buildAdderTask());
        }

        executor = Executors.newFixedThreadPool(16);
        tasks.forEach(executor::submit);
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(1);
        }
        Long finish2 = System.currentTimeMillis();
        System.out.println("AtomicLong finished in " + (finish2 - start2) + " ms");

    }

    private static Runnable buildAddingTask() {
        return () -> {
            for (int i = 0; i < 100_000; i++) {
                atomicLong.addAndGet(1);
            }
        };
    }

    private static Runnable buildAdderTask() {
        return () -> {
            for (int i = 0; i < 100_000; i++) {
                adder.add(1);
            }
        };
    }

    private static Runnable buildAdderTask() {
        return () -> {
            for (int i = 0; i < 100_000; i++) {
                adder.add(1);
            }
        };
    }
}
