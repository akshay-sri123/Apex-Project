package Aggregator;

import com.apex.AdInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 23/12/16.
 */
public class AggregatorSet_v2
{
	List<Aggregator_v2> aggregatorList = new ArrayList<>();
	
	void addAggregator_v2(Aggregator_v2 aggregator_v2)
	{
		aggregatorList.add(aggregator_v2);
	}
	
	void processItem(AdInfo adInfo)
	{
		for(Aggregator_v2 aggr : aggregatorList)
		{
			Pair<List, List> current = aggr.getGroup(adInfo);
			System.out.println(current.toString());
			aggr.COUNT(current.getSecond(), adInfo);
		}
	}
}