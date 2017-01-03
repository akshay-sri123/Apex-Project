package Aggregator;

import com.apex.AdInfo;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by akshay on 28/12/16.
 */
public class Converter
{
	protected int offset = 0, varoffset = 0, id = 0;
	protected  byte[] byteKeys;
	protected  byte[] byteMetrics;
	
	public Converter(int id)
	{
		this.id = id;
	}
	
	/*
	  KEY ID LIST
	  1->Publisher
	  2->Location
	  3->Advertiser
	  4->Publisher, Location
	  5->Advertiser, Location
	  6->Publisher, Advertiser
	  7->Publisher, Advertiser, Location
	   */
	  
	  /*
	    BYTE ARRAY STORAGE FORMAT
	   _____________________________________________________
	  |offset1|len1|offset2|len2|offset3|len3|val1|val2|val3|
	   -----------------------------------------------------
		|           |              |          ^     ^   ^
		|___________|______________|__________|	    |   |
	                |______________|________________|   |
	                               |____________________|
	 
	     
	     4 bytes - offset
	     1 bytes - length
	     variable bytes - string length
	   */
	
	int getRequiredLengthForKeys(int id, AdInfo adInfo) {
		int byteLen = 0;
		switch (id) {
			case 1:
				byteLen += 5;
				byteLen += ((adInfo.getPublisher()).toString()).length();
				break;
			case 2:
				byteLen = 5;
				byteLen += ((adInfo.getLocation()).toString()).length();
				break;
			case 3:
				byteLen = 5;
				byteLen += ((adInfo.getAdvertiser()).toString()).length();
				break;
			case 4:
				byteLen = 10;
				byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getLocation()).toString()).length();
				break;
			case 5:
				byteLen = 10;
				byteLen += ((adInfo.getAdvertiser()).toString()).length() + ((adInfo.getLocation()).toString()).length();
				break;
			case 6:
				byteLen = 10;
				byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getAdvertiser()).toString()).length();
				break;
			case 7:
				byteLen = 15;
				byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getAdvertiser()).toString()).length() +
						((adInfo.getLocation()).toString()).length();
				break;
		}
		return byteLen;
	}
	
	public int calcVarOffset(int id)
	{
		switch (id)
		{
			case 1:
				varoffset += 5;
				break;
			case 2:
				varoffset += 5;
				break;
			case 3:
				varoffset += 5;
				break;
			case 4:
				varoffset += 10;
				break;
			case 5:
				varoffset += 10;
				break;
			case 6:
				varoffset += 10;
				break;
			case 7:
				varoffset += 15;
				break;
		}
		return varoffset;
	}
	
	public void writeInt(byte[] bytes, int val)
	{
		bytes[offset+0] = (byte)(val & 0xFF);
		bytes[offset+1] = (byte)((val & 0xFF00) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000) >> 24);
	}
	
	public void writeString(byte[] bytes, String str)
	{
		writeInt(bytes, varoffset);
		offset+=1;
		
		bytes[offset]=(byte)str.length();
		
		byte[] strBytes=str.getBytes();
		
		for(int i=0;i<strBytes.length;i++)
		{
			bytes[i + varoffset] = strBytes[i];
		}
		varoffset += strBytes.length;
	}
	
	public String readString()
	{
		String str="";
		int len = (int)byteKeys[1];
		for(int i=0;i<len;i++)
		{
			str += (char)byteKeys[1+i];
		}
		return str;
	}
	
	public void getKeyBytes(int id, AdInfo adInfo)
	{
		String str = null;
		varoffset = offset = 0;
		varoffset = calcVarOffset(id);
		byteKeys = new byte[getRequiredLengthForKeys(1, adInfo)];
		switch (id)
		{
			case 1:
				str = (adInfo.getPublisher()).toString();
				writeString(byteKeys, str);
				System.out.print("Generated byte[] is : " + byteKeys.length+"\n");
				System.out.println(Hex.encodeHexString(byteKeys));
				System.out.println();
				
				/*
					10
					Generated byte[] is : 10
					550008965727979
					[1, YAHOO] [4.5, 3, 0, 1]
				 */
				break;
			
			case 2:
				str = (adInfo.getLocation()).toString();
				writeString(byteKeys, str);
				System.out.print("Generated byte[] is : " + byteKeys.length+"\n");
				System.out.println(Hex.encodeHexString(byteKeys));
				System.out.println();
				break;
			
			
			case 3:
				str = (adInfo.getAdvertiser()).toString();
				writeString(byteKeys, str);
				System.out.print("Generated byte[] is : " + byteKeys.length+"\n");
				System.out.println(Hex.encodeHexString(byteKeys));
				System.out.println();
				break;
			
			case 4:
				break;
			
		}
	}
	
	
	
	
}
