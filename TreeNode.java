import java.util.*;
import java.io.*;

@SuppressWarnings("unchecked")
class TreeNode<E>{
	private E data;
	private TreeNode left, right;
	
	public TreeNode(E data){
		this(data, null, null);
	}
	
	public TreeNode(E data, TreeNode left, TreeNode right){
		this.data = data;
		this.left = left;
		this.right= right;
	}
	
	public E getData(){
		return this.data;
	}
	
	public TreeNode getLeft(){
		return this.left;
	}
	
	public TreeNode getRight(){
		return this.right;
	}
	
	public boolean isLeaf(){
		return left == null && right == null;
	}

	static String chosencity;
	
	static String placename;
	static ArrayList<String> US_AtoM = new ArrayList<>();
	static ArrayList<String> US_NtoZ = new ArrayList<>();
	static ArrayList<String> Canadalist = new ArrayList<>();
	static  ArrayList<String> Mexicolist = new ArrayList<>();
	static  ArrayList<String> SouthAmericalist = new ArrayList<>();
	static  ArrayList<String> CentralAmericalist = new ArrayList<>();

	static ArrayList<String> UnitedStates = new ArrayList<>(); 
	static ArrayList<String> Europe = new ArrayList<>();
	static ArrayList<String> Africa = new ArrayList<>();

	static ArrayList<String> Europe_AtoG = new ArrayList<>();
	static ArrayList<String> Europe_HtoM= new ArrayList<>();
	static ArrayList<String> Europe_NtoT = new ArrayList<>();
	static ArrayList<String> Europe_UtoZ= new ArrayList<>();
	
	static ArrayList<String> MiddleEastlist = new ArrayList<>();
	static ArrayList<String> SmallAsialist = new ArrayList<>();
	
	static ArrayList<String> Australialist = new ArrayList<>();
	static ArrayList<String> NewZealandlist = new ArrayList<>();
	static ArrayList<String> Africa_AtoG = new ArrayList<>();
	static ArrayList<String> Africa_HtoM = new ArrayList<>();
	static ArrayList<String> Africa_NtoT = new ArrayList<>();
	static ArrayList<String> Africa_UtoZ = new ArrayList<>();
	
	static ArrayList<String> chosenlist = new ArrayList<String>();
	static String cityname="";
	
	
	public static void main() throws FileNotFoundException{
		//if true, getRight
		
		Scanner scanfile = new Scanner (new File("TimeZoneList.txt"));
		Scanner input = new Scanner(System.in);
		createArrayList(scanfile);
		sortRegionArray();
		sortAndIndexAll();
		ArrayList<String> chosenlist = BuildTreeNode(input);
		String chosencity = getCityName(chosenlist);
		System.out.println("Chosen city is " + chosencity);
		
	}
	
