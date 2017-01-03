package Aggregator;

import com.apex.AdInfo;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by akshay on 28/12/16.
 */
public class Converter
{
	protected int offset = 0, varoffset = 0, id = 0, revoffset = 0;
	protected  byte[] byteKeys;
	protected  byte[] byteMetrics;

	
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
	
	int getRequiredLengthforValues()
	{
		return (8+8+1);
	}
	
	public int calcKeyVarOffset(int id)
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
	
	public int readInteger(byte[] bytes) throws NoSuchFieldException, IllegalAccessException
	{
		int val=(((int)bytes[revoffset+0]) << 0)|
				(((int)bytes[revoffset+1]) << 8)|
				(((int)bytes[revoffset+2]) << 16)|
				(((int)bytes[revoffset+3]) << 24);
		
		return val;
	}
	
	void writeLong(byte[] bytes, long val)
	{
		bytes[offset+0] = (byte)(val & 0xFFL);
		bytes[offset+1] = (byte)((val & 0xFF00L) >> 8);
		bytes[offset+2] = (byte)((val & 0xFF0000L) >> 16);
		bytes[offset+3] = (byte)((val & 0xFF000000L) >> 24);
		bytes[offset+4] = (byte)((val & 0xFF00000000L) >> 32);
		bytes[offset+5] = (byte)((val & 0xFF0000000000L) >> 40);
		bytes[offset+6] = (byte)((val & 0xFF000000000000L) >> 48);
		bytes[offset+7] = (byte)((val & 0xFF00000000000000L) >> 56);
	}
	
	public long readLong(byte[] bytes)
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
	
	void writeBoolean(byte[] bytes, boolean bool)
	{
		if(bool==true)
		{
			bytes[offset]=1;
		}
		else {
			bytes[offset]=0;
		}
	}
	
	public boolean readBoolean(byte[] bytes)
	{
		boolean bool;
		if((int)bytes[revoffset]==1)
		{
			bool=true;
		}
		else
		{
			bool=false;
		}
		return bool;
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

	  BYTE ARRAY STORAGE FORMAT
	 _____________________________________________________
	|offset1|len1|offset2|len2|offset3|len3|val1|val2|val3|
	 -----------------------------------------------------
	  |           |              |          ^     ^   ^
	  |___________|______________|__________|	  |   |
				  |______________|________________|   |
								 |____________________|
   
	   
	   4 bytes - offset
	   1 bytes - length
	   variable bytes - string length
	 */
	public byte[] getKeyBytes(int id, AdInfo adInfo)
	{
		String str = null, str2 = null, str3 = null;
		varoffset = offset = 0;
		varoffset = calcKeyVarOffset(id);
		byteKeys = new byte[getRequiredLengthForKeys(id , adInfo)];
		switch (id)
		{
			case 1:
				str = (adInfo.getPublisher()).toString();
				writeString(byteKeys, str);
				break;
			
			case 2:
				str = (adInfo.getLocation()).toString();
				writeString(byteKeys, str);
				break;
			
			
			case 3:
				str = (adInfo.getAdvertiser()).toString();
				writeString(byteKeys, str);
				break;
			
			case 4:
				str = (adInfo.getPublisher()).toString();
				str2 = (adInfo.getLocation()).toString();
				writeString(byteKeys, str);
				writeString(byteKeys, str2);
				break;
				
			case 5:
				str = (adInfo.getAdvertiser()).toString();
				str2 = (adInfo.getLocation()).toString();
				writeString(byteKeys, str);
				writeString(byteKeys, str2);
				break;
				
			case 6:
				str = (adInfo.getPublisher()).toString();
				str2 = (adInfo.getAdvertiser()).toString();
				writeString(byteKeys, str);
				writeString(byteKeys, str2);
				break;
				
			case 7:
				str = (adInfo.getPublisher()).toString();
				str2 = (adInfo.getAdvertiser()).toString();
				str3 = (adInfo.getLocation()).toString();
				writeString(byteKeys, str);
				writeString(byteKeys, str2);
				writeString(byteKeys, str3);
				break;
		}
		return byteKeys;
	}
	
	/*
	  VALUE ID LIST
	  1->long cost
	  2->long impressions
	  3->boolean Clicks
	
	  BYTE ARRAY STORAGE FORMAT
	 ______________
	|val1|val2|val3|
	 --------------
	  8    8    1
	   
	   8 bytes - cost
	   8 bytes - impressions
	   1 bytes - click
	 */
	
	public byte[] getValueBytes(AdInfo adInfo)
	{
		offset = 0;
		byteMetrics = new byte[getRequiredLengthforValues()];
		long cost = adInfo.getCost();
		long impressions = adInfo.getImpressions();
		boolean clicks = adInfo.isClicks();
		
		writeLong(byteMetrics, cost);
		offset += 8;
		writeLong(byteMetrics, impressions);
		offset +=8;
		writeBoolean(byteMetrics, clicks);
		offset += 1;
		
		return byteMetrics;
	}
	
	public void updateByteValues(byte[] valBytes, AdInfo adInfo)
	{
		revoffset = offset = 0;
		long cost, impressions;
		boolean clicks;
		cost = readLong(valBytes);
		revoffset += 8;
		impressions = readLong(valBytes);
		revoffset += 8;
		clicks = readBoolean(valBytes);
		revoffset += 1;
		
		cost += adInfo.getCost();
		impressions += adInfo.getImpressions();
		//clicks += (adInfo.isClicks() ? 1 : 0);
		
		writeLong(byteMetrics, cost);
		offset += 8;
		writeLong(byteMetrics, impressions);
		offset +=8;
		writeBoolean(byteMetrics, clicks);
		offset += 1;
		//System.out.println("Cost : "+cost+" , Impressions : "+impressions+" , Clicks : "+clicks);
	}
	
}
