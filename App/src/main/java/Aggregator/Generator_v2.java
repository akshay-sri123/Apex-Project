package Aggregator;

import com.apex.*;
import java.io.*;
import com.opencsv.CSVReader;

import java.io.FileReader;
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
			seconds = 5;
		}

		AggregatorSet aggregatorSet = new AggregatorSet();
		Converter converter = new Converter();
		for (int i = 1; i <= 7; i++) {
			AggregatorById aggregator = new AggregatorById(i);
		    aggregatorSet.addAggregator(aggregator);
		}
		
		//CSVReader csvReader = new CSVReader(new FileReader("/home/vishal/Documents/InputFile.csv"), ',');
		
		long startTime = System.currentTimeMillis();
		long currentTime = 0, timeDifference = 0;
		
		String[] nextLine;
		
//		while((nextLine = csvReader.readNext()) != null)
//		{
//			AdInfo adInfo = new AdInfo(null, null, null, 0l, 0l, false);
//			if(nextLine != null)
//			{
//				adInfo.setPublisher(nextLine[0]);
//				adInfo.setAdvertiser(nextLine[1]);
//				adInfo.setLocation((nextLine[2]));
//				adInfo.setCost(Long.parseLong(nextLine[3]));
//				adInfo.setImpressions((Long.parseLong(nextLine[4])));
//				adInfo.setClicks(Boolean.parseBoolean(nextLine[5]));
//
//				aggregatorSet.processItem(adInfo);
//			}
//		}
        FileWriter filewriter=new FileWriter("/home/vishal/Documents/Inputfile.csv");
		do
		{
			AdInfo adInfo = new AdInfo(randomPublisher.random().toString(), randomAdvertiser.random().toString(), randomLocation.random().toString(),
					randomValueGenerator.randomCost(), randomValueGenerator.randomImpressions(), randomValueGenerator.randomClicks());
     		aggregatorSet.processItem(adInfo);

     		filewriter.append(adInfo.advertiser);
     		filewriter.append(',');
            filewriter.append(adInfo.publisher);
            filewriter.append(',');
            filewriter.append(adInfo.location);
            filewriter.append(',');
            filewriter.append(Long.toString(adInfo.cost));
            filewriter.append(',');
            filewriter.append(Long.toString(adInfo.impressions));
            filewriter.append(',');
            filewriter.append(Boolean.toString(adInfo.clicks));
            filewriter.append("\n");
			currentTime = System.currentTimeMillis();
			timeDifference = currentTime - startTime;
		} while(timeDifference < (seconds * 1000));
		
		for(Aggregator aggr : aggregatorSet.getAggregatorList())
		{
			aggr.dump();
		}
	}
	
}

