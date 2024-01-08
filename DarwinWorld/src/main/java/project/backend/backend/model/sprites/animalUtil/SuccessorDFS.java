package project.backend.backend.model.sprites.animalUtil;

import project.backend.backend.model.sprites.Animal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;



public class SuccessorDFS {

    public static int searchSuccessorsNo(Animal theFather) {
        if (theFather == null) {
            throw new IllegalArgumentException("theFather must not be null");
        }
        HashSet<Animal> visited = new HashSet<>();
        Stack<Animal> stack = new Stack<>();

        stack.push(theFather);

        while (!stack.isEmpty()) {
            Animal current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                List<Animal> children = current.getChildrenList();
                for (Animal child : children) {
                    stack.push(child);
                }
            }
        }
        return visited.size()-1; //without theFather
    }
}
