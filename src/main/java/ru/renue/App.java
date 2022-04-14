package ru.renue;

public class App {
    public static void main(String[] args) {
        ConsoleUi consoleUi;
        if (args.length > 0) {
            consoleUi = new ConsoleUi(Integer.parseInt(args[0]));
        } else {
            consoleUi = new ConsoleUi();
        }
        consoleUi.work();
    }
}
