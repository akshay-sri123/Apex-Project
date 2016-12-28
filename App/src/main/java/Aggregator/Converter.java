package Aggregator;

import com.apex.AdInfo;

/**
 * Created by akshay on 28/12/16.
 */
public class Converter
{
	protected int byteLen = 0, offset = 0, varoffset = 0;
	protected  byte[] byteKeys;
	protected  byte[] byteMetrics;
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
	   
	   */
	
	int getRequiredLengthForKeys(int id, AdInfo adInfo) {
		switch (id) {
			case 1:
				byteLen += 8;
				byteLen += ((adInfo.getPublisher()).toString()).length();
				break;
			case 2:
				byteLen = 8;
				byteLen += ((adInfo.getLocation()).toString()).length();
				break;
			case 3:
				byteLen = 8;
				byteLen += ((adInfo.getAdvertiser()).toString()).length();
				break;
			case 4:
				byteLen = 16;
				byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getLocation()).toString()).length();
				break;
			case 5:
				byteLen = 16;
				byteLen += ((adInfo.getAdvertiser()).toString()).length() + ((adInfo.getLocation()).toString()).length();
				break;
			case 6:
				byteLen = 16;
				byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getAdvertiser()).toString()).length();
				break;
			case 7:
				byteLen = 24;
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
				varoffset += 8;
				break;
			case 2:
				varoffset += 8;
				break;
			case 3:
				varoffset += 8;
				break;
			case 4:
				varoffset += 16;
				break;
			case 5:
				varoffset += 16;
				break;
			case 6:
				varoffset += 16;
				break;
			case 7:
				varoffset += 24;
				break;
		}
	}
	
	public byte[] getKeyBytes(int id, AdInfo adInfo)
	{
		byte[] strBytes = null;
		byteKeys = new byte[getRequiredLengthForKeys(1, adInfo)];
		switch (id)
		{
			case 1:
				varoffset = calcVarOffset(id);
				String str = (adInfo.getPublisher()).toString();
				int len = str.length();
				byteKeys[1]=(byte)len;
				strBytes = str.getBytes();
				for (int i = 0; i < len; i++) {
					byteKeys[varoffset + i] = strBytes[i];
				}
				varoffset += len;
				break;
			
			case 2:
				varoffset = 2;
				String str = (adInfo.getLocation()).toString();
				int len = str.length();
				byteKeys[1]=(byte)len;
				strBytes = str.getBytes();
				for (int i = 0; i < len; i++) {
					byteKeys[varoffset + i] = strBytes[i];
				}
				varoffset += len;
				break;
			
			
			case 3:
				varoffset = 2;
				String str = (adInfo.getAdvertiser()).toString();
				int len = str.length();
				byteKeys[1]=(byte)len;
				strBytes = str.getBytes();
				for (int i = 0; i < len; i++) {
					byteKeys[varoffset + i] = strBytes[i];
				}
				varoffset += len;
				break;
			
			case 4:
				varoffset = 4;
				break;
			
			
			
		}
		return byteKeys;
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
	
	
	
}
