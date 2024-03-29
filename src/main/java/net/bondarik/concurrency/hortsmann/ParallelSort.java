package net.bondarik.concurrency.hortsmann;

import java.util.Arrays;
import java.util.Random;

public class ParallelSort {

    private static Random random = new Random();

    public static void main(String[] args) {
        int len = 10;

        while (len <= 1_000_000_000) {
            int[] serialArr = generateArray(len);
            int[] parallelArr = serialArr.clone();

            Long start = System.currentTimeMillis();
            Arrays.sort(serialArr);
            Long end = System.currentTimeMillis();
            System.out.println(String.format("Sort %s, size = %d, time = %d", "serial", len, end - start));

            Long start1 = System.currentTimeMillis();
            Arrays.parallelSort(parallelArr);
            Long end1 = System.currentTimeMillis();
            System.out.println(String.format("Sort %s, size = %d, time = %d", "parallel", len, end1 - start1));

            len *= 10;
        }


    }

    private static int[] generateArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

}
