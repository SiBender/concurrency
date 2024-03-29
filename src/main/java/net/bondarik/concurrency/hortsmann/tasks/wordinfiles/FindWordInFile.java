package net.bondarik.concurrency.hortsmann.tasks.wordinfiles;

import net.bondarik.concurrency.hortsmann.util.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FindWordInFile {
    private static final String REGEXP = "[^0-9a-zA-Zа-яА-Я_\\-]+";
    private static final String PATH = "C:/test";
    private static final String WORD_TO_FIND = "все";

    private static List<File> filesToAnalise;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        filesToAnalise = FileUtils.getFilesInDirectory(PATH);

        List<Callable<Boolean>> tasks = filesToAnalise.stream().map(file -> buildSearchingTask(file, WORD_TO_FIND))
                                        .collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(2); //если много потоков, то успевают отработать все таски

        executor.invokeAny(tasks);
        executor.shutdown();

        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }

        System.out.println("end of program");
    }

    private static Callable<Boolean> buildSearchingTask(File file, String needed) {
        return () -> {
            boolean isFound = false;
            try {
                Scanner scanner = new Scanner(Files.newInputStream(Paths.get(file.getAbsolutePath())));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(REGEXP);
                    for (String word : words) {
                        if (needed.equals(word)) {
                            isFound = true;
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }

            System.out.println(String.format("WORD '%s' is %sfound in %s", needed, isFound ? "" : "not ", file.getAbsolutePath()));

            return isFound;
        };
    }
}
