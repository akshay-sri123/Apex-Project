package Aggregator;

import com.apex.AdInfo;
import com.sun.org.apache.bcel.internal.generic.INEG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator
{
	protected final String[] keys;
	protected final String[] metrics;
	private final int id;
	private Map<List, List> aggMap = new HashMap<>();
	
	public Aggregator(String[] keys, String[] metrics, int id) {
		this.keys = keys;
		this.metrics = metrics;
		this.id = id;
	}
	
	public void add(AdInfo adInfo)
	{
    List key = getKey(adInfo);
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
    vals.set(0, (Double)vals.get(0) + adInfo.getCost());
    vals.set(1, (Integer)vals.get(1) + adInfo.getImpressions());
    vals.set(2, (Integer)vals.get(2) + (adInfo.isClicks() ? 1: 0));
  }

  private List getInitialValues(AdInfo adInfo)
  {
    List vals;
    vals = new ArrayList();
    //0->cost, 1->impressions, 2->click
    vals.add(adInfo.getCost());
    vals.add(adInfo.getImpressions());
    vals.add(adInfo.isClicks() ? 1 : 0);
    return vals;
  }

  private List getKey(AdInfo adInfo)
  {
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

	public void dump()
	{
		for (Map.Entry entry : aggMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}