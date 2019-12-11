import java.sql.Timestamp;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This program is based on code base 656 by Emma Tanur and Grace Bratzel. 
// It creates a simulator for ValleyBike. The user can interact with the program to
// The readMe.txt contains further documentation regarding the many functionalities provided to users,
// and how to interact with the system.

// Authors: Hannah, Jess, Mae, Nashshaba

public class ValleyBikeSim {
	// ***** Static Data Structures ****//

	/**
	 * List containing Station objects
	 */
	private static List<Station> all_stations;
	/**
	 * List containing Ride objects.
	 */
	private static List<Ride> all_rides;
	/**
	 * List containing Account objects
	 */
	private static List<Account> all_accounts;
	/**
	 * List containing Ride History objects
	 */
	private static List<RideHistory> ride_history;
	/**
	 * List containing Bike objects
	 */
	private static List<Bikes> all_bikes; // List that contains pedelec bike objects
	private static LocateBikes bikeCollection = new LocateBikes();

	/**
	 * Path to stations-data.csv file
	 */
	private static String path = "data-files/station-data.csv";

	/**
	 * Current user
	 */
	private static Account currentUser;

	/**
	 * Has welcome message has already been printed?
	 */
	private static boolean welcome = false;

	// ***** Static GUI Related Variables ****//
	static boolean gui = false;
	static JFrame frame;
	static int width = 600;
	static int height = 600;
	static ImageIcon logoIMG = new ImageIcon("data-files/ValleyBike.PNG");
	static JButton home;
	static JButton logIn;
	static JButton createAccount;
	static JLabel welcomeText;
	static JLabel please;
	static JTextField IDtext;
	static JLabel userPass;
	static JPanel homePanel;
	static JButton enterCreate;
	static JLabel congrats;
	static JTextField passText;
	static JLabel userID;
	static JButton enterLogin;
	static JButton logout;
	static JLabel menuType;
	static JPanel menuPanel;
	static JLabel consoleLabel;
	static JLabel wrongPass;
	static JLabel wrongID;
	static JLabel forgot;

//-----------------------------------------------------------------------------------------------------------------//
	// ***** Print Methods ****//

