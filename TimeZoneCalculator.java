/*@author Hatsune Hirano
 *@version 1.0.0
 */

import java.util.*;
import java.io.*;



public class TimeZoneCalculator{


	static Queue<String> queueplacenames = new LinkedList<>(); //store city/country names
	static Queue<Double> desUTCoffset = new LinkedList<>();
	static Map<String, Double> UTCoffsetsdata = new HashMap<>(); //store UTCoffset and city/country name https://www.geeksforgeeks.org/map-interface-java-examples/
	static String showcity;
	static String convertfrom;
	static ArrayList<String> chosenlist = new ArrayList<>();
	static boolean addcity = true;
	static ArrayList<String> availablecity = new ArrayList<>();
	
	
	public static void main(String[]args) throws FileNotFoundException{
		//start prep
		boolean addmore = true;
		Scanner scanfile = new Scanner(new File("TimeZoneList.txt"));
		storeUTCoffsetdata(scanfile);
		String placename2 = "";
		
		Scanner scanfile2 = new Scanner(new File("TimeZoneList.txt"));
		TreeNode.createArrayList(scanfile2);
		TreeNode.sortRegionArray();
		TreeNode.sortAndIndexAll();
		//end prep

		boolean calculate = true;

		while(calculate == true){
		Scanner input = new Scanner(System.in);
		System.out.println("Select city you want to conver to");

		while(addmore == true){
			getWaitlistQueue(input);
			addmore = askAddMoreCity();
		}
		
		String convertfrom = getConvertFrom(input);
		
		double originoffset = getOriginUTCoffset(convertfrom, UTCoffsetsdata);


		getDestinationUTCQueue(UTCoffsetsdata,queueplacenames);
		double oritime = askTime(convertfrom);
		
		double oriUTCoffset = getOriginUTCoffset(convertfrom, UTCoffsetsdata);
		System.out.println(oritime);
		Map<String, Double> MapResult = getResult(desUTCoffset, oritime, oriUTCoffset);
		

		System.out.println("When it is " + oritime + " in " + convertfrom + "...");
		while(!queueplacenames.isEmpty()){
			String name = queueplacenames.remove();
			double time = MapResult.get(name);
			
			if(time%1 >= 0.59){//if min exists and min>60
				time += 1.0;
				time -= 0.60;
			}
			if(time > 24){
				time -= 24;//nextday
				System.out.printf("Time in " + name + " is %.2f", time);
				System.out.print(" (next day) \n ");
			}else if(time < 0){
				time += 24;
				System.out.printf("Time in " + name + " is %.2f", time);
				System.out.print(" (yesterday) \n");
			}else{
				System.out.printf("Time in " + name + " is %.2f", time);
				System.out.println("");
		
			}
		}

		calculate = askDoAgain();
	}
		
	}

	/**
	 *Stores places name(city/country) and their UCT offset in Map
	 *
	 *@param scanner that reads TimeZoneList.txt, and HashMap that stores places name and it UCT offset
	 *@
	 */
	public static void storeUTCoffsetdata(Scanner scan){//txtの方に見やすいよう"United States" とか"Canada"とか書きたいんだけどそれを書いた時の処理がうまくできない
		if(scan == null){
			throw new IllegalArgumentException("TimeZoneList.txt is empty, please check the file");
		}
		int count=0;
		showcity = "";
		
		while(scan.hasNextLine()){//if there is a line
			String line = scan.nextLine();// String line has line
			Scanner seeline = new Scanner(line);// Scanner placename sees line
			
			while(seeline.hasNext()){//while there is something in eachline
				String placename = seeline.next();
				
				while(seeline.hasNext() && !seeline.hasNextDouble()){
					String placename2 = seeline.next();
					placename = placename + " " + placename2;
				}

				
					if(!placename.contains("@")){
						double offset = seeline.nextDouble();
						UTCoffsetsdata.put(placename, offset);
						availablecity.add(placename);
						showcity += placename + "\n";
						count++;
					}else{
						showcity += "\n"+ placename + "\n";
					}
			}
		}
		Collections.sort(availablecity);//.sort();
	}

