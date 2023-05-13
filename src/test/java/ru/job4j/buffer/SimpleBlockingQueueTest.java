package ru.job4j.buffer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferAndPoolByOneThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> actual = Collections.synchronizedList(new ArrayList<>());

        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            try {
                actual.add(queue.pool());
                actual.add(queue.pool());
                actual.add(queue.pool());
                actual.add(queue.pool());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertThat(actual).containsAll(List.of(1, 2, 3, 4));
    }

    @Test
    void whenOfferByOneAndPoolByTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> actual = Collections.synchronizedList(new ArrayList<>());

        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        Thread consumer1 = new Thread(() -> {
            try {
                actual.add(queue.pool());
                actual.add(queue.pool());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Consumer 1");
        Thread consumer2 = new Thread(() -> {
            try {
                actual.add(queue.pool());
                actual.add(queue.pool());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Consumer 2");
        producer.start();
        consumer1.start();
        consumer2.start();
        producer.join();
        consumer1.join();
        consumer2.join();

        assertThat(actual).containsAll(List.of(1, 2, 3, 4));
    }

    @Test
    void whenOfferAndPoolByTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> actual = Collections.synchronizedList(new ArrayList<>());

        Thread producer1 = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Producer 1");
        Thread producer2 = new Thread(() -> {
            try {
                queue.offer(3);
                queue.offer(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Producer 2");
        Thread consumer1 = new Thread(() -> {
            try {
                actual.add(queue.pool());
                actual.add(queue.pool());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Consumer 1");
        Thread consumer2 = new Thread(() -> {
            try {
                actual.add(queue.pool());
                actual.add(queue.pool());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, "Consumer 2");
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();

        assertThat(actual).containsAll(List.of(1, 2, 3, 4));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);
        final CopyOnWriteArrayList<Integer> actual = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        for (Integer integer : expected) {
                            queue.offer(integer);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            actual.add(queue.pool());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(actual).containsAll(expected);
    }
}
