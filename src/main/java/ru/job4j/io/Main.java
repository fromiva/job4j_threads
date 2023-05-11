package ru.job4j.io;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        URL url = Main.class.getResource("file.txt");
        Objects.requireNonNull(url);
        File file = new File(url.getFile());
        new SaveContent(file).saveContent(new ParseFile(file).getContent());
    }
}
