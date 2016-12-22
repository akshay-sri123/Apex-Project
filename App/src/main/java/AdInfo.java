import java.io.Serializable;

/**
 * Created by Akshay on 12/21/2016.
 */

public class AdInfo
{
	public Publisher publisher;
	public Advertiser advertiser;
	public Location location;
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public AdInfo(Publisher publisher, Advertiser advertiser, Location location) {
		this.publisher = publisher;
		this.advertiser = advertiser;
		this.location = location;
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
	
	@Override
	public String toString() {
		return "AdInfo{" +
				"publisher=" + publisher +
				", advertiser=" + advertiser +
				", location=" + location +
				'}';
	}
}

