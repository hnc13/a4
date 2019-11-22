
public class Account {
	private int type;
	private int id;
	private String password;
	private int payMet; //0 for n/a, 1 for debit, 2  for credit
	
	public Account (int type, int id, String password, int payMet) {
		this.type = type;
		this.id = id;
		this.password = password;
		this.payMet = payMet;
	}
	
	public int getType () {
		return this.type;
	}
	
	public int getID () {
		return this.id;
	}
	
	public String getPassword () {
		return this.password;
	}
	
	public int getPayMet () {
		return this.payMet;
	}
	
	public void setType (int type) {
		this.type = type;
	}  
	
	public void setID (int id) {
		this.id = id;
	}
	
	public void setPassword (String pass) {
		this.password = pass;
	}
	
	public void setPayMet (int payMet) {
		this.payMet = payMet;
	}
}
