/*************************************************************************
 *  Compilation:  javac Location.java
 *  Execution:    java Location
 *
 *  Have the information of one location, amount of grains and number of 
 *	persons currently on it.
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
  
public class Location {

	public static int grain_grow_interval;
	public static int num_grain_grown;

	public int grains_here;		// Amount of grains in this patch.
	public int max_grain_here;  // Max amount of grains this patch can hold.
	public Person[] list_persons;	// List of the persons in the current location.
	public int next_harvest_in;    //Time since last harvest of this location.
		
//Methods { addPerson(person), getGrains( ), removeAllPersons()}


    public Location( int max_grains){
    	max_grain_here = max_grains;
    }

    public void getGrains (){
		System.out.println("Hello, World");
	}


    public static void main(String[] args) {
    	Location loc1 = new Location(50);
    	System.out.println(loc1.max_grain_here); 
    }
}
























