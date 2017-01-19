package Aggregator;

import com.apex.AdInfo;
import com.opencsv.CSVReader;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Reader
{
	public void reader(CSVReader csvReader,AggregatorSet aggregatorSet) throws IOException, NoSuchFieldException, IllegalAccessException {
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
	}
}
