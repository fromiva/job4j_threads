package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferAndPoolByOneThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> actual = Collections.synchronizedList(new ArrayList<>());

        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
        }, "Producer");
        Thread consumer = new Thread(() -> {
            actual.add(queue.pool());
            actual.add(queue.pool());
            actual.add(queue.pool());
            actual.add(queue.pool());
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
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
        }, "Producer");
        Thread consumer1 = new Thread(() -> {
            actual.add(queue.pool());
            actual.add(queue.pool());
        }, "Consumer 1");
        Thread consumer2 = new Thread(() -> {
            actual.add(queue.pool());
            actual.add(queue.pool());
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
            queue.offer(1);
            queue.offer(2);
        }, "Producer 1");
        Thread producer2 = new Thread(() -> {
            queue.offer(3);
            queue.offer(4);
        }, "Producer 2");
        Thread consumer1 = new Thread(() -> {
            actual.add(queue.pool());
            actual.add(queue.pool());
        }, "Consumer 1");
        Thread consumer2 = new Thread(() -> {
            actual.add(queue.pool());
            actual.add(queue.pool());
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
}
