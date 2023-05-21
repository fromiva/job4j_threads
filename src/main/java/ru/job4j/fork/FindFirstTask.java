package ru.job4j.fork;

import java.util.Objects;
import java.util.concurrent.RecursiveTask;

class FindFirstTask<T> extends RecursiveTask<Integer> {
    private final static int LIMIT = 10;
    private final T[] array;
    private final int rangeStart;
    private final int rangeEnd;
    private final T target;

    public FindFirstTask(T[] array, int rangeStart, int rangeEnd, T target) {
        this.array = array;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        int rangeLength = rangeEnd - rangeStart;
        int rangeMiddle = rangeStart + rangeLength / 2;
        if (rangeLength > LIMIT) {
            RecursiveTask<Integer> left = new FindFirstTask<>(array, rangeStart, rangeMiddle, target);
            RecursiveTask<Integer> right = new FindFirstTask<>(array, rangeMiddle, rangeEnd, target);
            left.fork();
            right.fork();
            int leftResult = left.join();
            int rightResult = right.join();
            return leftResult != -1 ? leftResult : rightResult;
        }
        for (int index = rangeStart; index < rangeEnd; index++) {
            if (Objects.equals(array[index], target)) {
                return index;
            }
        }
        return result;
    }
}
