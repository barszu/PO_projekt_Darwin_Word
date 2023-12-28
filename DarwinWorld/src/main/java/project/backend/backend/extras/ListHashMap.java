package project.backend.backend.extras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class represents a HashMap that maps keys of type K to lists of values of type V.
 * It provides methods to add and remove values associated with a specific key,
 * as well as methods to retrieve the list of values for a key, check if a key or value exists,
 * check if the map is empty, and get the set of keys.
 * It also overrides the toString method to provide a string representation of the map.
 *
 * ATTENTION: There are no assignments to empty list!
 *            Always paired list has at least one element!
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of values that lists associated with keys in this map can hold
 */
public class ListHashMap<K, V>{


    private final HashMap<K, List<V>> normalHashMap = new HashMap<>();
    public void putInside(K key, V value) {
        if (normalHashMap.containsKey(key)) {
            normalHashMap.get(key).add(value);
        } else {
            List<V> newList = new ArrayList<>();
            newList.add(value);

            normalHashMap.put(key, newList);
        }
    }

    public void removeFrom(K key , V value){
        if (!normalHashMap.containsKey(key)) {
            throw new IllegalArgumentException("no such key in ListHashMap");
        }
        List<V> list = normalHashMap.get(key);
        if (!list.contains(value)) {
            throw new IllegalArgumentException("no such value in ListHashMap on key: " + key);
        }

        list.remove(value);
        if (list.isEmpty()){
            normalHashMap.remove(key);
        }
    }

    public List<V> getListFrom(K key) {
        if (!normalHashMap.containsKey(key)) {
            throw new IllegalArgumentException("no such key in ListHashMap");
        }
        return normalHashMap.get(key);
    }

    public boolean containsKey(K key){
        return normalHashMap.containsKey(key);
    }

    public boolean containsValue(K key , V value){
        if (!normalHashMap.containsKey(key)) {
            throw new IllegalArgumentException("no such key in ListHashMap");
        }
        return normalHashMap.get(key).contains(value);
    }

    public boolean isEmpty(){
        return normalHashMap.isEmpty();
    }

    public Set<K> keySet(){
        return normalHashMap.keySet();
    }



    @Override
    public String toString(){
        return normalHashMap.toString();
    }

}

