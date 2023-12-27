package project.backend.model.models;

import org.junit.jupiter.api.Test;
import project.backend.backend.extras.CyclicListExtras;

import static org.junit.jupiter.api.Assertions.*;

class CyclicListExtrasTest {

    @Test
    void getNewIdx() {
        int size = 10;
        //TODO: SIMON werify this tests

        assertEquals(0 , CyclicListExtras.getNewIdx(0 , 10 ,size));
        assertEquals(1 , CyclicListExtras.getNewIdx(0 , 11 ,size));
        assertEquals(9 , CyclicListExtras.getNewIdx(0 , -1 ,size));
        assertEquals(8 , CyclicListExtras.getNewIdx(0 , -2 ,size));
        assertEquals(0 , CyclicListExtras.getNewIdx(0 , -10 ,size));
        assertEquals(1 , CyclicListExtras.getNewIdx(0 , -11 ,size));
        assertEquals(0 , CyclicListExtras.getNewIdx(0 , -20 ,size));
        assertEquals(9 , CyclicListExtras.getNewIdx(0 , -21 ,size));
        assertEquals(9 , CyclicListExtras.getNewIdx(0 , -40 ,size));


        assertEquals(9 , CyclicListExtras.getNewIdx(9 , 1 ,size));
        assertEquals(0 , CyclicListExtras.getNewIdx(9 , 2 ,size));
        assertEquals(8 , CyclicListExtras.getNewIdx(9 , -1 ,size));
        assertEquals(7 , CyclicListExtras.getNewIdx(9 , -2 ,size));
        assertEquals(9 , CyclicListExtras.getNewIdx(9 , -10 ,size));
        assertEquals(8 , CyclicListExtras.getNewIdx(9 , -11 ,size));


    }

}