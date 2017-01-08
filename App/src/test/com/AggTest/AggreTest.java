package com.AggTest;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

/**
 * Created by Akshay on 1/6/2017.
 */
public class AggreTest
{
	static int offset = 0, revoffset =0;
	static byte[] writeLong(byte[] bytes, long val)
	{
		bytes[offset+0] = (byte)(val & 0xFFL);
		bytes[offset+1] = (byte)((val & 0xFF00L) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000L) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000L) >> 24);
		bytes[offset+4] = (byte)((val & 0xFF00000000L) >> 32);
		bytes[offset+5] = (byte)((val & 0xFF0000000000L) >> 40);
		bytes[offset+6] = (byte)((val & 0xFF000000000000L) >> 48);
		bytes[offset+7] = (byte)((val & 0xFF00000000000000L) >> 56);
		
		return bytes;
	}
	
	public static long readLong(byte[] bytes)
	{
		long val=(((long) bytes[revoffset+0] & 0xFF))|
				(((long)bytes[revoffset+1] & 0xFF) << 8)|
				(((long)bytes[revoffset+2] & 0xFF) << 16)|
				(((long)bytes[revoffset+3] & 0xFF) << 24)|
				(((long)bytes[revoffset+4] & 0xFF) << 32)|
				(((long)bytes[revoffset+5] & 0xFF) << 40)|
				(((long)bytes[revoffset+6] & 0xFF) << 48)|
				(((long)bytes[revoffset+7] & 0xFF) << 54);
		return val;
	}
	
	public static void main(String[] args) {
		long number = 13L;
		
		byte[] longNum = new byte[8];
		longNum = writeLong(longNum, number);
		System.out.println(Arrays.toString(longNum));
	}
}