	/**
	 *Builds TreeNode and ask users questions to select one city list.
	 * @param Scanner console
	 * @return ArrayList<String> that is a list of city names.
	 *
	 */
	public static ArrayList<String> BuildTreeNode(Scanner console){
		
		TreeNode<String> SouthAmerica = new TreeNode<>("SouthAmerica");
		TreeNode<String> CentralAmerica = new TreeNode<>("CentralAmerica");
		TreeNode<String> SouthorCentralAmerica = new TreeNode<>("South America?", CentralAmerica, SouthAmerica);

		
		TreeNode<String> USAtoM = new TreeNode<>("US AtoM");
		TreeNode<String> USNtoZ = new TreeNode<>("US NtoZ");
		
		TreeNode<String> US = new TreeNode<>("Start with A~M?", USNtoZ, USAtoM);
		TreeNode<String> Canada = new TreeNode<>("Canada");
		TreeNode<String> Mexico = new TreeNode<>("Mexico");
		TreeNode<String> nonUS = new TreeNode<>("Canada?", Mexico, Canada);
		
		
		
		TreeNode<String> NorthAmerica = new TreeNode<>("United States?", nonUS, US);


		TreeNode<String> AfricaUtoZ = new TreeNode<>("AfricaUtoZ");
		TreeNode<String> AfricaNtoT = new TreeNode<>("AfricaNtoT");
		TreeNode<String> AfricaHtoM = new TreeNode<>("AfricaHtoM");
		TreeNode<String> AfricaAtoG = new TreeNode<>("AfricaAtoG");
		
		TreeNode<String> AfricaNtoZ = new TreeNode<>("Start with N~T?", AfricaUtoZ, AfricaNtoT);
		TreeNode<String> AfricaAtoM = new TreeNode<>("Start with A~G?", AfricaHtoM, AfricaAtoG);
		
		TreeNode<String> Africa = new TreeNode<>("Start with A~M?", AfricaNtoZ, AfricaAtoM);

		
		TreeNode<String> somewhereineast = new TreeNode<>("where? tell me via email");
		TreeNode<String> nonAsiaAndNonEuropeandNonOceania = new TreeNode<>("Africa?", somewhereineast, Africa);

		TreeNode<String> otherInOceania = new TreeNode<>("islands");//edit here later
		TreeNode<String> NewZeaLand = new TreeNode<>("New Zealand");
		TreeNode<String> nonAustralia = new TreeNode<>("New Zealand?", otherInOceania, NewZeaLand);
		TreeNode<String> Australia = new TreeNode<>("Australia");
		
		TreeNode<String> Oceania = new TreeNode<>("Australia?", nonAustralia, Australia);
		TreeNode<String> nonAsiaAndNonEurope = new TreeNode<>("Oceania?", nonAsiaAndNonEuropeandNonOceania, Oceania);

		TreeNode<String> EuropeUtoZ =new TreeNode<>("Europe UtoZ");
		TreeNode<String> EuropeNtoT = new TreeNode<>("Europe NtoT");
		TreeNode<String> EuropeHtoM = new TreeNode<>("Europe HtoM");
		TreeNode<String> EuropeAtoG = new TreeNode<>("Europe AtoG");
		
		TreeNode<String> Europe_NtoZ = new TreeNode<>("Start with N~T?",EuropeUtoZ, EuropeNtoT);
		TreeNode<String> Europe_AtoM = new TreeNode<>("Start with A~G?", EuropeHtoM, EuropeAtoG);
		TreeNode<String> Europe = new TreeNode<>("Start with A~M?", Europe_NtoZ, Europe_AtoM);
		TreeNode<String> MiddleEast = new TreeNode<>("Middle East");
		TreeNode<String> SmallAsia = new TreeNode<>("Small Asia");
		
		TreeNode<String> LargeAsia = new TreeNode<>("Middle East?", SmallAsia, MiddleEast);
		TreeNode<String> nonAsia = new TreeNode<>("Europe?", nonAsiaAndNonEurope, Europe);

		
		TreeNode<String> East = new TreeNode<>("Asia?", nonAsia, LargeAsia);
		TreeNode<String> West = new TreeNode<>("North America?", SouthorCentralAmerica, NorthAmerica);
		TreeNode<String> hemisphere = new TreeNode<>("West Hemisphere?", East, West);

		TreeNode<String> temp = hemisphere;
	
		//Scanner console = new Scanner(System.in);
		while(!temp.isLeaf()){
			System.out.print(temp.getData());
			System.out.println(" 1.Yes 2.No ");

			
			if(console.nextInt() % 2 == 1){//any integer work yay! means odd numbers
				temp = temp.getRight();
			}else{
				temp = temp.getLeft();
			}
		}

		String itstime = temp.getData();
		//ArrayList<String> chosenlist = new ArrayList<String>();
	
		if(itstime.equals("US AtoM")){
			System.out.println("Which city? Type Number");
			System.out.println(US_AtoM);
			chosenlist.addAll(US_AtoM);
			
			
		}else if(itstime.equals("US NtoZ")){
			System.out.println("Which city? Type Number");
			System.out.println(US_NtoZ);
			chosenlist.addAll(US_NtoZ);
			
			
		}else if(itstime.equals("Canada")){
			System.out.println("Which city? Type Number");
			System.out.println(Canadalist);
			chosenlist.addAll(Canadalist);
			
			
			
		}else if(itstime.equals("Mexico")){
			System.out.println("Which city? Type Number");
			System.out.println(Mexicolist);
			chosenlist.addAll(Mexicolist);
			
			
			
		}else if(itstime.equals("SouthAmerica")){
			System.out.println("Which city? Type Number");
			System.out.println(SouthAmericalist);
			chosenlist.addAll(SouthAmericalist);
			
			
		}else if(itstime.equals("CentralAmerica")){
			System.out.println("Which city? Type Number");
			System.out.println(CentralAmericalist);
			chosenlist.addAll(CentralAmericalist);
			
			
			
		}else if(itstime.equals("Europe AtoG")){
			System.out.println("Which city? Type Number");
			System.out.println(Europe_AtoG);
			chosenlist.addAll(Europe_AtoG);
			
			
		}else if(itstime.equals("Europe HtoM")){
			System.out.println("Which city? Type Number");
			System.out.println(Europe_HtoM);
			chosenlist.addAll(Europe_HtoM);
			
			
		}else if(itstime.equals("Europe NtoT")){
			System.out.println("Which city? Type Number");
			System.out.println(Europe_NtoT);
			chosenlist.addAll(Europe_NtoT);
			
			
		}else if(itstime.equals("Europe UtoZ")){
			System.out.println("Which city? Type Number");
			System.out.println(Europe_UtoZ);
			chosenlist.addAll(Europe_UtoZ);
			
			
		}else if(itstime.equals("Middle East")){
			System.out.println("Which city? Type Number");
			System.out.println(MiddleEastlist);
			chosenlist.addAll(MiddleEastlist);
			
			
		}else if(itstime.equals("Small Asia")){
			System.out.println("Which city? Type Number");
			System.out.println(SmallAsialist);
			chosenlist.addAll(SmallAsialist);
			
			
		}else if(itstime.equals("Australia")){
			System.out.println("Which city? Type Number");
			System.out.println(Australialist);
			chosenlist.addAll(Australialist);
			
			
		}else if(itstime.equals("New Zealand")){
			System.out.println("Which city? Type Number");
			System.out.println(NewZealandlist);
			chosenlist.addAll(NewZealandlist);
			
			
		}else if(itstime.equals("AfricaAtoG")){
			System.out.println("Which city? Type Number");
			System.out.println(Africa_AtoG);
			chosenlist.addAll(Africa_AtoG);
			
			
		}else if(itstime.equals("AfricaHtoM")){
			System.out.println("Which city? Type Number");
			System.out.println(Africa_HtoM);
			chosenlist.addAll(Africa_HtoM);
			
			
		}else if(itstime.equals("AfricaNtoT")){
			System.out.println("Which city? Type Number");
			System.out.println(Africa_NtoT);
			chosenlist.addAll(Africa_NtoT);
			
			
		}else if(itstime.equals("AfricaUtoZ")){
			System.out.println("Which city? Type Number");
			System.out.println(Africa_UtoZ);
			chosenlist.addAll(Africa_UtoZ);
			
			
		}else{
			System.out.println(temp.getData());
		}
		
		return chosenlist;
	}

