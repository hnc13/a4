import java.util.HashMap;
import java.util.Scanner;

public class LocateBikes {
	private static String pedToFind;
	private static int bikeLocation;
	private static Scanner l;
	public static void pedLocation(String[] args) {
		
		System.out.println("Please enter pedelec ID: "); /*Will correspond to
		pedelec bike that the user checks out */
		l = new Scanner(System.in);
		pedToFind = l.next(); 
		HashMap < String, Integer > stations = new HashMap<String, Integer>();
		
	    stations.put("P101",21);
		stations.put("P102",21);
		stations.put("P103",21);
		stations.put("P104",21);
		
		stations.put("P105",24);
		stations.put("P106",24);
		
		stations.put("P107",30);
		stations.put("P108",30);
		stations.put("P109",30);
		stations.put("P110",30);
		stations.put("P111",30);
		stations.put("P112",30);
		stations.put("P113",30);
		stations.put("P114",30);
		
		stations.put("P115",25);
		stations.put("P116",25);
		stations.put("P117",25);
		stations.put("P118",25);
		stations.put("P119",25);
		stations.put("P120",25);
		
		stations.put("P121",29);
		stations.put("P122",29);
		stations.put("P123",29);
		stations.put("P124",29);
		
		stations.put("P125",33);
		stations.put("P126",33);
		stations.put("P127",33);
		stations.put("P128",33);
		stations.put("P129",33);
		stations.put("P130",33);
		stations.put("P131",33);
		stations.put("P132",33);
		
		stations.put("P133",20);
		stations.put("P134",20);
		stations.put("P135",20);
		stations.put("P136",20);
		stations.put("P137",20);
		stations.put("P138",20);
		
		stations.put("P139",26);
		stations.put("P140",26);
		stations.put("P141",26);
		stations.put("P142",26);
		stations.put("P143",26);
		
		stations.put("P144",23);
		stations.put("P145",23);
		stations.put("P146",23);
		stations.put("P147",23);
		stations.put("P148",23);
		stations.put("P149",23);
		
		stations.put("P150",27);
		stations.put("P151",27);
		stations.put("P152",27);
		
		stations.put("P153",31);
		stations.put("P154",31);
		stations.put("P155",31);
		
		stations.put("P156",28);
		stations.put("P157",28);
		stations.put("P158",28);
		stations.put("P159",28);
		stations.put("P160",28);
		
		stations.put("P161",32);
		stations.put("P162",32);
		stations.put("P163",32);
		stations.put("P164",32);
		stations.put("P165",32);
		stations.put("P166",32);
		stations.put("P167",32);
		stations.put("P168",32);
		
		stations.put("P169",22);
		stations.put("P170",22);
		stations.put("P171",22);
		stations.put("P172",22);
		stations.put("P173",22);
		stations.put("P174",22);
		stations.put("P175",22);
		stations.put("P176",22);
		
		
		bikeLocation = 0;
		for (String i : stations.keySet()) {
			if ( pedToFind == i){
			bikeLocation = stations.get(i);		
			System.out.println("Pedelec " + pedToFind +" is currently at station "
					 + bikeLocation);
			break;
			}
		}
		
		
	}

}