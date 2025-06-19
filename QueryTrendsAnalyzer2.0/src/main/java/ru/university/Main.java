package ru.university;

import javafx.application.Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Проверяем аргументы командной строки
        if (args.length > 0 && args[0].equals("--cli")) {
            runCliMode(args);
        } else {
            // Запускаем GUI приложение
            Application.launch(QueryAnalyzerApp.class, args);
        }
    }

    /**
     * Режим командной строки (CLI)
     * @param args аргументы командной строки
     */
    private static void runCliMode(String[] args) {
        if (args.length < 2) {
            printCliHelp();
            return;
        }

        String filePath = args[1];
        int topN = 10; // Значение по умолчанию

        if (args.length >= 3) {
            try {
                topN = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: неверный формат числа для top-N. Использую значение по умолчанию (10).");
            }
        }

        // Создаем анализатор
        QueryAnalyzer analyzer = new QueryAnalyzer();

        // Читаем и обрабатываем файл
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                analyzer.processQuery(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        // Выводим результаты
        printResults(analyzer, topN);
    }

    /**
     * Печатает результаты анализа в консоль
     * @param analyzer анализатор запросов
     * @param topN количество топовых результатов для вывода
     */
    private static void printResults(QueryAnalyzer analyzer, int topN) {
        System.out.println("Общая статистика:");
        System.out.println("• Всего запросов: " + analyzer.getTotalQueries());
        System.out.println("• Уникальных запросов: " + analyzer.getUniqueQueriesCount());
        System.out.println();

        System.out.println("Топ " + topN + " популярных запросов:");
        List<Map.Entry<String, Integer>> topQueries = analyzer.getTopQueries(topN);

        for (int i = 0; i < topQueries.size(); i++) {
            Map.Entry<String, Integer> entry = topQueries.get(i);
            System.out.printf("%2d. %-40s - %d occurrences%n",
                    i + 1,
                    truncate(entry.getKey(), 40),
                    entry.getValue());
        }
    }

    /**
     * Обрезает строку до указанной длины
     * @param input входная строка
     * @param maxLength максимальная длина
     * @return обрезанная строка
     */
    private static String truncate(String input, int maxLength) {
        if (input.length() <= maxLength) {
            return input;
        }
        return input.substring(0, maxLength - 3) + "...";
    }

    /**
     * Выводит справку по использованию CLI режима
     */
    private static void printCliHelp() {
        System.out.println("Использование:");
        System.out.println("  GUI режим (по умолчанию):");
        System.out.println("    java -jar query-analyzer.jar");
        System.out.println();
        System.out.println("  CLI режим:");
        System.out.println("    java -jar query-analyzer.jar --cli <путь-к-файлу> [top-N]");
        System.out.println();
        System.out.println("Аргументы:");
        System.out.println("  --cli          - запуск в режиме командной строки");
        System.out.println("  <путь-к-файлу> - путь к файлу с поисковыми запросами");
        System.out.println("  [top-N]        - количество выводимых топ запросов (опционально, по умолчанию 10)");
        System.out.println();
        System.out.println("Пример:");
        System.out.println("  java -jar query-analyzer.jar --cli queries.txt 5");
    }
}