	/**
	 *get City name by ask users to pick city name from city name list
	 * @param ArrayList<String> which is a list of city names
	 * @return city name as String
	 */
	public static String getCityName(ArrayList<String>citylist){
		Scanner scancitynumber = new Scanner(System.in);
		int citynumber = scancitynumber.nextInt();
		
		String cityname = citylist.get(citynumber-1);
		//char detectperiod = cityname.charAt();

		loop:for(int a=0; a<cityname.length(); a++){
			char detectperiod = cityname.charAt(a);
			if(detectperiod =='.'){
				cityname = cityname.substring(a+1);
			}
		}
		//cityname = cityname.substring(2);
		return cityname;
	}

	/**
	 *adds city names in correspond ArrayList
	 * @param data from a TimeZoneList.txt
	 *
	 */
	public static void createArrayList(Scanner scanfile){

		String checkregion = "";
		int counter=0;
		
		while(scanfile.hasNextLine()){//if there is a line
			String line = scanfile.nextLine();// String line has line
			Scanner seeline = new Scanner(line);// Scanner placename sees line
			
			while(seeline.hasNext()){//while there is something in eachline
				placename = seeline.next();
				
				while(seeline.hasNext() && !seeline.hasNextDouble()){
					String placename2 = seeline.next();
					placename = placename + " " + placename2;
				}

				if(placename.contains("@") && !placename.contains("end")){
					checkregion = placename.substring(1);
					
				}else if(!placename.contains("@")){
					double offset2 = seeline.nextDouble();
					String placenamelow = placename.toLowerCase();
					char firstchar = placenamelow.charAt(0);
					
					//abcdefg hijklm
					//nopqrst uvwxyz
					
					if(checkregion.equals("United States")){
						
						if((int)firstchar<= 109 && (int)firstchar >= 97 ){	
							US_AtoM.add(placename);
							UnitedStates.add(placename);
							counter++;
							
						}else if((int)firstchar >=110 && (int)firstchar <=122){
							US_NtoZ.add(placename);
							UnitedStates.add(placename);
							counter++;
						}
						//|| firstchar == ''
					}else if(checkregion.equals("Canada")){
						Canadalist.add(placename);
						counter++;

					}else if(checkregion.equals("Mexico")){
						Mexicolist.add(placename);
						counter++;
						
					}else if(checkregion.equals("Europe")){
						if((int)firstchar >= 97 && (int)firstchar <= 103){
							Europe.add(placename);
							Europe_AtoG.add(placename);
							counter++;

						}else if((int)firstchar>= 104 && (int) firstchar <=109){
						Europe.add(placename);
							Europe_HtoM.add(placename);
							counter++;
						
						}else if((int)firstchar >= 110 && (int)firstchar <=116){
							Europe.add(placename);
							Europe_NtoT.add(placename);
							counter++;
							
						}else if ((int)firstchar >= 117 && (int)firstchar<=122){
							Europe.add(placename);
							Europe_UtoZ.add(placename);
							counter++;
						}
					}else if(checkregion.equals("Middle East")){
						MiddleEastlist.add(placename);
						counter++;
						
					}else if(checkregion.equals("Asia")){
						SmallAsialist.add(placename);
						counter++;

					}else if(checkregion.equals("South America")){
						SouthAmericalist.add(placename);
						counter++;

					}else if(checkregion.equals("Central America")){
						CentralAmericalist.add(placename);
						counter++;
						
					}else if(checkregion.equals("Australia")){
						Australialist.add(placename);
						counter++;
						
					}else if(checkregion.equals("New Zealand")){
						NewZealandlist.add(placename);
						counter++;
						
					}else if(checkregion.equals("Africa")){
						if((int)firstchar>= 97 && (int)firstchar <= 103){
							
							Africa_AtoG.add(placename);
							counter++;

						}else if((int)firstchar>=104 && (int)firstchar<= 109){
							Africa_HtoM.add(placename);
							Europe.add(placename);
							counter++;
						
						}else if((int)firstchar>= 110 && (int)firstchar <= 116){
							Africa_NtoT.add(placename);
							Europe.add(placename);
							counter++;
							
						}else if ((int)firstchar >= 117 && (int)firstchar <= 122){
							Africa_UtoZ.add(placename);
							Europe.add(placename);
							counter++;
						}
					}

					
				}else if(placename.equals("@end")){
					//do nothing
				}
			}
		}
	}

	
	/**
	 *sorts and add index to datas in ArrayList
	 *@param list of city names as ArrayList<String>
	 * @return ArrayList<String> as sorted and Index added list.
	 */
	public static ArrayList<String> sortAndIndex (ArrayList<String>list){
		sortArrayLists(list);
		addIndex(list);

		return list;
	}

