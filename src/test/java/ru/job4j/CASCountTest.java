package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    void whenNoIncrement() {
        CASCount count = new CASCount();
        assertThat(count.get()).isEqualTo(0);
    }

    @Test
    void whenIncrement() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get()).isEqualTo(1);
    }

    @Test
    void whenIncrementTwoTimes() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        assertThat(count.get()).isEqualTo(2);
    }

    @Test
    void whenIncrementByTwoThreads() throws InterruptedException {
        CASCount count = new CASCount();
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertThat(count.get()).isEqualTo(20);
    }
}
