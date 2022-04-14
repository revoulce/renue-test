package ru.renue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    private final BufferedReader _reader;

    public ReadFile(String filename) throws FileNotFoundException {
        _reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
    }

    public String readNextLine() throws IOException {
        return _reader.readLine();
    }

    public String[] readNextLines(int n) throws IOException {
        String[] lines = new String[n];

        for (int i = 0; i < n; ++i) {
            if ((lines[i] = _reader.readLine()) == null) {
                if (i == 0) {
                    return null;
                }

                break;
            }
        }

        return lines;
    }

    public List<String> readAllLines() throws IOException {
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = _reader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    public void close() {
        try {
            _reader.close();
        } catch (IOException e) {
            System.err.println("Stream already closed.");
            e.printStackTrace();
        }
    }
}
