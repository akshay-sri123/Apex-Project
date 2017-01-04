package Aggregator;

import java.util.Arrays;

public class ByteArrayKeyValPair
{
  private byte[] key;
  private byte[] val;

  public ByteArrayKeyValPair(byte[] key, byte[] val)
  {
    this.key = key;
    this.val = val;
  }

  public byte[] getKey()
  {
    return key;
  }

  public byte[] getVal()
  {
    return val;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ByteArrayKeyValPair that = (ByteArrayKeyValPair)o;

    return Arrays.equals(key, that.key);
  }

  @Override
  public int hashCode()
  {
    return Arrays.hashCode(key);
  }

  @Override
  public String toString()
  {
    return "ByteArrayKeyValPair{" +
      "key=" + Arrays.toString(key) +
      ", val=" + Arrays.toString(val) +
      '}';
  }
}
