package net.bondarik.concurrency.hortsmann.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileUtils {
    public static List<File> getFilesInDirectory(String rootPath) {
        List<File> files = new ArrayList<>();

        Queue<File> disr = new LinkedList<>();
        File root = new File(rootPath);
        disr.add(root);
        while (!disr.isEmpty()) {
            File currentDir = disr.poll();
            for (File file : currentDir.listFiles()) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    disr.add(file);
                }
            }
        }

        return files;
    }
}
