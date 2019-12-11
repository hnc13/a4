/**
 * Blueprint for a Station object. It station includes:
 * (i) ID, (ii) Name, (iii) Available, (iv) available bikes, (v) available pedelecs, 
 * (vi) available docks, (vii) capacity, (viii) address, (ix) presence/ absence of kiosk
 * (x) Presence/absence of maintenance request
 * Author: Emma Tanur & Grace Bratzel
 */

public class Station
{
	/**
	 * Station ID
	 */
	private int id;
	/**
	 * Station Name
	 */
	private String name;
	/**
	 * Number of bikes at station
	 */
	private int bikes;
	/**
	 * Number of pedelecs at station
	 */
	private int pedelecs;
	/**
	 * Number of available docks
	 */
	private int avail_docks;
	/**
	 * Maintenance request:
	 * <p> 1: Maintenance request <p>
	 * <p> 0: No maintenance request <p>
	 */
	private int maintainence_req;
	/**
	 * Number of total docks at a station
	 */
	private int capacity;
	/**
	 * Maintenance request:
	 * <p> true: Kiosk exists at station <p>
	 * <p> 0: No kiosk at station <p>
	 */
	private boolean kiosk;
	/**
	 * Station Address
	 */
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
	
	/**
	 * Get number of bikes
	 * @return number of bikes at a station
	 */
	public int getBikes(){
		return bikes;
	}
	/**
	 * Get number of pedelecs
	 * @return number of pedelecs at a station
	 */
	public int getPeds(){
		return pedelecs;
	}
	/**
	 * Get number of available docks
	 * @return number of available docks at a station
	 */
	public int getAvDocs(){
		return avail_docks;
	}
	/**
	 * Check whether there is a maintenance request
	 * @return 1 if maintenance is requested, otherwise return 0
	 */
	public int getMainReq(){
		return maintainence_req;
	}
	/**
	 * Get capacity of station, i.e. how many docks it has 
	 * @return number of total docks
	 */
	public int getCap(){
		return capacity;
	}
	/**
	 * Get station ID 
	 * @return station ID
	 */
	public int getID(){
		return id;
	}
	/**
	 * Get station name 
	 * @return station name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Get station address 
	 * @return station address
	 */
	public String getAddress(){
		return address;
	}
	/**
	 * Check whether station has kiosk 
	 * @return true if kiosk is present, otherwise return false
	 */
	public boolean getKiosk(){
		return kiosk;
	}
	
	/**
	 * Set bike quantity
	 * @param bikes - number of bikes at station
	 */
	public void setBikes(int bikes){
		this.bikes = bikes;
	}
	/**
	 * Set pedelec quantity
	 * @param pedelecs - number of pedelecs at station
	 */
	public void setPedelecs(int pedelecs){
		this.pedelecs = pedelecs;
	}
	/**
	 * Set quantity of available docks
	 * @param docks - number of available docks at station
	 */
	public void setDocks(int docks){
		this.avail_docks = docks;
	}
	/**
	 * Set maintenance request status
	 * @param req - 1 if maintenance request; 0 if no maintenance request
	 */
	public void setReq(int req){
		this.maintainence_req = req;
	}
	/**
	 * Set capacity of station
	 * @param cap - number of total docks a station can have
	 */
	public void setCapacity(int cap){
		this.capacity = cap;
	}

	
}
