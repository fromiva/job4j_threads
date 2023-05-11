package ru.job4j.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class SaveContent {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (file) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                out.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
