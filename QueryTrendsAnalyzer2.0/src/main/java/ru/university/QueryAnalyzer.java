package ru.university;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryAnalyzer {
    private MyHashMap<String, Integer> queryCountMap = new MyHashMap<>();
    private int totalQueries = 0;

    public void processQuery(String query) {
        if (query == null || query.isBlank()) return;

        String normalized = normalizeQuery(query);
        if (normalized.isBlank()) return;

        int count = queryCountMap.containsKey(normalized) ?
                queryCountMap.get(normalized) : 0;
        queryCountMap.put(normalized, count + 1);
        totalQueries++;
    }

    public void processQueries(List<String> queries) {
        for (String query : queries) {
            processQuery(query);
        }
    }

    private String normalizeQuery(String query) {
        return query.toLowerCase()
                .trim()
                .replaceAll("\\s+", " ");
    }

    public List<Map.Entry<String, Integer>> getTopQueries(int n) {
        List<Map.Entry<String, Integer>> results = new ArrayList<>();

        for (String query : queryCountMap.keySet()) {
            results.add(new AbstractMap.SimpleEntry<>(
                    query,
                    queryCountMap.get(query)
            ));
        }

        results.sort((e1, e2) -> {
            int valueCompare = e2.getValue().compareTo(e1.getValue());
            if (valueCompare != 0) return valueCompare;
            return e1.getKey().compareTo(e2.getKey());
        });

        return results.subList(0, Math.min(n, results.size()));
    }

    public int getTotalQueries() {
        return totalQueries;
    }

    public int getUniqueQueriesCount() {
        return queryCountMap.size();
    }

    public int getQueryFrequency(String query) {
        String normalized = normalizeQuery(query);
        return queryCountMap.containsKey(normalized) ?
                queryCountMap.get(normalized) : 0;
    }

    public void reset() {
        // Замена clear() на создание новой мапы
        this.queryCountMap = new MyHashMap<>();
        totalQueries = 0;
    }
}