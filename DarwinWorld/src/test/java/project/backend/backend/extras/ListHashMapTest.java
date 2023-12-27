package project.backend.backend.extras;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListHashMapTest {

    @Test
    void putInside() {
        ListHashMap<Integer, Integer> listHashMap = new ListHashMap<>();
        listHashMap.putInside(1, 1);
        listHashMap.putInside(1, 2);
        listHashMap.putInside(1, 3);
        listHashMap.putInside(2, 1);
        listHashMap.putInside(2, 2);
        listHashMap.putInside(2, 3);
        listHashMap.putInside(3, 1);
        listHashMap.putInside(3, 2);
        listHashMap.putInside(3, 3);

        assertEquals(3, listHashMap.getListFrom(1).size());
        assertEquals(3, listHashMap.getListFrom(2).size());
        assertEquals(3, listHashMap.getListFrom(3).size());

        System.out.println(listHashMap);
    }

    @Test
    void removeFrom() {
        ListHashMap<Integer, Integer> listHashMap = new ListHashMap<>();
        listHashMap.putInside(1, 1);
        listHashMap.putInside(1, 2);
        listHashMap.putInside(1, 3);
        listHashMap.putInside(2, 1);
        listHashMap.putInside(2, 2);
        listHashMap.putInside(2, 3);
        listHashMap.putInside(3, 1);
        listHashMap.putInside(3, 2);
        listHashMap.putInside(3, 3);

        listHashMap.removeFrom(1, 1);
        listHashMap.removeFrom(1, 2);
        listHashMap.removeFrom(1, 3);
        listHashMap.removeFrom(2, 1);
        listHashMap.removeFrom(2, 2);
        listHashMap.removeFrom(2, 3);
        listHashMap.removeFrom(3, 1);
        listHashMap.removeFrom(3, 2);
        listHashMap.removeFrom(3, 3);

        assertTrue(listHashMap.isEmpty());

        System.out.println(listHashMap);
    }

}