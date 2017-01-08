package com.apex;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import Aggregator.Converter;

public class IntSerializationTests
{

  @Test
  public void testSerialization()
  {
    byte[] arr = new byte[8];
    Random rand = new Random();
    for (int i = 0; i < 100; i++) {
      long val = rand.nextLong();
      Converter conv = new Converter();
      conv.writeLong(arr, val);
      System.out.println(Arrays.toString(arr));

      long val2 = conv.readLong(arr);
      System.out.println("read value is val " + val + " val2 " + val2);
      Assert.assertEquals("value wrote and read should be same ", val, val2);
    }
  }
}
