package Aggregator;

import java.util.ArrayList;
import java.util.List;


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
