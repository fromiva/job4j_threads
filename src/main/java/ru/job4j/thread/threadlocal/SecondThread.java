package ru.job4j.thread.threadlocal;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Thread No 2");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
