package project.backend.backend.extras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

