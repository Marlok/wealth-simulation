/*************************************************************************
 *  Compilation:  javac Main.java
 *  Execution:    java Main
 *  Execution:    java Main #People #vision_max  #metabolism_max 
 *						#life_expectancy_min #life_expectancy_max 
 *						#percent_best_land #grain_grow_interval 
 *						#Ticks_to_Run
 *
 *  
 *  The wealth distribution model is a representation of where the wealth in 
 *	a society is distributed, and how this wealth distribution changes 
 *	over time.
 *	This program creates 3 csv files in the directori Matlab, the files are 
 *	called poor.csv, middle.csv and rich.csv. 
 *	Each file contains the number if poor, middle and rich people between 
 *	each tick.
 *	The program accept initial parameters for: 
 *	#People                 (2-1000) Number of people in the simulation
 *	#vision_max  			(1-15) Max range in one person vision
 *	#life_expectancy_min 	(1-100) Min ticks a person will live   
 *	#life_expectancy_max 	(1-100) Max ticks a person can live 
 *	#percent_best_land 		(5% - 25%) Porcentage of land with many grains
 *	#grain_grow_interval 	(1-10) How often grain grows
 *	#num_grain_grown        (1-10) How much grain is grown each time
 *	#Ticks_to_Run			(0 - ...) Amount of ticks in the simulation
 *
 *	Example with default parameters:
 *  % java Main
 *	Running with default parameters ... 
 *	Setting up grains on the grid
 * 	Locating starting point of people on the grid
 *	Running simulation...
 *	Simulation completed. Saving...
 *	Files with the amount of poor, middle, and rich have been created, plot 
 *  with Matlab with the script in Matlab/wealthPlot.m
 *  
 *
 *	Example with user defined parameters:
 *	% java Main 250 5 15 1 83 10 4 10 1000
 *	Running with parameters defined by user..
 *	Setting up grains on the grid
 *	Locating starting point of people on the grid
 *	Running simulation...
 *	Simulation completed. Saving...
 *	Files with the amount of poor, middle, and rich have been created, plot 
 *	with Matlab with the script in Matlab/wealthPlot.m
 *
 *************************************************************************/
import java.io.*;


public class Main {
/* This class is the main program, get parameters from the user, or use 
* 	defalt ones to run the simulation.
*/


	public static int PEOPLE;
	public static int ticks = 0;
	public static int numOfTicks = 0;
	public static int MAX_GRAIN = 50; /* maximum amount any patch can hold */			
	public static int GINI_INDEX_RESERVE = 0;		
	public static int LORENZ_POINTS = 0;
	public static double percent_best_land;
	public static int grain_grow_interval;

	public static Location[][] all_Location; 	// 2D array with all locations
	public static Person[] all_People; 			// array of people
	public static int NUM_COLUMNS = 50;			// Size of the grid
	public static int NUM_ROWS = 50;
	public static double DIFFUSE_PERCENT = 25;	// use to difuse grains 
												// depending on best land


    public static void main(String[] args) {
    	if (args.length ==  0) { // If no parameters are defined use default
    		System.out.println((char)27 + "[32mRunning with default "+
    									   "parameters ... "+ (char)27 + "[0m");

	    	PEOPLE = 250; 						// (2-1000)
	    	Person.vision_max = 5;				// (1-15)
	    	Person.metabolism_max = 15;			// (1-25)
	    	Person.life_expectancy_min = 1; 	// (1-100)
	    	Person.life_expectancy_max = 83;	// (1-100)
	    	percent_best_land = 10;				// (5% - 25%)

	    	grain_grow_interval = 1;			// (1-10)
			Location.num_grain_grown = 4;		// (1-10);

			numOfTicks = 1000;

    	} else{
    		if (args.length == 9){ 
    			// Use defined parameters

    			System.out.println((char)27 + "[31mRunning with parameters "+
    									 "defined by user.."+ (char)27 + "[0m");

    			PEOPLE = Integer.parseInt(args[0]); 	
		    	Person.vision_max = Integer.parseInt(args[1]);		   
		    	Person.metabolism_max = Integer.parseInt(args[2]);	   
		    	Person.life_expectancy_min = Integer.parseInt(args[3]);
		    	Person.life_expectancy_max = Integer.parseInt(args[4]);
		    	percent_best_land = Integer.parseInt(args[5]);		

		    	grain_grow_interval = Integer.parseInt(args[6]);			
				Location.num_grain_grown = Integer.parseInt(args[7]);		

				numOfTicks = Integer.parseInt(args[8]);
    		}else{ 
    			// There are some defined parameters but no complete

    			System.out.println((char)27 + "[31mError in the number of "+
    				                           "parameters" + (char)27 + "[0m");
    			
    			System.out.println("Use java Main #People #vision_max "+
    			   "#metabolism_max #life_expectancy_min #life_expectancy_max "+
    			   "#percent_best_land #grain_grow_interval "+
    			   "#num_grain_grown #Ticks_to_Run ");
    			return;
    		}
    	}

		// Preparing the grid 
		System.out.println("Setting up grains on the grid");
		setupLocations(NUM_COLUMNS, NUM_ROWS);

		// Add person to the grid
		System.out.println("Locating starting point of people on the grid");
		setupPersons(PEOPLE);


		// Used to save in a file

		String strPoor="";
		String strMiddle="";
		String strRich="";


	
		System.out.println("Running simulation..."); 

		for (int i =0;i<numOfTicks;i++){
			// This is the main loop to run the simulation

    		update(); // update the simulation
    		
    		// the code next count the amount of Rich, middle and poor people
    		// after each tick

    		int rich = 0;
			int poor = 0;
			int middle = 0;
    		for(Person p: all_People){
	    		if (p.myClass == 'r'){
	    			rich++;
	    		}
	    		if (p.myClass == 'm'){
	    			middle++;
	    		}
	    		if (p.myClass == 'p'){
	    			poor++;
	    		}
	    	}
	    	// Add the values for each tick

	    	strPoor += poor + ",";
			strMiddle += middle + ",";
			strRich += rich + ",";
    	}

    	System.out.println((char)27 + "[32mSimulation completed. Saving..."+
    		(char)27 + "[0m"); 

    	//Saving the files

		savetoFile(strPoor.substring(0, strPoor.length()-1), "poor");
		savetoFile(strMiddle.substring(0, strMiddle.length()-1), "middle");
		savetoFile(strRich.substring(0, strRich.length()-1), "rich");
		
		System.out.println((char)27 + "[34mFiles with the amount of poor, "+
			    "middle, and rich have been created, plot with Matlab with "+
			    "the script Matlab/wealthPlot.m"+(char)27 + "[0m"); 

		//Program End
    }

