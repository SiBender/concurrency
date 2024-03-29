package net.bondarik.concurrency.hortsmann.tasks.wordinfiles;

import net.bondarik.concurrency.hortsmann.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SetOfWordsInFiles {
    private static final String REGEXP = "[^0-9a-zA-Zа-яА-Я_\\-]+";
    private static final String PATH = "C:/test";

    private static final ConcurrentHashMap<String, Set<File>> result = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();

        List<File> files = FileUtils.getFilesInDirectory(PATH);

        List<Runnable> tasks = files.stream().map(SetOfWordsInFiles::buildTask).collect(Collectors.toList());

        ExecutorService executor = Executors.newFixedThreadPool(8);
        tasks.forEach(executor::submit);
        executor.shutdown();

        while (!executor.isTerminated()) {
            Thread.sleep(1);
        }

        Long finish = System.currentTimeMillis();



        System.out.println("Program finished in " + (finish - start) + " ms");
        System.out.println("size = " + result.size());
        System.out.println("values count = " + result.values().stream().map(Set::size).reduce(Integer::sum));
        System.out.println(result);
    }

    private static Runnable buildTask(File file) {
        return () -> {
            try {
                Scanner scanner = new Scanner(new FileInputStream(file.getAbsolutePath()));
                while (scanner.hasNextLine()) {
                    String[] words = scanner.nextLine().split(REGEXP);
                    Arrays.stream(words).forEach(word -> put(word, file));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        };
    }

    private static void put(String word, File file) {
        result.merge(word,
                     new HashSet<>(Collections.singletonList(file)),
                     (prev, next) -> {prev.addAll(next); return prev;});

       /* Set<File> files = result.get(word);
        if (files == null) {
            files = new HashSet<>();
            result.put(word, files);
        }
        files.add(file);*/

    }
}
