//Blueprint for a Ride object
//Ride object includes user information, from/to data, and start/end data.

//Emma Tanur and Grace Bratzel

import java.sql.Timestamp;

public class Ride
{
	private int user;
	private int from;
	private int to;
	private Timestamp start;
	private Timestamp end;
	
	public Ride(int user){
		this.user = user;
	}
	public int getUser(){
		return user;
	}
	public int getFrom(){
		return from;
	}
	public int getTo(){
		return to;
	}
	public Timestamp getStart(){
		return start;
	}
	public Timestamp getEnd(){
		return end;
	}
	
	public void setFrom(int from){
		this.from = from;
	}
	public void setTo(int to){
		this.to = to;
	}
	public void setStart(Timestamp start){
		this.start = start;
	}
	public void setEnd(Timestamp end){
		this.end = end;
	}
}
