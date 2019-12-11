/**
 * Blueprint for Bike object
 * It sets & gets all attributes relating to each pedelec bike in the 
 * valley bike system, including its ID and location
 * @author jessicahernandez
 */
public class Bikes {
	private String pedID;
	private int station; 
	private int inUse; 
	private int userID; 
	
	public Bikes (String pedID, int station, int inUse, int userID) {
		this.pedID = pedID;
		this.station = station;
		this.inUse = inUse;
		this.userID = userID;
	}
	/**
	 * 
	 * @return its unique bike ID
	 */
	public String getbikeID () {
		return this.pedID;
	}
	/**
	 * 
	 * @return the station where the bike is located
	 */
	
	public int getStation () {
		return this.station;
	}
	/**
	 * 
	 * @return whether or not the bike is in use:
	 * 1 - if the bike is being used;
	 * 0 - if the bike is docked at a station;
	 */
	public int getBikeStatus () {
		return this.inUse;
	}
	/**
	 * 
	 * @return the ID of the user when it is checked out. User ID is null when the bike 
	 * is docked at a station.
	 */
	
	public int getUser() {
		return this.userID;
	}
	/**
	 * 
	 * @param station is the new station ID of where a bike's location should be set to.
	 */
	public void setStation (int station) {
		this.station = station;
	}
	/**
	 * 
	 * @param inUse lets us know whether a bike is checked out or not.
	 * When the bike is checked out, inUse is set to 1.
	 * When the bike is docked at a station, InUse is set to 0.
	 * This must be updated each time a user rents/returns a bike. 
	 */
	
	public void setBikeStatus (int inUse) {
		this.inUse = inUse;
	}
	/**
	 * 
	 * @param userID is the ID of the user wanting to rent a bike.
	 * This is updated to match the ID of the user currently using the bike at time of rental.
	 * This is updated and set to null when a user returns a bike and is no longer in use at time
	 * bike return/ end ride. 
	 */
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
	