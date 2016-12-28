package Aggregator;

import com.apex.AdInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator
{
	protected int byteLen = 0, offset = 0, varoffset = 0;
	Converter converter=new Converter();
	protected  byte[] byteKeys;
	protected  byte[] byteMetrics;
	protected final String[] keys;
	protected final String[] metrics;
	private final int id;
	private Map<List, List> aggMap = new HashMap<>();
	private Map<byte[], List> byteMap = new HashMap<>();
	
	public Aggregator(String[] keys, String[] metrics, int id) {
		this.keys = keys;
		this.metrics = metrics;
		this.id = id;
	}
	
	public void add(AdInfo adInfo) throws NoSuchFieldException, IllegalAccessException
	{
    List key = getKey(adInfo);
		//System.out.println(byteKeys);
		//System.out.println(readString());
		List vals = aggMap.get(key);
		if(vals == null) {
      vals = getInitialValues(adInfo);
			aggMap.put(key, vals);
		} else {
		  updateValues(vals, adInfo);
    }
  }

  private void updateValues(List vals, AdInfo adInfo)
  {
  	/*
  	0->cost
  	1->clicks
  	2->impressions
  	3->frequency
  	 */
    vals.set(0, (Double)vals.get(0) + adInfo.getCost());
    vals.set(1, (Integer)vals.get(1) + adInfo.getImpressions());
    vals.set(2, (Integer)vals.get(2) + (adInfo.isClicks() ? 1: 0));
    vals.set(3, (Integer)vals.get(3) + 1);
  }

  private List getInitialValues(AdInfo adInfo)
  {
    List vals;
    vals = new ArrayList();
    //0->cost, 1->impressions, 2->click, 3->frequency
    vals.add(adInfo.getCost());
    vals.add(adInfo.getImpressions());
    vals.add(adInfo.isClicks() ? 1 : 0);
    vals.add(1);
    return vals;
  }

  private List getKey(AdInfo adInfo) throws NoSuchFieldException, IllegalAccessException {
    List key = new ArrayList();
    key.add(id);
    for(String keyStr : keys)
    {
      if(keyStr.equals("Publisher"))
      {
      	key.add(adInfo.getPublisher());
      }
      else if(keyStr.equals("Location"))
      {
      	key.add(adInfo.getLocation());
      }
      else if(keyStr.equals("Advertiser"))
      {
      	key.add(adInfo.getAdvertiser());
      }
    }
    return key;
  }
  
  public byte[] getKeyBytes(int id, AdInfo adInfo)
  {
    byteKeys = new byte[getRequiredLengthForKeys(1, adInfo)];
  	switch (id)
        {
		  case 1:
			  varoffset = 2;
			  String str = (adInfo.getPublisher()).toString();
			  int len = str.length();
			  byteKeys[1]=(byte)len;
			  byte[] strBytes = str.getBytes();
			  for (int i = 0; i < len; i++) {
				  byteKeys[varoffset + i] = strBytes[i];
			  }
			  varoffset += len;
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

  int getRequiredLengthForKeys(int id, AdInfo adInfo)
  {
        switch (id)
        {
	        case 1:
		        byteLen = 2;
	        	byteLen += ((adInfo.getPublisher()).toString()).length();
	        	break;
	        case 2:
		        byteLen = 2;
		        byteLen += ((adInfo.getLocation()).toString()).length();
		        break;
	        case 3:
		        byteLen = 2;
		        byteLen += ((adInfo.getAdvertiser()).toString()).length();
		        break;
	        case 4:
		        byteLen = 4;
		        byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getLocation()).toString()).length();
		        break;
	        case 5:
		        byteLen = 4;
		        byteLen += ((adInfo.getAdvertiser()).toString()).length() + ((adInfo.getLocation()).toString()).length();
		        break;
	        case 6:
		        byteLen = 4;
		        byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getAdvertiser()).toString()).length();
		        break;
	        case 7:
		        byteLen = 6;
		        byteLen += ((adInfo.getPublisher()).toString()).length() + ((adInfo.getAdvertiser()).toString()).length() +
				        ((adInfo.getLocation()).toString()).length();
		        break;
        }
        
        return byteLen;
  }
  
	public void dump()
	{
		for (Map.Entry entry : aggMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}