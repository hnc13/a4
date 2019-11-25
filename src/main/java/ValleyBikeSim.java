import java.sql.Timestamp;
import java.io.*;
import java.util.*;
import java.text.*;

//This file creates a simulator for Valley Bikes. The user can interact with the program to
//view the list of all stations, save the station list to a csv files, record a ride, read in ride data 
//from a file and summarize it, and distribute bikes and pedelecs between stations.

//Authors: Emma Tanur and Grace Bratzel

public class ValleyBikeSim{
	
	private static List<Station> all_stations; //List that contains Station objects
	private static List<Ride> all_rides; //List that contains Ride objects
	private static List<Account> all_accounts; //List that contains Account objects
	private static String path = "data-files/station-data.csv"; //path of csv file
	private static Account currentUser; //the current user
	private static boolean welcome = false; //whether or not the welcome message has already been printed
	
	//Prints out station list in the console
	public static void viewStationList(){
		all_stations.sort(Comparator.comparing(Station::getID)); //Sort on the ID
		System.out.printf( "%s	%s	%s	%s	%s	%s	%s	%s	%n", "ID","Bikes","Pedelec", "AvDocs", "MainReq","Cap","Kiosk", "Name - Address");
		for(Station s:all_stations){
			System.out.printf("%s	", s.getID());
			System.out.printf("%s	", s.getBikes());
			System.out.printf("%s	", s.getPeds());
			System.out.printf("%s	", s.getAvDocs());
			System.out.printf("%s	", s.getMainReq());
			System.out.printf("%s	", s.getCap());
			System.out.printf("%s	", s.getKiosk());
			System.out.printf("%s	", s.getName() + " - " + s.getAddress());
			System.out.println();
		}
	}
	
	//Prints out ride list in the console
	public static void listAllRides(){
		for(Ride r: all_rides){
			System.out.print("User: " + r.getUser() + "     ");
			System.out.print("From: " + r.getFrom() + "     ");
			System.out.print("To: " + r.getTo() + "     ");
			System.out.print("Start: " + r.getStart() + "     ");
			System.out.print("End: " + r.getEnd() + "     ");
		}
		System.out.println();
	}
	
	//Turn Station object into a string
	public static String objectToString(Station obj){
		int kiosk_int = 0;
		if(obj.getKiosk()){
			kiosk_int = 1;
		}
		String temp = obj.getID() + "," + obj.getName() + "," + obj.getBikes() + "," + obj.getPeds() + "," + obj.getAvDocs() + "," + obj.getMainReq() + "," + obj.getCap() + "," + kiosk_int + "," + obj.getAddress();
		return temp;
	}
	
	//Turn Account object into a string
		public static String accountToString(Account obj){
			String temp = obj.getType() + "," + obj.getID() + "," + obj.getPassword() + "," + obj.getMembership() + "," + obj.getBalance();
			return temp;
		}
	
