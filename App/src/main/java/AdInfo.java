import com.apex.Advertiser;
import com.apex.Location;
import com.apex.Publisher;

/**
 * Created by Akshay on 12/21/2016.
 */

public class AdInfo
{
	public Publisher publisher;
	public Advertiser advertiser;
	public Location location;
	
	public long cost;
	public long impressions;
	public boolean clicks;
	
	public AdInfo(Publisher publisher, Advertiser advertiser, Location location, long cost, long impressions, boolean clicks) {
		this.publisher = publisher;
		this.advertiser = advertiser;
		this.location = location;
		this.cost = cost;
		this.impressions = impressions;
		this.clicks = clicks;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	public Advertiser getAdvertiser() {
		return advertiser;
	}
	
	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public long getCost() {
		return cost;
	}
	
	public void setCost(long cost) {
		this.cost = cost;
	}
	
	public long getImpressions() {
		return impressions;
	}
	
	public void setImpressions(long impressions) {
		this.impressions = impressions;
	}
	
	public boolean isClicks() {
		return clicks;
	}
	
	public void setClicks(boolean clicks) {
		this.clicks = clicks;
	}
	
	@Override
	public String toString() {
		return "AdInfo{" +
				"publisher=" + publisher +
				", advertiser=" + advertiser +
				", location=" + location +
				", cost=" + cost +
				", impressions=" + impressions +
				", clicks=" + clicks +
				'}';
	}
}

