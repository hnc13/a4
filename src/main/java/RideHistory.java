/**
 * Blueprint for a Ride object. It contains:
 * (i)userID
 * (ii)bikeID of bike rented/returned
 * (iii)start stationID & destination stationID
 * (iv) start Time & endTime
 */


public class RideHistory{
	/**
	 * User ID of logged in user
	 */
	private int userID;
	/**
	 * The station from which a user rents a bike
	 */
	private int startStationID;
	/**
	 * The station to which a user returns a bike
	 */
	private int destStationID;
	/**
	 * Bike ID of bike user rents
	 */
	private String bikeID;
	/**
	 * Start time of a ride
	 */
	private String startTime;
	/**
	 * End time of a ride
	 */
	private String endTime;
	/**
	 * Check whether ride is in progress or not
	 * <p> 0: Ride in progress <p>
	 * <p> 1: Ride completed <p>
	 */
	private int completedRide;
	
	public RideHistory(int userID, String bikeID, int from, int to, String start, String end,int completed ){
		this.userID = userID;
		this.bikeID = bikeID;
		this.startStationID=from;
		this.destStationID = to;
		this.startTime =start;
		this.endTime = end;
		this.completedRide = completed;
	}
	
	/**
	 * Get Bike ID
	 * @return Bike ID
	 */
	public String getBikeID() {
		return this.bikeID;
	}
	/**
	 * Get User ID
	 * @return User ID
	 */
	public int getUserID(){
		return this.userID;
	}
	/**
	 * Get station from which bike was rented
	 * @return Start Station ID
	 */
	public int getStartStation(){
		return startStationID;
	}
	/**
	 * Get station to which bike was returned
	 * @return Destination Station ID
	 */
	public int getDestStation(){
		return this.destStationID;
	}
	/**
	 * Get start time of ride
	 * @return Start Time
	 */
	public String getStartTime(){
		return this.startTime;
	}
	/**
	 * Get end time of ride
	 * @return End Time
	 */
	public String getEndTime(){
		return this.endTime;
	}
	/**
	 * Check whether ride is in progress
	 * @return 1 if ride is complete; 0 if ride is in progress
	 */
	public int getCompletedRide(){
		return this.completedRide;
	}

	
	/**
	 * Set user ID to 'userID'
	 * @param userID - ID of user who decides to rent a bike
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	/**
	 * Set bike ID to 'bikeID'
	 * @param bikeID - ID of bike which the user chooses to rent out 
	 */
	public void setBikeID(String bikeID){
		this.bikeID = bikeID;
	}
	/**
	 * Set start station to 'startStationID'
	 * @param startStationID - station ID of station from which bike is rented
	 */
	public void setFrom(int startStationID){
		this.startStationID = startStationID;
	}
	/**
	 * Set destination station to 'destStationID'
	 * @param destStationID - station ID of the station where bike is returned
	 */
	public void setTo(int destStationID){
		this.destStationID = destStationID;
	}
	/**
	 * Set start time to 'startTime'
	 * @param startTime - Time of bike rental
	 */
	public void setStart(String startTime){
		this.startTime = startTime;
	}
	/**
	 * Set end time to 'endTime'
	 * @param endTime - Time at which user returned rented bike
	 */
	public void setEnd(String endTime){
		this.endTime = endTime;
	}
	/**
	 * Set completedRide variable to 'completed'
	 * @param completed - 1 if user completed ride, 0 if user has on-going ride
	 */
	public void setCompletedRide(int completed){
		this.completedRide = completed;
	}
	
	
	
}

