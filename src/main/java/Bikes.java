
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
	public String getbikeID () {
		return this.pedID;
	}
	
	public int getStation () {
		return this.station;
	}
	
	public int getBikeStatus () {
		return this.inUse;
	}
	
	public int getUser() {
		return this.userID;
	}
 
	public void setStation (int station) {
		this.station = station;
	}
	
	public void setBikeStatus (int inUse) {
		this.inUse = inUse;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
	