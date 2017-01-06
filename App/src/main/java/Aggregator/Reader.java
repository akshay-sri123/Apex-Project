package Aggregator;

import com.apex.AdInfo;
import com.opencsv.CSVReader;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Akshay on 1/6/2017.
 */
public class Reader
{
	public void reader(CSVReader csvReader, AdInfo adInfo)throws IOException
	{
		String[] nextLine;
		
		while((nextLine = csvReader.readNext()) != null)
		{
			
			if(nextLine != null)
			{
				adInfo.setPublisher(nextLine[0]);
				adInfo.setAdvertiser(nextLine[1]);
				adInfo.setLocation((nextLine[2]));
				adInfo.setCost(Long.parseLong(nextLine[3]));
				adInfo.setImpressions((Long.parseLong(nextLine[4])));
				adInfo.setClicks(Boolean.parseBoolean(nextLine[5]));
			}
		}
	}
}
