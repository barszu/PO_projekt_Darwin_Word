package project.backend.backend.model.sprites.animalUtil;

import project.backend.backend.model.sprites.Animal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * This class provides a method to search for all successors of a given animal using Depth-First Search (DFS).
 */
public class SuccessorDFS {

    /**
     * This method returns the number of successors of a given animal.
     * It uses Depth-First Search (DFS) to traverse the tree of successors.
     * It maintains a stack of animals to visit and a set of visited animals.
     * It starts with the given animal and visits all its successors.
     * It counts the number of visited animals, excluding the given animal.
     *
     * @param theFather The animal to search for its successors.
     * @return The number of successors of the given animal.
     * @throws IllegalArgumentException if the given animal is null.
     */
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
