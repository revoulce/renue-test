package ru.renue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Airports {
    ReadFile _airportsFile;

    public Airports(Path pathToCsv) {
        _airportsFile = null;
        try {
            _airportsFile = new ReadFile(pathToCsv.toString());
        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found.");
            e.printStackTrace();
        }
    }

    public List<String[]> search(int column, String searchString) throws IOException {
        if (_airportsFile == null) {
            return null;
        }

        List<String[]> result = new ArrayList<>();
        String[] temp;
        String[] tokens;
        String token, lastToken;


        int startPosition, currentColumn, currentIndex;
        int bufferSize = 1000;
        boolean isInQuotes;

        while ((temp = _airportsFile.readNextLines(bufferSize)) != null) {
            for (String line : temp) {
                if (line == null) break;

                tokens = new String[column + 1];

                startPosition = 0;
                currentColumn = 0;
                currentIndex = 0;
                isInQuotes = false;

                for (int currentPosition = 0; currentPosition < line.length(); currentPosition++) {
                    if (currentColumn >= column) break;

                    if (line.charAt(currentPosition) == '\"') {
                        isInQuotes = !isInQuotes;
                    } else if (line.charAt(currentPosition) == ',' && !isInQuotes) {
                        tokens[currentIndex] = line.substring(startPosition, currentPosition);
                        startPosition = currentPosition + 1;
                        ++currentColumn;
                        ++currentIndex;
                    }
                }

                lastToken = line.substring(startPosition);
                tokens[currentIndex] = lastToken.equals(",") ? "" : lastToken;

                token = tokens[column - 1];

                if (token != null) {
                    if (token.startsWith("\"")) {
                        token = token.substring(token.indexOf("\"") + 1);
                    }

                    if (searchString.equals("") || token.startsWith(searchString)) {
                        result.add(tokens);
                    }
                }
            }
        }

        result.sort(Comparator.comparing(o -> o[column - 1]));

        return result;
    }

    public boolean isFileNotFoundException() {
        return _airportsFile == null;
    }
}
