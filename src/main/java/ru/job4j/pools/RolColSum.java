package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        for (int i = 0; i < size; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
            }
            for (int j = 0; j < size; j++) {
                colSum += matrix[j][i];
            }
            result[i] = new Sums(rowSum, colSum);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int size = matrix.length;
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < size; i++) {
            futures.put(i, getTask(matrix, i));
        }
        Sums[] result = new Sums[size];
        for (int i : futures.keySet()) {
            try {
                result[i] = futures.get(i).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int size = matrix.length;
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
            }
            for (int j = 0; j < size; j++) {
                colSum += matrix[j][i];
            }
            return new Sums(rowSum, colSum);
        });
    }

    public record Sums(int rowSum, int colSum) {
    }
}