	/**
	 * Print list of existing stations in the console.
	 */
	public static void viewStationList() {
		all_stations.sort(Comparator.comparing(Station::getID)); // Sort on the ID
		System.out.printf("%s	%s	%s	%s	%s	%s	%s	%s	%n", "ID", "Bikes", "Pedelec", "AvDocs", "MainReq", "Cap",
				"Kiosk", "Name - Address");
		for (Station s : all_stations) {
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

	/**
	 * Print ride history data of all users in the console.
	 */
	public static void viewRideHistory() {
		System.out.printf("%s %s %s	%s	%s	%s %s %n", "User ID", "Bike ID", "Start Station", "End Station",
				"Start time", "End Time", "Completed Ride");

		for (RideHistory r : ride_history) {
			System.out.printf("%s	", r.getUserID());
			System.out.printf("%s	", r.getBikeID());
			System.out.printf("%s	", r.getStartStation());
			System.out.printf("%s	", r.getDestStation());
			System.out.printf("%s	", r.getStartTime());
			System.out.printf("%s	", r.getEndTime());
			System.out.printf("%s	", r.getCompletedRide());
			System.out.println();
		}
	}

	/**
	 * Print ride data from 'all-rides' data structure that is populated by either
	 * sample-ride-data-0820/0821.csv in the console.
	 */
	public static void listAllRides() {
		for (Ride r : all_rides) {
			System.out.print("User: " + r.getUser() + "     ");
			System.out.print("From: " + r.getFrom() + "     ");
			System.out.print("To: " + r.getTo() + "     ");
			System.out.print("Start: " + r.getStart() + "     ");
			System.out.print("End: " + r.getEnd() + "     ");
		}
		System.out.println();
	}

	/**
	 * Print number of bikes and pedelecs in every station.
	 */
	public static void viewBikeStats() {
		all_stations.sort(Comparator.comparing(Station::getID)); // Sort on the ID //sort stations by ID for proper
																	// order
		System.out.printf("%s	%s	%s	%s	%n", "ID", "Bikes", "Peds", "Station");
		for (Station s : all_stations) {

			System.out.printf("%s	", s.getID());
			System.out.printf("%s	", s.getBikes());
			System.out.printf("%s	", s.getPeds());
			System.out.printf("%s	", s.getName());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * Print all available bikes at a particular station.
	 * 
	 * @param stationID - ID of the station user wants to rent a bike from.
	 */
	public static void viewBikesAtStation(int stationID) {
		String bikeID;
		for (Bikes b : all_bikes) {
			if (b.getStation() == stationID && b.getBikeStatus() != 1) {
				bikeID = b.getbikeID();
				System.out.print("Bike ID: " + bikeID);
				System.out.println();
			}
		}

	}
//-----------------------------------------------------------------------------------------------------------------//	
	// ***** Helper Methods ****//

	/**
	 * Convert Station object to string
	 * 
	 * @param obj - Station Object
	 * @return a string containing all object attributes of a station
	 */
	public static String objectToString(Station obj) {
		int kiosk_int = 0;
		if (obj.getKiosk()) {
			kiosk_int = 1;
		}
		String temp = obj.getID() + "," + obj.getName() + "," + obj.getBikes() + "," + obj.getPeds() + ","
				+ obj.getAvDocs() + "," + obj.getMainReq() + "," + obj.getCap() + "," + kiosk_int + ","
				+ obj.getAddress();
		return temp;
	}

	/**
	 * Convert Ride History object to string
	 * 
	 * @param obj - Ride History Object
	 * @return a string containing all object attributes of a ride
	 */
	public static String rideHistoryToString(RideHistory obj) {

		String temp = obj.getUserID() + "," + obj.getBikeID() + "," + obj.getStartStation() + "," + obj.getDestStation()
				+ "," + obj.getStartTime() + "," + obj.getEndTime() + "," + obj.getCompletedRide();
		return temp;
	}

	/**
	 * Convert Account object to string
	 * 
	 * @param obj - Account Object
	 * @return a string containing all object attributes of an user account
	 */
	public static String accountToString(Account obj) {
		String temp = obj.getType() + "," + obj.getID() + "," + obj.getPassword() + "," + obj.getMembership() + ","
				+ obj.getBalance();
		return temp;
	}

	/**
	 * Convert Bike object to string
	 * 
	 * @param obj - Bike Object
	 * @return a string containing all object attributes of a bike
	 */
	public static String bikeToString(Bikes obj) {
		String temp = obj.getbikeID() + "," + obj.getStation() + "," + obj.getBikeStatus() + "," + obj.getUser();
		return temp;
	}

	/**
	 * Does station with passed station ID already exist?
	 * 
	 * @param id - ID of a station
	 * @return true if such a station exists or false if no such station exists
	 */
	public static boolean stationExists(int id) {
		for (Station s : all_stations) {
			if (s.getID() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Is a given bike available to rent/use?
	 * 
	 * @param bikeID - ID of bike user wants to rent/use
	 * @return true if the bike is not being used or false if it is currently being
	 *         used.
	 */
	public static boolean bikeNotInUse(String bikeID, int stationID) {
		for (Bikes b : all_bikes) {
			if (b.getbikeID().equals(bikeID) && b.getStation() == stationID) {
				if (b.getBikeStatus() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Calculate total number of bikes in all stations.
	 * 
	 * @return total number of bikes in the ValleBike System
	 */
	public static int totalBikes() {
		int total = 0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getBikes();
		}
		return total;
	}

	/**
	 * Calculate total number of pedelecs in all stations.
	 * 
	 * @return total number of pedelecs in the ValleBike System
	 */
	public static int totalPedelecs() {
		int total = 0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getPeds();
		}
		return total;
	}

	/**
	 * Calculate total capacity of all stations.
	 * 
	 * @return total capacity of stations in the ValleBike System
	 */
	public static int totalCapacity() {
		int total = 0;
		Iterator<Station> iterator = all_stations.listIterator();
		while (iterator.hasNext()) {
			total += iterator.next().getCap();
		}
		return total;
	}

//-----------------------------------------------------------------------------------------------------------------//	
	// ***** Save Data ****//

	/**
	 * Save all data stored in 'all_station' list in 'station-data.csv' file.
	 */
	public static void saveStationList() {
		try {
			FileWriter myWriter = new FileWriter(path);
			myWriter.append("ID,Name,Bikes,Pedelecs,Available Docks,Maintainence,Capacity,Kiosk,Address");
			myWriter.append("\n");
			for (Station s : all_stations) {
				myWriter.append(objectToString(s));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Your station has been saved.");
	}

	/**
	 * Save all data stored in 'ride_hstory' list in 'ride-history.csv' file.
	 */
	public static void saveRideHistory() {
		try {
			FileWriter myWriter = new FileWriter("data-files/ride-history.csv");
			myWriter.append(
					"User ID,Bike ID,Start Station ID,Destination Station ID, Start Time, End Time, Completed Ride");
			myWriter.append("\n");
			for (RideHistory r : ride_history) {
				myWriter.append(rideHistoryToString(r));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save all data stored in 'all_accounts' list in 'Accounts.csv' file.
	 */
	public static void saveAccountList() {
		try {
			FileWriter myWriter = new FileWriter("data-files/Accounts.csv");
			myWriter.append(
					"Type (0 for user and 1 for admin),ID,Password,Membership(0 for none/1 for Founding Member/2 for yearly membership/ "
							+ "3 for monthly membership,Balance");
			myWriter.append("\n");
			for (Account a : all_accounts) {
				myWriter.append(accountToString(a));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save all data stored in 'all_bikes' list in 'bikeLocation-data.csv' file.
	 */
	public static void saveBikeLocation() {
		try {
			FileWriter myWriter = new FileWriter("data-files/bikeLocation-data.csv");
			myWriter.append("bikeID,station,inUse,UserID");
			myWriter.append("\n");
			for (Bikes b : all_bikes) {
				myWriter.append(bikeToString(b));
				myWriter.append("\n");
			}
			myWriter.flush();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//-----------------------------------------------------------------------------------------------------------------		
	// ***** Add Data ****//

	/**
	 * Allow ValleyBike system administrator to add a new station.
	 */
	public static void userAddStation() {
		Scanner c = new Scanner(System.in); // Create a Scanner object
		String input = null;

		System.out.println();
		System.out.println("Please fill out the information requested below.");
		System.out.println("Type 'back' to return to main menu.");

		// Input station id (catches non-integer inputs and duplicate IDs)
		int id = -1;
		while (id == -1) {
			System.out.println("Station ID: ");
			input = c.nextLine();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try {
				id = Integer.parseInt(input);
				for (Station s : all_stations) { // check that ID is unique
					if (id == s.getID()) {
						System.out.println("'" + input + "' is already in use. Please try again.");
						System.out.println();
						id = -1;
						break;
					}
				}
			} catch (Exception e) { // check that input is a number
				System.out.println("'" + input + "' is not an accepted Station ID. Please try again.");
				continue;
			}
		}

		// Collect user input for station name (catches duplicate names)
		String name = null;
		while (name == null) {
			System.out.println("Station Name: ");
			name = c.nextLine();
			if (name.equalsIgnoreCase("back") || name.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			for (Station s : all_stations) { // check that name is unique
				if (name.equals(s.getName())) {
					System.out.println("'" + name + "' is already in use. Please try a different ID.");
					System.out.println();
					name = null;
				}
			}
		}

		// Assuming # of bikes will always be 0
		int bikes = 0;

		// Collect user input for number of pedelecs (catches non-integer inputs)
		int pedelecs = -1;
		while (pedelecs == -1) {
			System.out.println("Number of pedelecs: ");
			input = c.nextLine();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try { // Check whether input is a number
				pedelecs = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println(
						"We're sorry. '" + input + "' is not an accepted number of pedelecs. Please try again.");
				System.out.println();
			}
		}

		// Collect user input for maintenance (catches input that isn't 'yes' or 'no')
		String maintainenceString = null;
		int maintainence = -1;
		while (maintainenceString == null) {
			System.out.println("Does this station require maintenance? (yes/no): ");
			maintainenceString = c.nextLine();
			if (maintainenceString.equalsIgnoreCase("back") || maintainenceString.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			} else if ((maintainenceString.equals("yes")) || (maintainenceString.equals("Yes"))) { // check that input
																									// is either 'yes'
																									// or 'no'
				maintainence = 1;
			} else if ((maintainenceString.equals("no")) || (maintainenceString.equals("No"))) {
				maintainence = 0;
			} else {
				System.out.println(
						"'" + maintainenceString + "' is not an accepted answer. Please type either 'yes' or 'no'.");
				System.out.println();
				maintainenceString = null;
			}
		}

		// Collect user input for station capacity (catches non-integer inputs and
		// numbers less than docks in use)
		int capacity = -1;
		while (capacity == -1) {
			System.out.println("Capacity of station: ");
			input = c.nextLine();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try { // Check whether input is a number
				capacity = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("'" + input + "' is not an accepted answer. Please try again.");
				System.out.println();
			}
			if ((capacity != -1) && (capacity < bikes + pedelecs)) { // check that input is less than docks in use
				System.out.println(
						"The capacity of the station must be greater than or equal to the number of docks in use ("
								+ (bikes + pedelecs) + "). Please try again.");
				System.out.println();
				capacity = -1;
			}
		}

		// Collect user input for station address (catch duplicate addresses)
		String address = null;
		while (address == null) {
			System.out.println("Station Address: ");
			address = c.nextLine();
			if (address.equalsIgnoreCase("back") || address.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			for (Station s : all_stations) { // check that address is unique
				if (address.equals(s.getAddress())) {
					System.out.println("'" + address + "' is already in use. Please try a different address.");
					System.out.println();
					address = null;
				}
			}
		}

		/*
		 * Collect user input for number of kiosks (catch non-integer inputs)
		 * 
		 * While the user may input any number of kiosks, we assume that all we care
		 * about is whether the station has any kiosks. So we enter this data as a true
		 * (there are 1 or more kiosks) or false (there are no kiosks).
		 */
		int kioskNum = -1;
		boolean kiosk = false;
		while (kioskNum == -1) {
			System.out.println("Number of kiosks: ");
			input = c.nextLine();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try {
				kioskNum = Integer.parseInt(input);
			} catch (Exception e) { // check that input is a number
				System.out.println("'" + input + "' is not an accepted number of kiosks. Please try again.");
				System.out.println();
			}
			if (kioskNum > 0) {
				kiosk = true;
			}
		}

		/*
		 * Calculate available docks
		 * 
		 * No need to ask the user about this, since we can calculate it based on other
		 * input.
		 */
		int avail_docks = capacity - (bikes + pedelecs);

		// Create new station object and add it to our station list
		Station myStation = new Station(id, name, bikes, pedelecs, avail_docks, maintainence, capacity, kiosk, address);
		all_stations.add(myStation);

		System.out.println(name + " station (ID #" + id + ") has been added.");
		System.out.println();
		System.out.println();
	}

	/**
	 * Parse data from given csv file and add to all_stations list
	 * <p>
	 * all_stations is a list that contains all station objects
	 * <p>
	 * 
	 * @param data - file path to station-data.csvv
	 */
	public static void addStation(String data) {
		String[] temp = data.split(","); // Turn data into an array
		// Parse out the string
		int id = Integer.parseInt(temp[0]);
		String name = temp[1];
		int bikes = Integer.parseInt(temp[2]);
		int peds = Integer.parseInt(temp[3]);
		int avDocs = Integer.parseInt(temp[4]);
		int mainReq = Integer.parseInt(temp[5]);
		int cap = Integer.parseInt(temp[6]);
		boolean kiosk = false;
		if (Integer.parseInt(temp[7]) != 0) {
			kiosk = true;
		}
		String address = temp[8];
		// Create a new station
		Station myStation = new Station(id, name, bikes, peds, avDocs, mainReq, cap, kiosk, address);
		all_stations.add(myStation);
	}

	/**
	 * Parse data from given csv file and add to all_bikes list
	 * <p>
	 * all_bikes is a list that contains all bike objects
	 * <p>
	 * 
	 * @param data - file path to bikeLocation-data.csv
	 */
	public static void addBike(String data) {
		String[] temp = data.split(","); // Turn data into an array
		// Parse out the string
		String pedID = temp[0];
		int station = Integer.parseInt(temp[1]);
		int inUse = Integer.parseInt(temp[2]);
		int userID = Integer.parseInt(temp[3]);
		boolean bikeStatus = false;
		if (Integer.parseInt(temp[2]) != 0) {
			bikeStatus = true;
		}
		// Add a new bike to existing ones
		Bikes pedelec = new Bikes(pedID, station, inUse, userID);
		all_bikes.add(pedelec);
	}

	/**
	 * Parse data from given csv file and add to all_accounts list
	 * <p>
	 * all_accounts is a list that contains all account objects
	 * <p>
	 * 
	 * @param data - file path to Account.csv
	 */
	public static void addAccount(String data) {
		String[] temp = data.split(","); // Turn data into an array
		// Parse out the string
		int type = Integer.parseInt(temp[0]);
		int id = Integer.parseInt(temp[1]);
		String password = temp[2];
		int membership = Integer.parseInt(temp[3]);
		int balance = Integer.parseInt(temp[4]);

		// Create a new account
		Account account = new Account(type, id, password, membership, balance);
		all_accounts.add(account);
	}

	/**
	 * Parse data from given csv file and add to ride_history list
	 * <p>
	 * ride_history is a list that contains ride records of all users
	 * <p>
	 * 
	 * @param data - file path to ride-history.csv
	 */
	public static void addRideHistory(String data) {
		String[] temp = data.split(","); // Turn data into an array
		// Parse out the string
		int userID = Integer.parseInt(temp[0]);
		String bikeID = temp[1];
		int startStationID = Integer.parseInt(temp[2]);
		int destStationID = Integer.parseInt(temp[3]);
		String startTime = temp[4];
		String endTime = temp[5];
		int completedRide = Integer.parseInt(temp[6]);

		// Create a new ride history
		RideHistory ride = new RideHistory(userID, bikeID, startStationID, destStationID, startTime, endTime,
				completedRide);
		ride_history.add(ride);
	}
//-----------------------------------------------------------------------------------------------------------------		

	/**
	 * Summarize rides after parsing a list of arrays in string form (from A1 code
	 * base).
	 * 
	 * @param data - List of String array containing rides.
	 */
	public static void summarizeRides(List<String[]> data) {
		int rides = data.size(); // Number of rides in file
		long avg_min = 0; // Average ride time
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time_1 = null; // Stores start date and time for each ride
		Date time_2 = null; // Stores end date and time for each ride

		// Iterate through saved data from file
		for (String[] arr : data) {
			// Station id recorded must match a Valley Bike station ID
			boolean station1_exists = false; // Set to false
			boolean station2_exists = false;
			for (Station s : all_stations) {
				if (s.getID() == Integer.parseInt(arr[1])) {
					station1_exists = true;
				}
				if (s.getID() == Integer.parseInt(arr[2])) {
					station2_exists = true;
				}
			}
			if (!station2_exists || !station1_exists) {
				System.out.print("Please make sure station ids are valid.");
				extracted();
				return;
			}

			try {
				// Convert start and end time from file into a simple date format
				// Set Date objects time_1 and time_2 to start and end time from file
				time_1 = f.parse(arr[3]);
				time_2 = f.parse(arr[4]);
			} catch (ParseException e) {
				System.out.print("Please make sure time format follows yyyy-[m]m-[d]d hh:mm:ss.\n");
				extracted();
				return;
			}
			avg_min = (time_2.getTime() - time_1.getTime()) / (60 * 1000) % 60 + avg_min;
		}
		if (time_1.getTime() >= time_2.getTime()) {
			System.out.println("Please make sure all rides are possible.\n");
			extracted();
			return;
		}
		avg_min = avg_min / rides; // Calculate average ride time
		System.out.println(
				"This ride list contains " + rides + " rides with an average ride time of " + avg_min + " minutes.");
	}

	/**
	 * Read a data file with ride information and calls summarizeRides() method
	 * (from A1 code base).
	 * 
	 * @throws ParseException
	 */
	public static void resolveRideData() throws ParseException {
		List<String[]> data = new ArrayList<String[]>(); // Holds rides listed in file
		while (true) {
			// Prompt user for file with ride information
			System.out.println("Enter the file name (including extension) of the file located in data-files: ");
			System.out.println("Type 'back' to return to menu.");
			Scanner a = new Scanner(System.in);
			String input = a.next();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				extracted();
				return;
			}

			// Read file and save data
			try {
				String file = a.next();
				String path = "data-files/" + file;
				BufferedReader csvReader = new BufferedReader(new FileReader(path));
				String row;
				int count = 0;
				// Read the file line by line and save to the list of arrays called "data"
				while ((row = csvReader.readLine()) != null) {
					if (count != 0) { // skips first row which contains column headers
						String[] temp = row.split(",");
						data.add(temp);
					}
					count++;
				}
				csvReader.close();
			} catch (Exception e) {
				System.out.println("That file does not exist. Please try again.\n");
				continue;
			}
			break;
		}
		summarizeRides(data); // Call for summary of rides

	}

	private static void extracted() {
		return;
	}

	// Sort all_stations by station capacity (greatest to smallest)
	public static void sortStationsByCapacity() {
		Comparator<Station> compareByCapacity = new Comparator<Station>() {
			@Override
			public int compare(Station s1, Station s2) {
				return Integer.compare(s1.getCap(), s2.getCap());
			}
		};
		all_stations.sort(compareByCapacity); // sort by capacity (smallest to greatest)
		Collections.reverse(all_stations); // now it's greatest to smallest
	}

	// Redistribute bikes and peds following the ratio of total bikes and peds to
	// total station capacity
	public static void equalizeStations() {
		int totalBikes = totalBikes();
		int totalPedelecs = totalPedelecs();
		int totalCapacity = totalCapacity();

		System.out.println();
		System.out.println("Your current distribution of bikes and pedelecs:");
		System.out.println();
		viewBikeStats();

		for (Station s : all_stations) {
			s.setBikes((int) ((s.getCap() * totalBikes) / totalCapacity));
			s.setPedelecs((int) ((s.getCap() * totalPedelecs) / totalCapacity));
		}

		// Calculate remaining bikes/peds to add to stations
		int remainingBikes = totalBikes - totalBikes();
		int remainingPedelecs = totalPedelecs - totalPedelecs();

		sortStationsByCapacity(); // sort stations by capacity (greatest to smallest)

		// Distribute remaining bikes to largest stations
		if (remainingBikes != 0) {
			for (Station s : all_stations) {
				if (s.getAvDocs() > 0) { // check if station is full
					int newBikeNum = s.getBikes() + 1;
					s.setBikes(newBikeNum);
					remainingBikes = remainingBikes - 1;
					if (remainingBikes == 0) {
						break;
					}
				}
			}
		}

		// distribute remaining pedelecs to largest stations
		if (remainingPedelecs != 0) {
			for (Station s : all_stations) {
				if (s.getAvDocs() > 0) { // check if station is full
					int newPedNum = s.getPeds() + 1;
					s.setPedelecs(newPedNum);
					remainingPedelecs = remainingPedelecs - 1;
					if (remainingPedelecs == 0) {
						break;
					}
				}

			}
		}

		System.out.println("Your updated, equalized distribution of bikes and pedelecs:");
		System.out.println();
		viewBikeStats();
	}

	/**
	 * Record ride data using user input (from A1 code base)
	 */
	public static void recordRide() {
		int station_id = 0;
		int new_ride = 1; // 1 = new ride; 0 = in-progress ride

		// Prompt user for ID
		Scanner a = new Scanner(System.in); // Create a Scanner object
		System.out.print("Please enter your user ID emailed to you: ");
		int user = Integer.parseInt(a.next());
		while (true) { // make sure the station exists
			System.out.print("Please enter station ID at current location: ");
			int id = Integer.parseInt(a.next());
			if (stationExists(id)) {
				station_id = id;
				break;
			} else {
				System.out.print("Invalid input.");
			}
		}
		// Prompt user for type of bike
		while (true) {
			System.out.print("Please enter one of the following options (B=bike, P=Pedelec): ");
			String type = a.next();
			if (type.equals("P")) {
				break;
			} else if (type.equals("B")) {
				System.out.println("No bikes available now."); // Assumptions: no bikes are available at any of the
																// stations
			} else {
				System.out.println("Invalid input.");
			}
		}

		// Check to see if user has an in-progress ride. If so, we are recording the end
		// of a ride.
		for (Ride r : all_rides) {
			if (r.getUser() == user && r.getEnd() == null) { // Check to see if the user has an in-progress ride
				new_ride = 0;
				// Edit station and ride data
				for (Station station : all_stations) {
					if (station.getID() == station_id) {
						if (station.getAvDocs() != 0) { // If there is capacity at the dock
							System.out.print('\n');
							System.out.println("Thank you for riding. Please check your email for a recipt");
							station.setPedelecs(station.getPeds() + 1); // add one to pedelecs count
							station.setDocks(station.getAvDocs() - 1); // subtract one from available docks
							Timestamp time = new Timestamp(System.currentTimeMillis()); // Transaction timestamp
							r.setEnd(time); // Set "end" time field to current time
							r.setTo(station_id); // Set "to" field to current dock location
						} else { // If there is no capacity
							System.out.println("No capacity here. Please park at another station. You can print the "
									+ "station list to see capacity.");
						}
					}
				}
			}
		}

		// Enter a new ride
		if (new_ride == 1) {
			// Iterate through stations to find current station data
			for (Station station : all_stations) {
				if (station.getID() == station_id) {
					if (station.getPeds() != 0) { // If station has available bikes
						System.out.print('\n');
						System.out.println(
								"Ride recorded and started. You will be charged once the bike is returned at any of our docks.");
						station.setPedelecs(station.getPeds() - 1); // Subtract one pedelec to station
						station.setDocks(station.getAvDocs() + 1); // Add one available docks to station
						// Create a new ride
						Ride r = new Ride(user);
						Timestamp time = new Timestamp(System.currentTimeMillis()); // the time and date of the
																					// transaction
						r.setStart(time); // Set start time of Ride object
						r.setFrom(station_id); // Set "from" field to current dock location
						all_rides.add(r);
					} else {
						System.out.println("Invalid transaction. No bikes are available at this station.");
					}
				}
			}
		}
	}

	/**
	 * Allow a new user to create an account. Only users can create accounts, not
	 * ValleyBike administrators.
	 */
	public static void createAccount() {
		int ID = 0;
		String password = null;
		int type = 0;
		int membership = 0;
		int balance = 0;
		Scanner c = new Scanner(System.in); // Create a Scanner object

		Random rand = new Random();
		Integer randomID = rand.nextInt(9999 - 1000) + 1000;
		boolean repeat = false;

		while (ID == 0) {
			repeat = false;
			for (Account a : all_accounts) {
				if (randomID.equals(a.getID())) {
					randomID = rand.nextInt(9999 - 1000) + 1000;
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

		// Input password
		while (password == null) {
			System.out.println("Create password: ");
			password = IDtext.getText(); // c.nextLine();
			if (password.equalsIgnoreCase("back") || password.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
		}

		Account user = new Account(type, ID, password, membership, balance);
		all_accounts.add(user);
		currentUser = user;

		saveAccountList();

		System.out.println("Congrats! Your account has been created. Your user ID is " + ID + ".");

		// Tell user account creation was successful
		congrats = new JLabel("Congrats! Your account has been created. Your user ID is " + ID + ".");
		congrats.setBounds(80, 150, 500, 20);

		// Activate log out button

		home = new JButton("Home Menu");
		home.setBounds(200, 180, 150, 40);

		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove anything currently on the GUI
				frame.remove(congrats);
				frame.remove(home);
				frame.repaint();
				drawHomePage();

			}
		});

		// Update the GUI
		frame.remove(IDtext);
		frame.remove(userPass);
		frame.remove(enterCreate);
		frame.add(congrats);
		frame.add(home);

		frame.repaint();

	}

	/**
	 * Allows a user to log into ValleyBike
	 * 
	 * @throws ParseException
	 */
	public static void logIn() throws ParseException {
		// Scanner s = new Scanner(System.in); // Create a Scanner object
		String input;
		Integer ID = 0;
		boolean trueID = false;
		boolean rightPass = false;

		// setting up labels for wrong info entered
		wrongID = new JLabel("ID was not found. Please double check your ID and try again.");
		wrongID.setBounds(100, 230, 500, 20);
		wrongPass = new JLabel("Password was not found. Please double check your password and try again.");
		wrongPass.setBounds(100, 310, 500, 20);

		// receives and checks the username

		// System.out.println("Please enter your user ID: ");

		// while (true) {
		input = IDtext.getText(); // s.next(); //
		for (Account a : all_accounts) {
			// gets the ID and checks if it is equal to input
			if (Integer.toString(a.getID()).equals(input)) {
				ID = a.getID();
				trueID = true;

				break;
			}

		}
//			if (trueID == true) {
//				break;
//			} 
		if (trueID == false) {
			System.out.println("Wrong ID");

			frame.add(wrongID);
			frame.repaint();
			return;
		} else {
			frame.remove(wrongID);
			// frame.revalidate();
			frame.repaint();
		}

		// System.out.println("That user ID is incorrect. Please try again.");
		// }

		// receives and checks the password

		// System.out.println("Please enter your password: ");
		// Scanner c = new Scanner(System.in);

		// while (true) {
		input = passText.getText(); // c.next(); //
		for (Account a : all_accounts) {
			if (ID.equals(a.getID())) {
				if (input.equals(a.getPassword())) {
					rightPass = true;
					frame.remove(wrongPass);
					frame.validate();
					frame.repaint();
					currentUser = a;
					break;
				}
			}

		}
//			if (rightPass == true) {
//				break;
//			}
		if (rightPass == false) {
			System.out.println("wrong password");

			frame.add(wrongPass);
			frame.repaint();
			return;
		}

		// System.out.println("That password is incorrect. Please try again.");
		// }

		System.out.println("Current User: " + currentUser.getID());

		// Setting up page for successful log in
		welcomeText.setText("Login successful! Welcome to ValleyBike user: " + currentUser.getID());
		welcomeText.setBounds(100, 100, 300, 20);

		// Setting up the User and Admin menu options
		menuType = new JLabel();
		menuType.setBounds(118, 2, 200, 20);

		menuPanel = new JPanel();
		menuPanel.setBounds(50, 120, 400, 300);
		menuPanel.setBackground(Color.gray);

		// adding instructions to the GUI
		consoleLabel = new JLabel("Please proceed to console once you have selected a menu item.");
		consoleLabel.setBounds(15, 20, 500, 50);
		menuPanel.add(consoleLabel);

		// if a user logged in, display the user menu
		if (currentUser.getType() == 0) {
			menuType.setText("Press Mouse for User Menu:");

			final JLabel itemSelected = new JLabel();
			itemSelected.setBounds(50, 150, 200, 20);
			itemSelected.setHorizontalAlignment(JLabel.CENTER);
			itemSelected.setSize(400, 100);

			// creating a popupmenu
			final JPopupMenu popupmenu = new JPopupMenu("User");
			JMenuItem viewStations = new JMenuItem("View Station List");
			JMenuItem membership = new JMenuItem("Purchase/Change Membership");
			JMenuItem cancelMem = new JMenuItem("Cancel Membership");
			JMenuItem startRd = new JMenuItem("Start Ride");
			JMenuItem endRd = new JMenuItem("End Ride");
			JMenuItem viewHis = new JMenuItem("View your ride history");
			JMenuItem viewBal = new JMenuItem("View total money spent with ValleyBike");

			// adding items to the menu
			popupmenu.add(viewStations);
			popupmenu.add(membership);
			popupmenu.add(cancelMem);
			popupmenu.add(startRd);
			popupmenu.add(endRd);
			popupmenu.add(viewHis);
			popupmenu.add(viewBal);

			// creating event listeners for menu items
			menuPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					popupmenu.show(menuPanel, e.getX(), e.getY());
				}
			});
			viewStations.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("View Station List in console.");
					viewStationList();
				}
			});
			membership.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Follows steps in console to purchase/change
					// membership.");
					buyMem();
				}
			});
			cancelMem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Follow steps in console to cancel membership.");
					cancelMem();
				}
			});
			startRd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Follow steps in console to start ride.");
					rentBike();
				}
			});
			endRd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Follow steps in console to end ride.");
					returnBike();
				}
			});

			viewHis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Ride History MenuItem clicked.");
					// inConsole();
					userHistory();
				}
			});

			viewBal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					itemSelected.setText("Balance MenuItem clicked.");
					// inConsole();
					System.out.println("You have spent $" + currentUser.getBalance() + " with ValleyBike.");
				}
			});

			// add elements to panel
			menuPanel.add(menuType);
			menuPanel.add(popupmenu);
			menuPanel.add(itemSelected);

		}
		// if an admin logged in, display the admin menu
		else if (currentUser.getType() == 1) {
			menuType.setText("Press Mouse for Admin Menu:");

//			final JLabel itemSelected = new JLabel();
//			itemSelected.setHorizontalAlignment(JLabel.CENTER);
//			itemSelected.setSize(400, 100);

			// "[1] View station list.", "[2] Add station.",
			// "[3] Save station list.", "[4] Equalize stations.", "[5] Resolve ride data.",
			// "[6] View total ride history.", "[7] Resolve maintenance issue."

			// creating a popupmenu
			final JPopupMenu popupmenu = new JPopupMenu("User");
			JMenuItem viewStations = new JMenuItem("View Station List");
			JMenuItem addStation = new JMenuItem("Add Station");
			JMenuItem saveStations = new JMenuItem("Save Station List");
			JMenuItem equalStations = new JMenuItem("Equalize Stations");
			JMenuItem trackBikes = new JMenuItem("Track Bikes");
			JMenuItem resolveRides = new JMenuItem("Resolve Ride Data");
			// adding items to the menu
			popupmenu.add(viewStations);
			popupmenu.add(addStation);
			popupmenu.add(saveStations);
			popupmenu.add(equalStations);
			popupmenu.add(trackBikes);
			popupmenu.add(resolveRides);

			// creating event listeners for menu items
			menuPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					popupmenu.show(menuPanel, e.getX(), e.getY());
				}
			});
			viewStations.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("View Station List in console.");
					viewStationList();
				}
			});
			addStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Follow steps in console to add station.");
					userAddStation();
				}
			});
			saveStations.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Station list saved.");
					saveStationList();
				}
			});
			equalStations.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Stations equalized. Check console for updated list.");
					equalizeStations();
				}
			});
			trackBikes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Track a bike MenuItem clicked.");
					// inConsole();
					trackBikes();
				}
			});
			resolveRides.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// itemSelected.setText("Rides summarized. View summary in console.");
					try {
						resolveRideData();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			// add elements to panel
			menuPanel.add(menuType);
			menuPanel.add(popupmenu);
			// menuPanel.add(itemSelected);

			// add elements to panel
			menuPanel.add(menuType);
		}

		// update frame and repaint
		frame.remove(userID);
		frame.remove(IDtext);
		frame.remove(userPass);
		frame.remove(passText);
		frame.remove(forgot);
		frame.remove(enterLogin);

		frame.add(welcomeText);
		frame.add(menuPanel);
		frame.add(logout);

		frame.repaint();

	}

	/**
	 * Allows the user to purchase a membership.
	 */
	public static void buyMem() {
		int mem = 0;
		String choice = "-1";
		String[] num = new String[] { "1", "2", "3" }; // membership options
		List<String> memOptions = Arrays.asList(num);

		String[] mems = new String[] { "Founding Member", "Annual", "Monthly" }; // membership types
		List<String> memsOptions = Arrays.asList(mems);

		String[] costs = new String[] { "90", "80", "20" }; // membership costs
		List<String> costOptions = Arrays.asList(costs);

		if (currentUser.getMembership() != 0) {
			System.out.println("Your current membership is: " + memsOptions.get(currentUser.getMembership() - 1) + ".");
		}

		System.out.println("We have 3 membership options available.");

		Scanner s = new Scanner(System.in); // Create a Scanner object
		while (true) {
			System.out.println("Please select a membership");
			System.out.println("[1]Founding Member: $90 annually. The first 60 minutes of each ride are included.");
			System.out.println("[2]Annual Membership: $80 annually. The first 45 minutes of each ride are included.");
			System.out.println("[3]Monthly Membership: $20 monthly. The first 45 minutes of each ride are included.");
			System.out.println("Type 'back' to return to menu.");

			choice = s.next();
			System.out.println(choice);
			choice = s.nextLine();
			// TODO: replaceabove line with below line
			// choice = s.nextLine().strip();

			if (choice.equalsIgnoreCase("back") || choice.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			if (memOptions.contains(choice)) {
				mem = Integer.parseInt(choice);
				if (currentUser.getMembership() == mem) {
					System.out.println("\nThat is your current membership.\n");
					continue;
				}
				System.out.println("You have selected the " + memsOptions.get(Integer.parseInt(choice) - 1)
						+ " membership. Are you sure? Y/N");
				Scanner c = new Scanner(System.in); // Create a Scanner object
				String input = c.next();
				if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
					int cost = Integer.parseInt(costOptions.get(Integer.parseInt(choice) - 1));
					boolean quit = payment(cost);
					if (quit) {
						continue;
					}
					currentUser.setMembership(Integer.parseInt(choice) - 1);
					break;
				} else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
					continue;
				} else {
					System.out.println("That input is incorrect. Please try again.");
					continue;
				}

			}

			System.out.println("That input is incorrect. Please try again.");
			continue;

		}
		for (Account a : all_accounts) {
			if (a == currentUser) {
				a.setMembership(mem);
			}
		}
		saveAccountList();
		System.out.println("Congratulations! You now have a ValleyBike " + memsOptions.get(Integer.parseInt(choice) - 1)
				+ " membership!");
	}

	/**
	 * Allow user to cancel their membership.
	 */
	public static void cancelMem() {
		String[] mems = new String[] { "Founding Member", "Annual", "Monthly" }; // Membership types
		List<String> memsOptions = Arrays.asList(mems);

		while (true) {
			if (currentUser.getMembership() == 0) {
				System.out.println("You don't have a membership to cancel!");
				return;
			}
			if (currentUser.getMembership() != 0) {
				System.out.println(
						"Your current membership is: " + memsOptions.get(currentUser.getMembership() - 1) + ".");
				System.out.println("Are you sure you want to cancel your membership? Y/N");
				Scanner c = new Scanner(System.in); // Create a Scanner object
				String input = c.next();
				if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
					c.close();
					System.out.println();
					extracted();
					return;
				}
				if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
					c.close();
					currentUser.setMembership(0);
					System.out.println("\nYour membership has been canceled.\n");
					return;
				} else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
					c.close();
					return;
				}
				c.close();
			}

		}

	}

	/**
	 * Allow user to make a payment.
	 * 
	 * @param cost - Cost of ride
	 */
	public static boolean payment(int cost) {
		boolean accepted = true;
		boolean quit = false;
		Random rand = new Random();
		int chance = rand.nextInt(9);
		Scanner s = new Scanner(System.in); // Create a Scanner object
		String input;
		System.out.println("Your total is $" + cost + ".");
		while (true) {
			System.out.println("Please enter your credit/debit card number: ");
			input = s.nextLine().replace(" ", "").replace("-", "");

			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				quit = true;
				return quit;
			}

			if (input.length() != 16) {
				System.out.println("Please enter a valid card number.");
				accepted = false;
				continue;
			} else if (input.length() == 16) {
				for (Character a : input.toCharArray()) {
					String b = a.toString();
					try { // check that input is a number
						int num = Integer.parseInt(b);
					} catch (Exception e) {
						System.out.println("Please enter a valid card number.");
						accepted = false;
						break;
					}
				}
			}

			if (accepted == false) {
				continue;
			} else {

				if (chance <= 9) {
					System.out.println("Your payment has been accepted.");
					int oldBal = currentUser.getBalance();
					currentUser.setBalance(cost + oldBal);
					return quit;
				} else {
					System.out.println("There was a problem proccessing your payment. Please try again.");
					continue;
				}
			}
		}
	}

	/**
	 * Request maintenance.
	 */
	public static void maintenance() {
		Scanner c = new Scanner(System.in); // Create a Scanner object
		String input;
		boolean done = false;
		int inputid;
		while (true) {
			System.out.println("Please enter a station number.");
			System.out.println("Type 'back' to return to menu.");
			input = c.next();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try { // check that input is a number
				inputid = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("\nNo such station exists.");
				continue;
			}
			boolean matchid = false;
			for (Station s : all_stations) {
				int id = s.getID();
				if (id == inputid) {
					if (s.getMainReq() == 0) {
						System.out.println("This station currently has no active maintenance requests.");
						matchid = true;
						break;
					} else {
						int oldMain = s.getMainReq();
						s.setReq(oldMain - 1);
						System.out.println("A worker has been sent to perform maintenance.");
						done = true;
						matchid = true;
						break;
					}
				}
			}

			if (matchid == false) {
				System.out.println("\nNo such station exists.");
				continue;
			}
			if (done == true) {
				break;
			}
		}
		saveStationList();
	}

	public static void trackBikes() {
		String bikeID;
		String missingBike; // Bike we are searching for
		int bikeLocation; // Where the bike is located (at a station or with a user)
		Scanner adminInput = new Scanner(System.in);
		System.out.print("Please enter the bike ID of the pedelec bike you are searching for.");
		System.out.println("Type 'back' to return to menu.");
		missingBike = adminInput.nextLine();
		if (missingBike.equalsIgnoreCase("back") || missingBike.equalsIgnoreCase("b")) {
			adminInput.close();
			System.out.println();
			extracted();
			return;
		}
		for (Bikes b : all_bikes) {
			if (b.getbikeID() == missingBike && b.getBikeStatus() != 1) {
				bikeID = b.getbikeID();
				bikeLocation = b.getStation();
				System.out.print("Bike: " + bikeID + "is at Station: " + bikeLocation);
				System.out.println();
				break;
			} else if (b.getBikeStatus() == 1) {
				bikeLocation = b.getUser();
				bikeID = b.getbikeID();
				System.out.print("Bike: " + bikeID + " is currently checked out by User: " + bikeLocation + ". ");
				break;

			}
		}
		adminInput.close();
		String adminDec;
		System.out.print("Do you want to search for another bike? y/n?");
		Scanner adminRes = new Scanner(System.in);
		adminDec = adminRes.nextLine();
		if (adminDec.equalsIgnoreCase("yes") || adminDec.equalsIgnoreCase("y")) {
			adminRes.close();
			trackBikes();
		} else if (adminDec.equalsIgnoreCase("no") || adminDec.equalsIgnoreCase("n")) {
			adminRes.close();
			System.out.println();
			extracted();
			return;
		}
	}
