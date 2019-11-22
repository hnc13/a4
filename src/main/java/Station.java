//Blueprint for a Station object
//Each station has an id, name, available bikes, available pedelecs, available docks, a maintenance request,
//capacity, an address, and kiosk information

//Emma Tanur and Grace Bratzel

public class Station
{
	private int id;
	private String name;
	private int bikes;
	private int pedelecs;
	private int avail_docks;
	private int maintainence_req;
	private int capacity;
	private boolean kiosk;
	private String address;

	public Station(int id, String name, int bikes, int pedelecs, int avail_docks, int maintainence_req, int capacity, boolean kiosk, String address){
		this.id = id;
		this.bikes = bikes;
		this.name = name;
		this.pedelecs = pedelecs;
		this.avail_docks = avail_docks;
		this.maintainence_req = maintainence_req;
		this.capacity = capacity;
		this.kiosk = kiosk;
		this.address = address;
	}
	
	public int getBikes(){
		return bikes;
	}
	public int getPeds(){
		return pedelecs;
	}
	public int getAvDocs(){
		return avail_docks;
	}
	public int getMainReq(){
		return maintainence_req;
	}
	public int getCap(){
		return capacity;
	}
	public int getID(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getAddress(){
		return address;
	}
	public boolean getKiosk(){
		return kiosk;
	}
	
	public void setBikes(int bikes){
		this.bikes = bikes;
	}
	public void setPedelecs(int pedelecs){
		this.pedelecs = pedelecs;
	}
	public void setDocks(int docks){
		this.avail_docks = docks;
	}
	public void setReq(int req){
		this.maintainence_req = req;
	}
	public void setCapacity(int cap){
		this.capacity = cap;
	}

	
}
