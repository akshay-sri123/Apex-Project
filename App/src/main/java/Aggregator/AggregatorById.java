package Aggregator;

import com.apex.AdInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator_v2
{
	private final int id;
	private Map<List, List> aggMap = new HashMap<>();
	idFactory idFactory = new idFactory();
	
	public Aggregator_v2(int id) {
		this.id = id;

	}
	
	public Pair<List, List> getGroup(AdInfo adInfo)
	{
		String[] keys = idFactory.getKey(id);
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
		
		List vals = aggMap.get(key);
		if(vals == null)
		{
			vals = new ArrayList();
			//0->cost, 1->impressions, 2->click
			vals.add(adInfo.getCost());
			vals.add(adInfo.getImpressions());
			vals.add(adInfo.isClicks() ? 1 : 0);
			aggMap.put(key, vals);
		}
		
		return new Pair<List, List>(key, vals);
	}
	
	public void COUNT(List vals, AdInfo adInfo)
	{
		vals.set(0, (Double)vals.get(0) + adInfo.getCost());
		vals.set(1, (Integer)vals.get(1) + adInfo.getImpressions());
		vals.set(2, (Integer)vals.get(2) + (adInfo.isClicks() ? 1: 0));
	}
}
