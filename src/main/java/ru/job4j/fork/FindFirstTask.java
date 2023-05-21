package ru.job4j.fork;

import java.util.Objects;
import java.util.concurrent.RecursiveTask;

class FindFirstTask extends RecursiveTask<Integer> {
    private final static int LIMIT = 10;
    private final Object[] array;
    private final int rangeStart;
    private final int rangeEnd;
    private final int rangeLength;
    private final int rangeMiddle;
    private final Object target;

    public FindFirstTask(Object[] array, int rangeStart, int rangeEnd, Object target) {
        this.array = array;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        rangeLength = rangeEnd - rangeStart;
        rangeMiddle = rangeStart + rangeLength / 2;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        if (rangeLength > LIMIT) {
            RecursiveTask<Integer> left = new FindFirstTask(array, rangeStart, rangeMiddle, target);
            RecursiveTask<Integer> right = new FindFirstTask(array, rangeMiddle, rangeEnd, target);
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
