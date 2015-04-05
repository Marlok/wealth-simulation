/*************************************************************************
 *  Compilation:  javac Location.java
 *  Execution:    java Location
 *
 *  Have the information of one location, amount of grains and number of 
 *	persons currently on it. The methods let us Harvest the location and 
 * 	grow grains in the location depending on time.
 *
 *  % java Location
 * 
 *	New Location_1 Created
 *	New Person_1 Created
 *	Person_1 Added to Location_1
 *	Grains in Location_1: 10
 *	Person_1 current wealth: 9
 *	Harvest location_1
 *	Person_1 current wealth: 19
 *	Grains in Location_1: 10
 *	Persons in location_1: 1
 *	New Person_2 Created
 *	Person_2 Added to Location_1
 *	Person_2 current wealth: 19
 *	Harvest location_1
 *	Person_2 current wealth: 24
 *	Persons in location_1: 2
 *	Removing all persons from location_1
 *	Persons in location_1: 0
 *	
 *************************************************************************/

import java.util.ArrayList;

public class Location {

	public static int grain_grow_interval = 2;	// (1-10);
	public static int num_grain_grown = 10;		// (1-10);

	public double grains_here = 0;		// Amount of grains in this patch.
	public int max_grain_here = 0;  // Max amount of grains this patch can hold.
	public ArrayList<Person> list_persons;	
							  	// List of the persons in the current location.

	public void grow_grain(int ticks){
		// if a patch does not have it's maximum amount of grain, add
  		// num-grain-grown to its grain amount, not more then maxi_grain
  		if (( ticks % grain_grow_interval) == 0){
			if (grains_here < max_grain_here){
  				grains_here = grains_here + num_grain_grown;
  				if (grains_here > max_grain_here){
  					grains_here = max_grain_here;
  				}
  			}
		}

	}

	public void harvest(){
	// Divide the number of actual grains in the location by the amount
	// of people courrently on it, if there is no people, the grains are 
	// not afected.

		if (list_persons.size()>0){
			int wealthHarvest = (int)(grains_here/list_persons.size());
			for(Person p: list_persons){
	      		p.add_wealth(wealthHarvest); 
	      		p.Update();
	      	}
	    	grains_here = 0;
	    	removeAllPersons();
		}
	} 

	public void addPerson (Person toAdd){
	// Add a person to this location

		list_persons.add(toAdd);
	}
	public void removeAllPersons(){
	// Remove all the persons from this location

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

    public static void main(String[] args) {

    	//Test Locations 

    	Location loc1 = new Location(50);
    	loc1.grains_here = 10;
    	System.out.println("New Location_1 Created");
    	Person per1 = new Person();
    	System.out.println("New Person_1 Created");
    	loc1.addPerson(per1);
    	System.out.println("Person_1 Added to Location_1");
    	System.out.println("Grains in Location_1: " + loc1.grains_here); 
    	System.out.println("Person_1 current wealth: " + per1.wealth);
    	loc1.harvest();
    	System.out.println("Harvest location_1");
    	System.out.println("Person_1 current wealth: " + per1.wealth);
    	System.out.println("Grains in Location_1: " + loc1.grains_here); 
    	System.out.println("Persons in location_1: " +loc1.list_persons.size()); 
    	Person per2 = new Person();
    	System.out.println("New Person_2 Created");
    	loc1.addPerson(per2);
    	System.out.println("Person_2 Added to Location_1");
    	System.out.println("Person_2 current wealth: " + per2.wealth);
    	loc1.harvest();
    	System.out.println("Harvest location_1");
    	System.out.println("Person_2 current wealth: " + per2.wealth);
    	System.out.println("Persons in location_1: " +loc1.list_persons.size());
    	System.out.println("Removing all persons from location_1");
    	loc1.removeAllPersons();
    	System.out.println("Persons in location_1: " +loc1.list_persons.size());
    }
}
























