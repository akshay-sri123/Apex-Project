package Aggregator;

import com.apex.AdInfo;
import com.apex.Advertiser;
import com.apex.Location;
import com.apex.Publisher;
import com.apex.RandomEnumGenerator;
import com.apex.RandomValueGenerator;

public class Generator_v2
{
	private static RandomEnumGenerator<Publisher> randomPublisher = new RandomEnumGenerator<Publisher>(Publisher.class);
	private static RandomEnumGenerator<Advertiser> randomAdvertiser = new RandomEnumGenerator<Advertiser>(Advertiser.class);
	private static RandomEnumGenerator<Location> randomLocation = new RandomEnumGenerator<Location>(Location.class);
	private static RandomValueGenerator randomValueGenerator = new RandomValueGenerator();
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		AggregatorSet aggregatorSet = new AggregatorSet();
		Converter converter = new Converter(1);
//		for (int i = 1; i <= 7; i++) {
//			AggregatorById aggregator = new AggregatorById(i);
//		    aggregatorSet.addAggregator(aggregator);
//		}
		
		AggregatorById aggregator = new AggregatorById(1);
		aggregatorSet.addAggregator(aggregator);
		
		long startTime = System.currentTimeMillis();
		long currentTime = 0;
		while((currentTime - startTime) != (1))
		{
			AdInfo adInfo = new AdInfo(randomPublisher.random().toString(), randomAdvertiser.random().toString(), randomLocation.random().toString(),
					randomValueGenerator.randomCost(), randomValueGenerator.randomImpressions(), randomValueGenerator.randomClicks());
			aggregatorSet.processItem(adInfo);
//			System.out.println(aggregator.getRequiredLengthForKeys(1, adInfo));
//			System.out.println(aggregator.getKeyBytes(1, adInfo));
//			System.out.println(aggregator.readString());
			System.out.println(converter.getRequiredLengthForKeys(1, adInfo));
			converter.getKeyBytes(1,adInfo);
			currentTime = System.currentTimeMillis();
		}

    for (Aggregator aggr : aggregatorSet.getAggregatorList()) {
		  aggr.dump();
    }
	}
}