// -----------------------------------------------------------------------------------------------------------------//
	// **RIDE HISTORY**

	/**
	 * Allow user to rent bike given that:
	 * <p> (i) User is logged in <p>
	 * <p> (ii) There are available bikes at user's current station <p>
	 */
	public static void rentBike() {
		int user = currentUser.getID();
		int stationId = -1; // Default value of stationId
		int rideNotInProgress = 1; // 1 = new ride; 0 = in-progress ride
		int end = 1; // Variable used to enter/exit while loop
		String bikeID = "0"; // Default value of bikeID

		// Create Scanner object
		Scanner userInput = new Scanner(System.in);

		// Loop through ride record to check if user has a ride in progress
		for (RideHistory rideObj : ride_history) {
			if (rideObj.getUserID() == user) {
				if (rideObj.getEndTime().equals("0") && rideObj.getCompletedRide() == 0) {
					rideNotInProgress = 0;

					// If ride is in progress do not allow user to rent bike:
					System.out.println("You have already rented a bike. Please return current bike to rent another.");
					end = 2;
					break; // Break out of for loop

				}
			}

		}

		// If user does not have a ride in progress, prompt the user to enter stationID
		while (stationId == -1 && end ==1) {
			System.out.print("Please enter station ID at current location: ");
			System.out.println("Type 'back' to return to menu.");
			String input = userInput.nextLine();
			if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
				System.out.println();
				extracted();
				return;
			}
			try { // If user enters a station ID, check whether station exists, if yes then set
					// stationId to entered value
				if (stationExists(Integer.parseInt(input))) {
					
					stationId = Integer.parseInt(input);
					break;
				} else { // Else prompt user to enter stationID that exists
					System.out.println("No such station exists. \nPlease enter valid station ID.");
				}
			} catch (Exception e) { // Check that input is a number
				System.out.println("'" + input + "' is not an accepted Station ID. Please try again.");
			}
		}

		if (end == 1 && stationId !=-1) {
			if (rideNotInProgress == 1) { // If user has no rides in progress, allow user to rent bike
				// Iterate through stations to find current station data
				for (Station station : all_stations) {
					if (station.getID() == stationId) {
						if (station.getPeds() != 0) {// If station has available bikes
							while (bikeID.equals("0")) { // Prompt user to select an available bike at chosen station
								System.out.println(
										"Please enter the bikeID of a bike from the following lists of bikes available at station "
												+ stationId);
								System.out.println("Or type 'back' to return to menu.");
								viewBikesAtStation(stationId);

								String input3 = userInput.nextLine();
								if (input3.equalsIgnoreCase("back") || input3.equalsIgnoreCase("b")) {
									System.out.println();
									extracted();
									return;
								}
								try { // If chosen bike is available to use, set bikeID to that of the chosen bike
									if (bikeNotInUse(input3, stationId)) {
										bikeID = input3;
										break;
									} else { // Else prompt user to enter valid bikeID
										System.out.println("No such bike exists. \nPlease enter valid bike ID.");
									}
								} catch (Exception e) { // Check that input is a number
									System.out
											.println("'" + input3 + "' is not an accepted bike ID. Please try again.");
								}
							}
							System.out.print('\n');
							System.out.println("You rented out " + bikeID + " bike. Your account was charged.");
							station.setPedelecs(station.getPeds() - 1); // Subtract one pedelec to station
							station.setDocks(station.getAvDocs() + 1); // Add one available docks to station
							saveStationList(); // Save changes to station-data.csv

							// Get current time
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
							LocalDateTime startTime = LocalDateTime.now();
							String time = dtf.format(startTime);

							// Create a new ride_history obj and add it to ride_history list
							RideHistory obj = new RideHistory(user, bikeID, stationId, 0, time, "0", 0);
							ride_history.add(obj);

							saveRideHistory(); // Save changes to ride-history.csv

							for (Bikes b : all_bikes) { // loop through all_bikes
								if (b.getbikeID().equals(bikeID)) { // Get bike that was just rented out, and set its
																	// status to in use
									b.setBikeStatus(1); // 1 = in-use; 0 = not in-use
									b.setUserID(currentUser.getID()); // Update the userID of the user using the bike
									saveBikeLocation(); // Save changes to bikeLocation-data.csv
								}
							}
							break;
						} else { // If there's no availables bikes at station, ask user to use a different
									// station
							System.out.println(
									"Invalid transaction. No bikes are available at this station. Please try again.");
						}
					}
				}
			}

		}
	}

	/**
	 * Allow user to return bike given that:
	 * <p> (i) User is logged in <p>
	 * <p> (ii) There are available docks at user's current station <p>
	 */
	public static void returnBike() {
		int user = currentUser.getID();
		int stationId = -1;
		int rideNotInProgress = 0; // 1 = new ride; 0 = in-progress ride
		int end = 1;
		// Create Scanner object
		Scanner userInput1 = new Scanner(System.in);

		// If user has no-ride in progress then ask user to rent a bike before they can
		// return
		for (RideHistory r : ride_history) {
			if (r.getUserID() == user) {
				if (!r.getEndTime().equals("0") && r.getCompletedRide() == 0) {
					rideNotInProgress = 1;
					// If ride is not in progress do not allow user to rent bike:
					System.out.println("You did not check out any bike. Please rent a bike before trying to return it.");
					end = 2;
					break;

				}

			}

		}

		// Get station id of the station the user want to return bike to
		while (stationId == -1) {
			System.out.print("Please enter station ID at current location: ");
			System.out.println("Type 'back' to return to menu.");
			String input1 = userInput1.nextLine();

			if (input1.equalsIgnoreCase("back") || input1.equalsIgnoreCase("b")) {

				System.out.println();
				extracted();
				return;
			}
			try {
				if (stationExists(Integer.parseInt(input1))) { // Check if such a station exists
					stationId = Integer.parseInt(input1);
				} else { // Else ask user to re-enter valid station ID
					System.out.println("No such station exists. \nPlease enter valid station ID.");
				}
			} catch (Exception e) { // Check that input is a number
				System.out.println("'" + input1 + "' is not an accepted Station ID. Please try again.");
			}
		}
		// Check to see if user has an in-progress ride. If so, we are recording the end
		// of a ride.
		if (rideNotInProgress == 0 && end == 1) {
			for (RideHistory r : ride_history) {
				if (r.getUserID() == currentUser.getID() && r.getCompletedRide() == 0) {
					// Edit station and ride data

					for (Station station : all_stations) {
						if (station.getID() == stationId) {
							if (station.getAvDocs() != 0) { // If there is capacity at the dock

								System.out.print('\n');
								System.out.println("Thank you for riding. Please check your email for a recipt.");
								station.setPedelecs(station.getPeds() + 1); // Add one to pedelecs count
								station.setDocks(station.getAvDocs() - 1); // Subtract one from available docks
								saveStationList();
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
								LocalDateTime startTime = LocalDateTime.now();
								String time = dtf.format(startTime);
								r.setEnd(time);
								r.setTo(stationId);
								r.setCompletedRide(1);

								saveRideHistory();

								for (Bikes b : all_bikes) { // Loop through all_bikes list to update bike stats
									if (b.getbikeID().equals(r.getBikeID())) {
										b.setBikeStatus(0);
										b.setUserID(0);
										b.setStation(stationId);
										saveBikeLocation();
									}
								}
								break;

							} else { // If there is no capacity
								System.out.println("No capacity here. Please park at another station. You can print the "
												+ "station list to see capacity.");
							}

						}

					}
				}

				// break;

			}
		}
	}

	/**
	 * Allows user to view their personal ride history.
	 */
	public static void userHistory() {
		int id = currentUser.getID();
		List<RideHistory> userRides = new ArrayList<>();
		for (RideHistory r : ride_history) {
			if (r.getUserID() == id) {
				userRides.add(r);
			}
		}

		if (!userRides.isEmpty()) {
			System.out.println("Here is your ride history:");
			for (RideHistory r : userRides) {
				System.out.println("Bike ID: " + r.getBikeID() + ". Start time: " + r.getStartTime()
						+ ". Start station: " + r.getStartStation() + ". End time : " + r.getEndTime()
						+ ". End station: " + r.getDestStation());
			}
		} else {
			System.out.println("You don't have any ride history!");
		}
	}

	/**
	 * This method draws the start page for the ValleyBike Simulator. Most of the
	 * GUI elements were built based on code from:
	 * https://www.javatpoint.com/java-swing
	 */
	public static void drawHomePage() {

		// stops menu from printing every time enter is pressed in console
		gui = true;

		// setting up the panel
		homePanel = new JPanel();
		homePanel.setBounds(100, 100, width - 200, height - 200);
		homePanel.setBackground(Color.gray);

		// creating the welcome label
		welcomeText = new JLabel("Welcome to the ValleyBike Simulator.");
		welcomeText.setBounds(110, 120, 250, 20);

		please = new JLabel("  Please log in or create an account.");
		please.setBounds(110, 150, 250, 20);

		// creating the buttons
		home = new JButton(logoIMG);
		home.setBounds(0, 0, 230, 90);

		// don't know why these two buttons are not changing location no matter what I
		// do to the setBounds
		logIn = new JButton("Log In");
		logIn.setBounds(50, 50, 100, 40);// x axis, y axis, width, height
		createAccount = new JButton("Create Account");
		createAccount.setBounds(500, 50, 100, 40);// x axis, y axis, width, height

		enterCreate = new JButton("Enter");
		enterCreate.setBounds(100, 230, 100, 40);

		enterLogin = new JButton("Log In");
		enterLogin.setBounds(100, 340, 100, 40);

		logout = new JButton("Log Out");
		logout.setBounds(width - 120, 20, 100, 40);

		// adding elements to the GUI
		homePanel.add(welcomeText);
		homePanel.add(please);
		homePanel.add(logIn);
		homePanel.add(createAccount);
		frame.add(home);
		frame.add(homePanel);

		// setting up action listeners for the buttons
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove elements from home page
				frame.remove(homePanel);

				// draw elements of logIn page
				userID = new JLabel("Please enter your ID");
				userID.setBounds(100, 170, 250, 20);

				IDtext = new JTextField();
				IDtext.setBounds(100, 200, 150, 20);

				userPass = new JLabel("Please enter your password");
				userPass.setBounds(100, 260, 250, 20);

				passText = new JTextField();
				passText.setBounds(100, 290, 150, 20);

				forgot = new JLabel("Forgot password or ID? Please call customer service at: 1-234-567-BIKE");
				forgot.setBounds(100, 400, 500, 20);

				// add to frame and repaint
				frame.add(userID);
				frame.add(IDtext);
				frame.add(userPass);
				frame.add(passText);
				frame.add(forgot);
				frame.add(enterLogin);

				frame.repaint();

			}
		});

		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove elements from home page
				frame.remove(homePanel);

				// draw elements of createAccount page
				userPass = new JLabel("Please enter your desired password");
				userPass.setBounds(100, 170, 250, 20);

				IDtext = new JTextField();
				IDtext.setBounds(100, 200, 150, 20);

				// add to frame and repaint
				frame.add(userPass);
				frame.add(IDtext);
				frame.add(enterCreate);

				frame.repaint();

			}
		});

		enterCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAccount();

			}
		});

		enterLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logIn();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure user is not logged in
				currentUser = null;

				// set window back to home page
				frame.remove(menuPanel);
				frame.remove(welcomeText);
				frame.remove(logout);
				frame.repaint();

				drawHomePage();

			}
		});

		// next is setting up a action listener for home that returns us to the main
		// page

		// setting the size, layout, and visibility of the frame
		frame.setSize(width, height);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

