package net.bondarik.concurrency.hortsmann;

import java.util.Arrays;

public class FibonacciByParallelPrefix {
    private static final int N = 20;

    public static void main(String[] args) {
        Matrix[] nums = new Matrix[N];

        Arrays.parallelSetAll(nums, Matrix::new);
        Arrays.parallelPrefix(nums, Matrix::multiply);

        System.out.println(nums[N - 1]);
    }

}
