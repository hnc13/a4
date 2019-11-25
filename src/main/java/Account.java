
public class Account {
	private int type;
	private int id;
	private String password;
	private int membership;
	
	public Account (int type, int id, String password, int membership) {
		this.type = type;
		this.id = id;
		this.password = password;
		this.membership = membership;
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
	
	public void setType (int type) {
		this.type = type;
	}  
	
	public void setID (int id) {
		this.id = id;
	}
	
	public void setPassword (String pass) {
		this.password = pass;
	}
	
	public void getMembership(int mem) {
		this.membership = mem;
	}
	
}
