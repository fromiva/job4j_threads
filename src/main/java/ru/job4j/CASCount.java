package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer old;
        Integer current;
        do {
            old = count.get();
            current = old + 1;
        } while (!count.compareAndSet(old, current));
    }

    public int get() {
        return count.get();
    }
}
