import java.util.*;
public class URPriorityQueue<E extends Comparable<E>> {
  private ArrayList<E> list = new ArrayList<>();

  public void add(E object) {
    list.add(object);
    int index = list.size() - 1;
    heapifyUp(index);
  }

  public E remove() {
    E object = list.get(0);
    int lastIndex = list.size() -  1;
    list.set(0, list.get(lastIndex));
    list.remove(lastIndex);
    if(list.size() > 0)
      heapifyDown(0);
    return object;
  }

  public int size() {
    return list.size();
  }

  public boolean contains(E object) {
    return list.contains(object);
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public void heapifyUp(int index) {
    while(index > 0) {
      int parent = (index - 1)/2;
      if(list.get(index).compareTo(list.get(parent)) < 0) {
        E temp = list.get(index);
        list.set(index, list.get(parent));
        list.set(parent, temp);
      }
      else
        return;
    }
  }

  public void heapifyDown(int index) {
    int leftChildIndex = 2 * index + 1;
    int rightChildIndex = 2 * index + 2;
    E val = list.get(index);
    while(leftChildIndex < list.size()) {
      E minVal = val;
      int minIndex = index;
      boolean smaller = false;
      if(list.get(leftChildIndex).compareTo(minVal) < 0) {
        minVal = list.get(leftChildIndex);
        minIndex = leftChildIndex;
        smaller = true;
      }
      if(rightChildIndex < size() && list.get(rightChildIndex).compareTo(minVal) < 0) {
        minVal = list.get(rightChildIndex);
        minIndex = rightChildIndex;
        smaller = true;
      }
      if(smaller) {
        list.set(index, minVal);
        list.set(minIndex, val);
        index = minIndex;
        leftChildIndex = 2 * index + 1;
        rightChildIndex = 2 * index + 2;
      }
      else
        return;
    }
  }
}
