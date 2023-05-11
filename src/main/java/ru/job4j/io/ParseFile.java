package ru.job4j.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() {
        return content(c -> c < 0x80);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder result = new StringBuilder();
        synchronized (file) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                int code;
                while ((code = in.read()) != -1) {
                    char c = (char) code;
                    if (filter.test(c)) {
                        result.append(c);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
