package Aggregator;

/**
 * Created by akshay on 23/12/16.
 */
public class idFactory
{
	public String[] getKey(int id)
	{
		String[] keys = null;
		if(id==1)
		{
			keys = new String[]{"Publisher"};
		}
		else if(id == 2)
		{
			keys = new String[]{"Location"};
		}
		else if(id == 3)
		{
			keys = new String[]{"Advertiser"};
		}
		else if(id == 4)
		{
			keys = new String[]{"Publisher","Location"};
		}
		else if(id == 5)
		{
			keys = new String[]{"Advertiser","Location"};
		}
		else if(id == 6)
		{
			keys = new String[]{"Publisher", "Advertiser"};
		}
		else if(id == 7)
		{
			keys = new String[]{"Publisher", "Advertiser", "Location"};
		}
		return keys;
	}
}
