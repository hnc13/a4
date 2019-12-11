import java.sql.Timestamp;
/**
 * Blueprint for a Ride object. It includes:
 * (i) User information
 * (ii) From/to station data
 * (iii)start/end ride time data
 * Author: Emma Tanur & Grace Bratzel
 */

public class Ride{
	/**
	 * User ID
	 */
	private int user;
	/**
	 * Start Station ID
	 */
	private int from;
	/**
	 * Destination Station ID
	 */
	private int to;
	/**
	 * Ride start time
	 */
	private Timestamp start;
	/**
	 * Ride end time
	 */
	private Timestamp end;
	
	public Ride(int user){
		this.user = user;
	}
	/**
	 * Get User ID
	 * @return User ID
	 */
	public int getUser(){
		return user;
	}
	/**
	 * Get station from which bike was rented
	 * @return Start Station ID
	 */
	public int getFrom(){
		return from;
	}
	/**
	 * Get station to which bike was returned
	 * @return Destination Station ID
	 */
	public int getTo(){
		return to;
	}
	/**
	 * Get start time of ride
	 * @return Start Time
	 */
	public Timestamp getStart(){
		return start;
	}
	
	/**
	 * Get end time of ride
	 * @return End Time
	 */
	public Timestamp getEnd(){
		return end;
	}
	
	/**
	 * Set start station to 'from'
	 * @param from - station ID of station from which bike is rented
	 */
	public void setFrom(int from){
		this.from = from;
	}
	/**
	 * Set destination station to 'to'
	 * @param to - station ID of the station where bike is returned
	 */
	public void setTo(int to){
		this.to = to;
	}
	/**
	 * Set start time to 'start'
	 * @param start - Time of bike rental
	 */
	public void setStart(Timestamp start){
		this.start = start;
	}
	/**
	 * Set end time to 'end'
	 * @param end - Time at which user returned rented bike
	 */
	public void setEnd(Timestamp end){
		this.end = end;
	}
}
