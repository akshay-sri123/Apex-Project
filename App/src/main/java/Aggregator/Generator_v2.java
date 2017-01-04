package Aggregator;

import com.apex.*;

import java.io.IOException;

public class Generator_v2
{
	private static RandomEnumGenerator<Publisher> randomPublisher = new RandomEnumGenerator<Publisher>(Publisher.class);
	private static RandomEnumGenerator<Advertiser> randomAdvertiser = new RandomEnumGenerator<Advertiser>(Advertiser.class);
	private static RandomEnumGenerator<Location> randomLocation = new RandomEnumGenerator<Location>(Location.class);
	private static RandomValueGenerator randomValueGenerator = new RandomValueGenerator();
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
		long seconds;
		if (args.length >= 1) {
			seconds = Long.parseLong(args[0]);
		} else {
			seconds = 300;
		}

		AggregatorSet aggregatorSet = new AggregatorSet();
		Converter converter = new Converter();
//		for (int i = 1; i <= 7; i++) {
//			AggregatorById aggregator = new AggregatorById(i);
//		    aggregatorSet.addAggregator(aggregator);
//		}
		
		AggregatorById aggregator = new AggregatorById(1);
		aggregatorSet.addAggregator(aggregator);
		
		long startTime = System.currentTimeMillis();
		long currentTime = 0, timeDifference = 0;
		do
		{
			AdInfo adInfo = new AdInfo(randomPublisher.random().toString(), randomAdvertiser.random().toString(), randomLocation.random().toString(),
					randomValueGenerator.randomCost(), randomValueGenerator.randomImpressions(), randomValueGenerator.randomClicks());
			aggregatorSet.processItem(adInfo);
//			System.out.println(aggregator.getRequiredLengthForKeys(1, adInfo));
//			System.out.println(aggregator.getKeyBytes(1, adInfo));
//			System.out.println(aggregator.readString());
			
			//System.out.println(converter.getRequiredLengthForKeys(1, adInfo));
			//converter.getKeyBytes(4,adInfo);
			//System.out.println(adInfo.toString());
			//converter.getValueBytes(adInfo);
			currentTime = System.currentTimeMillis();
			timeDifference = currentTime - startTime;
		} while(timeDifference < (seconds * 1000));

    for (Aggregator aggr : aggregatorSet.getAggregatorList()) {
		  aggr.dump();
    }
	}
}