	/**
	 *asks whether user wanto to add another city/country that convert to
	 *
	 *@return true if user want to add more city/country 
	 */
	public static boolean askAddMoreCity(){

		System.out.println("Do you want to add city/country convert to? \n 1.Yes 2.No"); 
		Scanner console2 = new Scanner (System.in);

		if(console2.nextDouble() == 1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 *Stores UTC offset of cities that user wants to know its time into Queue
	 * @param Map<String, Double> Mapdatas, Queue<String>citynamequeue
	 * 
	 */

	public static void getDestinationUTCQueue(Map<String,Double>Mapdatas, Queue<String>citynamequeue){
		for(int s = 0; s<citynamequeue.size(); s++){
			String name;
			name = citynamequeue.remove();
			citynamequeue.add(name);
			double Offsets = Mapdatas.get(name);
			desUTCoffset.add(Offsets);
		}
		//System.out.println(desUTCoffset);
	}

	/**
	 *gets UTC offset of origin city from Map<String, Double> UTCoffsetdata
	 * @param String which is origin city name, Map<String, Double> Mapdatas, which includes UTCoffset data.
	 * @return origin's UTC offset as double 
	 *
	 */

	public static double getOriginUTCoffset(String origin, Map<String,Double>Mapdatas){
		return Mapdatas.get(origin);
	}

	/**
	 *gets Waitlist of destination city names
	 *@param Scanner
	 *@return Queue that stores name of each destination city
	 */

	public static Queue<String> getWaitlistQueue(Scanner input) {
		//boolean addmore = true;
		//while(addmore == true){
			ArrayList<String> chosenlist = TreeNode.BuildTreeNode(input);
			String chosencity = TreeNode.getCityName(chosenlist);
			System.out.println("Chosen city is " + chosencity);
			queueplacenames.add(chosencity);
			chosenlist.clear();
			//addmore = askAddMoreCity();
			//System.out.println(queueplacenames);
		//}
		return queueplacenames;
	}

	/**
	 *gets city name from a list of city names
	 * @param Scanner
	 * @return city name as String
	 *
	 */

	public static String getConvertFrom(Scanner input){
		System.out.println("Choose city you want to convert from");
		ArrayList<String> chosenlist = TreeNode.BuildTreeNode(input);
		String chosencity = TreeNode.getCityName(chosenlist);

		return chosencity;
	}

	/**
	 *calculates destination's time, and store the result and city's name in Map
	 * @param Queue<Double> that stores each destination city's UTC offset, origin's time, and origin's UTC offset
	 * @return Map<String, Double> that has destination's city name and time.
	 */
	public static Map<String, Double> getResult(Queue<Double>desUTCoffset, double oritime, double oriUTC){//目的地の標準時からの時差は判明している。出発地の標準時からの時差も判明している。やるべきことは、目的地の時間＝出発地の標準時に目的地の標準時を足すこと（UTCがプラスかマイナスかは関係ない）
		Map<String, Double> resultMap = new HashMap<>();

		while(!desUTCoffset.isEmpty()){
			
			double desUTC = desUTCoffset.remove();
			
			double timeGAP = desUTC - oriUTC;
			double time = oritime + timeGAP;
			String place = queueplacenames.remove();
			queueplacenames.add(place);
			resultMap.put(place, time);
		}
		
		return resultMap;
	}

	/**asks user to answer time in origin city
	 * @param convertfrom as String that stores origin's city name
	 * @return double as time in origin city.
	 *
	 */
	public static double askTime(String convertfrom){
		Scanner userinput = new Scanner(System.in);

		System.out.println("What time is it in " + convertfrom + " ? (Ex. 1:30pm -> 13.30)");
		return userinput.nextDouble();
		
	}

	/**asks user whether user wants to convert time again from the beginning
	 *@return boolean if user wants to convert it again. 
	 */
	public static boolean askDoAgain(){
		Scanner userinput = new Scanner(System.in);
		System.out.println("Do you want to calculate again? \t 1.Yes 2.No");
		if(userinput.nextInt() % 2 == 1){
			return true;
		}else{
			return false;
		}
	}

}
