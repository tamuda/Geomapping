import java.util.*;
// cite: https://medium.com/swlh/hashmap-implementation-for-java-90a5f58d4a5b
public class MyMap {
  private LinkedList<MyKey> entries;

    public MyMap() {
        if(entries == null) {
            entries = new LinkedList<>();
        }
    }

    public LinkedList<MyKey> getEntries() {
        return entries;
    }

    public void addEntry(MyKey entry) {
        this.entries.add(entry);
    }

    public void removeEntry(MyKey entry) {
        this.entries.remove(entry);
    }
}
