
public class Account {
	private int type;
	private int id;
	private String password;
	private int membership;
	private int balance;
	
	public Account (int type, int id, String password, int membership, int balance) {
		this.type = type;
		this.id = id;
		this.password = password;
		this.membership = membership;
		this.balance = balance;
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
	
	public int getMembership() {
		return this.membership;
	}
	
	public int getBalance() {
		return this.balance;
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
	
	public void setMembership(int mem) {
		this.membership = mem;
	}
	
	public void setBalance(int bal) {
		this.balance = bal;
	}
	
}
