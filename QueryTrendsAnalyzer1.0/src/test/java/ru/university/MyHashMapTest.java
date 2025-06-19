package ru.university;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    @Test
    void testBasicOperations() {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        map.put("a", 1);
        map.put("b", 2);
        map.put("a", 3); // Перезапись

        assertEquals(3, map.get("a"));
        assertEquals(2, map.get("b"));
        assertNull(map.get("c"));
        assertEquals(2, map.size());
    }

    @Test
    void testCollisions() {
        MyHashMap<Integer, String> map = new MyHashMap<>();

        // Все ключи будут в одном бакете из-за простой хеш-функции
        for (int i = 0; i < 100; i++) {
            map.put(i, "Value" + i);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals("Value" + i, map.get(i));
        }
    }
}