package ru.university;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar query-analyzer.jar <file-path> [top-n]");
            return;
        }

        String filePath = args[0];
        int topN = args.length > 1 ? Integer.parseInt(args[1]) : 10; // По умолчанию топ-10

        QueryAnalyzer analyzer = new QueryAnalyzer();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                analyzer.processQuery(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Вывод результатов
        System.out.println("Top " + topN + " popular queries:");
        List<Map.Entry<String, Integer>> topQueries = analyzer.getTopQueries(topN);

        for (int i = 0; i < topQueries.size(); i++) {
            Map.Entry<String, Integer> entry = topQueries.get(i);
            System.out.printf("%d. %s (%d occurrences)%n",
                    i + 1,
                    entry.getKey(),
                    entry.getValue());
        }
    }
}