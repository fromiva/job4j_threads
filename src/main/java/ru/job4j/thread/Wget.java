package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

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

    private static URL acquireUrl(String address) {
        URL result;
        try {
            result = new URI(address).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Incorrect URL to download.", e);
        }
        return result;
    }

    private static int acquireSpeed(String speed) {
        int result;
        try {
            result = Integer.parseInt(speed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect argument for download speed.", e);
        }
        return result;
    }

    private static File acquireFile(URL url) {
        String[] elements = url.getFile().split("/");
        return Path.of(elements[elements.length - 1]).toFile();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            int bytesSaved = 0;
            int time = 1;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, bytesRead);
                bytesSaved += bytesRead;
                if (bytesSaved > speed * time) {
                    System.out.print("\rProcessed " + bytesSaved + " bytes for " + time + " sec.");
                    Thread.sleep(1000);
                    time++;
                }
            }
            System.out.println("\rSaved to: " + file.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
