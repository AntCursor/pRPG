package types;

import java.util.Iterator;
import java.util.Optional;

public class List<T> implements Iterable<T> {
  private T item = null;
  private Optional<List<T>> nList = Optional.empty();

  public List() {
  }

  public List(T item, Optional<List<T>> next) {
    this.item = item;
    this.nList = next;
  }

  public T get() {
    return item;
  }

  public Optional<List<T>> next() {
    return nList;
  }

  public List<T> add(T item) {
    if (this.item == null) {
      this.item = item;
      return this;
    }

    List<T> last = this;
    while (last.nList.isPresent()) {
      last = last.nList.get();
    }

    last.nList = Optional.of(new List<T>(item, Optional.empty()));
    return last.nList.get();
  }

  @Override
  public Iterator<T> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<T> {
    private Optional<List<T>> current = Optional.of(List.this);

    @Override
    public boolean hasNext() {
      return current.isPresent() && current.get().item != null;
    }

    @Override
    public T next() {
      List<T> node = current.get();
      T out = node.item;
      current = node.next();

      return out;
    }

  }
}