//-----------------------------------------------------------------------------------------------------------------//	

	/**
	 * Main Method
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		// creating instance of JFrame
		frame = new JFrame();

		// initializing all array lists
		all_stations = new ArrayList<Station>();
		all_rides = new ArrayList<Ride>();
		ride_history = new ArrayList<RideHistory>();
		all_accounts = new ArrayList<Account>();
		all_bikes = new ArrayList<Bikes>();

		// Allow user to log in or create an account
		Scanner s = new Scanner(System.in); // Create a Scanner object
		System.out.println("Welcome to the ValleyBike Simulator.");
		System.out.println("Please log in or create an account.");

		// fill all_accounts
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data-files/Accounts.csv"));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if (count != 0) {
					addAccount(row); // Add account to all_accounts
				}
				count++;
			}
			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// fill all_stations
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if (count != 0) {
					addStation(row); // Add station to all_stations
				}
				count++;
			}
			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Fill ride_history
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data-files/ride-history.csv"));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if (count != 0) {
					addRideHistory(row); // Add ride_history obj to ride_history
				}
				count++;
			}
			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Fill all_bikes
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data-files/bikeLocation-data.csv"));
			String row;
			int count = 0;
			while ((row = csvReader.readLine()) != null) {
				if (count != 0) {
					addBike(row); // Add account to all_accounts
				}
				count++;
			}
			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// draws main page
		drawHomePage();

		String[] num01 = new String[] { "0", "1" }; // log in options
		List<String> logInOptions = Arrays.asList(num01);
		String choice = "";

		while (currentUser == null) {
			System.out.println("[0] Log in.");
			System.out.println("[1] Create account.");
			choice = s.next();
			if (logInOptions.contains(choice)) {
				break;
			}
			if (choice.equals("0")) { // log in
				logIn();
			} else if (choice.equals("1")) { // create a new account
				createAccount();
			}
		}

		while (gui == false) {
			// menu for users
			if (currentUser.getType() == 0) {
				while (true) {
					if (welcome == false) {
						System.out.println("Welcome to ValleyBike, user " + currentUser.getID() + ".");
						welcome = true;
					}
					String[] num = new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }; // menu options
					List<String> menuOptions = Arrays.asList(num);
					String input;

					Scanner c = new Scanner(System.in); // Create a Scanner object
					System.out.println("Please choose from one of the following menu options:");
					String[] options = new String[] { "[0] Quit Program.", "[1] View station list.",
							"[2] Purchase/Change Membership.", "[3] Cancel Membership.", "[4] Start ride.",
							"[5] End ride.", "View total money spent on ValleyBike.", "[7] View your ride history." };
					while (true) {
						for (int i = 0; i < options.length; i++) {
							System.out.println(options[i]);
						}
						input = s.next();
						if (menuOptions.contains(input)) {
							break;
						}
					}

					if (input.equals("0")) { // quit program
						System.exit(0);
					} else if (input.equals("1")) { // view station list
						viewStationList();
					} else if (input.equals("2")) { // purchase/change membership
						buyMem();
					} else if (input.equals("3")) { // cancel membership
						cancelMem();
					} else if (input.equals("4")) { // start ride
						rentBike();

					} else if (input.equals("5")) { // end ride
						returnBike();
					} else if (input.equals("6")) { // view balance (meaning total money spent on ValleyBike)
						System.out.println("You have spent $" + currentUser.getBalance() + ".");
					} else if (input.equals("7")) { // view user's ride history
						userHistory();
					}

				}
			}

			// Menu for admins
			else if (currentUser.getType() == 1) {
				while (true) {
					if (welcome == false) {
						System.out.println("Welcome to ValleyBike, admin " + currentUser.getID() + ".");
						welcome = true;
					}
					String[] num = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }; // menu options
					List<String> menuOptions = Arrays.asList(num);
					String input;

					Scanner c = new Scanner(System.in); // Create a Scanner object
					System.out.println("Please choose from one of the following menu options:");
					String[] options = new String[] { "[0] Quit Program.", "[1] View station list.", "[2] Add station.",
							"[3] Save station list.", "[4] Equalize stations.", "[5] Resolve ride data.",
							"[6] View total ride history.", "[7] Resolve maintenance issue." };
					while (true) {
						for (int i = 0; i < options.length; i++) {
							System.out.println(options[i]);
						}
						input = s.next();
						if (menuOptions.contains(choice)) {
							break;
						}
					}

					if (input.equals("0")) { // quit program
						System.exit(0);
					} else if (input.equals("1")) { // view station list
						viewStationList();
					} else if (input.equals("2")) { // add station
						userAddStation();
					} else if (input.equals("3")) { // save station list
						saveStationList();
					} else if (input.equals("4")) { // equalize stations
						equalizeStations();
					} else if (input.equals("5")) { // resolve ride data
						resolveRideData();
					} else if (input.equals("6")) { // track bikes
						trackBikes();
						System.out.println("We are working on locating this bike.");
					} else if (input.equals("7")) { // view total ride history
						viewRideHistory();
					} else if (input.equals("8")) { // resolve maintenance issue
						maintenance();
					}
				}

			}
		}
	}

//-----------------------------------------------------------------------------------------------------------------//
}