    public static void savetoFile(String text, String name){
    // This method save a string into a file located in Matlab/ directory
    //	text -> string to save
    //	name -> file name

    	try {
            File file = new File("Matlab/"+name+".csv");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    public static void update(){
    // rules to update for each tick
    	// First each person look around for the direction to move next
    	// depending of the vision.
    	for(Person p: all_People){
	    	p.updateLocation(all_Location);
	    }

	   	// Check for all locations and repart the grains on each one
	   	// if no person is in the location keep the number of grains
	   	// if more than one person is in the location the grains are 
	   	// distributed evenly 

    	for(int i = 0 ; i < NUM_COLUMNS ; i++){
	    	for(int j = 0 ; j < NUM_ROWS ; j++){
	      		all_Location[i][j].harvest();
	    	} 
	    } 

	    // Check if people are alive and move them, if death reset inital
	    // parameters

	    for(Person p: all_People){
	    	p.Update();
	    }

	    // Update the class of each person, determining poor, middle and rich
	    // by finding the richest person, if eople have less then 1/3 of 
	    // these valu, they are poor, if they own between 1/3 and 2/3 of the
	    // riches, they are middle, and more than 2/3 are rich.

	    Person.updateClass(all_People);

	    // grow grains on the land depending of the frecuency defined by 
	    // grain_grow_interval.

	    if (( ticks % grain_grow_interval) == 0){
			for(int i = 0 ; i < NUM_COLUMNS ; i++){
	    		for(int j = 0 ; j < NUM_ROWS ; j++){
	      			all_Location[i][j].grow_grain();
	    		} 
	    	}
		}

		// update the number of ticks
		ticks = ticks + 1;
	}



	public static void setupPersons(int numPeople ){
	// This method create persons and add them to an array
	// after creating them locate them on the grid randomly
    	
    	all_People = new Person[numPeople];

	    for(int i = 0 ; i < all_People.length ; i++){
	      all_People[i] = new Person();
	    }   

	    for(Person p: all_People){
	    	p.col = randomInt(0,NUM_COLUMNS-1);
	    	p.row = randomInt(0,NUM_ROWS-1);
	    }
	}

    public static void setupLocations ( int col, int row ){
    	// This function starts a new 2D array of locations 
    	// Create new object for each location and
    	// disperse grains over the grid. 

    	all_Location = new Location[col][row];  
	    for(int i = 0 ; i < col ; i++){
	    	for(int j = 0 ; j < row ; j++){
	      		all_Location[i][j] = new Location(); 
	    	} 
	    } 
	    spreadGrains (col,row);
	}



	public static void spreadGrains (int col, int row){
		// This function spread grains on locations by giving a max_grain 
		// value for each location, all locations are full of grains in the 
		// start.
		// Depending on the variable percent_best_land the amount
		// of locations is determinated as "best Land"
		// from this best land, the grains are diffused around.


		// We start by defining "best area" in a random way

    	for(int i = 0 ; i < col ; i++){
	    	for(int j = 0 ; j < row ; j++){
	    		if (randomInt(0,100) <= percent_best_land){
	    			all_Location[i][j].max_grain_here = MAX_GRAIN;
	    			all_Location[i][j].grains_here = MAX_GRAIN;
	    		}

	    	} 
	    }

	    // From the "best area" we take some grains (DIFFUSE_PERCENT) and 
	    // spred them on the 8 neighbors 

	    for (int t = 0; t<5; t++){
		    for(int i = 0 ; i < col ; i++){
		    	for(int j = 0 ; j < row ; j++){
		    		if (all_Location[i][j].max_grain_here != 0){
		    			all_Location[i][j].grains_here = 
		    							     all_Location[i][j].max_grain_here ;
		    			diffuseGrains (i, j, DIFFUSE_PERCENT);
		    		}
		    	}
		    } 
		}

		// We diffuse some more the grains to spred them over the grid

		for (int t = 0; t<10; t++){
		    for(int i = 0 ; i < col ; i++){
		    	for(int j = 0 ; j < row ; j++){
		    		diffuseGrains (i, j, DIFFUSE_PERCENT);
		    	}
		    } 
		}
		
		// We set the inital value as an intenger, and set it as the max value

	    for(int i = 0 ; i < col ; i++){
	    	for(int j = 0 ; j < row ; j++){
	    		all_Location[i][j].max_grain_here = 
	    								    (int)all_Location[i][j].grains_here;
	    		all_Location[i][j].grains_here = 
	    								 (int)all_Location[i][j].max_grain_here;
	    	} 
	 	}
    }




    public static void addToNeighbors (int col, int row , double grains){
    	// Add the value of grains to the 8 neighbors 

    	// Right, 
    	all_Location[moveColRight(col,1)][row].grains_here += grains;
    	// Left
    	all_Location[moveColLeft(col,1)][row].grains_here += grains;
    	// Up 
    	all_Location[col][moveRowUp(row,1)].grains_here += grains;
    	// Down
    	all_Location[col][moveRowDown(row,1)].grains_here += grains;
    	// Up-Right
    	all_Location[moveColRight(col,1)][moveRowUp(row,1)].grains_here 
    																  += grains;
    	// Up-Left
    	all_Location[moveColLeft(col,1)][moveRowUp(row,1)].grains_here 
    																  += grains;
    	// Down_Right												
    	all_Location[moveColRight(col,1)][moveRowDown(row,1)].grains_here 
    																  += grains;
    	// Down-Left
    	all_Location[moveColLeft(col,1)][moveRowDown(row,1)].grains_here 
    																  += grains;
    }

    
	public static void diffuseGrains (int col, int row , double percent ){
		// Calculate the value to be diffused depending on the percent value
		// remove this grains from the owner
		// repart this value to the 8 neighbors 

		double grains = (all_Location[col][row].grains_here)*(percent/100); 
		all_Location[col][row].grains_here -= grains;
    	addToNeighbors (col, row , ((1.0*grains)/8));
	}


//-------------- MOVE ON THE GRID ------------------
	// The grid has no limits, if you are traveling over locations, the 
	// extreme right will move you to the starting left.
	// The same going Up and Down, any limit with conect to the oposit side
	// This funcition help to evaluate the next location starting from a 
	// defined one.

	// This methods are used when look for the next location and when
	// diffusing the grains on each location

    public static int moveColRight(int col, int steps){
    	if ((col + steps) >= NUM_COLUMNS){
    		return moveColRight( 0, ((col + steps) - NUM_COLUMNS));
    	}
    	return (col + steps);
    }

    public static int moveColLeft(int col, int steps){
    	if ((col - steps) < 0){
    		return moveColLeft( NUM_COLUMNS, (steps-col));
    	}
    	return (col - steps);
    }

    public static int moveRowDown(int row, int steps){
    	if ((row + steps) >= NUM_ROWS){
    		return moveRowDown( 0, ((row + steps) - NUM_ROWS));
    	}
    	return (row + steps);
    }
    public static int moveRowUp(int row, int steps){
    	if ((row - steps) < 0){
    		return moveRowUp( NUM_ROWS, (steps-row));
    	}
    	return (row - steps);
    }

//-------------- END MOVE ON THE GRID ------------------




//-------------- EXTRA METHODS  ------------------

	public static int randomInt( int min, int max){
		// Return a reandom int within the limits min-max

        int range = max-min;
        return (int)(Math.random()*(range+1)+min);
    }

    public static void printAllLocations(){
    	// print the value of grains in a grid.
    	// Use for testing
		System.out.println("-----------------------------"); 
		String oneRow ="";
		int number;
		String format ="";
		for(int i = 0 ; i < NUM_COLUMNS ; i++){
	    	for(int j = 0 ; j < NUM_ROWS ; j++){
	    		number = (int)( all_Location[i][j].grains_here);
	    		format = Integer.toString(number);
	    		format = String.format("%1$"+2+ "s", format);
	      		oneRow += format + " | "; 
	    	} 
	    	System.out.println(oneRow); 
	    	oneRow = "";
	    }
	}
	public static void printAllPersons(){
    	// print the value of wealth of all persons.
		// use for testing.

		for(Person p: all_People){
	    	System.out.println("Wealth: "+p.wealth ); 
	    }
	}
}














