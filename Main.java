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
  
public class Main {

	public static int ticks = 0;
	public static int MAX_GRAIN = 0; /* maximum amount any patch can hold */			
	public static int GINI_INDEX_RESERVE = 0;		
	public static int LORENZ_POINTS = 0;
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
    	Main start = new Main();
    	
    	final int PEOPLE = 30; 				// (2-1000)
    	Person.vision_max = 5;				// (1-15)
    	Person.metabolism_max = 10;			// (1-25)
    	Person.life_expectancy_min = 1; 	// (1-100)
    	Person.life_expectancy_max = 100;	// (1-100)

    	Location.grain_grow_interval = 1;	// (1-10)
		Location.num_grain_grown = 1;	// (1-10);
    	start.start(PEOPLE, 3,5);
    	 
    }

    public void start (int numPeople, int numColumns, int numRows ){
    	Person[] all_People = new Person[numPeople];
    	Location[][] all_Location = 
    				new Location[numColumns][numRows];

	    for(int i = 0 ; i < all_People.length ; i++){
	      all_People[i] = new Person();
	    }   

	    for(int i = 0 ; i < numColumns ; i++){
	    	for(int j = 0 ; j < numRows ; j++){
	      		all_Location[i][j] = new Location(); 
	    	} 
	    } 

	    for(Person p: all_People){
	    	int randCol = randomInt(0,numColumns-1);
	    	int randRow = randomInt(0,numRows-1);
	    	all_Location[randCol][randRow].addPerson(p);
	    	System.out.println(randCol +", "+randRow ); 
	    }
	}

	public void update(){
		ticks = ticks + 1;
	}


	public int randomInt( int min, int max){
        int range = max-min;
        return (int)(Math.random()*(range+1)+min);
    }
}














