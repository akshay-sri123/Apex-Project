import java.util.*;

/**
 * Created by Akshay on 12/21/2016.
 */
public class Generator
{
	private static RandomEnumGenerator<Publisher> randomPublisher = new RandomEnumGenerator<Publisher>(Publisher.class);
	private static RandomEnumGenerator<Advertiser> randomAdvertiser = new RandomEnumGenerator<Advertiser>(Advertiser.class);
	private static RandomEnumGenerator<Location> randomLocation = new RandomEnumGenerator<Location>(Location.class);
	
	public static void main(String[] args) {
		List<AdInfo> adList = new ArrayList<AdInfo>();
		
		for (int i = 0; i < 10; i++) {
			AdInfo adInfo = new AdInfo(randomPublisher.random(), randomAdvertiser.random(), randomLocation.random());
			adList.add(adInfo);
		}
		Aggregator aggregator = new Aggregator();
		
		aggregator.DISPLAY(adList);
		//aggregator.COUNT_BY_PUBLISHER(adList, randomPublisher.random());
		//aggregator.GROUP_BY_LOCATION(adList, randomLocation.random());
		//aggregator.GROUP_BY_PUBLISHER(adList, randomPublisher.random());
		
		aggregator.GROUP_BY_LOCATION_PUBLISHER(adList, randomPublisher.random(), randomLocation.random());
	}
}
