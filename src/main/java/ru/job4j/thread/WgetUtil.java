package ru.job4j.thread;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public final class WgetUtil {
    public static URL acquireUrl(String address) {
        URL result;
        try {
            result = new URI(address).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Incorrect URL to download.", e);
        }
        return result;
    }

    public static int acquireSpeed(String speed) {
        int result;
        try {
            result = Integer.parseInt(speed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect argument for download speed.", e);
        }
        return result;
    }

    public static File acquireFile(URL url) {
        String[] elements = url.getFile().split("/");
        return Path.of(elements[elements.length - 1]).toFile();
    }
}
