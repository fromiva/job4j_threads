package ru.job4j.fork;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static java.util.Arrays.copyOfRange;

public class ForkSearch<T> {
    private final static int LIMIT = 10;
    private final ForkJoinPool pool = new ForkJoinPool();
    private final T[] array;

    public ForkSearch(T[] array) {
        this.array = array;
    }

    public int findFirst(T target) {
        return pool.invoke(new FindFirstTask(array, target));
    }

    class FindFirstTask extends RecursiveTask<Integer> {
        private final T[] array;
        private final T target;

        public FindFirstTask(T[] array, T target) {
            this.array = array;
            this.target = target;
        }

        @Override
        protected Integer compute() {
            int res = -1;
            int start = 0;
            int middle = array.length / 2;
            int end = array.length;
            if (end > LIMIT) {
                RecursiveTask<Integer> left = new FindFirstTask(copyOfRange(array, start, middle), target);
                RecursiveTask<Integer> right = new FindFirstTask(copyOfRange(array, middle, end), target);
                int leftRes = left.fork().join();
                int rightRes = right.fork().join();
                res = leftRes != -1 ? leftRes : rightRes != -1 ? middle + rightRes : rightRes;
            } else {
                for (int i = start; i < end; i++) {
                    if (Objects.equals(array[i], target)) {
                        res = i;
                        break;
                    }
                }
            }
            return res;
        }
    }
}
