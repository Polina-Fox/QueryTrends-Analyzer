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

        List<Map.Entry<String, Integer>> top = analyzer.getTopQueries(2);

        assertEquals(2, top.size());
        assertEquals("java tutorial", top.get(0).getKey());
        assertEquals(3, top.get(0).getValue());
        assertEquals("python course", top.get(1).getKey());
        assertEquals(1, top.get(1).getValue());
    }

    @Test
    void testEmptyFile() {
        QueryAnalyzer analyzer = new QueryAnalyzer();
        List<Map.Entry<String, Integer>> top = analyzer.getTopQueries(10);
        assertTrue(top.isEmpty());
    }
}
