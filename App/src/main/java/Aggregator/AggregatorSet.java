package Aggregator;

import com.apex.AdInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 23/12/16.
 */
public class AggregatorSet
{
	List<Aggregator> aggregatorList = new ArrayList<>();
	
	void addAggregator(Aggregator aggregator)
	{
		aggregatorList.add(aggregator);
	}
	
	void processItem(AdInfo adInfo)
	{
		for(Aggregator aggr : aggregatorList)
		{
			Pair<List, List> current = aggr.getGroup(adInfo);
			System.out.println(current.toString());
			aggr.COUNT(current.getSecond(), adInfo);
		}
	}
}
