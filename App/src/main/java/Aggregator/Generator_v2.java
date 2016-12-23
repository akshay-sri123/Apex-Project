package Aggregator;

import com.apex.*;

import java.util.Random;

/**
 * Created by akshay on 23/12/16.
 */
public class Generator_v2
{
	private static Random random = new Random();
	private static RandomEnumGenerator<Publisher> randomPublisher = new RandomEnumGenerator<Publisher>(Publisher.class);
	private static RandomEnumGenerator<Advertiser> randomAdvertiser = new RandomEnumGenerator<Advertiser>(Advertiser.class);
	private static RandomEnumGenerator<Location> randomLocation = new RandomEnumGenerator<Location>(Location.class);
	private static RandomValueGenerator randomValueGenerator = new RandomValueGenerator();
	
	public static void main(String[] args)
	{
		int id = random.nextInt(7)+1;
		
		Aggregator_v2 aggregator_v2 = new Aggregator_v2(id);
		AggregatorSet_v2 aggregatorSet_v2 = new AggregatorSet_v2();
		aggregatorSet_v2.addAggregator_v2(aggregator_v2);
		
		for(int i=0;i<10;i++)
		{
			AdInfo adInfo = new AdInfo(randomPublisher.random().toString(), randomAdvertiser.random().toString(), randomLocation.random().toString(),
					randomValueGenerator.randomCost(), randomValueGenerator.randomImpressions(), randomValueGenerator.randomClicks());
			aggregatorSet_v2.processItem(adInfo);
		}
	}
}
