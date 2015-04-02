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

import java.util.ArrayList;

public class Location {

	public static int grain_grow_interval;
	public static int num_grain_grown;
	public static int ticks = 0;

	public int grains_here;		// Amount of grains in this patch.
	public int max_grain_here;  // Max amount of grains this patch can hold.
	public ArrayList<Person> list_persons;	// List of the persons in the current location.
	
//Methods { addPerson(person), getGrains( ), removeAllPersons()}


	public void update(){
		harvest();
		if ((grain_grow_interval % ticks) == 0){
			grow_grain();
		}
	}

	public void grow_grain(){
		// if a patch does not have it's maximum amount of grain, add
  		// num-grain-grown to its grain amount
  		if (grains_here < max_grain_here){
  			grains_here = grains_here + num_grain_grown;
  			if (grains_here > max_grain_here){
  				grains_here = max_grain_here;
  			}
  		}
	}

	public void harvest(){
		int wealthHarvest;
		if (list_persons.size()>0){
			wealthHarvest = (int)(grains_here/list_persons.size());
			for(Person p: list_persons){
	      		p.add_wealth(wealthHarvest); 
	      	}
		}
		grains_here = 0;
	} 

	public void addPerson (Person toAdd){
		list_persons.add(toAdd);
	}
	public void removeAllPersons(){
		list_persons.clear();
	}

    public Location( int max_grains){
    	max_grain_here = max_grains;
    	list_persons = new ArrayList<Person>();
    }

    public Location(){
    	list_persons = new ArrayList<Person>();
    	//System.out.println("New Location");
    }

    public void getGrains (){
		System.out.println("Hello, World");
	}


    public static void main(String[] args) {
    	Location loc1 = new Location(50);
    	Person per1 = new Person();
    	Person per2 = new Person();
    	loc1.addPerson(per1);
    	loc1.addPerson(per2);
    	System.out.println(loc1.list_persons.size()); 
    	loc1.removeAllPersons();
    	System.out.println(loc1.list_persons.size()); 
    }
}
























