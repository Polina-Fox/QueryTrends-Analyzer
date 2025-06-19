package ru.university;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class QueryAnalyzerTest {
    @Test
    void testQueryProcessing() {
        QueryAnalyzer analyzer = new QueryAnalyzer();

        analyzer.processQuery("java tutorial");
        analyzer.processQuery("Java Tutorial");
        analyzer.processQuery("  python course  ");
        analyzer.processQuery("Java TUTORIAL");
        analyzer.processQuery("data science");

        List<Map.Entry<String, Integer>> top = analyzer.getTopQueries(3);

        assertEquals(3, top.size());

        // Проверяем первый элемент
        assertEquals("java tutorial", top.get(0).getKey());
        assertEquals(3, top.get(0).getValue());

        // Проверяем наличие всех элементов с частотой 1
        boolean hasPython = false;
        boolean hasDataScience = false;

        for (int i = 1; i < top.size(); i++) {
            String key = top.get(i).getKey();
            int value = top.get(i).getValue();
            assertEquals(1, value);

            if (key.equals("python course")) hasPython = true;
            if (key.equals("data science")) hasDataScience = true;
        }

        assertTrue(hasPython);
        assertTrue(hasDataScience);
    }

    @Test
    void testEmptyFile() {
        QueryAnalyzer analyzer = new QueryAnalyzer();
        List<Map.Entry<String, Integer>> top = analyzer.getTopQueries(10);
        assertTrue(top.isEmpty());
    }
}
