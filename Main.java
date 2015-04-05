/*************************************************************************
 *  Compilation:  javac Main.java
 *  Execution:    java Main
 *
 *  Prints "Hello, World". By tradition, this is everyone's first program.
 *
 *  % java Test
 *  Test, Test
 *
 *  These 17 lines of text are comments. They are not part of the program;
 *  they serve to remind us about its properties. The first two lines tell
 *  us what to type to compile and test the program. The next line describes
 *  the purpose of the program. The next few lines give a sample execution
 *  of the program and the resulting output. We will always include such 
 *  lines in our programs and encourage you to do the same.
 *
 *************************************************************************/
import java.io.*;
public class Main {

	public static int ticks = 0;
	public static int MAX_GRAIN = 50; /* maximum amount any patch can hold */			
	public static int GINI_INDEX_RESERVE = 0;		
	public static int LORENZ_POINTS = 0;
	public static double percent_best_land;

	public static Location[][] all_Location; 
	public static Person[] all_People; 
	public static int NUM_COLUMNS = 50;
	public static int NUM_ROWS = 50;
	public static double DIFFUSE_PERCENT = 25;
	/*
	public static int num_people = 0;			/* Amount of people. /
	public static int max_vision			Max value of distance.
	metabolism_max		Max amount of metabolism.
	life_expectancy_min		
	life_expectancy_max
	percent_best_land		Percentage of land that has max-grain.
	grain_growth_interval	How often grain grows.
	num_grains_grown		How much grain is grown each interval.
	richest_wealth		The value on wealth for the richest person.
	poorest_wealth		The value of wealth for the purest person.

	*/

    public static void main(String[] args) {
    	int PEOPLE = 250; 				// (2-1000)
    	Person.vision_max = 5;				// (1-15)
    	Person.metabolism_max = 15;			// (1-25)
    	Person.life_expectancy_min = 1; 	// (1-100)
    	Person.life_expectancy_max = 83;	// (1-100)
    	percent_best_land = 10;				// (5% - 25%)

    	Location.grain_grow_interval = 1;	// (1-10)
		Location.num_grain_grown = 4;		// (1-10);
    	//start.start(PEOPLE, 3,5);

		//System.out.println(move_Left(Integer.parseInt(args[0]), Integer.parseInt(args[1]))); 
		//setupLocations (NUM_ROWS, NUM_COLUMNS);

		String strPoor="";
		String strMiddle="";
		String strRich="";

		setupLocations(NUM_COLUMNS, NUM_ROWS);
		setupPersons(PEOPLE);
		
		

		int poor=0;
		int middle=0;
		int rich=0;

		for(Person p: all_People){
    		if (p.wealth > (2.0*50/3)){
    			rich++;
    		}else{
    			if (p.wealth > (1.0*50/3)){
    				middle++;
    			}else{
    				poor++;
    			}
    		}
    	}
    	strPoor=""+rich;
		strMiddle=""+middle;
		strRich=""+poor;

		for (int i =0;i<100;i++){
			update();
			System.out.println("-------------------------------------"); 
			//printAllLocations();
			poor=0;
			middle=0;
			rich=0;

			for(Person p: all_People){
	    		if (p.wealth > (2.0*Person.max_wealth_now/3)){
	    			rich++;
	    		}else{
	    			if (p.wealth > (1.0*Person.max_wealth_now/3)){
	    				middle++;
	    			}else{
	    				poor++;
	    			}
	    		}
	    	}
	    	Person.min_wealth_now = Person.max_wealth_now;
	    	Person.max_wealth_now = 0;
	    	System.out.println(rich+" "+middle+" "+poor+"= "+(rich+middle+poor));
	    	strPoor+= ","+poor;
			strMiddle+= ","+middle;
			strRich+= ","+rich;
		}
		savetoFile(strPoor, "poor");
		savetoFile(strMiddle, "middle");
		savetoFile(strRich, "rich");
		
    	 
    }

    public static void savetoFile(String text, String name){
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

    	for(int i = 0 ; i < NUM_COLUMNS ; i++){
	    	for(int j = 0 ; j < NUM_ROWS ; j++){
	      		all_Location[i][j].harvest();
	    	} 
	    } 
	    for(Person p: all_People){
	    	p.updateLocation(all_Location);
	    }
	    for(int i = 0 ; i < NUM_COLUMNS ; i++){
	    	for(int j = 0 ; j < NUM_ROWS ; j++){
	      		all_Location[i][j].grow_grain(ticks);
	    	} 
	    }
		ticks = ticks + 1;
	}



	public static void setupPersons(int numPeople ){
    	
    	all_People = new Person[numPeople];

	    for(int i = 0 ; i < all_People.length ; i++){
	      all_People[i] = new Person();
	    }   

	    for(Person p: all_People){
	    	p.col = randomInt(0,NUM_COLUMNS-1);
	    	p.row = randomInt(0,NUM_ROWS-1);
	    	all_Location[p.col][p.row].addPerson(p);
	    	//System.out.println(randCol +", "+randRow ); 
	    }
	}

    public static void setupLocations ( int col, int row ){
    	// This function starts a new 2D array of locations 
    	// Create new object for each locaitn and
    	// disperse grains over this locations. 

    	all_Location = new Location[col][row];  
	    for(int i = 0 ; i < col ; i++){
	    	for(int j = 0 ; j < row ; j++){
	      		all_Location[i][j] = new Location(); 
	    	} 
	    } 
	    spreadGrains (col,row);
	}



	public static void spreadGrains (int col, int row){
		// This function spread grains on locations
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
	    // spred them on the neighbors 

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
		
		for(Person p: all_People){
	    	System.out.println("Wealth: "+p.wealth ); 
	    }
	}
}














