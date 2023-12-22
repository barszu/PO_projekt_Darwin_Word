package project.backend.model.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CyclicListExtrasTest {

    CyclicListExtras cyclicListExtras = new CyclicListExtras(10);

    @Test
    void getNewIdx() {
        assertEquals(0, cyclicListExtras.getNewIdx(0, 0));
        assertEquals(1, cyclicListExtras.getNewIdx(0, 1));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -1));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 10));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -10));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 20));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -20));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 30));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -30));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 40));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -40));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 50));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -50));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 60));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -60));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 70));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -70));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 80));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -80));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 90));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -90));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 100));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -100));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 110));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -110));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 120));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -120));
        assertEquals(0, cyclicListExtras.getNewIdx(0, 130));
        assertEquals(9, cyclicListExtras.getNewIdx(0, -130));
    }

}