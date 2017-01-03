package Aggregator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 1/3/2017.
 */
public class ConverterSet
{
	List<Converter> converterList = new ArrayList<Converter>();
	
	void addConverter(Converter converter)
	{
		converterList.add(converter);
	}
	
	public List<Converter> getConverterList() {
		return converterList;
	}
}
