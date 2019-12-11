
public class Account {
	private int type; //whether the user is a user or an admin
	private int id; //user's id
	private String password; //user's password
	private int membership; //the type of membership the user has
	private int balance; //how much money the user has paid ValleyBike
	
	public Account (int type, int id, String password, int membership, int balance) {
		this.type = type;
		this.id = id;
		this.password = password;
		this.membership = membership;
		this.balance = balance;
	}
	
	/**
	 * 
	 * @return account type (user or admin)
	 */
	public int getType () {
		return this.type;
	}
	
	/**
	 * 
	 * @return account id
	 */
	public int getID () {
		return this.id;
	}
	
	/**
	 * 
	 * @return account password
	 */
	public String getPassword () {
		return this.password;
	}
	
	/**
	 * 
	 * @return account membership type
	 */
	public int getMembership() {
		return this.membership;
	}
	
	/**
	 * 
	 * @return money spent by user
	 */
	public int getBalance() {
		return this.balance;
	}
	
	/**
	 * change the type of account (user or admin)
	 * @param type
	 */
	public void setType (int type) {
		this.type = type;
	}  
	
	/**
	 * change the account's id number
	 * @param id
	 */
	public void setID (int id) {
		this.id = id;
	}
	
	/**
	 * change the account's password
	 * @param pass
	 */
	public void setPassword (String pass) {
		this.password = pass;
	}
	
	/**
	 * change the account's membership type
	 * @param mem
	 */
	public void setMembership(int mem) {
		this.membership = mem;
	}
	
	/**
	 * update the amount of money spent by the account
	 * @param bal
	 */
	public void setBalance(int bal) {
		this.balance = bal;
	}
	
}
