//Blueprint for a Ride object
//Ride History contains:
// (i)userID
// (ii)bikeID of bike rented/returned
// (iii)start stationID & destination stationID
// (iv) start Time & endTime

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RideHistory{
	private int userID;
	private int startStationID;
	private int destStationID;
	private int bikeID;
	private String startTime;
	private String endTime;
	
	public RideHistory(int userID, int bikeID, int from, int to, String start, String end){
		this.userID = userID;
		this.bikeID = bikeID;
		this.startStationID=from;
		this.destStationID = to;
		this.startTime =start;
		this.endTime = end;
	}
	
	public int getBikeID() {
		return this.bikeID;
	}
	
	public int getUserID(){
		return this.userID;
	}
	public int getStartStation(){
		return startStationID;
	}
	public int getDestStation(){
		return this.destStationID;
	}
	public String getStartTime(){
		return this.startTime;
	}
	public String getEndTime(){
		return this.endTime;
	}

	
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	public void setBikeID(int bikeID){
		this.bikeID = bikeID;
	}
	public void setFrom(int startStationID){
		this.startStationID = startStationID;
	}
	public void setTo(int destStationID){
		this.destStationID = destStationID;
	}
	public void setStart(String startTime){
		this.startTime = startTime;
	}
	public void setEnd(String endTime){
		this.endTime = endTime;
	}
	
	
	
}

