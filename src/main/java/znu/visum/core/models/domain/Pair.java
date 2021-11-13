package znu.visum.core.models.domain;

import java.util.Objects;

/**
 * Represents a pair of values. Should be a domain model only but can be found in application too,
 * because it's more convenient.
 */
public class Pair<K, V> {
  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Pair)) return false;
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(getKey(), pair.getKey()) && Objects.equals(getValue(), pair.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey(), getValue());
  }
}
