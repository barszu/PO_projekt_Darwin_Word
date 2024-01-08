package project.backend.backend.model.sprites;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void getSuccessorsNo() {
        Animal father = new Animal();
        Animal child1 = new Animal();
        Animal child2 = new Animal();
        Animal child3 = new Animal();
        Animal child4 = new Animal();
        Animal child5 = new Animal();
        father.getChildrenList().addAll(List.of(child1 , child2 , child3));
        child1.getChildrenList().addAll(List.of(child4 , child5));
        assertEquals(5 , father.getSuccessorsNo());
    }
}