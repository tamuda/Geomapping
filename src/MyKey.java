public class MyKey<K, V> {
  private K key;
  private V val;

  public MyKey(K k, V v) {
    key = k;
    val = v;
  }

  public int hashCode(){
    return key.hashCode();
  }

  public boolean equals(Object obj) {
    if(key.equals((K)obj))
      return true;
    return false;
  }

  public Object getKey() {
    return key;
  }

  public Object getValue() {
    return val;
  }

  public void setKey(Object obj) {
    key = (K)obj;
  }

  public void setValue(Object obj) {
    val = (V)val;
  }
}
