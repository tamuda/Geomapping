import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
// cite: https://medium.com/swlh/hashmap-implementation-for-java-90a5f58d4a5b
// Some Methods and general structure taken from above implementation
public class MyHashMap<K, V> {
    private int CAPACITY = 10;
    MyMap[] bucket;
    private int size = 0;

    public MyHashMap() {
        this.bucket = new MyMap[CAPACITY];
    }
    private int getHash(K key) {
        return (key.hashCode() & 0xfffffff) % CAPACITY;
    }

    private MyKey getEntry(K key) {
        int hash = getHash(key);
        for (int i = 0; i < bucket[hash].getEntries().size(); i++) {
            MyKey myKeyValueEntry = bucket[hash].getEntries().get(i);
            if(myKeyValueEntry.getKey().equals(key)) {
                return myKeyValueEntry;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        if(containsKey(key)) {
            MyKey entry = getEntry(key);
            entry.setValue(value);
        } else {
            int hash = getHash(key);
            if(bucket[hash] == null) {
                bucket[hash] = new MyMap();
            }
            bucket[hash].addEntry(new MyKey<>(key, value));
            size++;
        }
    }

    public V get(K key) {
        return containsKey(key) ? (V) getEntry(key).getValue() : null;
    }

    public boolean containsKey(K key) {
        int hash = getHash(key);
        return !(Objects.isNull(bucket[hash]) || Objects.isNull(getEntry(key)));
    }

    public void delete(K key) {
        if(containsKey(key)) {
            int hash = getHash(key);
            bucket[hash].removeEntry(getEntry(key));
            size--;
        }
    }

    public void replace(K key, V val) {
      if(containsKey(key)) {
        getEntry(key).setValue(val);
      }
    }

    public int size() {
        return size;
    }
}
