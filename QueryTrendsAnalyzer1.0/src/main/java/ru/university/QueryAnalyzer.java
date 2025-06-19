package ru.university;

import java.util.*;

public class QueryAnalyzer {
    private final MyHashMap<String, Integer> queryCountMap = new MyHashMap<>();

    // Обработка одного запроса
    public void processQuery(String query) {
        if (query == null || query.isBlank()) return;

        String normalized = normalizeQuery(query);
        int count = queryCountMap.containsKey(normalized) ?
                queryCountMap.get(normalized) : 0;
        queryCountMap.put(normalized, count + 1);
    }

    // Нормализация запроса
    private String normalizeQuery(String query) {
        return query.toLowerCase()
                .trim()
                .replaceAll("\\s+", " "); // Удаление лишних пробелов
    }

    // Получение топ-N запросов
    public List<Map.Entry<String, Integer>> getTopQueries(int n) {
        List<Map.Entry<String, Integer>> results = new ArrayList<>();

        // Собираем все записи
        for (String query : queryCountMap.keySet()) {
            results.add(new AbstractMap.SimpleEntry<>(
                    query,
                    queryCountMap.get(query)
            ));
        }

        // Сортировка по убыванию частоты
        results.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Возвращаем топ-N результатов
        return results.subList(0, Math.min(n, results.size()));
    }
}