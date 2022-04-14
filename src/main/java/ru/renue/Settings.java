package ru.renue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Считывание и хранение настроек программы
 */
public class Settings {
    private int _column = 0;

    public Settings() {
        ReadFile file = null;
        List<String> settingsList = null;

        try {
            file = new ReadFile(Paths.get("application.yml").toString());
            settingsList = file.readAllLines();
        } catch (FileNotFoundException e) {
            System.err.println("Error opening settings file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading settings file.");
            e.printStackTrace();
        } finally {
            if (file != null) {
                file.close();
            }
        }

        if (settingsList != null) {
            if (settingsList.get(1).split(":")[0].trim().equals("column")) { // Проверка что поле действительно настройка для колонки
                int column = Integer.parseInt(settingsList.get(1).split(":")[1].trim());
                if (column > 0) { // Колонок не может быть меньше одной
                    _column = column;
                }
            }
        }
    }

    public Settings(int column) {
        _column = column;
    }

    public int getColumn() {
        return _column;
    }
}