	//Write current station list to a file. Updates current csv file that was read
	public static void saveStationList(){
		try{
			FileWriter myWriter = new FileWriter(path);
			myWriter.append("ID,Name,Bikes,Pedelecs,Available Docks,Maintainence,Capacity,Kiosk,Address");
			myWriter.append("\n");
			for(Station s : all_stations){
				myWriter.append(objectToString(s));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	//Write current account list to a file. Updates current csv file that was read
	public static void saveAccountList() {
		try{
			FileWriter myWriter = new FileWriter("data-files/Accounts.csv");
			myWriter.append("Type (0 for user and 1 for admin),ID,Password,Membership(0 for none/1 for Founding Member/2 for yearly membership/ "
					+ "3 for monthly membership,Balance");
			myWriter.append("\n");
			for(Account a : all_accounts){
				myWriter.append(accountToString(a));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	//Collect user input and add a Station object to local field all_stations
	public static void userAddStation(){
		Scanner c = new Scanner(System.in);  // Create a Scanner object
		String input = null;
		
		System.out.println();
		System.out.println("Please fill out the information requested below.");
		System.out.println("Type 'back' to return to main menu.");

		//input station id (catches non-integer inputs and duplicate IDs)
		int id = -1;		
		while (id == -1) {
			System.out.println("Station ID: ");
			input = c.nextLine();
			if (input.equals("back")) {
				System.out.println();
				return;
			}
			try { 
				id = Integer.parseInt(input);			
				for (Station s : all_stations) { // check that ID is unique
				if (id == s.getID()) {
					System.out.println("'" + input + "' is already in use. Please try again.");
					System.out.println();
					id = -1;
					}
				}
				} catch (Exception e) { // check that input is a number
				System.out.println("'" + input + "' is not an accepted Station ID. Please try again.");
			}				
		}		

		//Collect user input for station name (catches duplicate names)
		String name = null;
		while (name == null) {
			System.out.println("Station Name: ");
			name = c.nextLine();
			if (name.equals("back")) {
				System.out.println();
				return;
			}
			for (Station s : all_stations) { //check that name is unique
				if (name.equals(s.getName())) {
					System.out.println("'" + name + "' is already in use. Please try a different ID.");
					System.out.println();
					name = null;
				}
			}
		}			

		//Collect user input for number of bikes (catches non-integer inputs)
		int bikes = -1;		
		while (bikes == -1) {
			System.out.println("Number of bikes: ");
			input = c.nextLine();
			if (input.equals("back")) {
				System.out.println();
				return;
			}
			try { //check that input is a number
				bikes = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("We're sorry. '" + input + "' is not an accepted number of bikes. Please try again.");
				System.out.println();
			}
		}

		//Collect user input for number of pedelecs (catches non-integer inputs)
		int pedelecs = -1;		
		while (pedelecs == -1) {
			System.out.println("Number of pedelecs: ");
			input = c.nextLine();
			if (input.equals("back")) {
				System.out.println();
				return;
			}
			try { //check that input is a number
				pedelecs = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("We're sorry. '" + input + "' is not an accepted number of pedelecs. Please try again.");
				System.out.println();
			}
		}
			
		//Collect user input for maintenance (catches input that isn't 'yes' or 'no')
		String maintainenceString = null;
		int maintainence = -1;
		while (maintainenceString == null) {
			System.out.println("Does this station require maintenance? (yes/no): ");
			maintainenceString = c.nextLine();
			if (maintainenceString.equals("back")) {
				System.out.println();
				return;
			}
				else if ((maintainenceString.equals("yes"))||(maintainenceString.equals("Yes"))) { //check that input is either 'yes' or 'no'
					maintainence = 1;
				}				
				else if ((maintainenceString.equals("no"))||(maintainenceString.equals("No"))) {
					maintainence = 0;
				}
				else {
					System.out.println("'" + maintainenceString + "' is not an accepted answer. Please type either 'yes' or 'no'.");
					System.out.println();
					maintainenceString = null;
				}	
		}
				
		//Collect user input for station capacity (catches non-integer inputs and numbers less than docks in use)
		int capacity = -1;		
		while (capacity == -1) {
			System.out.println("Capacity of station: ");
			input = c.nextLine();
			if (input.equals("back")) {
				System.out.println();
				return;
			}
			try { //check that input is a number
				capacity = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("'" + input + "' is not an accepted answer. Please try again.");
				System.out.println();
			}			
			if ((capacity != -1)&&(capacity < bikes + pedelecs)) { // check that input is less than docks in use
				System.out.println("The capacity of the station must be greater than or equal to the number of docks in use (" + (bikes+pedelecs) + "). Please try again.");
				System.out.println();
				capacity = -1;
			}
		}	
		
		//Collect user input for station address (catch duplicate addresses)
		String address = null;
		while (address == null) {
			System.out.println("Station Address: ");
			address = c.nextLine();	
			if (address.equals("back")) {
				System.out.println();
				return;
			}
			for (Station s : all_stations) { //check that address is unique
				if (address.equals(s.getAddress())){
					System.out.println("'" + address + "' is already in use. Please try a different address.");
					System.out.println();
					address = null;
				}
			}
		}

		//Collect user input for number of kiosks (catch non-integer inputs)		
		/* While the user may input any number of kiosks, we assume that all we care about is whether the station has any kiosks.
		 * So we enter this data as a true (there are 1 or more kiosks) or false (there are no kiosks).*/
		int kioskNum = -1;
		boolean kiosk = false;
		while (kioskNum == -1) {
			System.out.println("Number of kiosks: ");
			input = c.nextLine();
			if (address.equals("back")) {
				System.out.println();
				return;
			}
			try { 
				kioskNum = Integer.parseInt(input);
			} catch (Exception e) { //check that input is a number
				System.out.println("'" + input + "' is not an accepted number of kiosks. Please try again.");
				System.out.println();
			}
			if (kioskNum > 0) {
				kiosk = true;
			}
		}

		//Calculate available docks
		/* No need to ask the user about this, since we can calculate it based on other input. */
		int avail_docks = capacity - (bikes + pedelecs);

		//Create new station object and add it to our station list
		Station myStation = new Station(id,name,bikes,pedelecs,avail_docks,maintainence,capacity,kiosk,address);
		all_stations.add(myStation);

		System.out.println(name + " station (ID #" + id + ") has been added.");
		System.out.println();
		System.out.println();
	}
	
	
	
	//Add a Station object to all_stations
	public static void addStation(String data){
		String[] temp = data.split(","); //turn data into an array
		//Parse out the string
		int id = Integer.parseInt(temp[0]);
		String name = temp[1];
		int bikes = Integer.parseInt(temp[2]);
		int peds = Integer.parseInt(temp[3]);
		int avDocs = Integer.parseInt(temp[4]);
		int mainReq = Integer.parseInt(temp[5]);
		int cap = Integer.parseInt(temp[6]);
		boolean kiosk = false;
		if(Integer.parseInt(temp[7]) != 0){
			kiosk = true;
		}
		String address = temp[8];
		//Create a new station
		Station myStation = new Station(id,name,bikes,peds,avDocs,mainReq,cap,kiosk,address);
		all_stations.add(myStation);	
	}
	
		//Add an Account object to all_accounts
		public static void addAccount(String data){
			String[] temp = data.split(","); //turn data into an array
			//Parse out the string
			int type = Integer.parseInt(temp[0]);
			int id = Integer.parseInt(temp[1]);
			String password = temp[2];
			int membership = Integer.parseInt(temp[3]);
			int balance = Integer.parseInt(temp[4]);
			
			//Create a new account
			Account account = new Account(type, id, password, membership, balance);
			all_accounts.add(account);	
		}
	
	//Summarize rides after parsing a list of arrays in string form
	public static void summarizeRides(List<String[]> data){
		int rides = data.size(); //Number of rides in file
		long avg_min = 0; //Average ride time
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time_1 = null; //Stores start date and time for each ride
		Date time_2 = null; //Stores end date and time for each ride
		
		//Iterate through saved data from file
		for(String[] arr: data){
			//Station id recorded must match a Valley Bike station ID
			boolean station1_exists = false; //set to false
			boolean station2_exists = false;
			for(Station s: all_stations){
				if(s.getID() == Integer.parseInt(arr[1])){
					station1_exists = true;
				}
				if(s.getID() == Integer.parseInt(arr[2])){
					station2_exists = true;
				}
			}
			if (!station2_exists || !station1_exists){ 
				System.out.print("Please make sure station ids are valid.");
				return;
			}
			
			try{
				//Convert start and end time from file into a simple date format
				//Set Date objects time_1 and time_2 to start and end time from file
				time_1 = f.parse(arr[3]); 
				time_2 = f.parse(arr[4]);
			}
			catch (ParseException e){
				System.out.print("Please make sure time format follows yyyy-[m]m-[d]d hh:mm:ss");
				return;
			}
			avg_min = (time_2.getTime()-time_1.getTime())/(60 * 1000) % 60 + avg_min;
		}
		avg_min = avg_min/rides; //Calculate average ride time
		System.out.println("This ride list contains " + rides + " rides with an average ride time of " + avg_min + " minutes.");
	}
	
	//Read a data file with ride information and calls summarizeRides() method
	public static void resolveRideData() throws ParseException{

		//Prompt user for file with ride information
		System.out.print("Enter the file name (including extension) of the file located in data-files: ");
		Scanner a = new Scanner(System.in);
		String file = a.next();
		String path = "data-files/" + file;
		List<String[]> data = new ArrayList<String[]>(); //Holds rides listed in file
		
		//Read file and save data
		try{
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			String row;
			int count = 0;
			//Read the file line by line and save to the list of arrays called "data"
			while ((row = csvReader.readLine()) != null) {
				if(count != 0){ //skips first row which contains column headers
					String[] temp = row.split(",");
					data.add(temp);
				}
				count++;
			}
			csvReader.close();
		}
		catch (Exception e) { 
	        e.printStackTrace(); 
	    }
		summarizeRides(data); //call for summary of rides
		
	}
	
	public static boolean stationExists(int id){
		for(Station s: all_stations){
			if(s.getID()==id){
				return true;
			}
		}
		return false;
	}
	
	//Calculate total number of bikes in all stations
	public static int totalBikes() {
		int total=0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getBikes();
		}
		return total;
	}

	//Calculate total number of peds in all stations
	public static int totalPedelecs() {
		int total=0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getPeds();
		}
		return total;
	}

	//Calculate total capacity for all stations
	public static int totalCapacity() {
		int total=0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getCap();
		}
		return total;
	}
	
	
	//Print number of bikes and pedelecs in every station
	public static void viewBikeStats(){
		all_stations.sort(Comparator.comparing(Station::getID)); //Sort on the ID //sort stations by ID for proper order
		System.out.printf( "%s	%s	%s	%s	%n", "ID", "Bikes", "Peds", "Station");
		for(Station s:all_stations){

			System.out.printf("%s	", s.getID());
			System.out.printf("%s	", s.getBikes());
			System.out.printf("%s	", s.getPeds());
			System.out.printf("%s	", s.getName());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	//Sort list all_stations by station capacity (greatest to smallest)
	public static void sortStationsByCapacity() {
		Comparator<Station> compareByCapacity = new Comparator<Station>() {
		    @Override
		    public int compare(Station s1, Station s2) {
		        return Integer.compare(s1.getCap(),s2.getCap());
		    }
		};
		all_stations.sort(compareByCapacity); // sort by capacity (smallest to greatest)
		Collections.reverse(all_stations); // now it's greatest to smallest
	}

	//Redistribute bikes and peds following the ratio of total bikes and peds to total station capacity
	public static void equalizeStations() {
		int totalBikes = totalBikes();
		int totalPedelecs = totalPedelecs();
		int totalCapacity = totalCapacity();
		
		System.out.println();
		System.out.println("Your current distribution of bikes and pedelecs:");
		System.out.println();
		viewBikeStats();

		for(Station s:all_stations){
			s.setBikes((int)((s.getCap()*totalBikes)/totalCapacity));	
			s.setPedelecs((int)((s.getCap()*totalPedelecs)/totalCapacity));
		}

		// Calculate remaining bikes/peds to add to stations
		int remainingBikes = totalBikes - totalBikes();
		int remainingPedelecs = totalPedelecs - totalPedelecs();

		sortStationsByCapacity(); //sort stations by capacity (greatest to smallest)
		
		// Distribute remaining bikes to largest stations
		if (remainingBikes != 0) {
			for (Station s:all_stations) {
				if (s.getAvDocs() > 0) { // check if station is full
					int newBikeNum = s.getBikes() + 1;
					s.setBikes(newBikeNum);
					remainingBikes = remainingBikes-1;
					if (remainingBikes==0) {
						break;
					}
				}
			}
		}

		//distribute remaining pedelecs to largest stations
		if (remainingPedelecs != 0) {
			for (Station s:all_stations) {
				if (s.getAvDocs() > 0) { //check if station is full
					int newPedNum = s.getPeds() + 1;
					s.setPedelecs(newPedNum);
					remainingPedelecs = remainingPedelecs-1;
					if (remainingPedelecs==0) {
						break;
					}
				}

			}
		}
		
		System.out.println("Your updated, equalized distribution of bikes and pedelecs:");
		System.out.println();
		viewBikeStats();
	}
	
	//Record ride data using user input
	public static void recordRide(){
		int station_id = 0;
		int new_ride = 1; //1 = new ride; 0 = in-progress ride
		
		//Prompt user for ID
		Scanner a = new Scanner(System.in);  // Create a Scanner object
		System.out.print("Please enter your user ID emailed to you: ");
		int user = Integer.parseInt(a.next());
		while(true){ //make sure the station exists
			System.out.print("Please enter station ID at current location: ");
			int id = Integer.parseInt(a.next());
			if(stationExists(id)){
				station_id = id;
				break;
			}
			else{
				System.out.print("Invalid input.");
			}
		}
		//Prompt user for type of bike
		while(true){
			System.out.print("Please enter one of the following options (B=bike, P=Pedelec): ");
			String type = a.next();
			if(type.equals("P")){
				break;
			}
			else if(type.equals("B")){
				System.out.println("No bikes available now."); //assumptions: no bikes are available at any of the stations
			}
			else{
				System.out.println("Invalid input.");
			}
		}
		
		//Check to see if user has an in-progress ride. If so, we are recording the end of a ride.
		for(Ride r: all_rides){
			if(r.getUser() == user && r.getEnd() == null){ //Check to see if the user has an in-progress ride
				new_ride = 0;
				//Edit station and ride data
				for(Station station: all_stations){
					if(station.getID() == station_id){
						if(station.getAvDocs() != 0){ //If there is capacity at the dock
							System.out.print('\n');
							System.out.println("Thank you for riding. Please check your email for a recipt");
							station.setPedelecs(station.getPeds()+1); //add one to pedelecs count
							station.setDocks(station.getAvDocs()-1); //subtract one from available docks
							Timestamp time = new Timestamp(System.currentTimeMillis()); //Transaction timestamp
							r.setEnd(time); //Set "end" time field to current time
							r.setTo(station_id); //Set "to" field to current dock location
						}
						else{ //If there is no capacity
							System.out.println("No capacity here. Please park at another station. You can print the "
												+ "station list to see capacity.");
						}
					}
				}
			}
		}
		
		//Enter a new ride
		if(new_ride == 1){ 
			//Iterate through stations to find current station data
			for(Station station: all_stations){
				if(station.getID() == station_id){
					if(station.getPeds() != 0){ //If station has available bikes
						System.out.print('\n');
						System.out.println("Ride recorded and started. You will be charged once the bike is returned at any of our docks.");
						station.setPedelecs(station.getPeds()-1); //Subtract one pedelec to station
						station.setDocks(station.getAvDocs()+1); //Add one available docks to station
						//Create a new ride
						Ride r = new Ride(user);
						Timestamp time = new Timestamp(System.currentTimeMillis()); //the time and date of the transaction
						r.setStart(time); //Set start time of Ride object
						r.setFrom(station_id); //Set "from" field to current dock location
						all_rides.add(r);
					}
					else{
						System.out.println("Invalid transaction. No bikes are available at this station.");
					}
				}
			}
		}
	}
	
	/**
	 * Allows a new user to create an account. Only users can create accounts, not admins. 
	 */
	public static void createAccount() {
		int ID = 0;
		String password = null;
		int type = 0;
		int membership = 0;
		int balance = 0;
		Scanner c = new Scanner(System.in);  // Create a Scanner object
		
		Random rand = new Random();
        Integer randomID = rand.nextInt(9999-1000)+1000;
        boolean repeat = false;
        
        while (ID == 0) {
        	repeat = false;
        	for(Account a : all_accounts) {
            	if(randomID.equals(a.getID())) {
            		randomID = rand.nextInt(9999-1000)+1000;
            		repeat = true;
            	}
            }
        	if (repeat == false) {
        		ID = randomID;
        	}
        } 
		
		System.out.println();
		System.out.println("Please fill out the information requested below.");
		System.out.println("Type 'back' to return to main menu.");

		//input password
		while (password == null) {
			System.out.println("Create password: ");
			password = c.nextLine();
			if (password.equals("back")) {
				System.out.println();
				return;
			}
		}
		
		Account user = new Account (type, ID, password, membership, balance);
		all_accounts.add(user);
		currentUser = user;
		
		saveAccountList();
		
		System.out.println("Congrats! Your account has been created. Your user ID is " + ID + ".");
	}
	
	/**
	 * Allows a user to log into ValleyBike
	 * @throws ParseException 
	 */
	public static void logIn() throws ParseException {
		Scanner s = new Scanner(System.in);  // Create a Scanner object
		String input;
		Integer ID = 0;
		boolean trueID = false;
		boolean rightPass = false;
		
		System.out.println("Please enter your user ID: ");
		while (true) {
			input = s.next();
			for (Account a : all_accounts) {
				if (Integer.toString(a.getID()).equals(input)) {
					ID = a.getID();
					trueID = true;
					break;
				}
			}
			if (trueID == true) {
				break;
			}
			System.out.println("That user ID is incorrect. Please try again.");
		}
		
		System.out.println("Please enter your password: ");
		Scanner c = new Scanner(System.in);
		while (true) {
			input = c.next();
			for (Account a : all_accounts) {
				if (ID.equals(a.getID())) {
					if (input.equals(a.getPassword())) {
						rightPass = true;
						currentUser = a;
						break;
					}
				}
			}
			if (rightPass == true) {
				break;
			}
			System.out.println("That password is incorrect. Please try again.");
		}
		
	}
	
	/**
	 * Allows the user to purchase a membership.
	 */
	public static void buyMem () {
		System.out.println("We have 3 membership options available.");
		
		int mem = 0;
		String choice;
		String[] num = new String[] {"1","2", "3"}; //membership options
		List<String> memOptions = Arrays.asList(num);
		
		String[] mems = new String[] {"Founding Member", "Annual", "Monthly"};
		List<String> memsOptions = Arrays.asList(mems);
		
		String[] costs = new String[] {"90","80","20"}; //membership costs
		List<String> costOptions = Arrays.asList(costs);
		
		Scanner s = new Scanner(System.in);  // Create a Scanner object
		while (true) {
			System.out.println("Please select a membership");
			System.out.println("[1]Founding Member: $90 annually. The first 60 minutes of each ride are included.");
			System.out.println("[2]Annual Membership: $80 annually. The first 45 minutes of each ride are included.");
			System.out.println("[3]Monthly Membership: $20 monthly. The first 45 minutes of each ride are included.");
			System.out.println("Type 'back' to return to menu.");
			choice = s.next();
			if (choice.equals("back")) {
				System.out.println();
				return;
			}
			if (memOptions.contains(choice)) {
				mem = Integer.parseInt(choice);
				System.out.println("You have selected the " + memsOptions.get(Integer.parseInt(choice)-1) + " membership. Are you sure? Y/N");
				Scanner c = new Scanner(System.in);  // Create a Scanner object
				String input = c.next();
				if (input.equals("Y") || input.equals("y") || input.equals("yes") || input.equals("Yes")) {
					currentUser.setMembership(Integer.parseInt(choice)-1);
					break;
				} 
				else if (input.equals("N") || input.equals("n") || input.equals("no") || input.equals("No")) {
					continue;
				}
			}
			System.out.println("That input is incorrect. Please try again.");
		}
		for (Account a : all_accounts) {
			if (a == currentUser) {
				a.setMembership(mem);
			}
		}
		int cost = Integer.parseInt(costOptions.get(Integer.parseInt(choice)-1));
		payment(cost);
		saveAccountList();
		System.out.println("Congratulations! You now have a ValleyBike " + memsOptions.get(Integer.parseInt(choice)-1) + " membership!");
		}
	
	
	/**
	 * Allows the user to change their membership.
	 */
	public static void changeMem () {
		String[] num = new String[] {"1","2", "3"}; //membership options
		List<String> memOptions = Arrays.asList(num);
		
		String[] mems = new String[] {"Founding Member", "Annual", "Monthly"}; //membership names
		List<String> memsOptions = Arrays.asList(mems);
		
		String[] costs = new String[] {"90","80","20"}; //membership costs
		List<String> costOptions = Arrays.asList(costs);
		
		System.out.println("Your current membership is: " + memsOptions.get(currentUser.getMembership()-1) +".");
		System.out.println("We have 3 membership options available.");
		
		int mem = 0;
		String choice;
		
		Scanner s = new Scanner(System.in);  // Create a Scanner object
		while (true) {
			System.out.println("Please select a membership");
			System.out.println("[1]Founding Member: $90 annually. The first 60 minutes of each ride are included.");
			System.out.println("[2]Annual Membership: $80 annually. The first 45 minutes of each ride are included.");
			System.out.println("[3]Monthly Membership: $20 monthly. The first 45 minutes of each ride are included.");
			System.out.println("Type 'back' to return to menu.");
			choice = s.next();
			if (choice.equals("back")) {
				System.out.println();
				return;
			}
			if (memOptions.contains(choice)) {
				mem = Integer.parseInt(choice);
				System.out.println("You have selected the " + memsOptions.get(Integer.parseInt(choice)-1) + " membership. Are you sure? Y/N");
				Scanner c = new Scanner(System.in);  // Create a Scanner object
				String input = c.next();
				if (input.equals("Y") || input.equals("y") || input.equals("yes") || input.equals("Yes")) {
					currentUser.setMembership(Integer.parseInt(choice)-1);
					break;
				} 
				else if (input.equals("N") || input.equals("n") || input.equals("no") || input.equals("No")) {
					continue;
				}
			}
			System.out.println("That input is incorrect. Please try again.");
		}
		for (Account a : all_accounts) {
			if (a == currentUser) {
				a.setMembership(mem);
			}
		}
		int cost = Integer.parseInt(costOptions.get(Integer.parseInt(choice)-1));
		payment(cost);
		saveAccountList();
		System.out.println("Congratulations! You now have a ValleyBike " + memsOptions.get(Integer.parseInt(choice)-1) + " membership!");
	}
	
	public static void payment (int cost) {
		Random rand = new Random();
        int chance = rand.nextInt(9);
		Scanner s = new Scanner(System.in);  // Create a Scanner object
		String input;
		System.out.println("Your total is $" + cost + ".");
		while(true) {
			System.out.println("Please enter your credit/debit card number: ");
			input = s.next();
			
			if (chance <= 9) {
				System.out.println("Your payment has been accepted.");
				int oldBal = currentUser.getBalance();
				currentUser.setBalance(cost+oldBal);
				break;
			} else {
				System.out.println("There was a problem proccessing your payment. Please try again.");
				continue;
			}
		}
		
	}
		
	
	public static void main(String[] args) throws ParseException {
		//initialize the fields
		all_stations = new ArrayList<Station>(); 
		all_rides = new ArrayList<Ride>();
		all_accounts = new ArrayList<Account>();
		
		//Allow user to log in or create an account
		Scanner s = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Welcome to the ValleyBike Simulator.");
		System.out.println("Please log in or create an account.");
		
		try{
			BufferedReader csvReader = new BufferedReader(new FileReader("data-files/Accounts.csv"));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if(count != 0){
					addAccount(row); //Add station to all_stations
				}
				count++;
			}
			csvReader.close();
			
			
		}
		catch (Exception e) { 
	        e.printStackTrace(); 
	    }
		
		String[] num01 = new String[] {"0","1"}; //log in options
		List<String> logInOptions = Arrays.asList(num01);
		String choice;
		
		while (true) {
			System.out.println("[0] Log in.");
			System.out.println("[1] Create account.");
			choice = s.next();
			if (logInOptions.contains(choice)) {
				break;
			}
		}
		
		if (choice.equals("0")) { //log in
			logIn();
		} else if (choice.equals("1")) { //create a new account
			createAccount();
		}
		
		try{
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if(count != 0){
					addStation(row); //Add station to all_stations
				}
				count++;
			}
			csvReader.close();
			
		}
		catch (Exception e) { 
	        e.printStackTrace(); 
	    }
		
		//menu for users
		if (currentUser.getType() == 0) {
			while (true) {
				if (welcome == false) {
					System.out.println("Welcome to ValleyBike, user " + currentUser.getID() + ".");
					welcome = true;
				}
				String[] num = new String[] {"0","1","2","3","4","5", "6"}; //menu options
				List<String> menuOptions = Arrays.asList(num);
				String input;
				
				try{
					BufferedReader csvReader = new BufferedReader(new FileReader(path));
					String row;
					int count = 0;
					while ((row = csvReader.readLine()) != null) {
						if(count != 0){
							addStation(row); //Add station to all_stations
						}
						count++;
					}
					csvReader.close();
					
				}
				catch (Exception e) { 
			        e.printStackTrace(); 
			    }
				
				
				
				Scanner c = new Scanner(System.in);  // Create a Scanner object
				System.out.println("Please choose from one of the following menu options:");
				String[] options = new String[] {"[0] Quit Program.", "[1] View station list.", "[2] Purchase/Change Membership.", "[3] Start Ride.", 
						"[4] End ride.", "[5] View ride history.", "[6] View balance."};
				while (true) {
					for (int i=0; i<options.length; i++){
						System.out.println(options[i]);
					}
					input = s.next();
					if (menuOptions.contains(input)) {
						break;
					}
				}
				
				if (input.equals("0")) { //quit program
					System.exit(0);
				} else if (input.equals("1")) { //view station list
					viewStationList();
				} else if (input.equals("2")) { //purchase/change membership
					if (currentUser.getMembership() == 0) {
						buyMem();
					} else if (currentUser.getMembership() > 0) {
						changeMem();
					}
				} else if (input.equals("3")) { //start ride
					//TODO: call start ride
					System.out.println("okay");
				} else if (input.equals("4")) { //end ride
					//TODO: call end ride
					System.out.println("okay");
				} else if (input.equals("5")) { //view ride history
					//TODO: call view ride history
					System.out.println("okay"); 
				} else if (input.equals("6")) { //view balance
					System.out.println("Your balance is $" + currentUser.getBalance() + ".");
				}
			}
		}
		
		//Menu for admins
		else if (currentUser.getType() == 1) {
			while(true) {
				if (welcome == false) {
					System.out.println("Welcome to ValleyBike, admin " + currentUser.getID() + ".");
					welcome = true;
				}
				String[] num = new String[] {"0","1","2","3","4","5","6","7","8"}; //menu options
				List<String> menuOptions = Arrays.asList(num);
				String input;
				
				Scanner c = new Scanner(System.in);  // Create a Scanner object
				System.out.println("Please choose from one of the following menu options:");
				String[] options = new String[] {"[0] Quit Program.", "[1] View station list.", "[2] Add station.", "[3] Save station list.", 
						"[4] Equalize stations.", "[5] Resolve ride data.", "[6] Track bikes.", "[7] View total ride history.", 
						"[8] Resolve maintenance issue."};
				while (true) {
					for (int i=0; i<options.length; i++){
						System.out.println(options[i]);
					}
					input = s.next();
					if (menuOptions.contains(choice)) {
						break;
					}
				}
				
				if (input.equals("0")) { //quit program
					System.exit(0);
				} else if (input.equals("1")) { //view station list
					viewStationList();
				} else if (input.equals("2")) { //add station
					userAddStation();
				} else if (input.equals("3")) { //save station list
					saveStationList();
				} else if (input.equals("4")) { //equalize stations
					equalizeStations();
				} else if (input.equals("5")) { //resolve ride data
					resolveRideData();
				} else if (input.equals("6")) { //track bikes
					//TODO: call track bikes
					System.out.println("okay");
				} else if (input.equals("7")) { //view total ride history
					//TODO: call view ride history
					System.out.println("okay");
				} else if (input.equals("8")) { //resolve maintenance issue
					//TODO: create resolve maintenance issue method
					System.out.println("okay");
				}
			}
			
			
		}
	}
}

