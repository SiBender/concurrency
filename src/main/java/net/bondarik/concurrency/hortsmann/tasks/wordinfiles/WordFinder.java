package net.bondarik.concurrency.hortsmann.tasks.wordinfiles;

import net.bondarik.concurrency.hortsmann.util.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

public class WordFinder {
    private static final String PATH = "C:/test";

    private static List<File> filesToAnalise;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        filesToAnalise = FileUtils.getFilesInDirectory(PATH);

        List<Callable<String>> tasks = new ArrayList<>();
        filesToAnalise.forEach(file -> {
            Callable<String> task = () -> {
                String filePath = file.getAbsolutePath();
                boolean isFound = false;
                try {

                    Scanner scanner = new Scanner(new FileInputStream(filePath));
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("word")) {
                            isFound = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("exception while working with " + filePath);
                }
                return "\n" + filePath + "-" + isFound;
            };

            tasks.add(task);
        });

        ExecutorService executor = Executors.newFixedThreadPool(15);

        List<Future<String>> results = new ArrayList<>(tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            results.add(executor.submit(tasks.get(i)));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(500);
        }

        List<String> r = new ArrayList<>();

        for (Future<String> f : results) {
            r.add(f.get());
        }
        System.out.println(r);
    }

}
