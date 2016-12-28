package Aggregator;

/**
 * Created by akshay on 28/12/16.
 */
public class Converter
{
	private int offset=0;
	private int varoffset=0;
	
	byte[] writeString(byte[] keys, String str)throws NoSuchFieldException, IllegalAccessException
	{
		writeInt(keys, varoffset);
		offset+=1;
		
		keys[offset]=(byte)str.length();
		
		byte[] strBytes=str.getBytes();
		
		for(int i=0;i<strBytes.length;i++)
		{
			keys[i+varoffset]=strBytes[i];
		}
		varoffset += strBytes.length;
		
		return  keys;
	}
	
	void writeInt(byte[] bytes, int val)
	{
		bytes[offset+0] = (byte)(val & 0xFF);
		bytes[offset+1] = (byte)((val & 0xFF00) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000) >> 24);
	}
	
}
