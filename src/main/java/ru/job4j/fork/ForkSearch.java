package ru.job4j.fork;

import java.util.concurrent.ForkJoinPool;

public class ForkSearch<T> {
    private final ForkJoinPool pool = new ForkJoinPool();
    private final T[] array;

    public ForkSearch(T[] array) {
        this.array = array;
    }

    public int findFirst(T target) {
        return pool.invoke(new FindFirstTask(array, 0, array.length, target));
    }
}