	/**
	 *sorts each ArrayLists
	 *
	 */

	public static void sortRegionArray(){
		Collections.sort(UnitedStates);
		Collections.sort(Canadalist);
		Collections.sort(Mexicolist);
		Collections.sort(Europe);
		Collections.sort(MiddleEastlist);
		Collections.sort(SmallAsialist);
		Collections.sort(Australialist);
		Collections.sort(NewZealandlist);
		Collections.sort(SouthAmericalist);
		Collections.sort(Africa);
		Collections.sort(CentralAmericalist);
	}

	
	/**
	 *sorts data in ArrayList
	 *@param an ArrayList<String> with city name
	 *@return sorted ArrayList
	 */
	public static ArrayList<String> sortArrayLists (ArrayList<String>list1){
		Collections.sort(list1);
		return list1;
	}

	/**
	 *add cisible index to each element
	 * @param an ArrayList<String> with city name
	 * @return an ArrayList<String> with index
	 */

	public static ArrayList<String> addIndex(ArrayList<String>list2){//use queue? get(i);recursion?
		ArrayList<String>aux = new ArrayList<String>();
		int arraylistsize = list2.size();

		for(int i = 0; i<arraylistsize; i++){
			int l = i + 1;
			String index = Integer.toString(l);
			aux.add(index + "." + list2.get(i));
		}
		list2.clear();
		list2.addAll(aux);
		//System.out.println("this is should include index" + list);
		return list2;
	}

	/**sorts and add Index to each city name in a city list
	 * 
	 *
	 */
	public static void sortAndIndexAll(){

		sortAndIndex(US_AtoM);
		sortAndIndex(US_NtoZ);
		sortAndIndex(Canadalist);
		sortAndIndex(Mexicolist);
		sortAndIndex(SouthAmericalist);
		sortAndIndex(CentralAmericalist);

		sortAndIndex(Europe_AtoG);
		sortAndIndex(Europe_HtoM);
		sortAndIndex(Europe_NtoT);
		sortAndIndex(Europe_UtoZ);
		
		sortAndIndex(MiddleEastlist);
		sortAndIndex(SmallAsialist);
		
		sortAndIndex(Australialist);
		sortAndIndex(NewZealandlist);
		
		sortAndIndex(Africa_AtoG);
		sortAndIndex(Africa_HtoM);
		sortAndIndex(Africa_NtoT);
		sortAndIndex(Africa_UtoZ);
	}
}
