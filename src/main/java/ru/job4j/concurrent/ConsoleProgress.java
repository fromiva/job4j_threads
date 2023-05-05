package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    char[] process = new char[]{'-', '\\', '|', '/'};

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (char c : process) {
                    System.out.print("\r load: " + c);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
