package Aggregator;

import com.apex.*;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class Generator_v2
{
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
		
		CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\Akshay\\Documents\\InputFile.csv"), ',');
		
		long startTime = System.currentTimeMillis();
		long currentTime = 0, timeDifference = 0;
		
		String[] nextLine;
		
		while((nextLine = csvReader.readNext()) != null)
		{
			AdInfo adInfo = new AdInfo(null, null, null, 0l, 0l, false);
			if(nextLine != null)
			{
				adInfo.setPublisher(nextLine[0]);
				adInfo.setAdvertiser(nextLine[1]);
				adInfo.setLocation((nextLine[2]));
				adInfo.setCost(Long.parseLong(nextLine[3]));
				adInfo.setImpressions((Long.parseLong(nextLine[4])));
				adInfo.setClicks(Boolean.parseBoolean(nextLine[5]));
				
				aggregatorSet.processItem(adInfo);
			}
		}
		
		for(Aggregator aggr : aggregatorSet.getAggregatorList())
		{
			aggr.dump();
		}
	}
	
}

