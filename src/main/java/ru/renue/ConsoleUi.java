package ru.renue;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

/**
 * Считывание ввода пользователя и вызов поиска по CSV файлу
 */
public class ConsoleUi {
    private Settings settings;
    private Airports airports;
    private final Scanner input;
    private final PrintStream output;

    public ConsoleUi() {
        settings = new Settings();
        airports = new Airports(Paths.get("airports.csv"));
        input = new Scanner(System.in);
        output = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        if (settings.getColumn() == 0) {
            fixSettings();
        }

        if (airports.isFileNotFoundException()) {
            fixAirports();
        }
    }


    public ConsoleUi(int column) {
        settings = new Settings(column);
        airports = new Airports(Paths.get("airports.csv"));
        input = new Scanner(System.in);
        output = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        if (settings.getColumn() == 0) {
            fixSettings();
        }

        if (airports.isFileNotFoundException()) {
            fixAirports();
        }
    }

    public void work() {
        output.print("Введите строку для поиска: ");
        String searchLine = input.nextLine();

        Instant start = null, finish = null;

        List<String[]> sortedAirports = null;
        try {
            start = Instant.now();
            sortedAirports = airports.search(settings.getColumn(), searchLine);
            finish = Instant.now();
        } catch (IOException e) {
            System.err.println("Файл 'airports.csv' был закрыт и произошла попытка считать из него данные.");
            e.printStackTrace();
        }

        if (sortedAirports != null) {
            for (String[] tokens : sortedAirports) {
                for (String token : tokens) {
                    output.print(token + ", ");
                }

                output.println();
            }
        }

        if (sortedAirports != null) {
            output.println("Количество найденных строк: " + sortedAirports.size());
        }
        output.println("Время, затраченное на поиск и сортировку: " + Duration.between(start, finish).toMillis() + " мс");
    }

    private void fixSettings() {
        while (settings.getColumn() == 0) {
            output.print("Введите колонку для работы: ");
            int column = Integer.parseInt(input.nextLine());
            if (column > 0) {
                settings = new Settings(column);
            }
        }
    }

    private void fixAirports() {
        while (airports.isFileNotFoundException()) {
            output.print("Введите путь к файлу 'airports.csv': ");
            String path = input.nextLine();
            if (!path.equals("")) {
                airports = new Airports(Paths.get(path));
            }
        }
    }
}
