package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

import static ru.job4j.thread.WgetUtil.*;

public class Wget implements Runnable {
    private final URL url;
    private final int speed;
    private final File file;

    public Wget(URL url, int speed) {
        this.url = url;
        this.speed = speed;
        this.file = acquireFile(url);
    }

    public static void main(String[] args) throws InterruptedException {
        URL url;
        int speed;
        try {
            url = acquireUrl(args[0]);
            speed = acquireSpeed(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Exit...");
            return;
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            int bytesSaved = 0;
            int time = 1;
            LocalDateTime start = LocalDateTime.now();
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, bytesRead);
                bytesSaved += bytesRead;
                if (bytesSaved > speed * time) {
                    System.out.print("\rProcessed " + bytesSaved + " bytes for " + time + " sec.");
                    Thread.sleep(Long.max(0,
                            Duration.between(
                            LocalDateTime.now(),
                            start.plusSeconds(time)
                                    ).toMillis()));
                    time++;
                }
            }
            System.out.println("\nSaved to: " + file.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
