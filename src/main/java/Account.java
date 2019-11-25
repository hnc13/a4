
public class Account {
	private int type;
	private int id;
	private String password;
	
	public Account (int type, int id, String password) {
		this.type = type;
		this.id = id;
		this.password = password;
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
	
	public void setType (int type) {
		this.type = type;
	}  
	
	public void setID (int id) {
		this.id = id;
	}
	
	public void setPassword (String pass) {
		this.password = pass;
	}
	
}
