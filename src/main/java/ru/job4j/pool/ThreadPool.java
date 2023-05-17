package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new ArrayList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(runnable(), "Thread " + i);
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }

    private Runnable runnable() {
        return () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tasks.pool().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}
