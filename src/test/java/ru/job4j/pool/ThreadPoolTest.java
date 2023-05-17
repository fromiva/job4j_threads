package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadPoolTest {
    @Test
    void whenWorkOneTime() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        AtomicInteger actual = new AtomicInteger(0);
        pool.work(actual::incrementAndGet);
        pool.shutdown();
        assertThat(actual.get()).isEqualTo(1);
    }

    @Test
    void whenWorkMultipleTimes() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        int limit = 25;
        AtomicInteger actual = new AtomicInteger(0);
        for (int i = 0; i < limit; i++) {
            pool.work(actual::incrementAndGet);
        }
        pool.shutdown();
        assertThat(actual.get()).isEqualTo(limit);
    }
}
